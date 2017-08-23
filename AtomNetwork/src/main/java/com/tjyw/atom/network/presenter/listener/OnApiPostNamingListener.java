package com.tjyw.atom.network.presenter.listener;

import com.tjyw.atom.network.model.NameDefinition;

import java.util.List;

/**
 * Created by stephen on 14/08/2017.
 */
public interface OnApiPostNamingListener {

    void postOnNamingSuccess(List<NameDefinition> result);
}
