package covidportal.main;

import covidportal.enumeracija.VrijednostiSimptoma;
import covidportal.iznimke.BazaPodatakaException;
import covidportal.model.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BazaPodataka extends Application {

    private static final String DATABASE_CONFIGURATION_FILE = "bazaPodatakaProperties";

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Connection veza = spojiSeNaBazu();
            System.out.println("Uspio sam se spojiti na bazu");
            zatvoriVezuSBazom(veza);
        } catch (SQLException | IOException e) {
            System.out.println("Nisam se uspio spojiti");
            e.printStackTrace();
        }
    }

    public static Connection spojiSeNaBazu() throws IOException, SQLException {
        Properties konfiguracijaBaze = new Properties();
        konfiguracijaBaze.load(new FileReader(DATABASE_CONFIGURATION_FILE));
        String urlBazePodataka = konfiguracijaBaze.getProperty("bazaPodatakaUrl");
        String korisnickoIme = konfiguracijaBaze.getProperty("korisnickoIme");
        String lozinka = konfiguracijaBaze.getProperty("lozinka");
        Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);
        return veza;
    }

    public static void zatvoriVezuSBazom(Connection veza) throws SQLException {
        veza.close();
    }


    public static List<Zupanija> dohvatiZupanije() throws BazaPodatakaException {
        List<Zupanija> listaZupanija = new ArrayList<>();
        try (Connection connection = spojiSeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT distinct ID, NAZIV, BROJ_STANOVNIKA, BROJ_ZARAZENIH_STANOVNIKA from ZUPANIJA ");
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("ZUPANIJA.ID");
                String naziv = resultSet.getString("NAZIV");
                Integer brojStanovnika = resultSet.getInt("BROJ_STANOVNIKA");
                Integer brojZarazenihStanovnika = resultSet.getInt("BROJ_ZARAZENIH_STANOVNIKA");

                Zupanija newZupanija = new Zupanija(id, naziv, brojStanovnika, brojZarazenihStanovnika);
                listaZupanija.add(newZupanija);
            }
        } catch (SQLException | IOException e) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, e);
        }
        return listaZupanija;
    }
    public static List<Zupanija> dohvatiJedneZupanijePremaIDu(Zupanija odredenaZupanija) throws BazaPodatakaException {
        List<Zupanija> listaZupanija = new ArrayList<>();
        try (Connection connection = spojiSeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT distinct ID, NAZIV, BROJ_STANOVNIKA, BROJ_ZARAZENIH_STANOVNIKA from ZUPANIJA WHERE ID =" + odredenaZupanija.getId().toString());
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("ZUPANIJA.ID");
                String naziv = resultSet.getString("NAZIV");
                Integer brojStanovnika = resultSet.getInt("BROJ_STANOVNIKA");
                Integer brojZarazenihStanovnika = resultSet.getInt("BROJ_ZARAZENIH_STANOVNIKA");

                Zupanija newZupanija = new Zupanija(id, naziv, brojStanovnika, brojZarazenihStanovnika);
                listaZupanija.add(newZupanija);
            }
        } catch (SQLException | IOException e) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, e);
        }
        return listaZupanija;
    }

    public static List<Simptom> dohvatiSimptome() throws BazaPodatakaException {
        List<Simptom> listaSimptoma = new ArrayList<>();
        try (Connection connection = spojiSeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT distinct ID, NAZIV, VRIJEDNOST from SIMPTOM");
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String naziv = resultSet.getString("NAZIV");
                VrijednostiSimptoma vrijednostiSimptoma = VrijednostiSimptoma.valueOf(resultSet.getString("VRIJEDNOST"));

                Simptom newSimptom = new Simptom(id, naziv, vrijednostiSimptoma);
                listaSimptoma.add(newSimptom);
            }
        } catch (SQLException | IOException e) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, e);
        }
        return listaSimptoma;
    }

    public static List<Simptom> dohvatiJedanSimptomPremaIDu(Simptom odredeniSimptom) throws BazaPodatakaException {
        List<Simptom> listaSimptoma = new ArrayList<>();
        try (Connection connection = spojiSeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * from SIMPTOM WHERE ID =" + odredeniSimptom.getId().toString());
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String naziv = resultSet.getString("NAZIV");
                VrijednostiSimptoma vrijednostiSimptoma = VrijednostiSimptoma.valueOf(resultSet.getString("VRIJEDNOST"));

                Simptom newSimptom = new Simptom(id, naziv, vrijednostiSimptoma);
                listaSimptoma.add(newSimptom);
            }
        } catch (SQLException | IOException e) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, e);
        }
        return listaSimptoma;
    }


    public static List<Bolest> dohvatiBolesti() throws BazaPodatakaException {
        List<Bolest> listaBolesti = new ArrayList<>();
        List<Simptom> listaSimptomaBolesti = new ArrayList<>();
        try (Connection connection = spojiSeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT distinct ID, NAZIV, VIRUS from BOLEST WHERE VIRUS = FALSE");
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String naziv = resultSet.getString("NAZIV");
                StringBuilder sqlUpitSimptomiBolesti = new StringBuilder(
                        "SELECT SIMPTOM_ID FROM BOLEST_SIMPTOM WHERE BOLEST_ID = " + id);

                System.out.println("Dohvaćam za " + id);
                Statement querySimptomiBolesti = connection.createStatement();
                ResultSet resultSetSimptomiBolesti = querySimptomiBolesti.executeQuery(sqlUpitSimptomiBolesti.toString());
                List<Simptom> listaSimpoma = BazaPodataka.dohvatiSimptome();
                listaSimptomaBolesti.clear();
                while (resultSetSimptomiBolesti.next()) {
                    Long simptomBolesti = resultSetSimptomiBolesti.getLong("SIMPTOM_ID");
                    System.out.println("Dohvaćam simptom  " + simptomBolesti);
                    for (Simptom s : listaSimpoma) {
                        if (s.getId().toString().equals(simptomBolesti.toString())) {
                            listaSimptomaBolesti.add(s);
                        }
                    }
                }
                System.out.println(listaSimptomaBolesti);
                Set<Simptom> setSimptoma = new HashSet<>(listaSimptomaBolesti);
                Bolest newBolest = new Bolest(id, naziv, setSimptoma);
                listaBolesti.add(newBolest);
            }
        } catch (SQLException | IOException e) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, e);
        }
        return listaBolesti;
    }


    public static List<Bolest> dohvatiJednuBolestPremaIDu(Bolest odredenaBolest) throws BazaPodatakaException {
        List<Bolest> listaBolesti = new ArrayList<>();
        List<Simptom> listaSimptomaBolesti = new ArrayList<>();
        try (Connection connection = spojiSeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * from BOLEST WHERE VIRUS = FALSE AND ID =" + odredenaBolest.getId().toString());
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String naziv = resultSet.getString("NAZIV");
                StringBuilder sqlUpitSimptomiBolesti = new StringBuilder(
                        "SELECT SIMPTOM_ID FROM BOLEST_SIMPTOM WHERE BOLEST_ID = " + id);

                System.out.println("Dohvaćam za " + id);
                Statement querySimptomiBolesti = connection.createStatement();
                ResultSet resultSetSimptomiBolesti = querySimptomiBolesti.executeQuery(sqlUpitSimptomiBolesti.toString());
                List<Simptom> listaSimpoma = BazaPodataka.dohvatiSimptome();
                listaSimptomaBolesti.clear();
                while (resultSetSimptomiBolesti.next()) {
                    Long simptomBolesti = resultSetSimptomiBolesti.getLong("SIMPTOM_ID");
                    System.out.println("Dohvaćam simptom  " + simptomBolesti);
                    for (Simptom s : listaSimpoma) {
                        if (s.getId().toString().equals(simptomBolesti.toString())) {
                            listaSimptomaBolesti.add(s);
                        }
                    }
                }
                System.out.println(listaSimptomaBolesti);
                Set<Simptom> setSimptoma = new HashSet<>(listaSimptomaBolesti);
                Bolest newBolest = new Bolest(id, naziv, setSimptoma);
                listaBolesti.add(newBolest);
            }
        } catch (SQLException | IOException e) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, e);
        }
        return listaBolesti;
    }

    public static List<Virus> dohvatiViruse() throws BazaPodatakaException {
        List<Virus> listaVirusa = new ArrayList<>();
        List<Simptom> listaSimptomaVirusa = new ArrayList<>();
        try (Connection connection = spojiSeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT distinct ID, NAZIV, VIRUS from BOLEST WHERE VIRUS = TRUE");
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String naziv = resultSet.getString("NAZIV");
                StringBuilder sqlUpitSimptomiVirusa = new StringBuilder(
                        "SELECT SIMPTOM_ID FROM BOLEST_SIMPTOM WHERE BOLEST_ID = " + id);

                System.out.println("Dohvaćam za " + id);
                Statement querySimptomiVirusa = connection.createStatement();
                ResultSet resultSetSimptomiVirusa = querySimptomiVirusa.executeQuery(sqlUpitSimptomiVirusa.toString());
                List<Simptom> listaSimpoma = BazaPodataka.dohvatiSimptome();
                System.out.println("Veličina: " + listaSimptomaVirusa.size());
                listaSimptomaVirusa.clear();
                System.out.println("Veličina: " + listaSimptomaVirusa.size());
                while (resultSetSimptomiVirusa.next()) {
                    Long simptomVirusa = resultSetSimptomiVirusa.getLong("SIMPTOM_ID");
                    System.out.println("Dohvaćam simptom  " + simptomVirusa);
                    for (Simptom s : listaSimpoma) {
                        if (s.getId().toString().equals(simptomVirusa.toString())) {
                            listaSimptomaVirusa.add(s);
                        }
                    }
                }
                System.out.println(listaSimptomaVirusa);
                Set<Simptom> setSimptoma = new HashSet<>(listaSimptomaVirusa);
                Bolest newVirus = new Virus(id, naziv, setSimptoma);
                listaVirusa.add((Virus) newVirus);
            }
        } catch (SQLException | IOException e) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, e);
        }
        return listaVirusa;
    }

    public static List<Virus> dohvatiJedanVirusPremaIDu(Virus odredeniVirus) throws BazaPodatakaException {
        List<Virus> listaVirusa = new ArrayList<>();
        List<Simptom> listaSimptomaVirusa = new ArrayList<>();
        try (Connection connection = spojiSeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * from BOLEST WHERE VIRUS = TRUE AND ID =" + odredeniVirus.getId().toString());
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String naziv = resultSet.getString("NAZIV");
                StringBuilder sqlUpitSimptomiVirusa = new StringBuilder(
                        "SELECT SIMPTOM_ID FROM BOLEST_SIMPTOM WHERE BOLEST_ID = " + id);

                System.out.println("Dohvaćam za " + id);
                Statement querySimptomiVirusa = connection.createStatement();
                ResultSet resultSetSimptomiVirusa = querySimptomiVirusa.executeQuery(sqlUpitSimptomiVirusa.toString());
                List<Simptom> listaSimpoma = BazaPodataka.dohvatiSimptome();
                System.out.println("Veličina: " + listaSimptomaVirusa.size());
                listaSimptomaVirusa.clear();
                System.out.println("Veličina: " + listaSimptomaVirusa.size());
                while (resultSetSimptomiVirusa.next()) {
                    Long simptomVirusa = resultSetSimptomiVirusa.getLong("SIMPTOM_ID");
                    System.out.println("Dohvaćam simptom  " + simptomVirusa);
                    for (Simptom s : listaSimpoma) {
                        if (s.getId().toString().equals(simptomVirusa.toString())) {
                            listaSimptomaVirusa.add(s);
                        }
                    }
                }
                System.out.println(listaSimptomaVirusa);
                Set<Simptom> setSimptoma = new HashSet<>(listaSimptomaVirusa);
                Bolest newVirus = new Virus(id, naziv, setSimptoma);
                listaVirusa.add((Virus) newVirus);
            }
        } catch (SQLException | IOException e) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, e);
        }
        return listaVirusa;
    }


    public static List<Osoba> dohvatiOsobe() throws BazaPodatakaException {
        List<Osoba> listaKontaktiranihOsoba = new ArrayList<>();
        List<Osoba> listaOsoba = new ArrayList<>();
        List<Osoba> listaSvihOsoba = dohvatiSveOsobe();
        Bolest zarazenBolescu = new Bolest(Long.valueOf(1), "Naziv", null);
        try (Connection connection = spojiSeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT distinct ID, IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID, BOLEST_ID from OSOBA");
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                listaKontaktiranihOsoba.clear();
                zarazenBolescu = new Bolest(Long.valueOf(1), "Naziv", null);
                Long idOsobe = resultSet.getLong("ID");
                String ime = resultSet.getString("IME");
                String prezime = resultSet.getString("PREZIME");
                String datumRodenjaString = resultSet.getString("DATUM_RODJENJA");
                Long idZupanije = resultSet.getLong("ZUPANIJA_ID");
                Long idBolesti = resultSet.getLong("BOLEST_ID");
                DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate datumRodenja = LocalDate.parse(datumRodenjaString, DATEFORMATTER);
                List<Zupanija> listaZupanija = BazaPodataka.dohvatiZupanije();
                List<Bolest> listaBolesti = BazaPodataka.dohvatiBolesti();
                List<Virus> listaVirusa = BazaPodataka.dohvatiViruse();
                Zupanija zupanijaPrebivalista = null;

                for (Zupanija z : listaZupanija) {
                    if (z.getId().equals(idZupanije)) {
                        zupanijaPrebivalista = z;
                    }
                }
                for (Bolest b : listaBolesti) {
                    System.out.println("Usporedujem : " + b.getId() + "  i  " + idBolesti);
                    if (b.getId().toString().equals(idBolesti.toString())) {
                        zarazenBolescu = b;
                    }
                }
                if (zarazenBolescu.getNaziv().equals("Naziv")) {
                    for (Virus v : listaVirusa) {
                        System.out.println("Usporedujem : " + v.getId() + "  i  " + idBolesti);
                        if (v.getId().toString().equals(idBolesti.toString())) {
                            zarazenBolescu = v;
                        }
                    }
                }

                StringBuilder sqlUpitKontaktOsobe = new StringBuilder(
                        "SELECT distinct KONTAKTIRANA_OSOBA_ID FROM KONTAKTIRANE_OSOBE WHERE OSOBA_ID =" + idOsobe);

                Statement queryKontaktOsobe = connection.createStatement();
                ResultSet resultSetKontaktOsobe = queryKontaktOsobe.executeQuery(sqlUpitKontaktOsobe.toString());
                while (resultSetKontaktOsobe.next()) {
                    Long idKontaktiraneOsobe = resultSetKontaktOsobe.getLong("KONTAKTIRANA_OSOBA_ID");
                    for (Osoba o : listaSvihOsoba) {
                        if (o.getId().toString().equals(idKontaktiraneOsobe.toString())) {
                            System.out.println("Spremam osobu : " + o.getIme());
                            listaKontaktiranihOsoba.add(o);
                        }
                    }
                }
                Set<Osoba> setKontaktiranihOsoba = new HashSet<>(listaKontaktiranihOsoba);
                List<Osoba> listaKOsoba = new ArrayList<>(setKontaktiranihOsoba);
                listaKontaktiranihOsoba.clear();
                if(listaKOsoba.isEmpty()){
                    Osoba novaOsoba = new Osoba.Builder()
                            .imaId(idOsobe)
                            .seZove(ime)
                            .sePreziva(prezime)
                            .imaGodina(datumRodenja)
                            .pripadaZupaniji(zupanijaPrebivalista)
                            .imaBolest(zarazenBolescu)
                            .build();
                    listaOsoba.add(novaOsoba);
                }
                else {
                    Osoba novaOsoba = new Osoba.Builder()
                            .imaId(idOsobe)
                            .seZove(ime)
                            .sePreziva(prezime)
                            .imaGodina(datumRodenja)
                            .pripadaZupaniji(zupanijaPrebivalista)
                            .imaBolest(zarazenBolescu)
                            .kontaktiraneOsobe(listaKOsoba)
                            .build();
                    listaOsoba.add(novaOsoba);
                }

            }
        } catch (SQLException | IOException e) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, e);
        }
        return listaOsoba;
    }

    public static List<Osoba> dohvatiJednuOsobuPremaIDu(Osoba odredenaOsoba) throws BazaPodatakaException {
        List<Osoba> listaKontaktiranihOsoba = new ArrayList<>();
        List<Osoba> listaOsoba = new ArrayList<>();
        List<Osoba> listaSvihOsoba = dohvatiSveOsobe();
        Bolest zarazenBolescu = new Bolest(Long.valueOf(1), "Naziv", null);
        try (Connection connection = spojiSeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT distinct ID, IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID, BOLEST_ID from OSOBA WHERE ID =" +odredenaOsoba.getId().toString());
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                listaKontaktiranihOsoba.clear();
                zarazenBolescu = new Bolest(Long.valueOf(1), "Naziv", null);
                Long idOsobe = resultSet.getLong("ID");
                String ime = resultSet.getString("IME");
                String prezime = resultSet.getString("PREZIME");
                String datumRodenjaString = resultSet.getString("DATUM_RODJENJA");
                Long idZupanije = resultSet.getLong("ZUPANIJA_ID");
                Long idBolesti = resultSet.getLong("BOLEST_ID");
                DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate datumRodenja = LocalDate.parse(datumRodenjaString, DATEFORMATTER);
                List<Zupanija> listaZupanija = BazaPodataka.dohvatiZupanije();
                List<Bolest> listaBolesti = BazaPodataka.dohvatiBolesti();
                List<Virus> listaVirusa = BazaPodataka.dohvatiViruse();
                Zupanija zupanijaPrebivalista = null;

                for (Zupanija z : listaZupanija) {
                    if (z.getId().equals(idZupanije)) {
                        zupanijaPrebivalista = z;
                    }
                }
                for (Bolest b : listaBolesti) {
                    System.out.println("Usporedujem : " + b.getId() + "  i  " + idBolesti);
                    if (b.getId().toString().equals(idBolesti.toString())) {
                        zarazenBolescu = b;
                    }
                }
                if (zarazenBolescu.getNaziv().equals("Naziv")) {
                    for (Virus v : listaVirusa) {
                        System.out.println("Usporedujem : " + v.getId() + "  i  " + idBolesti);
                        if (v.getId().toString().equals(idBolesti.toString())) {
                            zarazenBolescu = v;
                        }
                    }
                }

                StringBuilder sqlUpitKontaktOsobe = new StringBuilder(
                        "SELECT distinct KONTAKTIRANA_OSOBA_ID FROM KONTAKTIRANE_OSOBE WHERE OSOBA_ID =" + idOsobe);

                Statement queryKontaktOsobe = connection.createStatement();
                ResultSet resultSetKontaktOsobe = queryKontaktOsobe.executeQuery(sqlUpitKontaktOsobe.toString());
                while (resultSetKontaktOsobe.next()) {
                    Long idKontaktiraneOsobe = resultSetKontaktOsobe.getLong("KONTAKTIRANA_OSOBA_ID");
                    for (Osoba o : listaSvihOsoba) {
                        if (o.getId().toString().equals(idKontaktiraneOsobe.toString())) {
                            System.out.println("Spremam osobu : " + o.getIme());
                            listaKontaktiranihOsoba.add(o);
                        }
                    }
                }
                Set<Osoba> setKontaktiranihOsoba = new HashSet<>(listaKontaktiranihOsoba);
                List<Osoba> listaKOsoba = new ArrayList<>(setKontaktiranihOsoba);
                listaKontaktiranihOsoba.clear();
                if(listaKOsoba.isEmpty()){
                    Osoba novaOsoba = new Osoba.Builder()
                            .imaId(idOsobe)
                            .seZove(ime)
                            .sePreziva(prezime)
                            .imaGodina(datumRodenja)
                            .pripadaZupaniji(zupanijaPrebivalista)
                            .imaBolest(zarazenBolescu)
                            .build();
                    listaOsoba.add(novaOsoba);
                }
                else {
                    Osoba novaOsoba = new Osoba.Builder()
                            .imaId(idOsobe)
                            .seZove(ime)
                            .sePreziva(prezime)
                            .imaGodina(datumRodenja)
                            .pripadaZupaniji(zupanijaPrebivalista)
                            .imaBolest(zarazenBolescu)
                            .kontaktiraneOsobe(listaKOsoba)
                            .build();
                    listaOsoba.add(novaOsoba);
                }

            }
        } catch (SQLException | IOException e) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, e);
        }
        return listaOsoba;
    }


    private static List<Osoba> dohvatiSveOsobe() throws BazaPodatakaException {
        List<Osoba> listaOsoba = new ArrayList<>();
        Zupanija zupanijaPrebivalista = null;
        Bolest zarazenBolescu = new Bolest(Long.valueOf(1), "Naziv", null);
        try (Connection connection = spojiSeNaBazu()) {
            StringBuilder sqlUpitDohvatiOsobe = new StringBuilder(
                    "SELECT * FROM OSOBA");
            Statement querydohvatiOsobe = connection.createStatement();
            ResultSet resultSetDohvatiOsobe = querydohvatiOsobe.executeQuery(sqlUpitDohvatiOsobe.toString());
            while (resultSetDohvatiOsobe.next()) {
                Long idOsobee = resultSetDohvatiOsobe.getLong(1);
                String imeOsobee = resultSetDohvatiOsobe.getString(2);
                String prezimeOsobee = resultSetDohvatiOsobe.getString(3);
                String datumRodenjaOsobee = resultSetDohvatiOsobe.getString(4);
                DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate datumRodenjaOsobe = LocalDate.parse(datumRodenjaOsobee, DATEFORMATTER);
                Long idZupanije = resultSetDohvatiOsobe.getLong(5);
                Long idBolesti = resultSetDohvatiOsobe.getLong(6);
                List<Zupanija> listaZupanija = BazaPodataka.dohvatiZupanije();
                List<Bolest> listaBolesti = BazaPodataka.dohvatiBolesti();
                List<Virus> listaVirusa = BazaPodataka.dohvatiViruse();

                for (Zupanija z : listaZupanija) {
                    if (z.getId().equals(idZupanije)) {
                        zupanijaPrebivalista = z;
                    }
                }
                for (Bolest b : listaBolesti) {
                    System.out.println("Usporedujem : " + b.getId() + "  i  " + idBolesti);
                    if (b.getId().toString().equals(idBolesti.toString())) {
                        zarazenBolescu = b;
                    }
                }
                if (zarazenBolescu.getNaziv().equals("Naziv")) {
                    for (Virus v : listaVirusa) {
                        System.out.println("Usporedujem : " + v.getId() + "  i  " + idBolesti);
                        if (v.getId().toString().equals(idBolesti.toString())) {
                            zarazenBolescu = v;
                        }
                    }
                }

                Osoba newOsoba = new Osoba(idOsobee, imeOsobee, prezimeOsobee, datumRodenjaOsobe, zupanijaPrebivalista, zarazenBolescu, null);
                listaOsoba.add(newOsoba);
            }

        } catch (SQLException | IOException e) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, e);
        }
        return listaOsoba;
    }


}
