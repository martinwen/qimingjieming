package com.tjyw.qmjm.factory;

import android.content.Intent;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.qmjm.activity.BaseActivity;
import com.tjyw.qmjm.activity.ClientMasterActivity;
import com.tjyw.qmjm.activity.ExplainMasterActivity;
import com.tjyw.qmjm.activity.NamingListActivity;
import com.tjyw.qmjm.activity.PayOrderActivity;
import com.tjyw.qmjm.activity.PayOrderListActivity;
import com.tjyw.qmjm.activity.UserSignInActivity;

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

    public static void launchExplainMasterActivity(BaseActivity context, String surname, String name, String day, int gender) {
        Intent intent = new Intent(context, ExplainMasterActivity.class);
        intent.putExtra(IApiField.S.surname, surname);
        intent.putExtra(IApiField.N.name, name);
        intent.putExtra(IApiField.D.day, day);
        intent.putExtra(IApiField.G.gender, gender);
        context.startActivity(intent);
    }

    public static void launchNamingListActivity(BaseActivity context, String surname, String day, int gender, int nameNumber) {
        Intent intent = new Intent(context, NamingListActivity.class);
        intent.putExtra(IApiField.S.surname, surname);
        intent.putExtra(IApiField.D.day, day);
        intent.putExtra(IApiField.G.gender, gender);
        intent.putExtra(IApiField.N.nameNumber, nameNumber);
        context.startActivity(intent);
    }

    public static void launchPayOrderActivity(BaseActivity context) {
        Intent intent = new Intent(context, PayOrderActivity.class);
        context.startActivity(intent);
    }

    public static void launchPayOrderListActivity(BaseActivity context) {
        Intent intent = new Intent(context, PayOrderListActivity.class);
        context.startActivity(intent);
    }

    public static void launchUserSignInActivity(BaseActivity context) {
        Intent intent = new Intent(context, UserSignInActivity.class);
        context.startActivity(intent);
    }
}
