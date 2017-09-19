package com.tjyw.atom.network.model;

import com.tjyw.atom.network.result.RetroListResult;

/**
 * Created by stephen on 8/4/16.
 */
public class MessageConverse extends RetroListResult<Message> {

    private static final long serialVersionUID = 584419609622206224L;

    public interface REPLY {

        int CHARGE = 50201;

        int INPUT = 50202;

        int NONE = 50203;
    }

    public String insertDate;

    public String userId;

    public int replyType = REPLY.NONE;

    public int redStatus;

    public int unread;

    public int totalCount = -1;

    public int count = -1;
}
