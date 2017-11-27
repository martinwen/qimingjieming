package com.tjyw.bbqm.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.network.model.NameCharacter;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.bbqm.ClientQmjmApplication;
import com.tjyw.bbqm.R;

import atom.pub.inject.From;
import atom.pub.inject.Injector;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by stephen on 10/10/2017.
 */
public class AtomExplainHeaderHolder {

    @From(R.id.explainNameContainer)
    protected ViewGroup explainNameContainer;

    @From(R.id.explainEvaluateValue)
    protected TextView explainEvaluateValue;

    @From(R.id.explainEvaluateDesc)
    protected TextView explainEvaluateDesc;

    protected NameBaseInfoHolder nameBaseInfoHolder;

    public AtomExplainHeaderHolder(View view) {
        Injector.inject(this, view);
        nameBaseInfoHolder = new NameBaseInfoHolder(view);
    }

    public void layout(Explain explain) {
        nameBaseInfoHolder.baseInfo(explain.nameZodiac);

        explainEvaluateValue.setText(ClientQmjmApplication.pGetString(R.string.atom_pub_resStringExplainEvaluate, explain.nameScore.evaluation));
        explainEvaluateDesc.setText(explain.nameScore.desc);

        explainNameContainer.removeAllViews();
        if (!ArrayUtil.isEmpty(explain.wordsList)) {
            Observable.from(explain.wordsList)
                    .take(4)
                    .compose(RxSchedulersHelper.<NameCharacter>io_main())
                    .subscribe(new Action1<NameCharacter>() {
                        @Override
                        public void call(NameCharacter character) {
                            explainNameContainer.addView(
                                    HeaderWordHolder.newInstance(ClientQmjmApplication.getContext(), character),
                                    explainNameContainer.getChildCount()
                            );
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });
        }
    }
}
