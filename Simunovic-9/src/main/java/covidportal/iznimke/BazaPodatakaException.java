package covidportal.iznimke;

public class BazaPodatakaException extends Exception {


    public BazaPodatakaException() {
        super("Dogodila se pogreška u komunikaciji s bazom podataka!");
    }

    public BazaPodatakaException(String message) {
        super(message);
    }

    public BazaPodatakaException(String message, Throwable cause) {
        super(message, cause);
    }

    public BazaPodatakaException(Throwable cause) {
        super(cause);
    }
}

