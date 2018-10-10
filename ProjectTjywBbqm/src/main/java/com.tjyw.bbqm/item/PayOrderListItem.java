package com.tjyw.bbqm.item;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tjyw.atom.network.model.Order;
import com.tjyw.atom.network.services.HttpPayServices;
import com.tjyw.bbqm.ClientQmjmApplication;
import com.tjyw.bbqm.R;

import java.util.List;

import atom.pub.inject.From;
import atom.pub.item.AtomPubFastAdapterAbstractItem;

/**
 * Created by stephen on 25/08/2017.
 */
public class PayOrderListItem extends AtomPubFastAdapterAbstractItem<Order, PayOrderListItem, PayOrderListItem.PayOrderListHolder> {

    public PayOrderListItem(Order src) {
        super(src);
    }

    @Override
    public PayOrderListHolder getViewHolder(View v) {
        return new PayOrderListHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_PayOrderListItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_pay_order_list_body;
    }

    @Override
    public void bindView(PayOrderListHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    public static class PayOrderListHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<Order> {

        @From(R.id.bodyAvatar)
        protected ImageView bodyAvatar;

        @From(R.id.bodyOrderNo)
        protected TextView bodyOrderNo;

        @From(R.id.bodyTitle)
        protected TextView bodyTitle;

        @From(R.id.bodyPrice)
        protected TextView bodyPrice;

        @From(R.id.bodyOrderDate)
        protected TextView bodyOrderDate;

        public PayOrderListHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, Order order) {
            bodyOrderNo.setText(order.name);
            bodyTitle.setText(order.title);
            bodyPrice.setText(context.getString(R.string.atom_pub_resStringRMB_s, order.money));
            bodyOrderDate.setText(order.dateCreated);

            switch (order.vipType) {
                case HttpPayServices.VIP_ID.SUIT:
                case HttpPayServices.VIP_ID.NEW_SUIT:
                    bodyAvatar.setImageResource(R.drawable.atom_pub_ic_pay_order_package_avatar);
                    break ;
                default:
                    bodyAvatar.setImageResource(R.drawable.atom_pub_ic_pay_order_avatar);
            }
        }
    }
}
