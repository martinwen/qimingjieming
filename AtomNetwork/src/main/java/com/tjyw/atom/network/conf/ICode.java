package com.tjyw.atom.network.conf;

/**
 * Created by stephen on 6/28/16.
 */
public interface ICode {

    interface SECTION {

        int USER_CENTER = 1119;

        int GALLERY = 1120;

        int MESSAGE = 1121;

        int PAY = 1122;

        int CAMERA = 1123;

        int CAMERA_FOR_ALBUM = 1124;

        int SCHEMA = 1125;

        int SS = 1126;

        int DYNAMIC = 1127;
    }

    interface USER_CENTER {

        int AVATAR = 11191;

        int ALBUM = 11192;

        int MODIFY = 11193;

        int MODIFY_TAG = 11194;

        int GREET = 11195;
    }

    interface GALLERY {

        int IMAGE_CHOOSE = 11201;

        int NEXT = 11202;

        int MEMBER = 11203;
    }

    interface PAY {

        int ALIPAY_SUCCESS = 11221;

        int WX_SUCCESS = 11222;
    }

    interface SCHEMA {

        int AVATAR = 11251;

        int ALBUM = 11252;

        int CAMERA = 11253;

        int PHOTO = 11254;
    }

    interface SS {

        int OK = 11261;

        int RESET_PASSWORD = 11262;
    }

    interface DYNAMIC {

        int ISSUE_SUCCESS = 11271;
    }
}
