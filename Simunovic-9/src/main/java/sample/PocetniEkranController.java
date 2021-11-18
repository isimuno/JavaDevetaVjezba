package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class PocetniEkranController {
    @FXML
    public void prikaziEkranZaPretraguZupanija() throws IOException {
        Parent pretragaZupanijaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "pretragaZupanija.fxml"));
        Scene pretragaZupanijaScene = new Scene(pretragaZupanijaFrame, 650, 450);
        Main.getMainStage().setScene(pretragaZupanijaScene);
    }

    @FXML
    public void prikaziEkranZaPretraguSimptoma() throws IOException {
        Parent pretragaSimptomaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "pretragaSimptoma.fxml"));
        Scene pretragaSimptomaScene = new Scene(pretragaSimptomaFrame, 650, 450);
        Main.getMainStage().setScene(pretragaSimptomaScene);
    }
    @FXML
    public void prikaziEkranZaPretraguOsoba() throws IOException {
        Parent pretragaOsobaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "pretragaOsoba.fxml"));
        Scene pretragaOsobaScene = new Scene(pretragaOsobaFrame, 800, 650);
        Main.getMainStage().setScene(pretragaOsobaScene);
    }
    @FXML
    public void prikaziEkranZaPretraguBolesti() throws IOException {
        Parent pretragaBolestiFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "pretragaBolesti.fxml"));
        Scene pretragaBolestiScene = new Scene(pretragaBolestiFrame, 650, 450);
        Main.getMainStage().setScene(pretragaBolestiScene);
    }

    @FXML
    public void prikaziEkranZaPretraguVirusa() throws IOException {
        Parent pretragaVirusaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "pretragaVirusa.fxml"));
        Scene pretragaVirusaScene = new Scene(pretragaVirusaFrame, 650, 450);
        Main.getMainStage().setScene(pretragaVirusaScene);
    }

    @FXML
    public void prikaziEkranZaDodavanjeZupanije() throws IOException {
        Parent dodavanjeZupanijeFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "dodavanjeNoveZupanije.fxml"));
        Scene dodavanjeZupanijeScene = new Scene(dodavanjeZupanijeFrame, 650, 450);
        Main.getMainStage().setScene(dodavanjeZupanijeScene);
    }
    @FXML
    public void prikaziEkranZaDodavanjeSimptoma() throws IOException {
        Parent dodavanjeSimptomaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "dodavanjeNovogSimptoma.fxml"));
        Scene dodavanjeSimptomaScene = new Scene(dodavanjeSimptomaFrame, 650, 450);
        Main.getMainStage().setScene(dodavanjeSimptomaScene);
    }
    @FXML
    public void prikaziEkranZaDodavanjeVirusa() throws IOException {
        Parent dodavanjeVirusaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "dodavanjeNovogVirusa.fxml"));
        Scene dodavanjeVirusaScene = new Scene(dodavanjeVirusaFrame, 650, 450);
        Main.getMainStage().setScene(dodavanjeVirusaScene);
    }
    @FXML
    public void prikaziEkranZaDodavanjeBolesti() throws IOException {
        Parent dodavanjeBolestiFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "dodavanjeNoveBolesti.fxml"));
        Scene dodavanjeBolestiScene = new Scene(dodavanjeBolestiFrame, 650, 450);
        Main.getMainStage().setScene(dodavanjeBolestiScene);
    }
    @FXML
    public void prikaziEkranZaDodavanjeOsoba() throws IOException {
        Parent dodavanjeOsobeFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "dodavanjeNoveOsobe.fxml"));
        Scene dodavanjeOsobeScene = new Scene(dodavanjeOsobeFrame, 700, 600);
        Main.getMainStage().setScene(dodavanjeOsobeScene);
    }

}
