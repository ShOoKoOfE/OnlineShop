package project.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.shokoofeadeli.onlineshop.R;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import project.models.Product;
import project.models.ProductViewModel;
import project.models.User;
import project.models.UserViewModel;
import project.repository.IResponseListener;
import project.repository.RetrieveRepositoryData;

import static project.base.BaseApplication.HideSoftKeyboar;
import static project.base.BaseApplication.getBaseApplication;
import static project.helper.PreferencesHelper.addSharedPreferences;
import static project.helper.PreferencesHelper.removeSharedPreferences;

public class EntrenceProfileFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.edtUserName)
    TextInputEditText edtUserName;
    @BindView(R.id.edtPassWord)
    TextInputEditText edtPassWord;
    @BindView(R.id.btnLogIn)
    Button btnLogIn;

    @Inject
    UserViewModel userViewModel;

    @Inject
    ProductViewModel productViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_enterence_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        getBaseApplication().getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUserName.getText().toString();
                String password = edtPassWord.getText().toString();
                if (username.isEmpty()) {
                    edtUserName.setError(getString(R.string.username_error));
                    return;
                }
                if (password.isEmpty()) {
                    edtPassWord.setError(getString(R.string.password_error));
                    return;
                }

                getBaseApplication().getUser().setUserUsername(username);
                getBaseApplication().getUser().setUserPassword(password);
                getUserInfo();
            }
        });
    }

    private void getUserInfo() {
        HideSoftKeyboar(getActivity());
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "login");
        map.put("username", getBaseApplication().getUser().getUserUsername());
        map.put("password", getBaseApplication().getUser().getUserPassword());
        RetrieveRepositoryData.getData(userViewModel.makeFutureUser(map), userViewModel.getLiveDataUser(map), new IResponseListener<User>() {
            @Override
            public void OnResponseComplete(User user) {
                if (user != null) {
                    user = user;
                    addSharedPreferences(user, getContext());
                    getUserBasketList();
                } else {
                    removeSharedPreferences(getContext());
                    Toast.makeText(getContext(), getString(R.string.warninig_login), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_search);
        if (item != null)
            item.setVisible(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getUserBasketList() {
        //productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "basket");
        map.put("user_id", "" + getBaseApplication().getUser().getUserId());
        RetrieveRepositoryData.getData(productViewModel.makeFutureProducts(map), productViewModel.getLiveDataProducts(map), new IResponseListener<ArrayList<Product>>() {
            @Override
            public void OnResponseComplete(ArrayList<Product> products) {
                getBaseApplication().setUserBasketList(products);
                ImageButton buttonProfile = getActivity().findViewById(R.id.btnProfile);
                buttonProfile.callOnClick();
            }
        });
    }
}
