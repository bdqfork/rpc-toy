package cn.bdqfork.rpc.registry;

import cn.bdqfork.rpc.config.RegistryConfig;

/**
 * @author bdq
 * @date 2019-03-03
 */
public abstract class AbstractRegistry implements Registry {
    protected boolean running;
    protected RegistryConfig registryConfig;

    @Override
    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }
}
