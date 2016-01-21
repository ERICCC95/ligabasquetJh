package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mycompany.myapp.domain.util.CustomDateTimeDeserializer;
import com.mycompany.myapp.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Jugador.
 */
@Entity
@Table(name = "JUGADOR")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Jugador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "nombre")
    private String nombre;

    @Column(name = "posicioncampo")
    private String posicioncampo;

    @Column(name = "canastas_totales")
    private Integer canastasTotales;

    @Column(name = "asistencias_totales")
    private Integer asistenciasTotales;

    @Column(name = "rebotes_totales")
    private Integer rebotesTotales;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "fecha_nacimiento")
    private DateTime fechaNacimiento;

    @ManyToOne
    private Equipo jugador;

    @OneToMany(mappedBy = "jugador")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EstadisticasJugadorPartido> jugadors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPosicioncampo() {
        return posicioncampo;
    }

    public void setPosicioncampo(String posicioncampo) {
        this.posicioncampo = posicioncampo;
    }

    public Integer getCanastasTotales() {
        return canastasTotales;
    }

    public void setCanastasTotales(Integer canastasTotales) {
        this.canastasTotales = canastasTotales;
    }

    public Integer getAsistenciasTotales() {
        return asistenciasTotales;
    }

    public void setAsistenciasTotales(Integer asistenciasTotales) {
        this.asistenciasTotales = asistenciasTotales;
    }

    public Integer getRebotesTotales() {
        return rebotesTotales;
    }

    public void setRebotesTotales(Integer rebotesTotales) {
        this.rebotesTotales = rebotesTotales;
    }

    public DateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(DateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Equipo getJugador() {
        return jugador;
    }

    public void setJugador(Equipo equipo) {
        this.jugador = equipo;
    }

    public Set<EstadisticasJugadorPartido> getJugadors() {
        return jugadors;
    }

    public void setJugadors(Set<EstadisticasJugadorPartido> estadisticasjugadorpartidos) {
        this.jugadors = estadisticasjugadorpartidos;
    }

    @Override
    public boolean equals(Object x) {
        if (this == x) {
            return true;
        }
        if (x == null || getClass() != x.getClass()) {
            return false;
        }

        Jugador jugador = (Jugador) x;

        if ( ! Objects.equals(id, jugador.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                ", posicioncampo='" + posicioncampo + "'" +
                ", canastasTotales='" + canastasTotales + "'" +
                ", asistenciasTotales='" + asistenciasTotales + "'" +
                ", rebotesTotales='" + rebotesTotales + "'" +
                ", fechaNacimiento='" + fechaNacimiento + "'" +
                '}';
    }
}
