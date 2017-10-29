package com.tjyw.bbqm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostNamingListener;
import com.tjyw.atom.network.result.RNameDefinition;
import atom.pub.inject.From;
import com.tjyw.bbqm.R;
import com.tjyw.bbqm.adapter.NameMasterAdapter;
import com.tjyw.bbqm.fragment.PayServiceFragment;

import java.util.concurrent.TimeUnit;

import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.functions.Action1;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 19/09/2017.
 */
@RequiresPresenter(NamingPresenter.class)
public class NameMasterActivity extends BaseToolbarActivity<NamingPresenter<NamingListActivity>> implements OnApiPostErrorListener, OnApiPostNamingListener {

    @From(R.id.nameAnalyze)
    protected TextView nameAnalyze;

    @From(R.id.nameFreedom)
    protected TextView nameFreedom;

    @From(R.id.nameRecommend)
    protected TextView nameRecommend;

    @From(R.id.nameLucky)
    protected TextView nameLucky;

    @From(R.id.nameMasterContainer)
    protected ViewPager nameMasterContainer;

    public NameMasterAdapter nameMasterAdapter;

    protected ListRequestParam listRequestParam;

    protected PayServiceFragment payServiceFragment;

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
                    .statusBarDarkFont(true)
                    .init();

            payServiceFragment = findFragmentById(R.id.payServiceFragment, PayServiceFragment.class);
            pHideFragment(payServiceFragment);
        }

        nameAnalyze.setSelected(true);
        nameAnalyze.setOnClickListener(this);
        nameFreedom.setOnClickListener(this);
        nameRecommend.setOnClickListener(this);
        nameLucky.setOnClickListener(this);

        nameMasterContainer.setOffscreenPageLimit(NameMasterAdapter.POSITION.ALL);
        nameMasterContainer.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case NameMasterAdapter.POSITION.ANALYZE:
                        setSelectedTab(nameAnalyze);
                        break ;
                    case NameMasterAdapter.POSITION.FREEDOM:
                        setSelectedTab(nameFreedom);
                        break ;
                    case NameMasterAdapter.POSITION.RECOMMEND:
                        setSelectedTab(nameRecommend);
                        break ;
                    case NameMasterAdapter.POSITION.LUCKY:
                        setSelectedTab(nameLucky);
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
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if (null != payServiceFragment && payServiceFragment.isVisible()) {
            pHideFragment(payServiceFragment);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nameAnalyze:
                showAnalyzeFragment();
                break ;
            case R.id.nameFreedom:
                showFreedomFragment();
                break ;
            case R.id.nameRecommend:
                showRecommendFragment();
                break ;
            case R.id.nameLucky:
                showLuckyFragment();
                break ;
            default:
                super.onClick(v);
        }
    }

    protected void setSelectedTab(View view) {
        if (! view.isSelected()) {
            view.setSelected(true);

            switch (view.getId()) {
                case R.id.nameAnalyze:
                    nameRecommend.setSelected(false);
                    nameFreedom.setSelected(false);
                    nameLucky.setSelected(false);
                    break ;
                case R.id.nameFreedom:
                    nameAnalyze.setSelected(false);
                    nameRecommend.setSelected(false);
                    nameLucky.setSelected(false);
                    break ;
                case R.id.nameRecommend:
                    nameAnalyze.setSelected(false);
                    nameFreedom.setSelected(false);
                    nameLucky.setSelected(false);
                    break ;
                case R.id.nameLucky:
                    nameAnalyze.setSelected(false);
                    nameFreedom.setSelected(false);
                    nameRecommend.setSelected(false);
            }
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
        nameMasterContainer.setAdapter(
                nameMasterAdapter = NameMasterAdapter.newInstance(getSupportFragmentManager(), result)
        );
    }

    public void showAnalyzeFragment() {
        if (null != nameMasterAdapter) {
            nameMasterAdapter.showAnalyzeFragment(nameMasterContainer);
        }
    }

    public void showFreedomFragment() {
        if (null != nameMasterAdapter) {
            nameMasterAdapter.showFreedomFragment(nameMasterContainer);
        }
    }

    public void showRecommendFragment() {
        if (null != nameMasterAdapter) {
            nameMasterAdapter.showRecommendFragment(nameMasterContainer);
        }
    }

    public void showLuckyFragment() {
        if (null != nameMasterAdapter) {
            nameMasterAdapter.showLuckyFragment(nameMasterContainer);
        }
    }
}
