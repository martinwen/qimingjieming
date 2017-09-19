package com.tjyw.atom.network.model;

import com.tjyw.atom.network.result.RetroResultItem;

import java.util.List;

/**
 * Created by stephen on 7/19/16.
 */
public class Message implements RetroResultItem {

    private static final long serialVersionUID = 8486803656566793582L;

    public interface TYPE {

        interface SENT {

            int TEXT = 50001; // 文字回复(打完的招呼、回答过的问题，最终会变成文字成为一条消息记录)

            int VOICE = 50002; // 语音回复(暂时不需要实现)

            int VIDEO = 50003; // 视频回复(暂时不需要实现)

            int INPUT = 50004; // 手输回复(充值或得到红包后，输入框中输入的内容)
        }

        interface INBOX {

            int TEXT = 50101; // 文字回复

            int QUESTION_TEXT = 50102; // QA文字问题

            int QUESTION_VOICE = 50103; // QA语音问题

            int VOICE = 50104; // 语音回复

            int RED = 50105; // 红包回复

            int PHOTO = 50106; // 图片回复

            int LOCAL_HOT_LINE = 50199; // 本地在线客服问答
        }
    }

    public interface RED {

        int INFANT = 50301; // 红包未打开

        int OPENED = 50302; // 红包已打开

        int SENIOR = 50303; // 红包已使用
    }

    public String date;

    public String detail;

    public List<PropertyOption> questionList;

    public boolean redPackOpenStatus;

    public boolean redPackUserStatus;

    public boolean state;

    public long timeStamp;

    public int type;

    public String voiceUrl;

    public String photo;

    public String userName;

    public int userGender;

    public int redStatus;

    public boolean system;
}
