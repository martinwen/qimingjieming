package com.tjyw.atom.network.model;

import android.content.Context;
import android.text.TextUtils;

import com.brianjmelton.stanley.ProxyGenerator;
import com.tjyw.atom.network.interfaces.IPrefClient;
import com.tjyw.atom.network.result.RetroResultItem;
import com.tjyw.atom.network.utils.JsonUtil;

/**
 * Created by stephen on 27/08/2017.
 */
public class ClientInit implements RetroResultItem {

    private static final long serialVersionUID = 6514460125172350058L;

    public static ClientInit getInstance(Context context) {
        IPrefClient client = new ProxyGenerator().create(context, IPrefClient.class);
        if (null != client) {
            ClientInit clientInit = JsonUtil.getInstance().parseObject(client.getClientInit(), ClientInit.class);
            if (null != clientInit) {
                return clientInit;
            }
        }

        return new ClientInit();
    }

    public static String getPayButtonText(Context context, String _default) {
        ClientInit clientInit = getInstance(context);
        if (null == clientInit || TextUtils.isEmpty(clientInit.payButtonText)) {
            return _default;
        } else {
            return clientInit.payButtonText;
        }
    }

    public String baijiaxing;

    public String jiemeng;

    public String quantangshi;

    public String shengxiao;

    public String yuer;

    public String yunqibaodian;

    public String about;

    public String feedback;

    public boolean listVip; // 是否在起名页显示套餐按钮

    public String listVipImageUrl;

    public String red_image_link; // 新手红包背景图地址

    public int red_packet_id; // 新手红包ID参数

    public String payButtonText; // 立即解锁

    public boolean saveInstance(Context context) {
        IPrefClient client = new ProxyGenerator().create(context, IPrefClient.class);
        if (null != client) {
            client.setClientInit(JsonUtil.getInstance().toJsonString(this));
            return true;
        } else {
            return false;
        }
    }
}
