package com.charlie.spring.annotation;

import com.charlie.spring.applicationcontext.CharlieApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CharlieSpringApplicationContext 类的作用类似于Spring的原生容器
 */
public class CharlieSpringApplicationContext {
    private Class configClass;
    // ioc存放的就是通过反射创建的对象(基于注解方式)
    private final ConcurrentHashMap<String, Object> ioc = new ConcurrentHashMap<>();

    // 构造器
    public CharlieSpringApplicationContext(Class configClass) {
        this.configClass = configClass;
        //System.out.println("this.configClass=" + this.configClass);
        // 得到要扫描的包
        // 1. 先得到CharlieSpringConfig的注解 @ComponentScan(value = "com.charlie.spring.component")
        ComponentScan componentScan = (ComponentScan) this.configClass.getDeclaredAnnotation(ComponentScan.class);
        // 2. 通过ComponentScan的value得到要扫描的包
        String path = componentScan.value();
        //System.out.println("要扫描的包=" + path);    // 要扫描的包=com.charlie.spring.component

        // 得到要扫描的包下的所有资源(类.class)
        // 1. 先得到类的加载器
        ClassLoader classLoader = CharlieApplicationContext.class.getClassLoader();
        // 2. 通过类的加载器获取到要扫描包的资源url
        path = path.replace(".", "/"); // 将路径中的 . 替换为 /
        URL resource = classLoader.getResource(path);   // "com/charlie/spring/component"
        //System.out.println("URL resource=" + resource); // file:/E:/Spring/spring/out/production/spring/com/charlie/spring/component
        // 3. 将要加载的资源(.class)路径下的文件进行遍历 => io
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                //System.out.println(f.getAbsolutePath());
                // E:\Spring\spring\out\production\spring\com\charlie\spring\component\UserService.class
                // 获取到 com.charlie.spring.component.UserService
                String fileAbsolutePath = f.getAbsolutePath();

                // 这里只处理 .class 文件
                if (fileAbsolutePath.endsWith(".class")) {
                    // 1. 获取到类名
                    String className = fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("\\") + 1, fileAbsolutePath.indexOf(".class"));
                    //System.out.println("className=" + className);   // className=MyComponent
                    // 2. 获取类的完整路径(全类名)
                    // path.replace("/", ".") => com.charlie.spring.component
                    String classFullName = path.replace("/", ".") + "." + className;
                    //System.out.println("classFullName=" + classFullName);   // com.charlie.spring.component.UserService

                    // 3. 判断该类是不是需要注入到容器中，就看该类是不是有注解 @Component @Service
                    try {
                        // 这时，就得到了该类的 Class 对象
                        // 1. 也可以使用 Class.forName(classFullName) 反射加载得到类对象
                        // 2. 区别在于 1) 会调用该类的静态方法，下面方法不会(轻量)
                        // 3. aClass.isAnnotationPresent(Component.class) 判断该类是否有 @Component 注解
                        Class<?> aClass = classLoader.loadClass(classFullName);
                        if (aClass.isAnnotationPresent(Component.class) || aClass.isAnnotationPresent(Controller.class)
                                || aClass.isAnnotationPresent(Service.class) || aClass.isAnnotationPresent(Repository.class)) {

                            // 通过注解获取value值，即自定义id。以Component为例
                            if (aClass.isAnnotationPresent(Component.class)) {
                                // 获取到该注解
                                Component component = aClass.getDeclaredAnnotation(Component.class);
                                String id = component.value();
                                if (!id.isEmpty()) {    // id非空 ""
                                    className = id;     // 则将key设置为id值
                                }
                            }

                            // 这时就可以反射对象，并放入到容器中
                            Class<?> clazz = Class.forName(classFullName);
                            Object instance = clazz.newInstance();
                            // 默认情况下，类名首字母小写作为id
                            ioc.put(StringUtils.uncapitalize(className), instance);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
    }

    // 编写方法，返回容器对象
    public Object getBean(String name) {
        return ioc.get(name);
    }
}
