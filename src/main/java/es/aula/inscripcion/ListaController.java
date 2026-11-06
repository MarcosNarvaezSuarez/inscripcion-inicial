package es.aula.inscripcion;

import javafx.beans.binding.Bindings;
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
    @FXML private Button btnNueva, btnBorrar, btnVolver;
    @FXML private Label lblContador;
    @FXML private TextArea txtDetalles;

    private FilteredList<Competidor> listaFiltrada;

    @FXML
    public void initialize() {
        // 1. Configurar las columnas
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colJuego.setCellValueFactory(cellData -> cellData.getValue().juegoProperty());
        colLiga.setCellValueFactory(cellData -> cellData.getValue().ligaProperty());
        colEquipo.setCellValueFactory(cellData -> cellData.getValue().equipoProperty());
        colFecha.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
        colObservaciones.setCellValueFactory(cellData -> cellData.getValue().observacionesProperty());
        
        colPerifericos.setCellValueFactory(cellData -> cellData.getValue().necesitaPerifericosProperty());
        colPerifericos.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : (item ? "Sí" : "No"));
            }
        });

        // 2. Cargar datos desde el gestor en memoria
        listaFiltrada = new FilteredList<>(GestorDatos.getCompetidores(), p -> true);
        tablaInscripciones.setItems(listaFiltrada);

        // 3. Listener para actualizar el panel de Detalles al seleccionar una fila
        txtDetalles.setText("No hay ninguna inscripción seleccionada.");
        tablaInscripciones.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String detalles = String.format("• Nombre: %s\n• Juego: %s\n• Liga: %s\n• Equipo: %s\n• Fecha del torneo: %s\n• Necesita periféricos: %s\n\n• Observaciones:\n%s",
                        newVal.getNombre(),
                        newVal.getJuego(),
                        newVal.getLiga(),
                        newVal.getEquipo(),
                        newVal.getFecha() != null ? newVal.getFecha().toString() : "Sin fecha",
                        newVal.isNecesitaPerifericos() ? "Sí" : "No",
                        (newVal.getObservaciones() != null && !newVal.getObservaciones().isEmpty()) ? newVal.getObservaciones() : "Ninguna observación.");
                txtDetalles.setText(detalles);
            } else {
                txtDetalles.setText("No hay ninguna inscripción seleccionada.");
            }
        });

        // 4. Configurar filtro ListView lateral
        listaJuegos.getItems().addAll("Valorant", "League of Legends", "CS:GO", "Deadlock");
        listaJuegos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                listaFiltrada.setPredicate(competidor -> competidor.getJuego().equals(newVal));
            }
        });

        // 5. Data Bindings
        lblContador.textProperty().bind(Bindings.concat("Número de registros: ", Bindings.size(GestorDatos.getCompetidores())));
        btnBorrar.disableProperty().bind(tablaInscripciones.getSelectionModel().selectedItemProperty().isNull());
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
            GestorDatos.getCompetidores().remove(seleccionado); 
            // La tabla y el panel de detalles se actualizan solos gracias al binding y al listener
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