package com.tjyw.atom.network.conf;

/**
 * Created by stephen on 16-6-30.
 */
public interface ISection {

    interface GENDER {

        int MALE = 1;

        int FEMALE = 2;
    }

    interface NAME_COUNT {

        int SINGLE = 1;

        int DOUBLE = 2;
    }

    interface JSON {

        String NODE = "{}";

        String ARRAY = "[]";
    }
}
