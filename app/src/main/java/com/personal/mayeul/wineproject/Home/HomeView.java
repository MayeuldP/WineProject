package com.personal.mayeul.wineproject.Home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.personal.mayeul.wineproject.Home.Presenter.HomePresenter;
import com.personal.mayeul.wineproject.Home.Presenter.IHomePresenter;
import com.personal.mayeul.wineproject.Home.View.CardView;
import com.example.mayeul.wineproject.R;
import com.personal.mayeul.wineproject.Home.View.IHomeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAYEUL on 15/12/2017.
 */

public class HomeView extends AppCompatActivity implements View.OnClickListener, IHomeView {
    private SwipeDeck cardStack;
    private FloatingActionButton iKnow;
    private FloatingActionButton iDidntKnow;
    private int number_elem;
    private List<ArrayList<String> > wine = new ArrayList<>();
    private ImageView info_button;
    private ImageView info_login;
    private IHomePresenter HomePresenter;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_deck);

        HomePresenter = new HomePresenter(getApplicationContext(), this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        info_button = (ImageView) toolbar.findViewById(R.id.toolbar_info);
        info_button.setOnClickListener(this);
        info_login = (ImageView) toolbar.findViewById(R.id.toolbar_login);
        info_login.setOnClickListener(this);
        setSupportActionBar(toolbar);
        toolbar.setElevation(15);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

         try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            number_elem = obj.getInt("number");
            JSONObject jsonArray = obj.getJSONObject("cards");
            for (int a = 1; a <= number_elem; a++)
            {
                ArrayList<String> current_wine = new ArrayList<>();
                current_wine.add(jsonArray.getJSONObject(Integer.toString(a)).getString("title"));
                current_wine.add(jsonArray.getJSONObject(Integer.toString(a)).getString("description"));
                wine.add(current_wine);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final CardView adapter = new CardView(wine, this);
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {Log.i("MainActivity", "card was swiped left, position in adapter: " + position);}
            @Override
            public void cardSwipedRight(int position) {Log.i("MainActivity", "card was swiped right, position in adapter: " + position);}
            @Override
            public void cardsDepleted() {
                Log.i("MainActivity", "no more cards");
            }
            @Override
            public void cardActionDown() {  }
            @Override
            public void cardActionUp() {}
        });

        iKnow = (FloatingActionButton) findViewById(R.id.i_knew);
        iDidntKnow = (FloatingActionButton) findViewById(R.id.i_didnt_knew);
        iKnow.setOnClickListener(this);
        iDidntKnow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.i_didnt_knew:
                cardStack.swipeTopCardLeft(700);
                break;
            case R.id.i_knew:
                cardStack.swipeTopCardRight(700);
                break;
            case R.id.toolbar_info:
                display_info_dialog();
                break;
            case R.id.toolbar_login:
                display_form_connexion();
                break;
        }
    }

    public void display_info_dialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.title_info_toolbar))
                .setMessage(getResources().getString(R.string.text_info_toolbar))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                });
                builder.create().show();
    }

    public void display_form_connexion()
    {
        AlertDialog.Builder password_dialog = new AlertDialog.Builder(this);
        password_dialog.setTitle(R.string.login_title_form);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_info_login, null);
        view.setBackgroundColor(getResources().getColor(R.color.white));
        password_dialog.setView(view);
//                dialog.setView(R.layout.dialog_change_password);

        final EditText mail = (EditText) view.findViewById(R.id.mail);
        final EditText password = (EditText) view.findViewById(R.id.password);
       // final Button existing_account = (Button) findViewById(R.id.got_account);
        password_dialog.setCancelable(false)
                .setPositiveButton(R.string.validate, null)
                .setNeutralButton(R.string.got_account, null)
                .setNegativeButton(R.string.cancel, null);
        alertDialog = password_dialog.create();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
        {
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("User = " + currentUser);
                    if (currentUser == null && (!mail.getText().toString().equals("") && !password.getText().toString().equals(""))){
                        System.out.println("Connexion loading...");
                        mAuth.createUserWithEmailAndPassword(mail.getText().toString().trim(), password.getText().toString().trim())
                                .addOnCompleteListener(HomeView.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            System.out.println("Connected...");
                                            display_snackbar("Vous êtes connecté");
                                            alertDialog.dismiss();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.d("FirebaseAuth", "onComplete" + task.getException().getMessage());
                                            if (task.getException().getMessage().equals("The email address is badly formatted."))
                                                mail.setError("Veuillez verifier votre email ou votre mot de passe");
                                            else if (task.getException().getMessage().equals("The email address is already in use by another account."))
                                            mail.setError("Email déjà existant");
                                            display_snackbar("Impossible de se connecter");
                                            System.out.println("Connexion failed...");
                                        }
                                    }
                                });
                    }
                    else
                        mail.setError("Email non correct");
                }
            });
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }
        else {
            password_dialog = null;
            password_dialog = new AlertDialog.Builder(this);
            password_dialog.setTitle(R.string.logged_title_form);
            int iend = currentUser.getEmail().indexOf("@");
            password_dialog.setMessage("Bonjour " + currentUser.getEmail().substring(0, iend) + " " + getResources().getString(R.string.logged_content));
            password_dialog.setCancelable(false)
                    .setPositiveButton(R.string.logout, null)
                    .setNegativeButton(R.string.cancel, null);
            alertDialog = password_dialog.create();
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();

                }});
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            display_snackbar("Vous êtes déjà connecté, page profil en cours de développement");
            //HomePresenter.ask_for_connexion(mail.toString(), password.toString());
        }
    }

    @Override
    public HomeView get_info_class()
    {
        return (HomeView.this);
    }

    @Override
    public void display_snackbar(String text)
    {
        //Snackbar.make(findViewById(R.id.coordinatorLayout), text, Snackbar.LENGTH_LONG);
        Toast.makeText(this, text,
                Toast.LENGTH_SHORT).show();    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("FrenchWine.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}