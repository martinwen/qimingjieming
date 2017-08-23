package com.tjyw.atom.network.result;

/**
 * Created by stephen on 16-8-18.
 */
public class RetroPayPreviewResult implements RetroResultItem  {

    private static final long serialVersionUID = 7738852811160932790L;

    public int money;

    public String orderNo;

    public String title;

    public static class PreviewData implements RetroResultItem {

        private static final long serialVersionUID = -6416347441880619433L;

        public String returnMsg;

        public String prepayId;

        public String partnerId;

        public String nonceStr;

        public String sign;

        public String timestamp;

        public String packageValue;

        public String returnCode;
    }
}
