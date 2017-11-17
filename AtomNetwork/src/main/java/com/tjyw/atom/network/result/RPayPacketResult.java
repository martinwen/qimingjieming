package com.tjyw.atom.network.result;

import com.tjyw.atom.network.model.PayCoupon;
import com.tjyw.atom.network.utils.ArrayUtil;

import java.util.List;

/**
 * Created by stephen on 17-11-17.
 */
public class RPayPacketResult implements RetroResultItem {

    private static final long serialVersionUID = 8821030539753992859L;

    public int totalCount = -1;

    public int count = -1;

    public List<PayCoupon> list;

    public PayCoupon getNewPacket(int position) {
        return null == list ? null : list.get(position);
    }

    public int sizeOfNewPacket() {
        return null == list ? 0 : list.size();
    }

    public void getPayPacketList(List<PayCoupon> list) {
        if (null != list) {
            if (! ArrayUtil.isEmpty(this.list)) {
                PayCoupon newPacket = this.list.get(0);
                if (null != newPacket) {
                    list.add(newPacket);
                }
            }
        }
    }
}
