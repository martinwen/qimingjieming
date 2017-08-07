package com.tjyw.qmjm.factory;

import android.content.Intent;

import com.tjyw.qmjm.activity.BaseActivity;
import com.tjyw.qmjm.activity.ClientMasterActivity;

/**
 * Created by stephen on 07/08/2017.
 */
public class IClientActivityLaunchFactory {

    public static void launchClientMasterActivity(BaseActivity context) {
        Intent intent = new Intent(context, ClientMasterActivity.class);

        context.startActivity(intent);
    }
}
