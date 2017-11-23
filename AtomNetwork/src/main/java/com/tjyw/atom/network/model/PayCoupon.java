package com.tjyw.atom.network.model;

import com.tjyw.atom.network.result.RetroResultItem;

/**
 * Created by stephen on 17-11-16.
 */
public class PayCoupon implements RetroResultItem {

    private static final long serialVersionUID = -8106684737892381351L;

    public interface STATUS {

        int UNUSED = 0;

        int USED = 1;

        int EXPIRE = 2;
    }

    public interface TYPE {

        int NEW_USER = 1;
    }

    public int id;

    public String title;

    public String money;

    public String full_cut_money;

    public int status;

    public int type;

    public String startDate;

    public String endDate;

    public String subTotal; // 小计
}
