package com.example.lecoin.fragment.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lecoin.HomeActivity;
import com.example.lecoin.R;
import com.example.lecoin.lib.Offer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.text.Normalizer;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment implements TextWatcher {
    private HomeActivity mParent;

    private static final String ARG_TITLE   = "title";
    private static final String ARG_PRICE   = "price";
    private static final String ARG_TAGS    = "tags";
    private static final String ARG_CONTENT = "content";

    private String title = "";
    private String content = "";
    private float price = 0.0f;
    private String[] tags = new String[] {};

    private EditText titleInput;
    private EditText priceInput;
    private EditText contentInput;
    private EditText tagsInput;
    private Button   sendButton;

    public AddFragment() { }

    public static AddFragment newInstance(String title, String content, String[] tags, float price) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_CONTENT, content);
        args.putString(ARG_TAGS, TagsToString(tags));
        args.putFloat(ARG_PRICE, price);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            content = getArguments().getString(ARG_CONTENT);
            tags = ParseTags(getArguments().getString(ARG_TAGS));
            price = getArguments().getFloat(ARG_PRICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        mParent = (HomeActivity) getActivity();

        titleInput   = (EditText) rootView.findViewById(R.id.add_title_input);
        priceInput   = (EditText) rootView.findViewById(R.id.add_price_input);
        contentInput = (EditText) rootView.findViewById(R.id.add_content_input);
        tagsInput    = (EditText) rootView.findViewById(R.id.add_tags_input);
        sendButton   = (Button) rootView.findViewById(R.id.add_send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Offer offer = new Offer();
                offer.title = title.trim();
                String query = Normalizer.normalize(offer.title.toLowerCase(), Normalizer.Form.NFD);
                query = query.replaceAll("[^\\p{ASCII}]", "");
                offer.query =query;
                offer.content = content.trim();
                offer.price = price;
                offer.tags = Arrays.asList(tags);

                if (offer.title.isEmpty())
                    titleInput.setError("Title must not be empty.");

                if (offer.content.length() < 20)
                    contentInput.setError("Content must be at least 20 caracter long.");

                if (offer.price <= 0.0f)
                    priceInput.setError("Price must be over 0$.");
                
                if (offer.tags.size() <= 0)
                    tagsInput.setError("Please specify at least one tag.");

                if (offer.title.isEmpty() || offer.content.length() < 20 || offer.price < 0.0f || offer.tags.size() <= 0)
                    return;

                mParent.PostOffer(offer).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Clear();
                        } else {
                            titleInput.setError("Error on import");
                        }
                    }
                });
            }
        });

        // Text changed listener will keep updated every
        // attributes
        titleInput.addTextChangedListener(this);
        priceInput.addTextChangedListener(this);
        contentInput.addTextChangedListener(this);
        sendButton.addTextChangedListener(this);

        return rootView;
    }

    public void Clear() {
        titleInput.setText("");
        priceInput.setText("");
        contentInput.setText("");
        tagsInput.setText("");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {     }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String rawPrice = priceInput.getText().toString();
        String rawTags = tagsInput.getText().toString();

        tags    = ParseTags(rawTags);
        title   = titleInput.getText().toString();
        price   = rawPrice.isEmpty() ? 0.0f : Float.parseFloat(rawPrice);
        content = contentInput.getText().toString();
    }

    @Override
    public void afterTextChanged(Editable editable) { }

    private static String[] ParseTags(String raw) {
        if (raw.isEmpty()) return new String[] {};

        return raw.split(";");
    }

    private static String TagsToString(String[] tags) {
        StringBuilder result = new StringBuilder();

        for (String t : tags) result.append(t).append(";");

        return result.toString();
    }
}