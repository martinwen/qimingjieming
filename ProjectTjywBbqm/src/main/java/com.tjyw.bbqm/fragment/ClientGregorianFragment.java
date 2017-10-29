package com.tjyw.bbqm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.atom.network.utils.DateTimeUtils;
import atom.pub.inject.From;
import atom.pub.inject.Injector;
import com.tjyw.bbqm.ClientQmjmApplication;
import com.tjyw.bbqm.R;
import com.xhinliang.lunarcalendar.LunarCalendar;
import com.xhinliang.lunarcalendar.LunarSolarSource;
import com.xhinliang.lunarcalendar.adapter.GregorianCalendarAdapter;
import com.xhinliang.lunarcalendar.holder.GregorianMonth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelChangedListener;
import antistatic.spinnerwheel.WheelVerticalView;
import antistatic.spinnerwheel.adapters.ListWheelAdapter;

/**
 * Created by stephen on 08/08/2017.
 */
public class ClientGregorianFragment extends BaseFragment implements OnWheelChangedListener {

    public interface OnGregorianSelectedListener {

        void gregorianOnSelected(LunarCalendar calendar, boolean isGregorianSolar, String hour, int hh);
    }

    @From(R.id.gregorianSwitchContainer)
    protected WheelVerticalView gregorianSwitchContainer;

    @From(R.id.gregorianYearContainer)
    protected WheelVerticalView gregorianYearContainer;

    @From(R.id.gregorianMonthContainer)
    protected WheelVerticalView gregorianMonthContainer;

    @From(R.id.gregorianDayContainer)
    protected WheelVerticalView gregorianDayContainer;

    @From(R.id.gregorianHourContainer)
    protected WheelVerticalView gregorianHourContainer;

    @From(R.id.gregorianCancel)
    protected TextView gregorianCancel;

    @From(R.id.gregorianOK)
    protected TextView gregorianOK;

    protected List<String> gregorianHourList;

    protected Pair<List<GregorianMonth>, List<GregorianMonth>> gregorianMonthHolderPair;

    protected OnGregorianSelectedListener onGregorianSelectedListener;

    public void setOnGregorianSelectedListener(OnGregorianSelectedListener onGregorianSelectedListener) {
        this.onGregorianSelectedListener = onGregorianSelectedListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.atom_pub_gregorian, container, true);
        Injector.inject(this, convertView);

        gregorianCancel.setOnClickListener(this);
        gregorianOK.setOnClickListener(this);

        gregorianSwitchContainer.addChangingListener(this);
        gregorianYearContainer.addChangingListener(this);
        gregorianMonthContainer.addChangingListener(this);

        return convertView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] resGregorian = ClientQmjmApplication.pGetResources().getStringArray(R.array.atom_pub_resGregorian);
        if (! ArrayUtil.isEmpty(resGregorian)) {
            gregorianSwitchContainer.setViewAdapter(
                    new ListWheelAdapter<String>(ClientQmjmApplication.getContext(), Arrays.asList(resGregorian))
            );
        }

        List<Integer> years = new ArrayList<Integer>();
        for (int i = 0; i <= 148; i ++) {
            years.add(i + 1901);
        }

        Calendar calendar = DateTimeUtils.getCurrentDate();
        int solarYear = calendar.get(Calendar.YEAR);

        gregorianYearContainer.setViewAdapter(new ListWheelAdapter<Integer>(ClientQmjmApplication.getContext(), years));
        gregorianYearContainer.setCurrentItem(Math.abs(solarYear - 1901));
        setGregorianDataWithSolarYear(solarYear);

        gregorianMonthContainer.setCurrentItem(calendar.get(Calendar.MONTH));
        gregorianDayContainer.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);

        String[] resGregorianHour = ClientQmjmApplication.pGetResources().getStringArray(R.array.atom_pub_resGregorianHour);
        if (! ArrayUtil.isEmpty(resGregorianHour)) {
            gregorianHourContainer.setViewAdapter(
                    new ListWheelAdapter<String>(ClientQmjmApplication.getContext(), gregorianHourList = Arrays.asList(resGregorianHour))
            );
            gregorianHourContainer.setCurrentItem(1, false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gregorianCancel:
                pHideFragment(this);
                break ;
            case R.id.gregorianOK:
                GregorianCalendarAdapter adapter = (GregorianCalendarAdapter) gregorianDayContainer.getViewAdapter();
                if (null != adapter) {
                    LunarCalendar calendar = adapter.get(gregorianDayContainer.getCurrentItem());
                    if (null != calendar && null != onGregorianSelectedListener) {
                        if (! ArrayUtil.isEmpty(gregorianHourList)) {
                            int position = gregorianHourContainer.getCurrentItem();
                            String hour = gregorianHourList.get(position == 0 ? 2 : position); // 不清楚的时候默认按子时0点计算
                            if (! TextUtils.isEmpty(hour)) {
                                int hh = 0;
                                Pattern pattern = Pattern.compile("[0-9]{1,2}");
                                Matcher matcher = pattern.matcher(hour);
                                if (matcher.find()) {
                                    hh = Integer.parseInt(matcher.group());
                                }

                                onGregorianSelectedListener.gregorianOnSelected(calendar, isGregorianSolar(), hour, hh);
                            }
                        }
                    }
                }

                pHideFragment(this);
        }
    }

    @Override
    public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
        if (oldValue != newValue) {
            switch (wheel.getId()) {
                case R.id.gregorianSwitchContainer:
                case R.id.gregorianYearContainer:
                    ListWheelAdapter adapter = (ListWheelAdapter) gregorianYearContainer.getViewAdapter();
                    setGregorianDataWithSolarYear((Integer) adapter.get(gregorianYearContainer.getCurrentItem()));
                    break;
                case R.id.gregorianMonthContainer:
                    if (null != gregorianMonthHolderPair) {
                        List<GregorianMonth> gregorianMonthList = isGregorianSolar() ? gregorianMonthHolderPair.first : gregorianMonthHolderPair.second;
                        if (null != gregorianMonthList) {
                            setGregorianDayWithMonth(gregorianMonthList.get(newValue));
                        }
                    }
            }
        }
    }

    /**
     * 公历年滚动后回调，需要重新设置选中年份的月集合(当显示农历时，月份可能会变化，有可能多于12个月--出现闰月)
     *
     * @param solarYear                 选中公历年
     */
    protected void setGregorianDataWithSolarYear(int solarYear) {
        gregorianMonthHolderPair = LunarSolarSource.getInstance().get(solarYear);
        if (null == gregorianMonthHolderPair) {
            gregorianMonthHolderPair = LunarSolarSource.getInstance().set(solarYear);
        }

        if (null != gregorianMonthHolderPair) {
            setGregorianMonthWithList(isGregorianSolar() ? this.gregorianMonthHolderPair.first : this.gregorianMonthHolderPair.second);
        }
    }

    /**
     * 重置月份滚轮的数据
     *
     * @param gregorianMonthList        选中公历年的月份集合
     */
    protected void setGregorianMonthWithList(List<GregorianMonth> gregorianMonthList) {
        List<String> solarMonth = new ArrayList<String>();
        int size = null == gregorianMonthList ? 0 : gregorianMonthList.size();
        if (size > 0) {
            for (int i = 0; i < size; i ++) { // 遍历月份，获取月份显示方案(1月、2月或正月、二月)
                GregorianMonth gregorianMonth = gregorianMonthList.get(i);
                if (null != gregorianMonth) {
                    solarMonth.add(gregorianMonth.absGetMonth(ClientQmjmApplication.pGetResources()));
                }
            }

            gregorianMonthContainer.setViewAdapter(
                    new ListWheelAdapter<String>(ClientQmjmApplication.getContext(), solarMonth)
            );

            int selectedMonth = Math.min(gregorianMonthContainer.getCurrentItem(), gregorianMonthList.size() - 1);
            setGregorianDayWithMonth(gregorianMonthList.get(selectedMonth));
        }
    }

    /**
     * 重置日期滚轮的数据
     *
     * @param gregorianMonth        选中月
     */
    protected void setGregorianDayWithMonth(GregorianMonth gregorianMonth) {
        if (null != gregorianMonth) {
            GregorianCalendarAdapter adapter = new GregorianCalendarAdapter(ClientQmjmApplication.getContext(), gregorianMonth.getCalendarList(), isGregorianSolar());
            gregorianDayContainer.setViewAdapter(adapter);
            gregorianDayContainer.setCurrentItem(Math.min(gregorianDayContainer.getCurrentItem(), adapter.getItemsCount() - 1));
        }
    }

    /**
     * 返回是否是公历年显示方式
     *
     * @return      true:公历 | false:农历 | default:公历
     */
    protected boolean isGregorianSolar() {
        switch (gregorianSwitchContainer.getCurrentItem()) {
            case 0:
                return true;
            case 1:
                return false;
            default:
                return true;
        }
    }
}
