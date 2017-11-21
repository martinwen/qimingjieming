package com.tjyw.atom.network.result;

import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.model.UserInfo;

/**
 * Created by stephen on 21/11/2017.
 */
public class RUserRegister implements RetroResultItem {

    private static final long serialVersionUID = 8110853558653494911L;

    public UserInfo user;

    public ClientInit init;
}
