package covidportal.sortiranje;

import covidportal.model.Zupanija;
import java.util.Comparator;

public class CovidSorter implements Comparator<Zupanija> {

    @Override
    public int compare(Zupanija z1, Zupanija z2) {

        Double brStanovnikaZ1 = z1.getBrojStanovnika().doubleValue();
        Double brStanovnikaZ2 = z2.getBrojStanovnika().doubleValue();
        Double brZarazenihZ1 = z1.getBrojZarazenihOsoba().doubleValue();
        Double brZarazenihZ2 = z2.getBrojZarazenihOsoba().doubleValue();
        Double brojZarazenihZupanija1 = (brZarazenihZ1/(brStanovnikaZ1));
        Double brojZarazenihZupanija2 = (brZarazenihZ2/(brStanovnikaZ2));
        if (brojZarazenihZupanija1.compareTo(brojZarazenihZupanija2)<0) {
            return 1;
        } else if (brojZarazenihZupanija1.compareTo(brojZarazenihZupanija2) > 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
