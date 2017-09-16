package mohz.m13.wicompanion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Mohil Zalavadiya on 15-09-2017.
 */

class KeyboardFragmentPagerAdapter extends FragmentPagerAdapter {
    KeyboardFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new KeyboardTabOneFragment();
            case 1:
                return new KeyboardTabTwoFragment();
            case 2:
                return new KeyboardTabThreeFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
