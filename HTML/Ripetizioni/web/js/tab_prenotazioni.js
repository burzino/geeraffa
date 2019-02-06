var xhrObj = setXMLHttpRequest();
var url_;

function setXMLHttpRequest() {
    var xhr = null;
    if (window.XMLHttpRequest) {      // browser standard con supporto nativo
      xhr = new XMLHttpRequest();}
    else if (window.ActiveXObject) {   // browser MS Internet Explorer 6 o precedente - ActiveX
      xhr = new ActiveXObject("Microsoft.XMLHTTP");}
    return xhr;
}

function aggiornaTabella(stud, corso, url)
{
    url_ = url;
    xhrObj.open("GET", url+"&stud="+stud+"&corso="+corso, true);
    
    xhrObj.onreadystatechange = aggiorna; // indico la funzione (updatePage) 
                                            // da invocare quando il server
                                            // termina l’esecuzione della richiesta

    xhrObj.send(null);
}

function aggiorna()
{
    if (xhrObj.readyState == 4) {           // 4: server ha completato esecuz. richiesta
        var risp = xhrObj.responseText; // responseText contiene valore di cap
        
        var arrDati = JSON.parse(risp);
        
        var i;
        var table = document.getElementById("bodyTable");
        var selStud = document.getElementById("selStudente");
        var selCorso = document.getElementById("selCorso");
        table.innerHTML = "";
        if(arrDati.length == 0)
        {
            if(selStud.value === 'tutti' && selCorso.value === 'tutti')
            table.innerHTML += 
                    "<tr><td colspan='6'> Nessuna prenotazione salvata </td></tr>";
            else if(selStud.value !== 'tutti' && selCorso.value === "tutti")
                table.innerHTML += 
                    "<tr>"
                    +   "<td colspan='6'> Nessuna prenotazione dello studente " 
                    + selStud.options[selStud.selectedIndex].text + "</td>"
                    +"</tr>";
            else if(selStud.value !== 'tutti' && selCorso.value !== "tutti")
                table.innerHTML += 
                    "<tr>"
                    +   "<td colspan='6'> Nessuna prenotazione dello studente " 
                    + selStud.options[selStud.selectedIndex].text + " per il corso di "
                    + selCorso.options[selCorso.selectedIndex].text + "</td>"
                    +"</tr>";
            else //Studenti tutti e corso selezionato
                table.innerHTML += 
                    "<tr>"
                    +   "<td colspan='6'> Nessuna prenotazione dello studente per il corso di"
                    +   selCorso.options[selCorso.selectedIndex].text + "</td>"
                    +"</tr>";
        }
        else
        {
            //alert(url_)
            for(i=0; i < arrDati.length; i++)
            {
                table.innerHTML += 
                    "<tr>"
                    +   "<td>" + arrDati[i].docPren + "</td>"
                    +   "<td>" + arrDati[i].corso + "</td>"
                    +   "<td>" + arrDati[i].studPren + "</td>"
                    +   "<td>" + arrDati[i].data + "</td>"                        
                    +   "<td>" + arrDati[i].oraInizio + " - " + arrDati[i].oraFine + "</td>"
                    +   "<td class='"+ arrDati[i].stato +"'>" + arrDati[i].stato + "</td>"
                    +"</tr>";                
            }
        }
    }
}