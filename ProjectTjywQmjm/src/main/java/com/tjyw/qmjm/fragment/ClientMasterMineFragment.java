package com.tjyw.qmjm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brianjmelton.stanley.ProxyGenerator;
import com.tjyw.atom.network.Network;
import com.tjyw.atom.network.conf.ICode;
import com.tjyw.atom.network.interfaces.IPrefClient;
import com.tjyw.atom.network.interfaces.IPrefUser;
import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.model.PayOrderNumber;
import com.tjyw.atom.network.model.UserInfo;
import com.tjyw.atom.network.presenter.PayPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiClientPostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import com.tjyw.atom.network.utils.DeviceUtil;
import com.tjyw.atom.network.utils.JsonUtil;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.activity.BaseActivity;
import com.tjyw.qmjm.activity.ClientMasterActivity;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 07/08/2017.
 */
@RequiresPresenter(PayPresenter.class)
public class ClientMasterMineFragment extends BaseFragment<PayPresenter<ClientMasterMineFragment>> implements
        OnApiClientPostListener.PostClientInitListener,
        OnApiPayPostListener.PostPayOrderUnReadNumListener {

    @From(R.id.masterMineUserSignIn)
    protected TextView masterMineUserSignIn;

    @From(R.id.masterMineUserAccount)
    protected TextView masterMineUserAccount;

    @From(R.id.mineCellOrder)
    protected TextView mineCellOrder;

    @From(R.id.mineCellCoupon)
    protected TextView mineCellCoupon;

    @From(R.id.mineCellCouponNew)
    protected TextView mineCellCouponNew;

    @From(R.id.mineCellCouponBubble)
    protected ImageView mineCellCouponBubble;

    @From(R.id.mineCellCollect)
    protected TextView mineCellCollect;

    @From(R.id.mineCellBJX)
    protected TextView mineCellBJX;

    @From(R.id.mineCellZGJM)
    protected TextView mineCellZGJM;

    @From(R.id.mineCellQTS)
    protected TextView mineCellQTS;

    @From(R.id.mineCellZodiac)
    protected TextView mineCellZodiac;

    @From(R.id.mineCellAbout)
    protected TextView mineCellAbout;

    @From(R.id.mineCellFeedback)
    protected TextView mineCellFeedback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_master_mine_cell, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new PayOrderNumber());

        drawWithUserInfo();

        mineCellOrder.setOnClickListener(this);
        mineCellCoupon.setOnClickListener(this);
        mineCellCollect.setOnClickListener(this);
        mineCellBJX.setOnClickListener(this);
        mineCellZGJM.setOnClickListener(this);
        mineCellQTS.setOnClickListener(this);
        mineCellZodiac.setOnClickListener(this);
        mineCellAbout.setOnClickListener(this);
        mineCellFeedback.setOnClickListener(this);

        ClientInit clientInit = ClientInit.getInstance(ClientQmjmApplication.getContext());
        if (null == clientInit) {
            getPresenter().postClientInit();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        IPrefClient client = new ProxyGenerator().create(ClientQmjmApplication.getContext(), IPrefClient.class);
        if (null != client) {
            if (hidden) {
                mineCellCouponNew.setVisibility(View.GONE);
            } else if (client.getNewFlagCoupon()) {
                mineCellCouponNew.setVisibility(View.GONE);
            } else {
                client.setNewFlagCoupon(true);
                mineCellCouponNew.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ICode.SECTION.SS:
                switch (resultCode) {
                    case ICode.SS.OK:
                        drawWithUserInfo();
                }
        }
    }

    @Override
    public void onClick(View v) {
        ClientInit clientInit = ClientInit.getInstance(ClientQmjmApplication.getContext());

        switch (v.getId()) {
            case R.id.masterMineUserSignIn:
                IClientActivityLaunchFactory.launchUserSignInActivity(this);
                break ;
            case R.id.mineCellOrder:
                IClientActivityLaunchFactory.launchPayOrderListActivity((BaseActivity) getActivity());
                break ;
            case R.id.mineCellCoupon:
                IClientActivityLaunchFactory.launchPayCouponListActivity(ClientMasterMineFragment.this);
                break ;
            case R.id.mineCellCollect:
                IClientActivityLaunchFactory.launchUserFavoriteListActivity(ClientMasterMineFragment.this);
                break ;
            case R.id.mineCellBJX:
                if (null != clientInit) {
                    IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, clientInit.baijiaxing, R.string.atom_pub_resStringMineBJX);
                }
                break ;
            case R.id.mineCellZGJM:
                if (null != clientInit) {
                    IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, clientInit.jiemeng, R.string.atom_pub_resStringMineZGJM);
                }
                break ;
            case R.id.mineCellQTS:
                if (null != clientInit) {
                    IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, clientInit.quantangshi, R.string.atom_pub_resStringMineQTS);
                }
                break ;
            case R.id.mineCellZodiac:
                if (null != clientInit) {
                    IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, clientInit.shengxiao, R.string.atom_pub_resStringMineZodiac);
                }
                break ;
            case R.id.mineCellAbout:
                StringBuilder builder = new StringBuilder(clientInit.about);
                builder.append("?v=").append(DeviceUtil.getClientVersionName(ClientQmjmApplication.getContext()));
                builder.append("&n=").append(ClientQmjmApplication.pGetString(R.string.app_name));
                builder.append("&c=").append(Network.getInstance().getCid());
                builder.append("&pid=").append(Network.getInstance().getPid());
                builder.append("&pack=").append(ClientQmjmApplication.getContext().getPackageName());

                IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, builder.toString(), R.string.atom_pub_resStringMineAbout);
                break ;
            case R.id.mineCellFeedback:
                if (null != clientInit) {
                    IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, clientInit.feedback, R.string.atom_pub_resStringMineFeedback);
                }
                break ;
            default:
                super.onClick(v);
        }
    }

    @Override
    public void postOnClientInitSuccess(ClientInit clientInit) {
        if (null != clientInit) {
            clientInit.saveInstance(ClientQmjmApplication.getContext());
        }
    }

    protected void drawWithUserInfo() {
        IPrefUser user = new ProxyGenerator().create(ClientQmjmApplication.getContext(), IPrefUser.class);
        if (null != user) {
            UserInfo userInfo = JsonUtil.getInstance().fromJson(user.getUserInfo(), UserInfo.class);
            if (null != userInfo) {
                masterMineUserAccount.setText(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringMineUserAccount, userInfo.account));
                if (TextUtils.isEmpty(userInfo.mobile)) {
                    masterMineUserSignIn.setText(R.string.atom_pub_resStringUserSignInClick);
                    masterMineUserSignIn.setOnClickListener(this);
                } else {
                    masterMineUserSignIn.setText(R.string.atom_pub_resStringUserSignInOK);
                    masterMineUserSignIn.setOnClickListener(null);
                    masterMineUserSignIn.setClickable(false);
                }
            }
        }
    }

    @Override
    public void postOnPayOrderUnReadNumSuccess(PayOrderNumber result) {
        if (result.redNumber != 0 || result.orderNumber != 0) {
            mineCellCouponBubble.setVisibility(View.VISIBLE);
        } else {
            mineCellCouponBubble.setVisibility(View.GONE);
        }

        if (getActivity() instanceof ClientMasterActivity) {
            ((ClientMasterActivity) getActivity()).postOnPayOrderUnReadNumSuccess(result);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void postPayOrderUnReadNum(PayOrderNumber payOrderNumber) {
        getPresenter().postPayOrderUnReadNum();
    }
}
