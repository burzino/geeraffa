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

function aggiornaTabella(corso, url)
{
    url_ = url;
    xhrObj.open("GET", url+"&corso="+corso, true);
    
    xhrObj.onreadystatechange = aggiorna; // indico la funzione (updatePage) 
                                            // da invocare quando il server
                                            // termina l’esecuzione della richiesta

    xhrObj.send(null);
}

function aggiorna()
{
    if (xhrObj.readyState == 4) {           // 4: server ha completato esecuz. richiesta
        var risp = xhrObj.responseText; // responseText contiene valore di cap
        var str = risp.split("?");
        var i;
        var table = document.getElementById("tablePren");
        table.innerHTML = "";
        if(str.length-1 == 0)
        {
            var dati = str[0].split(";");
            table.innerHTML += 
                    "<tr>"
                    +   "<td colspan='5'> Nessuna prenotazione di " 
                    + document.getElementById('selCorsoPrenota').value + "</td>"
                    +"</tr>";
        }
        else
        {
            //alert(url_)
            for(i=0; i < str.length-1; i++)
            {
                //alert(str);
                var dati = str[i].split(";");
                table.innerHTML += 
                        "<tr>"
                        +   "<td>" + dati[0] + "</td>"
                        +   "<td>" + dati[1] + "</td>"
                        +   "<td>" + dati[2] + "</td>"
                        +   "<td>" + dati[3] + "</td>"
                        +   "<td><input type='button' class='btn btn-danger' data-toggle='modal' value='Disdici'"
                        +   " onclick=\"aggiornaTabella('tutti','/Ripetizioni/Controller?toDo=disdici&id=" + dati[4] + "')\" />"
                        +   "</td>"
                        +"</tr>";
            }
        }
    }
}