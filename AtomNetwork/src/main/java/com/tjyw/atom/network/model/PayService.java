package com.tjyw.atom.network.model;

import com.tjyw.atom.network.result.RetroResultItem;

/**
 * Created by stephen on 17-8-18.
 */
public class PayService implements RetroResultItem {

    private static final long serialVersionUID = -2282241746086150868L;

    public interface VIP_ID {

        int RECOMMEND = 1;

        int LUCKY = 2;

        int DJM = 3;

        int XJM = 4;
    }

    public int id;

    public String service;

    public String money;

    public String surname;

    public String day;

    public String detail;

    public String oldMoney;

    public String eachDay;

    public String validDate;

    public boolean payRepeat; // 是否复购

    public int discount; // 打折

    public String discountMoney; // 打折价
}