package study.netty.channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import study.netty.channel.handlle.EchoClientHandler;

import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2017/7/17.
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group  = new NioEventLoopGroup();
        try{
            Bootstrap boot = new Bootstrap();
            boot.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("localhost",8888))
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture feature = boot.connect().sync();
            feature.channel().closeFuture().sync();
        }
        finally{
            group.shutdownGracefully().sync();
        }

    }
}
