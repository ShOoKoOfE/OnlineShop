package project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shokoofeadeli.onlineshop.R;

import java.util.ArrayList;

import static project.base.BaseApplication.getBaseApplication;

public class FilterRecyclerAdapter extends RecyclerView.Adapter<FilterRecyclerAdapter.ViewHolder> {
    public static ArrayList<String> filterFactoryList = new ArrayList<>();
    private ArrayList<String> list;

    public FilterRecyclerAdapter(ArrayList<String> list) {
        this.list = list;
        filterFactoryList.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_filter, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = list.get(position);
        holder.txtFactory.setText(item);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = (LinearLayout) view;
                int count = linearLayout.getChildCount();
                for (int i = 0; i < count; i++) {
                    View childAt = linearLayout.getChildAt(i);
                    if (childAt instanceof TextView) {
                        final TextView textView = (TextView) childAt;
                        getBaseApplication().getHandler().post(new Runnable() {
                            public void run() {
                                if (filterFactoryList.contains(textView.getText().toString())) {
                                    filterFactoryList.remove(textView.getText().toString());
                                } else {
                                    filterFactoryList.add(textView.getText().toString());
                                }
                            }
                        });
                    } else if (childAt instanceof ImageView) {
                        final ImageView imageView = (ImageView) childAt;
                        getBaseApplication().getHandler().post(new Runnable() {
                            public void run() {
                                if (imageView.getVisibility() == View.VISIBLE) {
                                    imageView.setVisibility(View.GONE);
                                } else {
                                    imageView.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup root;
        TextView txtFactory;
        ImageView imgSelect;

        public ViewHolder(View view) {
            super(view);
            root = (ViewGroup) view;
            txtFactory = view.findViewById(R.id.txtFactory);
            imgSelect = view.findViewById(R.id.imgSelect);
        }
    }
}
