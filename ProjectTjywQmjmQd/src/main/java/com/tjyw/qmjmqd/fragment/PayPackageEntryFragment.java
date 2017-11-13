package com.tjyw.qmjmqd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.qmjmqd.ClientQmjmApplication;
import com.tjyw.qmjmqd.R;

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

    @From(R.id.bodyServiceOldPrice)
    protected TextView bodyServiceOldPrice;

    @From(R.id.bodyServicePriceEach)
    protected TextView bodyServicePriceEach;

    @From(R.id.bodyValidDate)
    protected TextView bodyValidDate;

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
        return inflater.inflate(R.layout.atom_pay_package_entry, container, true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            return ;
        } else if (null == listRequestParam || null == payService) {
            return ;
        } else if (null != getView()) {
            getView().setOnClickListener(this);
        }

        bodyServiceName.setText(payService.service);
        bodyServiceDesc.setText(payService.detail);
        bodyServicePrice.setText(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s_Yuan, payService.money));
        bodyServiceOldPrice.setText(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s_Yuan, payService.oldMoney));
        bodyServicePriceEach.setText(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringPayPackageMoneyEach, payService.eachDay));
        bodyValidDate.setText(payService.validDate);

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
