package com.tjyw.qmjm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.conf.ISection;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostNamingListener;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.dialog.NamingPayWindows;
import com.tjyw.qmjm.item.NamingWordItem;

import java.util.ArrayList;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 14/08/2017.
 */
@RequiresPresenter(NamingPresenter.class)
public class NamingListActivity extends BaseToolbarActivity<NamingPresenter<NamingListActivity>> implements OnApiPostErrorListener, OnApiPostNamingListener {

    protected String postSurname;

    protected int postGender;

    protected int postNameNumber;

    @From(R.id.namingListContainer)
    protected RecyclerView namingListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postSurname = pGetStringExtra(IApiField.S.surname, null);
        if (TextUtils.isEmpty(postSurname)) {
            finish();
            return ;
        } else {
            postGender = pGetIntExtra(IApiField.G.gender, ISection.GENDER.MALE);
            postNameNumber = pGetIntExtra(IApiField.N.nameNumber, ISection.NAME_COUNT.SINGLE);
        }

        setContentView(R.layout.atom_naming_list);
        tSetToolBar(getString(R.string.atom_pub_resStringNamingList));

        getPresenter().postNaming(
                postSurname, postGender, postNameNumber
        );

        namingListContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        NamingPayWindows.newInstance(getSupportFragmentManager());
                }
            }
        });
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void postOnNamingSuccess(List<String> result) {
        List<NamingWordItem> itemList = new ArrayList<NamingWordItem>();
        int size = result.size();
        for (int i = 0; i < size; i ++) {
            itemList.add(new NamingWordItem(result.get(i)));
        }

        FastItemAdapter<NamingWordItem> fastItemAdapter = new FastItemAdapter<>();
        fastItemAdapter.set(itemList);

        namingListContainer.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        namingListContainer.setAdapter(fastItemAdapter);
    }
}
