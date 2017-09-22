package com.tjyw.qmjm.holder;

import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.model.NameZodiac;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.inject.Injector;
import com.tjyw.qmjm.R;

/**
 * Created by stephen on 17-9-22.
 */
public class YunShiSheetHolder {

    @From(R.id.yunShiShiYe)
    protected TextView yunShiShiYe;

    @From(R.id.yunShiXueYe)
    protected TextView yunShiXueYe;

    @From(R.id.yunShiCaiYun)
    protected TextView yunShiCaiYun;

    @From(R.id.yunShiAiQing)
    protected TextView yunShiAiQing;

    public YunShiSheetHolder(View view) {
        Injector.inject(this, view);
    }

    public void sheet(NameZodiac nameZodiac) {
        yunShiShiYe.setText(nameZodiac.shiye);
        yunShiXueYe.setText(nameZodiac.xueye);
        yunShiCaiYun.setText(nameZodiac.caiyun);
        yunShiAiQing.setText(nameZodiac.aiqing);
    }
}
