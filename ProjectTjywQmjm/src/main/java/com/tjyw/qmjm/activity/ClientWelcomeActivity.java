package com.tjyw.qmjm.activity;

import android.content.Context;
import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.tjyw.qmjm.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 17-8-1.
 */
public class ClientWelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.atom_qmjm_client_welcome);
        setContentView(R.layout.atom_master_naming);

        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
