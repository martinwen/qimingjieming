package com.tjyw.bbqm.factory;

import android.content.Intent;
import android.support.annotation.StringRes;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.conf.ICode;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import atom.pub.fragment.AtomPubBaseFragment;
import com.tjyw.bbqm.ClientQmjmApplication;
import com.tjyw.bbqm.activity.BaseActivity;
import com.tjyw.bbqm.activity.ClientMasterActivity;
import com.tjyw.bbqm.activity.ClientTouchActivity;
import com.tjyw.bbqm.activity.ExplainMasterActivity;
import com.tjyw.bbqm.activity.NameMasterActivity;
import com.tjyw.bbqm.activity.NamingListActivity;
import com.tjyw.bbqm.activity.PayOrderActivity;
import com.tjyw.bbqm.activity.PayOrderListActivity;
import com.tjyw.bbqm.activity.UserFavoriteListActivity;
import com.tjyw.bbqm.activity.UserSignInActivity;
import com.tjyw.bbqm.fragment.BaseFragment;

/**
 * Created by stephen on 07/08/2017.
 */
public class IClientActivityLaunchFactory {

    public static void launchClientMasterActivity(BaseActivity context, int position, boolean clear) {
        Intent intent = new Intent(context, ClientMasterActivity.class);
        intent.putExtra(IApiField.T.t, position);
        if (clear) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        context.startActivity(intent);
    }

    public static void launchExplainMasterActivity(BaseActivity context, ListRequestParam listRequestParam, int delayed) {
        Intent intent = new Intent(context, ExplainMasterActivity.class);
        intent.putExtra(IApiField.P.param, listRequestParam);
        intent.putExtra(IApiField.D.delayed, delayed);
        context.startActivity(intent);
    }

    public static void launchExplainMasterActivity(BaseFragment context, ListRequestParam listRequestParam, int delayed) {
        Intent intent = new Intent(context.getActivity(), ExplainMasterActivity.class);
        intent.putExtra(IApiField.P.param, listRequestParam);
        intent.putExtra(IApiField.D.delayed, delayed);
        context.startActivity(intent);
    }

    public static void launchNameMasterActivity(BaseActivity context, ListRequestParam listRequestParam, int delayed) {
        Intent intent = new Intent(context, NameMasterActivity.class);
        intent.putExtra(IApiField.P.param, listRequestParam);
        intent.putExtra(IApiField.D.delayed, delayed);
        context.startActivity(intent);
    }

    public static void launchNamingListActivity(BaseActivity context, ListRequestParam listRequestParam) {
        Intent intent = new Intent(context, NamingListActivity.class);
        intent.putExtra(IApiField.P.param, listRequestParam);
        context.startActivity(intent);
    }

    public static void launchPayOrderActivity(BaseFragment context, ListRequestParam param, PayService payService) {
        Intent intent = new Intent(context.getActivity(), PayOrderActivity.class);
        intent.putExtra(IApiField.P.param, param);
        intent.putExtra(IApiField.P.payService, payService);
        context.startActivityForResult(intent, ICode.SECTION.PAY);
    }

    public static void launchPayOrderListActivity(BaseActivity context) {
        Intent intent = new Intent(context, PayOrderListActivity.class);
        context.startActivity(intent);
    }

    public static void launchPayOrderListActivity(BaseFragment context) {
        Intent intent = new Intent(context.getActivity(), PayOrderListActivity.class);
        context.startActivity(intent);
    }

    public static void launchUserSignInActivity(BaseActivity context) {
        Intent intent = new Intent(context, UserSignInActivity.class);
        context.startActivityForResult(intent, ICode.SECTION.SS);
    }

    public static void launchUserSignInActivity(AtomPubBaseFragment context) {
        Intent intent = new Intent(context.getActivity(), UserSignInActivity.class);
        context.startActivityForResult(intent, ICode.SECTION.SS);
    }

    public static void launchUserFavoriteListActivity(AtomPubBaseFragment context) {
        Intent intent = new Intent(context.getActivity(), UserFavoriteListActivity.class);
        context.startActivityForResult(intent, ICode.SECTION.SS);
    }

    public static void launchTouchActivity(AtomPubBaseFragment context, String touchUrl, @StringRes int title) {
        launchTouchActivity(context, touchUrl, ClientQmjmApplication.pGetString(title));
    }

    public static void launchTouchActivity(AtomPubBaseFragment context, String touchUrl, String title) {
        Intent intent = new Intent(context.getActivity(), ClientTouchActivity.class);
        intent.putExtra(IApiField.U.url, touchUrl);
        intent.putExtra(IApiField.T.title, title);
        context.startActivity(intent);
    }
}
