package com.mayeul.wineproject.InfoUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mayeul.wineproject.R;
import com.mayeul.wineproject.InfoUser.Presenter.IInfoUserPresenter;
import com.mayeul.wineproject.InfoUser.Presenter.InfoUserPresenter;
import com.personal.mayeul.wineproject.InfoUser.Presenter.IInfoUserPresenter;
import com.personal.mayeul.wineproject.InfoUser.Presenter.InfoUserPresenter;

/**
 * Created by MAYEUL on 18/01/2018.
 */

public class InfoUserView extends Fragment implements IInfoUserView, View.OnClickListener{

    private TextView info_user_mark;
    private TextView info_user_who_you_are;
    private Button loggout;
    private IInfoUserPresenter infoUserPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.info_user_view, null);

        info_user_mark = (TextView) mView.findViewById(R.id.user_mark);
        info_user_who_you_are = (TextView) mView.findViewById(R.id.user_profile);
        loggout = (Button) mView.findViewById(R.id.loggout);
        loggout.setOnClickListener(this);
        infoUserPresenter = new InfoUserPresenter(this);

        return mView;
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
