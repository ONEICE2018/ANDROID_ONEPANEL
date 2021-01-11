package com.oneice.onepanel;

import com.oneice.onepanel.onetools.ConvertCode;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;


public class TcpClient {
    public TcpClient getClient(String cIP, int cPORT) {
        return client;

    }

    public void setClient(TcpClient client) {
        this.client = client;
    }

    private TcpClient client = null;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    //与服务端的连接通道
    public io.netty.channel.Channel channel = null;
    private boolean life = false;

    public boolean getLife() {
        if (channel == null) {
            setLife(false);
        }
        return life;
    }

    public void disconnect() {
        if (channel != null) {
            channel.close();
        }
    }

    public void setLife(boolean life) {
        this.life = life;
        //连接成功与否的不同处理
        MainActivity.modbusAsker.setLife(life);
        MainActivity.mainActivity.Tcpislife(life);


    }


    private String IP;
    private int PORT;
    private boolean connecting = false;


    public TcpClient(String cIP, int cPORT) {
        this.IP = cIP;
        this.PORT = cPORT;


        if (channel != null && channel.isOpen()) {
            channel.close();
            System.out.println("TCP2222");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("TCP3333");
                connect(IP, PORT);

            }
        }).start();
    }

    /**
     * 连接服务端
     */
    public void connect(String IP, int PORT) {
        try {
            NioEventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap()
                    // 指定channel类型
                    .channel(NioSocketChannel.class)
                    // 指定EventLoopGroup
                    .group(group)
                    // 指定Handler
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //添加发送数据编码器
                            pipeline.addLast(new ClientEncoder());
                            //添加数据处理器
                            pipeline.addLast(new TcpClientListener());
                        }
                    });
            // 连接到服务端
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(IP, PORT));
            //获取连接通道
            channel = channelFuture.sync().channel();
            if (channel != null)
                setLife(true);
            System.out.println("TCP5555");
            // sendconmod(0x00A0,0);

        } catch (Exception e) {
            // Log.e(TAG, "连接失败：" + e.getMessage());
            System.out.println("TCP44444");
            setLife(false);
            e.printStackTrace();
        }
    }

    public class ClientEncoder extends MessageToByteEncoder<Object> {

        private static final String TAG = "ClientEncoder";

        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, Object data, ByteBuf byteBuf) throws Exception {
            //自己发送过来的东西进行编码
            byteBuf.writeBytes(ConvertCode.hexString2Bytes(data.toString()));
        }
    }

    public class TcpClientListener extends SimpleChannelInboundHandler<Object> {
        /*
         *
         * 接收到数据的非阻塞回调 需要进行流式处理
         * */
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            //  String seedata=msg.toString();
            ByteBuf in = (ByteBuf) msg;
            List<Byte> bl = new ArrayList<>();

            try {
                while (in.isReadable()) { // (1)
                    bl.add(in.readByte());
                }
                byte[] bls = ConvertCode.listTobyte(bl);
                MainActivity.mainActivity.analysis_Modbus(bls, bl.size());
            } finally {
                // ReferenceCountUtil.release(msg); // (2)
            }

            //showmsgs(msg.toString());
        }

        /**
         * 与服务端连接成功的回调
         *
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            setLife(true);

            //  showmsgs("TCP OK!");
        }

        /**
         * 与服务端断开的回调
         *
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            setLife(false);
            //showmsgs("TCP Client lost");
        }

    }


}



