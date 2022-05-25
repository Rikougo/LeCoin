package com.example.lecoin.fragment.profile;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.example.lecoin.HomeActivity;
import com.example.lecoin.R;
import com.example.lecoin.fragment.LoginFragment;
import com.example.lecoin.lib.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HomeActivity mParent;

    public InformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InformationFragment newInstance(String param1, String param2) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);

        mParent = (HomeActivity) getActivity();

        //get all button & textfield
        Button updating = rootView.findViewById(R.id.update);
        TextInputLayout mailText = rootView.findViewById(R.id.user_info_mail);
        TextInputLayout nameText = rootView.findViewById(R.id.User_info_name);
        TextInputLayout posText = rootView.findViewById(R.id.User_info_localisation);
        Switch switchStatus = rootView.findViewById(R.id.status);

        //set all information in view
        mParent.getUserRef().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            mailText.getEditText().setText(mParent.authMail());
            nameText.getEditText().setText(user.getName());
            //posText.getEditText().setText(user.getLocalisation().toString());
            switchStatus.setChecked(user.getStatus());
        });


        //update firebase with new info when pressed
        updating.setOnClickListener(view -> {
            mParent.getUserRef().addOnSuccessListener(documentSnapshot -> {
                User user = documentSnapshot.toObject(User.class);
                if(!nameText.getEditText().getText().toString().equals("") && !nameText.getEditText().getText().toString().equals(user.name)){
                    mParent.updateName(nameText.getEditText().getText().toString());
                }
                if(!mailText.getEditText().getText().toString().equals("") && !mailText.getEditText().getText().toString().equals(mParent.authMail())){
                    mParent.updateMail(mailText.getEditText().getText().toString());
                }
                mParent.updateStatus(switchStatus.isChecked());
            });
        });

        return rootView;
    }
}