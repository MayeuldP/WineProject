package com.mayeul.wineproject.Home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mayeul.wineproject.Home.Presenter.HomePresenter;
import com.mayeul.wineproject.Home.Presenter.IHomePresenter;
import com.mayeul.wineproject.Home.View.IHomeView;
import com.mayeul.wineproject.Home.View.CardView;
import com.mayeul.wineproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAYEUL on 15/12/2017.
 */

public class HomeView extends Fragment  implements View.OnClickListener, IHomeView {
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
    private  FragmentActivity listener;
    private Context context;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.swipe_container, parent, false);
        context = getActivity();
        HomePresenter = new HomePresenter(context, this);
        cardStack = (SwipeDeck) rootView.findViewById(R.id.swipe_deck);

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

        final CardView adapter = new CardView(wine, context);
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {}
            @Override
            public void cardSwipedRight(int position) {}
            @Override
            public void cardsDepleted() { }
            @Override
            public void cardActionDown() {  }
            @Override
            public void cardActionUp() {}
        });

        iKnow = (FloatingActionButton) rootView.findViewById(R.id.i_knew);
        iDidntKnow = (FloatingActionButton) rootView.findViewById(R.id.i_didnt_knew);
        iKnow.setOnClickListener(this);
        iDidntKnow.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = getFragmentManager();
        Fragment fragment = null;
        Class fragmentClass;
        Bundle bundle = new Bundle();

        switch (view.getId())
        {
            case R.id.i_didnt_knew:
                cardStack.swipeTopCardLeft(700);
                HomePresenter.ask_saveOnDatabase(false);
                break;
            case R.id.i_knew:
                cardStack.swipeTopCardRight(700);
                HomePresenter.ask_saveOnDatabase(true);
                break;
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
        Toast.makeText(context, text,
                Toast.LENGTH_SHORT).show();    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("FrenchWine.json");
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