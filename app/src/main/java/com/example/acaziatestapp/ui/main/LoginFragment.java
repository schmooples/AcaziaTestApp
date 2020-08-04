package com.example.acaziatestapp.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acaziatestapp.R;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private EditText etxPw;
    private AutoCompleteTextView etxEmail;
    private Button btnLogin;
    private CheckBox cbSave;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.etxEmail = view.findViewById(R.id.etx_email);
        this.etxPw = view.findViewById(R.id.etx_password);
        this.btnLogin = view.findViewById(R.id.btn_login);
        this.cbSave = view.findViewById(R.id.cb_save);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mViewModel.setAutoCompleteEmail(this.etxEmail, this.etxPw, getViewLifecycleOwner(), getContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.login(etxEmail.getText().toString(), etxPw.getText().toString(), getContext(), cbSave.isChecked(),
                        getActivity());

            }
        });
    }
}
