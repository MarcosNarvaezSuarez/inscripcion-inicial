package es.aula.inscripcion;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Competidor {

	// Atributos como propiedades observables de JavaFX
	private final StringProperty nombre;
	private final StringProperty juego;
	private final StringProperty liga;
	private final StringProperty equipo;
	private final ObjectProperty<LocalDate> fecha;
	private final BooleanProperty necesitaPerifericos;
	private final StringProperty observaciones;

	// Constructor
	public Competidor(String nombre, String juego, String liga, String equipo, LocalDate fecha,
			boolean necesitaPerifericos, String observaciones) {
		this.nombre = new SimpleStringProperty(nombre);
		this.juego = new SimpleStringProperty(juego);
		this.liga = new SimpleStringProperty(liga);
		this.equipo = new SimpleStringProperty(equipo);
		this.fecha = new SimpleObjectProperty<>(fecha);
		this.necesitaPerifericos = new SimpleBooleanProperty(necesitaPerifericos);
		this.observaciones = new SimpleStringProperty(observaciones);
	}

	// --- MÉTODOS PARA 'nombre' ---
	public String getNombre() {
		return nombre.get();
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public StringProperty nombreProperty() {
		return nombre;
	}

	// --- MÉTODOS PARA 'juego' ---
	public String getJuego() {
		return juego.get();
	}

	public void setJuego(String juego) {
		this.juego.set(juego);
	}

	public StringProperty juegoProperty() {
		return juego;
	}

	// --- MÉTODOS PARA 'liga' ---
	public String getLiga() {
		return liga.get();
	}

	public void setLiga(String liga) {
		this.liga.set(liga);
	}

	public StringProperty ligaProperty() {
		return liga;
	}

	// --- MÉTODOS PARA 'equipo' ---
	public String getEquipo() {
		return equipo.get();
	}

	public void setEquipo(String equipo) {
		this.equipo.set(equipo);
	}

	public StringProperty equipoProperty() {
		return equipo;
	}

	// --- MÉTODOS PARA 'fecha' ---
	public LocalDate getFecha() {
		return fecha.get();
	}

	public void setFecha(LocalDate fecha) {
		this.fecha.set(fecha);
	}

	public ObjectProperty<LocalDate> fechaProperty() {
		return fecha;
	}

	// --- MÉTODOS PARA 'necesitaPerifericos' ---
	public boolean isNecesitaPerifericos() {
		return necesitaPerifericos.get();
	}

	public void setNecesitaPerifericos(boolean necesitaPerifericos) {
		this.necesitaPerifericos.set(necesitaPerifericos);
	}

	public BooleanProperty necesitaPerifericosProperty() {
		return necesitaPerifericos;
	}

	// --- MÉTODOS PARA 'observaciones' ---
	public String getObservaciones() {
		return observaciones.get();
	}

	public void setObservaciones(String observaciones) {
		this.observaciones.set(observaciones);
	}

	public StringProperty observacionesProperty() {
		return observaciones;
	}
}