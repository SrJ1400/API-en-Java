/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package suprem_tournaments;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creado el 
 * Terminado el
 * @author Sr.J
 */
@Entity
@Table(name = "solicitudes_continuas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitudesContinuas.findAll", query = "SELECT s FROM SolicitudesContinuas s"),
    @NamedQuery(name = "SolicitudesContinuas.findByIdSolicitudesContinuas", query = "SELECT s FROM SolicitudesContinuas s WHERE s.idSolicitudesContinuas = :idSolicitudesContinuas"),
    @NamedQuery(name = "SolicitudesContinuas.findByNombreSolicitudesContinuas", query = "SELECT s FROM SolicitudesContinuas s WHERE s.nombreSolicitudesContinuas = :nombreSolicitudesContinuas"),
    @NamedQuery(name = "SolicitudesContinuas.findByFotoUrlSolicitudesContinuas", query = "SELECT s FROM SolicitudesContinuas s WHERE s.fotoUrlSolicitudesContinuas = :fotoUrlSolicitudesContinuas"),
    @NamedQuery(name = "SolicitudesContinuas.findByFechaNacimiento", query = "SELECT s FROM SolicitudesContinuas s WHERE s.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "SolicitudesContinuas.findByDatos", query = "SELECT s FROM SolicitudesContinuas s WHERE s.datos = :datos"),
    @NamedQuery(name = "SolicitudesContinuas.findByDatosPublicos", query = "SELECT s FROM SolicitudesContinuas s WHERE s.datosPublicos = :datosPublicos")})
public class SolicitudesContinuas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_solicitudes_continuas")
    private Integer idSolicitudesContinuas;
    @Size(max = 69)
    @Column(name = "nombre_solicitudes_continuas")
    private String nombreSolicitudesContinuas;
    @Size(max = 200)
    @Column(name = "foto_url_solicitudes_continuas")
    private String fotoUrlSolicitudesContinuas;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Size(max = 200)
    @Column(name = "datos")
    private String datos;
    @Size(max = 128)
    @Column(name = "datos_publicos")
    private String datosPublicos;

    public SolicitudesContinuas() {
    }

    public SolicitudesContinuas(Integer idSolicitudesContinuas) {
        this.idSolicitudesContinuas = idSolicitudesContinuas;
    }

    public Integer getIdSolicitudesContinuas() {
        return idSolicitudesContinuas;
    }

    public void setIdSolicitudesContinuas(Integer idSolicitudesContinuas) {
        this.idSolicitudesContinuas = idSolicitudesContinuas;
    }

    public String getNombreSolicitudesContinuas() {
        return nombreSolicitudesContinuas;
    }

    public void setNombreSolicitudesContinuas(String nombreSolicitudesContinuas) {
        this.nombreSolicitudesContinuas = nombreSolicitudesContinuas;
    }

    public String getFotoUrlSolicitudesContinuas() {
        return fotoUrlSolicitudesContinuas;
    }

    public void setFotoUrlSolicitudesContinuas(String fotoUrlSolicitudesContinuas) {
        this.fotoUrlSolicitudesContinuas = fotoUrlSolicitudesContinuas;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitudesContinuas != null ? idSolicitudesContinuas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudesContinuas)) {
            return false;
        }
        SolicitudesContinuas other = (SolicitudesContinuas) object;
        if ((this.idSolicitudesContinuas == null && other.idSolicitudesContinuas != null) || (this.idSolicitudesContinuas != null && !this.idSolicitudesContinuas.equals(other.idSolicitudesContinuas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "suprem_tournaments.SolicitudesContinuas[ idSolicitudesContinuas=" + idSolicitudesContinuas + " ]";
    }

}
