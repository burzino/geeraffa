import mysql.connector

def connection(db):
    mydb = mysql.connector.connect(
    host="localhost",  # your host, usually localhost
    user="root",  # your username
    passwd="",  # your password
    db="uefa")  # name of the data base
    return mydb

def select(sql,db):
    cursor = db.cursor()
    try:
        # Execute the SQL command
        cursor.execute(sql)
        # Fetch all the rows in a list of lists.
        results = cursor.fetchall()
        cursor.close()
        db.close()
        if len(results) > 0:
            return results
        else:
            print('errore select dati\ninserire nuovamente i dati')
            return -1
    except:
        print("Error: unable to fecth data")
        db.close()
        return -1

def insert(sql,db):
    cursor = db.cursor()
    try:
        # Execute the SQL command
        cursor.execute(sql)
        db.commit()
        cursor.close()
        db.close()
        return 0

    except:
        print("Error: unable to insert user")
        db.close()
        return -1

