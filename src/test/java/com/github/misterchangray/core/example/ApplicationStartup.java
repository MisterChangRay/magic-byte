package com.github.misterchangray.core.example;

import com.github.misterchangray.core.MagicByte;

/**
 * 这里是项目的启动类
 */
public class ApplicationStartup {
    public static void main(String[] args) {
        // 假设此处数据为设备的上行数据
        byte[] deviceUploadMessage = new byte[]{};

        // 以下为业务端逻辑解析建议方式
        // 1. 首先应该解析报文头
        Head pack = MagicByte.pack(deviceUploadMessage, Head.class);
        // 2. 这里省略检查报文头逻辑
        // 3. 根据报文类型解析为特定的报文格式
        switch (pack.getCmd()) {
            case 1:
                HeartbeatCmd pack1 = MagicByte.pack(deviceUploadMessage, HeartbeatCmd.class);
                doHeartbeatCmd(pack1);
                break;
            case 2:
                EchoCmd pack2 = MagicByte.pack(deviceUploadMessage, EchoCmd.class);
                doEchoCmd(pack2);
                break;
            // 更多的报文类型
        }
    }

    private static void doEchoCmd(EchoCmd pack2) {
        // 这里处理EchoCmd 的业务
    }

    private static void doHeartbeatCmd(HeartbeatCmd pack1) {
        // 这里处理 HeartbeatCmd 的业务
    }

}
