$(document).ready( function() {
    dataPren = document.getElementById("dataPren");
    
    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);

    var today = now.getFullYear()+"-"+(month)+"-"+(day) ;

   $('#dataPren').val(today);
   dataPren.min = today;
   
   orderselect(document.getElementById("selCorso"));
});

//Settaggio chiamata AJAX
var xhrObj = setXMLHttpRequest();
var url_;
var dataPren;

function setXMLHttpRequest() {
    var xhr = null;
    if (window.XMLHttpRequest) {      // browser standard con supporto nativo
      xhr = new XMLHttpRequest();}
    else if (window.ActiveXObject) {   // browser MS Internet Explorer 6 o precedente - ActiveX
      xhr = new ActiveXObject("Microsoft.XMLHTTP");}
    return xhr;
}

function salvaUrl(corso, cmbDoc, url)
{
    url_ = url;
    popolaCmbDocenti(corso, cmbDoc);
}

//AJAX
function popolaCmbDocenti(corso, cmbDocenti)
{   
    xhrObj.open("GET", url_+"&corso="+corso+"&cmb=true", true);
    
    xhrObj.onreadystatechange = popola;

    xhrObj.send(null);
}

//Ricevo risposta dal SERVER
function popola()
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

function cambioData(){
    var date = new Date(dataPren.value);
    var day = date.getDay();
    
    //day 0 = domenica - day 6 = sabato!
    
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