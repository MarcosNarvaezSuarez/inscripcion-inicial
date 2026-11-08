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
import javafx.beans.binding.Bindings;

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
    private ComboBox<String> equiposComboBox;
    
    @FXML
    private DatePicker fechaTorneo; 
    
    @FXML
    private TextArea observaciones; 

    @FXML
    private CheckBox perifericosCheckBox; 
    
    @FXML
    private VBox resultados;
    
    @FXML
    private Label lblVistaPrevia;
    
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
    private void cambioLista() throws IOException {
        botonCambio.requestFocus();
        Parent vista = FXMLLoader.load(getClass().getResource("lista.fxml"));
        Stage stage = (Stage) botonCambio.getScene().getWindow();
        stage.getScene().setRoot(vista);
    }

    @FXML
    public void initialize() {
        juegosComboBox.getItems().addAll("Valorant", "League of Legends", "CS:GO", "Deadlock");
        ligasComboBox.getItems().addAll("Nivel profesional", "Nivel medio", "Nivel bajo");
        equiposComboBox.getItems().addAll("NaVi", "T1", "G2", "MKOI", "GENG","IG");
        
        nombreParticipante.textProperty().addListener((observable, oldValue, newValue) -> {
            borrarButton.setDisable(newValue.trim().isEmpty());
        });
        
        lblVistaPrevia.textProperty().bind(
            Bindings.when(nombreParticipante.textProperty().isEmpty().or(juegosComboBox.valueProperty().isNull()))
                    .then("Rellene nombre y juego para ver la vista previa")
                    .otherwise(Bindings.concat("Inscribiendo a: ", nombreParticipante.textProperty(), " en el torneo de ", juegosComboBox.valueProperty()))
        );
    }
    
    private void mostrarError(String mensaje) {
        // 1. Creamos la etiqueta con el mensaje que nos pasen entre los paréntesis
        Label etiquetaError = new Label(mensaje);
        
        // 2. Le damos estilo para que se vea rojo y en negrita
        etiquetaError.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold;");
        
        // 3. Lo añadimos a la caja de resultados de la pantalla
        resultados.getChildren().add(etiquetaError);
    }
    
    @FXML
    private void guardarInscripcion() throws IOException { // <-- ¡Añade throws IOException porque luego cambiaremos de pantalla!
        String nombre = nombreParticipante.getText();
        String juegoSeleccionado = juegosComboBox.getValue();
        String ligaSeleccionada = ligasComboBox.getValue();
        String equipoSeleccionado = equiposComboBox.getValue();
        LocalDate fecha = fechaTorneo.getValue();
        boolean necesitaPerifericos = perifericosCheckBox.isSelected();
        String textoObservaciones = observaciones.getText();
        
        resultados.getChildren().clear();
        
        // 1. Validar nombre
        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarError("Error: El nombre es obligatorio.");
            nombreParticipante.requestFocus(); // Lleva el ratón allí
            return; // Detiene la ejecución
        }

        // 2. Validar que se ha elegido un juego
        if (juegoSeleccionado == null) {
            mostrarError("Error: Debes seleccionar un juego.");
            juegosComboBox.requestFocus();
            return;
        }
        
        // 3. Validar que se ha elegido liga
        if (ligaSeleccionada == null) {
            mostrarError("Error: Debes seleccionar una liga.");
            ligasComboBox.requestFocus();
            return;
        }

        // 4. Validar que se ha elegido equipo
        if (equipoSeleccionado == null) {
            mostrarError("Error: Debes seleccionar un equipo.");
            equiposComboBox.requestFocus();
            return;
        }
        
        if (fecha == null) {
        	mostrarError("Error: La fecha no puede ser de una fecha pasada.");
        	fechaTorneo.requestFocus();
        	return;
        }

     // Si llegamos hasta aquí, no hay errores. Creamos el competidor:
        Competidor nuevoCompetidor = new Competidor(
            nombre, juegoSeleccionado, ligaSeleccionada, equipoSeleccionado, 
            fecha, necesitaPerifericos, textoObservaciones
        );

        // Lo añadimos a la lista que sobrevive en memoria:
        GestorDatos.getCompetidores().add(nuevoCompetidor);

        // Y por último, volvemos a la pantalla de la tabla:
        cambioLista();
        
        
        //Sección de resumen de la inscripción
        String infoJuego = juegoSeleccionado != null ? juegoSeleccionado : "Ninguno";
        String infoLiga = ligaSeleccionada != null ? ligaSeleccionada : "Ninguna";
        String infoFecha = fecha != null ? fecha.toString() : "Sin fecha";
        String infoPerifericos = necesitaPerifericos ? "Sí" : "No";
        String infoObservaciones = (textoObservaciones == null || textoObservaciones.trim().isEmpty()) ? "Ninguna" : textoObservaciones;
        String infoEquipo= equipoSeleccionado !=null ? equipoSeleccionado : "Ninguno";

        Label linea1 = new Label("Nombre: " + nombre + " | Juego: " + infoJuego);
        Label linea2 = new Label("Liga: " + infoLiga + " | Fecha: " + infoFecha);
        Label linea3 = new Label("Necesita periféricos: " + infoPerifericos + " | Equipo: " + equipoSeleccionado);
        
        Label linea4 = new Label("Observaciones: " + infoObservaciones);
        linea4.setWrapText(true);
        
        linea1.setStyle("-fx-text-fill: #333333;");
        linea2.setStyle("-fx-text-fill: #333333;");
        linea3.setStyle("-fx-text-fill: #333333;");
        linea4.setStyle("-fx-text-fill: #555555; -fx-font-style: italic;");

        resultados.getChildren().addAll(linea1, linea2, linea3, linea4);
    }
}