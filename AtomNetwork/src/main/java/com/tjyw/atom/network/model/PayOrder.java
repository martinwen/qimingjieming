package com.tjyw.atom.network.model;

import com.tjyw.atom.network.result.RetroResultItem;

/**
 * Created by stephen on 17-8-18.
 */
public class PayOrder implements RetroResultItem {

    private static final long serialVersionUID = -2282241746086150868L;

    public String title;

    public int money;

    public String orderNo;

    public Wxparameter data;

    public static class Wxparameter implements RetroResultItem {

        private static final long serialVersionUID = 6437711568496752033L;

        public String timestamp;

        public String sign;

        public String returnCode;

        public String returnMsg;

        public String packageValue;

        public String partnerId;

        public String nonceStr;

        public String prepayId;

        public String appID;
    }
}
