/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package suprem_tournaments;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creado el 
 * Terminado el
 * @author Sr.J
 */
@Entity
@Table(name = "combates_individuales")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "CombatesIndividuales.findAll", query = "SELECT c FROM CombatesIndividuales c"),
    @NamedQuery(name = "CombatesIndividuales.findByIdCombatesIndividuales", query = "SELECT c FROM CombatesIndividuales c WHERE c.idCombatesIndividuales = :idCombatesIndividuales"),
    
    //Querys personalizadas
    @NamedQuery(name = "CombatesIndividuales.findCombatesIndividualesGanadosByIdSolicitundAndIdTorneo", query = "SELECT c FROM CombatesIndividuales c WHERE c.idSolicitudGanador.idSolicitudes = :idSolicitud AND c.idTorneoIndividual.idTorneoIndividual = :idTorneo"),
    @NamedQuery(name = "CombatesIndividuales.findCombatesIndividualesPerdidosByIdSolicitundAndIdTorneo", query = "SELECT c FROM CombatesIndividuales c WHERE c.idSolicitudGanador.idSolicitudes != :idSolicitud AND c.idTorneoIndividual.idTorneoIndividual = :idTorneo"),
    @NamedQuery(name = "CombatesIndividuales.findByTorneoIndividualId", query = "SELECT c FROM CombatesIndividuales c WHERE c.idTorneoIndividual.idTorneoIndividual = :idTorneoIndividual")})
public class CombatesIndividuales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_combates_individuales")
    private Integer idCombatesIndividuales;
    @JoinColumn(name = "id_solicitud1", referencedColumnName = "id_solicitudes")
    @ManyToOne
    private Solicitudes idSolicitud1;
    @JoinColumn(name = "id_solicitud2", referencedColumnName = "id_solicitudes")
    @ManyToOne
    private Solicitudes idSolicitud2;
    @JoinColumn(name = "id_solicitud_ganador", referencedColumnName = "id_solicitudes")
    @ManyToOne
    private Solicitudes idSolicitudGanador;
    @JoinColumn(name = "id_torneo_individual", referencedColumnName = "id_torneo_individual")
    @ManyToOne
    private TorneosIndividuales idTorneoIndividual;

    public CombatesIndividuales() {
    }

    public CombatesIndividuales(Integer idCombatesIndividuales) {
        this.idCombatesIndividuales = idCombatesIndividuales;
    }

    public Integer getIdCombatesIndividuales() {
        return idCombatesIndividuales;
    }

    public void setIdCombatesIndividuales(Integer idCombatesIndividuales) {
        this.idCombatesIndividuales = idCombatesIndividuales;
    }

    public Solicitudes getIdSolicitud1() {
        return idSolicitud1;
    }

    public void setIdSolicitud1(Solicitudes idSolicitud1) {
        this.idSolicitud1 = idSolicitud1;
    }

    public Solicitudes getIdSolicitud2() {
        return idSolicitud2;
    }

    public void setIdSolicitud2(Solicitudes idSolicitud2) {
        this.idSolicitud2 = idSolicitud2;
    }

    public Solicitudes getIdSolicitudGanador() {
        return idSolicitudGanador;
    }

    public void setIdSolicitudGanador(Solicitudes idSolicitudGanador) {
        this.idSolicitudGanador = idSolicitudGanador;
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
        hash += (idCombatesIndividuales != null ? idCombatesIndividuales.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CombatesIndividuales)) {
            return false;
        }
        CombatesIndividuales other = (CombatesIndividuales) object;
        if ((this.idCombatesIndividuales == null && other.idCombatesIndividuales != null) || (this.idCombatesIndividuales != null && !this.idCombatesIndividuales.equals(other.idCombatesIndividuales))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "suprem_tournaments.CombatesIndividuales[ idCombatesIndividuales=" + idCombatesIndividuales + " ]";
    }

}
