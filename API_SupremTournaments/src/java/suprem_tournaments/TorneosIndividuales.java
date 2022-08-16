/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package suprem_tournaments;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Creado el 
 * Terminado el
 * @author Sr.J
 */
@Entity
@Table(name = "torneos_individuales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TorneosIndividuales.findAll", query = "SELECT t FROM TorneosIndividuales t"),
    @NamedQuery(name = "TorneosIndividuales.findByIdTorneoIndividual", query = "SELECT t FROM TorneosIndividuales t WHERE t.idTorneoIndividual = :idTorneoIndividual"),
    @NamedQuery(name = "TorneosIndividuales.findByNombreTorneoIndividual", query = "SELECT t FROM TorneosIndividuales t WHERE t.nombreTorneoIndividual = :nombreTorneoIndividual"),
    @NamedQuery(name = "TorneosIndividuales.findByFotoUrlTorneoIndividual", query = "SELECT t FROM TorneosIndividuales t WHERE t.fotoUrlTorneoIndividual = :fotoUrlTorneoIndividual"),
    @NamedQuery(name = "TorneosIndividuales.findByDescripcionCorta", query = "SELECT t FROM TorneosIndividuales t WHERE t.descripcionCorta = :descripcionCorta"),
    @NamedQuery(name = "TorneosIndividuales.findByDescripcionCompleta", query = "SELECT t FROM TorneosIndividuales t WHERE t.descripcionCompleta = :descripcionCompleta"),
    @NamedQuery(name = "TorneosIndividuales.findByNivel", query = "SELECT t FROM TorneosIndividuales t WHERE t.nivel = :nivel"),
    @NamedQuery(name = "TorneosIndividuales.findBySolicitudesMaximos", query = "SELECT t FROM TorneosIndividuales t WHERE t.solicitudesMaximos = :solicitudesMaximos"),
    @NamedQuery(name = "TorneosIndividuales.findByFechaFinalizacion", query = "SELECT t FROM TorneosIndividuales t WHERE t.fechaFinalizacion = :fechaFinalizacion"),
    @NamedQuery(name = "TorneosIndividuales.findByFechaInicio", query = "SELECT t FROM TorneosIndividuales t WHERE t.fechaInicio = :fechaInicio"),

    //Querys creadas por Sr.J para en el futuro facilitar al usuario la busqueda de Torneos
    @NamedQuery(name = "TorneosIndividuales.findByMenoresEdad", query = "SELECT t FROM TorneosIndividuales t WHERE t.menoresEdad = :menoresEdad"),
    @NamedQuery(name = "TorneosIndividuales.findByNombreGestor", query = "SELECT t FROM TorneosIndividuales t WHERE t.idGestor.nombre = :nombreCreador"),
    @NamedQuery(name = "TorneosIndividuales.findByEmailGestor", query = "SELECT t FROM TorneosIndividuales t WHERE t.idGestor.email = :emailCreador"),
        
    @NamedQuery(name = "TorneosIndividuales.findByFechaFinalizacionDesc", query = "SELECT t FROM TorneosIndividuales t ORDER BY t.fechaFinalizacion desc"),
    @NamedQuery(name = "TorneosIndividuales.findByFechaFinalizacionAsc", query = "SELECT t FROM TorneosIndividuales t ORDER BY t.fechaFinalizacion asc"),
    @NamedQuery(name = "TorneosIndividuales.findByNivelDesc", query = "SELECT t FROM TorneosIndividuales t ORDER BY t.nivel desc"),
    @NamedQuery(name = "TorneosIndividuales.findByNivelAsc", query = "SELECT t FROM TorneosIndividuales t ORDER BY t.nivel asc"),
    @NamedQuery(name = "TorneosIndividuales.findByPlazasLibres", query = "SELECT t FROM TorneosIndividuales t WHERE size(t.solicitudesCollection) < t.solicitudesMaximos"),
    @NamedQuery(name = "TorneosIndividuales.findByPlazasLibresAndAfterDate", query = "SELECT t FROM TorneosIndividuales t WHERE size(t.solicitudesCollection) < t.solicitudesMaximos AND t.fechaFinalizacion > :fecha")

})
public class TorneosIndividuales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_torneo_individual")
    private Integer idTorneoIndividual;
    @Size(max = 69)
    @Column(name = "nombre_torneo_individual")
    private String nombreTorneoIndividual;
    @Size(max = 200)
    @Column(name = "foto_url_torneo_individual")
    private String fotoUrlTorneoIndividual;
    @Size(max = 128)
    @Column(name = "descripcion_corta")
    private String descripcionCorta;
    @Size(max = 200)
    @Column(name = "descripcion_completa")
    private String descripcionCompleta;
    @Column(name = "nivel")
    private Integer nivel;
    @Column(name = "solicitudes_maximos")
    private Integer solicitudesMaximos;
    @Column(name = "fecha_finalizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaFinalizacion;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "menores_edad")
    private Boolean menoresEdad;
    @OneToMany(mappedBy = "idTorneoIndividual")
    private Collection<CombatesIndividuales> combatesIndividualesCollection;
    @JoinColumn(name = "id_gestor", referencedColumnName = "id_gestor")
    @ManyToOne
    private Gestor idGestor;
    @OneToMany(mappedBy = "idTorneoIndividual")
    @JsonbTransient
    private Collection<Solicitudes> solicitudesCollection;

    public TorneosIndividuales() {
    }

    public TorneosIndividuales(Integer idTorneoIndividual) {
        this.idTorneoIndividual = idTorneoIndividual;
    }

    public Integer getIdTorneoIndividual() {
        return idTorneoIndividual;
    }

    public void setIdTorneoIndividual(Integer idTorneoIndividual) {
        this.idTorneoIndividual = idTorneoIndividual;
    }

    public String getNombreTorneoIndividual() {
        return nombreTorneoIndividual;
    }

    public void setNombreTorneoIndividual(String nombreTorneoIndividual) {
        this.nombreTorneoIndividual = nombreTorneoIndividual;
    }

    public String getFotoUrlTorneoIndividual() {
        return fotoUrlTorneoIndividual;
    }

    public void setFotoUrlTorneoIndividual(String fotoUrlTorneoIndividual) {
        this.fotoUrlTorneoIndividual = fotoUrlTorneoIndividual;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getDescripcionCompleta() {
        return descripcionCompleta;
    }

    public void setDescripcionCompleta(String descripcionCompleta) {
        this.descripcionCompleta = descripcionCompleta;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getSolicitudesMaximos() {
        return solicitudesMaximos;
    }

    public void setSolicitudesMaximos(Integer solicitudesMaximos) {
        this.solicitudesMaximos = solicitudesMaximos;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Boolean getMenoresEdad() {
        return menoresEdad;
    }

    public void setMenoresEdad(Boolean menoresEdad) {
        this.menoresEdad = menoresEdad;
    }

    @XmlTransient
    public Collection<CombatesIndividuales> getCombatesIndividualesCollection() {
        return combatesIndividualesCollection;
    }

    public void setCombatesIndividualesCollection(Collection<CombatesIndividuales> combatesIndividualesCollection) {
        this.combatesIndividualesCollection = combatesIndividualesCollection;
    }

    public Gestor getIdGestor() {
        return idGestor;
    }

    public void setIdGestor(Gestor idGestor) {
        this.idGestor = idGestor;
    }

    @XmlTransient
    public Collection<Solicitudes> getSolicitudesCollection() {
        return solicitudesCollection;
    }

    public void setSolicitudesCollection(Collection<Solicitudes> solicitudesCollection) {
        this.solicitudesCollection = solicitudesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTorneoIndividual != null ? idTorneoIndividual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TorneosIndividuales)) {
            return false;
        }
        TorneosIndividuales other = (TorneosIndividuales) object;
        if ((this.idTorneoIndividual == null && other.idTorneoIndividual != null) || (this.idTorneoIndividual != null && !this.idTorneoIndividual.equals(other.idTorneoIndividual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "suprem_tournaments.TorneosIndividuales[ idTorneoIndividual=" + idTorneoIndividual + " ]";
    }

}
