package com.tjyw.qmjm.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.adapter.ExplainMasterAdapter;
import com.tjyw.qmjm.fragment.ExplainMasterDestinyFragment;
import com.tjyw.qmjm.fragment.ExplainMasterOverviewFragment;
import com.tjyw.qmjm.fragment.ExplainMasterZodiacFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by stephen on 17-8-11.
 */
public class ExplainMasterActivity extends BaseToolbarActivity {

    @BindView(R.id.explainMasterContainer)
    protected ViewPager explainMasterContainer;

    protected ExplainMasterAdapter explainMasterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.atom_explain_host);
        tSetToolBar(getString(R.string.atom_pub_resStringMasterTabExplain));

        Observable.timer(2, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<Long>io_main())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        explainMasterAdapter = new ExplainMasterAdapter(getSupportFragmentManager());
                        explainMasterAdapter.setExplainMasterOverviewFragment(ExplainMasterOverviewFragment.newInstance());
                        explainMasterAdapter.setExplainMasterZodiacFragment(ExplainMasterZodiacFragment.newInstance());
                        explainMasterAdapter.setExplainMasterDestinyFragment(ExplainMasterDestinyFragment.newInstance());

                        explainMasterContainer.setAdapter(explainMasterAdapter);
                    }
                });
    }
}
