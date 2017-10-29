package com.tjyw.bbqm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.conf.ICode;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import atom.pub.inject.From;
import com.tjyw.bbqm.ClientQmjmApplication;
import com.tjyw.bbqm.R;
import com.tjyw.bbqm.activity.BaseActivity;
import com.tjyw.bbqm.factory.IClientActivityLaunchFactory;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_name_master_lucky, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listRequestParam = (ListRequestParam) pGetSerializableExtra(IApiField.P.param);
        if (null != listRequestParam) {
            maskerShowProgressView(false);
            getPresenter().postPayListVip(
                    2,
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
            case R.id.atom_pub_resIdsOK:
                IClientActivityLaunchFactory.launchPayOrderActivity(this, listRequestParam, payService);
                break ;
            default:
                super.onClick(v);
        }
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
}
