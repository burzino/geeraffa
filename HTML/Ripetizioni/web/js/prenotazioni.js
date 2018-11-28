function setXMLHttpRequest() {
    var xhr = null;
    if (window.XMLHttpRequest) {      // browser standard con supporto nativo
      xhr = new XMLHttpRequest();}
    else if (window.ActiveXObject) {   // browser MS Internet Explorer 6 o 						// precedente - ActiveX
      xhr = new ActiveXObject("Microsoft.XMLHTTP");}
    return xhr;
}

var xhrObj = setXMLHttpRequest();
var url_;

function aggiornaTabella(corso, url)
{
    url_ = url
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
                    +   "<td>"
                    +   "<input type='button' class='btn btn-danger' data-toggle='modal' value='Disdici' onclick=\"window.location.href='"+ url_ + "?toDo=disdici&id=" + dati[4] + "'\"/>"
                    +   "</td>"
                    +"</tr>";
        }
        //document.getElementById("txt").value = txt; // assegno risp al campo cap
						     // della form della pagina html
    }
}