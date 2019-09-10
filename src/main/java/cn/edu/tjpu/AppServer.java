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

import java.io.IOException;
import java.io.InputStream;

/**
 * Hello world!
 */
public class AppServer {
    private int port = 8081;
    private static final Log LOGGER = LogFactory.getLog(AppServer.class);

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        InputStream in = null;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,
                    workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            //	所有自定义的业务从这开始
                            System.out.println("有客户端连接");
                            ch.pipeline().addLast("decoder", new MyDecoder());
                            ch.pipeline().addLast(new PlanterServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(this.port).sync();
            MyBatisUtil.initDataSource();
            System.out.println("服务端启动成功，端口为:" + port);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            LOGGER.error("服务端启动失败", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new AppServer().start();
    }
}
