package com.tjyw.qmjm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.tjyw.atom.network.model.PayCoupon;
import com.tjyw.atom.network.model.PayOrderNumber;
import com.tjyw.atom.network.presenter.UserPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiUserPostListener;
import com.tjyw.atom.network.result.RPayPacketResult;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.adapter.ClientMasterAdapter;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.tjyw.qmjm.item.PayCouponListItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 17-11-16.
 */
@RequiresPresenter(UserPresenter.class)
public class PayCouponListActivity extends BaseToolbarActivity<UserPresenter<PayCouponListActivity>> implements
        OnApiPostErrorListener, OnApiUserPostListener.PostUserListPacketListener {

    @From(R.id.payCouponListContainer)
    protected RecyclerView payCouponListContainer;

    protected FastItemAdapter<PayCouponListItem> payCouponFastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.atom_pay_coupon_list);
        tSetToolBar(getString(R.string.atom_pub_resStringMineCoupon));

        immersionBarWith()
                .fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(true)
                .init();

        payCouponFastAdapter = new FastItemAdapter<PayCouponListItem>();
        payCouponFastAdapter.withOnClickListener(new FastAdapter.OnClickListener<PayCouponListItem>() {
            @Override
            public boolean onClick(View v, IAdapter<PayCouponListItem> adapter, PayCouponListItem item, int position) {
                switch (item.src.status) {
                    case PayCoupon.STATUS.UNUSED:
                        IClientActivityLaunchFactory.launchClientMasterActivity(PayCouponListActivity.this, ClientMasterAdapter.POSITION.NAMING, true);
                        return false;
                    default:
                        return false;
                }
            }
        });
        payCouponListContainer.setAdapter(payCouponFastAdapter);
        payCouponListContainer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        maskerShowProgressView(false);
        getPresenter().postUserListPacket();
    }

    @Override
    public void maskerOnClick(View view, int clickLabelRes) {
        maskerShowProgressView(false);
        getPresenter().postUserListPacket();
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {
        throwable.printStackTrace();
        maskerShowMaskerLayout(getString(R.string.atom_pub_resStringNetworkBroken), R.string.atom_pub_resStringRetry);
    }

    @Override
    public void postOnUserListPacketSuccess(RPayPacketResult result) {
        EventBus.getDefault().post(new PayOrderNumber()); // 清除掉主界面的新订单红点

        List<PayCoupon> list = new ArrayList<PayCoupon>();
        result.getPayPacketList(list);

        int size = list.size();
        if (ArrayUtil.isEmpty(list)) {
            maskerShowMaskerLayout(getString(R.string.atom_pub_resStringPayCouponListLack), 0);
        } else {
            maskerHideProgressView();
            for (int i = 0; i < size; i ++) {
                PayCouponListItem item = new PayCouponListItem(list.get(i));
                payCouponFastAdapter.add(item);
            }
        }
    }
}
