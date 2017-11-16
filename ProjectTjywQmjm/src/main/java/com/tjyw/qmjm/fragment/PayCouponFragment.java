package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;

import atom.pub.fresco.ImageFacade;
import atom.pub.inject.From;

/**
 * Created by stephen on 17-11-16.
 */
public class PayCouponFragment extends BaseFragment {

    @From(R.id.payCouponDisplayView)
    protected SimpleDraweeView payCouponDisplayView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.atom_pay_coupon, container, true);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (! hidden) {
            ClientInit clientInit = ClientInit.getInstance(ClientQmjmApplication.getContext());
            if (null != clientInit && ! TextUtils.isEmpty(clientInit.red_image_link)) {
                ImageFacade.loadImage(clientInit.red_image_link + "?time=" + System.currentTimeMillis(), payCouponDisplayView);
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
                    showToast(clientInit.red_packet_id + "::" + clientInit.red_image_link);
                }
                break ;
            default:
                pHideFragment(R.anim.abc_fade_in, R.anim.abc_fade_out, this);
        }
    }
}
