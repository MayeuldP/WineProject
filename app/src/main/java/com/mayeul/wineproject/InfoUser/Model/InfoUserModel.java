package com.mayeul.wineproject.InfoUser.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mayeul.wineproject.InfoUser.Presenter.IInfoUserPresenter;
import com.personal.mayeul.wineproject.InfoUser.Presenter.IInfoUserPresenter;

/**
 * Created by MAYEUL on 18/01/2018.
 */

public class InfoUserModel implements IInfoUserModel{

    private IInfoUserPresenter infoUserPresenter;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public InfoUserModel(IInfoUserPresenter infoUserPresenter)
    {
        this.infoUserPresenter = infoUserPresenter;
    }

    @Override
    public void start_loggout() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null)
        {
            FirebaseAuth.getInstance().signOut();
            infoUserPresenter.result_loggout(200);
        }
        else
            infoUserPresenter.result_loggout(404);
    }
}
