//Creazione datepicker con JQuery UI
$( function() {
          $( "#dataPren" ).datepicker({
                showOn: "button",
                buttonImage: "img/calendar.png",
                buttonText: "Seleziona una data",
                minDate: 0,
                showAnim: "clip",
                closeText: "Chiudi",
                //Imposto la lingua italiana del calendario!
                prevText: "&#x3C;Prec",
                nextText: "Succ&#x3E;",
                currentText: "Oggi",
                monthNames: [ "Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno",
                        "Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre" ],
                monthNamesShort: [ "Gen","Feb","Mar","Apr","Mag","Giu",
                        "Lug","Ago","Set","Ott","Nov","Dic" ],
                dayNames: [ "Domenica","Lunedì","Martedì","Mercoledì","Giovedì","Venerdì","Sabato" ],
                dayNamesShort: [ "Dom","Lun","Mar","Mer","Gio","Ven","Sab" ],
                dayNamesMin: [ "Do","Lu","Ma","Me","Gi","Ve","Sa" ],
                weekHeader: "Sm",
                dateFormat: "dd/mm/yy",
                firstDay: 1,
                isRTL: false,
                showMonthAfterYear: false,
                yearSuffix: ""
            });
});


//Settaggio chiamata AJAX
var xhrObj = setXMLHttpRequest();
var url_;
var corso_;
var dataPren;
var docente;

var orari = ['15', '16', '17', '18', '19'];

//HTTP request
function setXMLHttpRequest() {
    var xhr = null;
    if (window.XMLHttpRequest) {      // browser standard con supporto nativo
      xhr = new XMLHttpRequest();}
    else if (window.ActiveXObject) {   // browser MS Internet Explorer 6 o precedente - ActiveX
      xhr = new ActiveXObject("Microsoft.XMLHTTP");}
    return xhr;
}

//Onload del body
function salvaDati(corso, cmbDoc, url)
{
    url_ = url;
    corso_ = corso;
    dataPren = document.getElementById("dataPren");
    orderselect(document.getElementById("selCorso"));
    popolaCmbDocenti(corso, cmbDoc);
}

//Onchange del corso - AJAX - Onchange del corso - AJAX
function popolaCmbDocenti(corso, cmbDocenti)
{   
    //Disabilito e azzero i campi del form
    
    document.getElementById("selDocente").disabled = true;
    document.getElementById("dataPren").disabled = true;
    
    document.getElementById("selDocente").innerHTML = "";
    document.getElementById("dataPren").value = "";
    document.getElementById("divOrari").innerHTML = "";
    
    xhrObj.open("GET", url_+"&corso="+corso+"&cmb=true", true);
    
    xhrObj.onreadystatechange = popolaDocenti;

    xhrObj.send(null);
}

//onchange selAlle - popola selDalle
function popolaCmbOrario(oraInizio)
{   
    var selAlle = document.getElementById("selAlle");
    selAlle.disabled = true;
    selAlle.innerHTML = "";
    
    
    for (var i = 0; i < orari.length; i++) {
        if(orari[i] > oraInizio)
            selAlle.innerHTML += "<option>" + orari[i] + "</option>";
    }
    selAlle.disabled = false;
}

//Onchange del docente
function cambioDocente()
{
    document.getElementById("divOrari").innerHTML = "";
    document.getElementById("dataPren").value = "";
}

//Ricevo risposta dal SERVER per popolare cmb dei Docenti - onchange selCorso
function popolaDocenti()
{
    //Ricevuta risposta dal SERVER!!
    if (xhrObj.readyState == 4) {
        var risp = xhrObj.responseText;
        
        var arrDocenti = JSON.parse(risp);
        var cmbDocenti = document.getElementById("selDocente");
        var titolo = document.getElementById("titolo");
        
        document.getElementById("divOrari").innerHTML = "";
        
        titolo.innerHTML = "";
        titolo.innerHTML += "PRENOTA LA TUA RIPETIZIONE DI " + arrDocenti[0].corso.toString().toUpperCase();
        
        cmbDocenti.innerHTML = "";
        
        //Non è stato trovato alcun docente che insegna il corso selezionato
        if(arrDocenti[0].corso == "none")
        {
            document.getElementById("selDocente").disabled = true;
            document.getElementById("dataPren").disabled = true;
            alert("Nessun docente insegna " + document.getElementById("selCorso").value);
        }
        else
        {
            for(var i=0; i<arrDocenti.length; i++)
            {
                cmbDocenti.innerHTML += 
                          "<option value='" + arrDocenti[i].idDoc + "'>"
                        + arrDocenti[i].nome + "</option>";
            }
            
            orderselect(document.getElementById("selDocente"));
            document.getElementById("selDocente").selectedIndex = 0;
            document.getElementById("selDocente").disabled = false;
        }
    }
}

//onchange dataPren - AJAX
function cambioData(docenteSel){    
    
    docente = docenteSel;
    ricercaRipetizioni();
}

//Richiamo SERVLET per capire le fasce orarie disponibili - onchange dataPren
function ricercaRipetizioni()
{
    xhrObj.open("GET", url_+"&corso="+corso_
            +"&docente="+docente+"&data="+dataPren.value, true);
    
    xhrObj.onreadystatechange = popolaOrari;

    xhrObj.send(null);
}

function popolaOrari()
{
    if (xhrObj.readyState == 4) {
        var risp = xhrObj.responseText;
        var arrPren = JSON.parse(risp);
        var divOrari = document.getElementById("divOrari");
        divOrari.innerHTML = "";
        var i = 0;
        var j=0;
        
        //Creo label fascia oraria
        divOrari.innerHTML += "<label> Selezionare orari tra quelli disponibili:</label><br>";
        
        //Creo label per le fasce orarie
        for(i=0; i<8; i++)
        {
            if(i%2==0)
            {
                divOrari.innerHTML += "<div id='aaa' style='width: 12.5%; float:left'>|" + orari[j] + "</div>"
                j++;
            }
            else if(i == 7)
            {
                divOrari.innerHTML += "<div style='width: 12.5%; float:left; text-align:right'>" + orari[j] + "|</div>"
                j++;
            }
            else
                divOrari.innerHTML += "<div style='width: 12.5%; height:20px; float:left'><div/>"
        }
        
        //Creo fasce orarie tutte libere
        for(i=0; i<4; i++)
        {   
            divOrari.innerHTML +=
                      "<button type='button' onclick='selezionaOrario(this)' "
                    + "class='btn' id='" + orari[i] + "'>LIBERA</button>";
            document.getElementById(orari[i]).classList.add("btn-success");
        }
        
        
        j = 0;
        if(arrPren[0].oraInizio != "none")
        {
            //MEtto occupate le fasce orarie già prenotate
            for(i=0; i<4; i++)
            {
                var btn = document.getElementById(orari[i]);
                
                if(j<arrPren.length && 
                        (arrPren[j].oraInizio == btn.id))
                {
                    document.getElementById(orari[i]).classList.remove("btn-success");
                    document.getElementById(orari[i]).classList.add("btn-danger");
                    document.getElementById(orari[i]).style.cursor = "default";
                    document.getElementById(orari[i]).innerHTML = "OCCUPATA";
                    document.getElementById(orari[i]).disabled = true;
                    j++;
                }
            }
            j=0;
            //Controllo se c'è una fascia oraria di più di un'ora (es. 15-17) per
            //segnare occupate le fasce orarie comprese tra le due ore di inizio e fine
            for(i=0; i<4; i++)
            {
                var btn = document.getElementById(orari[i]);
                
                if(j<arrPren.length && 
                        (btn.id > arrPren[j].oraInizio && btn.id < arrPren[j].oraFine))
                {   
                    document.getElementById(orari[i]).classList.remove("btn-success");
                    document.getElementById(orari[i]).classList.add("btn-danger");
                    document.getElementById(orari[i]).style.cursor = "default";
                    document.getElementById(orari[i]).innerHTML = "OCCUPATA";
                    document.getElementById(orari[i]).disabled = true;
                    j++;
                }
            }
            
        }
    }   
}

//Onclick orari
function selezionaOrario(btn)
{
    //Se è libera --> diventa SELEZINONATA
    if(btn.className == "btn btn-success")
    {
        btn.classList.remove("btn-success");
        btn.classList.add("btn-primary");
        btn.innerHTML = "";
        btn.innerHTML += "SELEZIONATA";
        document.getElementById("btnPren").disabled = false;
    }
    //Se era selezionata --> torna libera
    else
    {                 
        btn.classList.remove("btn-primary");
        btn.classList.add("btn-success");
        btn.innerHTML = "";
        btn.innerHTML += "LIBERA";        
        
        //Se non ci sono orari selezionati --> PRENOTA DISABILITATO
        var i;
        var sel=0;
        
        for(i=0; i<4; i++)
        {
            var btnCheck = document.getElementById(orari[i]);
            //Se trova un selezionato
            if(btnCheck.className == "btn btn-primary")
                sel = 1;
        }
        
        if(sel == 0)
           document.getElementById("btnPren").disabled = true;
    }
}

//Onclick PRENOTA
function setFasciaOraria()
{
    var fasciaOraria = document.forms[0].elements["orario"];
    
    var oraSel= "";
    //Trovo tutti i btn selezionati
    for(i=0; i<4; i++)
    {
        var btnCheck = document.getElementById(orari[i]);
        //Orario selezionato
        if(btnCheck.className == "btn btn-primary")
            oraSel += btnCheck.id + "-";
    }
    var fasce = oraSel.split("-");   
    
    //Setto il parametro con gli orari selezionati
    fasciaOraria.value = fasce;
    
    
    var dataPren = document.forms[0].elements["data"];
    dataPren.value = document.getElementById("dataPren").value;
}

//Ordina alfabeticamente le select
function orderselect(selElem)
{
    var tmpAry = new Array();
    var selected = selElem.value;
    for (var i=0;i<selElem.options.length;i++) {
        tmpAry[i] = new Array();
        tmpAry[i][0] = selElem.options[i].text;
        tmpAry[i][1] = selElem.options[i].value;
    }
    tmpAry.sort();
    while (selElem.options.length > 0) {
        selElem.options[0] = null;
    }
    for (var i=0;i<tmpAry.length;i++) {
        var op = new Option(tmpAry[i][0], tmpAry[i][1]);
        selElem.options[i] = op;
    }
    selElem.value = selected;
}