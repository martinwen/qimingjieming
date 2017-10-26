package atom.pub;

import android.graphics.Point;
import android.view.Display;

/**
 * Created by stephen on 07/08/2017.
 */
public class AtomPubDisplayConfigure {

    public static AtomPubDisplayConfigure instance = new AtomPubDisplayConfigure();

    public static AtomPubDisplayConfigure getInstance() {
        return instance;
    }

    protected int displayScreenWidth = 720;

    protected int displayScreenHeight = 1080;

    public void pSetScreenSize(Display display) {
        if (null != display) {
            Point point = new Point();
            display.getSize(point);

            this.displayScreenWidth = point.x;
            this.displayScreenHeight = point.y;
        }
    }
}
