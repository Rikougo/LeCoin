package com.example.lecoin.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lecoin.HomeActivity;
import com.example.lecoin.R;
import com.example.lecoin.lib.Offer;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.DateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OfferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfferFragment extends Fragment {
    private HomeActivity mParent;

    private TextView titleView;
    private TextView dateView;
    private TextView contentView;
    private TextView priceView;
    private ChipGroup chipGroup;
    private Button openButton;
    private Button bookmarkButton;

    public OfferFragment() {}
    public static OfferFragment newInstance() {
        OfferFragment fragment = new OfferFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_offer, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstance) {
        mParent = (HomeActivity) getActivity();

        dateView    = rootView.findViewById(R.id.offer_creation);
        priceView   = rootView.findViewById(R.id.offer_price);
        titleView   = rootView.findViewById(R.id.offer_title);
        contentView = rootView.findViewById(R.id.offer_description);

        chipGroup = rootView.findViewById(R.id.offer_tags);
        chipGroup.removeAllViews();
    }

    void SetData(Offer offer) {
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

        dateView.setText(shortDateFormat.format(offer.created_at));
        priceView.setText(Float.toString(offer.price));
        titleView.setText(offer.title);
        contentView.setText(offer.content);

        chipGroup.removeAllViews();

        for(String s : offer.tags) {
            Chip chip = new Chip(getActivity());
            chip.setText(s);
            chipGroup.addView(chip);
        }
    }
}