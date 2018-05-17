package com.tjyw.bbqmqd.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.brianjmelton.stanley.ProxyGenerator;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.conf.ICode;
import com.tjyw.atom.network.interfaces.IPrefClient;
import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostNamingListener;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.network.services.HttpPayServices;
import com.tjyw.atom.network.utils.JsonUtil;
import com.tjyw.bbqmqd.R;
import com.tjyw.bbqmqd.adapter.NameMasterAdapter;
import com.tjyw.bbqmqd.factory.IClientActivityLaunchFactory;
import com.tjyw.bbqmqd.fragment.PayPackageEntryFragment;
import com.tjyw.bbqmqd.fragment.PayServiceFragment;

import java.util.concurrent.TimeUnit;

import atom.pub.fresco.ImageFacade;
import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.functions.Action1;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 19/09/2017.
 */
@RequiresPresenter(NamingPresenter.class)
public class NameMasterActivity extends BaseToolbarActivity<NamingPresenter<NameMasterActivity>> implements
        OnApiPostErrorListener,
        OnApiPostNamingListener,
        OnApiPayPostListener.PostPayListVipListener {

    @From(R.id.nameTabHost)
    protected TabLayout explainTabHost;

    @From(R.id.nameMasterContainer)
    protected ViewPager nameMasterContainer;

    @From(R.id.nameMasterPayPackageEntry)
    protected SimpleDraweeView nameMasterPayPackageEntry;

    protected NameMasterAdapter nameMasterAdapter;

    protected ListRequestParam listRequestParam;

    protected PayServiceFragment payServiceFragment;

    protected PayPackageEntryFragment payPackageEntryFragment;

    protected PayService payService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listRequestParam = (ListRequestParam) pGetSerializableExtra(IApiField.P.param);
        if (null == listRequestParam) {
            finish();
            return ;
        } else {
            setContentView(R.layout.atom_name_master);
            tSetToolBar(getString(R.string.atom_pub_resStringName));

            immersionBarWith()
                    .fitsSystemWindows(true)
                    .statusBarColor(R.color.colorPrimary)
                    .statusBarDarkFont(STATUSBAR_DARK_FONT)
                    .init();

            payServiceFragment = findFragmentById(R.id.payServiceFragment, PayServiceFragment.class);
            payPackageEntryFragment = findFragmentById(R.id.payPackageEntryFragment, PayPackageEntryFragment.class);
            pHideFragment(payServiceFragment, payPackageEntryFragment);
        }

        int delayed = pGetIntExtra(IApiField.D.delayed, 100);
        if (delayed >= 1000) {
            maskerShowProgressView(false, true, getString(R.string.atom_pub_resStringNetworkRequesting));
        } else {
            maskerShowProgressView(false);
        }

        Observable.timer(delayed, TimeUnit.MILLISECONDS)
                .compose(RxSchedulersHelper.<Long>io_main())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (! isFinishing()) {
                            getPresenter().postNameDefinitionData(
                                    listRequestParam.surname,
                                    listRequestParam.day,
                                    listRequestParam.gender,
                                    listRequestParam.nameNumber
                            );
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        finish();
                    }
                });

        IPrefClient client = new ProxyGenerator().create(getApplicationContext(), IPrefClient.class);
        if (null != client) {
            ClientInit clientInit = JsonUtil.getInstance().parseObject(client.getClientInit(), ClientInit.class);
            if (null != clientInit && clientInit.listVip && ! TextUtils.isEmpty(clientInit.listVipImageUrl)) {
                ImageFacade.loadImage(clientInit.listVipImageUrl, nameMasterPayPackageEntry);
                nameMasterPayPackageEntry.setVisibility(View.VISIBLE);
                nameMasterPayPackageEntry.setOnClickListener(this);

//                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.atom_anim_scale_set);
//                set.setTarget(nameMasterPayPackageEntry);
//                set.start();
            }
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
                            IClientActivityLaunchFactory.launchPayPackageActivity(this, listRequestParam);
                        }
                }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if (null != payServiceFragment && payServiceFragment.isVisible()) {
            pHideFragment(payServiceFragment);
        } else if (null != payPackageEntryFragment && payPackageEntryFragment.isVisible()) {
            pHideFragment(payPackageEntryFragment);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nameMasterPayPackageEntry:
                if (null == payService) {
                    maskerShowProgressView(true);
                    getPresenter().postPayListVipDiscount(
                            HttpPayServices.VIP_ID.NEW_SUIT,
                            listRequestParam.surname,
                            listRequestParam.day
                    );
                } else {
                    postOnPayListVipSuccess(1, payService);
                }
                break ;
            default:
                super.onClick(v);
        }
    }

    public void showContainerFragment(int position, boolean smoothScroll) {
        if (null != nameMasterAdapter) {
            nameMasterContainer.setCurrentItem(position, smoothScroll);
        }
    }

    @Override
    public void maskerOnClick(View view, int clickLabelRes) {
        super.maskerOnClick(view, clickLabelRes);
        maskerShowProgressView(false);
        getPresenter().postNameDefinitionData(
                listRequestParam.surname,
                listRequestParam.day,
                listRequestParam.gender,
                listRequestParam.nameNumber
        );
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {
        throwable.printStackTrace();
        maskerShowMaskerLayout(getString(R.string.atom_pub_resStringNetworkBroken), R.string.atom_pub_resStringRetry);
    }

    @Override
    public void postOnNamingSuccess(RNameDefinition result) {
        maskerHideProgressView();

        nameMasterAdapter = NameMasterAdapter.newInstance(getSupportFragmentManager(), result);
        nameMasterContainer.setOffscreenPageLimit(NameMasterAdapter.POSITION.ALL);
        nameMasterContainer.setAdapter(nameMasterAdapter);
        nameMasterContainer.setCurrentItem(pGetIntExtra(IApiField.T.t, NameMasterAdapter.POSITION.ANALYZE), false);

        explainTabHost.setupWithViewPager(nameMasterContainer);
    }

    @Override
    public void postOnPayListVipSuccess(int type, PayService payService) {
        maskerHideProgressView();
        this.payService = payService;

        payPackageEntryFragment.setListRequestParam(listRequestParam);
        payPackageEntryFragment.setPayService(payService);
        payPackageEntryFragment.setOnPayPackageEntryClickListener(new PayPackageEntryFragment.OnPayPackageEntryClickListener() {
            @Override
            public void payPackageOnServicePayClick(PayPackageEntryFragment fragment, ListRequestParam listRequestParam, PayService payService) {
                if (null != listRequestParam && null != payService) {
                    IClientActivityLaunchFactory.launchPayOrderActivity(NameMasterActivity.this, listRequestParam, payService);
                }
            }
        });

        pShowFragment(R.anim.abc_fade_in, R.anim.abc_fade_out, payPackageEntryFragment);
    }
}
