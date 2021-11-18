package covidportal.genericsi;

import covidportal.model.Osoba;
import covidportal.model.Virus;

import java.util.ArrayList;
import java.util.List;

public class KlinikaZaInfektivneBolesti<T extends Virus, S extends Osoba> {
    List<T> listaVirusa = new ArrayList<T>();
    List<S> listaOsoba = new ArrayList<S>();

    public KlinikaZaInfektivneBolesti(List<T> listaVirusa, List<S> listaOsoba) {
        this.listaVirusa = listaVirusa;
        this.listaOsoba = listaOsoba;
    }

    public List<T> getListaVirusa() {
        return listaVirusa;
    }

    public void setListaVirusa(List<T> listaVirusa) {
        this.listaVirusa = listaVirusa;
    }

    public List<S> getListaOsoba() {
        return listaOsoba;
    }

    public void setListaOsoba(List<S> listaOsoba) {
        this.listaOsoba = listaOsoba;
    }
}

