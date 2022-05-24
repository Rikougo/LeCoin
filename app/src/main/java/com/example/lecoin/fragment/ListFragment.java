package com.example.lecoin.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SearchView;

import com.example.lecoin.HomeActivity;
import com.example.lecoin.R;
import com.example.lecoin.adapter.OfferAdapter;
import com.example.lecoin.lib.Offer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    private HomeActivity mParent;

    private RecyclerView mRecyclerView;
    private OfferAdapter mAdapter;

    public ListFragment() { }

    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ((SearchView)rootView.findViewById(R.id.list_search)).setIconifiedByDefault(false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.OfferList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(layoutManager);

        Offer[] data = new Offer[] {
            new Offer(null, null, null),
            new Offer(null, null, null),
            new Offer(null, null, null),
        };

        mAdapter = new OfferAdapter(data);

        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  { }
}