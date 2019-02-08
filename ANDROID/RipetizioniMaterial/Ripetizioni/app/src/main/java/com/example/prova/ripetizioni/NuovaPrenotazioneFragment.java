package com.example.prova.ripetizioni;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NuovaPrenotazioneFragment extends Fragment implements View.OnClickListener{

    View RootView;
    Spinner lstCorsi;
    Spinner lstDocenti;
    Button btnPrenota,btn1516,btn1617,btn1718,btn1819;
    String corsoSel;
    EditText txtData;
    boolean b1516=false,b1617=false,b1718=false,b1819=false;
    public static NuovaPrenotazioneFragment newInstance() {
        NuovaPrenotazioneFragment fragment = new NuovaPrenotazioneFragment();
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView=inflater.inflate(R.layout.fragment_nuova_prenotazione,container,false);
        btnPrenota=(Button)RootView.findViewById(R.id.material_button2);
        btn1516=(Button)RootView.findViewById(R.id.btn1516);
        btn1516.setEnabled(false);
        btn1516.setOnClickListener(this);
        btn1617=(Button)RootView.findViewById(R.id.btn1617);
        btn1617.setEnabled(false);
        btn1617.setOnClickListener(this);
        btn1718=(Button)RootView.findViewById(R.id.btn1718);
        btn1718.setEnabled(false);
        btn1718.setOnClickListener(this);
        btn1819=(Button)RootView.findViewById(R.id.btn1819);
        btn1819.setEnabled(false);
        btn1819.setOnClickListener(this);

        btnPrenota.setOnClickListener(this);
        lstCorsi=(Spinner)RootView.findViewById(R.id.lstCorsi);
        lstDocenti=(Spinner)RootView.findViewById(R.id.lstDocenti);
        txtData=(EditText) RootView.findViewById(R.id.txtData);
        txtData.setRawInputType(InputType.TYPE_NULL);
        lstDocenti.setClickable(false);
        return  RootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Corso corso=new Corso();
        final List<Corso> corsi;
        corsi=corso.getCorsi();
        ArrayAdapter<Corso> spinnerCorsiAdapter=new ArrayAdapter<Corso>(  getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item,corsi);
        lstCorsi.setAdapter(spinnerCorsiAdapter);


        lstCorsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                corsoSel=lstCorsi.getSelectedItem().toString();
                Docente docente= new Docente(lstCorsi.getSelectedItem().toString());
                List<Docente> docenti;
                docenti=docente.getDocenti();
                ArrayAdapter<Docente> spinnerDocentiAdapter=new ArrayAdapter<Docente>(  getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item,docenti);
                lstDocenti.setAdapter(spinnerDocentiAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                btn1516.setBackgroundColor(Color.GREEN);
                                btn1516.setEnabled(true);
                                btn1819.setBackgroundColor(Color.GREEN);
                                btn1819.setEnabled(true);
                                btn1718.setBackgroundColor(Color.GREEN);
                                btn1718.setEnabled(true);
                                btn1617.setBackgroundColor(Color.GREEN);
                                btn1617.setEnabled(true);
                                String[] ore=new String[100];
                                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                                Date date = new Date(year-1900, monthOfYear, dayOfMonth);
                                String dayOfWeek = simpledateformat.format(date);


                                if(dayOfWeek.equals("Sunday")){
                                    Toast.makeText(getActivity().getBaseContext(),"Domenica non disponibile",Toast.LENGTH_SHORT).show();
                                }else if (dayOfWeek.equals("Saturday")){
                                    Toast.makeText(getActivity().getBaseContext(),"Sabato non disponibile",Toast.LENGTH_SHORT).show();
                                }else if (dayOfMonth<c.get(Calendar.DAY_OF_MONTH)){
                                    Toast.makeText(getActivity().getBaseContext(),"Data non valida",Toast.LENGTH_SHORT).show();
                                }else {
                                    txtData.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                    String docentePerSplit=lstDocenti.getSelectedItem().toString();
                                    String[] split = docentePerSplit.split(" ");
                                    try {
                                        Prenotazione prenotazioneOre= new Prenotazione(split[0],txtData.getText().toString());
                                        ore=prenotazioneOre.getOreJson();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    for (int i=0;i<ore.length;i++)
                                    {
                                        String ini,fin;
                                        String[] splitOre=ore[i].split("-");
                                        ini=splitOre[0];
                                        fin=splitOre[1];
                                        switch (ini) {
                                            case "15":
                                                    if (fin.equals("16")) {
                                                        btn1516.setBackgroundColor(Color.RED);
                                                        btn1516.setEnabled(false);
                                                    }else if (fin.equals("17"))
                                                    {
                                                        btn1516.setBackgroundColor(Color.RED);
                                                        btn1516.setEnabled(false);
                                                        btn1617.setBackgroundColor(Color.RED);
                                                        btn1617.setEnabled(false);
                                                    }else if (fin.equals("18"))
                                                    {
                                                        btn1516.setBackgroundColor(Color.RED);
                                                        btn1516.setEnabled(false);
                                                        btn1718.setBackgroundColor(Color.RED);
                                                        btn1718.setEnabled(false);
                                                        btn1617.setBackgroundColor(Color.RED);
                                                        btn1617.setEnabled(false);
                                                    }else if (fin.equals("19"))
                                                    {
                                                        btn1516.setBackgroundColor(Color.RED);
                                                        btn1516.setEnabled(false);
                                                        btn1819.setBackgroundColor(Color.RED);
                                                        btn1819.setEnabled(false);
                                                        btn1718.setBackgroundColor(Color.RED);
                                                        btn1718.setEnabled(false);
                                                        btn1617.setBackgroundColor(Color.RED);
                                                        btn1617.setEnabled(false);
                                                    }
                                                break;
                                            case "16":
                                                 if (fin.equals("17"))
                                                {
                                                   btn1617.setBackgroundColor(Color.RED);
                                                    btn1617.setEnabled(false);
                                                }else if (fin.equals("18"))
                                                {

                                                    btn1718.setBackgroundColor(Color.RED);
                                                    btn1718.setEnabled(false);
                                                    btn1617.setBackgroundColor(Color.RED);
                                                    btn1617.setEnabled(false);
                                                }else if (fin.equals("19"))
                                                {

                                                    btn1819.setBackgroundColor(Color.RED);
                                                    btn1819.setEnabled(false);
                                                    btn1718.setBackgroundColor(Color.RED);
                                                    btn1718.setEnabled(false);
                                                    btn1617.setBackgroundColor(Color.RED);
                                                    btn1617.setEnabled(false);
                                                }
                                                break;
                                            case "17":
                                                if (fin.equals("18"))
                                                {
                                                    btn1718.setBackgroundColor(Color.RED);
                                                    btn1718.setEnabled(false);
                                                }else if (fin.equals("19"))
                                                {
                                                    btn1819.setBackgroundColor(Color.RED);
                                                    btn1819.setEnabled(false);
                                                    btn1718.setBackgroundColor(Color.RED);
                                                    btn1718.setEnabled(false);
                                                }
                                                break;
                                            case "18":
                                                if (fin.equals("19"))
                                                {
                                                   btn1819.setBackgroundColor(Color.RED);
                                                   btn1819.setEnabled(false);
                                                }
                                                break;
                                        }
                                    }
                                }
                                }


                        }, mYear, mMonth, mDay);


                datePickerDialog.show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.material_button2:
                String dataI="",dataF="";

                SharedPreferences pref =getActivity().getSharedPreferences("LoginPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                String idUser =pref.getString("userId", null); // getting String
                String docentePerSplit=lstDocenti.getSelectedItem().toString();
                String[] split = docentePerSplit.split(" ");
                String idDocente=split[0];
                corsoSel=lstCorsi.getSelectedItem().toString();
                String data =txtData.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date= null;
                try {
                    date = format.parse(data);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String day          = (String) DateFormat.format("dd",   date);
                String monthNumber  = (String) DateFormat.format("MM",   date);
                String year         = (String) DateFormat.format("yyyy", date);
                data=year+"/"+monthNumber+"/"+day;

                if (b1516==true)
                {
                    dataI=data+" 15:00:00";
                    if (b1617==true)
                    {
                        if (b1718==true)
                        {
                            if (b1819==true)
                            {
                                dataF=data+" 19:00:00";
                            }
                            else
                                dataF=data+" 18:00:00";
                        }else
                            dataF=data+" 17:00:00";
                    }else
                        dataF=data+" 16:00:00";
                }else if (b1617==true)
                {
                    dataI=data+" 16:00:00";
                    if (b1718==true)
                    {
                        if (b1819==true)
                        {
                            dataF=data+" 19:00:00";
                        }
                        else
                            dataF=data+" 18:00:00";
                    }else
                        dataF=data+" 17:00:00";
                }else if (b1718==true)
                {
                    dataI=data+" 17:00:00";
                    if (b1819==true)
                    {
                        dataF=data+" 19:00:00";
                    }
                    else
                        dataF=data+" 18:00:00";
                }else if (b1819==true)
                {
                    dataI=data+" 18:00:00";
                    dataF=data+" 19:00:00";
                }


                JSonNuovaPrenotazione nuovaPrenotazione = new JSonNuovaPrenotazione();
                try {
                    nuovaPrenotazione.doit(idUser,idDocente,corsoSel,dataI,dataF);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int duration = Toast.LENGTH_LONG;

                Toast.makeText(getActivity(), "prenotazione effettuata",duration).show();


                break;
            case R.id.btn1516:
                if (b1516==false)
                {
                    b1516=true;
                    btn1516.setBackgroundColor(Color.YELLOW);
                }
                else {
                    b1516=false;
                    btn1516.setBackgroundColor(Color.GREEN);
                }

                break;
            case R.id.btn1617:
                if (b1617==false)
                {
                    b1617=true;
                    btn1617.setBackgroundColor(Color.YELLOW);
                }
                else {
                    b1617=false;
                    btn1617.setBackgroundColor(Color.GREEN);
                }

                break;
            case R.id.btn1718:
                if (b1718==false)
                {
                    b1718=true;
                    btn1718.setBackgroundColor(Color.YELLOW);
                }
                else {
                    b1718=false;
                    btn1718.setBackgroundColor(Color.GREEN);
                }

                break;
            case R.id.btn1819:
                if (b1819==false)
                {
                    b1819=true;
                    btn1819.setBackgroundColor(Color.YELLOW);
                }
                else {
                    b1819=false;
                    btn1819.setBackgroundColor(Color.GREEN);
                }

                break;
        }
    }
}
