package com.example.orchidinn.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.orchidinn.fragments.PreviousFragment;
import com.example.orchidinn.fragments.UpcommingFragment;

public class StayAdapter extends FragmentStateAdapter {


    public StayAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new UpcommingFragment();
            case 1:
                return  new PreviousFragment();
            default:
                return new UpcommingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
