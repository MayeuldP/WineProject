package com.mayeul.wineproject.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mayeul.wineproject.R;
import com.mayeul.wineproject.Login.Presenter.ILoginPresenter;
import com.mayeul.wineproject.Login.Presenter.LoginPresenter;

/**
 * Created by MAYEUL on 15/01/2018.
 */

public class LoginView  extends Fragment implements ILoginView, View.OnClickListener{

    private EditText email;
    private EditText password;
    private Button signin;
    private Button login;
    private ILoginPresenter loginPresenter;
    private LoginView mFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.login_view, null);

        loginPresenter = new LoginPresenter(this);
        email = (EditText) mView.findViewById(R.id.mail);
        password = (EditText) mView.findViewById(R.id.password);
        signin = (Button) mView.findViewById(R.id.register);
        login = (Button) mView.findViewById(R.id.loggin);
        signin.setOnClickListener(this);
        login.setOnClickListener(this);

        return mView;
    }

    @Override
    public void updateUi(int code)
    {
        System.out.println("Retour requete, code = " + code);
        email.requestFocus();
        switch (code)
        {
            /*Compte crée et connecté*/
            case 200:
                Toast.makeText(getContext(), "Connecté", Toast.LENGTH_LONG);
                getFragmentManager().popBackStackImmediate();
                break;
            /*Email invalide (format)*/
            case 404:
                email.setError("Format de l'email incorrect");
                break;
            /*Mot de passe trop simple (format)*/
            case 405:
                password.setError("Mot de passe trop simpliste");
                break;
            /*Email déjà existante*/
            case 406:
                email.setError("Email déjà existant");
                break;
            case 407:
                email.setError("Connexion echoué, vérifiez vos informations de connexion");
                break;
            case 408:
                email.setError("Echec de l'inscription");
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        if (getFragmentManager().getFragments().size() > 0)
            return true;
        else
            return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.register:
                System.out.println("LoginView : Register");
                loginPresenter.askForUserCreation(email.getText().toString().trim(), password.getText().toString().trim(), getContext());
                break;
            case R.id.loggin:
                System.out.println("LoginView : Loggin");
                loginPresenter.ask_for_loggin(email.getText().toString().trim(), password.getText().toString().trim(), getContext());
                break;
        }
    }
}