package com.tjyw.qmjm.support;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.atom.pub.interfaces.IAtomPubToolBar;
import com.tjyw.qmjm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stephen on 17-8-4.
 */
public class AtomClientToolbarSupport implements IAtomPubToolBar, View.OnClickListener {

    public static AtomClientToolbarSupport newInstance(IAtomPubToolBar iAtomPubToolBar, View source) {
        return new AtomClientToolbarSupport(iAtomPubToolBar, source);
    }

    @BindView(R.id.atomPubToolBar)
    protected Toolbar atomPubToolBar;

    @BindView(R.id.atomPubToolBarCenterView)
    protected TextView atomPubToolBarCenterView;

    @BindView(R.id.atomPubToolBarRightFirst)
    protected TextView atomPubToolBarRightFirst;

    @BindView(R.id.atomPubToolBarRightSecondBubble)
    protected ImageView atomPubToolBarRightSecondBubble;

    @BindView(R.id.atomPubToolBarRightSecond)
    protected TextView atomPubToolBarRightSecond;

    protected IAtomPubToolBar iAtomPubToolBar;

    public AtomClientToolbarSupport(IAtomPubToolBar iAtomPubToolBar, View source) {
        this.iAtomPubToolBar = iAtomPubToolBar;
        ButterKnife.bind(this, source);
    }

    @Override
    public void tSetToolBar(CharSequence title) {
        tSetToolBar(title, true);
    }

    @Override
    public void tSetToolBar(CharSequence title, boolean hasBackBtn, Integer... right) {
        tSetToolbarVisibility(View.VISIBLE);
        if (null != atomPubToolBarCenterView) {
            atomPubToolBarCenterView.setText(title);
        }

        if (hasBackBtn && null != atomPubToolBar) {
//            atomPubToolBar.setNavigationIcon(R.drawable.ic_navi_back);
            atomPubToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    if (context instanceof FragmentActivity) {
                        ((FragmentActivity) context).onBackPressed();
                    } else if (context instanceof Activity) {
                        ((Activity) context).onBackPressed();
                    }
                }
            });
        }

        if (ArrayUtil.isEmpty(right)) {
             atomPubToolBarRightFirst.setText(null);
             atomPubToolBarRightSecond.setText(null);
        } else if (right.length == 1) {
             atomPubToolBarRightFirst.setText(null);
             atomPubToolBarRightFirst.setOnClickListener(null);

             atomPubToolBarRightSecond.setText(right[0]);
             atomPubToolBarRightSecond.setOnClickListener(this);
        } else {
             atomPubToolBarRightSecond.setText(right[0]);
             atomPubToolBarRightSecond.setText(right[1]);
        }
    }

    @Override
    public void tSetToolbarVisibility(int visibility) {
        if (null != atomPubToolBarCenterView) {
            atomPubToolBarCenterView.setVisibility(visibility);
        }
    }

    @Override
    public void tSetToolbarCenterTitleMaxLines(int maxLines) {
        atomPubToolBarCenterView.setMaxLines(maxLines);
    }

    @Override
    public void tSetToolbarRightSecondBubble(int count) {
        atomPubToolBarRightSecondBubble.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void tOnToolbarRightViewClick(View v) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.atomPubToolBarRightFirst:
            case R.id.atomPubToolBarRightSecond:
                if (null != iAtomPubToolBar) {
                    iAtomPubToolBar.tOnToolbarRightViewClick(v);
                }
                break ;
        }
    }
}
