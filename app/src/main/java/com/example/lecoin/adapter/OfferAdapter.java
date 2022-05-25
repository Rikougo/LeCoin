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
        private final TextView priceView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Element " + getAdapterPosition() + " clicked.");
                }
            });

            titleView    = (TextView) v.findViewById(R.id.offer_title);
            descView     = (TextView) v.findViewById(R.id.offer_description);
            priceView    = (TextView) v.findViewById(R.id.offer_price);
            creationView = (TextView) v.findViewById(R.id.offer_creation);
        }

        public TextView GetTitleView() {
            return titleView;
        }
        public TextView GetCreationView() {
            return creationView;
        }
        public TextView GetDescView() {
            return descView;
        }
        public TextView GetPriceView() { return priceView; }
    }

    public OfferAdapter(Offer[] offers) {
        mOfferData = offers;
    }

    public void setData(Offer[] newData) {
        mOfferData = newData;
        notifyDataSetChanged();
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
        Offer offer = mOfferData[position];
        holder.GetTitleView().setText(offer.GetTitle());
        holder.GetCreationView().setText(offer.GetCreationDate().toString());
        holder.GetDescView().setText(offer.GetDescription());
        holder.GetPriceView().setText(Float.toString(offer.price));
    }

    @Override
    public int getItemCount() {
        return mOfferData == null ? 0 : mOfferData.length;
    }
}
