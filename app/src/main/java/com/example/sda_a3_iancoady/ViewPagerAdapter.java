package com.example.sda_a3_iancoady;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/*
 * viewPager adapter.
 * @author Chris Coughlan 2019
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    ViewPagerAdapter(@NonNull FragmentManager fragment, int behavior, Context nContext) {
        super(fragment, behavior);
        context = nContext;
    }


    //Decides what fragment activity to call based on what fragment is active at the time of swiping.
    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new Fragment();

        //finds the tab position (note array starts at 0)
        position = position+1;

        //finds the fragment
        switch (position)
        {
            case 1:
                //code
                fragment = new Welcome();
                break;
            case 2:
                //code
                fragment = new ProductList();
                break;

            case 3:
                //code
                fragment = new OrderTshirt();
                break;
        }

        return fragment;
    }

    //Total number of tabs in the pagerview
    @Override
    public int getCount() {
        return 3;
    }


    //Set's the title of each fragment in the pagerview
    @Override
    public CharSequence getPageTitle(int position) {
        position = position+1;

        CharSequence tabTitle = "";

        //finds the fragment
        switch (position)
        {
            case 1:
                //code
                tabTitle = context.getString(R.string.tab1);
                break;
            case 2:
                //code
                tabTitle = context.getString(R.string.tab2);
                break;
            case 3:
                //code
                tabTitle = context.getString(R.string.tab3);
                break;
        }

        return tabTitle;
    }
}
