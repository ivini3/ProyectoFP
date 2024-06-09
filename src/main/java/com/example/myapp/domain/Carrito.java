package com.example.myapp.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "numeropedido")
@Entity
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long numeropedido;
    
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private int totalCantidad = 0;
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<Producto> productos = new ArrayList<>();
    public long getNumeropedido() {
        return numeropedido;
    }
    public void setNumeropedido(long numeropedido) {
        this.numeropedido = numeropedido;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public int getTotalCantidad() {
        return totalCantidad;
    }
    public void setTotalCantidad(int totalCantidad) {
        this.totalCantidad = totalCantidad;
    }
    public List<Producto> getProductos() {
        return productos;
    }
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
