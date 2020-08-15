package project.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shokoofeadeli.onlineshop.R;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import project.models.Factor;
import project.models.Product;
import project.models.ProductViewModel;
import project.repository.IResponseListener;
import project.repository.RetrieveRepositoryData;

import static project.comon.NumberTextWatcherForThousand.GetCurrency;

public class FactorRecyclerAdapter extends RecyclerView.Adapter<FactorRecyclerAdapter.ViewHolder> {
    ArrayList<Factor> factorList;
    Context context;
    @Inject
    ProductViewModel productViewModel;

    public FactorRecyclerAdapter(Context context, ArrayList<Factor> factorList) {
        this.context = context;
        this.factorList = factorList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Factor factorSelected = factorList.get(position);
        holder.txtFactorNum.setText(factorSelected.getFactorNumber());
        holder.txtFactorDate.setText(context.getString(R.string.date) + factorSelected.getFactorDate());
        holder.txtFactorPrice.setText(GetCurrency(factorSelected.getFactorPrice() + " "));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.linearFactorDetails.getVisibility() == View.GONE) {
                    getfactorProductList(holder.txtFactorNum.getText().toString(), holder.lstFactorDetails);
                    holder.imgSpinnerFactor.setImageResource(R.drawable.ic_arrow_drop_up);
                    holder.linearFactorDetails.setVisibility(View.VISIBLE);
                    holder.linearFactorDetails.setAlpha(0.0f);
                    holder.linearFactorDetails.animate()
                            .alpha(1.0f)
                            .setListener(null);
                } else {
                    holder.imgSpinnerFactor.setImageResource(R.drawable.ic_arrow_drop);
                    holder.linearFactorDetails.animate()
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    holder.linearFactorDetails.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return factorList.size();
    }

    private void getfactorProductList(String FactorNum, RecyclerView lstFactorDetails) {
       // productViewModel = new ViewModelProvider((FragmentActivity) context).get(ProductViewModel.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "factor_details");
        map.put("factor_number", FactorNum);
        RetrieveRepositoryData.getData(productViewModel.makeFutureProducts(map), productViewModel.getLiveDataProducts(map), new IResponseListener<ArrayList<Product>>() {
            @Override
            public void OnResponseComplete(ArrayList<Product> products) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                lstFactorDetails.setLayoutManager(new LinearLayoutManager(context));
                FactorDetailsRecyclerAdapter adapter = new FactorDetailsRecyclerAdapter(context, products);
                lstFactorDetails.setLayoutManager(layoutManager);
                lstFactorDetails.setAdapter(adapter);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup root;
        ImageButton imgSpinnerFactor;
        TextView txtFactorPrice;
        TextView txtFactorNum;
        TextView txtFactorDate;
        LinearLayout linearFactorDetails;
        RecyclerView lstFactorDetails;

        public ViewHolder(View view) {
            super(view);
            root = (ViewGroup) view;
            imgSpinnerFactor = view.findViewById(R.id.imgSpinnerFactor);
            txtFactorPrice = view.findViewById(R.id.txtFactorPrice);
            txtFactorNum = view.findViewById(R.id.txtFactorNum);
            txtFactorDate = view.findViewById(R.id.txtFactorDate);
            linearFactorDetails = view.findViewById(R.id.linearFactorDetails);
            lstFactorDetails = view.findViewById(R.id.lstFactorDetails);
        }
    }
}
