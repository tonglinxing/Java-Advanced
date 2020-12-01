package com.tonglxer.grammer.proxy;

import com.tonglxer.grammer.proxy.cglib.CGLIBSendMessage;
import com.tonglxer.grammer.proxy.cglib.CGLIBSendMessageProxyFactory;
import com.tonglxer.grammer.proxy.jdk.JdkProxyFactory;
import com.tonglxer.grammer.proxy.jdk.SendMessage;
import com.tonglxer.grammer.proxy.jdk.SendMessageImpl;

/**
 * @Author Tong LinXing
 * @date 2020/11/30
 */
public class ProxyDemo {

    /**
     * jdk 和 cglib 动态代理机制对比：
     *
     * 1. JDK 动态代理只能只能代理实现了接口的类，而 CGLIB 可以代理未实现任何接口的类。
     * 2. CGLIB 动态代理是通过生成一个被代理类的子类来拦截被代理类的方法调用，因此不能代理声明为 final 类型的类和方法。
     * 3. jdk方式的效率更高
     *
     * 静态代理和动态代理的区别：
     *
     * 1. 灵活性：
     * 动态代理更加灵活，不需要必须实现接口，可以直接代理实现类，并且可以不需要针对每个目标类都创建一个代理类。
     * 静态代理中，接口一旦新增加方法，目标对象和代理对象都要进行修改。
     * 2. JVM层面：
     * 静态代理在编译时就将接口、实现类、代理类这些都变成了一个个实际的 class 文件。
     * 动态代理是在运行时动态生成类字节码，并加载到 JVM 中的。
     *
     * */
    public static void main(String[] args) {
        jdkProxyDemo();
        System.out.println();
        cglibProxyDemo();
    }

    /**
     * jdk动态代理机制
     *
     * 1. 定义一个接口及其实现类.
     * 2. 自定义 InvocationHandler 并重写invoke方法,
     *    在 invoke 方法中调用原生方法（被代理类的方法）并自定义一些处理逻辑.
     * 3. 通过 Proxy.newProxyInstance方法创建代理对象.
     *
     * */
    private static void jdkProxyDemo() {
        Object object = JdkProxyFactory.getProxy(new SendMessageImpl());
        if (object instanceof SendMessage) {
            SendMessage sendMessage = (SendMessage)object;
            sendMessage.send("Hello jdk Proxy.");
        }
    }

    /**
     * cglib动态代理机制
     *
     * 1. 定义一个类.
     * 2. 自定义 MethodInterceptor 并重写 intercept 方法,
     *    intercept 用于拦截增强被代理类的方法，和 JDK 动态代理中的 invoke 方法类似.
     * 3. 通过 Enhancer 类的 create()创建代理类.
     *
     * */
    private static void cglibProxyDemo() {
        Object object = CGLIBSendMessageProxyFactory.getProxy(CGLIBSendMessage.class);
        if (object instanceof CGLIBSendMessage) {
            CGLIBSendMessage sendMessage = (CGLIBSendMessage)object;
            sendMessage.send("Hello cglib Proxy.");
        }
    }
}
