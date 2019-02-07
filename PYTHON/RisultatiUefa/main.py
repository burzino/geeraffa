import ado
import uefa
from termcolor import colored

def login():
    print('Nome utente: ')
    user = input()
    print('Password: ')
    pwd = input()
    sql = "SELECT * FROM utente WHERE Nome = '" + user + "' AND password = '" + pwd + "'"
    myDb=ado.connection('uefa')
    result=ado.eseguiQuery(sql,myDb)
    if len(result) > 0:
        return 0
    else:
        print('Errore inserimento dati\ninserire nuovamente i dati')
        return -1

def registrazione():
    print(colored('Inserire i dati necessari per completare la registrazione','magenta'))
    print('Nome utente: ')
    usr = input()
    print('Password: ')
    pwd = input()
    print('Email: ')
    email = input()
    myDb=ado.connection('uefa')
    sql = ("INSERT INTO `utente`(`nome`, `password`, `email`) VALUES ('"+usr+"','"+pwd+"','"+email+"')")
    return ado.eseguiNonQuery(sql,myDb)



def stampaMenu():
    scelta = input()
    result = -1
    while result < 0:
        if scelta == '0':
            return 500
        elif scelta == '1':
            result = login()
        elif scelta == '2':
            result = registrazione()
            if result > -1:
                print(colored('Registrazione effettuata con successo\neffetuare il login: ','blue'))
                scelta = '1'
                result = login()
            else:
                print(colored('Errore durante la registrazione\ninserire nuovamente i dati','red'))
        else:
            print(colored("Errore nell'inserimento della scelta\neffettuare la scelta:",'red'))
            result = -1
            scelta = input()
    return result


def main():
    print(colored('\nPer poter continuare è necessario essere registrati e bisogna effettuare il login\n','blue'))
    print(colored('0 - exit','red'))
    print(colored('1 - login\n2 - registrati','green'))
    result = stampaMenu()
    if result < 0:
        print('errore')
    elif result == 500:
        return 0
    else:
        uefa.stampaMenu()
    return 0



print(colored('PROGETTO IUM - anno accademico 2018/19\nsvolto e presentato da:\n-FAVOLE Federico\n-GERBAUDO Luca\n-RACCA Andrea Rocco\n','magenta'))

try:
    main()
except:
    print(colored('errore','red'))