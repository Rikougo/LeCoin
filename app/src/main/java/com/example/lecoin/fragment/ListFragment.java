package com.example.lecoin.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import com.example.lecoin.HomeActivity;
import com.example.lecoin.R;
import com.example.lecoin.adapter.OfferAdapter;
import com.example.lecoin.lib.Offer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    private HomeActivity mParent;

    private androidx.appcompat.widget.SearchView mSearchView;
    private OfferAdapter mAdapter;
    private ArrayAdapter<String> mSpinnerAdapter;

    public ListFragment() { }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        mParent = (HomeActivity) getActivity();

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.OfferList);
        SearchView searchView = rootView.findViewById(R.id.list_search);

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println("New search : " + s);

                Task<QuerySnapshot> query;
                if (!s.isEmpty()) {
                    query = mParent.RequestAllOfferBySearch(s);
                } else {
                    query = mParent.RequestAllOffer();
                }

                query.addOnSuccessListener(task -> {
                    List<Offer> offers = task.toObjects(Offer.class);
                    Offer[] offersArray = new Offer[offers.size()];
                    offers.toArray(offersArray);
                    mAdapter.setData(offersArray);
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    this.onQueryTextSubmit(s);
                }

                return false;
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        Offer[] data = new Offer[] { };

        mAdapter = new OfferAdapter(data);

        // initial request all
        mParent.RequestAllOffer().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (mAdapter != null) {
                    List<Offer> offers = queryDocumentSnapshots.toObjects(Offer.class);
                    HashSet<String> tags = new HashSet<String>();

                    Offer[] offersArray = new Offer[offers.size()];

                    for(int i = 0; i < offers.size(); i++) {
                        offersArray[i] = offers.get(i);

                        if (offers.get(i).tags != null) tags.addAll(offers.get(i).tags);
                    }
                    mAdapter.setData(offersArray);
                }
            }
        });

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  { }
}