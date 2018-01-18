package com.mayeul.wineproject.Login.Presenter;

import android.content.Context;

import com.mayeul.wineproject.Login.ILoginView;
import com.personal.mayeul.wineproject.Login.ILoginView;
import com.personal.mayeul.wineproject.Login.LoginView;
import com.personal.mayeul.wineproject.Login.Model.ILoginModel;
import com.personal.mayeul.wineproject.Login.Model.LoginModel;

/**
 * Created by MAYEUL on 15/01/2018.
 */

public class LoginPresenter implements ILoginPresenter{

    private ILoginView iLoginView;
    private ILoginModel iLoginModel;

    public LoginPresenter(ILoginView iLoginView)
    {
        this.iLoginView = iLoginView;
        iLoginModel = new LoginModel(this);
    }

    @Override
    public void askForUserCreation(String email, String password, Context mFragment)
    {
        System.out.println("LoginPresenter : askForUserCreation");
        iLoginModel.startRegistration(email, password, mFragment);
    }

    @Override
    public void ask_for_loggin(String email, String password, Context mFragment)
    {
        iLoginModel.start_loggin_fragment(email, password, mFragment);
    }

    @Override
    public void updateUi(int code)
    {
        System.out.println("Update UI = " + code);
        iLoginView.updateUi(code);
    }
}
