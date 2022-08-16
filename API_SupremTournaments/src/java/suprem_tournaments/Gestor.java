/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package suprem_tournaments;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Creado el 
 * Terminado el
 * @author Sr.J
 */
@Entity
@Table(name = "gestor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gestor.findAll", query = "SELECT g FROM Gestor g"),
    @NamedQuery(name = "Gestor.findByIdGestor", query = "SELECT g FROM Gestor g WHERE g.idGestor = :idGestor"),
    @NamedQuery(name = "Gestor.findByNombre", query = "SELECT g FROM Gestor g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "Gestor.findByEmail", query = "SELECT g FROM Gestor g WHERE g.email = :email"),
    @NamedQuery(name = "Gestor.findByContrasenya", query = "SELECT g FROM Gestor g WHERE g.contrasenya = :contrasenya")})
public class Gestor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gestor")
    private Integer idGestor;
    @Size(max = 69)
    @Column(name = "nombre")
    private String nombre;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 124)
    @Column(name = "email")
    private String email;
    @Size(max = 124)
    @Column(name = "contrasenya")
    private String contrasenya;
    @OneToMany(mappedBy = "idGestor")
    private Collection<TorneosIndividuales> torneosIndividualesCollection;

    public Gestor() {
    }

    public Gestor(Integer idGestor) {
        this.idGestor = idGestor;
    }

    public Integer getIdGestor() {
        return idGestor;
    }

    public void setIdGestor(Integer idGestor) {
        this.idGestor = idGestor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    @XmlTransient
    public Collection<TorneosIndividuales> getTorneosIndividualesCollection() {
        return torneosIndividualesCollection;
    }

    public void setTorneosIndividualesCollection(Collection<TorneosIndividuales> torneosIndividualesCollection) {
        this.torneosIndividualesCollection = torneosIndividualesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGestor != null ? idGestor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gestor)) {
            return false;
        }
        Gestor other = (Gestor) object;
        if ((this.idGestor == null && other.idGestor != null) || (this.idGestor != null && !this.idGestor.equals(other.idGestor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "suprem_tournaments.Gestor[ idGestor=" + idGestor + " ]";
    }

}
