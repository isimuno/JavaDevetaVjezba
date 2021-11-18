package sample;

import covidportal.iznimke.BazaPodatakaException;
import covidportal.main.BazaPodataka;
import covidportal.model.Zupanija;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DodavanjeNoveZupanijeController {


    @FXML
    private TextField nazivZupanije;

    @FXML
    private TextField brojStanovnikaZupanije;

    @FXML
    private TextField brojZarazenih;

    @FXML
    private Label idZupanije;

    Long maxID = Long.valueOf(0);

    List<Zupanija> listaZupanija=new ArrayList<>();



    public void initialize() throws IOException, BazaPodatakaException {

        listaZupanija = BazaPodataka.dohvatiZupanije();
        for(int i =0; i < listaZupanija.size();i++) {
            if(Integer.parseInt(listaZupanija.get(i).getId().toString()) > maxID) {
                maxID = listaZupanija.get(i).getId();
            }
            else {
                continue;
            }
        }

        idZupanije.setText(String.valueOf(maxID+1));
    }

    public void dodajNovuZupaniju() throws IOException, BazaPodatakaException {
        listaZupanija = BazaPodataka.dohvatiZupanije();
        StringBuffer pogreskaPoruka = new StringBuffer();
        initialize();

        if(nazivZupanije.getText().isBlank() || brojStanovnikaZupanije.getText().isBlank() || brojZarazenih.getText().isBlank()) {
            if(nazivZupanije.getText().isBlank() && brojStanovnikaZupanije.getText().isBlank() && brojZarazenih.getText().isBlank()) {
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli podatke !");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();
            }
            else if(nazivZupanije.getText().isBlank()) {
                pogreskaPoruka.append("Niste unijeli naziv zupanije!");
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli naziv zupanije!");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();}

            else if(brojStanovnikaZupanije.getText().isBlank()) {
                pogreskaPoruka.append("Niste unijeli broj stanovnika!");

                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli broj stanovnika!");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();}

            else if(brojZarazenih.getText().isBlank()) {
                pogreskaPoruka.append("Niste unijeli broj zarazenih!");

                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli broj zarazenih!");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();}
        }
        else {
            maxID +=1;
            try (Connection veza = BazaPodataka.spojiSeNaBazu()) {
                PreparedStatement preparedStatement = veza
                        .prepareStatement(
                                "insert into ZUPANIJA(ID, NAZIV, BROJ_STANOVNIKA, BROJ_ZARAZENIH_STANOVNIKA)" +
                                        "values (?, ?, ?, ?);");
                preparedStatement.setString(1, maxID.toString());
                preparedStatement.setString(2, nazivZupanije.getText());
                preparedStatement.setString(3, brojStanovnikaZupanije.getText());
                preparedStatement.setString(4, brojZarazenih.getText());
                preparedStatement.executeUpdate();
            } catch (IOException | SQLException ex) {
                String poruka = "Došlo je do pogreške u radu s bazom podataka";
                throw new BazaPodatakaException(poruka, ex);
            }
            Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
            alertPoruka.setTitle("Uspješno se dodali županiju!");
            alertPoruka.setHeaderText("Zupanija "+ nazivZupanije.getText() + " je uspješno dodana!");
            alertPoruka.setContentText("Uspješno dodana zupanija: " +"\n"
                    +"Naziv: "+ nazivZupanije.getText() + "\n"
                    +"Broj stanovnika: "+ brojStanovnikaZupanije.getText() +"\n"
                    +"Broj zarazenih: "+ brojZarazenih.getText());
            alertPoruka.showAndWait();
            initialize();
            nazivZupanije.setText("");
            brojZarazenih.setText("");
            brojStanovnikaZupanije.setText("");
        }
    }

}
