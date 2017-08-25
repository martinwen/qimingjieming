package com.tjyw.qmjm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.conf.ISection;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.network.model.NameCharacter;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostExplainListener;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.adapter.ClientMasterAdapter;
import com.tjyw.qmjm.adapter.ExplainMasterAdapter;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.tjyw.qmjm.holder.HeaderWordHolder;

import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.functions.Action1;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 17-8-11.
 */
@RequiresPresenter(NamingPresenter.class)
public class ExplainMasterActivity extends BaseToolbarActivity<NamingPresenter<ExplainMasterActivity>> implements OnApiPostErrorListener, OnApiPostExplainListener {

    @From(R.id.explainNameContainer)
    protected ViewGroup explainNameContainer;

    @From(R.id.explainEvaluateValue)
    protected TextView explainEvaluateValue;

    @From(R.id.explainEvaluateDesc)
    protected TextView explainEvaluateDesc;

    @From(R.id.explainNaming)
    protected TextView explainNaming;

    @From(R.id.explainOverview)
    protected TextView explainOverview;

    @From(R.id.explainZodiac)
    protected TextView explainZodiac;

    @From(R.id.explainDestiny)
    protected TextView explainDestiny;

    @From(R.id.explainMasterContainer)
    protected ViewPager explainMasterContainer;

    protected ExplainMasterAdapter explainMasterAdapter;

    protected String postSurname;

    protected String postName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postSurname = pGetStringExtra(IApiField.S.surname, null);
        postName = pGetStringExtra(IApiField.N.name, null);
        if (TextUtils.isEmpty(postSurname) || TextUtils.isEmpty(postName)) {
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
        explainNaming.setOnClickListener(this);

        maskerShowProgressView(false);
        getPresenter().postExplain(
                postSurname,
                postName,
                pGetStringExtra(IApiField.D.day, null),
                pGetIntExtra(IApiField.G.gender, ISection.GENDER.MALE)
        );
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.explainNaming:
                IClientActivityLaunchFactory.launchClientMasterActivity(this, ClientMasterAdapter.POSITION.NAMING, true);
                break ;
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

        explainEvaluateValue.setText(getString(R.string.atom_pub_resStringExplainEvaluate, explain.nameScore.score));
        explainEvaluateDesc.setText(explain.nameScore.desc);

        explainNameContainer.removeAllViews();
        if (!ArrayUtil.isEmpty(explain.wordsList)) {
            Observable.from(explain.wordsList)
                    .take(4)
                    .compose(RxSchedulersHelper.<NameCharacter>io_main())
                    .subscribe(new Action1<NameCharacter>() {
                        @Override
                        public void call(NameCharacter character) {
                            explainNameContainer.addView(
                                    HeaderWordHolder.newInstance(ClientQmjmApplication.getContext(), character),
                                    explainNameContainer.getChildCount()
                            );
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });
        }
    }

    @Override
    public void maskerOnClick(View view) {
        maskerShowProgressView(false);
        getPresenter().postExplain(
                postSurname,
                postName,
                pGetStringExtra(IApiField.D.data, null),
                pGetIntExtra(IApiField.G.gender, ISection.GENDER.MALE)
        );
    }

    protected void setSelectedTab(View view) {
        if (! view.isSelected()) {
            view.setSelected(true);

            switch (view.getId()) {
                case R.id.explainOverview:
                    explainZodiac.setSelected(false);
                    explainDestiny.setSelected(false);
                    break ;
                case R.id.explainZodiac:
                    explainOverview.setSelected(false);
                    explainDestiny.setSelected(false);
                    break ;
                case R.id.explainDestiny:
                    explainOverview.setSelected(false);
                    explainZodiac.setSelected(false);
            }
        }
    }
}
