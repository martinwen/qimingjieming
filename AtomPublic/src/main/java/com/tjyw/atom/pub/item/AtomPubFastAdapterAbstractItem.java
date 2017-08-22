package com.tjyw.atom.pub.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mikepenz.fastadapter.IClickable;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.tjyw.atom.pub.inject.Injector;

/**
 * Created by stephen on 11/08/2017.
 */
public abstract class AtomPubFastAdapterAbstractItem<T, Item extends IItem & IClickable, VH extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder> extends AbstractItem<Item, VH> {

    public T src;

    public AtomPubFastAdapterAbstractItem(T src) {
        this.src = src;
    }

    public static abstract class AtomPubFastAdapterItemHolder<T> extends RecyclerView.ViewHolder {

        public AtomPubFastAdapterItemHolder(View itemView) {
            super(itemView);
            Injector.inject(this, itemView);
        }

        public abstract void onBindView(Context context, T t);
    }
}
