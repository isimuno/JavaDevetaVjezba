package covidportal.model;


import covidportal.enumeracija.VrijednostiSimptoma;

import java.io.Serializable;

/**
 * Klasa Simptom za kreiranje instance simptoma
 * nasljeđuje ImenovaniEntitet i njenu varijablu naziv
 */
public class Simptom extends ImenovaniEntitet implements Serializable {
    private VrijednostiSimptoma vrijednost;

    public Simptom(Long id, String naziv, VrijednostiSimptoma vrijednost) {
        super(id,naziv);
        this.vrijednost = vrijednost;
    }

    @Override
    public String toString() {
        return naziv;
    }

    public VrijednostiSimptoma getVrijednost() {
        return vrijednost;
    }

    public void setVrijednost(VrijednostiSimptoma vrijednost) {
        this.vrijednost = vrijednost;
    }
}
