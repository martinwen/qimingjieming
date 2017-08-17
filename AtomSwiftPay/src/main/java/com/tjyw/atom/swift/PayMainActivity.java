//package com.tjyw.atom.swift;
//
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.swiftfintech.pay.MainApplication;
//import com.swiftfintech.pay.activity.PayPlugin;
//import com.swiftfintech.pay.bean.RequestMsg;
//import com.swiftfintech.pay.utils.MD5;
//import com.swiftfintech.pay.utils.SignUtils;
//import com.swiftfintech.pay.utils.Util;
//import com.swiftfintech.pay.utils.XmlUtils;
//import com.swiftpass.cn.pay.test.R;
//
//public class PayMainActivity extends Activity {
//    String TAG = "PayMainActivity";
//
//    private EditText money, body, mchId, notifyUrl, orderNo, signKey, appId, seller_id, credit_pay;
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//    }
//
//    public Handler handler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                //                case PayHandlerManager.PAY_H5_FAILED: //失败，原因如有（商户未开通[pay.weixin.wappay]支付类型）等
//                //                    Log.i(TAG, "" + msg.obj);
//                //                    break;
//                //                case PayHandlerManager.PAY_H5_SUCCES: //成功
//                //                    Log.i(TAG, "" + msg.obj);
//                //                    break;
//
//                default:
//                    break;
//            }
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_main);
//
//        //注册handler，接受调wftsdk通知消息。
//        //        PayHandlerManager.registerHandler(PayHandlerManager.PAY_H5_RESULT, handler);
//
//        Button button = (Button) findViewById(R.id.submitPay);
//
//        money = (EditText) findViewById(R.id.money);
//
//        body = (EditText) findViewById(R.id.body);
//
//        mchId = (EditText) findViewById(R.id.mchId);
//
//        notifyUrl = (EditText) findViewById(R.id.notifyUrl);
//
//        orderNo = (EditText) findViewById(R.id.orderNo);
//
//        signKey = (EditText) findViewById(R.id.signKey);
//
//        //appId = (EditText)findViewById(R.id.appId);
//
//        seller_id = (EditText) findViewById(R.id.seller_id);
//
//        credit_pay = (EditText) findViewById(R.id.credit_pay);
//
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                new GetPrepayIdTask().execute();
//
//                //                Intent intent = new Intent("cn.swiftpass.wxspay.PayMainActivity");
//                //                PayMainActivity.this.startActivity(intent);
//            }
//        });
//    }
//
//    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {
//
//        private ProgressDialog dialog;
//
//        private String accessToken;
//
//        public GetPrepayIdTask(String accessToken) {
//            this.accessToken = accessToken;
//        }
//
//        public GetPrepayIdTask() {
//        }
//
//        @Override
//        protected void onPreExecute() {
//            dialog =
//                    ProgressDialog.show(PayMainActivity.this,
//                            getString(R.string.app_tip),
//                            getString(R.string.getting_prepayid));
//        }
//
//        @Override
//        protected void onPostExecute(Map<String, String> result) {
//            if (dialog != null) {
//                dialog.dismiss();
//            }
//            if (result == null) {
//                Toast.makeText(PayMainActivity.this, getString(R.string.get_prepayid_fail), Toast.LENGTH_LONG).show();
//            } else {
//                if (result.get("status").equalsIgnoreCase("0")) // 成功
//                {
//
//                    Toast.makeText(PayMainActivity.this, R.string.get_prepayid_succ, Toast.LENGTH_LONG).show();
//                    RequestMsg msg = new RequestMsg();
//                    msg.setTokenId(result.get("token_id"));
//                    //msg.setTokenId("1b1bcb6a0beba1cdf2c0e2c45efee4ea1");
//                    // 微信wap支付
//                    //msg.setSchemeUri("payscheme://payResult:8888");
//                    msg.setTradeType(MainApplication.PAY_WX_WAP);
//                    PayPlugin.unifiedH5Pay(PayMainActivity.this, msg);
//
//                    // QQwap支付
//                    //                    msg.setTradeType(MainApplication.PAY_QQ_WAP);
//                    //                    PayPlugin.unifiedH5Pay(PayMainActivity.this, msg);
//                    //
//                } else {
//                    Toast.makeText(PayMainActivity.this, getString(R.string.get_prepayid_fail), Toast.LENGTH_LONG)
//                            .show();
//                }
//
//            }
//
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//
//        @Override
//        protected Map<String, String> doInBackground(Void... params) {
//            // 统一预下单接口
//            String url = "https://pay.swiftpass.cn/pay/gateway";
//
//            String entity = getParams();
//
//            Log.d(TAG, "doInBackground, url = " + url);
//            Log.d(TAG, "doInBackground, entity = " + entity);
//
//            byte[] buf = Util.httpPost(url, entity);
//            if (buf == null || buf.length == 0) {
//                return null;
//            }
//            String content = new String(buf);
//            Log.d(TAG, "doInBackground, content = " + content);
//            try {
//                return XmlUtils.parse(content);
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return null;
//            }
//        }
//    }
//
//    private String genNonceStr() {
//        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
//    }
//
//    private String genOutTradNo() {
//        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
//        Date date = new Date();
//        String key = format.format(date);
//
//        java.util.Random r = new java.util.Random();
//        key += r.nextInt();
//        key = key.substring(0, 15);
//        return key;
//    }
//
//    String payOrderNo;
//
//    /**
//     * 组装参数
//     * <功能详细描述>
//     *
//     * @return
//     * @see [类、类#方法、类#成员]
//     */
//    private String getParams() {
//
//        Map<String, String> params = new HashMap<String, String>();
//        //注意：device_info、mch_app_name、mch_app_id这三个具体传值必须以文档说明为准，传真实有效的，否则有可能无法正常支付！！！
//        params.put("device_info", "AND_SDK");
//        params.put("mch_app_name", "王者荣耀");
//        params.put("mch_app_id", "com.tencent.tmgp.sgame");
//        params.put("body", "SPay收款"); // 商品名称
//        params.put("service", "unified.trade.pay"); // 支付类型
//        params.put("version", "2.0"); // 版本
//        params.put("mch_id", "7552900037"); // 威富通商户号
//        //        params.put("mch_id", mchId.getText().toString()); // 威富通商户号
//        params.put("notify_url", "http://zhifu.dev.swiftpass.cn/spay/notify"); // 后台通知url
//        params.put("nonce_str", genNonceStr()); // 随机数
//        payOrderNo = genOutTradNo();
//        params.put("out_trade_no", payOrderNo); //订单号
//        params.put("mch_create_ip", "127.0.0.1"); // 机器ip地址
//        params.put("total_fee", money.getText().toString()); // 总金额
//        params.put("limit_credit_pay", credit_pay.getText().toString()); // 是否限制信用卡支付， 0：不限制（默认），1：限制
//        String sign = createSign("11f4aca52cf400263fdd8faf7a69e007", params); // 密钥
//
//        params.put("sign", sign); // sign签名
//
//        return XmlUtils.toXml(params);
//    }
//
//    public String createSign(String signKey, Map<String, String> params) {
//        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
//        SignUtils.buildPayParams(buf, params, false);
//        buf.append("&key=").append(signKey);
//        String preStr = buf.toString();
//        String sign = "";
//        // 获得签名验证结果
//        try {
//            sign = MD5.md5s(preStr).toUpperCase();
//        } catch (Exception e) {
//            sign = MD5.md5s(preStr).toUpperCase();
//        }
//        return sign;
//    }
//
//    public void buildPayParams(StringBuilder sb, Map<String, String> payParams, boolean encoding) {
//        List<String> keys = new ArrayList<String>(payParams.keySet());
//        Collections.sort(keys);
//        for (String key : keys) {
//            sb.append(key).append("=");
//            if (encoding) {
//                sb.append(urlEncode(payParams.get(key)));
//            } else {
//                sb.append(payParams.get(key));
//            }
//            sb.append("&");
//        }
//        sb.setLength(sb.length() - 1);
//    }
//
//    public String urlEncode(String str) {
//        try {
//            return URLEncoder.encode(str, "UTF-8");
//        } catch (Throwable e) {
//            return str;
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (data == null) {
//            return;
//        }
//
//        String respCode = data.getExtras().getString("resultCode");
//        if (!TextUtils.isEmpty(respCode) && respCode.equalsIgnoreCase("success")) {
//            //标示支付成功
//            Toast.makeText(PayMainActivity.this, "支付成功", Toast.LENGTH_LONG).show();
//        } else { //其他状态NOPAY状态：取消支付，未支付等状态
//            Toast.makeText(PayMainActivity.this, "未支付", Toast.LENGTH_LONG).show();
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }
//}