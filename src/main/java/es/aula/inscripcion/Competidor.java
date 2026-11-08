package es.aula.inscripcion;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Competidor {

	private final StringProperty nombre;

	private String juego;
	private String liga;
	private String equipo;
	private LocalDate fecha;
	private boolean necesitaPerifericos;
	private String observaciones;

	public Competidor(String nombre, String juego, String liga, String equipo, LocalDate fecha,
			boolean necesitaPerifericos, String observaciones) {
		this.nombre = new SimpleStringProperty(nombre);
		this.juego = juego;
		this.liga = liga;
		this.equipo = equipo;
		this.fecha = fecha;
		this.necesitaPerifericos = necesitaPerifericos;
		this.observaciones = observaciones;
	}

	public String getNombre() {
		return nombre.get();
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public StringProperty nombreProperty() {
		return nombre;
	}

	public String getJuego() {
		return juego;
	}

	public void setJuego(String juego) {
		this.juego = juego;
	}

	public String getLiga() {
		return liga;
	}

	public void setLiga(String liga) {
		this.liga = liga;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean isNecesitaPerifericos() {
		return necesitaPerifericos;
	}

	public void setNecesitaPerifericos(boolean necesitaPerifericos) {
		this.necesitaPerifericos = necesitaPerifericos;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}