package com.tjyw.atom.network.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.NameData;
import com.tjyw.atom.network.model.NameDefinition;
import com.tjyw.atom.network.param.ListRequestParam;

/**
 * Created by stephen on 17-8-23.
 */
public class RNameDefinition extends RetroListResult<NameDefinition> {

    private static final long serialVersionUID = -7187365063506464362L;

    @JsonProperty(IApiField.I.info)
    public ListRequestParam param;

    public NameData data;

    public String statusLabel;

    public String surname;

    public String name;

    public String day;

    public int gender;

    public int nameNumber;
}
