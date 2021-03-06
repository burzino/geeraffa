package com.example.prova.ripetizioni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    String userPref, pwdPref, logged;
    boolean primoLog;
    BottomNavigationView bNV;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    JSonLogin JLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getApplicationContext().getSharedPreferences("LoginPref", 0);
        editor = pref.edit();
        userPref =pref.getString("user", null); // getting String
        pwdPref=pref.getString("pwd", null); // getting String
        primoLog=true;
        setActionbarTitle("GEERAFFA");
        bNV =(BottomNavigationView)findViewById(R.id.bottom_navigation);
        bNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.corsi:
                        setActionbarTitle("Corsi disponibili");
                        selectedFragment = CorsiFragment.newInstance();
                        break;
                    case R.id.prenotazioni:
                        setActionbarTitle("Prenotazioni future");
                        selectedFragment = PrenotazioniFragment.newInstance();
                        break;
                    case R.id.nuovaPrenotazione:
                        setActionbarTitle("Prenota");
                        selectedFragment = NuovaPrenotazioneFragment.newInstance();
                        break;
                    case R.id.account:
                        setActionbarTitle("Profilo e storico");
                        selectedFragment = profiloFragment.newInstance();

                        break;
                    case R.id.login:
                        setActionbarTitle("Login");
                        selectedFragment = LoginFragment.newInstance();


                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();



                return true;
            }
        });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, CorsiFragment.newInstance());
        transaction.commit();
        if(logged==null)
        try {
            checkLogin();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public void setActionbarTitle(String s)
    {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(s);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.colorSecondary));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

    public void toPrenotazioni(){
        Fragment  selectedFragment = PrenotazioniFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, PrenotazioniFragment.newInstance());
        transaction.commit();
    }
    public void checkLogin() throws ExecutionException, InterruptedException {
        pref = getApplicationContext().getSharedPreferences("LoginPref", 0);
        editor = pref.edit();
        userPref =pref.getString("user", null); // getting String
        pwdPref=pref.getString("pwd", null); // getting String
        if (userPref != null && pwdPref != null && userPref != "" && pwdPref != ""&&logged==null) {
            JLogin = new JSonLogin();
            String[] login = JLogin.doit(userPref,pwdPref);
            logged=login[0];
            primoLog = false;

        }
        else {
            bNV.getMenu().findItem(R.id.account).setVisible(false);
            bNV.getMenu().findItem(R.id.prenotazioni).setVisible(false);
            bNV.getMenu().findItem(R.id.nuovaPrenotazione).setVisible(false);
            bNV.getMenu().findItem(R.id.login).setVisible(true);
            logged=null;
            setActionbarTitle("Corsi disponibili");
            Fragment selectedFragment = CorsiFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
        }
        if(logged==null)
        {
            bNV.getMenu().findItem(R.id.account).setVisible(false);
            bNV.getMenu().findItem(R.id.prenotazioni).setVisible(false);
            bNV.getMenu().findItem(R.id.nuovaPrenotazione).setVisible(false);
        }
        else{
            bNV.getMenu().findItem(R.id.account).setVisible(true);
            bNV.getMenu().findItem(R.id.prenotazioni).setVisible(true);
            bNV.getMenu().findItem(R.id.nuovaPrenotazione).setVisible(true);
            bNV.getMenu().findItem(R.id.login).setVisible(false);
            bNV.setSelectedItemId(R.id.corsi);
            //se non si è loggati ma ci sono user e pwd salvati sul dispositivo, avvia il login automaticamente

        }
    }

}
