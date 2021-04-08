package edu.karen.nikoghosyan.moviedb.ui.information;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.karen.nikoghosyan.moviedb.R;

public class InformationMovieFragment extends Fragment {

    private InformationMovieViewModel mViewModel;

    public static InformationMovieFragment newInstance() {
        return new InformationMovieFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.information_movie_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InformationMovieViewModel.class);
        // TODO: Use the ViewModel
    }

}