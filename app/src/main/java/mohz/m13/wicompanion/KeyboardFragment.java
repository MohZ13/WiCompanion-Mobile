package mohz.m13.wicompanion;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mohil Zalavadiya on 15-09-2017.
 */

public class KeyboardFragment extends Fragment {
    private int[] imageIds = {
            R.drawable.ic_tab_keyboard,
            R.drawable.ic_tab_computer,
            R.drawable.ic_tab_win
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_keyboard, container, false);

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new KeyboardFragmentPagerAdapter(getChildFragmentManager()));

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < imageIds.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageIds[i]);
        }

        return rootView;
    }
}
