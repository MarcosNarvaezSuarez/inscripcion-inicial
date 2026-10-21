package es.aula.inscripcion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class InscripcionController {
    
    @FXML
    private TextField nombreParticipante; 

    @FXML
    private Button borrarButton;
    
    @FXML
    private Button botonCambio;
    
    @FXML
    private Button inscribirseButton;
    
    @FXML
    private ComboBox<String> juegosComboBox;
    
    @FXML
    private ComboBox<String> ligasComboBox;
    
    @FXML
    private DatePicker fechaTorneo; 
    
    @FXML
    private TextArea observaciones; 

    @FXML
    private CheckBox perifericosCheckBox; 
    
    @FXML
    private VBox resultados;
    
    @FXML
    private void borrar() {
        nombreParticipante.clear();
        observaciones.clear();
        juegosComboBox.getSelectionModel().clearSelection();
        ligasComboBox.getSelectionModel().clearSelection();
        fechaTorneo.setValue(null);
        perifericosCheckBox.setSelected(false);
        
        resultados.getChildren().clear();
        borrarButton.setDisable(true);
    }
    
    @FXML
    private void cambioInicio() throws IOException {
        botonCambio.requestFocus();
        Parent vista = FXMLLoader.load(getClass().getResource("inicio.fxml"));
        Stage stage = (Stage) botonCambio.getScene().getWindow();
        stage.getScene().setRoot(vista);
    }

    @FXML
    public void initialize() {
        juegosComboBox.getItems().addAll("Valorant", "League of Legends", "CS:GO", "Deadlock");
        ligasComboBox.getItems().addAll("Nivel profesional", "Nivel medio", "Nivel bajo");
        
        nombreParticipante.textProperty().addListener((observable, oldValue, newValue) -> {
            borrarButton.setDisable(newValue.trim().isEmpty());
        });
    }
    
    @FXML
    private void guardarInscripcion() {
        String nombre = nombreParticipante.getText();
        String juegoSeleccionado = juegosComboBox.getValue();
        String ligaSeleccionada = ligasComboBox.getValue();
        LocalDate fecha = fechaTorneo.getValue();
        boolean necesitaPerifericos = perifericosCheckBox.isSelected();
        String textoObservaciones = observaciones.getText();
        
        resultados.getChildren().clear();
        
        if (nombre == null || nombre.trim().isEmpty()) {
            Label errorLabel = new Label("Error: El nombre es obligatorio.");
            errorLabel.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold;");
            resultados.getChildren().add(errorLabel);
            return; 
        }

        String infoJuego = juegoSeleccionado != null ? juegoSeleccionado : "Ninguno";
        String infoLiga = ligaSeleccionada != null ? ligaSeleccionada : "Ninguna";
        String infoFecha = fecha != null ? fecha.toString() : "Sin fecha";
        String infoPerifericos = necesitaPerifericos ? "Sí" : "No";
        String infoObservaciones = (textoObservaciones == null || textoObservaciones.trim().isEmpty()) ? "Ninguna" : textoObservaciones;

        Label linea1 = new Label("Nombre: " + nombre + " | Juego: " + infoJuego);
        Label linea2 = new Label("Liga: " + infoLiga + " | Fecha: " + infoFecha);
        Label linea3 = new Label("Necesita periféricos: " + infoPerifericos);
        
        Label linea4 = new Label("Observaciones: " + infoObservaciones);
        linea4.setWrapText(true);
        
        linea1.setStyle("-fx-text-fill: #333333;");
        linea2.setStyle("-fx-text-fill: #333333;");
        linea3.setStyle("-fx-text-fill: #333333;");
        linea4.setStyle("-fx-text-fill: #555555; -fx-font-style: italic;");

        resultados.getChildren().addAll(linea1, linea2, linea3, linea4);
    }
}