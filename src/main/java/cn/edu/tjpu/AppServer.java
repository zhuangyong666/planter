package cn.edu.tjpu;

import cn.edu.tjpu.decoder.MyDecoder;
import cn.edu.tjpu.serverhandler.PlanterServerHandler;
import cn.edu.tjpu.utils.MyBatisUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AppServer {
    private static final Log LOGGER = LogFactory.getLog(AppServer.class);

    public void start(int port) {
        //配置nio服务端线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChildChannelHandler());
            //绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();
            if (!MyBatisUtil.initDataSource()) {
                System.out.println("初始化数据源失败");
            }
            System.out.println("服务器启动成功，端口为" + port);
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            //优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            //ByteBuf delimer = Unpooled.copiedBuffer("245F".getBytes());
            socketChannel.pipeline().addLast(new MyDecoder());
            //socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimer));
            socketChannel.pipeline().addLast(new PlanterServerHandler());
        }
    }

    public static void main(String[] args) {
        new AppServer().start(8080);
    }
}
