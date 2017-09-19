package com.tjyw.qmjm.item;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mikepenz.fastadapter.IClickable;
import com.mikepenz.fastadapter.IItem;
import com.tjyw.atom.network.model.Message;
import com.tjyw.atom.pub.fresco.ImageFacade;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.R;

import java.util.List;

/**
 * Created by stephen on 7/19/16.
 */
public abstract class MessageConverseItem<T extends Message, Item extends IItem & IClickable, VH extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder>
        extends AtomPubFastAdapterAbstractItem<T, Item, VH> {

    @From(value = R.id.bodyAvatar, canBeNull = true)
    protected SimpleDraweeView bodyAvatar;

    public MessageConverseItem(T src) {
        super(src);
    }

    @Override
    public void bindView(VH holder, List<Object> payloads) {
        super.bindView(holder, payloads);

        if (null != bodyAvatar) {
            if (src.system) {
                ImageFacade.loadImage(R.drawable.atom_ic_message_call_center, bodyAvatar);
            } else {
//                UserInfoResourceHelper.loadUserAvatarCircleResource(bodyAvatar, src.photo, src.userGender);
            }
        }
    }
}
