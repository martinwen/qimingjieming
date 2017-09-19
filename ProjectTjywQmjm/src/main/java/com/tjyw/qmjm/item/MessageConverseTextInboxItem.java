package com.tjyw.qmjm.item;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tjyw.atom.network.model.Message;
import com.tjyw.atom.network.utils.DateTimeUtils;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.R;

/**
 * Created by stephen on 18/09/2017.
 */
public class MessageConverseTextInboxItem extends MessageConverseItem<Message, MessageConverseTextInboxItem, MessageConverseTextInboxItem.ConverseTextInboxHolder> {

    public MessageConverseTextInboxItem(Message src) {
        super(src);
    }

    @Override
    public ConverseTextInboxHolder getViewHolder(View v) {
        return new ConverseTextInboxHolder(v);
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_message_converse_body_text_inbox;
    }

    public static class ConverseTextInboxHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<Message> {

        @From(R.id.bodyTime)
        protected TextView bodyTime;

        @From(R.id.bodyAvatar)
        protected SimpleDraweeView bodyAvatar;

        @From(R.id.bodyContent)
        protected TextView bodyContent;

        public ConverseTextInboxHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, Message message) {
            bodyTime.setText(DateTimeUtils.printCalendarByPattern(DateTimeUtils.getCalendar(message.timeStamp), DateTimeUtils.yyyy_MM_dd_HH_mm));

            if (TextUtils.isEmpty(message.detail)) {
                bodyContent.setText(R.string.atom_pubResStringMessageConverseDefaultInboxText);
            } else {
                bodyContent.setText(message.detail);
            }
        }
    }
}
