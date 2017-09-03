package com.tjyw.atom.network.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tjyw.atom.network.conf.ISection;

import java.io.Serializable;

/**
 * Created by stephen on 30/08/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListRequestParam implements Serializable {

    private static final long serialVersionUID = 4152121590171607400L;

    public String surname;

    public String name;

    public String day;

    public int gender = ISection.GENDER.MALE;

    public int nameNumber = ISection.NAME_COUNT.SINGLE;

    public String orderNo;
}
