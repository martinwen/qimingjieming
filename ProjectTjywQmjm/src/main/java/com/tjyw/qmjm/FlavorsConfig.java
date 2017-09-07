package com.tjyw.qmjm;

import android.content.Context;

import com.tjyw.atom.network.NetworkFlavorsConfig;
import com.tjyw.atom.network.conf.IChannel;
import com.tjyw.atom.network.utils.ChannelUtil;

/**
 * Created by stephen on 14/08/2017.
 */
public class FlavorsConfig {

    public static class NetworkBuildConfig implements NetworkFlavorsConfig {

        @Override
        public String getCid() {
            return BuildConfig.CLIENT_C_ID;
        }

        @Override
        public String getCName() {
            return BuildConfig.CLIENT_C_NAME;
        }

        @Override
        public String getPid() {
            return BuildConfig.CLIENT_P_ID;
        }
    }

    public static class NetworkChannelConfig implements NetworkFlavorsConfig {

        protected ChannelUtil.ChannelObject channelObject;

        public NetworkChannelConfig(Context context) {
            channelObject = ChannelUtil.getChannel(context);
        }

        @Override
        public String getCid() {
            return null == channelObject ? IChannel.C1000.CID : channelObject.cid;
        }

        @Override
        public String getCName() {
            return null == channelObject ? IChannel.C1000.NAME : channelObject.cname;
        }

        @Override
        public String getPid() {
            return null == channelObject ? ChannelUtil.PID : channelObject.pid;
        }
    }
}
