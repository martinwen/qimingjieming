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
import com.tjyw.atom.network.conf.ICode;
import com.tjyw.atom.network.interfaces.IPrefUser;
import com.tjyw.atom.network.model.UserInfo;
import com.tjyw.atom.network.utils.JsonUtil;
import com.tjyw.atom.pub.fragment.AtomPubBaseFragment;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.activity.BaseActivity;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.tjyw.qmjm.item.MasterMineItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 07/08/2017.
 */
public class ClientMasterMineFragment extends AtomPubBaseFragment {

    @From(R.id.masterMineUserSignIn)
    protected TextView masterMineUserSignIn;

    @From(R.id.masterMineUserAccount)
    protected TextView masterMineUserAccount;

    @From(R.id.masterMineContainer)
    protected RecyclerView masterMineContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_master_mine, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawWithUserInfo();

        FastItemAdapter<MasterMineItem> adapter = new FastItemAdapter<MasterMineItem>();
        masterMineContainer.setAdapter(adapter);
        masterMineContainer.setLayoutManager(new GridLayoutManager(getContext(), 3));

        List<MasterMineItem> itemList = new ArrayList<MasterMineItem>();
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineOrder, R.drawable.atom_pub_ic_mine_order)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineCollect, R.drawable.atom_pub_ic_mine_collect)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineService, R.drawable.atom_pub_ic_mine_service)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineBJX, R.drawable.atom_pub_ic_mine_bjx)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineZGJM, R.drawable.atom_pub_ic_mine_zgjm)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineQTS, R.drawable.atom_pub_ic_mine_qts)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineZodiac, R.drawable.atom_pub_ic_mine_zodiac)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMineBaby, R.drawable.atom_pub_ic_mine_baby)));
        itemList.add(new MasterMineItem(new Pair<Integer, Integer>(R.string.atom_pub_resStringMinePregnant, R.drawable.atom_pub_ic_mine_pregnant)));

        adapter.add(itemList).withOnClickListener(new FastAdapter.OnClickListener<MasterMineItem>() {
            @Override
            public boolean onClick(View v, IAdapter<MasterMineItem> adapter, MasterMineItem item, int position) {
                switch (item.src.first) {
                    case R.string.atom_pub_resStringMineOrder:
                        IClientActivityLaunchFactory.launchPayOrderListActivity((BaseActivity) getActivity());
                        break ;
                    case R.string.atom_pub_resStringMineCollect:
                        IClientActivityLaunchFactory.launchUserFavoriteListActivity(ClientMasterMineFragment.this);
                        break ;
                    case R.string.atom_pub_resStringMineService:
                    case R.string.atom_pub_resStringMineBJX:
                    case R.string.atom_pub_resStringMineZGJM:
                    case R.string.atom_pub_resStringMineQTS:
                    case R.string.atom_pub_resStringMineZodiac:
                    case R.string.atom_pub_resStringMineBaby:
                    case R.string.atom_pub_resStringMinePregnant:
                        break ;
                }

                return true;
            }
        });
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
}
