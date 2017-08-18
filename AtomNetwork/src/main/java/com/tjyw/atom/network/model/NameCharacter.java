package com.tjyw.atom.network.model;

import com.tjyw.atom.network.result.RetroResultItem;

public class NameCharacter implements RetroResultItem {

    private static final long serialVersionUID = 6943080322236702655L;

    public NameCharacter() {

    }

    public NameCharacter(String word) {
        this.word = word;
    }

    public String word;

    public String type;

    public String wuxing;

    public int jiantibihua;

    public String jiantipinyin;

    public String fanti;

    public String fantibihua;

    public String shuxing;

    public String jibenjieshi; // 基本解释

    public String xiangxijieshi; // 详细解释
}