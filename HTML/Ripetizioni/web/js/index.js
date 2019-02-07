var xhrObj = setXMLHttpRequest();
var url_ = "";


function setXMLHttpRequest() {
    var xhr = null;
    if (window.XMLHttpRequest) {      // browser standard con supporto nativo
      xhr = new XMLHttpRequest();}
    else if (window.ActiveXObject) {   // browser MS Internet Explorer 6 o precedente - ActiveX
      xhr = new ActiveXObject("Microsoft.XMLHTTP");}
    return xhr;
}

function getCorsi(url)
{
    url_ = url
    
    xhrObj.open("GET", url);
    
    xhrObj.onreadystatechange = checkLogged; // indico la funzione (updatePage) 
                                            // da invocare quando il server
                                            // termina l’esecuzione della richiesta

    xhrObj.send(null);
}

function checkLogged()
{
    if (xhrObj.readyState == 4) {           // 4: server ha completato esecuz. richiesta
        //alert("Lista salvata");
    }
}