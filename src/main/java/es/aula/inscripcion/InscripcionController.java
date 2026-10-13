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
import javafx.scene.layout.VBox; // Importante añadir esta importación
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
    private ComboBox<String> juegosComboBox;
    
    @FXML
    private ComboBox<String> ligasComboBox;
    
    @FXML
    private DatePicker fechaTorneo; 
    
    @FXML
    private TextArea observaciones; 

    @FXML
    private CheckBox perifericosCheckBox; 
    
    // Añadimos la VBox que pusiste en el FXML
    @FXML
    private VBox resultados;
    
    @FXML
    private void borrar() {
        // Limpiamos todos los campos
        nombreParticipante.clear();
        observaciones.clear();
        juegosComboBox.getSelectionModel().clearSelection();
        ligasComboBox.getSelectionModel().clearSelection();
        fechaTorneo.setValue(null);
        perifericosCheckBox.setSelected(false);
        
        // Limpiamos los resultados mostrados en pantalla
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
        
        // Habilitar el botón de borrar solo si se escribe algo en el nombre
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
        
        // Limpiamos los resultados anteriores antes de mostrar los nuevos
        resultados.getChildren().clear();
        
        if (nombre == null || nombre.trim().isEmpty()) {
            Label errorLabel = new Label("Error: El nombre es obligatorio.");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            resultados.getChildren().add(errorLabel);
            return; 
        }

        // Preparamos el texto a mostrar manejando posibles valores nulos
        String infoJuego = juegoSeleccionado != null ? juegoSeleccionado : "Ninguno";
        String infoLiga = ligaSeleccionada != null ? ligaSeleccionada : "Ninguna";
        String infoFecha = fecha != null ? fecha.toString() : "Sin fecha";
        String infoPerifericos = necesitaPerifericos ? "Sí" : "No";

        // Creamos Labels con la información
        Label linea1 = new Label("Nombre: " + nombre + " | Juego: " + infoJuego);
        Label linea2 = new Label("Liga: " + infoLiga + " | Fecha: " + infoFecha);
        Label linea3 = new Label("Necesita periféricos: " + infoPerifericos);
        
        // Aplicamos un poco de estilo para que se vea mejor (opcional)
        linea1.setStyle("-fx-text-fill: #333333;");
        linea2.setStyle("-fx-text-fill: #333333;");
        linea3.setStyle("-fx-text-fill: #333333;");

        // Añadimos los Labels creados dentro de la VBox
        resultados.getChildren().addAll(linea1, linea2, linea3);
    }
}