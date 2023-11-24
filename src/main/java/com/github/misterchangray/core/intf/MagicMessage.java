package com.github.misterchangray.core.intf;

/**
 * 消息模板类
 *
 * 所有注册消息需要实现该接口
 */
public interface MagicMessage {

    /**
     * 获取消息的类型值
     * @return 返回对应消息的类型
     */
    default int cmd() {return -1;};

}
