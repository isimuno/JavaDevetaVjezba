package sample;

import covidportal.iznimke.BazaPodatakaException;
import covidportal.main.BazaPodataka;
import covidportal.model.Bolest;
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

public class PetragaBolestiController {
    @FXML
    public TextField nazivBolesti;
    @FXML
    private TableView bolestiTableView;
    @FXML
    List<Bolest> listaBolesti;
    @FXML
    private TableColumn<Bolest, Long> tableColumnIdBolesti;
    @FXML
    private TableColumn<Bolest, String> tableColumnNazivBolesti;
    @FXML
    private TableColumn<Bolest, String> tableColumnSimptomiBolesti;


    @FXML
    public void initialize() throws IOException, BazaPodatakaException {
        tableColumnIdBolesti.setCellValueFactory(new PropertyValueFactory<Bolest, Long>("id"));
        tableColumnNazivBolesti.setCellValueFactory(new PropertyValueFactory<Bolest, String>("naziv"));
        tableColumnSimptomiBolesti.setCellValueFactory(new PropertyValueFactory<Bolest, String>("simptomi"));
        listaBolesti = BazaPodataka.dohvatiBolesti();
        for(int i =0; i<listaBolesti.size();i++) {
            System.out.println("Lista bolesti:" + listaBolesti.get(i).getNaziv());
        }
        filtrirajBolesti();
    }
    @FXML
    public void filtrirajBolesti() {
        List<Bolest> filterBolesti = new ArrayList<>();
        if (nazivBolesti.getText().isEmpty() == false) {
            filterBolesti= listaBolesti.stream()
                    .filter(p -> p.getNaziv().contains(nazivBolesti.getText())).collect(Collectors.toList());
        }
        else {
            filterBolesti = listaBolesti;
        }
        ObservableList<Bolest> listaBolesti = FXCollections.observableArrayList(filterBolesti);
        bolestiTableView.setItems(listaBolesti);

    }

}
