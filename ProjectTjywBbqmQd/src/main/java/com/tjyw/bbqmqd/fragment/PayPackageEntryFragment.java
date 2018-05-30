package com.tjyw.bbqmqd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.bbqmqd.ClientQmjmApplication;
import com.tjyw.bbqmqd.R;

import atom.pub.inject.From;

/**
 * Created by stephen on 17-11-9.
 */
public class PayPackageEntryFragment extends BaseFragment {

    public interface OnPayPackageEntryClickListener {

        void payPackageOnServicePayClick(PayPackageEntryFragment fragment, ListRequestParam listRequestParam, PayService payService);
    }

    @From(R.id.bodyServiceDiscount)
    protected ImageView bodyServiceDiscount;

    @From(R.id.bodyServiceName)
    protected TextView bodyServiceName;

    @From(R.id.bodyServiceDesc)
    protected TextView bodyServiceDesc;

    @From(R.id.bodyServicePrice)
    protected TextView bodyServicePrice;

    @From(R.id.bodyServiceUnlock)
    protected TextView bodyServiceUnlock;

    protected ListRequestParam listRequestParam;

    protected PayService payService;

    protected OnPayPackageEntryClickListener onPayPackageEntryClickListener;

    public void setListRequestParam(ListRequestParam listRequestParam) {
        this.listRequestParam = listRequestParam;
    }

    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    public void setOnPayPackageEntryClickListener(OnPayPackageEntryClickListener onPayPackageEntryClickListener) {
        this.onPayPackageEntryClickListener = onPayPackageEntryClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_pay_suit, container, true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            return ;
        } else if (null == listRequestParam || null == payService) {
            return ;
        } else if (null != getView()) {
            getView().setOnClickListener(this);
            bodyServiceUnlock.setOnClickListener(this);
        }

        bodyServiceDiscount.setVisibility(payService.discount < 10 ? View.VISIBLE : View.GONE);

        bodyServiceName.setText(payService.service);
//        bodyServiceDesc.setText(payService.detail);

        String[] payPriceWords = ClientQmjmApplication.pGetResources().getStringArray(R.array.atom_pub_resStringPayPriceWord);
        if (! ArrayUtil.isEmpty(payPriceWords)) {
            SpannableStringBuilder builder = new SpannableStringBuilder(payPriceWords[1]);
            int length = builder.length();
            builder.append(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s_Yuan_Simple, payService.money));
            builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ClientQmjmApplication.getContext(), R.color.atom_pub_resTextColorRed)), length, builder.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new RelativeSizeSpan(1.4f), length, builder.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(payPriceWords[0]);
            length = builder.length();
            builder.append(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s_Yuan_Simple, payService.oldMoney));
            builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ClientQmjmApplication.getContext(), R.color.atom_pub_resTextColorRed)), length, builder.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(payPriceWords[2]);
            length = builder.length();
            builder.append(payPriceWords[5]);
            builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ClientQmjmApplication.getContext(), R.color.atom_pub_resTextColorRed)), length, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(payPriceWords[4]);
            bodyServicePrice.setText(builder);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bodyServiceUnlock:
                pHideFragment(this);
                if (null != onPayPackageEntryClickListener) {
                    onPayPackageEntryClickListener.payPackageOnServicePayClick(this, listRequestParam, payService);
                }
                break ;
            default:
                pHideFragment(this);
        }
    }
}
