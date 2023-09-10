package io.github.fernando.domain.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
//@Table(name = "TB_CLIENTE", schema = "sc_ms0001")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;



    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Pedido> Pedidos;

    public Cliente() {
    }

    public Cliente(String name) {
        this.name = name;
    }


    public Set<Pedido> getPedidos() {
        return Pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        Pedidos = pedidos;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }







    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
