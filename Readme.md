# Anforderungen
- Einstieg 
  - falls kein aktiver Peer in der Liste der Peers
  - Suche im Bereich Port 1050 bis 1305 bis ein Peer gefunden wurde
  - Tausche die Peerliste mit dem gefundenen Peer aus
  - Routing gibts nicht -> Verbindung direkt mit Port des Peers
  - Spieler werden via public key identifiziert
    
- Spiel starten
    - ein Spieler aus der Peerliste wird angefragt
    - ist der Spieler nicht anzutreffen wird er in der Liste als gelöscht markiert
    - **KNOWN BUG** der angefragte Spieler muss zweimal "y" angeben um das Spiel anzunehmen (anderer Thread der auf Eingabe wartet kann nicht unterbrochen werden (java undso))
    
- Ranking
    - absolute Anzahl der gewonnenen Spiele
    - wird nach einem gewonnenen Spiel hochgezählt
    - nach Ende eines Spiels wird der Spielstand an die Peers in der Liste geschickt
    - auch wenn ein Peer, mal nicht online ist, ist sein Ranking noch gespeichert, da er in der Peerliste nur als nicht da markiert wird, das ranking bleibt und wird auch wieder zueordnet, wenn der Spieler später einen anderen port verwendet, da die zuordnung via public key erfolgt

# Einstieg:
- Jeder Spiele benötigt ein Public / Private Key aus dem RSA verfahren.
- Die Bytes vom Public Key werden aufsummiert und der Betrag gebildet. Danach wird die Summe Modulo 255 gerechnet. Wird zur Findung eines freien Ports verwendet
- Der Einstieg ist ab Port 1050. Der eigene Port wird addiert auf den Basis Port.
- Ist dieser Besetzt wird der eigene Port mit  7 addiert. Bis der Port frei ist.


# Cold Start
- Kein Spieler ist Bekannt. Der Spieler geht jeden Port durch, bis er einen gefunden hat.
- Beim Hello werden alle bekannten Spieler mit Ranking, Port und Public Key ausgetauscht.
- Der Spieler kann entscheiden, ob er einen Partner zum Spielen suchen will oder das Ranking anzeigen


# Warm Start
- Keine Suche wird ausgeführt. Es wird davon ausgegengen, dass die Spieler noch an den Ports existieren
- Der Spieler kann entscheiden, ob er einen Partner zum Spielen suchen will oder das Ranking anzeigen

# Spiel
- Alice fragt an Bob
- Bob sagt "nein" --> Alice fragt Charlie
- Bob sagt "ja" --> Alice macht den ersten Zug:
- Alice weiß, dass Bob mit dem Public Key k1 an Port p1 ist.
- Alice schickt im Move den Move signiert mit
- Bob weiß, dass Alice mit dem Public Key k2 an Port p2 ist.
- Bob checkt die Signatur vom Move
- Signatur stimmt: Bob macht einen Zug, signiert hin und scickt in an Alice
- Signatur stimmt nicht: Bob verweigert weitere Züge und sucht sich andere Partner
- Nach beenden des Spiels werden die aktualisierten Rankings an alle Spieler verteilt
- Der Spieler kann entscheiden, ob er einen Partner zum Spielen suchen will oder das Ranking anzeigen

# Peer A Sicht
1. Finde einen Peer von Port 1050 bis 1305
2. Tausche Peertabelle
3. Spiele ein Spiel:
   Schicke eine Spielanfrage an den Peer
   Wenn der Peer zusagt:
   3.a Fange an mit ersten Zug, sibnieren den Zug, Schicke an B  
   3.b B führ den Zug bei sich aus, macht einen neuen Zug, signiert ihn, Schickt ihn an A  
   3.c A führ den Zug bei sich aus, macht einen neuen Zug, signiert ihn, Schickt ihn an B
   3.d weiter mit 3.d bis ein Sieger oder ein remi feststeht
4. Nachdem das Spiel zu Ende ist inkrementiert der Verlierer das Ranking des Gewinners in seiner Peerlist und schickt die aktualisierte Peerlist an alle seine bekannten Peers

# Peer B Sicht
1. B wird von A angefragt
2. B schickt seine Peertabelle zurück
   Peer A schickt eine Spielanfrage, die Peer b annimmt
3. Warten auf ersten Zug von A
4. Anworten auf den Zug A so lange bis ein sieger / remi feststeht


# Mögliche Angriffe
Ein Peer täuscht ein Disconnect vor  
Ein Peer manipuliert das Ergebnis  

HighScore kann nicht übermittelt werden --> Nur validirung wenn bei das Ergebnis an den selben Peer schicken. Auch dann ist es nicht garantiert
Nachricht wird angefangen und Verändert --> Geht nicht, da signiert. Es seiden der Public Key wurde schon beim Austauscht verändert
