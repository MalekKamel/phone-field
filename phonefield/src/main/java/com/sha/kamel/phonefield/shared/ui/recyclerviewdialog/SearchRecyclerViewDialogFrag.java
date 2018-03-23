package com.sha.kamel.phonefield.shared.ui.recyclerviewdialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Filterable;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.sha.kamel.andrutil.ObservableUtil;
import com.sha.kamel.phonefield.R;
import com.sha.kamel.phonefield.shared.ui.adapter.BaseRecyclerAdapter;
import com.trello.rxlifecycle2.components.RxDialogFragment;

import java.util.concurrent.TimeUnit;

public class SearchRecyclerViewDialogFrag extends RxDialogFragment {

    private RecyclerView rv;
    private EditText et_search;
    private BaseRecyclerAdapter adapter;


    // Logic here  ------------------------------

    public static SearchRecyclerViewDialogFrag newInstance(BaseRecyclerAdapter adapter) {
        if (!(adapter instanceof Filterable))
            throw new IllegalStateException("Adapter must be Filterable.");

        SearchRecyclerViewDialogFrag fragment = new SearchRecyclerViewDialogFrag();
        fragment.adapter = adapter;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_dialog_search_recycler_view,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        rv = getView().findViewById(R.id.rv);
        et_search = getView().findViewById(R.id.et_search);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
        search();
    }

    public void search() {
        Filterable filterable = (Filterable) adapter;

        RxTextView.textChanges(et_search)
                .debounce(200, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .map(String::toLowerCase)
                .compose(bindToLifecycle())
                .compose(ObservableUtil.handleExceptionKeepingSubscription())
                .subscribe(string -> filterable.getFilter().filter(string));
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }
}


