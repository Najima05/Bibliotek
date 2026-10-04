Bibliotekshanteraren

Kort beskrivning av en konsolbaserad bibliotekshanterare skriven i Java.

Innehåll:
1. Lägga till böcker
2. Registrera medlemmar
3. Låna böcker
4. Lämna tillbaka böcker
5. Söka efter böcker med titel eller författare
6. Visa alla böcker med lånestatus
7. Se vilken medlem som har flest aktiva lån. 
8. En medlem kan ha högst 5 aktiva lån.

Data lagras i arrayer som utökas automatiskt med Arrays.copyOf() när de blir fulla.
Böckerna sorteras med Selection Sort och sökningen sker med linjär sökning.

Lösning och design

Programmet är uppdelat i flera klasser med tydliga ansvarsområden:
1. Book: En record som innehåller ISBN, titel och författare. En record passar bra eftersom klassen
   främst används för att lagra information om en bok.
2. Member: Hanterar medlemmens ID, namn och antal aktiva lån samt regeln om högst fem lån.
3. Methods: Innehåller metoderna addLoan() och removeLoan(). Jag valde att placera dem i en separat
   klass för att hålla Member fokuserad på medlemsinformation och regler.
4. Library: Hanterar bibliotekets huvudfunktioner, exempelvis att lägga till och söka efter böcker,
   registrera medlemmar samt hantera utlåning och återlämning.
5. Main: Hanterar menyn och användargränssnittet. Inmatning sker med IO.readln() i stället för Scanner.

Motivering
Jag använde arrayer eftersom uppgiften inte tillåter Collections Framework. Jag valde linjär sökning och Selection Sort
eftersom de är relativt enkla att implementera manuellt och uppfyller uppgiftens krav.
Genom att dela upp programmet i flera klasser blir koden mer strukturerad och lättare att förstå, testa och ändra.

Reflektion

Arbetet har gett mig bättre förståelse för hur flera Java-klasser samarbetar i ett program.
Jag har arbetat med arrayer, records, metoder, inkapsling, felhantering, sökning och sortering.
Jag är nöjd med uppdelningen mellan klasserna. Metoderna addLoan() och removeLoan() hade också kunnat placeras
i Member, men jag valde en separat klass eftersom jag upplevde att det gjorde strukturen tydligare.
Om jag skulle vidareutveckla programmet skulle jag kunna förbättra användargränssnittet, lägga till möjligheten
att ta bort böcker och medlemmar samt utveckla fler sökalternativ.

Källkritik:
Jag har använt kursmaterial och lektionernas genomgångar för att förstå programmeringskonceptet och java syntaxen.
Jag har även använt Java dokumentationen och Dev.java mycket för att lära mig mer om Java och förstå olika metoder och
hur funktioner fungerar. Jag har också använt AI-hjälp i IntelliJ för att få hjälp med problem och delar av koden som jag inte har förstått. 


