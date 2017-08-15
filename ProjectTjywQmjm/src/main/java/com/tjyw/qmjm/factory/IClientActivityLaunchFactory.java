package com.tjyw.qmjm.factory;

import android.content.Intent;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.qmjm.activity.BaseActivity;
import com.tjyw.qmjm.activity.ClientMasterActivity;
import com.tjyw.qmjm.activity.ExplainMasterActivity;
import com.tjyw.qmjm.activity.NamingListActivity;

import java.util.List;

/**
 * Created by stephen on 07/08/2017.
 */
public class IClientActivityLaunchFactory {

    public static void launchClientMasterActivity(BaseActivity context) {
        Intent intent = new Intent(context, ClientMasterActivity.class);

        context.startActivity(intent);
    }

    public static void launchExplainMasterActivity(BaseActivity context, String surname, String name) {
        Intent intent = new Intent(context, ExplainMasterActivity.class);
        intent.putExtra(IApiField.S.surname, surname);
        intent.putExtra(IApiField.N.name, name);
        context.startActivity(intent);
    }

    public static void launchNamingListActivity(BaseActivity context, String surname, int gender, int nameCount) {
        Intent intent = new Intent(context, NamingListActivity.class);
        intent.putExtra(IApiField.S.surname, surname);
        intent.putExtra(IApiField.G.gender, gender);
        intent.putExtra(IApiField.N.nameNumber, nameCount);
        context.startActivity(intent);
    }
}
