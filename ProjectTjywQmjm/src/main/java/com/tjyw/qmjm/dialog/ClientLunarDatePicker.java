package com.tjyw.qmjm.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.crazypumpkin.versatilerecyclerview.library.WheelRecyclerView;
import com.tjyw.atom.network.utils.DateTimeUtils;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.inject.Injector;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.xhinliang.lunarcalendar.LunarSolarSource;
import com.xhinliang.lunarcalendar.holder.GregorianMonth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import timber.log.Timber;

/**
 * Created by stephen on 08/08/2017.
 */
public class ClientLunarDatePicker extends DialogFragment implements View.OnClickListener, WheelRecyclerView.OnSelectListener {

    public static ClientLunarDatePicker newInstance(FragmentManager manager) {
        ClientLunarDatePicker lunarDatePicker = new ClientLunarDatePicker();
        lunarDatePicker.show(manager, ClientLunarDatePicker.class.getName());
        return lunarDatePicker;
    }

    @From(R.id.gregorianSwitchContainer)
    protected WheelRecyclerView gregorianSwitchContainer;

    @From(R.id.gregorianYearContainer)
    protected WheelRecyclerView gregorianYearContainer;

    @From(R.id.gregorianMonthContainer)
    protected WheelRecyclerView gregorianMonthContainer;

    @From(R.id.gregorianDayContainer)
    protected WheelRecyclerView gregorianDayContainer;

    @From(R.id.gregorianHourContainer)
    protected WheelRecyclerView gregorianHourContainer;

    @From(R.id.gregorianCancel)
    protected TextView gregorianCancel;

    @From(R.id.gregorianOK)
    protected TextView gregorianOK;

    protected Pair<List<GregorianMonth>, List<GregorianMonth>> gregorianSelected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.atom_master_lunar_date_picker, container, true);
        Injector.inject(this, convertView);

        gregorianCancel.setOnClickListener(this);
        gregorianOK.setOnClickListener(this);

        List<String> gregorian = new ArrayList<String>();
        gregorian.add(ClientQmjmApplication.pGetString(R.string.atom_pub_resGregorianLunar));
        gregorian.add(ClientQmjmApplication.pGetString(R.string.atom_pub_resGregorianSolar));
        gregorianSwitchContainer.setData(gregorian);

        gregorianSwitchContainer.setOnSelectListener(this);
        gregorianYearContainer.setOnSelectListener(this);
        gregorianMonthContainer.setOnSelectListener(this);

        Calendar calendar = DateTimeUtils.getCurrentDate();
        int solarYear = calendar.get(Calendar.YEAR);
        int solarMonth = calendar.get(Calendar.MONTH);
        int solarDay = calendar.get(Calendar.DAY_OF_MONTH);

        List<String> years = new ArrayList<String>();
        for (int i = 0; i <= 148; i ++) {
            years.add(String.valueOf(i + 1901));
        }

        gregorianYearContainer.setData(years);
        gregorianYearContainer.setSelect(Math.abs(solarYear - 1901));

        setGregorianDataWithSolarYear(solarYear);

        gregorianMonthContainer.setSelect(solarMonth);
        gregorianDayContainer.setSelect(solarDay);

        return convertView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gregorianCancel:
                dismissAllowingStateLoss();
                break ;
            case R.id.gregorianOK:
        }
    }

    @Override
    public void onSelect(WheelRecyclerView recyclerView, int position, String data) {
        Timber.tag("Gx").e("position::%d, data::%s, recyclerView::%s", position, data, recyclerView);
        switch (recyclerView.getId()) {
            case R.id.gregorianSwitchContainer:

                break ;
            case R.id.gregorianYearContainer:
                setGregorianDataWithSolarYear(Integer.parseInt(data));
                break ;
            case R.id.gregorianMonthContainer:
                if (null != gregorianSelected) {
                    List<GregorianMonth> gregorianMonthList = isGregorianSolar() ? gregorianSelected.first : gregorianSelected.second;
                    if (null != gregorianMonthList) {
                        setGregorianDayWithMonth(gregorianMonthList.get(position));
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
        Pair<List<GregorianMonth>, List<GregorianMonth>> gregorianMonthHolderPair = LunarSolarSource.getInstance().get(solarYear);
        if (null == gregorianMonthHolderPair) {
            gregorianMonthHolderPair = LunarSolarSource.getInstance().set(solarYear);
        }

        if (null != gregorianMonthHolderPair) {
            gregorianSelected = gregorianMonthHolderPair;
            setGregorianMonthWithList(isGregorianSolar() ? gregorianSelected.first : gregorianSelected.second);
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
        for (int i = 0; i < size; i ++) { // 遍历月份，获取月份显示方案(1月2月或正月二月)
            GregorianMonth gregorianMonth = gregorianMonthList.get(i);
            if (null != gregorianMonth) {
                solarMonth.add(gregorianMonth.absGetMonth(ClientQmjmApplication.pGetResources()));
            }
        }

        gregorianMonthContainer.setData(solarMonth);
        setGregorianDayWithMonth(gregorianMonthList.get(0));
    }

    /**
     * 重置日期滚轮的数据
     *
     * @param gregorianMonth        选中月
     */
    protected void setGregorianDayWithMonth(GregorianMonth gregorianMonth) {
        if (null != gregorianMonth) {
            List<String> dateOfMonth = gregorianMonth.getDateOfMonth(ClientQmjmApplication.pGetResources());
            gregorianDayContainer.setData(dateOfMonth);
        }
    }

    /**
     * 返回是否是公历年显示方式
     *
     * @return      true:公历 | false:农历 | default:公历
     */
    protected boolean isGregorianSolar() {
        switch (gregorianSwitchContainer.getSelected()) {
            case 0:
                return true;
            case 1:
                return false;
            default:
                return true;
        }
    }

}
