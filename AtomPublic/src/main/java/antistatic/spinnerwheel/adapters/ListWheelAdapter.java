package antistatic.spinnerwheel.adapters;

import android.content.Context;

import atom.pub.R;

import java.util.List;

/**
 * Created by stephen on 19/08/2017.
 */
public class ListWheelAdapter<T> extends AbstractWheelTextAdapter {

    protected List<T> items;

    public ListWheelAdapter(Context context, List<T> items) {
        super(context, R.layout.atom_pub_wheel_item, R.id.tv_name);
        this.items = items;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.size()) {
            T item = items.get(index);
            if (null == item) {
                return null;
            } else {
                return item.toString();
            }
        }
        return null;
    }

    public T get(int position) {
        return null == items ? null : items.get(position);
    }

    @Override
    public int getItemsCount() {
        return null == items ? 0 : items.size();
    }
}
