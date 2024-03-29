package com.charlie.spring.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// 切面类，类似于之前写的MyProxyProvider，但是功能强大很多
@Component  // 会注入到IOC容器
@Order(value = 2)   // 表示该切面类执行的顺序，value的值越小，优先级越高
@Aspect     // 标识是一个切面类，底层有切面编程支撑(动态代理+反射+动态绑定)
public class SmartAnimalAspect {

    // 定义一个切入点，在后面使用时可以直接引用，提高了复用性
    @Pointcut(value = "execution(public int com.charlie.spring.aop.aspectj.APo.getSum(int, int))")
    public void myPointCut() {}

    // 希望将showBeginLog方法切入到APo-getSum前执行(前置通知)
    //@Before(value = "execution(public int com.charlie.spring.aop.aspectj.APo.getSum(int, int))")
    /**
     * 1. Before 表示是前置通知，即在目标对象执行方法前执行
     * 2. value 指定切入到哪个类(APo)的哪个方法(getSum)，带形参列表是因为可能同名方法(重载)
     *      形式：访问修饰符 返回类型 全类名.方法名(形参列表)
     * 3. showBeginLog方法可以理解成就是一个切入方法，方法名可以自定义，如 showBeginLog
     * 4. JoinPoint 表示在底层执行时，由Aspectj切面框架给该切入方法传入 joinPoint对象
     *      通过该方法，可以获取到相关信息
     */
    // 这里使用一个定义好的切入点
    @Before(value = "myPointCut()")
    public void showBeginLog(JoinPoint joinPoint) {
        // 通过链接点对象joinPoint可以获取方法签名，即com.charlie.spring.aop.aspectj.APo.getSum(int, int)
        Signature signature = joinPoint.getSignature();
        System.out.println("SmartAnimalAspect切面类showBeginLog()[使用切入点表达式重用]-方法执行前-日志-方法名-" + signature.getName() + "-参数-" +
                Arrays.toString(joinPoint.getArgs()));
    }

    // 返回通知，即把showSuccessEndLog()方法切入到目标对象方法正常执行完毕后的地方
    // 1. 如果希望目标方法(getSum)执行结果返回给切入方法(showSuccessEndLog)
    // 2. 可以在 @AfterReturning 增加属性，returning="res"
    // 3. 同时在切入方法增加参数 Object res
    // 4. 注意：return="res" 和 Object res 参数名要保持一致
    //@AfterReturning(value = "execution(public int com.charlie.spring.aop.aspectj.APo.getSum(int, int))",
    //        returning = "res") // 返回通知
    @AfterReturning(value = "myPointCut()", returning = "res")
    public void showSuccessEndLog(JoinPoint joinPoint, Object res) {
        Signature signature = joinPoint.getSignature();
        System.out.println("SmartAnimalAspect切面类showSuccessEndLog()-方法执行正常结束-日志-方法名-" + signature.getName()
                + "-返回结果=" + res);
    }

    // 异常通知：即把showExceptionLog方法切入到目标对象方法执行发生异常的catch块
    //@AfterThrowing(value = "execution(public int com.charlie.spring.aop.aspectj.APo.getSum(int, int))",
    //        throwing = "throwable")
    @AfterThrowing(value = "myPointCut()", throwing = "throwable")
    public void showExceptionLog(JoinPoint joinPoint, Throwable throwable) {
        Signature signature = joinPoint.getSignature();
        System.out.println("SmartAnimalAspect切面类showExceptionLog()-方法执行异常-日志-方法名-" + signature.getName() + "-异常信息=" + throwable);
    }

    // 最终通知：把showFinallyEndLog方法切入到目标方法执行后，不管是否发生异常，都要执行 finally{}块
    //@After(value = "execution(public int com.charlie.spring.aop.aspectj.APo.getSum(int, int))") // 返回通知
    @After(value = "myPointCut()")
    public void showFinallyEndLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("SmartAnimalAspect切面类showFinallyEndLog()-方法执行异常-日志-方法名-" + signature.getName());
    }

    // 新的前置通知
    //@Before(value = "execution(public void com.charlie.spring.aop.aspectj.Camera.work()) || execution(public void com.charlie.spring.aop.aspectj.Phone.work())")
    //public void hi(JoinPoint joinPoint) {
    //    Signature signature = joinPoint.getSignature();
    //    System.out.println("(SmartAnimalAspect-前置通知-Phone&Camera-)" + signature.getName());
    //}
    @Before(value = "execution(public void com.charlie.spring.aop.aspectj.UsbInterface.work())")
    public void hi(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("(SmartAnimalAspect-前置通知-Phone&Camera-)" + signature.getName());
    }

    // 作用域没有实现接口的类
    @Before(value = "execution(public void Car.run())")
    public void ok1(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("切面类-ok1方法-" + signature.getName());
    }

    @After(value = "execution(public void Car.run())")
    public void ok2(JoinPoint joinPoint) {  // AOP-JoinPoint
        // 获取目标方法名：run
        String name = joinPoint.getSignature().getName();
        // 获取目标方法所属类的类名：com.charlie.spring.aop.aspectj.Car
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        // 获取目标方法所属类的简单类名：Car
        String simpleName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        // 获取目标方法声明类型(public(1), private protected)，以int形式返回
        int modifiers = joinPoint.getSignature().getModifiers();
        // 获取传入目标方法的参数，返回一个数组
        Object[] args = joinPoint.getArgs();
        // 获取被代理的对象：Car@2772
        Object target = joinPoint.getTarget();
        // 获取代理对象自己：com.charlie.spring.aop.aspectj.Car@a8e6492
        Object aThis = joinPoint.getThis();
        System.out.println("ok2~");
    }

}
