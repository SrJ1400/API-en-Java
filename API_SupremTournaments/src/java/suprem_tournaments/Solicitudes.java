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
@Table(name = "solicitudes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitudes.findAll", query = "SELECT s FROM Solicitudes s"),
    @NamedQuery(name = "Solicitudes.findByIdSolicitudes", query = "SELECT s FROM Solicitudes s WHERE s.idSolicitudes = :idSolicitudes"),
    @NamedQuery(name = "Solicitudes.findByNombreSolicitudes", query = "SELECT s FROM Solicitudes s WHERE s.nombreSolicitudes = :nombreSolicitudes"),
    @NamedQuery(name = "Solicitudes.findByFotoUrlSolicitudes", query = "SELECT s FROM Solicitudes s WHERE s.fotoUrlSolicitudes = :fotoUrlSolicitudes"),
    @NamedQuery(name = "Solicitudes.findByFechaNacimiento", query = "SELECT s FROM Solicitudes s WHERE s.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Solicitudes.findByDatos", query = "SELECT s FROM Solicitudes s WHERE s.datos = :datos"),
    @NamedQuery(name = "Solicitudes.findByDatosPublicos", query = "SELECT s FROM Solicitudes s WHERE s.datosPublicos = :datosPublicos"),
    @NamedQuery(name = "Solicitudes.findByEstado", query = "SELECT s FROM Solicitudes s WHERE s.estado = :estado"),
    
    //Query personalizada
    @NamedQuery(name = "Solicitudes.findByTorneoIndividualId", query = "SELECT s FROM Solicitudes s WHERE s.idTorneoIndividual.idTorneoIndividual = :idTorneoIndividual"),
    @NamedQuery(name = "Solicitudes.findByAcceptAndTorneoIndividualId", query = "SELECT s FROM Solicitudes s WHERE s.idTorneoIndividual.idTorneoIndividual = :idTorneoIndividual AND s.estado = 'Acceptada'"),
    @NamedQuery(name = "Solicitudes.findBySolicitadasAndTorneoIndividualId", query = "SELECT s FROM Solicitudes s WHERE s.idTorneoIndividual.idTorneoIndividual = :idTorneoIndividual AND s.estado = 'Solicitada'"),
    @NamedQuery(name = "Solicitudes.findByRechazadasAndTorneoIndividualId", query = "SELECT s FROM Solicitudes s WHERE s.idTorneoIndividual.idTorneoIndividual = :idTorneoIndividual AND s.estado = 'Rechazada'")


})
public class Solicitudes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_solicitudes")
    private Integer idSolicitudes;
    @Size(max = 69)
    @Column(name = "nombre_solicitudes")
    private String nombreSolicitudes;
    @Size(max = 200)
    @Column(name = "foto_url_solicitudes")
    private String fotoUrlSolicitudes;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Size(max = 200)
    @Column(name = "datos")
    private String datos;
    @Size(max = 128)
    @Column(name = "datos_publicos")
    private String datosPublicos;
    @Size(max = 69)
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "idSolicitud1")
    @JsonbTransient
    private Collection<CombatesIndividuales> combatesIndividualesCollection;
    @OneToMany(mappedBy = "idSolicitud2")
    @JsonbTransient
    private Collection<CombatesIndividuales> combatesIndividualesCollection1;
    @OneToMany(mappedBy = "idSolicitudGanador")
    @JsonbTransient
    private Collection<CombatesIndividuales> combatesIndividualesCollection2;
    @JoinColumn(name = "id_torneo_individual", referencedColumnName = "id_torneo_individual")
    @ManyToOne
    private TorneosIndividuales idTorneoIndividual;

    public Solicitudes() {
    }

    public Solicitudes(Integer idSolicitudes) {
        this.idSolicitudes = idSolicitudes;
    }

    public Integer getIdSolicitudes() {
        return idSolicitudes;
    }

    public void setIdSolicitudes(Integer idSolicitudes) {
        this.idSolicitudes = idSolicitudes;
    }

    public String getNombreSolicitudes() {
        return nombreSolicitudes;
    }

    public void setNombreSolicitudes(String nombreSolicitudes) {
        this.nombreSolicitudes = nombreSolicitudes;
    }

    public String getFotoUrlSolicitudes() {
        return fotoUrlSolicitudes;
    }

    public void setFotoUrlSolicitudes(String fotoUrlSolicitudes) {
        this.fotoUrlSolicitudes = fotoUrlSolicitudes;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getDatosPublicos() {
        return datosPublicos;
    }

    public void setDatosPublicos(String datosPublicos) {
        this.datosPublicos = datosPublicos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<CombatesIndividuales> getCombatesIndividualesCollection() {
        return combatesIndividualesCollection;
    }

    public void setCombatesIndividualesCollection(Collection<CombatesIndividuales> combatesIndividualesCollection) {
        this.combatesIndividualesCollection = combatesIndividualesCollection;
    }

    @XmlTransient
    public Collection<CombatesIndividuales> getCombatesIndividualesCollection1() {
        return combatesIndividualesCollection1;
    }

    public void setCombatesIndividualesCollection1(Collection<CombatesIndividuales> combatesIndividualesCollection1) {
        this.combatesIndividualesCollection1 = combatesIndividualesCollection1;
    }

    @XmlTransient
    public Collection<CombatesIndividuales> getCombatesIndividualesCollection2() {
        return combatesIndividualesCollection2;
    }

    public void setCombatesIndividualesCollection2(Collection<CombatesIndividuales> combatesIndividualesCollection2) {
        this.combatesIndividualesCollection2 = combatesIndividualesCollection2;
    }

    public TorneosIndividuales getIdTorneoIndividual() {
        return idTorneoIndividual;
    }

    public void setIdTorneoIndividual(TorneosIndividuales idTorneoIndividual) {
        this.idTorneoIndividual = idTorneoIndividual;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitudes != null ? idSolicitudes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitudes)) {
            return false;
        }
        Solicitudes other = (Solicitudes) object;
        if ((this.idSolicitudes == null && other.idSolicitudes != null) || (this.idSolicitudes != null && !this.idSolicitudes.equals(other.idSolicitudes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "suprem_tournaments.Solicitudes[ idSolicitudes=" + idSolicitudes + " ]";
    }

}
