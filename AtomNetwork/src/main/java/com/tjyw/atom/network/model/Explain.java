package com.tjyw.atom.network.model;

import com.tjyw.atom.network.result.RetroResultItem;

import java.util.List;

/**
 * Created by stephen on 14/08/2017.
 */
public class Explain implements RetroResultItem {

    private static final long serialVersionUID = -2619753110314861329L;

    public List<Word> wordsList;

    public NameScore nameScore;

    public List<WuGe> wugeList;

    public SanCai sancai;

    public static class Word implements RetroResultItem {

        private static final long serialVersionUID = 6943080322236702655L;

        public String word;

        public String type;

        public String wuxing;

        public int jiantibihua;

        public String jiantipinyin;

        public String fanti;

        public String fantibihua;

        public String shuxing;

        public String jibenjieshi; // 基本解释

        public String xingxijieshi; // 详细解释
    }

    public static class NameScore implements RetroResultItem {

        private static final long serialVersionUID = -1221679170908492997L;

        public int score;

        public String desc;
    }

    public static class WuGe implements RetroResultItem {

        private static final long serialVersionUID = -4216193048087371723L;

        public String name;

        public String jixiong;

        public String zonglun;

        public String shiyi;

        public String anshi;
    }

    public static class SanCai implements RetroResultItem {

        private static final long serialVersionUID = -7944160821550718175L;

        public String jixiong;

        public String zonglun;

        public String jichuyun;

        public String chenggongyun;

        public String renjiyun;

        public String xingge;

        public String shiyi;
    }
}
