package com.tjyw.qmjm.adapter;

import android.content.Context;

import antistatic.spinnerwheel.adapters.AbstractWheelTextAdapter;

/**
 * Created by stephen on 17-8-9.
 */
public class GregoianMonthAdapter extends AbstractWheelTextAdapter {

    protected GregoianMonthAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemsCount() {
        return 0;
    }

    @Override
    protected CharSequence getItemText(int index) {
        return null;
    }
}
