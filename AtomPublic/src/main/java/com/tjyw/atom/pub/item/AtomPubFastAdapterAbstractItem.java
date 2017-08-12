package com.tjyw.atom.pub.item;

import android.support.v7.widget.RecyclerView;

import com.mikepenz.fastadapter.IClickable;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.items.AbstractItem;

/**
 * Created by stephen on 11/08/2017.
 */
public abstract class AtomPubFastAdapterAbstractItem<T, Item extends IItem & IClickable, VH extends RecyclerView.ViewHolder> extends AbstractItem<Item, VH> {

    protected T src;

    public AtomPubFastAdapterAbstractItem(T src) {
        this.src = src;
    }

}
