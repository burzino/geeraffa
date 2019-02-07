#rimuvo spazi inutili all'interno della stringa
def removeSpace(a):
    for k in range(0, len(a) - 1):
        if a[k] == ' ' and a[k + 1] == ' ':
            a[k] = ''
        elif a[k] == '\n' or a[k] == '\r':
            a[k] = ''

    if a[0] == ' ':
        a[0] = ''
    elif a[len(a)-1] == ' ':
        a[len(a) - 1] = ''
    return ''.join(a)

def cercaMax(a):
    leng = 0
    for i in range(0, len(a)):
        if len(a[i].get_text())>leng:
            leng = len(a[i].get_text())
    return  leng

