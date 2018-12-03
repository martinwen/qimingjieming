package com.tjyw.qmjmqd.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tjyw.qmjmqd.R;

import static com.tjyw.qmjmqd.Constans.*;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    public static final int WXPAY_SUCCESS_CODE = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpayentry);

        api = WXAPIFactory.createWXAPI(this, WX_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case 0://成功

                    break;
                case -1://错误

                    break;
                case -2://用户取消

                    break;
            }
            Intent intent = new Intent();
            intent.setAction(PAY_BROADCASTRECEIVER);
            intent.putExtra(PAY_ORDER_STATUS, resp.errCode);
            this.sendBroadcast(intent);
            finish();
        }
    }
}