package com.tjyw.atom.network.model;

import com.tjyw.atom.network.result.RetroResultItem;

/**
 * Created by stephen on 17-8-18.
 */
public class Pay implements RetroResultItem {

    private static final long serialVersionUID = -2282241746086150868L;

    public int id;

    public String desc;

    public int money;

    public String service;
}
