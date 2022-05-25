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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.w3c.dom.Text;

import java.text.DateFormat;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
    private Offer[] mOfferData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView titleView;
        public final TextView creationView;
        public final TextView descView;
        public final TextView priceView;
        public final ChipGroup chipGroup;

        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(view -> {
                System.out.println("Element " + getAdapterPosition() + " clicked.");
            });

            titleView    = (TextView) v.findViewById(R.id.offer_title);
            descView     = (TextView) v.findViewById(R.id.offer_description);
            priceView    = (TextView) v.findViewById(R.id.offer_price);
            creationView = (TextView) v.findViewById(R.id.offer_creation);
            chipGroup    = (ChipGroup) v.findViewById(R.id.offer_tags);
        }
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
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

        holder.titleView.setText(offer.GetTitle());

        holder.creationView.setText(shortDateFormat.format(offer.created_at));
        holder.descView.setText(offer.GetDescription());
        holder.priceView.setText(Float.toString(offer.price));

        holder.chipGroup.removeAllViews();
        if (offer.tags != null) {
            for(String s : offer.tags) {
                Chip chip = new Chip(holder.chipGroup.getContext());
                chip.setText(s);
                holder.chipGroup.addView(chip);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mOfferData == null ? 0 : mOfferData.length;
    }
}
