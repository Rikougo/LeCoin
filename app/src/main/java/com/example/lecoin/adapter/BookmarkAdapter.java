package com.example.lecoin.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lecoin.R;
import com.example.lecoin.lib.Offer;

public class BookmarkAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
    private Offer[] mOfferData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView creationView;
        private final TextView descView;
        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Element " + getAdapterPosition() + " clicked.");
                }
            });

            titleView = (TextView) v.findViewById(R.id.offer_title);
            creationView = (TextView) v.findViewById(R.id.offer_creation);
            descView = (TextView) v.findViewById(R.id.offer_description);
        }

        public TextView getTitleView() {
            return titleView;
        }
        public TextView getCreationView() {
            return creationView;
        }
        public TextView getDescView() {
            return descView;
        }
    }

    public BookmarkAdapter(Offer[] offers) {
        mOfferData = offers;
    }

    @NonNull
    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_offer, parent, false);

        return new OfferAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.ViewHolder holder, int position) {
        Offer offer = mOfferData[position];
        holder.getTitleView().setText(offer.GetTitle());
        holder.getCreationView().setText(offer.GetCreationDate().toString());
        holder.getDescView().setText(offer.GetDescription());
    }

    @Override
    public int getItemCount() {
        return mOfferData == null ? 0 : mOfferData.length;
    }
}
