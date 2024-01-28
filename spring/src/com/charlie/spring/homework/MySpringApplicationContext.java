package com.charlie.spring.homework;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class MySpringApplicationContext {
    private Class configClass;      // 配置类 <=> beans.xml
    private final ConcurrentHashMap<String, Object> ioc = new ConcurrentHashMap<>();    // ioc容器

    public MySpringApplicationContext(Class configClass) {
        // 拿到注解类
        this.configClass = configClass;
        // 获取注解类上注解类
        ComponentScan componentScan = (ComponentScan) this.configClass.getDeclaredAnnotation(ComponentScan.class);
        // 拿到注解信息value，即component-scan 扫描路径
        String path = componentScan.value();   // com.charlie.spring.component
        // 获取扫描路径下所有的类文件
        // 1. 先得到类加载器，使用App方式来加载
        ClassLoader classLoader = MySpringApplicationContext.class.getClassLoader();
        // 使用 / 替换类名中的 .
        path = path.replace(".", "/");  // com/charlie/spring/component
        // 找到目标资源的全路径
        URL resource = classLoader.getResource(path); // file:/E:/Spring/spring/out/production/spring/com/charlie/spring/component
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            loadBeans(file, path, classLoader);
        }
    }

    public Object getBeans(String key) {
        return ioc.get(key);
    }

    private void loadBeans(File file, String path, ClassLoader classLoader) {
        File[] files = file.listFiles();
        for (File f : files) {
            // 文件绝对路径
            String absolutePath = f.getAbsolutePath();  // // E:\Spring\spring\out\production\spring\com\charlie\spring\component\UserService.class
            if (absolutePath.endsWith(".class")) {  // 说明是类文件
                // CharlieSpringApplicationContext
                String className = absolutePath.substring(absolutePath.lastIndexOf("\\") + 1, absolutePath.indexOf(".class"));
                // 得到类的全路径 com.charlie.spring.component.UserService
                String classFullPath = path.replace("/", ".") + "." + className;
                try {
                    // 获取到扫描包下的类的clazz对象
                    Class<?> clazz = classLoader.loadClass(classFullPath);  // 轻量级类加载
                    if (clazz.isAnnotationPresent(Component.class) || clazz.isAnnotationPresent(Service.class)
                            || clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Repository.class)) {

                        // 以controller为例，加载自定义key，即value
                        if (clazz.isAnnotationPresent(Component.class)) {
                            className = clazz.getDeclaredAnnotation(Component.class).value();
                        }
                        Class<?> aClass = Class.forName(classFullPath);
                        try {
                            Object instance = aClass.newInstance();
                            ioc.put(StringUtils.uncapitalize(className), instance);
                        } catch (InstantiationException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        System.out.println("非组件...");
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
