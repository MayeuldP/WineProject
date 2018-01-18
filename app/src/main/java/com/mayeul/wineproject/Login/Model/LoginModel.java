package com.mayeul.wineproject.Login.Model;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.personal.mayeul.wineproject.Home.HomeView;
import com.personal.mayeul.wineproject.InfoUser.InfoUserView;
import com.personal.mayeul.wineproject.Login.LoginView;
import com.personal.mayeul.wineproject.Login.Presenter.ILoginPresenter;
import com.personal.mayeul.wineproject.MainActivity;

/**
 * Created by MAYEUL on 15/01/2018.
 */

public class LoginModel implements ILoginModel {

    private ILoginPresenter iLoginPresenter;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public LoginModel(ILoginPresenter iLoginPresenter)
    {
        this.iLoginPresenter = iLoginPresenter;
    }

    @Override
    public void startRegistration(String email, String password, Context mFragment)
    {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        System.out.println("LoginModel : startRegistration / Info => " + currentUser);

        /*Aucun utilisateur enregistré*/
        if (currentUser == null && (!email.equals("") && !password.equals("")))
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) mFragment, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                iLoginPresenter.updateUi(200);
                                System.out.println("Connected...");
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d("FirebaseAuth", "onComplete" + task.getException().getMessage());
                                if (task.getException().getMessage().equals("The email address is badly formatted."))
                                    iLoginPresenter.updateUi(404);
                                    //updateUi();
                                    //email.setError("Veuillez verifier votre email ou votre mot de passe");
                                else if (task.getException().getMessage().equals("The email address is already in use by another account."))
                                    iLoginPresenter.updateUi(406);
                                System.out.println("Connexion failed...");
                            }
                        }
                    });
        }
        else
            iLoginPresenter.updateUi(408);
    }

    @Override
    public void start_loggin_fragment(String email, String password, Context mFragment)
    {
        mAuth = FirebaseAuth.getInstance();
        if (!email.equals("") && !password.equals(""))
            mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener((Activity) mFragment, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        iLoginPresenter.updateUi(200);
                    } else {
                        // If sign in fails, display a message to the user.
                        System.out.println("Authentification failed");
                        iLoginPresenter.updateUi(407);
                    }
                }
            });
        else
            iLoginPresenter.updateUi(407);
    }
}
