package covidportal.model;

import java.io.Serializable;

/**
 * Klasa Zupanija koja nasljeđuje klasu ImenovniEntitet
 */
public class Zupanija extends  ImenovaniEntitet implements Serializable {

    private Integer brojStanovnika;
    private Integer brojZarazenihOsoba;


    /**
     * @param naziv  varijabla tipa String koja definira naziv zupanije
     * @param brojStanovnika varijabla tipa Integer koja definira broj stanovnika u zupaniji
     */

    public Zupanija(Long id, String naziv, Integer brojStanovnika, Integer brojZarazenihOsoba) {
        super(id, naziv);
        this.brojStanovnika = brojStanovnika;
        this.brojZarazenihOsoba = brojZarazenihOsoba;
    }

    @Override
    public String toString() {
        return  naziv;
    }

    public Integer getBrojZarazenihOsoba() {
        return brojZarazenihOsoba;
    }

    public void setBrojZarazenihOsoba(Integer brojZarazenihOsoba) {
        this.brojZarazenihOsoba = brojZarazenihOsoba;
    }

    public Integer getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(Integer brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }


}
