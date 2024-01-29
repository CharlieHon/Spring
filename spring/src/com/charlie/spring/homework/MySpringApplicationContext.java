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
    private Class config;
    private final ConcurrentHashMap<String, Object> ioc = new ConcurrentHashMap<>();

    public MySpringApplicationContext(Class config) {
        this.config = config;
        // 获取配置类(beans.xml)的注解value，即component-scan
        ComponentScan declaredAnnotation = (ComponentScan) config.getDeclaredAnnotation(ComponentScan.class);
        String path = declaredAnnotation.value();   // com.charlie.spring.component
        path = path.replace(".", "/");  // com/charlie/spring/component
        // 得到类加载器
        ClassLoader classLoader = MySpringApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource(path);
        File file = new File(resource.getPath());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String absolutePath = f.getAbsolutePath();
                if (absolutePath.endsWith(".class")) {
                    String className = absolutePath.substring(absolutePath.lastIndexOf("\\") + 1, absolutePath.indexOf(".class"));
                    String fullClassName = path.replace("/", ".") + "." + className;
                    try {
                        Class<?> clazz = Class.forName(fullClassName);
                        if (clazz.isAnnotationPresent(Component.class) || clazz.isAnnotationPresent(Repository.class)
                                || clazz.isAnnotationPresent(Service.class) || clazz.isAnnotationPresent(Controller.class)) {
                            // 以Controller为例，演示将注解的value值作为bean的id
                            if (clazz.isAnnotationPresent(Component.class)) {
                                className =  clazz.getDeclaredAnnotation(Component.class).value();
                            }
                            Object bean = clazz.newInstance();
                            ioc.put(StringUtils.uncapitalize(className), bean);
                        } else {
                            System.out.println(fullClassName + "非注解类...");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("非以.class结尾的组件...");
                }
            }
        }
    }

    public Object getBean(String key) {
        return ioc.get(key);
    }
}
