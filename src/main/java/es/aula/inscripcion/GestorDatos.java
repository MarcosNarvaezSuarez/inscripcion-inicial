package es.aula.inscripcion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class GestorDatos {

	private static final ObservableList<Competidor> competidores = FXCollections.observableArrayList(
			new Competidor("Ana", "Valorant", "Nivel profesional", "NaVi", LocalDate.of(2024, 5, 10), true,
					"Lleva su propio teclado"),
			new Competidor("Luis", "League of Legends", "Nivel medio", "G2", LocalDate.of(2024, 5, 12), false,
					"Ninguna"),
			new Competidor("Carlos", "CS:GO", "Nivel alto", "T1", LocalDate.of(2024, 6, 1), true, "Alergia al polvo"),
			new Competidor("Marta", "Valorant", "Nivel medio", "MKOI", LocalDate.of(2024, 5, 10), false, "Ninguna"),
			new Competidor("Pedro", "Deadlock", "Nivel bajo", "GENG", LocalDate.of(2024, 7, 20), true,
					"Necesita auriculares especiales"));

	public static ObservableList<Competidor> getCompetidores() {
		return competidores;
	}
}