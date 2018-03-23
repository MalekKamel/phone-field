package com.sha.kamel.phonefield.shared.ui.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.sha.kamel.phonefield.shared.ui.recyclerviewdialog.SearchRecyclerViewDialogFrag;

import java.util.List;

/**
 * Created by Sha on 4/20/17.
 */

public abstract class BaseRecyclerAdapter<M,VH extends BaseViewHolder<M>> extends RecyclerView.Adapter<VH> {

    protected List<M> list;

    protected boolean isLoadingAdded;
    protected RecyclerAdapterCallback recyclerAdapterCallback;

    protected Context context(){
        return recyclerAdapterCallback.context();
    }

    protected AppCompatActivity activity(){
        return recyclerAdapterCallback.activity();
    }

    public Fragment findFragmentByTag(String tag){
        return activity().getFragmentManager().findFragmentByTag(tag);
    }

    protected View view(){
        return recyclerAdapterCallback.view();
    }

    public BaseRecyclerAdapter(List<M> list, RecyclerAdapterCallback recyclerAdapterCallback) {
        this.list = list;
        this.recyclerAdapterCallback = recyclerAdapterCallback;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    public abstract VH getViewHolder(ViewGroup viewGroup, int viewType);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        try {
            M item = list.get(position);
            holder.item = item;
            holder.bindView(item);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public M item(int position){
        return list.get(position);
    }

    public void addAll(List<M> items){
        list.addAll(items);
        notifyDataSetChanged();
    }

    public void replace(List<M> items){
        list = items;
        notifyDataSetChanged();
    }

    public List<M> list(){
        return list;
    }

    protected interface ItemViewType{
        int LOADING = 0;
        int NORMAL = 1;
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }


}
