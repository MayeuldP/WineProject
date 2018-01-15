package com.personal.mayeul.wineproject.Home.Presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.personal.mayeul.wineproject.Home.View.IHomeView;

/**
 * Created by MAYEUL on 15/01/2018.
 */

public class HomePresenter implements IHomePresenter {
    private Context context;
    private IHomeView iHomeView;
    private FirebaseAuth mAuth;

    public HomePresenter(Context context, IHomeView iHomeView)
    {
        this.context = context;
        this.iHomeView = iHomeView;
    }

    @Override
    public void ask_for_connexion(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        System.out.println("User = " + currentUser);
        if (currentUser == null)
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(iHomeView.get_info_class(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                System.out.println("Connected...");
                                iHomeView.display_snackbar("Vous êtes connecté");
                            } else {
                                // If sign in fails, display a message to the user.
                                iHomeView.display_snackbar("Impossible de se connecter");
                                //updateUI(null);
                            }
                        }
                    });
    }
}
