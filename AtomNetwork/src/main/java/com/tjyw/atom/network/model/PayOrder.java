package com.tjyw.atom.network.model;

import com.tjyw.atom.network.result.RetroResultItem;

/**
 * Created by stephen on 17-8-18.
 */
public class PayOrder implements RetroResultItem {

    private static final long serialVersionUID = -2282241746086150868L;

    public String charset;

    public String mch_id;

    public String nonce_str;

    public String services;

    public String sign_type;

    public String status;

    public String token_id;

    public String version;

    public String orderNo;
}
