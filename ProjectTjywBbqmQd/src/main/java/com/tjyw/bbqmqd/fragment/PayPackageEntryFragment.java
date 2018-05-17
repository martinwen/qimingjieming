package com.tjyw.bbqmqd.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
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

    @From(R.id.bodyServiceName)
    protected TextView bodyServiceName;

    @From(R.id.bodyServiceDesc)
    protected TextView bodyServiceDesc;

    @From(R.id.bodyServicePrice)
    protected TextView bodyServicePrice;

    @From(R.id.bodyServiceDiscount)
    protected TextView bodyServiceDiscount;

    @From(R.id.bodyServiceOldPrice)
    protected TextView bodyServiceOldPrice;

    @From(R.id.atom_pub_resIdsOK)
    protected TextView atom_pub_resIdsOK;

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
            if (null != bodyServiceOldPrice.getPaint()) {
                bodyServiceOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            }
        }

        bodyServiceName.setText(payService.service);
        bodyServiceDesc.setText(payService.detail);

        SpannableStringBuilder builder = new SpannableStringBuilder(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringPayPackageMoney));
        int length = builder.length();
        builder.append(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s_Yuan, payService.money));
        builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ClientQmjmApplication.getContext(), R.color.atom_pub_resTextColorRed)), length, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringPayPackageMoneyEach, payService.eachDay));
        bodyServicePrice.setText(builder);

        if (payService.discount < 10) {
            bodyServiceDiscount.setText(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringPayPackageDiscount, payService.discount));
        } else {
            bodyServiceDiscount.setVisibility(View.GONE);
        }

        bodyServiceOldPrice.setText(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s_Yuan, payService.oldMoney));
        if (null != bodyServiceOldPrice.getPaint()) {
            bodyServiceOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        }

        atom_pub_resIdsOK.setText(ClientInit.getPayButtonText(ClientQmjmApplication.getContext(), ClientQmjmApplication.pGetString(R.string.atom_pub_resStringPayPay)));
        atom_pub_resIdsOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.atom_pub_resIdsOK:
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
