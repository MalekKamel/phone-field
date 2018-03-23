package com.sha.kamel.phonefield;

import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import com.sha.kamel.andrutil.Callback;
import com.sha.kamel.phonefield.R;
import com.sha.kamel.phonefield.shared.ui.adapter.BaseRecyclerAdapter;
import com.sha.kamel.phonefield.shared.ui.adapter.BaseViewHolder;
import com.sha.kamel.phonefield.shared.ui.adapter.RecyclerAdapterCallback;
import com.sha.kamel.phonefield.shared.ui.recyclerviewdialog.SearchRecyclerViewDialogFrag;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sha on 4/20/17.
 */

public class CountryAdapter extends
        BaseRecyclerAdapter<Country, CountryAdapter.Vh> implements
        Filterable{

    private List<Country> immutableList = new ArrayList<>();
    private Callback<Country> callback;

    public CountryAdapter(
            List<Country> values,
            RecyclerAdapterCallback recyclerAdapterCallback,
            Callback<Country> callback
    ){
        super(values, recyclerAdapterCallback);
        immutableList.addAll(values);
        this.callback = callback;

    }

    @Override
    public Vh getViewHolder(ViewGroup viewGroup, int viewType) {
        return new Vh(viewGroup);
    }

    public class Vh extends BaseViewHolder<Country> {
        private TextView tv_countryName;
        private ImageView iv_flag;

        Vh(ViewGroup viewGroup) {
            super(viewGroup, R.layout.viewholder_country);
            tv_countryName = findViewById(R.id.tv_countryName);
            iv_flag = findViewById(R.id.iv_flag);
            itemView.setOnClickListener(v ->{
                callback.call(item);
                ((SearchRecyclerViewDialogFrag) findFragmentByTag(SearchRecyclerViewDialogFrag.class.getSimpleName()))
                        .dismiss();
            });
        }

        @Override
        public void bindView(Country item) {
            iv_flag.setImageResource(item.getResId(context()));
            tv_countryName.setText(item.getName(context()));
        }
    }

    // Filtering logic ------------------------

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new CustomFilter();

    public class CustomFilter extends Filter {
        private List<Country> filteredList = new ArrayList<>();

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();
            for (final Country item : immutableList) {
                if (item.getName(context()).toLowerCase().contains(constraint.toString().toLowerCase())) {
                    filteredList.add(item);
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = filteredList;
            notifyDataSetChanged();
        }
    }
}