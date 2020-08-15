package project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.shokoofeadeli.onlineshop.R;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import project.models.Product;
import project.models.ProductViewModel;
import project.repository.IResponseListener;
import project.repository.RetrieveRepositoryData;

import static project.base.BaseApplication.getBaseApplication;

public class SpinnerRecyclerAdapter extends RecyclerView.Adapter<SpinnerRecyclerAdapter.ViewHolder> {
    @Inject
    ProductViewModel productViewModel;
    private ArrayList<Integer> spinnerArray;
    private Product product;
    private Context context;
    private RecyclerView lstSpinner;
    private TextView txtTotalPrice;
    private BasketRecyclerAdapter basketAdapter;

    public SpinnerRecyclerAdapter(Product product, ArrayList<Integer> spinnerArray, Context context, RecyclerView lstSpinner, TextView txtTotalPrice, BasketRecyclerAdapter basketAdapter) {
        this.product = product;
        this.spinnerArray = spinnerArray;
        this.context = context;
        this.lstSpinner = lstSpinner;
        this.txtTotalPrice = txtTotalPrice;
        this.basketAdapter = basketAdapter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_spinner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtCountNum.setText("" + spinnerArray.get(position));
        holder.txtCountNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Product item : getBaseApplication().getUserBasketList()) {
                    if (item.equals(product)) {
                        item.setBasketProductCount(spinnerArray.get(position));
                        product.setBasketProductCount(spinnerArray.get(position));
                       // productViewModel = new ViewModelProvider((FragmentActivity) context).get(ProductViewModel.class);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("action", "update_basket_count");
                        map.put("user_id", "" + getBaseApplication().getUser().getUserId());
                        map.put("product_id", "" + product.getProductId());
                        map.put("basket_product_count", "" + product.getBasketProductCount());
                        RetrieveRepositoryData.getData(productViewModel.makeFutureProducts(map), productViewModel.getLiveDataProducts(map), new IResponseListener<ArrayList<Product>>() {
                            @Override
                            public void OnResponseComplete(ArrayList<Product> products) {
                                long totalPrice = 0;
                                for (Product product : products) {
                                    totalPrice += product.ProductPrice();
                                }
                                final long total = totalPrice;
                                getBaseApplication().getHandler().post(new Runnable() {
                                    public void run() {
                                        txtTotalPrice.setText(total + " " + getBaseApplication().getContext().getString(R.string.toman));
                                        basketAdapter.notifyDataSetChanged();
                                        lstSpinner.setVisibility(View.GONE);
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return spinnerArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup root;
        TextView txtCountNum;

        public ViewHolder(View view) {
            super(view);
            root = (ViewGroup) view;
            txtCountNum = view.findViewById(R.id.txtCountNum);
        }
    }
}
