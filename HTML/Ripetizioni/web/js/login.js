var xhrObj = setXMLHttpRequest();
var url_ = "";

//lascio disabilitato il tasto login se i campi sono vuoti
$(document).ready(function() {
    $('.form-group input').keyup(function() {

        var empty = false;
        $('.form-group input').each(function() {
            if ($(this).val().length == 0) {
                empty = true;
            }
        });

        if (empty) {
            $('#submit').prop('disabled', true);
        } else {
            $('#submit').prop("disabled", false);
        }
    });
});


function setXMLHttpRequest() {
    var xhr = null;
    if (window.XMLHttpRequest) {      // browser standard con supporto nativo
      xhr = new XMLHttpRequest();}
    else if (window.ActiveXObject) {   // browser MS Internet Explorer 6 o precedente - ActiveX
      xhr = new ActiveXObject("Microsoft.XMLHTTP");}
    return xhr;
}

function verifica(url)
{
    url_ = url
    var user = document.getElementById("username").value;
    var pwd = document.getElementById("pwd").value;
    
    xhrObj.open("GET", url+"?toDo=login&username="+user+"&pwd="+pwd, true);
    
    xhrObj.onreadystatechange = checkLogged; // indico la funzione (updatePage) 
                                            // da invocare quando il server
                                            // termina l’esecuzione della richiesta

    xhrObj.send(null);
}

function checkLogged()
{
    if (xhrObj.readyState == 4) {           // 4: server ha completato esecuz. richiesta
        var risp = xhrObj.responseText; // responseText contiene valore di cap       
        
        var logged = JSON.parse(risp);                       
        
        var errorLogin = document.getElementById("errorLogin");
        
        if(logged.val === "N")
        {
            errorLogin.style.visibility = "visible";
            window.location.hash = '#divLogin';
        }
        else
        {     
            if(logged.val === "Y")
                window.location.href = "http://localhost:8080/Ripetizioni/index.jsp";
            else // vale A --> ADMIN
                window.location.href = "http://localhost:8080/Ripetizioni/tab_docenti.jsp";           
        }
    }
}