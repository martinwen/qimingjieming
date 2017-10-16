package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;

import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 17-8-11.
 */
@RequiresPresenter(NamingPresenter.class)
public class NameMasterLuckyFragment extends NameMasterRecommendFragment {

    public static NameMasterLuckyFragment newInstance(ListRequestParam param) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IApiField.P.param, param.clone());

        NameMasterLuckyFragment fragment = new NameMasterLuckyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @From(R.id.namePayContainer)
    protected ViewGroup namePayContainer;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_name_master_lucky, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null != listRequestParam) {
            requestListData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.atom_pub_resIdsOK:
                IClientActivityLaunchFactory.launchPayOrderActivity(this, listRequestParam, payService);
                break ;
            default:
                super.onClick(v);
        }
    }

    @Override
    public void postOnNamingSuccess(RNameDefinition result) {
        super.postOnNamingSuccess(result);
        namePayContainer.setVisibility(View.GONE);
    }

    @Override
    public void postOnPayListVipSuccess(int type, PayService payService) {
        maskerHideProgressView();
        this.payService = payService;

        atom_pub_resIdsOK.setOnClickListener(this);

        bodySurname.setText(payService.surname);
        bodyDate.setText(payService.day);
        bodyServiceName.setText(payService.service);
        bodyServicePrice.setText(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringRMB_s, payService.money));
    }

    @Override
    protected void requestListData() {
        if (null != listRequestParam) {
            maskerShowProgressView(false);
            if (hasOrderNo()) {
                getPresenter().postPayOrderNameList(listRequestParam.orderNo);
            } else {
                getPresenter().postPayListVip(
                        2,
                        listRequestParam.surname,
                        listRequestParam.day
                );
            }
        }
    }

    @Override
    protected boolean canShowPayInterceptWindow() {
        return false;
    }
}
