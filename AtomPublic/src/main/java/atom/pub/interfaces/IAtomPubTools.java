package atom.pub.interfaces;

import android.support.annotation.StringRes;

/**
 * Created by stephen on 7/7/16.
 */
public interface IAtomPubTools {

    void showToast(@StringRes int id);

    void showToast(String message);

    void pHideSoftInput();

    void pShowSoftInput();
}
