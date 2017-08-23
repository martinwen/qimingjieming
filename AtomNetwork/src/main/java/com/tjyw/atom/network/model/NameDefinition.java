package com.tjyw.atom.network.model;

import com.tjyw.atom.network.result.RetroResultItem;

import java.util.List;

/**
 * Created by stephen on 17-8-23.
 */
public class NameDefinition implements RetroResultItem {

    private static final long serialVersionUID = 3037741131798324414L;

    public List<NameCharacter> wordsList;

    public NameScore nameScore;

    public String name;

    public boolean favorite;
}
