package com.example.orchidinn;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.orchidinn.fragments.BookingFragment;
import com.example.orchidinn.fragments.HomeFragment;
import com.example.orchidinn.fragments.ProfileFragment;
import com.example.orchidinn.fragments.StaysFragment;

public class viewPagerAdapter extends FragmentStateAdapter {
    public viewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //this is for changing pages
       switch (position){
           case 0: return new HomeFragment();
           case 1: return new BookingFragment();
           case 2: return new StaysFragment();
           case 3: return new ProfileFragment();
           default:return new HomeFragment();
       }
    }

    @Override
    public int getItemCount() {
        return 4;
        //4 pages so return 4
    }
}
