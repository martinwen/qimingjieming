package com.tjyw.atom.pub.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tjyw.atom.pub.inject.Injector;

/**
 * Created by stephen on 11/08/2017.
 */
public abstract class AtomPubFastAdapterItemHolder<Item> extends RecyclerView.ViewHolder {

    public AtomPubFastAdapterItemHolder(View itemView) {
        super(itemView);
        Injector.inject(this, itemView);
    }

    public abstract void onBindView(Context context, Item item);
}
