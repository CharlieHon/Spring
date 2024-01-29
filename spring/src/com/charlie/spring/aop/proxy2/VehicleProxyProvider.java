package com.charlie.spring.aop.proxy2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// VehicleProxyProvider 该类可以返回一个代理对象
public class VehicleProxyProvider {
    // 定义一个属性，target_vehicle表示真正要执行的对象，该对象实现了Vehicle接口
    private Vehicle target_vehicle;

    public VehicleProxyProvider(Vehicle target_vehicle) {
        this.target_vehicle = target_vehicle;
    }

    // 编写一个方法，可以返回一个代理对象
    // 1. 这个方法非常重要，较难理解
    public Vehicle getProxy() {
        /*
        public static Object newProxyInstance(ClassLoader loader,
                                      Class<?>[] interfaces,
                                      InvocationHandler h)

         1. Proxy.newProxyInstance() 方法可以返回一个代理对象
         2. ClassLoader loader：类加载器
         3. Class<?>[] interfaces：将来要代理对象的接口信息
         4. InvocationHandler h：调用处理器/对象，有一个非常重要的方法invoke
         */

        // 1) 得到类加载器
        ClassLoader classLoader = target_vehicle.getClass().getClassLoader();
        // 2) 得到要代理的对象/被执行对象的接口信息(其实现的接口有哪些)，底层是通过接口来完成
        Class<?>[] interfaces = target_vehicle.getClass().getInterfaces();

        // 3) 创建InvocationHandler对象
        // 因为InvocationHandler是接口，所以可以通过匿名对象方式来创建该对象
        /**
         * public interface InvocationHandler {
         *      public Object invoke(Object proxy, Method method, Object[] args)
         *         throws Throwable;
         * }
         * invoke方法是将来执行target_vehicle的方法时，会调用到的
         */
        InvocationHandler invocationHandler = new InvocationHandler() {
            /**
             * invoke方法是将来执行target_vehicle的方法时，会调用到
             * proxy 表示代理对象
             * method 就是通过代理对象调用方法时的那个方法 如：代理对象.run()
             * args：表示调用 代理对象.run(xx) 传入的参数xx
             * @return 表示代理对象.run(xx) 执行后的结果
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("交通工具开始运行了....");
                // 这里通过反射调用 target_vehicle 的 method 方法
                // method是：public abstract void com.charlie.spring.aop.proxy2.Vehicle.run()
                // target_vehicle是：Ship对象
                // args 是null
                Object result = method.invoke(target_vehicle, args);
                System.out.println("交通停止运行....");
                return result;
            }
        };

        Vehicle proxy = (Vehicle) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        return proxy;
    }
}
