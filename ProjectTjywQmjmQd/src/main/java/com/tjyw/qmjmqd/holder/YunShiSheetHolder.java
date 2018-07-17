package com.tjyw.qmjmqd.holder;

import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.model.NameZodiac;
import atom.pub.inject.From;
import atom.pub.inject.Injector;
import com.tjyw.qmjmqd.R;

/**
 * Created by stephen on 17-9-22.
 */
public class YunShiSheetHolder {

    @From(value = R.id.yunShiShiYe, canBeNull = true)
    protected TextView yunShiShiYe;

    @From(value = R.id.yunShiXueYe, canBeNull = true)
    protected TextView yunShiXueYe;

    @From(value = R.id.yunShiCaiYun, canBeNull = true)
    protected TextView yunShiCaiYun;

    @From(value = R.id.yunShiAiQing, canBeNull = true)
    protected TextView yunShiAiQing;

    public YunShiSheetHolder(View view) {
        Injector.inject(this, view);
    }

    public void sheet(NameZodiac nameZodiac) {
        if (null != yunShiShiYe) {
            yunShiShiYe.setText(nameZodiac.shiye);
        }

        if (null != yunShiXueYe) {
            yunShiXueYe.setText(nameZodiac.xueye);
        }

        if (null != yunShiCaiYun) {
            yunShiCaiYun.setText(nameZodiac.caiyun);
        }

        if (null != yunShiAiQing) {
            yunShiAiQing.setText(nameZodiac.aiqing);
        }
    }
}
