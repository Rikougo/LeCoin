package com.example.lecoin.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.lecoin.HomeActivity;
import com.example.lecoin.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    private HomeActivity mParent;

    public RegisterFragment() { }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mParent = (HomeActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  {
        Button register = getView().findViewById(R.id.registerButton);

        // Handle register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mParent.SignupUser(
                        ((EditText)getView().findViewById(R.id.register_input_mail)).getText().toString(),
                        ((EditText)getView().findViewById(R.id.register_input_password)).getText().toString(),
                        ((EditText)getView().findViewById(R.id.register_input_username)).getText().toString(),
                        ((EditText)getView().findViewById(R.id.register_input_city)).getText().toString(),
                        ((CheckBox)getView().findViewById(R.id.checkBoxStatusR)).isChecked()
                );
            }
        });

        ((Button)getView().findViewById(R.id.goToLogin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mParent.SwitchTo(LoginFragment.class, null);
            }
        });
    }
}