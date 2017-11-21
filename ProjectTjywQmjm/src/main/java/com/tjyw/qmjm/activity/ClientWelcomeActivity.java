package com.tjyw.qmjm.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.ImageView;

import com.brianjmelton.stanley.ProxyGenerator;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.interfaces.IPrefUser;
import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.model.UserInfo;
import com.tjyw.atom.network.presenter.UserPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiClientPostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiUserPostListener;
import com.tjyw.atom.network.result.RUserRegister;
import com.tjyw.atom.network.utils.JsonUtil;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.adapter.ClientMasterAdapter;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.concurrent.TimeUnit;

import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by stephen on 17-8-1.
 */
@RequiresPresenter(UserPresenter.class)
public class ClientWelcomeActivity extends BaseActivity<UserPresenter<ClientWelcomeActivity>> implements
        OnApiPostErrorListener,
        OnApiClientPostListener.PostClientInitListener,
        OnApiUserPostListener.PostUserRegisterListener {

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
            getPresenter().postClientInit();
        } else { // 没有用户信息时，先请求register接口创建用户，再访问init接口初始化
            getPresenter().postNewUserRegister();
        }
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {
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

    @Override
    public void postOnClientInitSuccess(ClientInit clientInit) {
        clientInit.saveInstance(getApplicationContext());
        requestPhoneStatePermission();
    }

    @Override
    public void postOnUserRegisterSuccess(RUserRegister result) {
        if (null != result.user) {
            saveUserInfo(result.user);
        }

        if (null != result.init) {
            result.init.saveInstance(getApplicationContext());
        }

        Observable.timer(1, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<Long>io_main())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        requestPhoneStatePermission();
                    }
                });
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
        RxPermissions permissions = null;
        if (isFinishing()) {
            return ;
        } else {
            try {
                permissions = new RxPermissions(this);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        if (null == permissions) {
            return ;
        }

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
