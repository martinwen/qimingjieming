package com.tjyw.atom.network.conf;

/**
 * Created by stephen on 16-6-30.
 */
public interface ISection {

    interface GENDER {

        int MALE = 1;

        int FEMALE = 2;
    }

    interface NAME_COUNT {

        int SINGLE = 1;

        int DOUBLE = 2;
    }

    interface JSON {

        String NODE = "{}";

        String ARRAY = "[]";
    }

    interface ZODIAC {

        String Zodiac1_Rat = "鼠";

        String Zodiac2_Ox = "牛";

        String Zodiac3_Tiger = "虎";

        String Zodiac4_Rabbit = "兔";

        String Zodiac5_Dragon = "龙";

        String Zodiac6_Snake = "蛇";

        String Zodiac7_Horse = "马";

        String Zodiac8_Goat = "羊";

        String Zodiac9_Monkey = "猴";

        String Zodiac10_Cock = "鸡";

        String Zodiac11_Dog = "狗";

        String Zodiac12_Pig = "猪";
    }
}
