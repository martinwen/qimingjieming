package com.tjyw.qmjm.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.inject.Injector;
import com.tjyw.qmjm.R;

public class HeaderWordHolder  {

    public static View newInstance(Context context, Explain.Word word) {
        View itemView = View.inflate(context, R.layout.atom_explain_header_word_body, null);
        new HeaderWordHolder(itemView).onBindView(context, word);
        return itemView;
    }

    @From(R.id.bodyPinYin)
    protected TextView bodyPinYin;

    @From(R.id.bodyWord)
    protected TextView bodyWord;

    @From(R.id.bodyElement)
    protected TextView bodyElement;

    public HeaderWordHolder(View itemView) {
        Injector.inject(this, itemView);
    }

    public void onBindView(Context context, Explain.Word word) {
        bodyPinYin.setText(word.jiantipinyin);
        bodyWord.setText(word.word);
        bodyElement.setText(context.getString(R.string.atom_pub_resStringExplainWordElement, word.wuxing));
    }
}