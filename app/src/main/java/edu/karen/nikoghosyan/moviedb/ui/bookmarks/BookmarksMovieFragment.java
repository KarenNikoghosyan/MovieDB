package edu.karen.nikoghosyan.moviedb.ui.bookmarks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.ui.bookmarks.adapters.BookmarksAdapter;

public class BookmarksMovieFragment extends Fragment {

    private BookmarksMovieViewModel bookmarksMovieViewModel;
    private RecyclerView rvBookmarks;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bookmarks_movie_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //bookmarksMovieViewModel.updateBookmarks();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvBookmarks = view.findViewById(R.id.rvBookmarks);

        bookmarksMovieViewModel = new ViewModelProvider(this).get(BookmarksMovieViewModel.class);

        bookmarksMovieViewModel.getBookmarkedMovies().observe(getViewLifecycleOwner(), movies -> {
            rvBookmarks.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            rvBookmarks.setAdapter(new BookmarksAdapter(movies));

        });
    }

    public void updateData(){
        //bookmarksMovieViewModel.updateBookmarks();
    }
}