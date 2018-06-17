package com.tjyw.bbbzqm.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.bbbzqm.ClientQmjmApplication;
import com.tjyw.bbbzqm.R;
import com.tjyw.bbbzqm.fragment.NameMasterAnalyzeFragment;
import com.tjyw.bbbzqm.fragment.NameMasterFreedomFragment;
import com.tjyw.bbbzqm.fragment.NameMasterLuckyFragment;
import com.tjyw.bbbzqm.fragment.NameMasterRecommendFragment;

/**
 * Created by stephen on 17-8-11.
 */
public class NameMasterAdapter extends FragmentPagerAdapter {

    public interface POSITION {

        int ANALYZE = 0;

        int FREEDOM = 1;

        int RECOMMEND = 2;

        int LUCKY = 3;

        int ALL = 4;
    }

    public static NameMasterAdapter newInstance(FragmentManager fragmentManager, RNameDefinition definition) {
        return new NameMasterAdapter(fragmentManager)
                    .setAnalyzeFragment(NameMasterAnalyzeFragment.newInstance(definition.data))
                    .setMasterFreedomFragment(NameMasterFreedomFragment.newInstance(definition))
                    .setRecommendFragment(NameMasterRecommendFragment.newInstance(definition))
                    .setLuckyFragment(NameMasterLuckyFragment.newInstance(definition.param));
    }

    protected NameMasterAnalyzeFragment masterAnalyzeFragment;

    protected NameMasterFreedomFragment masterFreedomFragment;

    protected NameMasterRecommendFragment masterRecommendFragment;

    protected NameMasterLuckyFragment masterLuckyFragment;

    public NameMasterAdapter(FragmentManager fm) {
        super(fm);
    }

    public NameMasterAdapter setAnalyzeFragment(NameMasterAnalyzeFragment masterAnalyzeFragment) {
        this.masterAnalyzeFragment = masterAnalyzeFragment;
        return this;
    }

    public NameMasterAdapter setMasterFreedomFragment(NameMasterFreedomFragment masterFreedomFragment) {
        this.masterFreedomFragment = masterFreedomFragment;
        return this;
    }

    public NameMasterAdapter setRecommendFragment(NameMasterRecommendFragment masterRecommendFragment) {
        this.masterRecommendFragment = masterRecommendFragment;
        return this;
    }

    public NameMasterAdapter setLuckyFragment(NameMasterLuckyFragment masterLuckyFragment) {
        this.masterLuckyFragment = masterLuckyFragment;
        return this;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION.ANALYZE:
                return masterAnalyzeFragment;
            case POSITION.FREEDOM:
                return masterFreedomFragment;
            case POSITION.RECOMMEND:
                return masterRecommendFragment;
            case POSITION.LUCKY:
            default:
                return masterLuckyFragment;
        }
    }

    @Override
    public int getCount() {
        return POSITION.ALL;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case POSITION.ANALYZE:
                return getPageTitle(R.string.atom_pub_resStringNameAnalyze, R.drawable.atom_bitmap_name_tab_analyze);
            case POSITION.FREEDOM:
                return getPageTitle(R.string.atom_pub_resStringNameFreedom, R.drawable.atom_bitmap_name_tab_freedom);
            case POSITION.RECOMMEND:
                return getPageTitle(R.string.atom_pub_resStringNameRecommend, R.drawable.atom_bitmap_name_tab_recommend);
            case POSITION.LUCKY:
            default:
                return getPageTitle(R.string.atom_pub_resStringNameLucky, R.drawable.atom_bitmap_name_tab_lucky);
        }
    }

    protected CharSequence getPageTitle(@StringRes int resId, @DrawableRes int drawable) {
        Drawable image = ContextCompat.getDrawable(ClientQmjmApplication.getContext(), drawable);
        if (null != image) {
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            SpannableStringBuilder builder = new SpannableStringBuilder(ClientQmjmApplication.pGetString(resId));
            builder.insert(0, " \n");
            builder.setSpan(new ImageSpan(image, ImageSpan.ALIGN_BASELINE), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return builder;
        } else {
            return ClientQmjmApplication.pGetString(resId);
        }
    }
}
