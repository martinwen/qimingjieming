package com.tjyw.bbqmqd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.conf.ICode;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import com.tjyw.atom.network.services.HttpPayServices;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.bbqmqd.ClientQmjmApplication;
import com.tjyw.bbqmqd.R;
import com.tjyw.bbqmqd.activity.BaseActivity;
import com.tjyw.bbqmqd.adapter.NameMasterAdapter;
import com.tjyw.bbqmqd.factory.IClientActivityLaunchFactory;

import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 17-8-11.
 */
@RequiresPresenter(NamingPresenter.class)
public class NameMasterLuckyFragment extends BaseFragment<NamingPresenter<NameMasterRecommendFragment>> implements OnApiPayPostListener.PostPayListVipListener {

    public static NameMasterLuckyFragment newInstance(ListRequestParam param) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IApiField.P.param, param.clone());

        NameMasterLuckyFragment fragment = new NameMasterLuckyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @From(R.id.bodyServiceDiscount)
    protected ImageView bodyServiceDiscount;

    @From(R.id.bodyServiceUnlock)
    protected TextView bodyServiceUnlock;

    @From(R.id.bodyServiceUnlockPrice)
    protected TextView bodyServiceUnlockPrice;

    @From(R.id.bodyServiceUnlockAll)
    protected TextView bodyServiceUnlockAll;

    @From(R.id.bodyServiceUnlockAllPrice)
    protected TextView bodyServiceUnlockAllPrice;

    protected ListRequestParam listRequestParam;

    protected PayService payService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_name_master_lucky, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listRequestParam = (ListRequestParam) pGetSerializableExtra(IApiField.P.param);
        if (null != listRequestParam) {
            maskerShowProgressView(false);
            getPresenter().postPayListVipDiscount(
                    HttpPayServices.VIP_ID.TJJM,
                    listRequestParam.surname,
                    listRequestParam.day
            );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ICode.SECTION.PAY:
                switch (resultCode) {
                    case ICode.PAY.ALIPAY_SUCCESS:
                    case ICode.PAY.WX_SUCCESS:
                        if (null != data) {
                            listRequestParam.orderNo = data.getStringExtra(IApiField.O.orderNo);
                            IClientActivityLaunchFactory.launchNamingListActivity((BaseActivity) getActivity(), listRequestParam);
                        }
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bodyServiceUnlock:
                IClientActivityLaunchFactory.launchPayOrderActivity(this, listRequestParam, payService);
                break ;
            case R.id.bodyServiceUnlockAll:
                if (null != payService.suit) {
                    IClientActivityLaunchFactory.launchPayOrderActivity(this, listRequestParam, payService.suit);
                }
            default:
                super.onClick(v);
        }
    }

    @Override
    public void postOnPayListVipSuccess(int type, PayService payService) {
        this.payService = payService;
        maskerHideProgressView();

        bodyServiceUnlock.setOnClickListener(this);
        bodyServiceUnlockAll.setOnClickListener(this);
        bodyServiceDiscount.setVisibility(payService.discount < 10 ? View.VISIBLE : View.GONE);

        SpannableStringBuilder builder = new SpannableStringBuilder(ClientQmjmApplication.pGetString(R.string.atom_resStringSuitAdTJJM1));
        int length = builder.length();
        builder.append(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s_Yuan_Simple, payService.money));
        builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ClientQmjmApplication.getContext(), R.color.atom_pub_resTextColorRed)), length, builder.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.append(ClientQmjmApplication.pGetString(R.string.atom_resStringSuitAdTJJM2)); // 原价
        builder.append(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s_Yuan_Simple, payService.oldMoney));
        builder.append(ClientQmjmApplication.pGetString(R.string.atom_resStringSuitAdTJJM3));
        bodyServiceUnlockPrice.setText(builder);

        if (null != payService.suit) {
            builder = new SpannableStringBuilder(ClientQmjmApplication.pGetString(R.string.atom_resStringSuitAd88_4));
            length = builder.length();
            builder.append(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s_Yuan_Simple, payService.suit.money));
            builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ClientQmjmApplication.getContext(), R.color.atom_pub_resTextColorRed)), length, builder.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            builder.append(ClientQmjmApplication.pGetString(R.string.atom_resStringSuitAd88_2)); // 原价
            builder.append(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s_Yuan_Simple, payService.suit.oldMoney));
            builder.append(ClientQmjmApplication.pGetString(R.string.atom_resStringSuitAd88_3));

            bodyServiceUnlockAllPrice.setText(builder);
        }
    }
}
