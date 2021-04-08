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

