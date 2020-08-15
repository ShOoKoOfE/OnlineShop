package project.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shokoofeadeli.onlineshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import project.base.BaseActivity;
import project.comon.ActionBarSetting;
import project.models.Category;
import project.ui.fragment.CategoriesFragment;

import static project.service.ServiceGenerator.API_BASE_URL;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {
    private ArrayList<Category> categoryList;
    private Context context;

    public CategoryRecyclerAdapter(Context context, ArrayList<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Category category = categoryList.get(position);
        holder.txtCategoryName.setText(category.getCategoryName());
        String imageURL = API_BASE_URL + category.getCategoryImageUrl();
        Picasso.with(context).load(imageURL).into(holder.imgCategory);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment fragment = new CategoriesFragment(category.getCategoryCode());
                setActionBarSetting(activity.getResources().getDrawable(R.drawable.actionbar_nologo), category.getCategoryName(), activity);
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content, fragment)
                        .addToBackStack("HomeActivity")
                        .commitAllowingStateLoss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    private void setActionBarSetting(Drawable drawable, String title, AppCompatActivity activity) {
        new ActionBarSetting(activity)
                .setBackground(drawable)
                .setTitle(title)
                .build();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup root;
        TextView txtCategoryName;
        ImageView imgCategory;

        public ViewHolder(View view) {
            super(view);
            root = (ViewGroup) view;
            txtCategoryName = view.findViewById(R.id.txtCategoryName);
            imgCategory = view.findViewById(R.id.imgCategory);
        }
    }
}
