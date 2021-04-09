# Einstieg:
- Jeder Spiele benötigt ein Public / Private Key aus dem RSA verfahren.
- Die Bytes vom Public Key werden aufsummiert und der Betrag gebildet. Danach wird die Summe Modulo 255 gerechnet.
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

