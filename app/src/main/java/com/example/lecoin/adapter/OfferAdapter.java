package com.example.lecoin.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import com.example.lecoin.R;
import com.example.lecoin.lib.Offer;

import org.w3c.dom.Text;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
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

    public OfferAdapter(Offer[] offers) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_offer, parent, false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTitleView().setText("Value at " + position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
