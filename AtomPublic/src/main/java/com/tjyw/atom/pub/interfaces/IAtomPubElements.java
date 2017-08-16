package com.tjyw.atom.pub.interfaces;

import android.util.Pair;

import com.tjyw.atom.pub.R;

/**
 * Created by stephen on 17-8-16.
 */
public interface IAtomPubElements {

    String METAL = "金";

    String WOOD = "木";

    String WATER = "水";

    String FIRE = "火";

    String EARTH = "土";

    interface Reference {

        Pair<String, Integer> METAL = new Pair<String, Integer>(IAtomPubElements.METAL, R.drawable.atom_pub_ic_elements_metal);

        Pair<String, Integer> WOOD = new Pair<String, Integer>(IAtomPubElements.METAL, R.drawable.atom_pub_ic_elements_wood);

        Pair<String, Integer> WATER = new Pair<String, Integer>(IAtomPubElements.METAL, R.drawable.atom_pub_ic_elements_water);

        Pair<String, Integer> FIRE = new Pair<String, Integer>(IAtomPubElements.METAL, R.drawable.atom_pub_ic_elements_fire);

        Pair<String, Integer> EARTH = new Pair<String, Integer>(IAtomPubElements.METAL, R.drawable.atom_pub_ic_elements_earth);
    }
}
