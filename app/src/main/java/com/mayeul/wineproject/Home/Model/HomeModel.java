package com.mayeul.wineproject.Home.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mayeul.wineproject.Home.Presenter.IHomePresenter;

import java.util.Map;

/**
 * Created by MAYEUL on 27/01/2018.
 */

public class HomeModel implements IHomeModel {

    private IHomePresenter homePresenter;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private int curent_point;

    public HomeModel(IHomePresenter homePresenter)
    {
        this.homePresenter = homePresenter;
    }

    @Override
    public void requestSaveOnDatabase(final boolean value) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        System.out.println("requesting data..");
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child(uid).child("Points").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                long point =  (Long) dataSnapshot.getValue();
                curent_point = (int) point;
                System.out.println("Points" + point);
                if (value == true)
                    curent_point += 1;
                else
                    curent_point -= 1;
                mDatabase.child(uid).child("Points").setValue(curent_point);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
