package com.tjyw.bbbzqm.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tjyw.atom.network.conf.ISection;
import com.tjyw.atom.network.model.NameZodiac;
import atom.pub.inject.From;
import atom.pub.inject.Injector;
import com.tjyw.bbbzqm.R;

/**
 * Created by stephen on 17-9-22.
 */
public class NameBaseInfoHolder {

    @From(R.id.nameBaseSolar)
    protected TextView nameBaseSolar;

    @From(R.id.nameBaseLunar)
    protected TextView nameBaseLunar;

    @From(R.id.nameBaseGender)
    protected TextView nameBaseGender;

    @From(R.id.nameBaseZodiac)
    protected ImageView nameBaseZodiac;

    public NameBaseInfoHolder(View view) {
        Injector.inject(this, view);
    }

    public void baseInfo(NameZodiac nameZodiac) {
        nameBaseSolar.setText(nameZodiac.gongli);
        nameBaseLunar.setText(nameZodiac.nongli);
        nameBaseGender.setText(nameZodiac.xingbie);

        switch (nameZodiac.shengxiao) {
            case ISection.ZODIAC.Zodiac1_Rat:
                nameBaseZodiac.setImageResource(R.drawable.atom_pub_png_zodiac_1_rat);
                break ;
            case ISection.ZODIAC.Zodiac2_Ox:
                nameBaseZodiac.setImageResource(R.drawable.atom_pub_png_zodiac_2_ox);
                break ;
            case ISection.ZODIAC.Zodiac3_Tiger:
                nameBaseZodiac.setImageResource(R.drawable.atom_pub_png_zodiac_3_tiger);
                break ;
            case ISection.ZODIAC.Zodiac4_Rabbit:
                nameBaseZodiac.setImageResource(R.drawable.atom_pub_png_zodiac_4_rabbit);
                break ;
            case ISection.ZODIAC.Zodiac5_Dragon:
                nameBaseZodiac.setImageResource(R.drawable.atom_pub_png_zodiac_5_dragon);
                break ;
            case ISection.ZODIAC.Zodiac6_Snake:
                nameBaseZodiac.setImageResource(R.drawable.atom_pub_png_zodiac_6_snake);
                break ;
            case ISection.ZODIAC.Zodiac7_Horse:
                nameBaseZodiac.setImageResource(R.drawable.atom_pub_png_zodiac_7_horse);
                break ;
            case ISection.ZODIAC.Zodiac8_Goat:
                nameBaseZodiac.setImageResource(R.drawable.atom_pub_png_zodiac_8_goat);
                break ;
            case ISection.ZODIAC.Zodiac9_Monkey:
                nameBaseZodiac.setImageResource(R.drawable.atom_pub_png_zodiac_9_monkey);
                break ;
            case ISection.ZODIAC.Zodiac10_Cock:
                nameBaseZodiac.setImageResource(R.drawable.atom_pub_png_zodiac_10_cock);
                break ;
            case ISection.ZODIAC.Zodiac11_Dog:
                nameBaseZodiac.setImageResource(R.drawable.atom_pub_png_zodiac_11_dog);
                break ;
            case ISection.ZODIAC.Zodiac12_Pig:
                nameBaseZodiac.setImageResource(R.drawable.atom_pub_png_zodiac_12_pig);
        }
    }
}
