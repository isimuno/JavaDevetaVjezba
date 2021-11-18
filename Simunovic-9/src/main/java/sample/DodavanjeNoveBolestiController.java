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

public class DodavanjeNoveBolestiController{
    @FXML
    private TextField nazivBolesti;
    @FXML
    private Label idBolesti;
    @FXML
    ListView<String> listViewBolesti;


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
        listViewBolesti.setItems(items);
        listViewBolesti.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Label label = new Label();
        label.setTextFill(Color.RED);

        listViewBolesti.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                    ObservableList<String> selectedItems = listViewBolesti.getSelectionModel().getSelectedItems();

                    StringBuilder builder = new StringBuilder("Selected items :");

                    for (String name : selectedItems) {
                        builder.append(name + "\n");
                    }

                    label.setText(builder.toString());

                });
        idBolesti.setText(String.valueOf(maxID+1));
    }
    public void dodajNovuBolest() throws IOException, BazaPodatakaException {
        listaVirusa = BazaPodataka.dohvatiViruse();
        StringBuffer pogreskaPoruka = new StringBuffer();

        if(nazivBolesti.getText().isBlank() || listViewBolesti.getSelectionModel().isEmpty()) {
            if(nazivBolesti.getText().isBlank()  && listViewBolesti.getSelectionModel().isEmpty()) {
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli podatke !");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();
            }
            else if(nazivBolesti.getText().isBlank()) {
                pogreskaPoruka.append("Niste unijeli naziv virusa!");
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli naziv virusa!");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();}

            else if(listViewBolesti.getSelectionModel().isEmpty()) {
                pogreskaPoruka.append("Niste odabrali simptome bolesti!");
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

            for(int i=0; i< listViewBolesti.getSelectionModel().getSelectedItems().size();i++){
                listaNazivaSimptoma.add(listViewBolesti.getSelectionModel().getSelectedItems().get(i).toString());
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
                preparedStatement.setString(2, nazivBolesti.getText());
                preparedStatement.setString(3, "FALSE");
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
            alertPoruka.setTitle("Uspješno se dodali bolest!");
            alertPoruka.setHeaderText("Bolest "+ nazivBolesti.getText() + " je uspješno dodana!");
            alertPoruka.setContentText("Uspješno dodan virus: " +"\n"
                    +"Naziv: "+ nazivBolesti.getText() + "\n"
                    +"Simptomi: "+ listViewBolesti.getSelectionModel().getSelectedItems().toString());
            alertPoruka.showAndWait();
            initialize();
            nazivBolesti.setText("");
        }


    }
}
