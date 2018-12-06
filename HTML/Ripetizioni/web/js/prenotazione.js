//Creazione datepicker con JQuery UI
$( function() {
          $( "#dataPren" ).datepicker({
                dateFormat: "dd/mm/yy",
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

$( function() {
    $( "#datepicker" ).datepicker( "option",
        $.datepicker.regional[ "it"] );
});

$(document).ready( function() {
    
    
    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);

    var today = now.getFullYear()+"-"+(month)+"-"+(day) ;

   //document.getElementById("dataPren").setAttribute("min", today);
   
   orderselect(document.getElementById("selCorso"));
});

//Settaggio chiamata AJAX
var xhrObj = setXMLHttpRequest();
var url_;
var corso_;
var dataPren;
var docente;

var orari = ['15:00', '16:00', '17:00', '18:00',  '19:00'];

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
    //dataPren.min = dataPren.value;
    popolaCmbDocenti(corso, cmbDoc);
}

//Onchange del corso - AJAX - Onchange del corso - AJAX
function popolaCmbDocenti(corso, cmbDocenti)
{   
    //Disabilito e azzero i campi del form
    
    document.getElementById("selDocente").disabled = true;
    document.getElementById("dataPren").disabled = true;
    document.getElementById("selDalle").disabled = true;
    document.getElementById("selAlle").disabled = true;
    
    document.getElementById("selDocente").innerHTML = "";
    document.getElementById("dataPren").value = "";
    document.getElementById("selDalle").innerHTML = "";
    document.getElementById("selAlle").innerHTML = "";
    
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


//Ricevo risposta dal SERVER per popolare cmb dei Docenti - onchange selCorso
function popolaDocenti()
{
    //Ricevuta risposta dal SERVER!!
    if (xhrObj.readyState == 4) {
        var risp = xhrObj.responseText;
        
        var arrDocenti = JSON.parse(risp);
        var cmbDocenti = document.getElementById("selDocente");
        var titolo = document.getElementById("titolo");
        titolo.innerHTML = "";
        titolo.innerHTML += "PRENOTA LA TUA RIPETIZIONE DI " + arrDocenti[0].corso.toString().toUpperCase();
        
        cmbDocenti.innerHTML = "";
        
        //Non è stato trovato alcun docente che insegna il corso selezionato
        if(arrDocenti[1] == null)
        {
            document.getElementById("selDocente").disabled = true;
            document.getElementById("dataPren").disabled = true;
            document.getElementById("selDalle").disabled = false;
            document.getElementById("selAlle").disabled = false;
            alert("Nessun docente insegna " + arrDocenti[0].corso);
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
            document.getElementById("selDocente").disabled = false;
            document.getElementById("dataPren").disabled = false;
        }
    }
}

//onchange dataPren - AJAX
function cambioData(docenteSel){    
    var date = new Date(dataPren.value);
    var day = date.getDay();
    docente = docenteSel;
    
    document.getElementById("selDalle").disabled = true;
    document.getElementById("selAlle").disabled = true;
        
    //day 0 = domenica - day 6 = sabato!
    if(day == 0 || day == 6)
    {
        alert("Selezionare un giorno feriale (lun-ven)!");
        dataPren.value = "";
    }
    else
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
        var selDalle = document.getElementById("selDalle");
        var selAlle = document.getElementById("selAlle");
        
        selDalle.innerHTML = "";
        
        //Nessuna prenotazione trovata per il docente selezionata nella data messa
        if(arrPren[0].oraInizio == "none")
        {           
            for (var i = 0; i < orari.length; i++) {
                selDalle.innerHTML += "<option>" + orari[i] + "</option>";
            }
        }
        else
        {
            for (var i = 0; i < orari.length-1; i++) {
                if(orari[i] != arrPren[0].oraInizio 
                        && !(orari[i]<arrPren[0].oraFine && orari[i]>arrPren[0].oraInizio))
                    selDalle.innerHTML +=
                        "<option>" + orari[i] + "</option>";
            }
        }
        
        selDalle.disabled = false;
        popolaCmbOrario(selDalle.value);
        
        selAlle.disabled = false;
    }   
}



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