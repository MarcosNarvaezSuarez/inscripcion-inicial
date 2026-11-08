package es.aula.inscripcion;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ListaController {

    @FXML private TableView<Competidor> tablaInscripciones;
    @FXML private TableColumn<Competidor, String> colNombre, colJuego, colLiga, colEquipo, colObservaciones;
    @FXML private TableColumn<Competidor, LocalDate> colFecha;
    @FXML private TableColumn<Competidor, Boolean> colPerifericos;

    @FXML private ListView<String> listaJuegos;
    @FXML private Button btnNueva, btnBorrar, btnRecuperar, btnVolver;
    @FXML private Label lblContador;
    @FXML private TextArea txtDetalles;

    private FilteredList<Competidor> listaFiltrada;
    
    // Propiedad para guardar la última inscripción borrada
    private final ObjectProperty<Competidor> ultimoEliminado = new SimpleObjectProperty<>(null);

    @FXML
    public void initialize() {

        
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        
        
        colJuego.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getJuego()));
        colLiga.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLiga()));
        colEquipo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEquipo()));
        colFecha.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getFecha()));
        colObservaciones.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getObservaciones()));
        
        
        colPerifericos.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().isNecesitaPerifericos()));
        colPerifericos.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : (item ? "Sí" : "No"));
            }
        });

        listaFiltrada = new FilteredList<>(GestorDatos.getCompetidores(), p -> true);
        tablaInscripciones.setItems(listaFiltrada);

        txtDetalles.setText("No hay ninguna inscripción seleccionada.");
        tablaInscripciones.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String detalles = String.format("• Nombre: %s\n• Juego: %s\n• Liga: %s\n• Equipo: %s\n• Fecha del torneo: %s\n• Necesita periféricos: %s\n\n• Observaciones:\n%s",
                        newVal.getNombre(), newVal.getJuego(), newVal.getLiga(), newVal.getEquipo(),
                        newVal.getFecha() != null ? newVal.getFecha().toString() : "Sin fecha",
                        newVal.isNecesitaPerifericos() ? "Sí" : "No",
                        (newVal.getObservaciones() != null && !newVal.getObservaciones().isEmpty()) ? newVal.getObservaciones() : "Ninguna observación.");
                txtDetalles.setText(detalles);
            } else {
                txtDetalles.setText("No hay ninguna inscripción seleccionada.");
            }
        });

        listaJuegos.getItems().addAll("Valorant", "League of Legends", "CS:GO", "Deadlock");
        listaJuegos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                listaFiltrada.setPredicate(competidor -> competidor.getJuego().equals(newVal));
            }
        });

        // Bindings
        lblContador.textProperty().bind(Bindings.concat("Número de registros: ", Bindings.size(GestorDatos.getCompetidores())));
        btnBorrar.disableProperty().bind(tablaInscripciones.getSelectionModel().selectedItemProperty().isNull());
        
        // El botón recuperar está deshabilitado si ultimoEliminado es nulo
        btnRecuperar.disableProperty().bind(ultimoEliminado.isNull());
    }

    @FXML
    private void quitarFiltro() {
        listaJuegos.getSelectionModel().clearSelection();
        listaFiltrada.setPredicate(p -> true);
    }

    @FXML
    private void borrarInscripcion() {
        Competidor seleccionado = tablaInscripciones.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            // Guardamos el objeto antes de borrarlo
            ultimoEliminado.set(seleccionado);
            GestorDatos.getCompetidores().remove(seleccionado); 
        }
    }

    @FXML
    private void recuperarInscripcion() {
        Competidor recuperado = ultimoEliminado.get();
        if (recuperado != null) {
            // Lo añadimos de vuelta a la colección
            GestorDatos.getCompetidores().add(recuperado);
            // Vaciamos la variable de recuperación (deshabilita el botón automáticamente)
            ultimoEliminado.set(null);
            // Seleccionamos el objeto recién recuperado en la tabla
            tablaInscripciones.getSelectionModel().select(recuperado);
        }
    }

    @FXML
    private void irAInscripcion() throws IOException {
        cambiarVentana("inscripcion.fxml", btnNueva);
    }

    @FXML
    private void volverInicio() throws IOException {
        cambiarVentana("inicio.fxml", btnVolver);
    }

    private void cambiarVentana(String fxml, Button botonOrigen) throws IOException {
        Parent vista = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) botonOrigen.getScene().getWindow();
        stage.getScene().setRoot(vista);
    }
}