package sample;

import covidportal.iznimke.BazaPodatakaException;
import covidportal.main.BazaPodataka;
import covidportal.model.*;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DodavanjeNoveOsobeController {

    @FXML
    private Label idOsobe;
    @FXML
    private TextField imeOsobe;
    @FXML
    private TextField prezimeOsobe;
    @FXML
    private DatePicker datumRodenja;
    @FXML
    public ComboBox comboBoxBolestOsobe = new ComboBox();
    @FXML
    public ComboBox comboBoxZupanijaOsobe = new ComboBox();
    @FXML
    ListView<Osoba> listViewKontaktOsobe;

    Long maxID = Long.valueOf(0);
    List<Osoba> listaOsoba = new ArrayList<>();
    List<Zupanija> listaZupanija = new ArrayList<>();
    List<Bolest> listaBolesti = new ArrayList<>();
    List<Virus> listaVirusa = new ArrayList<>();

    public void initialize() throws IOException, BazaPodatakaException {
        listaOsoba = BazaPodataka.dohvatiOsobe();
        for (int i = 0; i < listaOsoba.size(); i++) {
            if (Integer.parseInt(listaOsoba.get(i).getId().toString()) > maxID) {
                maxID = listaOsoba.get(i).getId();
            } else {
                continue;
            }
        }

        listaZupanija = BazaPodataka.dohvatiZupanije();
        listaBolesti = BazaPodataka.dohvatiBolesti();
        listaVirusa =  BazaPodataka.dohvatiViruse();

        ObservableList<Zupanija> zupanije = FXCollections.observableArrayList(listaZupanija);
        ObservableList<Bolest> bolesti = FXCollections.observableArrayList(listaBolesti);
        ObservableList<Virus> virusi = FXCollections.observableArrayList(listaVirusa);
        comboBoxBolestOsobe.getItems().add(" ");
        comboBoxBolestOsobe.getItems().addAll(bolesti);
        comboBoxBolestOsobe.getItems().addAll(virusi);
        comboBoxZupanijaOsobe.getItems().add(" ");
        comboBoxZupanijaOsobe.getItems().addAll(zupanije);
        ObservableList<Osoba> osobe = FXCollections.observableArrayList(listaOsoba);
        listViewKontaktOsobe.setItems(osobe);
        listViewKontaktOsobe.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Label label = new Label();
        label.setTextFill(Color.RED);

        listViewKontaktOsobe.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends Osoba> ov, Osoba old_val, Osoba new_val) -> {
                    ObservableList<Osoba> selectedItems = listViewKontaktOsobe.getSelectionModel().getSelectedItems();

                    StringBuilder builder = new StringBuilder("Selected items :");

                    for (Osoba name : selectedItems) {
                        builder.append(name.getIme().toString() + "\n");
                    }


                });
        idOsobe.setText(String.valueOf(maxID + 1));
    }

    public void dodajNovuOsobu() throws IOException, BazaPodatakaException {
        listaOsoba = BazaPodataka.dohvatiOsobe();
        StringBuffer pogreskaPoruka = new StringBuffer();

        if (imeOsobe.getText().isBlank() || prezimeOsobe.getText().isBlank() || datumRodenja.getValue().toString().isBlank() || comboBoxBolestOsobe.getSelectionModel().getSelectedItem().toString().equals(" ") || comboBoxZupanijaOsobe.getSelectionModel().getSelectedItem().toString().equals(" ")) {
            if (imeOsobe.getText().isBlank() && listViewKontaktOsobe.getSelectionModel().isEmpty()) {
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli podatke !");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();
            } else if (imeOsobe.getText().isBlank()) {
                pogreskaPoruka.append("Niste unijeli ime osobe!");
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli ime!");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();
            } else if (prezimeOsobe.getText().isBlank()) {
                pogreskaPoruka.append("Niste unijeli prezime osobe!");
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli prezime!");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();
            } else if (datumRodenja.getValue().toString().isBlank()) {
                pogreskaPoruka.append("Niste unijeli starost osobe!");
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste unijeli starost!");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();
            } else if (comboBoxZupanijaOsobe.getSelectionModel().getSelectedItem().toString().equals(" ")) {
                pogreskaPoruka.append("Niste odabrali županiju prebivališta!");
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste odabrali županiju!");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();
            } else if (comboBoxBolestOsobe.getSelectionModel().getSelectedItem().toString().equals(" ")) {
                pogreskaPoruka.append("Niste odabrali bolest osobe!");
                Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
                alertPoruka.setTitle("Pogreska prilikom unosa !");
                alertPoruka.setHeaderText("Niste odabrali bolest!");
                alertPoruka.setContentText("Molimo popunite sva polja prilikom unosa !");
                alertPoruka.showAndWait();
            }
        } else {
            List<Osoba> listaKontaktOsoba = new ArrayList<>();
            maxID += 1;
            String idOdabraneZupanije = " ";
            for (Zupanija z : listaZupanija) {
                if (z.getNaziv().equals(comboBoxZupanijaOsobe.getSelectionModel().getSelectedItem().toString())) {
                    idOdabraneZupanije = z.getId().toString();
                }
            }
            String idOdabraneBolesti = " ";
            for (Virus v : listaVirusa) {
                if (v.getNaziv().equals(comboBoxBolestOsobe.getSelectionModel().getSelectedItem().toString())) {
                    idOdabraneBolesti = v.getId().toString();
                }
            }
            if (idOdabraneBolesti == " ") {
                for (Bolest b : listaBolesti) {
                    if (b.getNaziv().equals(comboBoxBolestOsobe.getSelectionModel().getSelectedItem().toString())) {
                        idOdabraneBolesti = b.getId().toString();
                    }
                }
            }
            if (listViewKontaktOsobe.getSelectionModel().isEmpty() == true) {
                System.out.println("Nema kontakt osoba!");
            } else {
                for (int i = 0; i < listViewKontaktOsobe.getSelectionModel().getSelectedItems().size(); i++) {
                   listaKontaktOsoba.add(listViewKontaktOsobe.getItems().get(i));
                }
            }

            try (Connection veza = BazaPodataka.spojiSeNaBazu()) {
                PreparedStatement preparedStatement = veza
                        .prepareStatement(
                                "insert into OSOBA(ID, IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID, BOLEST_ID)" +
                                        "values (?, ?, ?, ?, ?, ?);");
                preparedStatement.setString(1, maxID.toString());
                preparedStatement.setString(2, imeOsobe.getText());
                preparedStatement.setString(3, prezimeOsobe.getText());
                preparedStatement.setString(4, datumRodenja.getValue().toString());
                preparedStatement.setString(5, idOdabraneZupanije);
                preparedStatement.setString(6, idOdabraneBolesti);
                preparedStatement.executeUpdate();

                for(int i=0;i<listaKontaktOsoba.size();i++){
                    PreparedStatement preparedStatementSimptomi = veza
                            .prepareStatement(
                                    "insert into KONTAKTIRANE_OSOBE(OSOBA_ID, KONTAKTIRANA_OSOBA_ID)" +
                                            "values (?, ?);");
                    preparedStatementSimptomi.setString(1, maxID.toString());
                    preparedStatementSimptomi.setString(2, listaKontaktOsoba.get(i).getId().toString());
                    preparedStatementSimptomi.executeUpdate();
                }
            } catch (IOException | SQLException ex) {
                String poruka = "Došlo je do pogreške u radu s bazom podataka";
                throw new BazaPodatakaException(poruka, ex);
            }




            Alert alertPoruka = new Alert(Alert.AlertType.INFORMATION);
            alertPoruka.setTitle("Uspješno se dodali sobu!");
            alertPoruka.setHeaderText("Osoba " + imeOsobe.getText() + " je uspješno dodana!");
            alertPoruka.setContentText("Uspješno dodana osoba: " + "\n"
                    + "Ime osoba:: " + imeOsobe.getText() + "\n"
                    + "Prezime osobe: " + prezimeOsobe.getText() + "\n"
                    + "Starost osobe: " + datumRodenja.getValue().toString()+ "\n"
                    + "Županija prebivališta osobe: " + comboBoxZupanijaOsobe.getSelectionModel().getSelectedItem().toString()+ "\n"
                    + "Bolest osobe: " + comboBoxBolestOsobe.getSelectionModel().getSelectedItem().toString()+ "\n"
                    + "Kontakt osobe : " + listViewKontaktOsobe.getSelectionModel().getSelectedItems().toString());
            alertPoruka.showAndWait();
            initialize();
            imeOsobe.setText("");
            prezimeOsobe.setText("");
            datumRodenja.setValue(LocalDate.now());
            comboBoxBolestOsobe.setValue(" ");
            comboBoxZupanijaOsobe.setValue(" ");

        }


    }

}
