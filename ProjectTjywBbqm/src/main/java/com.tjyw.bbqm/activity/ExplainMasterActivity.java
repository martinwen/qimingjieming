package com.tjyw.bbqm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostExplainListener;
import com.tjyw.bbqm.R;
import com.tjyw.bbqm.adapter.ExplainMasterAdapter;
import com.tjyw.bbqm.adapter.NameMasterAdapter;
import com.tjyw.bbqm.factory.IClientActivityLaunchFactory;

import java.util.concurrent.TimeUnit;

import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.functions.Action1;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 17-8-11.
 */
@RequiresPresenter(NamingPresenter.class)
public class ExplainMasterActivity extends BaseToolbarActivity<NamingPresenter<ExplainMasterActivity>> implements OnApiPostErrorListener, OnApiPostExplainListener {

    @From(R.id.explainTabHost)
    protected TabLayout explainTabHost;

    @From(R.id.explainMasterContainer)
    protected ViewPager explainMasterContainer;

    @From(R.id.explainGoodName)
    protected TextView explainGoodName;

    protected ExplainMasterAdapter explainMasterAdapter;

    protected ListRequestParam listRequestParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listRequestParam = (ListRequestParam) pGetSerializableExtra(IApiField.P.param);
        if (null == listRequestParam) {
            finish();
            return ;
        } else {
            setContentView(R.layout.atom_explain_host);
            tSetToolBar(getString(R.string.atom_pub_resStringMasterTabExplain));

            immersionBarWith()
                    .fitsSystemWindows(true)
                    .statusBarColor(R.color.colorPrimary)
                    .statusBarDarkFont(STATUSBAR_DARK_FONT)
                    .init();

            explainGoodName.setOnClickListener(this);
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
                            getPresenter().postExplain(
                                    listRequestParam.surname,
                                    listRequestParam.name,
                                    listRequestParam.day,
                                    listRequestParam.gender
                            );
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        finish();
                    }
                });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.explainGoodName:
                if (null != listRequestParam) {
                    IClientActivityLaunchFactory.launchNameMasterActivity(this, listRequestParam, 100, NameMasterAdapter.POSITION.FREEDOM);
                }
                break ;
            default:
                super.onClick(v);
        }
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {
        throwable.printStackTrace();
        maskerShowMaskerLayout(getString(R.string.atom_pub_resStringNetworkBroken), R.string.atom_pub_resStringRetry);
    }

    @Override
    public void postOnExplainSuccess(Explain explain) {
        maskerHideProgressView();

        explainMasterContainer.setOffscreenPageLimit(ExplainMasterAdapter.POSITION.ALL);
        explainMasterContainer.setAdapter(
                explainMasterAdapter = ExplainMasterAdapter.newInstance(getSupportFragmentManager(), explain)
        );

        explainTabHost.setupWithViewPager(explainMasterContainer);
        explainTabHost.getTabAt(0).select();
    }

    @Override
    public void maskerOnClick(View view, int clickLabelRes) {
        super.maskerOnClick(view, clickLabelRes);
        maskerShowProgressView(false);
        getPresenter().postExplain(
                listRequestParam.surname,
                listRequestParam.name,
                listRequestParam.day,
                listRequestParam.gender
        );
    }
}
