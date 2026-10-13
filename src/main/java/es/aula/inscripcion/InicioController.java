package es.aula.inscripcion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class InicioController {

    @FXML
    private Button cambioInscripcion;
    

    @FXML
    private void irAInscripcion() throws IOException {
    	
    	cambioInscripcion.requestFocus();
    	Parent vista = FXMLLoader.load(getClass().getResource("inscripcion.fxml"));
    	Stage stage = (Stage) cambioInscripcion.getScene().getWindow();
    	stage.getScene().setRoot(vista);
    	
    	
    }


}
