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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.shokoofeadeli.onlineshop.R;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import project.helper.ToastHelper;
import project.models.User;
import project.models.UserViewModel;
import project.repository.IResponseListener;
import project.repository.RetrieveRepositoryData;

import static project.base.BaseApplication.HideSoftKeyboar;
import static project.base.BaseApplication.getBaseApplication;
import static project.helper.PreferencesHelper.addSharedPreferences;
import static project.helper.PreferencesHelper.removeSharedPreferences;

public class RegisterProfileFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.edtName)
    TextInputEditText edtName;
    @BindView(R.id.edtPass)
    TextInputEditText edtPass;
    @BindView(R.id.edtMobile)
    TextInputEditText edtMobile;
    @BindView(R.id.edtEmail)
    TextInputEditText edtEmail;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @Inject
    UserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_profile, container, false);
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
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtName.getText().toString();
                String password = edtPass.getText().toString();
                String email = edtEmail.getText().toString();
                String mobile = edtMobile.getText().toString();
                if (username.isEmpty()) {
                    edtName.setError(getString(R.string.username_error));
                    return;
                }
                if (password.isEmpty()) {
                    edtPass.setError(getString(R.string.password_error));
                    return;
                }
                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edtEmail.setError(getString(R.string.email_error));
                    return;
                }
                if (mobile.isEmpty()) {
                    edtMobile.setError(getString(R.string.mobile_error));
                    return;
                }

                getBaseApplication().getUser().setUserUsername(username);
                getBaseApplication().getUser().setUserPassword(password);
                getBaseApplication().getUser().setUserEmail(email);
                getBaseApplication().getUser().setUserMobile(mobile);
                registerUser();
            }
        });
    }

    private void registerUser() {
        HideSoftKeyboar(getActivity());
       // userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "register");
        map.put("username", getBaseApplication().getUser().getUserUsername());
        map.put("password", getBaseApplication().getUser().getUserPassword());
        map.put("mobile", getBaseApplication().getUser().getUserMobile());
        map.put("email", getBaseApplication().getUser().getUserEmail());
        RetrieveRepositoryData.getData(userViewModel.makeFutureUser(map), userViewModel.getLiveDataUser(map), new IResponseListener<User>() {
            @Override
            public void OnResponseComplete(User user) {
                if (user != null) {
                    getBaseApplication().setUser(user);
                    addSharedPreferences(getBaseApplication().getUser(), getContext());
                    ImageButton buttonProfile = getActivity().findViewById(R.id.btnProfile);
                    buttonProfile.callOnClick();
                } else {
                    removeSharedPreferences(getContext());
                    edtName.setText("");
                    edtPass.setText("");
                    edtEmail.setText("");
                    edtMobile.setText("");
                    ToastHelper.ShowToast(getContext(), getString(R.string.warning_username));
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
}
