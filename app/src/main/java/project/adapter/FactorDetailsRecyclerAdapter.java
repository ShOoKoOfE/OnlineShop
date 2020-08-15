package project.adapter;

import android.content.Context;
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

import static project.service.ServiceGenerator.API_BASE_URL;

public class FactorDetailsRecyclerAdapter extends RecyclerView.Adapter<FactorDetailsRecyclerAdapter.ViewHolder> {
    private ArrayList<Product> factorProductList;
    private Context context;

    public FactorDetailsRecyclerAdapter(Context context, ArrayList<Product> factorProductList) {
        this.context = context;
        this.factorProductList = factorProductList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_factor_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = factorProductList.get(position);
        holder.txtProductName.setText(product.getProductName());
        String imageURL = API_BASE_URL + product.getProductImageUrl();
        Picasso.with(context).load(imageURL).into(holder.imgProduct);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return factorProductList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup root;
        TextView txtProductName;
        ImageView imgProduct;

        public ViewHolder(View view) {
            super(view);
            root = (ViewGroup) view;
            txtProductName = view.findViewById(R.id.txtProductName);
            imgProduct = view.findViewById(R.id.imgProduct);
        }
    }
}
