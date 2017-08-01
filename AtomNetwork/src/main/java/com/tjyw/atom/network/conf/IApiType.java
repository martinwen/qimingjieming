package com.tjyw.atom.network.conf;

/**
 * Created by stephen on 7/14/16.
 */
public interface IApiType {

    interface User {

        int Search = 10001;

        int Search_Near = 10002;

        int Search_Recommend = 10003;

        int Search_Encounter = 10004;

        int SEARCH_NEXT_GREET = 10007; // 次日招呼

        enum PHOTO_STATUS {

            ENABLE, // 审核通过正常图片

            CHECK, // 审核中的图片

            DISABLE, // 审核不通过的图片
        }
    }

    interface CLIENT {

        interface SECURITY {

            interface TYPE {

                int TOP = 1;

                int SECOUND = 2;

                int BASE = 3;
            }
        }
    }
}
