package com.tjyw.atom.network.conf;

/**
 * Created by stephen on 13/12/2016.
 */

public interface IScheme {

    interface PATH {

        String COMPLETE_INFO = "completeInfo";

        String UPLOAD_AVATAR = "uploadAvatar";

        String CUSTOMER_SERVICE = "customerService";

        String MOBILE_AUTH = "mobileAuth";

        interface FULL {

            String COMPLETE_INFO = "com.ewsh.td://erwanshenghuo/completeInfo";

            String UPLOAD_AVATAR = "com.ewsh.td://erwanshenghuo/uploadAvatar";

            String CUSTOMER_SERVICE = "com.ewsh.td://erwanshenghuo/customerService";

            String MOBILE_AUTH = "com.ewsh.td://erwanshenghuo/mobileAuth";
        }
    }
}
