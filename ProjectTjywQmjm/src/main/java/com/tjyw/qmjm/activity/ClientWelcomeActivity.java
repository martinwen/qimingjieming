package com.tjyw.qmjm.activity;

import android.os.Bundle;

import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.adapter.ClientMasterAdapter;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by stephen on 17-8-1.
 */
public class ClientWelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.atom_qmjm_client_welcome);
        immersionBarWith()
                .statusBarDarkFont(true)
                .init();

        Observable.timer(100, TimeUnit.MILLISECONDS)
                .compose(RxSchedulersHelper.<Long>io_main())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        IClientActivityLaunchFactory.launchClientMasterActivity(ClientWelcomeActivity.this, ClientMasterAdapter.POSITION.NAMING, false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }
}
