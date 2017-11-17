package com.tjyw.atom.network.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tjyw.atom.network.conf.ISection;
import com.tjyw.atom.network.model.NameDefinition;

import java.io.Serializable;

/**
 * Created by stephen on 30/08/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListRequestParam implements Serializable, Cloneable {

    private static final long serialVersionUID = 4152121590171607400L;

    public String surname;

    public String name;

    public String day;

    public int gender = ISection.GENDER.MALE;

    public int nameNumber = ISection.NAME_COUNT.DOUBLE;

    public String orderNo;

    public Integer redPacketId;

    public ListRequestParam() {

    }

    public ListRequestParam(NameDefinition definition) {
        this.surname = definition.surname;
        this.name = definition.getGivenName();
        this.day = definition.day;
        this.gender = definition.gender;
    }

    @Override
    public ListRequestParam clone() {
        try {
            return (ListRequestParam) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
