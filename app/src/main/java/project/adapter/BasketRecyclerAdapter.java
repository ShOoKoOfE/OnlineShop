package project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.shokoofeadeli.onlineshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import project.models.Product;
import project.models.ProductViewModel;
import project.repository.IResponseListener;
import project.repository.RetrieveRepositoryData;
import project.ui.activity.HomeActivity;
import project.ui.activity.ProductDetailActivity;

import static project.base.BaseApplication.getBaseApplication;
import static project.comon.NumberTextWatcherForThousand.GetCurrency;
import static project.comon.NumberTextWatcherForThousand.getDecimalFormattedString;
import static project.service.ServiceGenerator.API_BASE_URL;

public class BasketRecyclerAdapter extends RecyclerView.Adapter<BasketRecyclerAdapter.ViewHolder> {
    @Inject
    ProductViewModel productViewModel;
    private ArrayList<Product> productsList;
    private Context context;
    private RecyclerView lstSpinner;
    private TextView txtTotalPrice;

    public BasketRecyclerAdapter(Context context, ArrayList<Product> productsList, RecyclerView lstSpinner, TextView txtTotalPrice) {
        this.context = context;
        this.productsList = productsList;
        this.lstSpinner = lstSpinner;
        this.txtTotalPrice = txtTotalPrice;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_basket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.imgSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBaseApplication().getHandler().post(new Runnable() {
                    public void run() {
                        lstSpinner.setVisibility(View.VISIBLE);
                        ArrayList<Integer> spinnerArray = new ArrayList<Integer>();
                        for (int i = 1; i <= productsList.get(position).getProductCount(); i++) {
                            spinnerArray.add(i);
                        }
                        SpinnerRecyclerAdapter spinnerAdapter = new SpinnerRecyclerAdapter(productsList.get(position), spinnerArray, context, lstSpinner, txtTotalPrice, BasketRecyclerAdapter.this);
                        lstSpinner.setAdapter(spinnerAdapter);
                    }
                });
            }
        });
        holder.txtProductName.setText(productsList.get(position).getProductName());
        holder.txtProductPrice.setText(GetCurrency(productsList.get(position).ProductPrice() + ""));
        holder.txtSpinnerCount.setText(productsList.get(position).getBasketProductCount() + "");
        String imageURL = API_BASE_URL + productsList.get(position).getProductImageUrl();
        Picasso.with(context).load(imageURL).into(holder.imgProduct);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // productViewModel = new ViewModelProvider((FragmentActivity) context).get(ProductViewModel.class);
                HashMap<String, String> map = new HashMap<>();
                map.put("action", "delete_basket_product");
                map.put("user_id", "" + getBaseApplication().getUser().getUserId());
                map.put("product_id", "" + productsList.get(position).getProductId());
                RetrieveRepositoryData.getData(productViewModel.makeFutureProducts(map), productViewModel.getLiveDataProducts(map), new IResponseListener<ArrayList<Product>>() {
                    @Override
                    public void OnResponseComplete(ArrayList<Product> products) {
                        productsList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, productsList.size());
                        notifyDataSetChanged();
                        long totalPrice = 0;
                        for (Product product : productsList) {
                            totalPrice += product.getProductPrice();
                        }
                        txtTotalPrice.setText(getDecimalFormattedString("" + totalPrice));
                        if (productsList.size() == 0) {
                            getBaseApplication().getHandler().post(new Runnable() {
                                public void run() {
                                    Intent intent = new Intent(context, HomeActivity.class);
                                    intent.putExtra("FRAGMENT", "Basket");
                                    context.startActivity(intent);
                                }
                            });
                        }
                    }
                });
            }
        });
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBaseApplication().getHandler().post(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(context, ProductDetailActivity.class);
                        intent.putExtra("SELECTED_PRODUCT_ID", productsList.get(position).getProductId());
                        context.startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup root;
        ImageView imgProduct;
        TextView txtProductName;
        TextView txtProductPrice;
        ImageButton imgDelete;
        ImageButton imgSpinner;
        TextView txtSpinnerCount;

        public ViewHolder(View view) {
            super(view);
            root = (ViewGroup) view;
            imgProduct = view.findViewById(R.id.imgProduct);
            txtProductName = view.findViewById(R.id.txtProductName);
            txtProductPrice = view.findViewById(R.id.txtProductPrice);
            imgDelete = view.findViewById(R.id.imgDelete);
            imgSpinner = view.findViewById(R.id.imgSpinner);
            txtSpinnerCount = view.findViewById(R.id.txtSpinnerCount);
        }
    }
}
