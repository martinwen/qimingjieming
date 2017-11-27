package com.tjyw.bbqm.item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.model.PayCoupon;
import com.tjyw.bbqm.ClientQmjmApplication;
import com.tjyw.bbqm.R;

import java.util.List;

import atom.pub.inject.From;
import atom.pub.item.AtomPubFastAdapterAbstractItem;

/**
 * Created by stephen on 25/08/2017.
 */
public class PayCouponListItem extends AtomPubFastAdapterAbstractItem<PayCoupon, PayCouponListItem, PayCouponListItem.PayCouponListHolder> {

    public PayCouponListItem(PayCoupon src) {
        super(src);
    }

    @Override
    public PayCouponListHolder getViewHolder(View v) {
        return new PayCouponListHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_PayCouponListItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_pay_coupon_list_body;
    }

    @Override
    public void bindView(PayCouponListHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    public static class PayCouponListHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<PayCoupon> {

        @From(R.id.bodyPriceContainer)
        protected ViewGroup bodyPriceContainer;

        @From(R.id.bodyPrice)
        protected TextView bodyPrice;

        @From(R.id.bodyDesc)
        protected TextView bodyDesc;

        @From(R.id.bodyTitleContainer)
        protected ViewGroup bodyTitleContainer;

        @From(R.id.bodyTitle)
        protected TextView bodyTitle;

        @From(R.id.bodyDate)
        protected TextView bodyDate;

        @From(R.id.bodyUserIt)
        protected TextView bodyUserIt;

        public PayCouponListHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, PayCoupon payCoupon) {
            bodyPrice.setText(context.getString(R.string.atom_pub_resStringRMB_s, payCoupon.money));
            bodyDesc.setText(context.getString(R.string.atom_pub_resStringPayCouponCanUse, payCoupon.full_cut_money));
            bodyTitle.setText(payCoupon.title);
            bodyDate.setText(context.getString(R.string.atom_pub_resStringPayCouponValidity, payCoupon.startDate, payCoupon.endDate));

            switch (payCoupon.status) {
                case PayCoupon.STATUS.UNUSED:
                    bodyPriceContainer.setBackgroundResource(R.drawable.atom_ic_pay_coupon_unuse_bg_left);
                    bodyTitleContainer.setBackgroundResource(R.drawable.atom_ic_pay_coupon_unuse_bg_right);
                    bodyUserIt.setVisibility(View.VISIBLE);
                    break ;
                case PayCoupon.STATUS.USED:
                case PayCoupon.STATUS.EXPIRE:
                default:
                    bodyPriceContainer.setBackgroundResource(R.drawable.atom_ic_pay_coupon_used_bg_left);
                    bodyTitleContainer.setBackgroundResource(R.drawable.atom_ic_pay_coupon_used_bg_right);
                    bodyUserIt.setVisibility(View.GONE);
            }
        }
    }
}
