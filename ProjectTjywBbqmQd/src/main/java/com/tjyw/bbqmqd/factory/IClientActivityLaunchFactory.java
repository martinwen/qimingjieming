package com.tjyw.bbqmqd.factory;

import android.content.Intent;
import android.support.annotation.StringRes;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.conf.ICode;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.bbqmqd.ClientQmjmApplication;
import com.tjyw.bbqmqd.activity.BaseActivity;
import com.tjyw.bbqmqd.activity.ClientMasterActivity;
import com.tjyw.bbqmqd.activity.ClientTouchActivity;
import com.tjyw.bbqmqd.activity.ExplainMasterActivity;
import com.tjyw.bbqmqd.activity.NameMasterActivity;
import com.tjyw.bbqmqd.activity.NamingListActivity;
import com.tjyw.bbqmqd.activity.PayCouponListActivity;
import com.tjyw.bbqmqd.activity.PayOrderActivity;
import com.tjyw.bbqmqd.activity.PayOrderListActivity;
import com.tjyw.bbqmqd.activity.PayPackageActivity;
import com.tjyw.bbqmqd.activity.UserFavoriteListActivity;
import com.tjyw.bbqmqd.activity.UserSignInActivity;
import com.tjyw.bbqmqd.fragment.BaseFragment;

import atom.pub.fragment.AtomPubBaseFragment;

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

    public static void launchNameMasterActivity(BaseActivity context, ListRequestParam listRequestParam, int delayed, int position) {
        Intent intent = new Intent(context, NameMasterActivity.class);
        intent.putExtra(IApiField.P.param, listRequestParam);
        intent.putExtra(IApiField.D.delayed, delayed);
        intent.putExtra(IApiField.T.t, position);
        context.startActivity(intent);
    }

    public static void launchNamingListActivity(BaseActivity context, ListRequestParam listRequestParam) {
        Intent intent = new Intent(context, NamingListActivity.class);
        intent.putExtra(IApiField.P.param, listRequestParam);
        context.startActivity(intent);
    }

    public static void launchPayPackageActivity(BaseActivity context, ListRequestParam listRequestParam) {
        Intent intent = new Intent(context, PayPackageActivity.class);
        intent.putExtra(IApiField.P.param, listRequestParam);
        context.startActivity(intent);
    }

    public static void launchPayOrderActivity(BaseActivity context, ListRequestParam param, PayService payService) {
        Intent intent = new Intent(context, PayOrderActivity.class);
        intent.putExtra(IApiField.P.param, param);
        intent.putExtra(IApiField.P.payService, payService);
        context.startActivityForResult(intent, ICode.SECTION.PAY);
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

    public static void launchPayCouponListActivity(BaseFragment context) {
        Intent intent = new Intent(context.getActivity(), PayCouponListActivity.class);
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
