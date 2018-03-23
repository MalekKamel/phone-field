package com.sha.kamel.phonefield.shared.ui.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public interface RecyclerAdapterCallback {
    View view();
    Context context();
    AppCompatActivity activity();
}
