package com.example.lecoin.fragment.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lecoin.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment implements TextWatcher {
    private static final String ARG_TITLE   = "title";
    private static final String ARG_TAGS    = "tags";
    private static final String ARG_CONTENT = "content";

    private String title;
    private String content;
    private String tags;

    private EditText titleInput;
    private EditText contentInput;
    private EditText tagsInput;
    private Button sendButton;

    public AddFragment() { }

    public static AddFragment newInstance(String title, String content, String tags) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_CONTENT, content);
        args.putString(ARG_TAGS, tags);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            content = getArguments().getString(ARG_CONTENT);
            tags = getArguments().getString(ARG_TAGS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        titleInput = (EditText) rootView.findViewById(R.id.add_title_input);
        contentInput = (EditText) rootView.findViewById(R.id.add_content_input);
        sendButton = (Button) rootView.findViewById(R.id.add_send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(title + "\n" + content);
            }
        });

        titleInput.addTextChangedListener(this);
        contentInput.addTextChangedListener(this);

        return rootView;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {     }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        title = titleInput.getText().toString();
        content = contentInput.getText().toString();
    }

    @Override
    public void afterTextChanged(Editable editable) { }
}