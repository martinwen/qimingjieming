package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.model.PayOrderNumber;
import com.tjyw.atom.network.presenter.UserPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiUserPostListener;
import com.tjyw.atom.network.utils.DateTimeUtils;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;

import org.greenrobot.eventbus.EventBus;

import atom.pub.fresco.ImageFacade;
import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 17-11-16.
 */
@RequiresPresenter(UserPresenter.class)
public class PayCouponFragment extends BaseFragment<UserPresenter<PayCouponFragment>> implements OnApiUserPostListener.PostUserGetNewRedPacketListener {

    @From(R.id.payCouponDisplayView)
    protected SimpleDraweeView payCouponDisplayView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.atom_pay_coupon, container, true);
        view.setOnClickListener(this);
        maskerHideMaskerLayout();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (! hidden) {
            ClientInit clientInit = ClientInit.getInstance(ClientQmjmApplication.getContext());
            if (null != clientInit && ! TextUtils.isEmpty(clientInit.red_image_link)) {
                String time = DateTimeUtils.printCalendarByPattern(DateTimeUtils.getCurrentDate(), DateTimeUtils.yyyy_MM_dd);
                ImageFacade.loadImage(clientInit.red_image_link + "?time=" + time, payCouponDisplayView);
                payCouponDisplayView.setOnClickListener(this);
            }
        }
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
