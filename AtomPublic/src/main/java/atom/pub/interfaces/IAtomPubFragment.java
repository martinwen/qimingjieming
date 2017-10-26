package atom.pub.interfaces;

import android.support.v4.app.Fragment;

/**
 * Created by stephen on 17-8-1.
 */
public interface IAtomPubFragment {

    void pShowFragment(Fragment... fragments);

    void pHideFragment(Fragment... fragments);

    void pShowFragment(int enter, int exit, Fragment... fragments);

    void pHideFragment(int enter, int exit, Fragment... fragments);
}
