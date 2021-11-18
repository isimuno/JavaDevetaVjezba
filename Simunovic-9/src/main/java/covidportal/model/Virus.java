package covidportal.model;

import java.io.Serializable;
import java.util.Set;

/**
 * Klasa Virus koja nasljeduje klasu Bolest i implementira sucelje Zarazno
 */
public class Virus extends Bolest implements Zarazno,Serializable {

    /**
     * @param naziv varijabla tipa String koja definira naziv virusa
     * @param simptomi polje simptoma
     */
    public Virus(Long id, String naziv, Set<Simptom> simptomi) {
        super(id, naziv, simptomi);
    }

    @Override
    public String toString() {
        return  naziv;
    }

    @Override
    public void prelazakZarazeNaOsobu(Osoba osoba) {
        Virus nazivVirusa = new Virus(id, naziv,getSimptomi());
        osoba.setZarazenBolescu(nazivVirusa);
    }
}
