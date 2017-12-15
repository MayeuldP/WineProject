package com.example.mayeul.wineproject.Home;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.mayeul.wineproject.Home.View.CardView;
import com.example.mayeul.wineproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAYEUL on 15/12/2017.
 */

public class HomeView extends AppCompatActivity implements View.OnClickListener {
    private SwipeDeck cardStack;
    private FloatingActionButton iKnow;
    private FloatingActionButton iDidntKnow;
    private int number_elem;
    private List<ArrayList<String> > wine = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_deck);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
             /*ArrayList ending = new ArrayList();
             ending.add(getResources().getString(R.string.card_title));
             ending.add(getResources().getString(R.string.card_description));
             wine.add(ending);*/
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
                cardStack.
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
        }
    }

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