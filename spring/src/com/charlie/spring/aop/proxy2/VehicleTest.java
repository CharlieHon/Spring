package com.charlie.spring.aop.proxy2;

import org.junit.Test;

public class VehicleTest {

    @Test
    public void proxyRun() {
        // 创建ship对象
        Vehicle vehicle = new Car();
        // 创建vehicleProxyProvider对象，并且传入要代理的对象
        VehicleProxyProvider vehicleProxyProvider = new VehicleProxyProvider(vehicle);
        // 获取代理对象，该对象可以代理执行方法
        // 1. proxy编译类型是 Vehicle 所以可以调用 run()
        // 2. 运行类型是 class com.sun.proxy.$Proxy4(代理类型)
        // 3. 因此执行proxy.run() 方法会进到代理对象的 invoke方法
        Vehicle proxy = vehicleProxyProvider.getProxy();
        // 如何体现动态？
        /*
        1. 被代理对象是动态的，通过接口指向实现的类对象 Vehicle vehicle = new Car();
        2. 执行的方法是动态指定的 proxy.run()
         */
        //proxy.run();
        String fly = proxy.fly(1000);
        System.out.println("fly=" + fly);
        System.out.println("OK!");
    }

    @Test
    public void run() {
        Vehicle vehicle = new Car();
        vehicle.run();
    }
}
