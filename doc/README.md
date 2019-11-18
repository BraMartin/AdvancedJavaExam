Bramar17 – Martin Braathen --- Berjoh17 – John Kevin Berg



For å teste koden:
- $ cd pgr200-eksamen-kevinberg92 (til der du har lagt denne filen)
- $ mvn test
- $ mvn install
- $ mvn package
- $ cd database-server
- $ java -jar target/Database-Server-0.0.1-SNAPSHOT.jar 
- Dette starter serveren og du kan nå kjøre spørringen fra klientsiden.
- Åpne et nytt terminalvindu
- $ cd pgr200-eksamen-kevinberg92 (til der du har lagt denne filen)
- $ cd database-client
- $ java -jar target/Database-Client-0.0.1-SNAPSHOT.jar <argument>
- Her kan du bruke følgene arguments: 
- add: denne legger til title og description (følg instruksjoner i terminal)
- list: skriver ut alt som er i database tabellen conference_talk.
- show: viser spesifikk talk (følg instruksjoner i terminal).

Når server kjører kan du også åpne en nettleser, og skrive «localhost:10080/api/list/talks» (funker bare på GET requester) og da vil du få se innholdet i databasen rett i nettleser.



Testene ShouldWriteStatusCode og ShouldReadOtherStatusCode i HttpServerTest, kjører grønt med infinitest, Junit og mvn test, men travis gir oss feilmelding med disse to. Vi har prøvd å feilsøke rundt, men siden det ikke er noe feilmeldinger på vår side (ingen av pcene vi har kodet på - totalt 4 forskjellige), har vi valgt å kommentere ut testene. Vi valgte å beholde klassen og testene slik at de er synlig, og det er fullt mulig å prøve å fjerne kommentering av klassen, og teste ut.


ScreenCastOMatic : https://www.youtube.com/watch?v=BC4z_ZtMkSs&feature=youtu.be&hd=1&fbclid=IwAR2D3F6fWRj9RC_AnVALkHdz_diUMlbTnEWJq7Ct34yy-P2ftmd-UkKfImg (fra innlevering 2)
vi fikk beskjed om at vi ikke trengte å lage ny. Vi fikk også tilbakemeldinger fra denne som gjorde at vi ble bedre på refaktorering.



### Databasemodell

![Databasemodell](doc/martinUML.png)



 ### Klassediagram
 Dette er klassediagrammet vi valgte å lage i en UML-editor før vi begynte med kodingen. Ikke alle underklassene er med, fordi vi valgte kun å ta med de klassene som var viktige for arbeidsprossesen vår. 
 ![Klassediagram](doc/klassediagram.png)
