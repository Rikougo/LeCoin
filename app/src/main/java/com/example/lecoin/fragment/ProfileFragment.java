package com.example.lecoin.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lecoin.HomeActivity;
import com.example.lecoin.R;
import com.example.lecoin.fragment.profile.AddFragment;
import com.example.lecoin.fragment.profile.InformationFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.GeoPoint;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private HomeActivity mParent;

    private TabLayout tabLayout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        mParent = (HomeActivity) getActivity();

        tabLayout = (TabLayout) rootView.findViewById(R.id.user_tabs);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println(tab.getId() + " " + R.id.user_info_tab + " " + R.id.user_add_page);

                String currentTabText = tab.getText().toString();
                if (currentTabText.equals("Informations")) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.user_fragment_view, InformationFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                } else if (currentTabText.equals("Add")) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.user_fragment_view, AddFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        AppCompatImageButton disconnectButton = rootView.findViewById(R.id.user_disconnect);

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mParent.GetAuth().signOut();
            }
        });

        return rootView;
    }

    public void updateName(String newName){
        DocumentReference updateUser = mParent.GetDatabase().collection("User").document(Objects.requireNonNull(mParent.GetAuth().getUid()));

        updateUser
                .update("name", newName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("User name succesfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.err.println("Error updating user name");
                    }
                });
    }

    public void updateStatus(int status){
        DocumentReference updateUser = mParent.GetDatabase().collection("User").document(Objects.requireNonNull(mParent.GetAuth().getUid()));

        updateUser
                .update("status", status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("User status succesfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.err.println("Error updating user status");
                    }
                });
    }

    public void updateBookmarks(int bookmark, boolean addRemove) {
        DocumentReference updateUser = mParent.GetDatabase().collection("User").document(Objects.requireNonNull(mParent.GetAuth().getUid()));

        if(addRemove){
            updateUser.update("bookmarks", FieldValue.arrayUnion("bookmark"));
        }
        else{
            updateUser.update("bookmarks", FieldValue.arrayRemove("bookmark"));
        }
    }

    public void updateLocalisation(GeoPoint localisation){
        DocumentReference updateUser = mParent.GetDatabase().collection("User").document(Objects.requireNonNull(mParent.GetAuth().getUid()));

        updateUser
                .update("localisation",  localisation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("User localisation succesfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.err.println("Error updating user localisation");
                    }
                });
    }
}