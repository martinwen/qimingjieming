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

    public static class Param {

        public String surname;

        public String day;

        public int gender;
    }
}
