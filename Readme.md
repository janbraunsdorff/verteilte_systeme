# Anforderungen
- Einstieg 
  - Suche im Beriech 1050 bis 1100 bis du einen gefunden hast
  - Tausche alle Bekannten Nachbarn aus
  - Routing Tabllen -> wenn ich zu Alice will, wenn muss ich anschreiben, damit er die Nachricht an Alice weiterleitet
                       oder man bekommt den Port zum direkten Anfragen
  - ID über Public Key
- Suche 
  - Suche nach Peer mit hilfe von Public Key oder Hash vom Key 
- Inhalte verbreiten
  - Vier Augengespräch, welches von anderne Peers weitergeleitet wird
  - entweder direktes Rooting oder über andere Peers weiterleiten
- Kommunikation
 - Austausch der Rooting Tabellen
 - Austausch der Spielstände


# Peer A Sicht
1. Finde einen Typen von Port 1050 bi 1100
2. Tausche Tabelle zum rooting
3. Spiele ein Spiel  
   3.a Fange an mit ersten Zug, singieren den Zug, Schicke an B  
   3.b B führ den Zug bei sich aus, macht einen neuen Zug, signiert ihn, Schickt ihn an A  
   3.c A führ den Zug bei sich aus, macht einen neuen Zug, signiert ihn, Schickt ihn an B
   3.d weiter mit 3.d bis ein Sieger oder ein remi feststeht
4. Nach dem es Gespielt wurde, schicken alle es an alle bekannten im Netz zur Speicherung ( Verteilter Highscore)  
   Man selber hat nur die Anzahl der gewonnen spiele und die Spiele der bekannten Knoten
5. Suche nach neunem Partner an Hand des Hashes oder Port incremnt

# Peer B Sicht
1. B wird angeschrieben mit Routing Tabellen
2. B schickt seine Routing Tabelle zurück
3. Warten auf ersten Zug von A
4. Anworten auf den Zug A so lange bis ein sieger / remi feststeht
5. An alle Bakannten das Ergebnis schicken


# Mögliche Angriffe
Ein Peer täuscht ein Disconnect vor  
Ein Peer manipuliert das Ergebnis  
Bei, Routing über Peers: Nachricht wird nicht weitergeleitet

HighScoore kann nicht übermittelt werden --> Nur validirung wenn bei das Ergebnis an den selben Peer schicken. Auch dann ist es nicht garantiert
Nachricht wird angefangen und Verändert --> Geht nicht, da signiert. Es seiden der Public Key wurde schon beim Austauscht verändert

