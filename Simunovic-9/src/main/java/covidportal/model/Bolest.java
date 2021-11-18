package covidportal.model;

import java.io.Serializable;
import java.util.Set;

/**
 * Klasa Bolest nasljeđuje klasu imenovani entitet
 * služi za kreiranje instance Bolesti
 */

public class Bolest extends ImenovaniEntitet implements Serializable {
    private Set<Simptom> simptomi;


    /**
     * @param naziv varijabla naziva bolesti
     * @param simptomi polje simptoma bolesti
     */
    public Bolest(Long id, String naziv, Set<Simptom> simptomi) {
        super(id, naziv);
        this.simptomi = simptomi;
    }

    @Override
    public String toString() {
        return naziv;
    }

    public Set<Simptom> getSimptomi() {
        return simptomi;
    }

    public void setSimptomi(Set<Simptom> simptomi) {
        this.simptomi = simptomi;
    }
}
