package com.example.madesubmission5.callback;

import android.database.Cursor;

public interface LoadFavoriteCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}
