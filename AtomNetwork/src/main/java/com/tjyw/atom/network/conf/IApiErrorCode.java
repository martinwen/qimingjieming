package com.tjyw.atom.network.conf;

/**
 * Created by stephen on 16-7-7.
 */
public interface IApiErrorCode {

    int OK = 0;

    interface SS {

        /**
         * 未绑定的帐号
         */
        int UNBOUND_MOBILE = 11002;

        /**
         * 当前手机号已绑定其它帐号
         */
        int BIND_DUPLICATE_MOBILE = 11101;

        /**
         * 短信验证码已超时
         */
        int SMS_CODE_OVER_TIME = 11102;

        /**
         * 短信验证码发送频率过快
         */
        int SMS_CODE_FREQUENCY_FAST = 11103;
    }
}
