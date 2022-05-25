package com.example.lecoin.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lecoin.HomeActivity;
import com.example.lecoin.R;
import com.example.lecoin.lib.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private HomeActivity mParent;

    public SearchFragment() { }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParent = (HomeActivity) getActivity();

        mParent.RequestLastOffer().addOnSuccessListener(task -> {
            OfferFragment fragment = (OfferFragment) getChildFragmentManager().findFragmentById(R.id.search_offer_fragment);

            if (fragment != null) {
                List<Offer> offers = task.toObjects(Offer.class);
                fragment.SetData(offers.get(0));
            } else {
                System.err.println("Error retrieving fragment");
            }
        });

        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}