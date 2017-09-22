package com.tjyw.qmjm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostNamingListener;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.adapter.NameMasterAdapter;
import com.tjyw.qmjm.dialog.NamingPayWindows;

import nucleus.factory.RequiresPresenter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 19/09/2017.
 */
@RequiresPresenter(NamingPresenter.class)
public class NameMasterActivity extends BaseToolbarActivity<NamingPresenter<NamingListActivity>> implements OnApiPostErrorListener, OnApiPostNamingListener {

    @From(R.id.nameAnalyze)
    protected TextView nameAnalyze;

    @From(R.id.nameRecommend)
    protected TextView nameRecommend;

    @From(R.id.nameLucky)
    protected TextView nameLucky;

    @From(R.id.nameMasterContainer)
    protected ViewPager nameMasterContainer;

    protected NameMasterAdapter nameMasterAdapter;

    protected NamingPayWindows namingPayWindows;

    protected ListRequestParam listRequestParam;

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
                    .statusBarDarkFont(true)
                    .init();
        }

        nameAnalyze.setSelected(true);
        nameAnalyze.setOnClickListener(this);
        nameRecommend.setOnClickListener(this);
        nameLucky.setOnClickListener(this);

        nameMasterContainer.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case NameMasterAdapter.POSITION.ANALYZE:
                        setSelectedTab(nameAnalyze);
                        break ;
                    case NameMasterAdapter.POSITION.RECOMMEND:
                        setSelectedTab(nameRecommend);
                        break ;
                    case NameMasterAdapter.POSITION.LUCKY:
                        setSelectedTab(nameLucky);
                }
            }
        });

        maskerShowProgressView(false);
        getPresenter().postNameDefinitionData(
                listRequestParam.surname,
                listRequestParam.day,
                listRequestParam.gender,
                listRequestParam.nameNumber
        );
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nameAnalyze:
                nameMasterAdapter.showAnalyzeFragment(nameMasterContainer);
                break ;
            case R.id.nameRecommend:
                nameMasterAdapter.showRecommendFragment(nameMasterContainer);
                break ;
            case R.id.nameLucky:
                nameMasterAdapter.showLuckyFragment(nameMasterContainer);
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
                    nameLucky.setSelected(false);
                    break ;
                case R.id.nameRecommend:
                    nameAnalyze.setSelected(false);
                    nameLucky.setSelected(false);
                    break ;
                case R.id.nameLucky:
                    nameAnalyze.setSelected(false);
                    nameRecommend.setSelected(false);
            }
        }
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {

    }

    @Override
    public void postOnNamingSuccess(RNameDefinition result) {
        maskerHideProgressView();
        nameMasterContainer.setAdapter(
                nameMasterAdapter = NameMasterAdapter.newInstance(getSupportFragmentManager(), result)
        );
    }
}