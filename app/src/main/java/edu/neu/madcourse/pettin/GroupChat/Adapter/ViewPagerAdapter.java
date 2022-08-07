//package edu.neu.madcourse.pettin.GroupChat.Adapter;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.viewpager2.adapter.FragmentStateAdapter;
//
//import java.util.ArrayList;
//
//import edu.neu.madcourse.pettin.GroupChat.Fragments.GroupChatFragment;
//import edu.neu.madcourse.pettin.GroupChat.Fragments.UserFragment;
//
//public class ViewPagerAdapter extends FragmentStateAdapter {
//
//
//    private ArrayList<Fragment> fragments;
//    private ArrayList<String> titles;
//
//    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
//        super(fragmentActivity);
//        this.fragments = new ArrayList<>();
//        this.titles = new ArrayList<>();
//    }
//
//    @Override
//    public Fragment createFragment(int position) {
//        switch (position) {
//            case 0:
//                return new GroupChatFragment();
//            case 1:
//                return new UserFragment();
//            default:
//                return new GroupChatFragment();
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 2;
//    }
//
//
//}
