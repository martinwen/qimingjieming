package com.tjyw.qmjm.interfaces;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by stephen on 17-8-1.
 */
public interface IClientIntentExtra {

    Intent pGetIntent();

    int pGetIntExtra(String key, int defaultValue);

    double pGetDoubleExtra(String key, double defaultValue);

    long pGetLongExtra(String key, long defaultValue);

    String pGetStringExtra(String key, String defaultValue);

    Boolean pGetBooleanExtra(String key, boolean defaultValue);

    Serializable pGetSerializableExtra(String key);
}
