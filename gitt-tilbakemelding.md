Bramar17 - Martin Braathen
Berjoh17 - John Kevin Berg

//testing av koden / maven kommentarer //

Vi oppdaget ihvertfall en unødvendig instansieringer, som f.eks Client.connectToServer når de allerede er inni i Client klassen.
konstruktøren i talkdao burde tatt inn datasource som parameter for å unngå mye duplisering. 
enkelte metode/klassenavn kunne vært litt kortere uten å miste forståelse, mensom også gjør det lettere å lese koden og forstå oppgavene til de.
Det kan muligens være mer oversiktlig at arguments kaller på metoder i en felles klasse istedenfor å kalle på klasser hver for seg.
Ellers er koden relativt ryddig med gode innrykk og forståelig syntax. ReadMe instruksene var  også bra skrevet og lett å forstå.

vi fikk mer forståelse av hvordan oppgaven ble løst uten HTTP med POST og GET requester.