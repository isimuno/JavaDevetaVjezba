package sample;

import covidportal.enumeracija.VrijednostiSimptoma;
import covidportal.iznimke.BazaPodatakaException;
import covidportal.main.BazaPodataka;
import covidportal.model.Simptom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PetragaSimptomaController {
    @FXML
    public TextField nazivSimptoma;
    @FXML
    private TableView simptomiTableView;
    @FXML
    public ComboBox comboBoxVrijednostSimptoma = new ComboBox();
    @FXML
    List<Simptom> listaSimptoma;
    @FXML
    private TableColumn<Simptom, Long> tableColumnIdSimptoma;
    @FXML
    private TableColumn<Simptom, String> tableColumnNazivSimptoma;
    @FXML
    private TableColumn<Simptom, VrijednostiSimptoma> tableColumnVrijednostSimptoma;


    @FXML
    public void initialize() throws IOException, BazaPodatakaException {
        tableColumnIdSimptoma.setCellValueFactory(new PropertyValueFactory<Simptom, Long>("id"));
        tableColumnNazivSimptoma.setCellValueFactory(new PropertyValueFactory<Simptom, String>("naziv"));
        tableColumnVrijednostSimptoma.setCellValueFactory(new PropertyValueFactory<Simptom, VrijednostiSimptoma>("vrijednost"));
        listaSimptoma = BazaPodataka.dohvatiSimptome();

        comboBoxVrijednostSimptoma.getItems().add(" ");
        VrijednostiSimptoma[] yourEnums = VrijednostiSimptoma.class.getEnumConstants();
        for (int i=0;i< yourEnums.length;i++){
            comboBoxVrijednostSimptoma.getItems().add(yourEnums[i].getNaziv());
        }
        comboBoxVrijednostSimptoma.setValue(" ");

        for(int i =0; i<listaSimptoma.size();i++) {
            System.out.println("Lista simptoma:" + listaSimptoma.get(i).getNaziv().toString());
        }
        filtrirajSimptome();
    }

    @FXML
    public void filtrirajSimptome() {
        List<Simptom> filterSimptoma;
        if (nazivSimptoma.getText().isEmpty() == false) {
            filterSimptoma = listaSimptoma.stream()
                    .filter(p -> p.getNaziv().contains(nazivSimptoma.getText())).collect(Collectors.toList());
        }
        else if (comboBoxVrijednostSimptoma.getValue().toString().equals(" ")==false) {
            filterSimptoma= listaSimptoma.stream()
                    .filter(s -> s.getVrijednost().getNaziv().equals(comboBoxVrijednostSimptoma.getValue().toString())).collect(Collectors.toList());
        }
        else {
            filterSimptoma = listaSimptoma;
        }
        ObservableList<Simptom> listaSimptoma = FXCollections.observableArrayList(filterSimptoma);
        simptomiTableView.setItems(listaSimptoma);

    }




}
