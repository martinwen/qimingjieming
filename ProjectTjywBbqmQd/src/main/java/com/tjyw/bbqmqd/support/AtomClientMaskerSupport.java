package com.tjyw.bbqmqd.support;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import atom.pub.inject.From;
import atom.pub.inject.Injector;
import atom.pub.interfaces.IAtomPubLayoutSupportMasker;
import com.tjyw.bbqmqd.R;

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

    @From(R.id.atomPubProgressReverseView)
    protected ImageView atomPubProgressReverseView;

    @From(R.id.atomPubProgressForwardView)
    protected ImageView atomPubProgressForwardView;

    @From(R.id.atomPubProgressHintView)
    protected TextView atomPubProgressHintView;

    protected OnMaskerClickListener maskerClickListener;

    protected AnimatorSet atomPubProgressAnimatorSet;

    public AtomClientMaskerSupport(OnMaskerClickListener listener, View source) {
        Injector.inject(this, source);
        this.maskerClickListener = listener;

        atomPubMaskerImage.setImageResource(R.drawable.atom_ic_illegal);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.atomPubMaskerClickText:
                if (null != maskerClickListener) {
                    maskerClickListener.maskerOnClick(v, (Integer) v.getTag());
                }
        }
    }

    @Override
    public void maskerShowProgressView(boolean isAlpha) {
        maskerShowProgressView(isAlpha, true, null);
    }

    @Override
    public void maskerShowProgressView(boolean isAlpha, boolean anim) {
        maskerShowProgressView(isAlpha, anim, null);
    }

    @Override
    public void maskerShowProgressView(boolean isAlpha, boolean anim, String hint) {
        atomPubProgressView.setBackgroundColor(
                ContextCompat.getColor(atomPubProgressView.getContext(),
                        isAlpha ? R.color.atom_pub_resColorLoadingBackgroundAlpha : R.color.atom_pub_resColorLoadingBackground
                )
        );

        if (anim) {
            if (null == atomPubProgressAnimatorSet) {
                Animator forward = AnimatorInflater.loadAnimator(atomPubProgressForwardView.getContext(), R.animator.atom_pub_anim_rotate_forward);
                forward.setTarget(atomPubProgressForwardView);
                Animator reverse = AnimatorInflater.loadAnimator(atomPubProgressReverseView.getContext(), R.animator.atom_pub_anim_rotate_reverse);
                reverse.setTarget(atomPubProgressReverseView);

                atomPubProgressAnimatorSet = new AnimatorSet();
                atomPubProgressAnimatorSet.setInterpolator(new LinearInterpolator());
                atomPubProgressAnimatorSet.playTogether(forward, reverse);
                atomPubProgressAnimatorSet.setDuration(3000);
                atomPubProgressAnimatorSet.start();
            } else if (! atomPubProgressAnimatorSet.isStarted()) {
                atomPubProgressAnimatorSet.start();
            }
        }

        atomPubProgressHintView.setText(hint);
        atomPubProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void maskerHideProgressView() {
        atomPubProgressView.setVisibility(View.GONE);
        if (null != atomPubProgressAnimatorSet) {
            atomPubProgressAnimatorSet.cancel();
        }
    }

    @Override
    public void maskerShowMaskerLayout(String msg, int clickLabelRes) {
        atomPubMaskerContainer.setVisibility(View.VISIBLE);
        maskerHideProgressView();

        atomPubMaskerMsg.setText(msg);
        atomPubMaskerClickText.setTag(clickLabelRes);
        if (clickLabelRes > 0) {
            atomPubMaskerClickText.setVisibility(View.VISIBLE);
            atomPubMaskerClickText.setText(clickLabelRes);
            atomPubMaskerClickText.setOnClickListener(this);
        } else {
            atomPubMaskerClickText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void maskerHideMaskerLayout() {
        atomPubMaskerContainer.setVisibility(View.GONE);
    }
}
