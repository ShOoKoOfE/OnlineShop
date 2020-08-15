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

import project.models.Product;
import project.ui.activity.ProductDetailActivity;

import static project.base.BaseApplication.getBaseApplication;
import static project.service.ServiceGenerator.API_BASE_URL;

public class FavouriteRecyclerAdapter extends RecyclerView.Adapter<FavouriteRecyclerAdapter.ViewHolder> {
    private ArrayList<Product> productList;
    private Context context;

    public FavouriteRecyclerAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product product = productList.get(position);
        holder.txtProductName.setText(product.getProductName());
        holder.txtProductCode.setText(product.getProductCode());
        holder.txtProductPrice.setText(product.getProductPrice() + context.getString(R.string.toman));
        String imageURL = API_BASE_URL + product.getProductImageUrl();
        Picasso.with(context).load(imageURL).into(holder.imgProduct);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBaseApplication().getHandler().post(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(context, ProductDetailActivity.class);
                        intent.putExtra("SELECTED_PRODUCT_ID", product.getProductId());
                        context.startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup root;
        ImageView imgProduct;
        TextView txtProductName;
        TextView txtProductCode;
        TextView txtProductPrice;

        public ViewHolder(View view) {
            super(view);
            root = (ViewGroup) view;
            imgProduct = view.findViewById(R.id.imgProduct);
            txtProductName = view.findViewById(R.id.txtProductName);
            txtProductCode = view.findViewById(R.id.txtProductCode);
            txtProductPrice = view.findViewById(R.id.txtProductPrice);
        }
    }
}
