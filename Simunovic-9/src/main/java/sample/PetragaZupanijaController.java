package sample;
import covidportal.iznimke.BazaPodatakaException;
import covidportal.main.BazaPodataka;
import covidportal.model.Zupanija;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PetragaZupanijaController {
    @FXML
    private BorderPane rootPane;
    @FXML
    public TextField nazivZupanije;
    @FXML
    private TableView zupanijeTableView;
    List<Zupanija> listaZupanija;
    @FXML
    private TableColumn<Zupanija, Long> tableColumnIdZupanije;
    @FXML
    private TableColumn<Zupanija, String> tableColumnNazivZupanije;
    @FXML
    private TableColumn<Zupanija, Integer> tableColumnBrojStanovnika;
    @FXML
    private TableColumn<Zupanija, Integer> tableColumnBrojZarazenih;

    @FXML
    public void initialize() throws IOException, BazaPodatakaException {
        tableColumnIdZupanije.setCellValueFactory(new PropertyValueFactory<Zupanija, Long>("id"));
        tableColumnNazivZupanije.setCellValueFactory(new PropertyValueFactory<Zupanija, String>("naziv"));
        tableColumnBrojStanovnika.setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojStanovnika"));
        tableColumnBrojZarazenih.setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojZarazenihOsoba"));
        listaZupanija = BazaPodataka.dohvatiZupanije();
        for(int i =0; i<listaZupanija.size();i++) {
            System.out.println("Lista bolesti:" + listaZupanija.get(i).getNaziv().toString());
        }
        filtrirajZupanije();
    }
    @FXML
    public void filtrirajZupanije() {
        List<Zupanija> filterZupanija = new ArrayList<>();
        if (nazivZupanije.getText().isEmpty() == false) {
            filterZupanija= listaZupanija.stream()
                    .filter(p -> p.getNaziv().contains(nazivZupanije.getText())).collect(Collectors.toList());
        }
        else {
            filterZupanija = listaZupanija;
        }
        ObservableList<Zupanija> listaZupanija = FXCollections.observableArrayList(filterZupanija);
        zupanijeTableView.setItems(listaZupanija);

    }




}
