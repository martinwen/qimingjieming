package com.xhinliang.lunarcalendar;

import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;

import com.tjyw.atom.network.utils.ArrayUtil;
import com.xhinliang.lunarcalendar.holder.AbsGregorianMonthHolder;

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

    /**
     * SparseArray的Key是年份
     * Pair是当前年份下的月份集合
     * Pair.first是公历月集合(包含当月下的日期数据)
     * Pair.second是农历月集合(包含当月下的日期数据)
     *
     * Pair.first和Pair.second的长度很可能不同，农历可能会多于公历的月份
     */
    protected final SparseArray<Pair<List<AbsGregorianMonthHolder>, List<AbsGregorianMonthHolder>>> source = new SparseArray<Pair<List<AbsGregorianMonthHolder>, List<AbsGregorianMonthHolder>>>();

    public Pair<List<AbsGregorianMonthHolder>, List<AbsGregorianMonthHolder>> get(int solarYear) {
        return source.get(solarYear);
    }

    public void set(int solarYear) {
        Pair<List<AbsGregorianMonthHolder>, List<AbsGregorianMonthHolder>> gregorianMonthHolderPair =
                new Pair<List<AbsGregorianMonthHolder>, List<AbsGregorianMonthHolder>>(
                        new ArrayList<AbsGregorianMonthHolder>(),
                        new ArrayList<AbsGregorianMonthHolder>()
                );

        source.put(solarYear, gregorianMonthHolderPair);

        String lunarMonthNameCached = null; // 农历月的名称(公历月遍历时同月份中可能会出现两个农历月，缓存名称发现不同时，创建新的农历月集合)
        AbsGregorianMonthHolder gregorianLunarMonthHolder = null;
        for (int month = 1; month <= 12; month ++) { // 遍历公历月
            AbsGregorianMonthHolder gregorianSolarMonthHolder = AbsGregorianMonthHolder.newSolarInstance();
            gregorianMonthHolderPair.first.add(gregorianSolarMonthHolder);

            LunarCalendar[][] monthCalendar = LunarCalendar.obtainCalendar(solarYear, month);
            if (! ArrayUtil.isEmpty(monthCalendar)) {
                for (LunarCalendar[] week : monthCalendar) {
                    if (! ArrayUtil.isEmpty(week)) {
                        for (LunarCalendar day : week) {
                            if (null == day) {
                                continue;
                            } else { // 设置公历月的日期数据
                                gregorianSolarMonthHolder.absSetMonth(day);
                                gregorianSolarMonthHolder.addLunarCalendar(day);
                            }

                            String lunarMonth = day.getLunarMonth();
                            if (null == lunarMonthNameCached || ! TextUtils.equals(lunarMonthNameCached, lunarMonth)) { // 同公历月中出现多个农历月时，生成新的农历月对象
                                lunarMonthNameCached = lunarMonth;
                                gregorianLunarMonthHolder = AbsGregorianMonthHolder.newLunarInstance();
                                gregorianLunarMonthHolder.absSetMonth(day);
                                gregorianMonthHolderPair.second.add(gregorianLunarMonthHolder);
                            } else { // 设置农历月的日期数据
                                gregorianLunarMonthHolder.addLunarCalendar(day);
                            }
                        }
                    }
                }
            }
        }
    }
}
