var dataPren;
$(document).ready( function() {
    dataPren = document.getElementById("dataPren");
    
    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);

    var today = now.getFullYear()+"-"+(month)+"-"+(day) ;

   $('#dataPren').val(today);
   dataPren.min = today;
});

function cambioData(){
    
}
