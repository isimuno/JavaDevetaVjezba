package covidportal.iznimke;

/**
 * Klasa DuplikatKontaktiraneOsobeException oznacava oznacenu iznimku
 */

public class DuplikatKontaktiraneOsobeException extends Exception {

    public DuplikatKontaktiraneOsobeException(String message) {
        super(message);
    }

    public DuplikatKontaktiraneOsobeException(Throwable cause) {
        super(cause);
    }

    public DuplikatKontaktiraneOsobeException(String message, Throwable cause) {
        super(message, cause);
    }

}
