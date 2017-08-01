package com.tjyw.atom.network.result;

/**
 * Created by stephen on 6/22/16.
 */
public class RetroResult<T extends RetroResultItem> implements RetroResultItem {

    private static final long serialVersionUID = 4090255516512845001L;

    public int code;

    public String message;

    public T items;

    public RetroResult() {

    }

    public RetroResult(T items) {
        this.items = items;
    }

    public boolean illegalRequest() {
        return null == items;
    }
}
