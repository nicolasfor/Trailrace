package com.cam.trailrace.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by nsanc on 13/09/2016.
 */
public class Usuario implements Serializable{

    private long id;

    private Date fechaCreado;

    private String nombre;

    private String correo;

    private String pais;

    private String ciudad;

    private String clave;

    private Date fechaNacimiento;

    private int tipoUsuario;

    private List<Equipo> equiposFavoritos;

    private List<Piloto> pilotosFavoritos;

    public Usuario() {
    }

    public Usuario(long id) {
        this.id = id;
    }

    public Usuario(String nombre, String correo, String clave){
        this.nombre=nombre;
        this.correo=correo;
        this.clave=clave;
    }
    public Usuario(long id, Date fechaCreado, String nombre, String correo, String pais, String ciudad, String clave, Date fechaNacimiento, int tipoUsuario, List<Equipo> equiposFavoritos, List<Piloto> pilotosFavoritos) {
        this.id = id;
        this.fechaCreado = fechaCreado;
        this.nombre = nombre;
        this.correo = correo;
        this.pais = pais;
        this.ciudad = ciudad;
        this.clave = clave;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoUsuario = tipoUsuario;
        this.equiposFavoritos = equiposFavoritos;
        this.pilotosFavoritos = pilotosFavoritos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(Date fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public List<Equipo> getEquiposFavoritos() {
        return equiposFavoritos;
    }

    public void setEquiposFavoritos(List<Equipo> equiposFavoritos) {
        this.equiposFavoritos = equiposFavoritos;
    }

    public List<Piloto> getPilotosFavoritos() {
        return pilotosFavoritos;
    }

    public void setPilotosFavoritos(List<Piloto> pilotosFavoritos) {
        this.pilotosFavoritos = pilotosFavoritos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                '}';
    }
}
