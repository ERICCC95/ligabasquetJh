package com.mycompany.myapp.domain;

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
 * A Partido.
 */
@Entity
@Table(name = "PARTIDO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Partido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "fecha")
    private DateTime fecha;

    @ManyToOne
    private Arbitro arbitro;

    @ManyToOne
    private Equipo equipoV;

    @ManyToOne
    private Equipo equipoL;

    @OneToOne
    private EstadisticasJugadorPartido partido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getFecha() {
        return fecha;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }

    public Equipo getEquipoV() {
        return equipoV;
    }

    public void setEquipoV(Equipo equipo) {
        this.equipoV = equipo;
    }

    public Equipo getEquipoL() {
        return equipoL;
    }

    public void setEquipoL(Equipo equipo) {
        this.equipoL = equipo;
    }

    public EstadisticasJugadorPartido getPartido() {
        return partido;
    }

    public void setPartido(EstadisticasJugadorPartido estadisticasjugadorpartido) {
        this.partido = estadisticasjugadorpartido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Partido partido = (Partido) o;

        if ( ! Objects.equals(id, partido.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Partido{" +
                "id=" + id +
                ", fecha='" + fecha + "'" +
                '}';
    }
}
