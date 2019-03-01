package cn.bdqfork.provider.api;

import cn.bdqfork.rpc.provider.Exporter;
import cn.bdqfork.rpc.provider.ServiceCenter;
import cn.bdqfork.rpc.provider.invoker.RpcRemoteInvoker;
import cn.bdqfork.rpc.registry.zookeeper.ZkRegistry;

/**
 * @author bdq
 * @date 2019-02-22
 */
public class Main {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int port = 8081;
                Exporter exporter = new Exporter("127.0.0.1", port, new ZkRegistry("127.0.0.1:2181", 60, 60));
                ServiceCenter server = new ServiceCenter("127.0.0.1", port, exporter);
                server.setInvoker(new RpcRemoteInvoker());
                server.register("rpc-test", UserService.class.getName(), new UserServiceImpl(port));
                server.start();
            }
        }).start();
    }
}
