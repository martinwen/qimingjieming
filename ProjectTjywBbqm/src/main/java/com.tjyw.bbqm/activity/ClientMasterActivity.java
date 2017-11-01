package com.tjyw.bbqm.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;

import com.aspsine.fragmentnavigator.FragmentNavigator;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.tjyw.atom.network.conf.IApiField;
import atom.pub.inject.From;
import com.tjyw.bbqm.ClientQmjmApplication;
import com.tjyw.bbqm.R;
import com.tjyw.bbqm.adapter.ClientMasterAdapter;
import com.tjyw.bbqm.fragment.ClientGregorianFragment;
import com.umeng.analytics.MobclickAgent;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 07/08/2017.
 */
public class ClientMasterActivity extends BaseActivity {

    @From(R.id.atomPubClientMasterNavigation)
    protected AHBottomNavigation atomPubClientMasterNavigation;

    protected FragmentNavigator fragmentNavigator;

    protected ClientGregorianFragment gregorianFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.atom_client_master);
        immersionBarWith()
                .statusBarDarkFont(true)
                .init();

        gregorianFragment = (ClientGregorianFragment) getSupportFragmentManager().findFragmentByTag(ClientGregorianFragment.class.getName());
        pHideFragment(gregorianFragment);

        fragmentNavigator = new FragmentNavigator(getSupportFragmentManager(), ClientMasterAdapter.newInstance(this), R.id.masterFragmentContainer);
        fragmentNavigator.onCreate(savedInstanceState);

        atomPubClientMasterNavigation.setAccentColor(ContextCompat.getColor(getApplicationContext(), R.color.atom_resColorTabHighLight));
        atomPubClientMasterNavigation.setInactiveColor(ContextCompat.getColor(getApplicationContext(), R.color.atom_resColorTabHighLight));

        int size = ClientMasterAdapter.MASTER_TAB_RESOURCE.size();
        for (int i = 0; i < size; i ++) {
            Pair<Integer, Integer> masterTabResource = ClientMasterAdapter.getMasterTabResource(i);
            if (null != masterTabResource) {
                atomPubClientMasterNavigation.addItem(
                        new AHBottomNavigationItem(
                                ClientQmjmApplication.pGetString(masterTabResource.first),
                                ContextCompat.getDrawable(ClientQmjmApplication.getContext(), masterTabResource.second),
                                ContextCompat.getColor(getApplicationContext(), R.color.atom_resColorTabHighLight)
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
                        case ClientMasterAdapter.POSITION.EXPLAIN:
                            immersionBar.fitsSystemWindows(false).transparentStatusBar().statusBarDarkFont(true).init();
                            break;
                        case ClientMasterAdapter.POSITION.MINE:
                            immersionBar.fitsSystemWindows(true).statusBarColor(R.color.colorPrimary).statusBarDarkFont(true).init();
                    }

                    fragmentNavigator.showFragment(position, false, false);
                    return true;
                }
            }
        });

        atomPubClientMasterNavigation.setCurrentItem(pGetIntExtra(IApiField.T.t, ClientMasterAdapter.POSITION.NAMING), true);
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

    public void showGregorianFragment(ClientGregorianFragment.OnGregorianSelectedListener listener) {
        pHideSoftInput();
        gregorianFragment.setOnGregorianSelectedListener(listener);
        pShowFragment(gregorianFragment);
    }
}
