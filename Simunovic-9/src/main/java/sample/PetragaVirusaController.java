package sample;

import covidportal.iznimke.BazaPodatakaException;
import covidportal.main.BazaPodataka;
import covidportal.model.Bolest;
import covidportal.model.Simptom;
import covidportal.model.Virus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PetragaVirusaController {
    @FXML
    public TextField nazivVirusa;
    @FXML
    private TableView virusiTableView;
    @FXML
    List<Virus> listaVirusa;
    @FXML
    private TableColumn<Virus, Long> tableColumnIdVirusa;
    @FXML
    private TableColumn<Virus, String> tableColumnNazivVirusa;
    @FXML
    private TableColumn<Virus, Simptom> tableColumnSimptomiVirusa;


    @FXML
    public void initialize() throws IOException, BazaPodatakaException {
        tableColumnIdVirusa.setCellValueFactory(new PropertyValueFactory<Virus, Long>("id"));
        tableColumnNazivVirusa.setCellValueFactory(new PropertyValueFactory<Virus, String>("naziv"));
        tableColumnSimptomiVirusa.setCellValueFactory(new PropertyValueFactory<Virus, Simptom>("simptomi"));
        listaVirusa = BazaPodataka.dohvatiViruse();
        for(int i =0; i<listaVirusa.size();i++) {
            System.out.println("Lista virusa:" + listaVirusa.get(i).getNaziv().toString());
        }
        filtrirajViruse();
    }
    @FXML
    public void filtrirajViruse() {
        List<Virus> filterVirusa = new ArrayList<>();
        if (nazivVirusa.getText().isEmpty() == false) {
            filterVirusa= listaVirusa.stream()
                    .filter(p -> p.getNaziv().contains(nazivVirusa.getText())).collect(Collectors.toList());
        }
        else {
            filterVirusa = listaVirusa;
        }
        ObservableList<Bolest> listaVirusa = FXCollections.observableArrayList(filterVirusa);
        virusiTableView.setItems(listaVirusa);

    }




}
