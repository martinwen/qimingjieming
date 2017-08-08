package com.tjyw.atom.pub.inject;

/**
 * @author zitian.zhang
 * @since 2013-3-1 下午3:27:18
 * 
 */
public class InjectException extends RuntimeException {

    private static final long serialVersionUID = 6764570896457507348L;

    public InjectException() {
        super();
    }

    public InjectException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public InjectException(String detailMessage) {
        super(detailMessage);
    }

    public InjectException(Throwable throwable) {
        super(throwable);
    }

}
