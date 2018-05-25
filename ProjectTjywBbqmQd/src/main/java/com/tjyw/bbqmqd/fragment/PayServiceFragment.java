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
import com.tjyw.atom.network.presenter.PayPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.services.HttpPayServices;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.bbqmqd.ClientQmjmApplication;
import com.tjyw.bbqmqd.R;
import com.tjyw.bbqmqd.factory.IClientActivityLaunchFactory;

import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 17-8-17.
 */
@RequiresPresenter(PayPresenter.class)
public class PayServiceFragment extends BaseFragment<PayPresenter<PayServiceFragment>> implements
        OnApiPostErrorListener,
        OnApiPayPostListener.PostPayListVipListener {

    public interface OnPayServiceClickListener {

        void payOnServicePayClick(PayServiceFragment fragment, ListRequestParam listRequestParam, PayService payService);
    }

    @From(R.id.bodyServiceDiscount)
    protected ImageView bodyServiceDiscount;

    @From(R.id.bodyServiceName)
    protected TextView bodyServiceName;

    @From(R.id.bodyServiceLogo)
    protected ImageView bodyServiceLogo;

    @From(R.id.bodyServicePrice)
    protected TextView bodyServicePrice;

    @From(R.id.bodyServiceUnlock)
    protected TextView bodyServiceUnlock;

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
            bodyServiceUnlock.setOnClickListener(this);
        }

        String[] payPriceWords = ClientQmjmApplication.pGetResources().getStringArray(R.array.atom_pub_resStringPayPriceWord);
        if (! ArrayUtil.isEmpty(payPriceWords)) {
            SpannableStringBuilder builder = new SpannableStringBuilder(payPriceWords[1]);
            int length = builder.length();
            builder.append(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s_Yuan_Simple, payService.money));
            builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ClientQmjmApplication.getContext(), R.color.atom_pubResColorYellow)), length, builder.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new RelativeSizeSpan(2f), length, builder.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(payPriceWords[0]);
            length = builder.length();
            builder.append(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s_Yuan_Simple, payService.oldMoney));
            builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ClientQmjmApplication.getContext(), R.color.atom_pubResColorYellow)), length, builder.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(payPriceWords[2]);
            length = builder.length();
            builder.append(payPriceWords[3]);
            builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ClientQmjmApplication.getContext(), R.color.atom_pubResColorYellow)), length, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(payPriceWords[4]);
            bodyServicePrice.setText(builder);
        }

        switch (payService.id) {
            case PayService.VIP_ID.RECOMMEND:
                bodyServiceName.setText(R.string.atom_pub_resStringNameServiceTitleRecommend);
                bodyServiceLogo.setImageResource(R.drawable.atom_png_pay_service_logo_recommend);
                break ;
            case PayService.VIP_ID.DJM:
            case PayService.VIP_ID.XJM:
                bodyServiceName.setText(R.string.atom_pub_resStringNameServiceTitleSelf);
                bodyServiceLogo.setImageResource(R.drawable.atom_png_pay_service_logo_jm);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bodyServiceUnlock:
                if (null != listener) {
                    listener.payOnServicePayClick(this, listRequestParam, payService);
                }
            default:
                pHideFragment(this);
        }
    }

    @Override
    public void postOnPayListVipSuccess(int type, PayService payService) {
        maskerHideProgressView();
        pHideFragment(this);
        IClientActivityLaunchFactory.launchPayOrderActivity(this, listRequestParam, payService);
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {
        throwable.printStackTrace();
    }
}
