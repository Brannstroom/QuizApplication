# OBLIG 5 - QUIZ
##### https://hiof.instructure.com/courses/5104/assignments/26098
##### Peter Brännström & Jörn Christoffer Sundt Olsen

### Velkommen til Quiz-applikasjonen som vi har valgt å lage som en del av Oblig 5 hvor du kan registrere en bruker, starte en Quiz og utfordre din kunnskap! Det er mange kategorier og tusenvis av spørsmål, hvor alle hentes gjennom TriviaAPI!

### Bruk av applikasjonen
Når du først starter applikasjonen må du registrere din egen bruker (eller ta i bruk admin brukeren (admin/admin)). Etter at du har registrert en bruker logger du inn med valgt brukernavn og passord, og blir sendt til hoved-siden hvor det vises litt statistikk om dine tidligere Quizzer, og en mulighet for å starte en quiz. Du kan velge både hvor lang quizzen skal være i antall spørsmål (5-30) og i hvilken kategori du ønsker spørsmål (alle kategorier er også et alternativ med "any category"). Dermed starter du quizzen og svarer ditt beste. Alle spørsmål hentes gjennom TriviaAPI (https://opentdb.com/api_config.php), noe som gjør at det fins tusenvis av spørsmål du kan svare på. Du vil fortløpende vite om du har svart riktig eller feil. Når du har svart på alle spørsmålene får du en liten prompt på hvor bra du har gjort det og blir sendt tilbake til hoved-siden hvor statistikken har oppdatert seg, du kan da starte en ny quiz om du ønsker, eller logge ut.

### GUI-vinduer
Vi kjører alt gjennom samme GUI-vindu (tror vi), men skiller mellom panelene. Vi har et panel for registrering, login, hoved-side og quiz-side. Hva de forskjellige panelene brukes til er åpenbart.

### Feil vi får
Det hender at vi noen ganger får en exception når vi kjører applikasjonen som vi ikke har løst, men pleier det å fungere å bare kjøre applikasjonen gjennom main-metoden på nytt.

### Endringer vi ønsker å gjøre
#### Ikke bare multiple-choise
Per nå er det bare multiple-choice spørsmål, men vi ønsker å legge til en funksjon for å kunne ha spørsmål hvor du skal skrive svaret. Det er derfor vi nå har laget Question-klassen som MultipleChoiceQuestion-klassen arver fra.

#### Leaderboard
Vi ønsker også å legge til et GUI-vindu for leaderboard hvor vi kan vise frem de beste Quizzerne.


#
### Klassediagram
![UML Diagram](https://github.com/Brannstroom/QuizApplication/blob/main/uml_diagram.png?raw=true)