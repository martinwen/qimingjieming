package com.tjyw.bbbzqm.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.model.NameCharacter;
import atom.pub.inject.From;
import atom.pub.inject.Injector;
import com.tjyw.bbbzqm.R;

public class HeaderWordHolder  {

    public static View newInstance(Context context, NameCharacter character) {
        View itemView = View.inflate(context, R.layout.atom_explain_header_word_body, null);
        new HeaderWordHolder(itemView).onBindView(context, character);
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

    public void onBindView(Context context, NameCharacter character) {
        bodyPinYin.setText(character.jiantipinyin);
        bodyWord.setText(character.word);
        bodyElement.setText(context.getString(R.string.atom_pub_resStringExplainWordElement, character.wuxing));
    }
}