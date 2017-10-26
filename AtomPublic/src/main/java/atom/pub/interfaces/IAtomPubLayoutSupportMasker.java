package atom.pub.interfaces;

import android.support.annotation.StringRes;
import android.view.View;

/**
 * Created by stephen on 16-7-20.
 */
public interface IAtomPubLayoutSupportMasker {

    interface OnMaskerClickListener {

        void maskerOnClick(View view, int clickLabelRes);
    }

    void maskerShowProgressView(boolean isAlpha);

    void maskerShowProgressView(boolean isAlpha, boolean anim);

    void maskerShowProgressView(boolean isAlpha, boolean anim, String hint);

    void maskerHideProgressView();

    void maskerShowMaskerLayout(String msg, @StringRes int clickLabelRes);

    void maskerHideMaskerLayout();
}
