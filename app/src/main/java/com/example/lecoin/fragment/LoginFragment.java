package com.example.lecoin.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lecoin.HomeActivity;
import com.example.lecoin.R;

public class LoginFragment extends Fragment {
    private HomeActivity mParent;

    private static final String ARG_MAIL = "Mail";
    private static final String ARG_PWD = "Password";

    private String mMail = "";
    private String mPwd = "";

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String mail, String pwd) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MAIL, mail);
        args.putString(ARG_PWD, pwd);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mMail = savedInstanceState.getString(ARG_MAIL);
            mPwd = savedInstanceState.getString(ARG_PWD);
        }

        mParent = (HomeActivity) getActivity();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_MAIL, mMail);
        outState.putString(ARG_PWD, mPwd);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  {
        ((EditText)getView().findViewById(R.id.signin_input_username)).setText(mMail);
        ((EditText)getView().findViewById(R.id.signin_input_password)).setText(mPwd);

        Button login = getView().findViewById(R.id.signin_login_button);

        login.setOnClickListener(view1 -> {
            String username = ((EditText)getView().findViewById(R.id.signin_input_username)).getText().toString();
            String password = ((EditText)getView().findViewById(R.id.signin_input_password)).getText().toString();
            if(!username.equals("") && !password.equals(""))
            mParent.LoginUser(username, password);
        });

        ((Button)getView().findViewById(R.id.signin_register_button)).setOnClickListener(view12 -> mParent.SwitchTo(RegisterFragment.class, null));
    }
}