$().ready(function() {
    // Selezione form e definizione dei metodi di validazione
    $("#signup-form").validate({
        // Definiamo le nostre regole di validazione
        rules : {
            // login - nome del campo di input da validare
            nome : {
              // Definiamo il campo login come obbligatorio
              required : true
            },
            cognome : {
              // Definiamo il campo login come obbligatorio
              required : true
            },
            email : {
                required : true,
                // Definiamo il campo email come un campo di tipo email
                email : true
            },
            username : {
              // Definiamo il campo username come obbligatorio
              required : true
            },            
            password : {
                required : true,
                // Settiamo la lunghezza minima e massima per il campo password
                minlength : 5,
                maxlength : 15
            }
        },
        // Personalizzimao i mesasggi di errore
        messages: {
            nome: "Inserire il proprio nome",
            cognome: "Inserire il proprio cognome",
            email: "Inserire una email valida",
            username: "Inserire un nome utente",
            password: {
                required: "Inserisci una password",
                minlength: "La password deve essere lunga minimo 5 caratteri",
                maxlength: "La password deve essere lunga al massimo 15 caratteri"
            }
        },
        // Settiamo il submit handler per la form
        submitHandler: function(form) {
            form.submit();
        }
    });
});