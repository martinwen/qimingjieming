package com.tjyw.bbqm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.bbqm.R;
import com.tjyw.bbqm.adapter.NameMasterAdapter;
import com.tjyw.bbqm.adapter.PayPackageAdapter;
import com.tjyw.bbqm.factory.IClientActivityLaunchFactory;

import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 19/09/2017.
 */
@RequiresPresenter(NamingPresenter.class)
public class PayPackageActivity extends BaseToolbarActivity<NamingPresenter<PayPackageActivity>> {

    @From(R.id.payPackageNormal)
    protected TextView payPackageNormal;

    @From(R.id.payPackageHigh)
    protected TextView payPackageHigh;

    @From(R.id.payPackageLuck)
    protected TextView payPackageLuck;

    @From(R.id.payPackageContainer)
    protected ViewPager payPackageContainer;

    @From(R.id.payOrderRepeatPay)
    protected TextView payOrderRepeatPay;

    protected PayPackageAdapter payPackageAdapter;

    protected ListRequestParam listRequestParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listRequestParam = (ListRequestParam) pGetSerializableExtra(IApiField.P.param);
        if (null == listRequestParam) {
            finish();
            return ;
        } else {
            setContentView(R.layout.atom_pay_package);
            tSetToolBar(getString(R.string.atom_pub_resStringName));

            immersionBarWith()
                    .fitsSystemWindows(true)
                    .statusBarColor(R.color.colorPrimary)
                    .init();
        }

        payPackageNormal.setSelected(true);
        payPackageNormal.setOnClickListener(this);
        payPackageHigh.setOnClickListener(this);
        payPackageLuck.setOnClickListener(this);

        payPackageContainer.setOffscreenPageLimit(PayPackageAdapter.POSITION.ALL);
        payPackageContainer.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case PayPackageAdapter.POSITION.NORMAL:
                        setSelectedTab(payPackageNormal);
                        break ;
                    case PayPackageAdapter.POSITION.HIGH:
                        setSelectedTab(payPackageHigh);
                        break ;
                    case PayPackageAdapter.POSITION.LUCKY:
                        setSelectedTab(payPackageLuck);
                }
            }
        });

        payPackageContainer.setAdapter(
                payPackageAdapter = PayPackageAdapter.newInstance(getSupportFragmentManager(), listRequestParam)
        );
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payPackageNormal:
                showPayPackageNormalFragment();
                break ;
            case R.id.payPackageHigh:
                showPayPackageHighFragment();
                break ;
            case R.id.payPackageLuck:
                showPayPackageLuckyFragment();
                break ;
            case R.id.payOrderRepeatPay:
                ListRequestParam param = (ListRequestParam) v.getTag();
                if (null != param) {
                    IClientActivityLaunchFactory.launchNameMasterActivity(this, param, 100, NameMasterAdapter.POSITION.FREEDOM);
                }
                break ;
            default:
                super.onClick(v);
        }
    }

    protected void setSelectedTab(View view) {
        if (! view.isSelected()) {
            view.setSelected(true);

            switch (view.getId()) {
                case R.id.payPackageNormal:
                    payPackageHigh.setSelected(false);
                    payPackageLuck.setSelected(false);
                    break ;
                case R.id.payPackageHigh:
                    payPackageNormal.setSelected(false);
                    payPackageLuck.setSelected(false);
                    break ;
                case R.id.payPackageLuck:
                    payPackageNormal.setSelected(false);
                    payPackageHigh.setSelected(false);
            }
        }
    }

    public void setPayOrderRepeatPay(RNameDefinition result) {
        if (null != result) {
            if (TextUtils.isEmpty(result.statusLabel)) {
                payOrderRepeatPay.setVisibility(View.GONE);
            } else {
                payOrderRepeatPay.setTag(new ListRequestParam(result));
                payOrderRepeatPay.setOnClickListener(this);
                payOrderRepeatPay.setVisibility(View.VISIBLE);
                payOrderRepeatPay.setText(result.statusLabel);
            }
        }
    }

    public void showPayPackageNormalFragment() {
        if (null != payPackageAdapter) {
            payPackageAdapter.showAnalyzeFragment(payPackageContainer);
        }
    }

    public void showPayPackageHighFragment() {
        if (null != payPackageAdapter) {
            payPackageAdapter.showFreedomFragment(payPackageContainer);
        }
    }

    public void showPayPackageLuckyFragment() {
        if (null != payPackageAdapter) {
            payPackageAdapter.showLuckyFragment(payPackageContainer);
        }
    }
}
