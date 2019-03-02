package cn.bdqfork.rpc.consumer.client;

import cn.bdqfork.common.exception.RemoteConnectionLostException;
import cn.bdqfork.common.exception.RpcException;
import cn.bdqfork.rpc.netty.DataDecoder;
import cn.bdqfork.rpc.netty.DataEncoder;
import cn.bdqfork.rpc.netty.NettyChannel;
import cn.bdqfork.rpc.serializer.HessianSerializer;
import cn.bdqfork.rpc.serializer.JdkSerializer;
import cn.bdqfork.rpc.serializer.Serializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bdq
 * @date 2019-02-20
 */
public class NettyClient {
    private Logger log = LoggerFactory.getLogger(NettyClient.class);

    private String host;
    private Integer port;
    private Serializer serializer;
    private EventLoopGroup group;

    public NettyClient(String host, Integer port) {
        this(host, port, new HessianSerializer());
    }

    public NettyClient(String host, Integer port, Serializer serializer) {
        this.host = host;
        this.port = port;
        this.serializer = serializer;
    }

    public void open() {
        group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(64 * 1024, 0, 4, 0, 4))
                                    .addLast(new DataDecoder(serializer))
                                    .addLast(new DataEncoder(serializer))
                                    .addLast(new ClientHandler());
                        }
                    });
            bootstrap.connect().sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            close();
        }
    }

    public void send(Object data) throws RemoteConnectionLostException {
        Channel channel = NettyChannel.getChannel(host, port);
        if (channel == null) {
            throw new RemoteConnectionLostException("connection lost !");
        }
        ChannelFuture future = channel.writeAndFlush(data);
        try {
            future.await();
            if (future.isSuccess()) {
                log.debug("send message success !");
            } else {
                log.error("failed send message !", future.cause());
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public void close() {
        group.shutdownGracefully();
    }
}
