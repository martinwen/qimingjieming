package com.tjyw.atom.network.result;

import java.util.List;

/**
 * Created by stephen on 7/12/16.
 */
public class RetroListResult<T> implements RetroResultItem {

    private static final long serialVersionUID = -9012318746356368754L;

    public List<T> list;

    public int totalCount = -1;

    public int count = -1;

    public T get(int position) {
        return null == list ? null : list.get(position);
    }

    public int size() {
        return null == list ? 0 : list.size();
    }
}
