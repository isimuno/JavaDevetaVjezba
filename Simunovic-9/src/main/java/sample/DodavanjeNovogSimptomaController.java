package sample;
import covidportal.enumeracija.VrijednostiSimptoma;
import covidportal.iznimke.BazaPodatakaException;
import covidportal.main.BazaPodataka;
import covidportal.model.Simptom;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DodavanjeNovogSimptomaController {
    @FXML
    private TextField nazivSimptoma;

    @FXML
    private ComboBox vrijednostSimptoma;

    @FXML
    private Button dodajNoviSimptom;

    @FXML
    private Label idSimptoma;

    Long maxID = Long.valueOf(0);
    List<Simptom> listaSimptoma=new ArrayList<>();

    public void initialize() throws IOException, BazaPodatakaException {
        listaSimptoma = BazaPodataka.dohvatiSimptome();
        for(int i =0; i < listaSimptoma.size();i++) {
            if(Integer.parseInt(listaSimptoma.get(i).getId().toString()) > maxID) {
                maxID = listaSimptoma.get(i).getId();
            }
            else {
                continue;
            }
        }

        vrijednostSimptoma.getItems().add(" ");
        VrijednostiSimptoma[] yourEnums = VrijednostiSimptoma.class.getEnumConstants();
        for (int i=0;i< yourEnums.length;i++){
            vrijednostSimptoma.getItems().add(yourEnums[i].getNaziv());
        }
        vrijednostSimptoma.setValue(" ");
        idSimptoma.setText(String.valueOf(maxID+1));
    }

    public void dodajNoviSimptom() throws IOException, BazaPodatakaException {
        listaSimptoma = BazaPodataka.dohvatiSimptome();
        StringBuffer pogreskaPoruka = new StringBuffer();

        if(nazivSimptoma.getText().isBlank() || vrijednostSimptoma.getItems().equals(" ")) {
            if(nazivSimptoma.getText().isBlank()  && vrijednostSimptoma.getItems().equals(" ")) {
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli podatke !");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();
            }
            else if(nazivSimptoma.getText().isBlank()) {
                pogreskaPoruka.append("Niste unijeli naziv simptoma!");
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli naziv simptoma!");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();}

            else if(vrijednostSimptoma.getItems().equals(" ")) {
                pogreskaPoruka.append("Niste odabrali vrijednost simptoma!");
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli vrijednost simptoma!");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();}

        }
        else {
            maxID +=1;
            try (Connection veza = BazaPodataka.spojiSeNaBazu()) {
                PreparedStatement preparedStatement = veza
                        .prepareStatement(
                                "insert into SIMPTOM(ID, NAZIV, VRIJEDNOST)" +
                                        "values (?, ?, ?);");
                preparedStatement.setString(1, maxID.toString());
                preparedStatement.setString(2, nazivSimptoma.getText());
                preparedStatement.setString(3, vrijednostSimptoma.getValue().toString());
                preparedStatement.executeUpdate();
            } catch (IOException | SQLException ex) {
                String poruka = "Došlo je do pogreške u radu s bazom podataka";
                throw new BazaPodatakaException(poruka, ex);
            }

            Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
            alertPoruka.setTitle("Uspješno se dodali simptom!");
            alertPoruka.setHeaderText("Simptom "+ nazivSimptoma.getText() + " je uspješno dodan!");
            alertPoruka.setContentText("Uspješno dodana simptom: " +"\n"
                    +"Naziv: "+ nazivSimptoma.getText() + "\n"
                    +"Vrijednost simptoma: "+ vrijednostSimptoma.getValue().toString());
            alertPoruka.showAndWait();
            initialize();
            nazivSimptoma.setText("");
            vrijednostSimptoma.setValue(" ");
        }

    }
}
