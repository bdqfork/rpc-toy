package cn.bdqfork.rpc.protocol.netty.consumer;

import cn.bdqfork.rpc.remote.Response;
import cn.bdqfork.rpc.remote.context.DefaultFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author bdq
 * @since 2019-02-20
 */
@ChannelHandler.Sharable
public class ClientContextHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DefaultFuture.doReceived((Response) msg);
    }

}
