L'aplicació ha de complir amb els següents requisits:

- En el nostre Escape Room virtual, que té un nom específic, oferim una varietat de sales temàtiques, pistes intrigants i objectes de decoració únics.
    Clase Escape Room.
        Atributos: Salas, Objetos, Pistas, (gestión ventas), Inventario <List<List<Sala>, List<Pista>, List<Objeto>>
        Método: calcular valor total, calcular ventas total, getValorInventario

- Cada sala té assignat un nivell de dificultat per proporcionar una experiència equilibrada i desafiadora
    Interfaz Sala.
    Clase Sala.
        Atributos: Nombre, Dificultad, Precio (solo sala), Objetos, Pistas, Jugador??
        Metodo getPuntuacion(suma objetos y pistas encontrados)

    Clase Jugador?¿

- Les pistes estan dissenyades amb temes específics per guiar els jugadors en la cerca de solucions

    Interfaz Pista
        Atributos: Nombre, Precio, Tema? [codigo, contraseña, símbolo], Objeto(trofeo)

        (Constructor!?)
        public Pista(str, bla) {
            if (!pista1.isPresent()) {
                sout("No puedes acceder a X")
            }
            this. = 
        }

- Els objectes de decoració contribueixen a crear una atmosfera immersiva i memorable a cada sala, utilitzant diferents tipus de materials.

    Interfaz Objeto.
        Atributos: Nombre, Precio, Material

- Tots els elements tenen un preu associat que reflecteix el seu valor en el joc
     (presente en cada elemento)

- El nostre Escape Room virtual ha de mantenir un inventari actualitzat de totes les sales, pistes i objectes de decoració disponibles.
    Fet

- A més, portarem un registre del valor total de l'inventari per tenir una visió clara dels nostres actius.
    fET

- L'aplicació oferirà una funcionalitat per emetre certificats de superació d'enigmes, on es registraran els assoliments aconseguits pels jugadors durant la seva experiència a l'Escape Room.

    TODO    

- A més, se'ls podran atorgar possibles regals o recompenses com a reconeixement per la seva habilitat i destresa en resoldre els reptes plantejats.

    if 1 pista. pin
    if 2 pistas, camiseta
    if 3 pistas, trofeo de oro.

- Pel que fa a les funcionalitats a mostrar per pantalla, s'espera que inclogui com a mínim les següents:

- Crear un nou Escape Room virtual.

    Singleton

- Afegir una nova sala amb el seu respectiu nivell de dificultat.

    new Room(name, dificultad)

- Incorporar pistes temàtiques per enriquir l'experiència de joc.

    Método Sala.
    cowboyRoom.addPista(new Pista(name piramide, enigma sarcofago)) ¿?

- Introduir objectes de decoració per ambientar les sales de manera única.

    Método Sala
    sala.addObjeto(new Objeto(bla, bla, bla))

- Mostrar l'inventari actualitzat, mostrant les quantitats disponibles de cada element (sales, pistes i objectes de decoració).

    Método Escape Room
    SR.showSalas(count if objeto is Sala) ##Sobre lista inventario.
    SR.showPistas
    SR.showOjbetos

- Visualitzar el valor total en euros de l'inventari de l'Escape Room virtual.

    OK. 

- Permetre la retirada de sales, pistes o objectes de decoració de l'inventari.

    Método Escape Room
    SR.removeSala
    ...

- Generar tiquets de venda per als diferents jugadors/es.

    Método Escape Room
    SR.printTicket() ventasTotal += valor

- Calcular i mostrar el total d'ingressos generats per vendes de tiquets de l'Escape Room virtual.

    Método Escape Room
    int getVentasTotal €€€

- Notificar als usuaris sobre esdeveniments importants a l'Escape Room, com l'addició de noves pistes, la finalització d'una sala, etc.

    Pattern Observer

- Els usuaris interessats en aquests esdeveniments podran sol·licitar registrar-se per poder rebre notificacions quan es produeixin esdeveniments rellevants

    Pattern Observer




GIT
    Main
    Dev
        branches

Convenciones (prefijos). Commits relacionados con stories (id)

Cómo escribir historias de ususario