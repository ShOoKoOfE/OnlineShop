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

import static project.comon.NumberTextWatcherForThousand.GetCurrency;
import static project.service.ServiceGenerator.API_BASE_URL;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {
    private ArrayList<Product> searchList;
    private Context context;

    public SearchRecyclerAdapter(Context context, ArrayList<Product> searchList) {
        this.context = context;
        this.searchList = searchList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = searchList.get(position);
        holder.txtProductName.setText(product.getProductName());
        holder.txtProductPrice.setText(GetCurrency(product.getProductPrice() + " "));
        String imageURL = API_BASE_URL + product.getProductImageUrl();
        Picasso.with(context).load(imageURL).into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return searchList == null ? 0 : searchList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup root;
        ImageView imgProduct;
        TextView txtProductName;
        TextView txtProductPrice;

        public ViewHolder(View view) {
            super(view);
            root = (ViewGroup) view;
            imgProduct = view.findViewById(R.id.imgProduct);
            txtProductName = view.findViewById(R.id.txtProductName);
            txtProductPrice = view.findViewById(R.id.txtProductPrice);
        }
    }
}
