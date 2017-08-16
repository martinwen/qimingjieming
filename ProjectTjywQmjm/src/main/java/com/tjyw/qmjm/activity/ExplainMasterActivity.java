package com.tjyw.qmjm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostExplainListener;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.adapter.ExplainMasterAdapter;
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
        }

        explainOverview.setSelected(true);
        explainOverview.setOnClickListener(this);
        explainZodiac.setOnClickListener(this);
        explainDestiny.setOnClickListener(this);

        getPresenter().postExplain(postSurname, postName);
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
            default:
                super.onClick(v);
        }
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void postOnExplainSuccess(Explain explain) {
        explainMasterContainer.setAdapter(
                explainMasterAdapter = ExplainMasterAdapter.newInstance(getSupportFragmentManager(), explain)
        );

        explainNameContainer.removeAllViews();
        if (!ArrayUtil.isEmpty(explain.wordsList)) {
            Observable.from(explain.wordsList)
                    .take(3)
                    .compose(RxSchedulersHelper.<Explain.Word>io_main())
                    .subscribe(new Action1<Explain.Word>() {
                        @Override
                        public void call(Explain.Word word) {
                            explainNameContainer.addView(
                                    HeaderWordHolder.newInstance(ClientQmjmApplication.getContext(), word),
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

    protected void setSelectedTab(View view) {
        if (! view.isSelected()) {
            view.setSelected(true);
            view.setBackgroundResource(R.drawable.atom_pub_selector_ink_text);

            switch (view.getId()) {
                case R.id.explainOverview:
                    explainZodiac.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
                    explainDestiny.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
                    break ;
                case R.id.explainZodiac:
                    explainOverview.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
                    explainDestiny.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
                    break ;
                case R.id.explainDestiny:
                    explainOverview.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
                    explainZodiac.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
            }
        }
    }
}
