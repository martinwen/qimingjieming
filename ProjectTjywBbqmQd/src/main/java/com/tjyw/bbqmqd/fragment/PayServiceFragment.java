package com.tjyw.bbqmqd.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.bbqmqd.R;

import atom.pub.inject.From;

/**
 * Created by stephen on 17-8-17.
 */
public class PayServiceFragment extends BaseFragment {

    public interface OnPayServiceClickListener {

        void payOnServicePayClick(PayServiceFragment fragment, ListRequestParam listRequestParam, PayService payService);
    }

    @From(R.id.payServiceClose)
    protected ImageView payServiceClose;

    @From(R.id.payServiceName)
    protected ImageView payServiceName;

    @From(R.id.payServicePrice)
    protected TextView payServicePrice;

    @From(R.id.payServiceOldPrice)
    protected TextView payServiceOldPrice;

    @From(R.id.payServiceBuy)
    protected ImageView payServiceBuy;

    @From(R.id.payServiceSuit)
    protected ImageView payServiceSuit;

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

        payServiceClose.setOnClickListener(this);
        payServiceBuy.setOnClickListener(this);
        payServiceSuit.setOnClickListener(this);

        payServicePrice.setText(String.valueOf(payService.money));

        payServiceOldPrice.setText("88");
        payServiceOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        switch (payService.id) {
            case PayService.VIP_ID.RECOMMEND:
                payServiceName.setImageResource(R.drawable.atom_pub_png_pay_window_recommend);
                break ;
            case PayService.VIP_ID.LUCKY:
                payServiceName.setImageResource(R.drawable.atom_pub_png_pay_window_lucky);
                break ;
            case PayService.VIP_ID.DJM:
                payServiceName.setImageResource(R.drawable.atom_pub_png_pay_window_djm);
                break ;
            case PayService.VIP_ID.XJM:
                payServiceName.setImageResource(R.drawable.atom_pub_png_pay_window_xjm);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payServiceBuy:
                pHideFragment(this);
                if (null != listener) {
                    listener.payOnServicePayClick(this, listRequestParam, payService);
                }
                break ;
            case R.id.payServiceSuit:

                break ;
            case R.id.payServiceClose:
            default:
                pHideFragment(this);
        }
    }
}
