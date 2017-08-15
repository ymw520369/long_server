/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2017年2月8日 	
 */
package org.alan.miner.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.google.protobuf.ByteString;
import org.alan.mars.protobuf.PbMessage;
import org.alan.mars.protobuf.PbMessage.ReqPingMessage;
import org.alan.mars.protobuf.PbMessage.ResPingMessage;
import org.alan.mars.protobuf.PbMessage.TXMessage;

/**
 *
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
public class TcpPingTest {

	static Logger log = Logger.getLogger(TcpPingTest.class);

	public static void main(String[] args) throws InterruptedException {
		PropertyConfigurator.configureAndWatch("config/miner.log4j.cfg");

		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					// 解码用
					p.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(2*1024*1024, 0, 4, 0, 4));
					p.addLast("protobufDecoder",
							new ProtobufDecoder(PbMessage.TXMessage.getDefaultInstance()));
					// 编码用
					p.addLast("frameEncoder", new LengthFieldPrepender(4));
					p.addLast("protobufEncoder", new ProtobufEncoder());
					p.addLast("handler", new HelloClientIntHandler());
				}
			});

			// Start the client.
			ChannelFuture f = b.connect("192.168.5.119", 11100).sync();
			// Wait until the connection is closed.
			// f.channel().closeFuture().sync();

			while (true) {
				long time = System.currentTimeMillis();
				log.info("send message time is " + time);
				ReqPingMessage rpm = ReqPingMessage.newBuilder().setTime(time)
						.build();
				f.channel().writeAndFlush(
						PbMessage.TXMessage.newBuilder()
								.setMessageType(rpm.getMessageType())
								.setDataMessage(rpm.toByteString()).build());
				Thread.sleep(10000);
			}

		} finally {
			workerGroup.shutdownGracefully();
		}

	}

	public static class HelloClientIntHandler extends
			SimpleChannelInboundHandler<TXMessage> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, TXMessage msg)
				throws Exception {
			int mt = msg.getMessageType();
			ByteString bs = msg.getDataMessage();
			System.out.println(bs.size());
			ResPingMessage rpm = ResPingMessage.parseFrom(bs);
			int ping = rpm.getPing();
			int mtp = rpm.getMessageType();
			log.info("message received, ping is " + ping + ",mtp=" + mtp);
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {

		}

	}
}
