package com.example.lecoin.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.lecoin.HomeActivity;
import com.example.lecoin.R;
import com.example.lecoin.lib.Offer;
import com.example.lecoin.lib.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ZoomedofferfferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZoomedofferfferFragment extends Fragment {
    private HomeActivity mParent;
    private String ID;

    public ZoomedofferfferFragment() {}
    public static ZoomedofferfferFragment newInstance() {
        ZoomedofferfferFragment fragment = new ZoomedofferfferFragment();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        mParent = (HomeActivity) getActivity();

        //layout
        TextView offerTitle = rootView.findViewById(R.id.offer_title);
        TextView offerPrice = rootView.findViewById(R.id.offer_price);
        TextView offerCreation = rootView.findViewById(R.id.offer_creation);
        TextView offerDescription = rootView.findViewById(R.id.offer_description);
        TextView contacterText = rootView.findViewById(R.id.contacter_text);
        Button bookmark = rootView.findViewById(R.id.bookmark);
        Button contacterButton = rootView.findViewById(R.id.contacter_button);

        //string contact
        AtomicReference<String> contact = null;

        //date format
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyyMMdd  HH:mm");


        mParent.getOffer(ID).addOnSuccessListener(documentSnapshot -> {
            Offer offer = documentSnapshot.toObject(Offer.class);
            assert offer != null;
            offerTitle.setText(offer.GetTitle());
            offerPrice.setText(String.valueOf(offer.price));
            offerCreation.setText(df.format(offer.GetCreationDate()));
            offerDescription.setText(offer.GetDescription());
            mParent.getUserRef(offer.author.getId()).get().addOnSuccessListener(documentSnapshot2 -> {
                User user = documentSnapshot2.toObject(User.class);
                contact.set(mParent.authMail());
            });;
        });

        contacterButton.setOnClickListener(view -> {
            contacterText.setText((CharSequence) contact);
        });

        return rootView;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}