package covidportal.iznimke;

/**
 * Klasa BolestIstihSimptomaException oznacava neoznacenu iznimku
 */

public class BolestIstihSimptomaException extends RuntimeException{


    public BolestIstihSimptomaException(String message) {
        super(message);
    }

    public BolestIstihSimptomaException(String message, Throwable cause) {
        super(message, cause);
    }

    public BolestIstihSimptomaException(Throwable cause) {
        super(cause);
    }
}
