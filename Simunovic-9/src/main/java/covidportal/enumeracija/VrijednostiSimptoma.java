package covidportal.enumeracija;

public enum VrijednostiSimptoma {
    Produktivni("Produktivni"),
    Intenzivno("Intenzivno"),
    Visoka("Visoka"),
    Jaka("Jaka");


    @Override
    public String toString() {
        return naziv;
    }

    public final String naziv;

    private VrijednostiSimptoma(String naziv){
        this.naziv=naziv;
    }

    public String getNaziv() {
        return naziv;
    }
}
