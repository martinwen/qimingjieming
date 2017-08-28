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
import com.tjyw.atom.network.model.Favorite;
import com.tjyw.atom.network.presenter.FavoritePresenter;
import com.tjyw.atom.network.presenter.IPost;
import com.tjyw.atom.network.presenter.listener.OnApiFavoritePostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.result.RetroListResult;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.tjyw.qmjm.item.UserFavoriteItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import me.dkzwm.widget.srl.SmoothRefreshLayout;
import nucleus.factory.RequiresPresenter;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 14/08/2017.
 */
@RequiresPresenter(FavoritePresenter.class)
public class UserFavoriteListActivity extends BaseToolbarActivity<FavoritePresenter<UserFavoriteListActivity>>
        implements SmoothRefreshLayout.OnRefreshListener, OnApiPostErrorListener, OnApiFavoritePostListener.PostListListener, OnApiFavoritePostListener.PostRemoveListener {

    static final int PAGE_SIZE = 10;

    @From(R.id.favoriteListRefreshLayout)
    protected SmoothRefreshLayout favoriteListRefreshLayout;

    @From(R.id.favoriteListContainer)
    protected RecyclerView favoriteListContainer;

    protected FastItemAdapter<UserFavoriteItem> userFavoriteAdapter;

    protected int pageNo;

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

        userFavoriteAdapter = new FastItemAdapter<UserFavoriteItem>();
        userFavoriteAdapter.withOnClickListener(new FastAdapter.OnClickListener<UserFavoriteItem>() {
            @Override
            public boolean onClick(View v, IAdapter<UserFavoriteItem> adapter, UserFavoriteItem item, int position) {
                if (null != item.src) {
                    IClientActivityLaunchFactory.launchExplainMasterActivity(
                            UserFavoriteListActivity.this,
                            item.src.surname,
                            item.src.getGivenName(),
                            item.src.day,
                            item.src.gender
                    );
                }
                return true;
            }
        }).withItemEvent(new ClickEventHook<UserFavoriteItem>() {
            @Nullable
            @Override
            public View onBind(@NonNull RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof UserFavoriteItem.UserFavoriteHolder) {
                    return ((UserFavoriteItem.UserFavoriteHolder) viewHolder).getNameWordCollect();
                } else {
                    return super.onBind(viewHolder);
                }
            }

            @Override
            public void onClick(View v, int position, FastAdapter<UserFavoriteItem> fastAdapter, UserFavoriteItem item) {
                maskerShowProgressView(true);
                getPresenter().postFavoriteRemove(item.src.id, item);
            }
        });

        favoriteListContainer.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        favoriteListContainer.setAdapter(userFavoriteAdapter);
        favoriteListContainer.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getApplicationContext())
                        .color(R.color.atom_pub_resColorDivider)
                        .sizeResId(R.dimen.atom_pubResDimenRecyclerViewDividerSize)
                        .marginResId(R.dimen.atom_pubResDimenRecyclerViewDivider16dp)
                        .showLastDivider()
                        .build());

        favoriteListRefreshLayout.setOnRefreshListener(this);
        onRefreshBegin(true);
    }

    @Override
    public void maskerOnClick(View view) {
        onRefreshBegin(true);
    }

    @Override
    public void onRefreshBegin(boolean isRefresh) {
        if (isRefresh) {
            maskerShowProgressView(true);
            getPresenter().postFavoriteList(pageNo = 0 , PAGE_SIZE);
        } else {
            getPresenter().postFavoriteList((++ pageNo) * PAGE_SIZE, PAGE_SIZE);
        }
    }

    @Override
    public void onRefreshComplete(boolean isSuccessful) {
        Timber.tag("Gx").e("onRefreshComplete::isSuccessful::%s", isSuccessful);
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
    public void postOnFavoriteListSuccess(RetroListResult<Favorite> result) {
        maskerHideProgressView();
        if (null == result) {
            favoriteListRefreshLayout.setDisableLoadMore(true);
            favoriteListRefreshLayout.refreshComplete();
            return ;
        }

        int size = result.size();
        List<UserFavoriteItem> itemList = new ArrayList<UserFavoriteItem>();
        for (int i = 0; i < size; i ++) {
            itemList.add(new UserFavoriteItem(result.get(i)));
        }

        if (favoriteListRefreshLayout.isRefreshing()) {
            userFavoriteAdapter.set(itemList);
        } else {
            userFavoriteAdapter.add(itemList);
        }

        favoriteListRefreshLayout.setDisableLoadMore(userFavoriteAdapter.getAdapterItemCount() >= result.totalCount);
        favoriteListRefreshLayout.refreshComplete();
    }

    @Override
    public void postOnFavoriteRemoveSuccess(Object item) {
        onRefreshBegin(true);
    }
}
