package com.tjyw.atom.network.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.NameDefinition;

/**
 * Created by stephen on 17-8-23.
 */
public class RNameDefinition extends RetroListResult<NameDefinition> {

    @JsonProperty(IApiField.I.info)
    public Param param;

    public static class Param implements RetroResultItem {

        private static final long serialVersionUID = 6308960240548585502L;

        public String surname;

        public String day;

        public int gender;

        public int nameNumber;
    }
}
