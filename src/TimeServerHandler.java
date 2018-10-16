import client.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: caorui
 * Time: 2018/10/16
 **/
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
/*        //分配容量为4bit的buffer
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        //buffer写入
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        //ChannelFuture就是一个IO操作，因为其是异步并且必须close关闭的，所以需要添加一个监听器来监听其状态并且执行close
        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        */

        ChannelFuture f = ctx.writeAndFlush(new UnixTime());
        f.addListener(ChannelFutureListener.CLOSE);

        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        }); // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
