package com.charlie.spring.ioc;

// 用于封装/记录bean的信息
// 1) Scope 2) Bean对应的Class对象，通过反射可以生成对应的对象
public class BeanDefinition {
    private String scope;
    private Class clazz;

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "scope='" + scope + '\'' +
                ", clazz=" + clazz +
                '}';
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
