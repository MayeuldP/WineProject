package com.mayeul.wineproject.Login.Model;

import android.content.Context;

import com.personal.mayeul.wineproject.Login.LoginView;

/**
 * Created by MAYEUL on 15/01/2018.
 */

public interface ILoginModel {
    void startRegistration(String email, String password,  Context mFragment);
    void start_loggin_fragment(String email, String password, Context mFragment);
}
