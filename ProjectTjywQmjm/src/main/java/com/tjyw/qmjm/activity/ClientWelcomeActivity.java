package com.tjyw.qmjm.activity;

import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.tjyw.qmjm.R;

/**
 * Created by stephen on 17-8-1.
 */
public class ClientWelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atom_qmjm_client_welcome);

        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }
}
