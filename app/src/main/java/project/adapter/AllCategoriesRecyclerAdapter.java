package project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shokoofeadeli.onlineshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import project.models.Category;
import project.ui.activity.ProductActivity;

import static project.base.BaseApplication.getBaseApplication;
import static project.service.ServiceGenerator.API_BASE_URL;

public class AllCategoriesRecyclerAdapter extends RecyclerView.Adapter<AllCategoriesRecyclerAdapter.ViewHolder> {
    public static final int TYPE_MAIN = 1;
    public static final int TYPE_SUB = 0;
    private Context context;
    private ArrayList<Category> categoriesList;

    public AllCategoriesRecyclerAdapter(Context context, ArrayList<Category> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @Override
    public int getItemViewType(int position) {
        Category category = categoriesList.get(position);
        return category.getCategoryMain();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = 0;
        switch (viewType) {
            case TYPE_MAIN:
                layoutId = R.layout.adapter_main_categories;
                break;
            case TYPE_SUB:
                layoutId = R.layout.adapter_sub_categories;
                break;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int type = holder.getItemViewType();
        Category category = categoriesList.get(position);
        String categoryCode = category.getCategoryCode();
        String categoryName = category.getCategoryName();
        final String filterCode = categoryCode;
        final String filterName = categoryName;
        holder.txtCategoriesName.setText(category.getCategoryName());
        if (type == TYPE_SUB) {
            holder.txtCategoriesName.setText(category.getCategoryName());
            String imageURL = API_BASE_URL + category.getCategoryImageUrl();
            Picasso.with(context).load(imageURL).into(holder.imgCategories);
        }
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBaseApplication().getHandler().post(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(context, ProductActivity.class);
                        intent.putExtra("category_code", filterCode);
                        intent.putExtra("category_name", filterName);
                        context.startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup root;
        ImageView imgCategories;
        TextView txtCategoriesName;

        public ViewHolder(View view) {
            super(view);
            root = (ViewGroup) view;
            imgCategories = view.findViewById(R.id.imgCategories);
            txtCategoriesName = view.findViewById(R.id.txtCategoriesName);
        }
    }
}
