package com.tjyw.qmjm.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.inject.Injector;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.activity.BaseActivity;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;

/**
 * Created by stephen on 17-8-17.
 */
public class NamingPayWindows extends DialogFragment implements View.OnClickListener {

    public static NamingPayWindows newInstance(FragmentManager manager, RNameDefinition.Param param) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IApiField.P.param, param);

        NamingPayWindows windows = new NamingPayWindows();
        windows.setArguments(bundle);
        windows.show(manager, NamingPayWindows.class.getName());
        return windows;
    }

    @From(R.id.atom_pub_resIdsOK)
    protected TextView atom_pub_resIdsOK;

    protected RNameDefinition.Param param;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AtomPubStyle_Translucent_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.atom_naming_pay, container, true);
        Injector.inject(this, convertView);

        atom_pub_resIdsOK.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null != getArguments()) {
            param = (RNameDefinition.Param) getArguments().getSerializable(IApiField.P.param);
        }

        if (null == param) {
            dismissAllowingStateLoss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.atom_pub_resIdsOK:
                dismissAllowingStateLoss();
                if (null != param) {
                    IClientActivityLaunchFactory.launchPayOrderActivity((BaseActivity) getActivity(), param);
                }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
