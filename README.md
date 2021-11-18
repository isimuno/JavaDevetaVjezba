# JavaDevetaVjezba

9.Deveta laboratorijska vježba
9.1. TEMA VJEŽBE
Svrha laboratorijske vježbe je korištenje JDBC-a i uvođenje H2
relacijske baze podataka u koju će se spremati podaci korišteni u prošloj
laboratorijskoj vježbi, umjesto datoteka.
9.2. ZADATAK ZA PRIPREMU
Proširiti rješenje osme laboratorijske vježbe na način da se kopira
rješenje te preimenuje u naziv koji sadrži indeks „9“, umjesto „8“. Osim
same mape s projektom, potrebno je promijeniti i naziv projekta unutar
IntelliJ-a korištenjem opcije „Refactor->Rename“. Program je potrebno
proširiti na način opisan u nastavku, a mora sadržavati iste
funkcionalnosti kao i osma laboratorijska vježba:
1. Na poveznici http://www.h2database.com/html/download.html
preuzeti H2 bazu podataka verzije 1.4.199. Također dodati u
„dependency“ modul s verzijom H2 baze podataka u datoteku
„pom.xml“.
2. Korištenjem datoteke „h2.bat“ unutar H2 instalacijske mape „h2/bin“
pokrenuti bazu podataka. Nakon toga bi se trebao u pregledniku
prikazati sljedeći prozor:
Slika 1. Dijalog za kreiranje baze podataka
Korištenjem „Embedded“ načina rada te definiranjem naziva baze
podataka (npr. „covid“) te postavljanje korisničkog imena i lozinke (npr.
„student“ i „student“) potrebno je kreirati bazu podataka koja će se 
koristiti u laboratorijskoj vježbi. Pritiskom na gumb „Connect“ obavlja
se kreiranje baze podataka i prijava u aplikaciju za administraciju.
Korištenjem opcije označene na sljedećoj slici moguće je odjaviti se iz
administracijske aplikacije kako bi se mogli prijaviti u „Server“ načinu
rada.
Slika 2. Konfiguracija H2 baze podataka
Ponovna prijava u aplikaciju za administraciju, ovaj put u „Server“
načinu rada moguće je obaviti korištenjem podataka sa sljedećeg
ekrana: naziv baze podataka mora biti isti kao i kod kreiranja, zajedno
s korisničkim podacima:
Slika 3. Dijalog za prijavu u „Server“ način rada
3. Izvršiti sljedeću SQL skriptu za kreiranje baze podataka koja će se
koristiti u JavaFX aplikaciji iz prethodne baze podataka:
CREATE TABLE SIMPTOM(
 ID LONG GENERATED ALWAYS AS IDENTITY,
 NAZIV VARCHAR(25) NOT NULL,
 VRIJEDNOST VARCHAR(25) NOT NULL,
 PRIMARY KEY (ID)
);
CREATE TABLE BOLEST(
 ID LONG GENERATED ALWAYS AS IDENTITY,
 NAZIV VARCHAR(25) NOT NULL,
 VIRUS BOOLEAN NOT NULL,
 PRIMARY KEY (ID)
);
CREATE TABLE BOLEST_SIMPTOM(
 BOLEST_ID LONG NOT NULL,
 SIMPTOM_ID LONG NOT NULL,
 PRIMARY KEY (BOLEST_ID, SIMPTOM_ID),
 FOREIGN KEY (BOLEST_ID) REFERENCES BOLEST(ID),
 FOREIGN KEY (SIMPTOM_ID) REFERENCES SIMPTOM(ID)
);
CREATE TABLE ZUPANIJA(
 ID LONG GENERATED ALWAYS AS IDENTITY,
 NAZIV VARCHAR(50) NOT NULL,
 BROJ_STANOVNIKA INT NOT NULL,
 BROJ_ZARAZENIH_STANOVNIKA INT NOT NULL,
 PRIMARY KEY (ID)
);
CREATE TABLE OSOBA(
 ID LONG GENERATED ALWAYS AS IDENTITY,
 IME VARCHAR(25) NOT NULL,
 PREZIME VARCHAR(25) NOT NULL,
 DATUM_RODJENJA DATE NOT NULL,
 ZUPANIJA_ID LONG NOT NULL,
 BOLEST_ID LONG NOT NULL,
 PRIMARY KEY (ID),
 FOREIGN KEY (ZUPANIJA_ID) REFERENCES ZUPANIJA(ID),
 FOREIGN KEY (BOLEST_ID) REFERENCES BOLEST(ID)
);
CREATE TABLE KONTAKTIRANE_OSOBE(
 OSOBA_ID LONG NOT NULL,
 KONTAKTIRANA_OSOBA_ID LONG NOT NULL,
 PRIMARY KEY (OSOBA_ID, KONTAKTIRANA_OSOBA_ID),
 FOREIGN KEY (OSOBA_ID) REFERENCES OSOBA(ID),
 FOREIGN KEY (KONTAKTIRANA_OSOBA_ID ) REFERENCES OSOBA(ID)
);
INSERT INTO SIMPTOM(NAZIV, VRIJEDNOST) VALUES ('Kašalj',
'Produktivni');
INSERT INTO SIMPTOM(NAZIV, VRIJEDNOST) VALUES ('Curenje nosa',
'Intenzivno');
INSERT INTO SIMPTOM(NAZIV, VRIJEDNOST) VALUES ('Temperatura',
'Visoka');
INSERT INTO SIMPTOM(NAZIV, VRIJEDNOST) VALUES ('Grlobolja', 'Jaka');
INSERT INTO SIMPTOM(NAZIV, VRIJEDNOST) VALUES ('Glavobolja',
'Produktivni');
INSERT INTO BOLEST(NAZIV, VIRUS) VALUES ('Prehlada', 'TRUE');
INSERT INTO BOLEST(NAZIV, VIRUS) VALUES ('Gripa', 'TRUE');
INSERT INTO BOLEST(NAZIV, VIRUS) VALUES ('Covid-19', 'TRUE');
INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES (1, 1);
INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES (1, 2);
INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES (2, 3);
INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES (2, 4);
INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES (2, 5);
INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES (3, 3);
INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES (3, 5);
INSERT INTO ZUPANIJA(NAZIV, BROJ_STANOVNIKA,
BROJ_ZARAZENIH_STANOVNIKA) VALUES('Grad Zagreb', 1000000, 3242);
INSERT INTO ZUPANIJA(NAZIV, BROJ_STANOVNIKA,
BROJ_ZARAZENIH_STANOVNIKA) VALUES('Zagrebačka', 200000, 1232);
INSERT INTO ZUPANIJA(NAZIV, BROJ_STANOVNIKA,
BROJ_ZARAZENIH_STANOVNIKA) VALUES('Splitsko-Dalmatinska',
200000, 2324);
INSERT INTO ZUPANIJA(NAZIV, BROJ_STANOVNIKA,
BROJ_ZARAZENIH_STANOVNIKA) VALUES('Međimurska', 120000, 4323);
INSERT INTO ZUPANIJA(NAZIV, BROJ_STANOVNIKA,
BROJ_ZARAZENIH_STANOVNIKA) VALUES('Varaždinska', 140000, 3976);
INSERT INTO OSOBA(IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID,
BOLEST_ID) VALUES ('Perica', 'Perić', '1968-10-12', 1, 2);
INSERT INTO OSOBA(IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID,
BOLEST_ID) VALUES ('Marija', 'Horvat', '1962-05-02', 2, 1);
INSERT INTO OSOBA(IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID,
BOLEST_ID) VALUES ('Mladen', 'Barić', '1978-02-23', 3, 3);
INSERT INTO OSOBA(IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID,
BOLEST_ID) VALUES ('Ivana', 'Milić', '1975-03-15', 4, 2);
INSERT INTO OSOBA(IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID,
BOLEST_ID) VALUES ('Marko', 'Kovačević', '1971-05-19', 5, 3);
INSERT INTO KONTAKTIRANE_OSOBE VALUES(1, 2);
INSERT INTO KONTAKTIRANE_OSOBE VALUES(1, 3);
INSERT INTO KONTAKTIRANE_OSOBE VALUES(1, 4);
INSERT INTO KONTAKTIRANE_OSOBE VALUES(2, 3);
INSERT INTO KONTAKTIRANE_OSOBE VALUES(2, 4);
INSERT INTO KONTAKTIRANE_OSOBE VALUES(3, 5);
INSERT INTO KONTAKTIRANE_OSOBE VALUES(4, 5);
4. Umjesto podatka o starosti unutar klase „Osoba“ koristiti datum
rođenja te izračunavati starost osobe kad zatreba u aplikaciji.
5. Kreirati klasu „BazaPodataka“ koja će imati sljedeće metode:
a. Metodu za otvaranje veze na bazu podataka
Laboratorijske vježbe iz kolegija “Programiranje u JAVA jeziku” 2020/2021
Specijalistički studij Informacijski sustavi
Veleučilište Velika Gorica
Stranica 6 od 7
© Autor: v. pred. Aleksander Radovan, dipl. ing.
b. Metodu za zatvaranje veze na bazu podataka
c. Metodu za dohvat svih simptoma (korištenjem upita „SELECT *
FROM SIMPTOM“)
d. Metodu za dohvat jednog simptoma prema identifikatoru
(korištenjem upita „SELECT * FROM SIMPTOM WHERE ID = ?“)
e. Metodu za spremanje novog simptoma u bazu podataka
(korištenjem upita koji je korišten u skripti iz trećeg zadatka)
f. Metodu za dohvat svih bolesti (korištenjem upita „SELECT *
FROM BOLEST“)
g. Metodu za dohvat jedne bolesti prema identifikator (korištenjem
upita „SELECT * FROM BOLEST WHERE ID = ?“)
h. Metodu za spremanje nove bolesti (korištenjem upita koji je
korišten u skripti iz trećeg zadatka)
i. Metodu za dohvat svih županija (korištenjem upita „SELECT *
FROM ZUPANIJA“)
j. Metodu za dohvat jedne županije prema identifikatoru
(korištenjem upita „SELECT * FROM ZUPANIJA WHERE ID = ?“)
k. Metodu za spremanje nove županije (korištenjem upita koji je
korišten u skripti iz trećeg zadatka)
l. Metodu za dohvat svih osoba (korištenjem upita „SELECT * FROM
OSOBA“)
m.Metodu za dohvat jedne osobe prema identifikatoru
(korištenjem upita „SELECT * FROM OSOBA WHERE ID = ?“)
n. Metodu za spremanje nove osobe (korištenjem upita koji je
korišten u skripti iz trećeg zadatka)
Prilikom dohvaćanja i spremanja podataka u vezne tablice
„BOLEST_SIMPTOM“ i „KONTAKTIRANE_OSOBE“ potrebno koristiti
identifikatore entiteta koje je potrebno spremiti u bazu podataka.
Primjer programskog koda za dohvat podataka iz baze i spremanje novih
podataka prikazan je u nastavku:
public static List<Student> dohvatiSveStudente() throws SQLException, IOException {
 List<Student> listaStudenata = new ArrayList<>();
 Connection veza = connectToDatabase();
 Statement stmt = veza.createStatement();
 ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTI");
 while (rs.next()) {
 int id = rs.getInt("id");
 String jmbag = rs.getString("jmbag");
 String ime = rs.getString("ime");
 String prezime = rs.getString("prezime");
 Date date = (Date) rs.getDate("datum_rodjenja");
 Instant instant = Instant.ofEpochMilli(date.getTime());
 LocalDate localDate = LocalDateTime.ofInstant(
instant, ZoneId.systemDefault()).toLocalDate();
 Student noviStudent = new Student(jmbag, ime, prezime, localDate);
 noviStudent.setId(id);
 listaStudenata.add(noviStudent);
 }
 closeConnectionToDatabase(veza);
 return listaStudenata;
}
public static void spremiNovogStudenta(Student noviStudent) throws SQLException,
IOException
{
 Connection veza = connectToDatabase();
 PreparedStatement upit =
 veza.prepareStatement(
 "INSERT INTO STUDENTI(id, jmbag, ime, prezime, datum_rodjenja) VALUES(1, ?, ?, ?, ?)");
 upit.setString(1, noviStudent.getJmbag());
 upit.setString(2, noviStudent.getIme());
 upit.setString(3, noviStudent.getPrezime());
 upit.setDate(4, Date.valueOf(noviStudent.getDatumRodjenja()));
 upit.executeUpdate();
 closeConnectionToDatabase(veza);
}
6. Podatke za spajanje na bazu podataka potrebno je izdvojiti u
„properties“ datoteku.
7. Prilagoditi enumeracije i ostale dijelove koda na način da budu
kompatibilni s podacima iz baze podataka. Razliku između virusa i
bolesti potrebno je ustanoviti na temelju „BOOLEAN“ parametra u
tablici „BOLEST“ (ako je postavljen na vrijednost „TRUE“, onda se radi
o virusu, inače se ne radi o virusu).
Primjer izvođenja programa za spremanje novih županija je prikazan na
sljedećem videu: https://www.youtube.com/watch?v=XNwiKS2mYWU.
