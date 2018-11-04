import mysql.connector

# richiedo il login per permettere l'accesso ai dati
def login():
    print('nome utente: ')
    user = input()
    print('password: ')
    pwd = input()

    # creo il collegamento con il database
    mydb = mysql.connector.connect(
        host="localhost",  # your host, usually localhost
        user="root",  # your username
        passwd="",  # your password
        db="uefa")  # name of the data base

    cursor = mydb.cursor()

    sql = "SELECT * FROM utente WHERE Nome = '" + user + "' AND password = '" + pwd + "'"

    try:
        # Execute the SQL command
        cursor.execute(sql)
        # Fetch all the rows in a list of lists.
        results = cursor.fetchall()
        cursor.close()
        mydb.close()
        if len(results) > 0:
            return 0
        else:
            print('errore inserimento dati\ninserire nuovamente i dati')
            return -1
    except:
        print("Error: unable to fecth data")
        mydb.close()
        return -1


def registrazione():
    print('inserire i dati necessari per completare la registrazione')
    print('nome utente: ')
    usr = input()
    print('password: ')
    pwd = input()
    print('email: ')
    email = input()

    # creo il collegamento con il database
    mydb = mysql.connector.connect(
        host="localhost",  # your host, usually localhost
        user="root",  # your username
        passwd="",  # your password
        db="uefa")  # name of the data base

    cursor = mydb.cursor()

    sql = ("INSERT INTO `utente`(`nome`, `password`, `email`) VALUES (%s,%s,%s)")

    val = (str(usr), str(pwd), str(email))
    try:
        # Execute the SQL command
        cursor.execute(sql, val)
        mydb.commit()
        cursor.close()
        mydb.close()
        return 0

    except:
        print("Error: unable to insert user")
        mydb.close()
        return -1
