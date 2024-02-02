package com.charlie.spring.ioc;


import com.charlie.spring.annotation.Autowired;
import com.charlie.spring.annotation.Component;
import com.charlie.spring.annotation.ComponentScan;
import com.charlie.spring.annotation.Scope;
import com.charlie.spring.processor.BeanPostProcessor;
import com.charlie.spring.processor.InitializingBean;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MySpringApplicationContext {
    private Class configClass;
    // 定义属性BeanDefinitionMap，存放Definition对象
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    // 定义属性SingletonObjects，存放单例对象
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    // 定义一个属性beanPostProcessorsList，存放后置处理器
    private List<BeanPostProcessor> beanPostProcessorsList = new ArrayList<>();

    public MySpringApplicationContext(Class configClass) {
        // 完成扫描指定包
        beanDefinitionByScan(configClass);
        //System.out.println("beanDefinitionMap=" + beanDefinitionMap);

        // 通过beanDefinitionMap初始化singletonObjects单例池
        Enumeration<String> keys = beanDefinitionMap.keys();
        // 遍历所有的beanDefinition对象
        while (keys.hasMoreElements()) {
            // 得到beanName
            String beanName = keys.nextElement();
            // 通过beanName得到beanDefinition对象
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            // 判断该bean是singleton还是prototype
            if ("singleton".equalsIgnoreCase(beanDefinition.getScope())) {
                // 将该bean实例放入到singletonObjects集合
                singletonObjects.put(beanName, createBean(beanName, beanDefinition));
            }
        }
        //System.out.println("singletonObjects=" + singletonObjects);
    }

    // 该方法完成对指定包的扫描，并将Bean信息封装到BeanDefinition对象，再放入到Map
    public void beanDefinitionByScan(Class configClass) {
        this.configClass = configClass;
        ComponentScan componentScan = (ComponentScan) this.configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = componentScan.value();
        path = path.replace(".", "/");
        // 1. 得到类的加载器-App类加载器，这样可以拿到实际执行的目标文件所在路径 target/classes
        ClassLoader classLoader = MySpringApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource(path);
        System.out.println("resource=" + resource);
        File file = new File(resource.getPath());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String fileAbsolutePath = f.getAbsolutePath();
                if (fileAbsolutePath.endsWith(".class")) {
                    String className = fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("\\") + 1,
                            fileAbsolutePath.indexOf(".class"));
                    String classFullName = path.replace("/", ".") + "." + className;
                    try {
                        Class<?> clazz = classLoader.loadClass(classFullName);
                        if (clazz.isAnnotationPresent(Component.class)) {
                            // 如果该类使用了 @Component注解，说明是 Spring bean
                            System.out.println("这是一个Spring bean=" + clazz + "，类名=" + className);

                            /*
                            1. 为了方便，将后置处理器放入到一个ArrayList集合中
                            2. 如果是后置处理器，就放入到 beanPostProcessorList
                            3. 在原生的Spring容器中，对后置处理器还是走个getBean，createBean
                                ，但是需要在singletonObjects中加入相应的业务逻辑
                            4. 因为这里只是为了理解后置处理器的机制，所以简化处理
                             */

                            // 判断当前clazz是否实现BeanPostProcessor接口
                            // 说明：这里不能通过 instanceof 来判断clazz是否实现了该接口，因为clazz是一个类对象而非实例对象
                            if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                                BeanPostProcessor beanPostProcessor = (BeanPostProcessor) clazz.newInstance();
                                // 放入到beanPostProcessorsList
                                beanPostProcessorsList.add(beanPostProcessor);
                                // 不将后置处理器放入单例池！
                                continue;
                            }

                            // 先得到 beanName
                            // 1. 得到Component注解
                            Component componentAnnotation = clazz.getDeclaredAnnotation(Component.class);
                            // 2. 获得注解的value，可能没有配置value值，则value为默认值""
                            String beanName = componentAnnotation.value();
                            if ("".equals(beanName)) {
                                // 将该类的首字母小写作为beanName
                                beanName = StringUtils.uncapitalize(className);
                            }
                            // 3. 将Bean的信息封装到BeanDefinition对象，放入到BeanDefinitionMap
                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setClazz(clazz);
                            // 4. 获取Scope
                            if (clazz.isAnnotationPresent(Scope.class)) {   // 如果有 @Scope 注解
                                // 如果配置了Scope，就获取配置的指
                                Scope scopeAnnotation = clazz.getDeclaredAnnotation(Scope.class);
                                beanDefinition.setScope(scopeAnnotation.value());
                            } else {
                                // 如果没有配置，就默认值为singleton
                                beanDefinition.setScope("singleton");
                            }
                            // 5. 将beanDefinition对象放入到Map
                            beanDefinitionMap.put(beanName, beanDefinition);
                        } else {
                            System.out.println("不是一个Spring bean=" + clazz + "，类名=" + className);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("=====================");
            }
        }
    }

    // 完成createBean(BeanDefinition beanDefinition) 方法
    public Object createBean(String beanName, BeanDefinition beanDefinition) {
        // 得到Bean的clazz对象
        Class clazz = beanDefinition.getClazz();
        // 使用反射得到实例
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            // 分析：这里会加入依赖注入的业务逻辑
            // 1. 遍历当前要创建的对象的所有字段，找到需要依赖注入的字段
            for (Field declaredField : clazz.getDeclaredFields()) {
                // 2. 判断该字段是否有 @Autowired 修饰
                if (declaredField.isAnnotationPresent(Autowired.class)) {
                    /* todo：处理 @Autowired 的 required
                    Autowired autowiredAnnotation declaredField.getAnnotation(Autowired.class)
                    autowiredAnnotation.value();
                    ...
                     */
                    // 3. 得到字段的名字，因为需要过名字进行装配
                    String name = declaredField.getName();
                    // 4. 通过getBean方法来获取要组装的对象
                    Object bean = getBean(name);
                    // 5. 进行组装，通过反射进行set
                    declaredField.setAccessible(true);  // 因为属性(private MonsterDAO monsterDAO)是私有的，所以需要进行爆破
                    declaredField.set(instance, bean);
                }
            }

            System.out.println("======创建好Bean======" + instance);

            // 在Bean的初始化方法前，调用后置处理器的before方法
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorsList) {
                // 在后置处理器的before方法，可以对容器的bean实例进行处理，可以返回处理后的bean实例
                //  相当于做啦一个前置处理
                Object current = beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
                // 判断后置处理器返回结果是否为null，非null则赋值给instance
                if (current != null) {
                    instance = current;
                }
            }

            // 在这里判断是否执行Bean的初始化方法
            // 1. 判断当前创建的Bean独享是否实现了InitializingBean接口
            // 2. instanceof 表示判断某个对象的运行类型是不是某个类型，或者某个类型的字类型
            // 4. 这里就是用到了接口编程
            if (instance instanceof InitializingBean) {
                // 3. 将 instance转成接口 InitializingBean类型
                ((InitializingBean) instance).afterPropertiesSet();
            }

            // 在Bean的初始化方法前，调用后置处理器的after方法
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorsList) {
                // 在后置处理器的before方法，可以对容器的bean实例进行处理，可以返回处理后的bean实例
                //  相当于做啦一个前置处理
                Object current = beanPostProcessor.postProcessAfterInitialization(instance, beanName);
                if (current != null) {
                    instance = current;
                }
            }
            System.out.println("==================================");
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如果反射创建对象失败
        return null;
    }

    // 编写方法 getBean(String beanName)，返回容器中的对象
    public Object getBean(String beanName) {
        // 判断传入的beanName是否在beanDefinitionMap中存在
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            // 得到beanDefinition的scope，分别进行处理
            if ("singleton".equalsIgnoreCase(beanDefinition.getScope())) {
                // 说明是单例，直接从单例池重获取
                return singletonObjects.get(beanName);
            } else {
                // 如果不是单例，调用 createBean，反射得到一个对象
                return createBean(beanName, beanDefinition);
            }
        } else {
            // 如果不存在，抛出一个空指针异常
            throw new NullPointerException("没有该bean");
        }
    }
}
