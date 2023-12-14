package android.outstandfood_client.view.screen.adapter;

import android.outstandfood_client.view.screen.fragment.DrinkFragment;
import android.outstandfood_client.view.screen.fragment.FoodFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerMenuAdapter extends FragmentStateAdapter {
    public ViewPagerMenuAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FoodFragment();
            case 1:
                return new DrinkFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
