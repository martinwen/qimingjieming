package com.tjyw.qmjm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;
import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.presenter.FavoritePresenter;
import com.tjyw.atom.network.presenter.IPost;
import com.tjyw.atom.network.presenter.listener.OnApiFavoritePostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.item.NamingWordItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import nucleus.factory.RequiresPresenter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 14/08/2017.
 */
@RequiresPresenter(FavoritePresenter.class)
public class UserFavoriteListActivity extends BaseToolbarActivity<FavoritePresenter<UserFavoriteListActivity>>
        implements OnApiPostErrorListener, OnApiFavoritePostListener.PostFavoriteAddListener, OnApiFavoritePostListener.PostFavoriteListListener, OnApiFavoritePostListener.PostFavoriteRemoveListener {

    @From(R.id.favoriteListContainer)
    protected RecyclerView favoriteListContainer;

    protected FastItemAdapter<NamingWordItem> nameDefinitionAdapter;

    protected RNameDefinition.Param postParam;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.atom_user_favorite_list);
        tSetToolBar(getString(R.string.atom_pub_resStringFavoriteList));

        immersionBarWith()
                .fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(true)
                .init();

        nameDefinitionAdapter = new FastItemAdapter<NamingWordItem>();
        nameDefinitionAdapter.withOnClickListener(new FastAdapter.OnClickListener<NamingWordItem>() {
            @Override
            public boolean onClick(View v, IAdapter<NamingWordItem> adapter, NamingWordItem item, int position) {
                return true;
            }
        }).withItemEvent(new ClickEventHook<NamingWordItem>() {
            @Nullable
            @Override
            public View onBind(@NonNull RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof NamingWordItem.NamingWordHolder) {
                    return ((NamingWordItem.NamingWordHolder) viewHolder).getNameWordCollect();
                } else {
                    return super.onBind(viewHolder);
                }
            }

            @Override
            public void onClick(View v, int position, FastAdapter<NamingWordItem> fastAdapter, NamingWordItem item) {
                getPresenter().postFavoriteRemove(
                        item.src.getSurname(), item.src.getGivenName()
                );
            }
        });

        favoriteListContainer.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        favoriteListContainer.setAdapter(nameDefinitionAdapter);
        favoriteListContainer.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getApplicationContext())
                        .color(R.color.atom_pub_resColorDivider)
                        .sizeResId(R.dimen.atom_pubResDimenRecyclerViewDividerSize)
                        .marginResId(R.dimen.atom_pubResDimenRecyclerViewDivider16dp)
                        .showLastDivider()
                        .build());

        maskerOnClick(null);
    }

    @Override
    public void maskerOnClick(View view) {
        maskerShowProgressView(false);
        getPresenter().postFavoriteList();
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {
        throwable.printStackTrace();
        switch (postId) {
            case IPost.FavoriteList:
                if (throwable instanceof IllegalRequestException) {
                    maskerShowMaskerLayout(throwable.getMessage(), R.string.atom_pub_resStringRetry);
                } else {
                    maskerShowMaskerLayout(getString(R.string.atom_pub_resStringNetworkBroken), R.string.atom_pub_resStringRetry);
                }
                break ;
            default:
                maskerHideProgressView();
        }
    }

    @Override
    public void postOnFavoriteAddSuccess() {
        nameDefinitionAdapter.notifyAdapterDataSetChanged();
    }

    @Override
    public void postOnFavoriteListSuccess(RNameDefinition result) {
        maskerHideProgressView();
        if (null != result) {
            postParam = result.param;
            int size = result.size();
            if (size > 0) {
                List<NamingWordItem> itemList = new ArrayList<NamingWordItem>();
                for (int i = 0; i < size; i++) {
                    itemList.add(new NamingWordItem(result.get(i)));
                }

                nameDefinitionAdapter.set(itemList);
            }
        }
    }

    @Override
    public void postOnFavoriteRemoveSuccess() {
        maskerShowProgressView(false);
        getPresenter().postFavoriteList();
    }
}
