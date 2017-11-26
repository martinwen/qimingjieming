package com.tjyw.qmjmqd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import atom.pub.inject.From;
import com.tjyw.qmjmqd.ClientQmjmApplication;
import com.tjyw.qmjmqd.R;

/**
 * Created by stephen on 17-8-17.
 */
public class PayServiceFragment extends BaseFragment {

    public interface OnPayServiceClickListener {

        void payOnServicePayClick(PayServiceFragment fragment, ListRequestParam listRequestParam, PayService payService);
    }

    @From(R.id.bodySurname)
    protected TextView bodySurname;

    @From(R.id.bodyDate)
    protected TextView bodyDate;

    @From(R.id.bodyServiceName)
    protected TextView bodyServiceName;

    @From(R.id.bodyServicePrice)
    protected TextView bodyServicePrice;

    @From(R.id.atom_pub_resIdsOK)
    protected TextView atom_pub_resIdsOK;

    protected ListRequestParam listRequestParam;

    protected PayService payService;

    protected OnPayServiceClickListener listener;

    public void setListRequestParam(ListRequestParam listRequestParam) {
        this.listRequestParam = listRequestParam;
    }

    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    public void setOnPayServiceClickListener(OnPayServiceClickListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_pay_service, container, true);
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

        atom_pub_resIdsOK.setOnClickListener(this);
        bodySurname.setText(payService.surname);
        bodyDate.setText(payService.day);
        bodyServiceName.setText(payService.service);
        bodyServicePrice.setText(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s, payService.money));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.atom_pub_resIdsOK:
                pHideFragment(this);
                if (null != listener) {
                    listener.payOnServicePayClick(this, listRequestParam, payService);
                }
                break ;
            default:
                pHideFragment(this);
        }
    }
}
