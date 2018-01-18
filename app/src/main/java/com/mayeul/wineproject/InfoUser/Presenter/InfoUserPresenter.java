package com.mayeul.wineproject.InfoUser.Presenter;

import com.personal.mayeul.wineproject.InfoUser.IInfoUserView;
import com.personal.mayeul.wineproject.InfoUser.Model.IInfoUserModel;
import com.personal.mayeul.wineproject.InfoUser.Model.InfoUserModel;

/**
 * Created by MAYEUL on 18/01/2018.
 */

public class InfoUserPresenter implements IInfoUserPresenter {

    private IInfoUserView infoUserView;
    private IInfoUserModel infoUserModel;

    public InfoUserPresenter(IInfoUserView infoUserView)
    {
        this.infoUserView = infoUserView;
        infoUserModel = new InfoUserModel(this);
    }

    @Override
    public void ask_for_loggout() {
        infoUserModel.start_loggout();
    }

    @Override
    public void result_loggout(int code)
    {
        switch (code)
        {
            /*Utilisateur deconnecté*/
            case 200:
                infoUserView.update_ui_loggout(200);
                break;
            /*Aucun utilisateur connecté (ne devrait pas arriver, mais au cas ou*/
            case 404:
                infoUserView.update_ui_loggout(404);
                break;
        }
    }
}
