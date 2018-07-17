package com.tjyw.qmjmqd.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.aspsine.fragmentnavigator.FragmentNavigator;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.model.PayOrderNumber;
import com.tjyw.atom.network.presenter.PayPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import com.tjyw.qmjmqd.ClientQmjmApplication;
import com.tjyw.qmjmqd.R;
import com.tjyw.qmjmqd.adapter.ClientMasterAdapter;
import com.tjyw.qmjmqd.fragment.ClientGregorianFragment;
import com.tjyw.qmjmqd.fragment.PayCouponFragment;
import com.umeng.analytics.MobclickAgent;

import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 07/08/2017.
 */
@RequiresPresenter(PayPresenter.class)
public class ClientMasterActivity extends BaseToolbarActivity<PayPresenter<ClientMasterActivity>> implements OnApiPayPostListener.PostPayOrderUnReadNumListener {

    @From(R.id.atomPubClientMasterNavigation)
    protected AHBottomNavigation atomPubClientMasterNavigation;

    protected FragmentNavigator fragmentNavigator;

    protected ClientGregorianFragment gregorianFragment;

    protected PayCouponFragment payCouponFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.atom_client_master);
        immersionBarWith()
                .fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(true)
                .init();

        gregorianFragment = (ClientGregorianFragment) getSupportFragmentManager().findFragmentByTag(ClientGregorianFragment.class.getName());
        payCouponFragment = (PayCouponFragment) getSupportFragmentManager().findFragmentByTag(PayCouponFragment.class.getName());
        pHideFragment(gregorianFragment, payCouponFragment);

        fragmentNavigator = new FragmentNavigator(getSupportFragmentManager(), ClientMasterAdapter.newInstance(this), R.id.masterFragmentContainer);
        fragmentNavigator.onCreate(savedInstanceState);

        atomPubClientMasterNavigation.setAccentColor(ContextCompat.getColor(getApplicationContext(), R.color.atom_resColorNavigationAccent));
        atomPubClientMasterNavigation.setInactiveColor(ContextCompat.getColor(getApplicationContext(), R.color.atom_resColorNavigationInactive));
        atomPubClientMasterNavigation.setNotificationBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.atom_pub_notification_background));

        int size = ClientMasterAdapter.MASTER_TAB_RESOURCE.size();
        for (int i = 0; i < size; i ++) {
            Pair<Integer, Integer> masterTabResource = ClientMasterAdapter.getMasterTabResource(i);
            if (null != masterTabResource) {
                atomPubClientMasterNavigation.addItem(
                        new AHBottomNavigationItem(
                                ClientQmjmApplication.pGetString(masterTabResource.first),
                                ContextCompat.getDrawable(ClientQmjmApplication.getContext(), masterTabResource.second)
                        )
                );
            }
        }

        atomPubClientMasterNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        atomPubClientMasterNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (gregorianFragment.isVisible()) {
                    pHideFragment(gregorianFragment);
                    return false;
                } else {
                    switch (position) {
                        case ClientMasterAdapter.POSITION.NAMING:
                            tSetToolBar(getString(R.string.atom_pub_resStringMasterTabNaming), false);
                            break ;
                        case ClientMasterAdapter.POSITION.EXPLAIN:
                            tSetToolBar(getString(R.string.atom_pub_resStringMasterTabExplain), false);
                            break ;
                        case ClientMasterAdapter.POSITION.MINE:
                            tSetToolBar(getString(R.string.atom_pub_resStringMasterTabMine), false);
                    }

                    fragmentNavigator.showFragment(position, false, false);
                    return true;
                }
            }
        });

        atomPubClientMasterNavigation.setCurrentItem(pGetIntExtra(IApiField.T.t, ClientMasterAdapter.POSITION.NAMING), true);

        ClientInit clientInit = ClientInit.getInstance(getApplicationContext());
        if (null != clientInit && ! TextUtils.isEmpty(clientInit.red_image_link)) {
            pShowFragment(payCouponFragment);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (null == intent) {

        } else if (intent.hasExtra(IApiField.T.t)) {
            int position = intent.getIntExtra(IApiField.T.t, ClientMasterAdapter.POSITION.NAMING);
            atomPubClientMasterNavigation.setCurrentItem(position, true);
        }
    }

    @Override
    public void onBackPressed() {
        if (gregorianFragment.isVisible()) {
            pHideFragment(gregorianFragment);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.atom_pub_resStringTip)
                    .setMessage(R.string.atom_pub_resStringAppQuit)
                    .setNegativeButton(R.string.atom_pub_resStringCancel, null)
                    .setPositiveButton(R.string.atom_pub_resStringOK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MobclickAgent.onKillProcess(ClientMasterActivity.this);
                            ClientMasterActivity.super.onBackPressed();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void postOnPayOrderUnReadNumSuccess(PayOrderNumber result) {
        if (result.redNumber != 0 || result.orderNumber != 0) {
            atomPubClientMasterNavigation.setNotification(" ", ClientMasterAdapter.POSITION.MINE);
        } else {
            atomPubClientMasterNavigation.setNotification((String) null, ClientMasterAdapter.POSITION.MINE);
        }
    }

    public void showGregorianFragment(ClientGregorianFragment.OnGregorianSelectedListener listener) {
        pHideSoftInput();
        gregorianFragment.setOnGregorianSelectedListener(listener);
        pShowFragment(gregorianFragment);
    }
}
