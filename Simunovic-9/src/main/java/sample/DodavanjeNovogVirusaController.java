package sample;

import covidportal.iznimke.BazaPodatakaException;
import covidportal.main.BazaPodataka;
import covidportal.model.Bolest;
import covidportal.model.Simptom;
import covidportal.model.Virus;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DodavanjeNovogVirusaController {

    @FXML
    private TextField nazivVirusa;
    @FXML
    private Label idVirusa;
    @FXML
    ListView<String> listViewVirusa;


    Long maxID = Long.valueOf(0);
    List<Simptom> listaSimptoma  = new ArrayList<>();
    List<String> listaNazivaSimptoma = new ArrayList<>();
    List<Virus> listaVirusa  = new ArrayList<>();
    List<Bolest> listaBolesti  = new ArrayList<>();

    public void initialize() throws IOException, BazaPodatakaException {
        listaVirusa = BazaPodataka.dohvatiViruse();
        listaBolesti = BazaPodataka.dohvatiBolesti();
        for(int i =0; i < listaVirusa.size();i++) {
            if(Integer.parseInt(listaVirusa.get(i).getId().toString()) > maxID) {
                maxID = listaVirusa.get(i).getId();
            }
            else {
                continue;
            }
        }

        for(int i =0; i < listaBolesti.size();i++) {
            if(Integer.parseInt(listaBolesti.get(i).getId().toString()) > maxID) {
                maxID = listaBolesti.get(i).getId();
            }
            else {
                continue;
            }
        }
        listaSimptoma = BazaPodataka.dohvatiSimptome();
        for(Simptom s: listaSimptoma){
            listaNazivaSimptoma.add(s.getNaziv());
        }
        ObservableList<String> items = FXCollections.observableArrayList(listaNazivaSimptoma);
        listViewVirusa.setItems(items);
        listViewVirusa.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Label label = new Label();
        label.setTextFill(Color.RED);

        listViewVirusa.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                    ObservableList<String> selectedItems = listViewVirusa.getSelectionModel().getSelectedItems();

                    StringBuilder builder = new StringBuilder("Selected items :");

                    for (String name : selectedItems) {
                        builder.append(name + "\n");
                    }

                    label.setText(builder.toString());

                });
        idVirusa.setText(String.valueOf(maxID+1));
    }
    public void dodajNoviVirus() throws IOException, BazaPodatakaException {
        listaVirusa = BazaPodataka.dohvatiViruse();
        StringBuffer pogreskaPoruka = new StringBuffer();

        if(nazivVirusa.getText().isBlank() || listViewVirusa.getSelectionModel().isEmpty()) {
            if(nazivVirusa.getText().isBlank()  && listViewVirusa.getSelectionModel().isEmpty()) {
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli podatke !");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();
            }
            else if(nazivVirusa.getText().isBlank()) {
                pogreskaPoruka.append("Niste unijeli naziv virusa!");
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli naziv virusa!");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();}

            else if(listViewVirusa.getSelectionModel().isEmpty()) {
                pogreskaPoruka.append("Niste odabrali simptome virusa!");
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli niti jedan simptom!");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();}
        }
        else {
            maxID+=1;
            List<String> listaNazivaSimptoma=new ArrayList<>();
            List<Integer> listaIndexaSimptoma=new ArrayList<>();

            for(int i=0; i< listViewVirusa.getSelectionModel().getSelectedItems().size();i++){
                listaNazivaSimptoma.add(listViewVirusa.getSelectionModel().getSelectedItems().get(i).toString());
            }
            for(int i=0;i<listaNazivaSimptoma.size();i++){
                for(Simptom s:listaSimptoma){
                    if(s.getNaziv().equals(listaNazivaSimptoma.get(i))){
                        listaIndexaSimptoma.add(s.getId().intValue());
                    }
                }
            }

            try (Connection veza = BazaPodataka.spojiSeNaBazu()) {
                PreparedStatement preparedStatement = veza
                        .prepareStatement(
                                "insert into BOLEST(ID, NAZIV, VIRUS)" +
                                        "values (?, ?, ?);");
                preparedStatement.setString(1, maxID.toString());
                preparedStatement.setString(2, nazivVirusa.getText());
                preparedStatement.setString(3, "TRUE");
                preparedStatement.executeUpdate();
                for(int i=0;i<listaIndexaSimptoma.size();i++){
                PreparedStatement preparedStatementSimptomi = veza
                        .prepareStatement(
                                "insert into BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID)" +
                                        "values (?, ?);");
                preparedStatementSimptomi.setString(1, maxID.toString());
                preparedStatementSimptomi.setString(2, listaIndexaSimptoma.get(i).toString());
                preparedStatementSimptomi.executeUpdate();
                }
            } catch (IOException | SQLException ex) {
                String poruka = "Došlo je do pogreške u radu s bazom podataka";
                throw new BazaPodatakaException(poruka, ex);
            }

            Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
            alertPoruka.setTitle("Uspješno se dodali virus!");
            alertPoruka.setHeaderText("Virus "+ nazivVirusa.getText() + " je uspješno dodana!");
            alertPoruka.setContentText("Uspješno dodan virus: " +"\n"
                    +"Naziv: "+ nazivVirusa.getText() + "\n"
                    +"Simptomi: "+ listViewVirusa.getSelectionModel().getSelectedItems().toString());
            alertPoruka.showAndWait();
            initialize();
            nazivVirusa.setText("");
        }


    }

}
