package com.xhinliang.lunarcalendar;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.SparseArray;

import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.atom.pub.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 08/08/2017.
 */
public class LunarSolarSource {

    private static LunarSolarSource instance = new LunarSolarSource();

    public static LunarSolarSource getInstance() {
        return instance;
    }

    protected final SparseArray<List<List<LunarCalendar>>> source = new SparseArray<List<List<LunarCalendar>>>();

    public List<List<LunarCalendar>> get(int solarYear) {
        return source.get(solarYear);
    }

    public void set(Context context, int solarYear) {
        List<List<LunarCalendar>> lunarSolarMonthList = new ArrayList<List<LunarCalendar>>();
        for (int month = 1; month < 12; month ++) {
            LunarCalendar[][] monthCalendar = LunarCalendar.obtainCalendar(solarYear, month);
            if (! ArrayUtil.isEmpty(monthCalendar)) {
                List<LunarCalendar> list = new ArrayList<LunarCalendar>();
                for (LunarCalendar[] week : monthCalendar) {
                    if (null != week) {
                        for (LunarCalendar day : week) {
                            if (null != day) {
                                list.add(day);
                            }
                        }
                    }
                }

                lunarSolarMonthList.add(list);
            }
        }

        if (! ArrayUtil.isEmpty(lunarSolarMonthList)) {
            source.put(solarYear, lunarSolarMonthList);
        }
    }

    public static class LunarSolar {

        public static LunarSolar Solar = new LunarSolar(1, R.string.atom_pub_resSolar);

        public static LunarSolar Lunar = new LunarSolar(1, R.string.atom_pub_resLunar);

        public int type;

        public int name;

        LunarSolar(int type, @StringRes int name) {
            this.type = type;
            this.name = name;
        }
    }
}
