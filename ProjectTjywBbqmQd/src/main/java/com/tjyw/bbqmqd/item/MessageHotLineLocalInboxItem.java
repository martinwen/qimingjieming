package com.tjyw.bbqmqd.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tjyw.atom.network.model.Message;
import com.tjyw.atom.network.model.PropertyOption;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.atom.network.utils.DateTimeUtils;
import atom.pub.inject.From;
import atom.pub.inject.Injector;
import atom.pub.item.AtomPubFastAdapterAbstractItem;

import com.tjyw.bbqmqd.ClientQmjmApplication;
import com.tjyw.bbqmqd.R;

import java.util.List;

/**
 * Created by stephen on 15/08/2017.
 */
public class MessageHotLineLocalInboxItem extends MessageConverseItem<Message, MessageHotLineLocalInboxItem, MessageHotLineLocalInboxItem.HotLineLocalInboxBodyHolder> {

    public MessageHotLineLocalInboxItem(Message src) {
        super(src);
    }

    @Override
    public HotLineLocalInboxBodyHolder getViewHolder(View v) {
        return new HotLineLocalInboxBodyHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_MessageHotLineLocalInboxItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_message_hotline_local_body_text_inbox;
    }

    @Override
    public void bindView(HotLineLocalInboxBodyHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    public static class HotLineLocalInboxBodyHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<Message> {

        @From(R.id.bodyTime)
        protected TextView bodyTime;

        @From(R.id.bodyAvatar)
        protected SimpleDraweeView bodyAvatar;

        @From(R.id.bodyTitle)
        protected TextView bodyTitle;

        @From(R.id.bodyContent)
        protected LinearLayout bodyContent;

        protected List<Builder> builderList;

        public HotLineLocalInboxBodyHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, Message message) {
            bodyTime.setText(DateTimeUtils.printCalendarByPattern(DateTimeUtils.getCalendar(message.timeStamp), DateTimeUtils.yyyy_MM_dd_HH_mm));
            bodyTitle.setText(message.detail);

            bodyContent.removeAllViews();
            builderList.clear();

            if (! ArrayUtil.isEmpty(message.questionList)) {
                int size = message.questionList.size();
                for (int i = 0; i < size; i ++) {
                    PropertyOption option = message.questionList.get(i);
                    if (null != option) {
                        builderList.add(new Builder(context)
                                .add(bodyContent)
                                .setBodyTitle(option.value)
                                .setBodyAnswer(option.answerDesc)
//                                .setOnClickListener(this)
                        );
                    }
                }
            }
        }
    }



    static class Builder {

        @From(R.id.bodyTitle)
        protected TextView bodyTitle;

        @From(R.id.bodyAnswer)
        protected TextView bodyAnswer;

        protected View convertView;

        public Builder(Context context) {
            convertView = LayoutInflater.from(context).inflate(R.layout.atom_message_hotline_local_body_text_inbox_qa, null);
            Injector.inject(this, convertView);
        }

        public Builder add(ViewGroup parent) {
            parent.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            return this;
        }

        public Builder setBodyTitle(String title) {
            bodyTitle.setText(title);
            return this;
        }

        public Builder setOnClickListener(View.OnClickListener l) {
            bodyTitle.setOnClickListener(l);
            return this;
        }

        public Builder setBodyAnswer(String answer) {
            bodyAnswer.setText(answer);
            return this;
        }

        public Builder reLayout(boolean expand) {
            bodyAnswer.setVisibility(expand ? View.VISIBLE : View.GONE);
            return this;
        }

        public TextView getBodyTitle() {
            return bodyTitle;
        }

        public boolean expanding() {
            return bodyAnswer.getVisibility() == View.VISIBLE;
        }
    }
}
