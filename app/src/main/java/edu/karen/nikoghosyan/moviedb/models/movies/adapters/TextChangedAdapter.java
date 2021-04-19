package edu.karen.nikoghosyan.moviedb.models.movies.adapters;

import android.text.Editable;
import android.text.TextWatcher;

public interface TextChangedAdapter extends TextWatcher {

    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after){}

    @Override
    default void afterTextChanged(Editable s){}
}
