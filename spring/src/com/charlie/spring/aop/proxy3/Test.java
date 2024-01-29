package com.charlie.spring.aop.proxy3;


public class Test {
    public static void main(String[] args) {
        SmartProxyProvider smartProxyProvider = new SmartProxyProvider(new APo());
        SmartAnimal proxy = smartProxyProvider.getProxy();
        System.out.println("proxy运行类型=" + proxy.getClass());    // class com.sun.proxy.$Proxy0
        proxy.getSub(10, 2);
        System.out.println("=========================");
        proxy.getSum(10, 2);
    }
}
