package com.tjyw.qmjm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;

/**
 * Created by stephen on 17-8-17.
 */
public class PayOrderActivity extends BaseToolbarActivity {

    @From(R.id.payService)
    protected TextView payService;

    @From(R.id.payPrice)
    protected TextView payPrice;

    @From(R.id.payUseAlipay)
    protected TextView payUseAlipay;

    @From(R.id.payUseWxPay)
    protected TextView payUseWxPay;

    @From(R.id.payUseBankCard)
    protected TextView payUseBankCard;

    @From(R.id.atom_pub_resIdsOK)
    protected TextView atom_pub_resIdsOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.atom_pay_order);
        tSetToolBar(getString(R.string.atom_pub_resStringPayOrder));

        payUseAlipay.setSelected(true);
        payUseAlipay.setOnClickListener(this);
        payUseWxPay.setOnClickListener(this);
        payUseBankCard.setOnClickListener(this);
        atom_pub_resIdsOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payUseAlipay:
                v.setSelected(true);
                payUseWxPay.setSelected(false);
                payUseBankCard.setSelected(false);
                break ;
            case R.id.payUseWxPay:
                v.setSelected(true);
                payUseAlipay.setSelected(false);
                payUseBankCard.setSelected(false);
                break ;
            case R.id.payUseBankCard:
                v.setSelected(true);
                payUseAlipay.setSelected(false);
                payUseWxPay.setSelected(false);
                break ;
            case R.id.atom_pub_resIdsOK:

        }
    }
}
