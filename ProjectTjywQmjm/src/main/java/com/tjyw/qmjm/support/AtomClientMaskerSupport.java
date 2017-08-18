package com.tjyw.qmjm.support;

import android.graphics.Color;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.inject.Injector;
import com.tjyw.atom.pub.interfaces.IAtomPubLayoutSupportMasker;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;

/**
 * Created by stephen on 17-8-18.
 */
public class AtomClientMaskerSupport implements View.OnClickListener, IAtomPubLayoutSupportMasker {

    public static AtomClientMaskerSupport newInstance(OnMaskerClickListener listener, View source) {
        if (null == source.findViewById(R.id.atomPubMaskerRootView)) {
            return null;
        } else {
            return new AtomClientMaskerSupport(listener, source);
        }
    }

    @From(R.id.atomPubMaskerRootView)
    protected ViewGroup atomPubMaskerRootView;

    @From(R.id.atomPubMaskerContainer)
    protected ViewGroup atomPubMaskerContainer;

    @From(R.id.atomPubMaskerImage)
    protected ImageView atomPubMaskerImage;

    @From(R.id.atomPubMaskerMsg)
    protected TextView atomPubMaskerMsg;

    @From(R.id.atomPubMaskerClickText)
    protected TextView atomPubMaskerClickText;

    @From(R.id.atomPubProgressView)
    protected ViewGroup atomPubProgressView;

    protected OnMaskerClickListener maskerClickListener;

    public AtomClientMaskerSupport(OnMaskerClickListener listener, View source) {
        Injector.inject(this, source);
        this.maskerClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.atomPubMaskerClickText:
                if (null != maskerClickListener) {
                    maskerClickListener.maskerOnClick(v);
                }
        }
    }

    @Override
    public void maskerShowProgressView(boolean isAlpha) {
        atomPubProgressView.setVisibility(View.VISIBLE);
        if (isAlpha) {
            atomPubProgressView.setBackgroundColor(Color.parseColor("#55000000"));
        } else {
            atomPubProgressView.setBackgroundColor(ContextCompat.getColor(
                    atomPubProgressView.getContext(), R.color.atom_pub_resColorBackground
            ));
        }
    }

    @Override
    public void maskerHideProgressView() {
        atomPubProgressView.setVisibility(View.GONE);
    }

    @Override
    public void maskerShowMaskerLayout(String msg, @StringRes int clickLabelRes) {
        atomPubMaskerContainer.setVisibility(View.VISIBLE);
        maskerHideProgressView();

        atomPubMaskerMsg.setText(msg);
        atomPubMaskerClickText.setText(clickLabelRes);
        atomPubMaskerClickText.setOnClickListener(this);
    }

    @Override
    public void maskerHideMaskerLayout() {
        atomPubMaskerContainer.setVisibility(View.GONE);
    }
}
