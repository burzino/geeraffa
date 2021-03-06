import ado
import uefa

def login():
    print('nome utente: ')
    user = input()
    print('password: ')
    pwd = input()
    sql = "SELECT * FROM utente WHERE Nome = '" + user + "' AND password = '" + pwd + "'"
    myDb=ado.connection('uefa')
    result=ado.eseguiQuery(sql,myDb)
    if len(result) > 0:
        return 0
    else:
        print('errore inserimento dati\ninserire nuovamente i dati')
        return -1

def registrazione():
    print('inserire i dati necessari per completare la registrazione')
    print('nome utente: ')
    usr = input()
    print('password: ')
    pwd = input()
    print('email: ')
    email = input()
    myDb=ado.connection('uefa')
    sql = ("INSERT INTO `utente`(`nome`, `password`, `email`) VALUES ('"+usr+"','"+pwd+"','"+email+"')")
    return ado.eseguiNonQuery(sql,myDb)



def stampaMenu():
    scelta = input()
    result = -1
    while result < 0:
        if scelta == '0':
            return 0
        elif scelta == '1':
            result = login()
        elif scelta == '2':
            result = registrazione()
            if result > -1:
                print('registrazione effettuata con successo\neffetuare il login: ')
                scelta = '1'
                result = login()
            else:
                print('errore durante la registrazione\ninserire nuovamente i dati')
        else:
            print("errore nell'inserimento della scelta\neffettuare la scelta:")
            result = -1
            scelta = input()
    return result


def main():
    print('\nPer poter continuare è necessario essere registrati e bisogna effettuare il login\n')
    print('0 - exit\n1 - login\n2 - registrati')
    result = stampaMenu()
    if result < 0:
        print('errore')
    else:
        uefa.stampaMenu()
    return 0



print('PROGETTO IUM - anno accademico 2018/19\nsvolto e presentato da:\n-FAVOLE Federico\n-GERBAUDO Luca\n-RACCA Andrea Rocco\n')

try:
    main()
except:
    print('errore')