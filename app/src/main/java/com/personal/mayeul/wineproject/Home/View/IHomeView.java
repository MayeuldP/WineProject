package com.personal.mayeul.wineproject.Home.View;

import com.personal.mayeul.wineproject.Home.HomeView;

/**
 * Created by MAYEUL on 15/01/2018.
 */

public interface IHomeView {
    void display_form_connexion();
    String loadJSONFromAsset();
    void display_info_dialog();
    void display_snackbar(String text);
    HomeView get_info_class();
}
