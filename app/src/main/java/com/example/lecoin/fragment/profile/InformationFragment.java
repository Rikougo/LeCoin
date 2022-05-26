package com.example.lecoin.fragment.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.example.lecoin.HomeActivity;
import com.example.lecoin.R;
import com.example.lecoin.lib.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class InformationFragment extends Fragment {
    private HomeActivity mParent;

    public InformationFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InformationFragment.
     */
    public static InformationFragment newInstance() {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);

        mParent = (HomeActivity) getActivity();

        //get all button & textfield
        Button updating = rootView.findViewById(R.id.update);
        TextInputLayout mailText = rootView.findViewById(R.id.user_info_mail);
        TextInputLayout nameText = rootView.findViewById(R.id.User_info_name);
        TextInputLayout posText = rootView.findViewById(R.id.User_info_localisation);
        Switch switchStatus = rootView.findViewById(R.id.status);

        //set all information in view
        mParent.getUser().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            System.out.println(user);
            mailText.getEditText().setText(mParent.authMail());
            nameText.getEditText().setText(user.getName());
            //posText.getEditText().setText(user.getLocalisation().toString());
            switchStatus.setChecked(user.getStatus());
        });


        //update firebase with new info when pressed
        updating.setOnClickListener(view -> {
            mParent.getUser().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    User user = task.getResult().toObject(User.class);
                    if(!nameText.getEditText().getText().toString().equals("") && !nameText.getEditText().getText().toString().equals(user.name)){
                        mParent.updateName(nameText.getEditText().getText().toString());
                    }
                    if(!mailText.getEditText().getText().toString().equals("") && !mailText.getEditText().getText().toString().equals(mParent.authMail())){
                        mParent.updateMail(mailText.getEditText().getText().toString());
                    }
                    mParent.updateStatus(switchStatus.isChecked());
                } else {
                    System.err.println("Error on updating user.");
                }
            });
        });



        mParent.getAllOfferBySearch("chevre").addOnCompleteListener(task -> {
            System.out.println("no");
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    System.out.println(document.getId() + " => " + document.getData());
                }
            } else {
                System.out.println("Error getting documents: ");
            }
        });

        return rootView;
    }
}