/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package suprem_tournaments;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import suprem_tournaments.exceptions.NonexistentEntityException;

/**
 * Creado el 
 * Terminado el
 * @author Sr.J
 */
public class CombatesIndividualesJpaController implements Serializable {

    public CombatesIndividualesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CombatesIndividuales combatesIndividuales) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitudes idSolicitud1 = combatesIndividuales.getIdSolicitud1();
            if (idSolicitud1 != null) {
                idSolicitud1 = em.getReference(idSolicitud1.getClass(), idSolicitud1.getIdSolicitudes());
                combatesIndividuales.setIdSolicitud1(idSolicitud1);
            }
            Solicitudes idSolicitud2 = combatesIndividuales.getIdSolicitud2();
            if (idSolicitud2 != null) {
                idSolicitud2 = em.getReference(idSolicitud2.getClass(), idSolicitud2.getIdSolicitudes());
                combatesIndividuales.setIdSolicitud2(idSolicitud2);
            }
            Solicitudes idSolicitudGanador = combatesIndividuales.getIdSolicitudGanador();
            if (idSolicitudGanador != null) {
                idSolicitudGanador = em.getReference(idSolicitudGanador.getClass(), idSolicitudGanador.getIdSolicitudes());
                combatesIndividuales.setIdSolicitudGanador(idSolicitudGanador);
            }
            TorneosIndividuales idTorneoIndividual = combatesIndividuales.getIdTorneoIndividual();
            if (idTorneoIndividual != null) {
                idTorneoIndividual = em.getReference(idTorneoIndividual.getClass(), idTorneoIndividual.getIdTorneoIndividual());
                combatesIndividuales.setIdTorneoIndividual(idTorneoIndividual);
            }
            em.persist(combatesIndividuales);
            if (idSolicitud1 != null) {
                idSolicitud1.getCombatesIndividualesCollection().add(combatesIndividuales);
                idSolicitud1 = em.merge(idSolicitud1);
            }
            if (idSolicitud2 != null) {
                idSolicitud2.getCombatesIndividualesCollection().add(combatesIndividuales);
                idSolicitud2 = em.merge(idSolicitud2);
            }
            if (idSolicitudGanador != null) {
                idSolicitudGanador.getCombatesIndividualesCollection().add(combatesIndividuales);
                idSolicitudGanador = em.merge(idSolicitudGanador);
            }
            if (idTorneoIndividual != null) {
                idTorneoIndividual.getCombatesIndividualesCollection().add(combatesIndividuales);
                idTorneoIndividual = em.merge(idTorneoIndividual);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CombatesIndividuales combatesIndividuales) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CombatesIndividuales persistentCombatesIndividuales = em.find(CombatesIndividuales.class, combatesIndividuales.getIdCombatesIndividuales());
            Solicitudes idSolicitud1Old = persistentCombatesIndividuales.getIdSolicitud1();
            Solicitudes idSolicitud1New = combatesIndividuales.getIdSolicitud1();
            Solicitudes idSolicitud2Old = persistentCombatesIndividuales.getIdSolicitud2();
            Solicitudes idSolicitud2New = combatesIndividuales.getIdSolicitud2();
            Solicitudes idSolicitudGanadorOld = persistentCombatesIndividuales.getIdSolicitudGanador();
            Solicitudes idSolicitudGanadorNew = combatesIndividuales.getIdSolicitudGanador();
            TorneosIndividuales idTorneoIndividualOld = persistentCombatesIndividuales.getIdTorneoIndividual();
            TorneosIndividuales idTorneoIndividualNew = combatesIndividuales.getIdTorneoIndividual();
            if (idSolicitud1New != null) {
                idSolicitud1New = em.getReference(idSolicitud1New.getClass(), idSolicitud1New.getIdSolicitudes());
                combatesIndividuales.setIdSolicitud1(idSolicitud1New);
            }
            if (idSolicitud2New != null) {
                idSolicitud2New = em.getReference(idSolicitud2New.getClass(), idSolicitud2New.getIdSolicitudes());
                combatesIndividuales.setIdSolicitud2(idSolicitud2New);
            }
            if (idSolicitudGanadorNew != null) {
                idSolicitudGanadorNew = em.getReference(idSolicitudGanadorNew.getClass(), idSolicitudGanadorNew.getIdSolicitudes());
                combatesIndividuales.setIdSolicitudGanador(idSolicitudGanadorNew);
            }
            if (idTorneoIndividualNew != null) {
                idTorneoIndividualNew = em.getReference(idTorneoIndividualNew.getClass(), idTorneoIndividualNew.getIdTorneoIndividual());
                combatesIndividuales.setIdTorneoIndividual(idTorneoIndividualNew);
            }
            combatesIndividuales = em.merge(combatesIndividuales);
            if (idSolicitud1Old != null && !idSolicitud1Old.equals(idSolicitud1New)) {
                idSolicitud1Old.getCombatesIndividualesCollection().remove(combatesIndividuales);
                idSolicitud1Old = em.merge(idSolicitud1Old);
            }
            if (idSolicitud1New != null && !idSolicitud1New.equals(idSolicitud1Old)) {
                idSolicitud1New.getCombatesIndividualesCollection().add(combatesIndividuales);
                idSolicitud1New = em.merge(idSolicitud1New);
            }
            if (idSolicitud2Old != null && !idSolicitud2Old.equals(idSolicitud2New)) {
                idSolicitud2Old.getCombatesIndividualesCollection().remove(combatesIndividuales);
                idSolicitud2Old = em.merge(idSolicitud2Old);
            }
            if (idSolicitud2New != null && !idSolicitud2New.equals(idSolicitud2Old)) {
                idSolicitud2New.getCombatesIndividualesCollection().add(combatesIndividuales);
                idSolicitud2New = em.merge(idSolicitud2New);
            }
            if (idSolicitudGanadorOld != null && !idSolicitudGanadorOld.equals(idSolicitudGanadorNew)) {
                idSolicitudGanadorOld.getCombatesIndividualesCollection().remove(combatesIndividuales);
                idSolicitudGanadorOld = em.merge(idSolicitudGanadorOld);
            }
            if (idSolicitudGanadorNew != null && !idSolicitudGanadorNew.equals(idSolicitudGanadorOld)) {
                idSolicitudGanadorNew.getCombatesIndividualesCollection().add(combatesIndividuales);
                idSolicitudGanadorNew = em.merge(idSolicitudGanadorNew);
            }
            if (idTorneoIndividualOld != null && !idTorneoIndividualOld.equals(idTorneoIndividualNew)) {
                idTorneoIndividualOld.getCombatesIndividualesCollection().remove(combatesIndividuales);
                idTorneoIndividualOld = em.merge(idTorneoIndividualOld);
            }
            if (idTorneoIndividualNew != null && !idTorneoIndividualNew.equals(idTorneoIndividualOld)) {
                idTorneoIndividualNew.getCombatesIndividualesCollection().add(combatesIndividuales);
                idTorneoIndividualNew = em.merge(idTorneoIndividualNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = combatesIndividuales.getIdCombatesIndividuales();
                if (findCombatesIndividuales(id) == null) {
                    throw new NonexistentEntityException("The combatesIndividuales with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CombatesIndividuales combatesIndividuales;
            try {
                combatesIndividuales = em.getReference(CombatesIndividuales.class, id);
                combatesIndividuales.getIdCombatesIndividuales();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The combatesIndividuales with id " + id + " no longer exists.", enfe);
            }
            Solicitudes idSolicitud1 = combatesIndividuales.getIdSolicitud1();
            if (idSolicitud1 != null) {
                idSolicitud1.getCombatesIndividualesCollection().remove(combatesIndividuales);
                idSolicitud1 = em.merge(idSolicitud1);
            }
            Solicitudes idSolicitud2 = combatesIndividuales.getIdSolicitud2();
            if (idSolicitud2 != null) {
                idSolicitud2.getCombatesIndividualesCollection().remove(combatesIndividuales);
                idSolicitud2 = em.merge(idSolicitud2);
            }
            Solicitudes idSolicitudGanador = combatesIndividuales.getIdSolicitudGanador();
            if (idSolicitudGanador != null) {
                idSolicitudGanador.getCombatesIndividualesCollection().remove(combatesIndividuales);
                idSolicitudGanador = em.merge(idSolicitudGanador);
            }
            TorneosIndividuales idTorneoIndividual = combatesIndividuales.getIdTorneoIndividual();
            if (idTorneoIndividual != null) {
                idTorneoIndividual.getCombatesIndividualesCollection().remove(combatesIndividuales);
                idTorneoIndividual = em.merge(idTorneoIndividual);
            }
            em.remove(combatesIndividuales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CombatesIndividuales> findCombatesIndividualesEntities() {
        return findCombatesIndividualesEntities(true, -1, -1);
    }

    public List<CombatesIndividuales> findCombatesIndividualesEntities(int maxResults, int firstResult) {
        return findCombatesIndividualesEntities(false, maxResults, firstResult);
    }

    private List<CombatesIndividuales> findCombatesIndividualesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CombatesIndividuales.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CombatesIndividuales findCombatesIndividuales(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CombatesIndividuales.class, id);
        } finally {
            em.close();
        }
    }

    public int getCombatesIndividualesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CombatesIndividuales> rt = cq.from(CombatesIndividuales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
