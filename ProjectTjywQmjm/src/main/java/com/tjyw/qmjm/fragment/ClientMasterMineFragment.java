package com.tjyw.qmjm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brianjmelton.stanley.ProxyGenerator;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.tjyw.atom.network.Network;
import com.tjyw.atom.network.conf.ICode;
import com.tjyw.atom.network.interfaces.IPrefUser;
import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.model.UserInfo;
import com.tjyw.atom.network.presenter.ClientPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiClientPostListener;
import com.tjyw.atom.network.utils.DeviceUtil;
import com.tjyw.atom.network.utils.JsonUtil;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.activity.BaseActivity;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.tjyw.qmjm.item.MasterMineItem;

import java.util.ArrayList;
import java.util.List;

import atom.pub.fragment.AtomPubBaseFragment;
import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 07/08/2017.
 */
@RequiresPresenter(ClientPresenter.class)
public class ClientMasterMineFragment extends BaseFragment<ClientPresenter<ClientMasterMineFragment>> implements OnApiClientPostListener.PostClientInitListener {

    @From(R.id.masterMineUserSignIn)
    protected TextView masterMineUserSignIn;

    @From(R.id.masterMineUserAccount)
    protected TextView masterMineUserAccount;

    @From(R.id.masterMineContainer)
    protected RecyclerView masterMineContainer;

    protected FastItemAdapter<MasterMineItem> masterMineItemFastItemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_master_mine, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        masterMineContainer.setAdapter(masterMineItemFastItemAdapter = new FastItemAdapter<MasterMineItem>());
        masterMineContainer.setLayoutManager(new GridLayoutManager(getContext(), 3));

        drawWithUserInfo();

        ClientInit clientInit = ClientInit.getInstance(ClientQmjmApplication.getContext());
        if (null == clientInit) {
            getPresenter().postClientInit();
        } else {
            drawWithClientInit(clientInit);
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
        switch (v.getId()) {
            case R.id.masterMineUserSignIn:
                IClientActivityLaunchFactory.launchUserSignInActivity(this);
                break ;
            default:
                super.onClick(v);
        }
    }

    @Override
    public void postOnClientInitSuccess(ClientInit clientInit) {
        if (null != clientInit) {
            clientInit.saveInstance(ClientQmjmApplication.getContext());
            drawWithClientInit(clientInit);
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

    protected void drawWithClientInit(final ClientInit clientInit) {
        List<MasterMineItem> itemList = new ArrayList<MasterMineItem>();
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineOrder, R.drawable.atom_pub_ic_mine_order)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineCoupon, R.drawable.atom_pub_ic_mine_order)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineCollect, R.drawable.atom_pub_ic_mine_collect)));
//        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineService, R.drawable.atom_pub_ic_mine_service)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineBJX, R.drawable.atom_pub_ic_mine_bjx)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineZGJM, R.drawable.atom_pub_ic_mine_zgjm)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineQTS, R.drawable.atom_pub_ic_mine_qts)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineZodiac, R.drawable.atom_pub_ic_mine_zodiac)));
//        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineBaby, R.drawable.atom_pub_ic_mine_baby)));
//        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMinePregnant, R.drawable.atom_pub_ic_mine_pregnant)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineAbout, R.drawable.atom_pub_ic_mine_about)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineFeedback, R.drawable.atom_pub_ic_mine_feedback)));

        masterMineItemFastItemAdapter.add(itemList).withOnClickListener(new FastAdapter.OnClickListener<MasterMineItem>() {
            @Override
            public boolean onClick(View v, IAdapter<MasterMineItem> adapter, MasterMineItem item, int position) {
                masterFastItemAdapterItemClick(clientInit, item);
                return true;
            }
        });
    }

    protected void masterFastItemAdapterItemClick(ClientInit clientInit, MasterMineItem item) {
        if (null == clientInit) {
            return ;
        }

        switch (item.src.first) {
            case R.string.atom_pub_resStringMineOrder:
                IClientActivityLaunchFactory.launchPayOrderListActivity((BaseActivity) getActivity());
                break ;
            case R.string.atom_pub_resStringMineCoupon:
                IClientActivityLaunchFactory.launchPayCouponListActivity(ClientMasterMineFragment.this);
                break ;
            case R.string.atom_pub_resStringMineCollect:
                IClientActivityLaunchFactory.launchUserFavoriteListActivity(ClientMasterMineFragment.this);
                break ;
            case R.string.atom_pub_resStringMineService:
                break ;
            case R.string.atom_pub_resStringMineBJX:
                IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, clientInit.baijiaxing, R.string.atom_pub_resStringMineBJX);
                break ;
            case R.string.atom_pub_resStringMineZGJM:
                IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, clientInit.jiemeng, R.string.atom_pub_resStringMineZGJM);
                break ;
            case R.string.atom_pub_resStringMineQTS:
                IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, clientInit.quantangshi, R.string.atom_pub_resStringMineQTS);
                break ;
            case R.string.atom_pub_resStringMineZodiac:
                IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, clientInit.shengxiao, R.string.atom_pub_resStringMineZodiac);
                break ;
            case R.string.atom_pub_resStringMineBaby:
                IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, clientInit.yuer, R.string.atom_pub_resStringMineBaby);
                break ;
            case R.string.atom_pub_resStringMinePregnant:
                IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, clientInit.yunqibaodian, R.string.atom_pub_resStringMinePregnant);
                break ;
            case R.string.atom_pub_resStringMineAbout:
                StringBuilder builder = new StringBuilder(clientInit.about);
                builder.append("?v=").append(DeviceUtil.getClientVersionName(ClientQmjmApplication.getContext()));
                builder.append("&n=").append(ClientQmjmApplication.pGetString(R.string.app_name));
                builder.append("&c=").append(Network.getInstance().getCid());
                builder.append("&pid=").append(Network.getInstance().getPid());
                builder.append("&pack=").append(ClientQmjmApplication.getContext().getPackageName());

                IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, builder.toString(), R.string.atom_pub_resStringMineAbout);
                break ;
            case R.string.atom_pub_resStringMineFeedback:
                IClientActivityLaunchFactory.launchTouchActivity(ClientMasterMineFragment.this, clientInit.feedback, R.string.atom_pub_resStringMineFeedback);
        }
    }
}
