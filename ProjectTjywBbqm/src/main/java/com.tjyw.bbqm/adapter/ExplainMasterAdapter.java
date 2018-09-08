package com.tjyw.bbqm.adapter;

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

import com.tjyw.atom.network.model.Explain;
import com.tjyw.bbqm.ClientQmjmApplication;
import com.tjyw.bbqm.R;
import com.tjyw.bbqm.fragment.ExplainMasterDestinyFragment;
import com.tjyw.bbqm.fragment.ExplainMasterOverviewFragment;
import com.tjyw.bbqm.fragment.ExplainMasterSanCaiFragment;
import com.tjyw.bbqm.fragment.ExplainMasterZodiacFragment;

/**
 * Created by stephen on 17-8-11.
 */
public class ExplainMasterAdapter extends FragmentPagerAdapter {

    public interface POSITION {

        int OVERVIEW = 0;

        int ZODIAC = 1;

        int DESTINY = 2;

        int SANCAI = 3;

        int ALL = 4;
    }

    public static ExplainMasterAdapter newInstance(FragmentManager fragmentManager, Explain explain) {
        return new ExplainMasterAdapter(fragmentManager)
                .setOverviewFragment(ExplainMasterOverviewFragment.newInstance(explain))
                .setZodiacFragment(ExplainMasterZodiacFragment.newInstance(explain))
                .setDestinyFragment(ExplainMasterDestinyFragment.newInstance(explain))
                .setSanCaiFragment(ExplainMasterSanCaiFragment.newInstance(explain));
    }

    protected ExplainMasterOverviewFragment overviewFragment;

    protected ExplainMasterZodiacFragment zodiacFragment;

    protected ExplainMasterDestinyFragment destinyFragment;

    protected ExplainMasterSanCaiFragment sanCaiFragment;

    public ExplainMasterAdapter(FragmentManager fm) {
        super(fm);
    }

    public ExplainMasterAdapter setOverviewFragment(ExplainMasterOverviewFragment overviewFragment) {
        this.overviewFragment = overviewFragment;
        return this;
    }

    public ExplainMasterAdapter setZodiacFragment(ExplainMasterZodiacFragment zodiacFragment) {
        this.zodiacFragment = zodiacFragment;
        return this;
    }

    public ExplainMasterAdapter setDestinyFragment(ExplainMasterDestinyFragment destinyFragment) {
        this.destinyFragment = destinyFragment;
        return this;
    }

    public ExplainMasterAdapter setSanCaiFragment(ExplainMasterSanCaiFragment sanCaiFragment) {
        this.sanCaiFragment = sanCaiFragment;
        return this;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION.OVERVIEW:
                return overviewFragment;
            case POSITION.ZODIAC:
                return zodiacFragment;
            case POSITION.DESTINY:
                return destinyFragment;
            case POSITION.SANCAI:
            default:
                return sanCaiFragment;
        }
    }

    @Override
    public int getCount() {
        return POSITION.ALL;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case ExplainMasterAdapter.POSITION.OVERVIEW:
                return getPageTitle(R.string.atom_pub_resStringExplainOverview, R.drawable.atom_bitmap_explain_tab_overview);
            case ExplainMasterAdapter.POSITION.ZODIAC:
                return getPageTitle(R.string.atom_pub_resStringExplainZodiac, R.drawable.atom_bitmap_explain_tab_zodiac);
            case ExplainMasterAdapter.POSITION.DESTINY:
                return getPageTitle(R.string.atom_pub_resStringExplainDestiny, R.drawable.atom_bitmap_explain_tab_destiny);
            case ExplainMasterAdapter.POSITION.SANCAI:
            default:
                return getPageTitle(R.string.atom_pub_resStringExplainSanCaiWuGe, R.drawable.atom_bitmap_explain_tab_sancaiwuge);
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
