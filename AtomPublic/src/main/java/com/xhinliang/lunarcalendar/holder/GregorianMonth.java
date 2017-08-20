package com.xhinliang.lunarcalendar.holder;

import android.content.res.Resources;

import com.tjyw.atom.network.utils.ArrayUtil;
import com.xhinliang.lunarcalendar.LunarCalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 17-8-9.
 */
public abstract class GregorianMonth {

    public static GregorianMonth newInstance(boolean lunar) {
        if (lunar) {
            return new GregorianLunarMonth();
        } else {
            return new GregorianSolarMonth();
        }
    }

    protected List<LunarCalendar> calendarList;

    public GregorianMonth() {
        calendarList = new ArrayList<LunarCalendar>();
    }

    public boolean addLunarCalendar(LunarCalendar lunarCalendar) {
        return null != lunarCalendar && null != calendarList && calendarList.add(lunarCalendar);
    }

    public LunarCalendar getLastLunarCalendar() {
        return ArrayUtil.isEmpty(calendarList) ? null : calendarList.get(calendarList.size() - 1);
    }

    public List<String> getDateOfMonth(Resources resources) {
        int size = null == calendarList ? 0 : calendarList.size();
        if (size == 0) {
            return null;
        }

        List<String> dateOfMonth = new ArrayList<String>();
        for (int i = 0; i < size; i ++) {
            LunarCalendar calendar = calendarList.get(i);
            if (null != calendar) {
                dateOfMonth.add(this.absGetDateOfMonth(resources, calendar));
            }
        }

        return dateOfMonth;
    }

    public List<LunarCalendar> getCalendarList() {
        return calendarList;
    }

    public abstract void absSetMonth(LunarCalendar lunarCalendar);

    public abstract String absGetMonth(Resources resources);

    protected abstract String absGetDateOfMonth(Resources resources, LunarCalendar calendar);

    static class GregorianLunarMonth extends GregorianMonth {

        public String lunarMonth;

        @Override
        public void absSetMonth(LunarCalendar lunarCalendar) {
            if (null != lunarCalendar) {
                if (null != lunarCalendar.getLunar() && lunarCalendar.getLunar().isLeap) {
                    this.lunarMonth = "闰" + lunarCalendar.getLunarMonth();
                } else {
                    this.lunarMonth = lunarCalendar.getLunarMonth();
                }
            }
        }

        @Override
        public String absGetMonth(Resources resources) {
            return lunarMonth;
        }

        @Override
        protected String absGetDateOfMonth(Resources resources, LunarCalendar calendar) {
            return calendar.getLunarDay();
        }
    }

    static class GregorianSolarMonth extends GregorianMonth {

        public int solarMonth;

        @Override
        public void absSetMonth(LunarCalendar lunarCalendar) {
            if (null != lunarCalendar) {
                this.solarMonth = lunarCalendar.getMonth();
            }
        }

        @Override
        public String absGetMonth(Resources resources) {
            return String.valueOf(solarMonth);
        }

        @Override
        protected String absGetDateOfMonth(Resources resources, LunarCalendar calendar) {
            return String.valueOf(calendar.getDay());
        }
    }
}
