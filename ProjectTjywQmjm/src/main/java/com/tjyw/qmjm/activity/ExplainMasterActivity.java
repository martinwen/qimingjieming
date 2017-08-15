package com.tjyw.qmjm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostExplainListener;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.adapter.ExplainMasterAdapter;
import com.tjyw.qmjm.item.ExplainHeaderWordItem;

import java.util.ArrayList;
import java.util.List;

import nucleus.factory.RequiresPresenter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 17-8-11.
 */
@RequiresPresenter(NamingPresenter.class)
public class ExplainMasterActivity extends BaseToolbarActivity<NamingPresenter<ExplainMasterActivity>> implements OnApiPostErrorListener, OnApiPostExplainListener {

    @From(R.id.explainNameContainer)
    protected RecyclerView explainNameContainer;

    @From(R.id.explainOverview)
    protected TextView explainOverview;

    @From(R.id.explainZodiac)
    protected TextView explainZodiac;

    @From(R.id.explainDestiny)
    protected TextView explainDestiny;

    @From(R.id.explainMasterContainer)
    protected ViewPager explainMasterContainer;

    protected ExplainMasterAdapter explainMasterAdapter;

    protected FastItemAdapter<ExplainHeaderWordItem> explainHeaderWordAdapter;

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

        explainOverview.setOnClickListener(this);
        explainZodiac.setOnClickListener(this);
        explainDestiny.setOnClickListener(this);

        explainNameContainer.setAdapter(explainHeaderWordAdapter = new FastItemAdapter<ExplainHeaderWordItem>());
        explainNameContainer.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

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
                explainMasterAdapter.showOverviewFragment(explainMasterContainer);
                break ;
            case R.id.explainZodiac:
                explainMasterAdapter.showZodiacFragment(explainMasterContainer);
                break ;
            case R.id.explainDestiny:
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
        List<ExplainHeaderWordItem> itemList = new ArrayList<ExplainHeaderWordItem>();
        List<Explain.Word> wordsList = explain.wordsList;
        int size = wordsList.size();
        for (int i = 0; i < size; i ++) {
            itemList.add(new ExplainHeaderWordItem(wordsList.get(i)));
        }

        explainHeaderWordAdapter.set(itemList);
        explainHeaderWordAdapter.notifyAdapterItemChanged(0);

        explainMasterContainer.setAdapter(
                explainMasterAdapter = ExplainMasterAdapter.newInstance(getSupportFragmentManager(), explain)
        );
    }
}
