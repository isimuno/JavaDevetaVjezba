package covidportal.sortiranje;


import covidportal.model.Virus;

import java.util.Comparator;

public class VirusSorter implements Comparator<Virus> {

    @Override
    public int compare(Virus v1, Virus v2) {
        if (v1 instanceof Virus && v2 instanceof Virus) {
            if (v1.getNaziv().compareTo(v2.getNaziv()) < 0) {
                return 1;
            } else if (v1.getNaziv().compareTo(v2.getNaziv()) > 0) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
}
