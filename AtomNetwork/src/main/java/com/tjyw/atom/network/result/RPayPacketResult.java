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

    public List<PayCoupon> newList;

    public PayCoupon getNewPacket(int position) {
        return null == newList ? null : newList.get(position);
    }

    public int sizeOfNewPacket() {
        return null == newList ? 0 : newList.size();
    }

    public void getPayPacketList(List<PayCoupon> list) {
        if (null != list) {
            if (! ArrayUtil.isEmpty(newList)) {
                PayCoupon newPacket = newList.get(0);
                if (null != newPacket) {
                    list.add(newPacket);
                }
            }
        }
    }
}
