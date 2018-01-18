package com.mayeul.wineproject.Home.Presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
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
    }
}
