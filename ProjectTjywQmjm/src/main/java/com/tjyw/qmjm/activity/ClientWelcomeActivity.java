package com.tjyw.qmjm.activity;

import android.content.Context;
import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 17-8-1.
 */
public class ClientWelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atom_qmjm_client_welcome);

        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .init();

        Observable.timer(2, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<Long>io_main())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        IClientActivityLaunchFactory.launchClientMasterActivity(ClientWelcomeActivity.this);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
