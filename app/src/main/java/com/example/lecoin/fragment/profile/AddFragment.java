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
import com.google.android.material.textfield.TextInputLayout;
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

    private TextInputLayout titleInput;
    private TextInputLayout priceInput;
    private TextInputLayout contentInput;
    private TextInputLayout tagsInput;
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

        titleInput   = (TextInputLayout) rootView.findViewById(R.id.add_title_input);
        priceInput   = (TextInputLayout) rootView.findViewById(R.id.add_price_input);
        contentInput = (TextInputLayout) rootView.findViewById(R.id.add_content_input);
        tagsInput    = (TextInputLayout) rootView.findViewById(R.id.add_tags_input);
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
                            priceInput.setErrorEnabled(false);
                            contentInput.setErrorEnabled(false);
                            titleInput.setErrorEnabled(false);
                            tagsInput.setErrorEnabled(false);
                            Clear();
                        } else {
                            titleInput.setError("Eror with server.");
                            priceInput.setError("Eror with server.");
                            contentInput.setError("Eror with server.");
                            tagsInput.setError("Eror with server.");
                            sendButton.setError("Eror with server.");
                        }
                    }
                });
            }
        });

        // Text changed listener will keep updated every
        // attributes
        titleInput.getEditText().addTextChangedListener(this);
        priceInput.getEditText().addTextChangedListener(this);
        contentInput.getEditText().addTextChangedListener(this);
        tagsInput.getEditText().addTextChangedListener(this);
        sendButton.addTextChangedListener(this);

        return rootView;
    }

    public void Clear() {
        titleInput.getEditText().setText("");
        priceInput.getEditText().setText("");
        contentInput.getEditText().setText("");
        tagsInput.getEditText().setText("");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {     }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        priceInput.setErrorEnabled(false);
        contentInput.setErrorEnabled(false);
        titleInput.setErrorEnabled(false);
        tagsInput.setErrorEnabled(false);

        String rawPrice = priceInput.getEditText().getText().toString().replaceAll("[^\\d.]", "");
        String rawTags = tagsInput.getEditText().getText().toString();

        tags    = ParseTags(rawTags);
        title   = titleInput.getEditText().getText().toString();
        price   = rawPrice.isEmpty() ? 0.0f : Float.parseFloat(rawPrice);
        content = contentInput.getEditText().getText().toString();
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