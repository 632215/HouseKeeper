package com.jinke.housekeeper.saas.patrol.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author : huominghao
 * date : 2018/2/13 0013
 * function :
 */

public class PatrolStatementPagerAdapter extends FragmentStatePagerAdapter {
    static String titleList[] = null;
    List<Fragment> list = new ArrayList<>();
    FragmentManager fragmentManager;

    public PatrolStatementPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.fragmentManager = fm;
        this.list = list;
    }

    public void setTitleList(String list[]) {
        this.titleList = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList[position];
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
