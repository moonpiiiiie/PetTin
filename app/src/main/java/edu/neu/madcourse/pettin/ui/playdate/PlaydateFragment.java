package edu.neu.madcourse.pettin.ui.playdate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.neu.madcourse.pettin.databinding.FragmentPlaydateBinding;

public class PlaydateFragment extends Fragment {

    private FragmentPlaydateBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PlaydateViewModel playdateViewModel =
                new ViewModelProvider(this).get(PlaydateViewModel.class);

        binding = FragmentPlaydateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPlaydate;
        playdateViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}