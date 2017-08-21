package com.tjyw.atom.alipay;

import java.io.Serializable;

/**
 * Created by stephen on 9/27/16.
 */
public class AlipayResult implements Serializable {

    private static final long serialVersionUID = -7626500467627535292L;

    public Response alipay_trade_app_pay_response;

    public String sign;

    public String sign_type;

    public static class Response implements Serializable {

        private static final long serialVersionUID = 7668703734764508152L;

        public String code;

        public String msg;

        public String app_id;

        public String out_trade_no;

        public String trade_no;

        public double total_amount;

        public String seller_id;

        public String charset;
    }
}
