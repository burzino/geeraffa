var dataPren;
$(document).ready( function() {
    dataPren = document.getElementById("dataPren");
    
    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);

    var today = now.getFullYear()+"-"+(month)+"-"+(day) ;

   $('#dataPren').val(today);
   dataPren.min = today;
   
   orderselect(document.getElementById("selCorso"));
   orderselect(document.getElementById("selDocente"));
});

function cambioData(){
    var date = new Date(dataPren.value);
    var day = date.getDay();
    
    //day 0 = domenica - day 6 = sabato!
    
}

function orderselect(selElem)
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
    for (var i=0;i<tmpAry.length;i++) {
        var op = new Option(tmpAry[i][0], tmpAry[i][1]);
        selElem.options[i] = op;
    }
    return;
}
