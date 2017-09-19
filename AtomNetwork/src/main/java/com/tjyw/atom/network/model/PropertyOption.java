package com.tjyw.atom.network.model;

import android.text.TextUtils;

import com.tjyw.atom.network.Network;
import com.tjyw.atom.network.result.RetroResultItem;

/**
 * Created by stephen on 16-8-25.
 */
public class PropertyOption implements RetroResultItem {

    private static final long serialVersionUID = -6419519349579924095L;

    public static PropertyOption newInstance(String value) {
        PropertyOption option = new PropertyOption();
        option.id = 999;
        option.value = value;
        return option;
    }

    public static String value(PropertyOption option) {
        return null == option ? null : option.value;
    }

    public static String value(PropertyOption option, int defaultStrRes) {
        return null == option || TextUtils.isEmpty(option.value) ? Network.pGetString(defaultStrRes) : option.value;
    }

    public static boolean available(PropertyOption option) {
        return null != option && option.id > 0;
    }

    public int id;

    public String value;

    public String answerDesc;
}
