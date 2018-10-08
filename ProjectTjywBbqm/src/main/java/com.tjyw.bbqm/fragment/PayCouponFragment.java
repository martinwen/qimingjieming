package com.tjyw.bbqm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.model.PayOrderNumber;
import com.tjyw.atom.network.presenter.UserPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiUserPostListener;
import com.tjyw.bbqm.ClientQmjmApplication;
import com.tjyw.bbqm.R;

import org.greenrobot.eventbus.EventBus;

import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 17-11-16.
 */
@RequiresPresenter(UserPresenter.class)
public class PayCouponFragment extends BaseFragment<UserPresenter<PayCouponFragment>> implements OnApiUserPostListener.PostUserGetNewRedPacketListener {

    @From(R.id.payCouponDisplayView)
    protected ImageView payCouponDisplayView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.atom_pay_coupon, container, true);
        view.setOnClickListener(this);
        maskerHideMaskerLayout();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        payCouponDisplayView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payCouponDisplayView:
                ClientInit clientInit = ClientInit.getInstance(ClientQmjmApplication.getContext());
                if (null != clientInit) {
                    v.setOnClickListener(null);
                    getPresenter().postUserGetNewRedPacket(clientInit.red_packet_id);
                }
                break ;
            default:
                pHideFragment(R.anim.abc_fade_in, R.anim.abc_fade_out, this);
        }
    }

    @Override
    public void postOnUserGetNewRedPacketSuccess(String message) {
        EventBus.getDefault().post(new PayOrderNumber());
        showToast(message);
        pHideFragment(R.anim.abc_fade_in, R.anim.abc_fade_out, this);
    }
}
