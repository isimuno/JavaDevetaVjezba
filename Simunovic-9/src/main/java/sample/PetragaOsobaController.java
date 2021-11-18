package sample;

import covidportal.iznimke.BazaPodatakaException;
import covidportal.main.BazaPodataka;
import covidportal.model.Osoba;
import covidportal.model.Zupanija;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PetragaOsobaController {
    @FXML
    public TextField imeOsobe;
    @FXML
    public TextField prezimeOsobe;
    @FXML
    public TextField godineOsobe;
    @FXML
    public ComboBox comboBoxBolestOsobe = new ComboBox();
    @FXML
    public ComboBox comboBoxZupanijaOsobe = new ComboBox();
    @FXML
    private TableView osobeTableView;
    @FXML
    List<Osoba> listaOsoba;
    @FXML
    private TableColumn<Osoba, Long> tableColumnIdOsobe;
    @FXML
    private TableColumn<Osoba, String> tableColumnImeOsobe;
    @FXML
    private TableColumn<Osoba, String> tableColumnPrezimeOsobe;
    @FXML
    private TableColumn<Osoba, LocalDate> tableColumnGodineOsobe;
    @FXML
    private TableColumn<Osoba, String> tableColumnZupanijaOsobe;
    @FXML
    private TableColumn<Osoba, String> tableColumnBolestOsobe;
    @FXML
    private TableColumn<Osoba, String> tableColumnKontaktOsobeOsobe;


    @FXML
    public void initialize() throws IOException, BazaPodatakaException {
        tableColumnIdOsobe.setCellValueFactory(new PropertyValueFactory<Osoba, Long>("id"));
        tableColumnImeOsobe.setCellValueFactory(new PropertyValueFactory<Osoba, String>("ime"));
        tableColumnPrezimeOsobe.setCellValueFactory(new PropertyValueFactory<Osoba, String>("prezime"));
        tableColumnGodineOsobe.setCellValueFactory(new PropertyValueFactory<Osoba, LocalDate>("datumRodenja"));
        tableColumnGodineOsobe.setCellFactory(column -> {
            return new TableCell<Osoba, LocalDate>() {
                @Override
                protected void updateItem(LocalDate datumRodenja, boolean empty) {
                    super.updateItem(datumRodenja, empty);
                    if (datumRodenja == null || empty) {
                        setText(null);
                    } else {
                        LocalDate danasniDatum = LocalDate.now();
                        Period diff = Period.between(datumRodenja, danasniDatum);
                        setText(String.valueOf(diff.getYears()));
                    }
                }
            };
        });
        tableColumnZupanijaOsobe.setCellValueFactory(new PropertyValueFactory<Osoba, String>("zupanija"));
        tableColumnBolestOsobe.setCellValueFactory(new PropertyValueFactory<Osoba, String>("zarazenBolescu"));
        tableColumnKontaktOsobeOsobe.setCellValueFactory(new PropertyValueFactory<Osoba, String>("kontaktiraneOsobe"));
        listaOsoba = FXCollections.observableArrayList(BazaPodataka.dohvatiOsobe());
        for (int i = 0; i < listaOsoba.size(); i++) {
            System.out.println("Lista osoba:" + listaOsoba.get(i).getIme());
        }
        List<String> listaBolesti = new ArrayList<>();
        for (Osoba o : listaOsoba) {
            if (o.getZarazenBolescu() != null) {
                listaBolesti.add(o.getZarazenBolescu().getNaziv());
            }
        }
        Set<String> namesAlreadySeen = new HashSet<>();
        listaBolesti.removeIf(p -> !namesAlreadySeen.add(p));
        comboBoxBolestOsobe.getItems().add(" ");
        comboBoxBolestOsobe.getItems().addAll(listaBolesti);
        comboBoxBolestOsobe.setEditable(true);

        List<Zupanija> listaZupanija = BazaPodataka.dohvatiZupanije();
        List<String> naziviZupanija = new ArrayList<>();
        for (Zupanija z : listaZupanija) {
            naziviZupanija.add(z.getNaziv());
        }
        Set<String> dupliNazivi = new HashSet<>();
        naziviZupanija.removeIf(z -> !dupliNazivi.add(z));
        comboBoxZupanijaOsobe.getItems().add(" ");
        comboBoxZupanijaOsobe.getItems().addAll(naziviZupanija);
        comboBoxZupanijaOsobe.setEditable(true);
        comboBoxZupanijaOsobe.setValue(" ");
        comboBoxBolestOsobe.setValue(" ");
        filtrirajOsobe();
    }

    @FXML
    public void filtrirajOsobe() {
        List<Osoba> filterOsobe = new ArrayList<>();

        if (imeOsobe.getText().isEmpty() == false) {
            filterOsobe = listaOsoba.stream()
                    .filter(p -> p.getIme().startsWith(imeOsobe.getText())).collect(Collectors.toList());
        } else if (prezimeOsobe.getText().isEmpty() == false) {
            filterOsobe = listaOsoba.stream()
                    .filter(p -> p.getPrezime().startsWith(prezimeOsobe.getText())).collect(Collectors.toList());
        } else if (godineOsobe.getText().isEmpty() == false) {
            filterOsobe = listaOsoba.stream()
                    .filter(p -> p.getDatumRodenja().toString().equals(godineOsobe.getText())).collect(Collectors.toList());
        } else if (comboBoxZupanijaOsobe.getValue().toString().equals(" ") == false) {
            filterOsobe = listaOsoba.stream()
                    .filter(z -> z.getZupanija().getNaziv().equals(comboBoxZupanijaOsobe.getValue().toString())).collect(Collectors.toList());
        } else if (comboBoxBolestOsobe.getValue().toString().equals(" ") == false) {
            filterOsobe = listaOsoba.stream()
                    .filter(b -> b.getZarazenBolescu().getNaziv().equals(comboBoxBolestOsobe.getValue().toString())).collect(Collectors.toList());
        } else {
            filterOsobe = listaOsoba;
        }
        ObservableList<Osoba> listaOsoba = FXCollections.observableArrayList(filterOsobe);
        osobeTableView.setItems(listaOsoba);

    }


}
