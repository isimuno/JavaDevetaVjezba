package covidportal.model;

import java.io.Serializable;

/**
 * Apstraktna klasa ImenovaniEntitet
 * prosljeđuje varijablu naziv klasama Bolest, Simptom, Zupanija
 */
public abstract class ImenovaniEntitet implements Serializable {
    Long id;
    String naziv;

    public ImenovaniEntitet(Long id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return  naziv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() { return naziv; }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
