package com.tjyw.qmjm.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.gyf.barlibrary.ImmersionBar;
import com.tjyw.atom.pub.adapter.AtomPubClientMasterAdapter;
import com.tjyw.qmjm.R;

import butterknife.BindView;

/**
 * Created by stephen on 07/08/2017.
 */
public class ClientMasterActivity extends BaseActivity {

    @BindView(R.id.atomPubClientMasterNavigation)
    protected AHBottomNavigation atomPubClientMasterNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atom_pub_client_master);

        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .init();

        atomPubClientMasterNavigation.setAccentColor(ContextCompat.getColor(getApplicationContext(), R.color.atom_ewsh_textColorBlack));
        atomPubClientMasterNavigation.setInactiveColor(ContextCompat.getColor(getApplicationContext(), R.color.atom_ewsh_textColorGrey));

        int size = AtomPubClientMasterAdapter.MASTER_TAB_RESOURCE.size();
        for (int i = 0; i < size; i ++) {
            Pair<Integer, Integer> masterTabResource = AtomPubClientMasterAdapter.getMasterTabResource(i);
            if (null != masterTabResource) {
                atomPubClientMasterNavigation.addItem(
                        new AHBottomNavigationItem(
                                masterTabResource.first, masterTabResource.second, android.R.color.white
                        )
                );
            }
        }
    }
}
