var xhrObj = setXMLHttpRequest();
var url_;

function orderselect(selElem, corso)
{
    var tmpAry = new Array();
    for (var i=0;i<selElem.options.length;i++) {
        tmpAry[i] = new Array();
        tmpAry[i][0] = selElem.options[i].text;
        tmpAry[i][1] = selElem.options[i].value;
    }
    tmpAry.sort();
    while (selElem.options.length > 0) {
        selElem.options[0] = null;
    }
    var sel = -1;
    var selTutti = -1;
    for (var i=0;i<tmpAry.length;i++) {
        var op = new Option(tmpAry[i][0], tmpAry[i][1]);
        selElem.options[i] = op;        
        if(selElem.options[i].value === "tutti")
            selTutti = i;
        else if(selElem.options[i].value === corso)
            sel = i;
    }
    
    if(sel != -1)
        selElem.selectedIndex = sel;
    else
        selElem.selectedIndex = selTutti;
}

function setXMLHttpRequest() {
    var xhr = null;
    if (window.XMLHttpRequest) {      // browser standard con supporto nativo
      xhr = new XMLHttpRequest();}
    else if (window.ActiveXObject) {   // browser MS Internet Explorer 6 o precedente - ActiveX
      xhr = new ActiveXObject("Microsoft.XMLHTTP");}
    return xhr;
}

function aggiornaTabella(corso, url)
{
    url_ = url;
    xhrObj.open("GET", url+"&corso="+corso, true);
    
    xhrObj.onreadystatechange = aggiorna; // indico la funzione (updatePage) 
                                            // da invocare quando il server
                                            // termina l’esecuzione della richiesta

    xhrObj.send(null);
}
function popolaModal(corso, url){
    
    document.getElementById('url').value = url;
    document.getElementById('corso').value = corso;
}
function getCorsoUrl(){
    aggiornaTabella(document.getElementById('corso').value, document.getElementById('url').value);
}
function aggiorna()
{
    if (xhrObj.readyState == 4) {           // 4: server ha completato esecuz. richiesta
        var risp = xhrObj.responseText; // responseText contiene valore di cap
        
        var arrDati = JSON.parse(risp);
        
        var i;
        var table = document.getElementById("tablePren");
        table.innerHTML = "";
        if(arrDati.length == 0)
        {
            if(document.getElementById('selCorso').value == 'tutti')
            table.innerHTML += 
                    "<tr><td colspan='6'> Nessuna prenotazione salvata </td></tr>";
            else
                table.innerHTML += 
                    "<tr>"
                    +   "<td colspan='6'> Nessuna prenotazione di " 
                    + document.getElementById('selCorso').value + "</td>"
                    +"</tr>";
        }
        else
        {
            //alert(url_)
            for(i=0; i < arrDati.length; i++)
            {
                if(arrDati[i].stato == "ATTIVA")
                {
                    var corsoSel = document.getElementById("selCorso").value;
                    
                    table.innerHTML += 
                        "<tr>"
                        +   "<td>" + arrDati[i].data + "</td>"                        
                        +   "<td>" + arrDati[i].oraInizio + " - " + arrDati[i].oraFine + "</td>"
                        +   "<td>" + arrDati[i].corso + "</td>"
                        +   "<td>" + arrDati[i].docente + "</td>"
                        +   "<td class='"+ arrDati[i].stato +"'>" + arrDati[i].stato + "</td>"
                        +   "<td><input style='width:100px' type='button' class='btn btn-danger' data-toggle='modal' data-target='#modalDisdici' value='Disdici'"
                        +   " onclick=\"popolaModal('tutti','/Ripetizioni/Controller?toDo=disdici&id=" + arrDati[i].idPren + "&corso=" + corsoSel + "')\" />"
                        +   "</td>"
                        +"</tr>";
                }
                else
                {
                    table.innerHTML += 
                            "<tr>"
                            +   "<td>" + arrDati[i].data + "</td>"                        
                            +   "<td>" + arrDati[i].oraInizio + " - " + arrDati[i].oraFine + "</td>"
                            +   "<td>" + arrDati[i].corso + "</td>"
                            +   "<td>" + arrDati[i].docente + "</td>"
                            +   "<td class='"+ arrDati[i].stato +"'>" + arrDati[i].stato + "</td>"
                            +   "<td><input style='width:100px' disabled type='button' class='btn btn-danger' value='' /> </td>"
                            +"</tr>";
                }
            }
        }
    }
}