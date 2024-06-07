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

@Data    
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
}
