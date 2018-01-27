package com.mayeul.wineproject.Home.Presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.mayeul.wineproject.Home.Model.HomeModel;
import com.mayeul.wineproject.Home.View.IHomeView;

/**
 * Created by MAYEUL on 15/01/2018.
 */

public class HomePresenter implements IHomePresenter {
    private Context context;
    private IHomeView iHomeView;
    private HomeModel homeModel;

    public HomePresenter(Context context, IHomeView iHomeView)
    {
        this.context = context;
        this.iHomeView = iHomeView;
        homeModel = new HomeModel(this);
    }

    @Override
    public void ask_saveOnDatabase(boolean value) {
        homeModel.requestSaveOnDatabase(value);
    }
}
