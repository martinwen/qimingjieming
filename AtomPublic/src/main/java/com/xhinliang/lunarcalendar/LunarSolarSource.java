package com.xhinliang.lunarcalendar;

import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;

import com.tjyw.atom.network.utils.ArrayUtil;
import com.xhinliang.lunarcalendar.holder.GregorianMonth;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

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
    protected final SparseArray<Pair<List<GregorianMonth>, List<GregorianMonth>>> source = new SparseArray<Pair<List<GregorianMonth>, List<GregorianMonth>>>();

    public Pair<List<GregorianMonth>, List<GregorianMonth>> get(int solarYear) {
        return source.get(solarYear);
    }

    public Pair<List<GregorianMonth>, List<GregorianMonth>> set(int solarYear) {
        Pair<List<GregorianMonth>, List<GregorianMonth>> gregorianMonthPair =
                new Pair<List<GregorianMonth>, List<GregorianMonth>>(
                        new ArrayList<GregorianMonth>(),
                        new ArrayList<GregorianMonth>()
                );

        source.put(solarYear, gregorianMonthPair);

        String lunarMonthNameCached = null; // 农历月的名称(公历月遍历时同月份中可能会出现两个农历月，缓存名称发现不同时，创建新的农历月集合)
        GregorianMonth gregorianLunarMonth = null;
        for (int month = 1; month <= 12; month ++) { // 遍历公历月
            GregorianMonth gregorianMonth = GregorianMonth.newInstance(false);
            gregorianMonthPair.first.add(gregorianMonth);

            LunarCalendar[][] monthCalendar = LunarCalendar.obtainCalendar(solarYear, month);
            if (! ArrayUtil.isEmpty(monthCalendar)) {
                for (LunarCalendar[] week : monthCalendar) {
                    if (! ArrayUtil.isEmpty(week)) {
                        for (LunarCalendar day : week) {
                            if (null == day) {
                                continue;
                            } else { // 设置公历月的日期数据
                                gregorianMonth.absSetMonth(day);
                                gregorianMonth.addLunarCalendar(day);
                            }

                            String lunarMonth = day.getLunarMonth();
                            if (null == lunarMonthNameCached || ! TextUtils.equals(lunarMonthNameCached, lunarMonth)) { // 同公历月中出现多个农历月时，生成新的农历月对象
                                lunarMonthNameCached = lunarMonth;
                                gregorianLunarMonth = GregorianMonth.newInstance(true);
                                gregorianLunarMonth.absSetMonth(day);
                                gregorianLunarMonth.addLunarCalendar(day);
                                gregorianMonthPair.second.add(gregorianLunarMonth);
                            } else { // 设置农历月的日期数据
                                LunarCalendar last = gregorianLunarMonth.getLastLunarCalendar();
                                if (null != last && null != last.getLunar() && last.getLunar().isLeap != day.getLunar().isLeap) {
                                    Timber.tag("gx").e("last::%s, current::%s", last, day);
                                    lunarMonthNameCached = lunarMonth;
                                    gregorianLunarMonth = GregorianMonth.newInstance(true);
                                    gregorianLunarMonth.absSetMonth(day);
                                    gregorianLunarMonth.addLunarCalendar(day);
                                    gregorianMonthPair.second.add(gregorianLunarMonth);
                                } else {
                                    gregorianLunarMonth.addLunarCalendar(day);
                                }
                            }
                        }
                    }
                }
            }
        }

        return gregorianMonthPair;
    }
}
