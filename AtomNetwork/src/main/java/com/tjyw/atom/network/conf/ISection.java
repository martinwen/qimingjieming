package com.tjyw.atom.network.conf;

/**
 * Created by stephen on 16-6-30.
 */
public interface ISection {

    interface GENDER {

        int MALE = 1;

        int FEMALE = 2;
    }

    interface STRING {

        String N = "\n";
    }

    int[] AGE = {
            18, 65
    };

    int[] Gender = {
            GENDER.MALE, GENDER.FEMALE
    };

    int[] HEIGHT = {
            150, 199
    };

    int[] WEIGHT = {
            80, 199
    };

    interface JSON {

        String NODE = "{}";

        String ARRAY = "[]";
    }
}
