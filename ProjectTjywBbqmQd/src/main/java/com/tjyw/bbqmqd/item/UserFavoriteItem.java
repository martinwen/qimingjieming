package com.tjyw.bbqmqd.item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.model.Favorite;
import com.tjyw.atom.network.model.NameCharacter;
import com.tjyw.atom.network.utils.ArrayUtil;
import atom.pub.inject.From;
import atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.bbqmqd.ClientQmjmApplication;
import com.tjyw.bbqmqd.R;
import com.tjyw.bbqmqd.holder.HeaderWordHolder;

import java.util.List;

/**
 * Created by stephen on 27/08/2017.
 */
public class UserFavoriteItem extends AtomPubFastAdapterAbstractItem<Favorite, UserFavoriteItem, UserFavoriteItem.UserFavoriteHolder> {

    public UserFavoriteItem(Favorite src) {
        super(src);
    }

    @Override
    public UserFavoriteHolder getViewHolder(View v) {
        return new UserFavoriteHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_UserFavoriteItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_naming_word_body;
    }

    @Override
    public void bindView(UserFavoriteHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    public static class UserFavoriteHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<Favorite> {

        @From(R.id.nameWordContainer)
        protected ViewGroup nameWordContainer;

        @From(R.id.nameWordCollect)
        protected TextView nameWordCollect;

        public UserFavoriteHolder(View itemView) {
            super(itemView);

            nameWordCollect.setSelected(true);
            nameWordCollect.setText(R.string.atom_pub_resStringNamingFavorited);
        }

        @Override
        public void onBindView(Context context, Favorite favorite) {
            nameWordContainer.removeAllViews();

            List<NameCharacter> wordsList = favorite.wordsList;
            if (! ArrayUtil.isEmpty(wordsList)) {
                for (int i = 0; i < wordsList.size(); i ++) {
                    NameCharacter character = wordsList.get(i);
                    if (null != character) {
                        nameWordContainer.addView(
                                HeaderWordHolder.newInstance(ClientQmjmApplication.getContext(), character), nameWordContainer.getChildCount()
                        );
                    }
                }
            }
        }

        public TextView getNameWordCollect() {
            return nameWordCollect;
        }
    }
}
