package covidportal.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Klasa Osoba služi za kreiranje instance osobe
 */
public class Osoba implements Serializable {
    private Long id;
    private String ime;
    private String prezime;
    private LocalDate datumRodenja;
    private Zupanija zupanija;
    private Bolest zarazenBolescu;
    private List<Osoba> kontaktiraneOsobe;


    /**
     * @param ime               varijabla tipa String (ime osobe)
     * @param prezime           varijabla tipa String (prezime osobe)
     * @param datumRodenja      varijabla tipa LocalDate (datum rodenja osobe)
     * @param zupanija          Objekt zupanije (zupanija prebivalista osobe)
     * @param zarazenBolescu    Objekt bolesti (bolest osobe)
     * @param kontaktiraneOsobe polje osoba (kontaktirane osobe)
     */
    public Osoba(Long id,String ime, String prezime, LocalDate datumRodenja, Zupanija zupanija, Bolest zarazenBolescu, List<Osoba> kontaktiraneOsobe) {
        this.id=id;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodenja = datumRodenja;
        this.zupanija = zupanija;
        this.zarazenBolescu = zarazenBolescu;
        this.kontaktiraneOsobe = kontaktiraneOsobe;
        if (zarazenBolescu instanceof Virus) {
            Virus nazivVirusa = new Virus(zarazenBolescu.getId(), zarazenBolescu.getNaziv(), zarazenBolescu.getSimptomi());
            if (kontaktiraneOsobe != null) {
                for (int i = 0; i < kontaktiraneOsobe.size(); i++) {
                    nazivVirusa.prelazakZarazeNaOsobu(kontaktiraneOsobe.get(i));
                }
            }
        }
    }

    /**
     * Klasa za kreiranje osobe pomoću Builder Patterna - nove funkcionalnosti JAVE
     */
    public static class Builder {
        private Long id;
        private String ime;
        private String prezime;
        private LocalDate datumRodenja;
        private Zupanija zupanija;
        private Bolest zarazenBolescu;
        private List<Osoba> kontaktiraneOsobe;


        public Builder() {
        }

        public Builder imaId(Long id) {
            this.id = id;
            return this;
        }

        public Builder seZove(String ime) {
            this.ime = ime;
            return this;
        }

        public Builder sePreziva(String prezime) {
            this.prezime = prezime;
            return this;
        }

        public Builder imaGodina(LocalDate datumRodenja) {
            this.datumRodenja = datumRodenja;
            return this;
        }

        public Builder pripadaZupaniji(Zupanija zupanija) {
            this.zupanija = zupanija;
            return this;
        }

        public Builder imaBolest(Bolest zarazenBolescu) {
            this.zarazenBolescu = zarazenBolescu;
            return this;
        }

        public Builder kontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
            this.kontaktiraneOsobe = kontaktiraneOsobe;
            return this;
        }

        public Osoba build() {
            return new Osoba(id, ime, prezime, datumRodenja, zupanija, zarazenBolescu, kontaktiraneOsobe);
        }

    }

    @Override
    public String toString() {
        return  getIme() + " " + getPrezime() ;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public LocalDate getDatumRodenja() {
        return datumRodenja;
    }

    public void setDatumRodenja(LocalDate datumRodenja) {
        this.datumRodenja = datumRodenja;
    }

    public Zupanija getZupanija() {
        return zupanija;
    }

    public void setZupanija(Zupanija zupanija) {
        this.zupanija = zupanija;
    }

    public Bolest getZarazenBolescu() {
        return zarazenBolescu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setZarazenBolescu(Bolest zarazenBolescu) {
        this.zarazenBolescu = zarazenBolescu;
    }

    public List<Osoba> getKontaktiraneOsobe() {
        return kontaktiraneOsobe;
    }

    public void setKontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
        this.kontaktiraneOsobe = kontaktiraneOsobe;
    }

}
