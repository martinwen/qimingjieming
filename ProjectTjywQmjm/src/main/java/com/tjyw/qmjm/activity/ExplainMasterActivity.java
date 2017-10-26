package com.tjyw.qmjm.activity;

import android.content.Context;
import android.os.Bundle;
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
import atom.pub.inject.From;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.adapter.ExplainMasterAdapter;

import java.util.concurrent.TimeUnit;

import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.functions.Action1;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 17-8-11.
 */
@RequiresPresenter(NamingPresenter.class)
public class ExplainMasterActivity extends BaseToolbarActivity<NamingPresenter<ExplainMasterActivity>> implements OnApiPostErrorListener, OnApiPostExplainListener {

    @From(R.id.explainOverview)
    protected TextView explainOverview;

    @From(R.id.explainZodiac)
    protected TextView explainZodiac;

    @From(R.id.explainDestiny)
    protected TextView explainDestiny;

    @From(R.id.explainSanCai)
    protected TextView explainSanCai;

    @From(R.id.explainMasterContainer)
    protected ViewPager explainMasterContainer;

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
                    .statusBarDarkFont(true)
                    .init();
        }

        explainOverview.setSelected(true);
        explainOverview.setOnClickListener(this);
        explainZodiac.setOnClickListener(this);
        explainDestiny.setOnClickListener(this);
        explainSanCai.setOnClickListener(this);

        explainMasterContainer.setOffscreenPageLimit(ExplainMasterAdapter.POSITION.ALL);
        explainMasterContainer.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case ExplainMasterAdapter.POSITION.OVERVIEW:
                        setSelectedTab(explainOverview);
                        break ;
                    case ExplainMasterAdapter.POSITION.ZODIAC:
                        setSelectedTab(explainZodiac);
                        break ;
                    case ExplainMasterAdapter.POSITION.DESTINY:
                        setSelectedTab(explainDestiny);
                        break ;
                    case ExplainMasterAdapter.POSITION.SANCAI:
                        setSelectedTab(explainSanCai);
                }
            }
        });

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
            case R.id.explainOverview:
                setSelectedTab(v);
                explainMasterAdapter.showOverviewFragment(explainMasterContainer);
                break ;
            case R.id.explainZodiac:
                setSelectedTab(v);
                explainMasterAdapter.showZodiacFragment(explainMasterContainer);
                break ;
            case R.id.explainDestiny:
                setSelectedTab(v);
                explainMasterAdapter.showDestinyFragment(explainMasterContainer);
                break ;
            case R.id.explainSanCai:
                setSelectedTab(v);
                explainMasterAdapter.showSanCaiFragment(explainMasterContainer);
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
        explainMasterContainer.setAdapter(
                explainMasterAdapter = ExplainMasterAdapter.newInstance(getSupportFragmentManager(), explain)
        );
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

    protected void setSelectedTab(View view) {
        if (! view.isSelected()) {
            view.setSelected(true);

            switch (view.getId()) {
                case R.id.explainOverview:
                    explainZodiac.setSelected(false);
                    explainDestiny.setSelected(false);
                    explainSanCai.setSelected(false);
                    break ;
                case R.id.explainZodiac:
                    explainOverview.setSelected(false);
                    explainDestiny.setSelected(false);
                    explainSanCai.setSelected(false);
                    break ;
                case R.id.explainDestiny:
                    explainOverview.setSelected(false);
                    explainZodiac.setSelected(false);
                    explainSanCai.setSelected(false);
                    break ;
                case R.id.explainSanCai:
                    explainOverview.setSelected(false);
                    explainZodiac.setSelected(false);
                    explainDestiny.setSelected(false);
            }
        }
    }
}
