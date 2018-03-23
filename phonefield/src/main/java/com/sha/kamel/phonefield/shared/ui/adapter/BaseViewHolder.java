package com.sha.kamel.phonefield.shared.ui.adapter;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sha on 4/20/17.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(ViewGroup viewGroup, @LayoutRes int layoutRes) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(layoutRes, viewGroup, false));
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return itemView.findViewById(id);
    }

    public abstract void bindView(T item);

    public T item;




}
