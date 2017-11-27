package com.tjyw.bbqm.item;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tjyw.atom.network.model.Message;
import com.tjyw.atom.network.utils.DateTimeUtils;
import com.tjyw.bbqm.R;

import atom.pub.inject.From;
import atom.pub.item.AtomPubFastAdapterAbstractItem;

/**
 * Created by stephen on 18/09/2017.
 */
public class MessageConverseTextSentItem extends MessageConverseItem<Message, MessageConverseTextSentItem, MessageConverseTextSentItem.ConverseTextSentHolder> {

    public MessageConverseTextSentItem(Message src) {
        super(src);
    }

    @Override
    public ConverseTextSentHolder getViewHolder(View v) {
        return new ConverseTextSentHolder(v);
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_message_converse_body_text_sent;
    }

    public static class ConverseTextSentHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<Message> {

        @From(R.id.bodyTime)
        protected TextView bodyTime;

        @From(R.id.bodyAvatar)
        protected SimpleDraweeView bodyAvatar;

        @From(R.id.bodyContent)
        protected TextView bodyContent;

        @From(R.id.bodyWaiting)
        protected TextView bodyWaiting;

        public ConverseTextSentHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, Message message) {
            bodyTime.setText(DateTimeUtils.printCalendarByPattern(DateTimeUtils.getCalendar(message.timeStamp), DateTimeUtils.yyyy_MM_dd_HH_mm));
            bodyContent.setText(message.detail);
//            bodyWaiting.setVisibility(null == next ? View.VISIBLE : View.GONE);
        }
    }
}
