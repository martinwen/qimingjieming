package com.tjyw.atom.network.model;

import com.tjyw.atom.network.result.RetroResultItem;
import com.tjyw.atom.network.utils.ArrayUtil;

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

    public String getGivenName() {
        if (ArrayUtil.isEmpty(wordsList)) {
            return null;
        } else {
            StringBuilder givenName = new StringBuilder();
            int size = wordsList.size();
            for (int i = 0; i < size; i ++) {
                NameCharacter character = wordsList.get(i);
                if (null != character) {
                    if (character.isGivenCharacter()) {
                        givenName.append(character.word);
                    }
                }
            }

            return givenName.toString();
        }
    }
}
