package com.tjyw.atom.network.conf;

/**
 * Created by stephen on 7/17/16.
 */
public interface IApiFrom {


    interface USER_EXPLORE {

        String FATE = "FATE"; // 缘分页打招呼

        String NEAR = "NEAR"; // 附近页打招呼

        String ENCOUNTER = "ENCOUNTER"; // 遇见页打招呼

        String MESSAGE_CONVERSE = "MESSAGE_CONVERSE"; // 消息详情页

        String MASTER = "MASTER"; // 我的

        String PAY_SUCCESS = "PAY_SUCCESS"; // 我的
    }

    interface USER_GREET {

        String FATE = "FATE"; // 缘分页打招呼

        String NEAR = "NEAR"; // 附近页打招呼

        String ENCOUNTER = "ENCOUNTER"; // 遇见页打招呼

        String EXPLORER = "EXPLORER"; // 用户详情页打招呼

        String RANDOM = "RANDOM"; // 用户随机推荐(首页出现)

        String MESSAGE_RECOMMEND = "MESSAGE_RECOMMEND"; // 消息列表页推荐

        String FIRST_VISIBLE = "FIRST_VISIBLE"; // 次日招呼
    }
}
