package com.tjyw.atom.network;

import com.tjyw.atom.network.result.RetroResult;

/**
 * Created by stephen on 10/04/2017.
 */
public class IllegalRequestException extends Exception {

    private static final long serialVersionUID = -374775794834287384L;

    public int code;

    public String message;

    public IllegalRequestException(RetroResult result) {
        if (null != result) {
            this.code = result.code;
            this.message = result.message;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
