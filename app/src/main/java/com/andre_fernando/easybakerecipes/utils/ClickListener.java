package com.andre_fernando.easybakerecipes.utils;

import android.view.View;

/**
 * A click listener interface
 */
public interface ClickListener {
    void onClick(@SuppressWarnings("unused") View view, int position);

    void onLongClick(@SuppressWarnings("unused") View view, int position);
}
