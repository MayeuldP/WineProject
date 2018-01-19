package com.mayeul.wineproject.Home.View;

import com.mayeul.wineproject.Home.HomeView;

/**
 * Created by MAYEUL on 15/01/2018.
 */

public interface IHomeView {
    String loadJSONFromAsset();
    void display_snackbar(String text);
    HomeView get_info_class();
}
