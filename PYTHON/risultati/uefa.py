import requests
from bs4 import BeautifulSoup
import datetime
import stdfunc

#ricavo anno corrente
current_year = datetime.datetime.now().year
#ricavo il mese corrente
current_month = datetime.datetime.now().month

# pagina da cui prelevo i dati
if current_month > 6:
    page = requests.get('https://it.uefa.com/uefachampionsleague/season=' + str(current_year + 1) + '/standings/')
else:
    page = requests.get('https://it.uefa.com/uefachampionsleague/season=' + str(current_year) + '/standings/')

#oggetto utilizzato per leggere i dati dalla pagina
soup = BeautifulSoup(page.text, "html.parser")

#array ove vengono salvati i dati letti dalla pagina
lst_team = soup.findAll('span', {'class', 'team-name'})
lst_points = soup.findAll('strong', {'class', 'js-points'})
lst_won = soup.findAll('td', {'class', 'table_team-won js-won'})
lst_draw = soup.findAll('td', {'class', 'table_team-drawn js-drawn'})
lst_lost = soup.findAll('td', {'class', 'table_team-lost js-lost'})
lst_for = soup.findAll('td', {'class', 'table_team-for js-goalsFor'})
lst_aganist = soup.findAll('td', {'class', 'table_team-against js-goalsAgainst'})
lst_difference = soup.findAll('td', {'class', 'table_team-goal-diff js-goalDifference'})
lst_teamLink = []

#leggo tutti i link delle pagine di dettagli di tutte le squadre
for item in soup.findAll('a', {'class', 'table_team-name_block'}):
    lst_teamLink.append('https://it.uefa.com' + item['href'])

#stampo la classifica dettagliata utiizzando gli array caricati in precedenza
def stampaClassifica():
    str_fileOut = ''
    i = 0

    for titoli in soup.findAll('h3', {'class','standings--groupname js-group-name '}):
        str_fileOut = str_fileOut + '\n\n\t' + titoli.get_text()

        for count in range(1, 5):
            str_fileOut = str_fileOut\
                    + lst_team[i].get_text()\
                    + '\tpoints: ' + lst_points[i].get_text()\
                    + '\twon: ' + lst_won[i].get_text()\
                    + '\tdraw: ' + lst_draw[i].get_text()\
                    + '\tlost: ' + lst_lost[i].get_text()\
                    + '\n\t\t\t\t'\
                    + '\tgoals for: ' + lst_for[i].get_text()\
                    + '\tgoals aganist: ' + lst_aganist[i].get_text()\
                    + '\tgoal difference: ' + lst_difference[i].get_text()
            str_fileOut = str_fileOut + '\n'
            i += 1


    str_fileOut = str_fileOut.replace('Å','n')
    str_fileOut = str_fileOut.replace('An','AS')
    str_fileOut = str_fileOut.replace('\x9e', '')
    str_fileOut = str_fileOut.replace('\x88', '')

    print(str_fileOut)

    out_file = open('test.txt', 'w')
    out_file.write(str_fileOut)
    print('\n\n')
    stampaMenu()

def statistiche(link):
    # pagina da cui prelevo i dati
    new_page = requests.get(link)

    # oggetto utilizzato per leggere i dati dalla pagina
    new_soup = BeautifulSoup(new_page.text, "html.parser")

    lst_rolePlayer = new_soup.findAll('span', {'class', 'squad--player-role'})
    lst_numberPlayer = new_soup.findAll('span', {'class', 'squad--player-num'})
    lst_namePlayer = new_soup.findAll('span', {'class', 'squad--player-name-name'})
    lst_surnamePlayer = new_soup.findAll('span', {'class', 'squad--player-name-surname'})
    lst_training = new_soup.findAll('span', {'class', 'squad--player-performance-training'})
    lst_match = new_soup.findAll('span', {'class', 'squad--player-performance-match'})
    lst_teamMatch = new_soup.findAll('span', {'class', 'fitty-fit'})
    lst_homeScore = new_soup.findAll('span', {'class', 'js-team--home-score'})
    lst_awayScore = new_soup.findAll('span', {'class', 'js-team--away-score'})
    lst_label = new_soup.findAll('span', {'class', 'statistics--list--label'})
    lst_data = new_soup.findAll('span', {'class', 'statistics--list--data'})

    str_number = ''
    str_name = ''
    str_role = ''
    str_match = ''

    print('Rosa: ')
    for index in range(0, len(lst_rolePlayer)):
        if index < (len(lst_numberPlayer)):
            str_number = lst_numberPlayer[index].get_text()
            str_number = stdfunc.removeSpace(list(str_number))
            if int(lst_numberPlayer[index].get_text()) < 10:
                str_number = ' ' + str_number
        else:
            str_number = '\n    '

        if index < (len(lst_namePlayer) - 1):
            str_name = lst_namePlayer[index].get_text() + ' ' + lst_surnamePlayer[index].get_text()
        else:
            str_name = lst_namePlayer[index].get_text()
        str_name = stdfunc.removeSpace(list(str_name))
        for j in range(len(str_name), 30):
            str_name = str_name + ' '

        str_role = ' role: ' + lst_rolePlayer[index].get_text()
        str_role = stdfunc.removeSpace(list(str_role))

        for k in range(len(str_role), 25):
            str_role = str_role + ' '

        print(str_number + str_name + str_role + str_match)
    print('\n* Giocatore lista B')
    print('\n')
    print('Partite giocate e da giocare')
    index = 0
    for i in range(0, len(lst_homeScore)):
        str_out = stdfunc.removeSpace(list(lst_teamMatch[index].get_text())) + ' '
        for k in range(len(str_out), 20):
            str_out = str_out + ' '
        if lst_homeScore[i].get_text() != '':
            str_out += stdfunc.removeSpace(list(lst_homeScore[i].get_text())) + '-' + stdfunc.removeSpace(list(lst_awayScore[i].get_text())) + '    ' + stdfunc.removeSpace(list(lst_teamMatch[index + 1].get_text()))
        else:
            str_out += ' -     ' + stdfunc.removeSpace(list(lst_teamMatch[index + 1].get_text()))

        print(str_out)
        index += 2
    print('\n')
    print('statistiche dettagliate')
    for i in range(0, len(lst_label)):
        str_out = lst_label[i].get_text() + ': ' + lst_data[i].get_text()
        str_out = stdfunc.removeSpace(list(str_out))
        print(str_out)


def stampaSquadre():
    str_out = ''
    aus = ''
    index = 0
    for i in range(0,8):
        for j in range(0,4):
            if (index + 1) < 10:
                aus = ' ' + str(index + 1) + ': ' + stdfunc.removeSpace(list(lst_team[index].get_text()))
            else:
                aus = str(index + 1) + ': ' + stdfunc.removeSpace(list(lst_team[index].get_text()))

            for j in range(len(aus), 35):
                aus = aus + ' '
            index += 1
            str_out = str_out + aus
        print(str_out)
        str_out = ''

    print('\ninserire codice squadra per maggiori dettagli altrimenti 0 per uscire')

    scelta = input()
    try:
        if scelta == '0':
            stampaMenu()
        if int(scelta) > 0 & int(scelta) < 32:
            print('\t\t' + stdfunc.removeSpace(list(lst_team[int(scelta) - 1].get_text())) + '\n')
            statistiche(lst_teamLink[int(scelta) - 1])
            stampaMenu()
    except:
        print('ERROR')
        stampaSquadre()

def stampaMenu():
    print('0 - exit')
    print('1 - stampa classifica dettagliata')
    print('2 - stampa squadre')
    scelta = input()
    try:
        if scelta == '1':
            stampaClassifica()
        elif scelta == '2':
            stampaSquadre()
        elif scelta == '0':
            return 0
        else:
            print('ERROR')
            stampaMenu()
    except:
        print('ERROR')