package com.tjyw.atom.alipay;

/**
 * Created by zhaijianwei on 16/3/2.
 */
public interface IAlipayCallback {

    interface RESULT_STATUS {

        int SUCCESS = 9000; // 订单支付成功

        int OPERATING = 8000; // 正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态

        int FAIL = 4000; // 订单支付失败

        int REPEAT = 5000; // 重复请求

        int CANCLE = 6001; // 用户中途取消

        int DISCONNECT = 6002; // 网络连接出错

        int UNKNOWN = 6004; // 支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
    }

    void pOnAliPayCallback(int resultStatus, AlipayResult result);
}
