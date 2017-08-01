package com.tjyw.atom.network.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by stephen on 7/4/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface RetroResultItem extends Serializable {

}
