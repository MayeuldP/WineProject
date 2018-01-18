package com.mayeul.wineproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.mayeul.wineproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mayeul.wineproject.Home.HomeView;
import com.personal.mayeul.wineproject.Home.HomeView;
import com.personal.mayeul.wineproject.InfoUser.InfoUserView;
import com.personal.mayeul.wineproject.Login.LoginView;

/**
 * Created by MAYEUL on 18/01/2018.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private FragmentManager fm = getSupportFragmentManager();
    private ImageView info_button;
    private ImageView info_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        info_button = (ImageView) toolbar.findViewById(R.id.toolbar_info);
        info_button.setOnClickListener(this);
        info_login = (ImageView) toolbar.findViewById(R.id.toolbar_login);
        info_login.setOnClickListener(this);
        //Initialize home fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.home_placeholder, new HomeView());
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        Class loadClass;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        switch (view.getId())
        {
            case R.id.toolbar_home:
                System.out.println("InfoOOO HOMME !");
                loadClass = HomeView.class;
                break;
            case R.id.toolbar_login:
                if (currentUser == null)
                    loadClass = LoginView.class;
                else
                    loadClass = InfoUserView.class;
                break;
            case R.id.toolbar_info:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getResources().getString(R.string.title_info_toolbar))
                        .setMessage(getResources().getString(R.string.text_info_toolbar))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        });
                builder.create().show();
                loadClass = null;
                break;
            default:
                loadClass = HomeView.class;
                break;
        }
        try {
            if (view.getId() != R.id.toolbar_info) {
                fragment = (Fragment) loadClass.newInstance();
                fragment.setArguments(bundle);
                fm.popBackStack();
                fm.beginTransaction().replace(R.id.home_placeholder, fragment).addToBackStack(loadClass.getName()).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
