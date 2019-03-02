package cn.bdqfork.rpc.consumer.proxy;

import cn.bdqfork.common.exception.RpcException;
import cn.bdqfork.rpc.invoker.Invoker;

/**
 * @author bdq
 * @date 2019-03-01
 */
public class ProxyFactoryBean<T> implements ProxyFactory<T> {
    private Invoker<Object> invoker;
    private Class<T> serviceInterface;
    private String refName;

    private ProxyFactoryBean(Invoker<Object> invoker, Class<T> serviceInterface, String refName) {
        this.invoker = invoker;
        this.serviceInterface = serviceInterface;
        this.refName = refName;
    }

    @Override
    public T getProxy(ProxyType proxyType) throws RpcException {
        if (proxyType == ProxyType.JDK) {
            return new JdkProxyInstanceGenerator<T>(invoker, serviceInterface, refName).newProxyInstance();
        } else {
            return new JavassistProxyInstanceGenerator<T>(invoker, serviceInterface, refName).newProxyInstance();
        }
    }

    public static class Builder<T> {
        private Invoker<Object> invoker;
        private Class<T> serviceInterface;
        private String refName;

        public Builder<T> invoker(Invoker<Object> invoker) {
            this.invoker = invoker;
            return this;
        }

        public Builder<T> serviceInterface(Class<T> serviceInterface) {
            this.serviceInterface = serviceInterface;
            return this;
        }

        public Builder<T> refName(String refName) {
            this.refName = refName;
            return this;
        }

        public ProxyFactoryBean<T> build() {
            return new ProxyFactoryBean<>(invoker, serviceInterface, refName);
        }
    }


}
