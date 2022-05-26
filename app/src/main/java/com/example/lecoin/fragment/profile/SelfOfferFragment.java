package com.example.lecoin.fragment.profile;

import android.app.TaskInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lecoin.HomeActivity;
import com.example.lecoin.R;
import com.example.lecoin.adapter.OfferAdapter;
import com.example.lecoin.lib.Offer;

import java.util.List;

public class SelfOfferFragment extends Fragment {
    private HomeActivity mParent;

    public SelfOfferFragment() {}
    public static SelfOfferFragment newInstance() {
        SelfOfferFragment fragment = new SelfOfferFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_self_offer, container, false);

        mParent = (HomeActivity) getActivity();

        RecyclerView recyclerView = rootView.findViewById(R.id.user_self_offer_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        OfferAdapter adapter = new OfferAdapter(new Offer[]{});
        recyclerView.setAdapter(adapter);

        mParent.getAllOfferByUser(mParent.GetAuth().getUid()).addOnSuccessListener(task -> {
            List<Offer> offers = task.toObjects(Offer.class);
            System.out.println("Loaded everything " + offers.toString());
            Offer[] offersArray = new Offer[offers.size()]; offers.toArray(offersArray);
            adapter.setData(offersArray);
        });

        return rootView;
    }
}