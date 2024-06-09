package com.example.myapp.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

 
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private String imagen;
    @Column(columnDefinition = "LONGTEXT")
@Lob
   private String descripcion;
   private int stock; 
   private double precio; 
   private int cantidad;
@ManyToOne
@OnDelete (action = OnDeleteAction.CASCADE)
private Categoria categoria;
@ManyToOne
  @JoinColumn(name = "carrito_id")
    private Carrito carrito;
public long getId() {
  return id;
}
public void setId(long id) {
  this.id = id;
}
public String getNombre() {
  return nombre;
}
public void setNombre(String nombre) {
  this.nombre = nombre;
}
public String getImagen() {
  return imagen;
}
public void setImagen(String imagen) {
  this.imagen = imagen;
}
public String getDescripcion() {
  return descripcion;
}
public void setDescripcion(String descripcion) {
  this.descripcion = descripcion;
}
public int getStock() {
  return stock;
}
public void setStock(int stock) {
  this.stock = stock;
}
public double getPrecio() {
  return precio;
}
public void setPrecio(double precio) {
  this.precio = precio;
}
public int getCantidad() {
  return cantidad;
}
public void setCantidad(int cantidad) {
  this.cantidad = cantidad;
}
public Categoria getCategoria() {
  return categoria;
}
public void setCategoria(Categoria categoria) {
  this.categoria = categoria;
}
public Carrito getCarrito() {
  return carrito;
}
public void setCarrito(Carrito carrito) {
  this.carrito = carrito;
}
}
