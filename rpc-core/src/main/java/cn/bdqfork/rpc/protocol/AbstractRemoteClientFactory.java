package cn.bdqfork.rpc.protocol;

import cn.bdqfork.common.constant.Const;
import cn.bdqfork.common.extension.ExtensionLoader;
import cn.bdqfork.common.URL;

/**
 * @author bdq
 * @since 2019/9/20
 */
public abstract class AbstractRemoteClientFactory implements RemoteClientFactory {

    @Override
    public RemoteClient[] getRemoteClients(URL url) {
        String serialization = url.getParameter(Const.SERIALIZATION_KEY, "jdk");
        Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class)
                .getExtension(serialization);
        int connections = Integer.parseInt(url.getParameter(Const.CONNECTIONS_KEY, "1"));

        RemoteClient[] remoteClients = new RemoteClient[connections];
        for (int i = 0; i < connections; i++) {
            remoteClients[i] = createRemoteClient(url, serializer);
        }
        return remoteClients;
    }

    protected abstract RemoteClient createRemoteClient(URL url, Serializer serializer);
}
