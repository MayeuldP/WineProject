package com.mayeul.wineproject.InfoUser;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mayeul.wineproject.InfoUser.Presenter.IInfoUserPresenter;
import com.mayeul.wineproject.InfoUser.Presenter.InfoUserPresenter;
import com.mayeul.wineproject.R;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

/**
 * Created by MAYEUL on 18/01/2018.
 */

public class InfoUserView extends Fragment implements IInfoUserView, View.OnClickListener{

    private TextView info_user_mark;
    private TextView info_user_who_you_are;
    private Button loggout;
    private IInfoUserPresenter infoUserPresenter;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private PieChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.info_user_view, null);

        info_user_mark = (TextView) mView.findViewById(R.id.user_mark);
        //info_user_who_you_are = (TextView) mView.findViewById(R.id.user_profile);
        loggout = (Button) mView.findViewById(R.id.loggout);
        loggout.setOnClickListener(this);
        infoUserPresenter = new InfoUserPresenter(this);
        mChart = (PieChart) mView.findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        updateUi();
        return mView;
    }

    public void updateUi()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        System.out.println("requesting data..");
        if (currentUser != null)
        {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mDatabase.child(uid).child("Points").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                    long point =  (Long) dataSnapshot.getValue();
                    float score = ((float) point/30)*100;
                    info_user_mark.setText(info_user_mark.getText() + " " + String.format("%.2f", score) + " %.\n Soit l'équivalent de " + point + " questions connus au total.");
                    ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

                    entries.add(new PieEntry((int) score, "👍"));
                    entries.add(new PieEntry(100-score , "👎"));

                    PieDataSet dataSet = new PieDataSet(entries, "");
                    dataSet.setDrawIcons(false);
                    //dataSet.setColor(R.color.toolbar_color);
                    dataSet.addColor(R.color.colorBlack);
                    dataSet.addColor(R.color.white);
                    dataSet.setSliceSpace(3f);

                    PieData data = new PieData(dataSet);
                    data.setValueFormatter(new PercentFormatter());
                    data.setValueTextSize(11f);
                    data.setValueTextColor(Color.WHITE);
                    mChart.setData(data);
                    mChart.invalidate();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.loggout:
                System.out.println("On call loggout");
                infoUserPresenter.ask_for_loggout();
                break;
        }
    }

    @Override
    public void update_ui_loggout(int code)
    {
        switch (code)
        {
            case 200:
                System.out.println("On depop");
                getFragmentManager().popBackStackImmediate();
                break;
            case 404:
                getFragmentManager().popBackStackImmediate();
                break;
        }
    }
}
