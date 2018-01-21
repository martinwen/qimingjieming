package com.tjyw.bbqmqd.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mobsandgeeks.saripaar.Validator;
import com.tjyw.atom.network.model.MessageConverse;
import com.tjyw.atom.network.presenter.IPost;
import com.tjyw.atom.network.presenter.MessageHotLinePresenter;
import com.tjyw.atom.network.presenter.listener.OnApiMessageHotLinePostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.result.REmptyResult;
import atom.pub.inject.From;
import atom.pub.interfaces.AtomPubValidationListener;
import com.tjyw.bbqmqd.R;

import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 11/04/2017.
 */
@RequiresPresenter(MessageHotLinePresenter.class)
public class MessageHotLineActivity extends BaseToolbarActivity<MessageHotLinePresenter<MessageHotLineActivity>>
        implements OnApiPostErrorListener,
        OnApiMessageHotLinePostListener.PostMessageHotLineDetailListener, OnApiMessageHotLinePostListener.PostMessageHotLineWriteListener {

    @From(R.id.messageConverseContainer)
    protected RecyclerView messageConverseContainer;

    @From(R.id.messageInputContainer)
    protected ViewGroup messageInputContainer;

//    @NotEmpty(trim = true, messageResId = R.string.td_messageReplyHint)
    @From(R.id.messageInputText)
    protected EditText messageInputText;

    @From(R.id.messageInputSend)
    protected TextView messageInputSend;

    protected FastAdapter messageHotLineAdapter;

    protected Validator messageReplyValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.atom_message_hotline);
        tSetToolBar(getString(R.string.atom_pub_resStringNamingList));

//        messageConverseContainer.setAdapter(messageHotLineAdapter = new MessageHotLineAdapter());
        messageConverseContainer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        maskerShowProgressView(false);
        getPresenter().postCustomerServiceDetail(1);

        messageReplyValidator = new Validator(this);
        messageReplyValidator.setValidationListener(new AtomPubValidationListener(getApplicationContext()) {

            @Override
            public void onValidationSucceeded() {
                maskerShowProgressView(true);
                getPresenter().postCustomerServiceWrite(messageInputText.getText().toString());
            }
        });

        messageInputContainer.setVisibility(View.VISIBLE);
        messageInputSend.setOnClickListener(this);
        messageInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEND:
                        messageReplyValidator.validate();
                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.messageInputSend:
                messageReplyValidator.validate();
                break ;
            default:
                super.onClick(v);
        }
    }

    @Override
    public void maskerOnClick(View view, int clickLabelRes) {
        super.maskerOnClick(view, clickLabelRes);
        maskerShowProgressView(false);
        getPresenter().postCustomerServiceDetail(1);
    }

    @Override
    public void postOnMessageHotLineDetailSuccess(MessageConverse result) {
//        messageHotLineAdapter.setList(result.list);
//        messageHotLineAdapter.notifyDataSetChanged();
//
//        Observable.just(messageHotLineAdapter.size())
//                .compose(RxSchedulersHelper.<Integer>io_main())
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer size) {
//                        messageConverseContainer.smoothScrollToPosition(size + 1);
//                        maskerHideMaskerLayout();
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        throwable.printStackTrace();
//                    }
//                });
    }

    @Override
    public void postMessageHotLineWriteSuccess(REmptyResult result) {
        getPresenter().postCustomerServiceDetail(1);
        messageInputText.clearFocus();
        messageInputText.setText(null);
        pHideSoftInput();
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {
        switch (postId) {
            case IPost.HotLine.Detail:
                maskerShowMaskerLayout(getString(R.string.atom_pub_resStringNetworkBroken), R.string.atom_pub_resStringRetry);
            case IPost.HotLine.Write:
                showToast(R.string.atom_pub_resStringNetworkBroken);
        }
    }
}
