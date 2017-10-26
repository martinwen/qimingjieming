package atom.pub;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import timber.log.Timber;

/**
 * Created by stephen on 16-12-19.
 */
public class AtomPubLocationHolder implements BDLocationListener {

    static final String TAG = AtomPubLocationHolder.class.getSimpleName();

    protected static final AtomPubLocationHolder manager = new AtomPubLocationHolder();

    public static AtomPubLocationHolder getManager() {
        return manager;
    }

    protected LocationClient locationClient;

    protected BDLocation bdLocation;

    AtomPubLocationHolder() {

    }

    public void start(Context context) {
        if (null == locationClient) {
            locationClient = new LocationClient(context);
            locationClient.registerLocationListener(this);
            locationClient.setLocOption(getClientOption());
        }

        locationClient.start();
    }

    public String getCity() {
        return null == bdLocation ? null : bdLocation.getCity();
    }

    public String getProvince() {
        return null == bdLocation ? null : bdLocation.getProvince();
    }

    protected LocationClientOption getClientOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy); // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll"); // 可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(1000 * 60); // 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true); // 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true); // 可选，默认false,设置是否使用gps
        option.setLocationNotify(false); // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true); // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true); // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false); // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false); // 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false); // 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        return option;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
        if (null != bdLocation) {
            switch (bdLocation.getLocType()) {
                case BDLocation.TypeGpsLocation:
                    Timber.tag(TAG).e("%s::Location Success::BDLocation.TypeGpsLocation::[%s, %s]", TAG, bdLocation.getProvince(), bdLocation.getCity());
                    break ;
                case BDLocation.TypeNetWorkLocation:
                    Timber.tag(TAG).e("%s::Location Success::BDLocation.TypeNetWorkLocation::[%s, %s]", TAG, bdLocation.getProvince(), bdLocation.getCity());
                    break ;
                case BDLocation.TypeOffLineLocation:
                    Timber.tag(TAG).e("%s::Location Success::BDLocation.TypeOffLineLocation::[%s, %s]", TAG, bdLocation.getProvince(), bdLocation.getCity());
                    break ;
                case BDLocation.TypeServerError:
                case BDLocation.TypeNetWorkException:
                case BDLocation.TypeCriteriaException:
                    Timber.tag(TAG).e("%s::Location Fail::%d", TAG, bdLocation.getLocType());
            }
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }
}
