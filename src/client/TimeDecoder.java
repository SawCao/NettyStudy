package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: caorui
 * Time: 2018/10/16
 **/
//解决分片问题，将接受到的数据流按大小接受，并且进行格式转换
public class TimeDecoder extends ByteToMessageDecoder { // (1)
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        if (in.readableBytes() < 4) {
            return; // (3)
        }

        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
