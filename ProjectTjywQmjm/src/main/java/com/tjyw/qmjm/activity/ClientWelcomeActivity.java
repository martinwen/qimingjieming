package com.tjyw.qmjm.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.ImageView;

import com.brianjmelton.stanley.ProxyGenerator;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tjyw.atom.network.RetroHttpMethods;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.interfaces.IPrefClient;
import com.tjyw.atom.network.interfaces.IPrefUser;
import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.model.UserInfo;
import com.tjyw.atom.network.presenter.UserPresenter;
import com.tjyw.atom.network.result.RetroResult;
import com.tjyw.atom.network.utils.JsonUtil;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.adapter.ClientMasterAdapter;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.concurrent.TimeUnit;

import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by stephen on 17-8-1.
 */
@RequiresPresenter(UserPresenter.class)
public class ClientWelcomeActivity extends BaseActivity<UserPresenter<ClientWelcomeActivity>> {

    static final int DELAY_TO_LAUNCH_MASTER = 2000;

    protected long createMillisTime;

    @From(R.id.welcomeSloganView)
    protected ImageView welcomeSloganView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.atom_master_welcome);
        immersionBarWith()
                .statusBarDarkFont(true)
                .init();

        createMillisTime = System.currentTimeMillis();
        IPrefUser user = new ProxyGenerator().create(getApplicationContext(), IPrefUser.class);
        if (null != user && ! TextUtils.isEmpty(user.getUserSession())) { // 判断本地是否有用户信息
            RetroHttpMethods.CLIENT().postClientInit(1) // 有用户信息时，只请求init接口初始化
                    .compose(RxSchedulersHelper.<RetroResult<ClientInit>>io_main())
                    .subscribe(new Action1<RetroResult<ClientInit>>() {
                        @Override
                        public void call(RetroResult<ClientInit> result) { // 成功后进入主界面
                            requestPhoneStatePermission();
                            IPrefClient client = new ProxyGenerator().create(getApplicationContext(), IPrefClient.class);
                            if (null != client) {
                                client.setClientInit(JsonUtil.getInstance().toJsonString(result.items));
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) { // 初始化接口失败不退出APP，也进入主界面
                            throwable.printStackTrace();
                            requestPhoneStatePermission();
                        }
                    });
        } else { // 没有用户信息时，先请求register接口创建用户，再访问init接口初始化
            Observable.merge(
                    RetroHttpMethods.USER().postUserRegister(1),
                    RetroHttpMethods.CLIENT().postClientInit(1)
            ).compose(RxSchedulersHelper.<RetroResult<?>>io_main())
                    .subscribe(new Action1<RetroResult<?>>() {
                        @Override
                        public void call(RetroResult<?> result) {
                            if (result.items instanceof UserInfo) {
                                saveUserInfo((UserInfo) result.items);
                            } else if (result.items instanceof ClientInit) {
                                IPrefClient client = new ProxyGenerator().create(getApplicationContext(), IPrefClient.class);
                                if (null != client) {
                                    ClientInit clientInit = (ClientInit) result.items;
                                    client.setClientInit(JsonUtil.getInstance().toJsonString(clientInit));
                                }
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) { // 请求失败时，用户信息如果已经存在，则进入主界面
                            throwable.printStackTrace();
                            IPrefUser user = new ProxyGenerator().create(getApplicationContext(), IPrefUser.class);
                            if (null != user && ! TextUtils.isEmpty(user.getUserSession())) {
                                requestPhoneStatePermission();
                            } else { // 否则说明注册接口出现异常，弹窗提示并退出应用
                                new AlertDialog.Builder(ClientWelcomeActivity.this)
                                        .setTitle(R.string.atom_pub_resStringTip)
                                        .setMessage(R.string.atom_pub_resStringNetworkBroken)
                                        .setPositiveButton(R.string.atom_pub_resStringOK, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                MobclickAgent.onKillProcess(getApplicationContext());
                                                finish();
                                            }
                                        }).show();
                            }
                        }
                    }, new Action0() {
                        @Override
                        public void call() { // 两个接口稳定请求结果回来后，进入主界面
                            Observable.timer(1, TimeUnit.SECONDS)
                                    .compose(RxSchedulersHelper.<Long>io_main())
                                    .subscribe(new Action1<Long>() {
                                        @Override
                                        public void call(Long aLong) {
                                            requestPhoneStatePermission();
                                        }
                                    });
                        }
                    });
        }
    }

    protected void saveUserInfo(UserInfo userInfo) {
        if (null != userInfo) {
            IPrefUser user = new ProxyGenerator().create(getApplicationContext(), IPrefUser.class);
            if (null != user) {
                user.setUserSession(userInfo.sessionKey);
                user.setUserInfo(JsonUtil.getInstance().toJsonString(userInfo));
            }
        }
    }

    protected void requestPhoneStatePermission() {
        RxPermissions permissions = new RxPermissions(this);
        if (permissions.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            launchMasterActivity();
        } else {
            permissions
                    .request(Manifest.permission.READ_PHONE_STATE)
                    .compose(RxSchedulersHelper.<Boolean>io_main())
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            launchMasterActivity();
                            if (! granted) {
                                showToast(R.string.atom_pub_resStringPermissionByUserDeny);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                            launchMasterActivity();
                            showToast(R.string.atom_pub_resStringPermissionByUserDeny);
                        }
                    });
        }
    }

    protected void launchMasterActivity() {
        createMillisTime = Math.abs(createMillisTime - System.currentTimeMillis());
        if (createMillisTime < DELAY_TO_LAUNCH_MASTER) {
            Observable.timer((DELAY_TO_LAUNCH_MASTER - createMillisTime), TimeUnit.MILLISECONDS)
                    .compose(RxSchedulersHelper.<Long>io_main())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            IClientActivityLaunchFactory.launchClientMasterActivity(ClientWelcomeActivity.this, ClientMasterAdapter.POSITION.NAMING, false);
                            finish();
                        }
                    });
        } else {
            IClientActivityLaunchFactory.launchClientMasterActivity(this, ClientMasterAdapter.POSITION.NAMING, false);
            finish();
        }
    }
}
