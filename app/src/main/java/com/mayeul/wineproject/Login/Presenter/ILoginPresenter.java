package com.mayeul.wineproject.Login.Presenter;

import android.content.Context;

import com.mayeul.wineproject.Login.LoginView;

/**
 * Created by MAYEUL on 15/01/2018.
 */

public interface ILoginPresenter {
    void askForUserCreation(String email, String password, Context mFragment);
    void ask_for_loggin(String email, String password, Context mFragment);
    void updateUi(int code);
}
