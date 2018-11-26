package com.example.geeraffa.ripetizioni;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String userPref, pwdPref, logged;
    boolean primoLog;
    TextView txtNomeCognome,txtEmail;
    ListView lstCorsi;
    CorsiAdapter adapterCorsi;
    NavigationView navigationView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lstCorsi=(ListView)findViewById(R.id.lstCorsi);
        //shared preferences per dati login
        //prende utente e password se sono già salvati sul dispositivo
        pref = getApplicationContext().getSharedPreferences("LoginPref", 0);
        editor = pref.edit();
        userPref =pref.getString("user", null); // getting String
        pwdPref=pref.getString("pwd", null); // getting String
        primoLog=true;


        //prende i parametri arrivati da LoginTestActivity
        Intent myintent = getIntent();
        logged = myintent.getStringExtra("logged");
        String nome = myintent.getStringExtra("nome");
        String cognome = myintent.getStringExtra("cognome");
        String email = myintent.getStringExtra("email");

        //BOTTONE IN BASSO A SINISTRA
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Menù laterale con l'item menu e i dati del profilo
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Item menù del Drawer(lista bottoni del menù)
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //visualizzazione dati utente nel header
        View headerView = navigationView.getHeaderView(0);
        txtNomeCognome = (TextView) headerView.findViewById(R.id.nomeCognomeDrawer);
        txtEmail = (TextView) headerView.findViewById(R.id.emailDrawer);

        //se arrivano i dati dall'activity login, visualizza i dati del profilo e permette di effettuare prenotazioni
        if (logged!=null) {
            txtNomeCognome.setText(nome + " " + cognome);
            txtEmail.setText(email);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.action_signin).setVisible(false);
            nav_Menu.findItem(R.id.nav_prenotazioni).setVisible(true);

        }else
        //se non si è loggati ma ci sono user e pwd salvati sul dispositivo, avvia il login automaticamente
        if (userPref!=null && pwdPref!=null &&userPref!=""&&pwdPref!="" ) {
            Intent myIntent = new Intent(Main2Activity.this, LoginTestActivity.class);
            myIntent.putExtra("user", userPref); //Optional parameters
            myIntent.putExtra("pwd", pwdPref); //Optional parameters
            Main2Activity.this.startActivity(myIntent);
            primoLog=false;
        }
        mostraCorsi();
    }
    //chiude il menu se si preme il pulsante back del dispositivo
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    //Avvia la funzione del bottone clickato nel menu in alto a destra(non nel drawer)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "apri impostazioni", Toast.LENGTH_LONG).show();
            return true;
        }else if (id==R.id.action_logout){
            txtNomeCognome.setText("");
            txtEmail.setText("");
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.action_signin).setVisible(true);
            nav_Menu.findItem(R.id.nav_prenotazioni).setVisible(false);
            editor.remove("user");
            editor.remove("pwd");
            editor.commit();
            logged=null;

        }

        return super.onOptionsItemSelected(item);
    }
    //Avvia la funzione del bottone clickato nel drawer
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_prenotazioni) {
            // Handle the camera action
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id== R.id.action_signin)
        {
            Intent myIntent = new Intent(Main2Activity.this, LoginTestActivity.class);
            Main2Activity.this.startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void mostraCorsi(){
        final ArrayList<Corso> listCorsi = new ArrayList<Corso>();
        adapterCorsi = new CorsiAdapter(Main2Activity.this,R.layout.corso_layout,listCorsi);
        lstCorsi.setAdapter(adapterCorsi);
        JSonCorsi JCorsi=new JSonCorsi();
        try {
            String[] corsi = JCorsi.doit();
            for (int i=0;i<corsi.length;i++){
                String[] corso = corsi[i].split("-");
                listCorsi.add(new Corso(corso[0],corso[1]));
            }
            adapterCorsi.notifyDataSetChanged();



        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
