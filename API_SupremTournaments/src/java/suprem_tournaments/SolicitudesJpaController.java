/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package suprem_tournaments;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import suprem_tournaments.exceptions.NonexistentEntityException;

/**
 * Creado el 
 * Terminado el
 * @author Sr.J
 */
public class SolicitudesJpaController implements Serializable {

    public SolicitudesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Solicitudes solicitudes) {
        if (solicitudes.getCombatesIndividualesCollection() == null) {
            solicitudes.setCombatesIndividualesCollection(new ArrayList<CombatesIndividuales>());
        }
        if (solicitudes.getCombatesIndividualesCollection1() == null) {
            solicitudes.setCombatesIndividualesCollection1(new ArrayList<CombatesIndividuales>());
        }
        if (solicitudes.getCombatesIndividualesCollection2() == null) {
            solicitudes.setCombatesIndividualesCollection2(new ArrayList<CombatesIndividuales>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TorneosIndividuales idTorneoIndividual = solicitudes.getIdTorneoIndividual();
            if (idTorneoIndividual != null) {
                idTorneoIndividual = em.getReference(idTorneoIndividual.getClass(), idTorneoIndividual.getIdTorneoIndividual());
                solicitudes.setIdTorneoIndividual(idTorneoIndividual);
            }
            Collection<CombatesIndividuales> attachedCombatesIndividualesCollection = new ArrayList<CombatesIndividuales>();
            for (CombatesIndividuales combatesIndividualesCollectionCombatesIndividualesToAttach : solicitudes.getCombatesIndividualesCollection()) {
                combatesIndividualesCollectionCombatesIndividualesToAttach = em.getReference(combatesIndividualesCollectionCombatesIndividualesToAttach.getClass(), combatesIndividualesCollectionCombatesIndividualesToAttach.getIdCombatesIndividuales());
                attachedCombatesIndividualesCollection.add(combatesIndividualesCollectionCombatesIndividualesToAttach);
            }
            solicitudes.setCombatesIndividualesCollection(attachedCombatesIndividualesCollection);
            Collection<CombatesIndividuales> attachedCombatesIndividualesCollection1 = new ArrayList<CombatesIndividuales>();
            for (CombatesIndividuales combatesIndividualesCollection1CombatesIndividualesToAttach : solicitudes.getCombatesIndividualesCollection1()) {
                combatesIndividualesCollection1CombatesIndividualesToAttach = em.getReference(combatesIndividualesCollection1CombatesIndividualesToAttach.getClass(), combatesIndividualesCollection1CombatesIndividualesToAttach.getIdCombatesIndividuales());
                attachedCombatesIndividualesCollection1.add(combatesIndividualesCollection1CombatesIndividualesToAttach);
            }
            solicitudes.setCombatesIndividualesCollection1(attachedCombatesIndividualesCollection1);
            Collection<CombatesIndividuales> attachedCombatesIndividualesCollection2 = new ArrayList<CombatesIndividuales>();
            for (CombatesIndividuales combatesIndividualesCollection2CombatesIndividualesToAttach : solicitudes.getCombatesIndividualesCollection2()) {
                combatesIndividualesCollection2CombatesIndividualesToAttach = em.getReference(combatesIndividualesCollection2CombatesIndividualesToAttach.getClass(), combatesIndividualesCollection2CombatesIndividualesToAttach.getIdCombatesIndividuales());
                attachedCombatesIndividualesCollection2.add(combatesIndividualesCollection2CombatesIndividualesToAttach);
            }
            solicitudes.setCombatesIndividualesCollection2(attachedCombatesIndividualesCollection2);
            em.persist(solicitudes);
            if (idTorneoIndividual != null) {
                idTorneoIndividual.getSolicitudesCollection().add(solicitudes);
                idTorneoIndividual = em.merge(idTorneoIndividual);
            }
            for (CombatesIndividuales combatesIndividualesCollectionCombatesIndividuales : solicitudes.getCombatesIndividualesCollection()) {
                Solicitudes oldIdSolicitud1OfCombatesIndividualesCollectionCombatesIndividuales = combatesIndividualesCollectionCombatesIndividuales.getIdSolicitud1();
                combatesIndividualesCollectionCombatesIndividuales.setIdSolicitud1(solicitudes);
                combatesIndividualesCollectionCombatesIndividuales = em.merge(combatesIndividualesCollectionCombatesIndividuales);
                if (oldIdSolicitud1OfCombatesIndividualesCollectionCombatesIndividuales != null) {
                    oldIdSolicitud1OfCombatesIndividualesCollectionCombatesIndividuales.getCombatesIndividualesCollection().remove(combatesIndividualesCollectionCombatesIndividuales);
                    oldIdSolicitud1OfCombatesIndividualesCollectionCombatesIndividuales = em.merge(oldIdSolicitud1OfCombatesIndividualesCollectionCombatesIndividuales);
                }
            }
            for (CombatesIndividuales combatesIndividualesCollection1CombatesIndividuales : solicitudes.getCombatesIndividualesCollection1()) {
                Solicitudes oldIdSolicitud2OfCombatesIndividualesCollection1CombatesIndividuales = combatesIndividualesCollection1CombatesIndividuales.getIdSolicitud2();
                combatesIndividualesCollection1CombatesIndividuales.setIdSolicitud2(solicitudes);
                combatesIndividualesCollection1CombatesIndividuales = em.merge(combatesIndividualesCollection1CombatesIndividuales);
                if (oldIdSolicitud2OfCombatesIndividualesCollection1CombatesIndividuales != null) {
                    oldIdSolicitud2OfCombatesIndividualesCollection1CombatesIndividuales.getCombatesIndividualesCollection1().remove(combatesIndividualesCollection1CombatesIndividuales);
                    oldIdSolicitud2OfCombatesIndividualesCollection1CombatesIndividuales = em.merge(oldIdSolicitud2OfCombatesIndividualesCollection1CombatesIndividuales);
                }
            }
            for (CombatesIndividuales combatesIndividualesCollection2CombatesIndividuales : solicitudes.getCombatesIndividualesCollection2()) {
                Solicitudes oldIdSolicitudGanadorOfCombatesIndividualesCollection2CombatesIndividuales = combatesIndividualesCollection2CombatesIndividuales.getIdSolicitudGanador();
                combatesIndividualesCollection2CombatesIndividuales.setIdSolicitudGanador(solicitudes);
                combatesIndividualesCollection2CombatesIndividuales = em.merge(combatesIndividualesCollection2CombatesIndividuales);
                if (oldIdSolicitudGanadorOfCombatesIndividualesCollection2CombatesIndividuales != null) {
                    oldIdSolicitudGanadorOfCombatesIndividualesCollection2CombatesIndividuales.getCombatesIndividualesCollection2().remove(combatesIndividualesCollection2CombatesIndividuales);
                    oldIdSolicitudGanadorOfCombatesIndividualesCollection2CombatesIndividuales = em.merge(oldIdSolicitudGanadorOfCombatesIndividualesCollection2CombatesIndividuales);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Solicitudes solicitudes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitudes persistentSolicitudes = em.find(Solicitudes.class, solicitudes.getIdSolicitudes());
            TorneosIndividuales idTorneoIndividualOld = persistentSolicitudes.getIdTorneoIndividual();
            TorneosIndividuales idTorneoIndividualNew = solicitudes.getIdTorneoIndividual();
            Collection<CombatesIndividuales> combatesIndividualesCollectionOld = persistentSolicitudes.getCombatesIndividualesCollection();
            Collection<CombatesIndividuales> combatesIndividualesCollectionNew = solicitudes.getCombatesIndividualesCollection();
            Collection<CombatesIndividuales> combatesIndividualesCollection1Old = persistentSolicitudes.getCombatesIndividualesCollection1();
            Collection<CombatesIndividuales> combatesIndividualesCollection1New = solicitudes.getCombatesIndividualesCollection1();
            Collection<CombatesIndividuales> combatesIndividualesCollection2Old = persistentSolicitudes.getCombatesIndividualesCollection2();
            Collection<CombatesIndividuales> combatesIndividualesCollection2New = solicitudes.getCombatesIndividualesCollection2();
            if (idTorneoIndividualNew != null) {
                idTorneoIndividualNew = em.getReference(idTorneoIndividualNew.getClass(), idTorneoIndividualNew.getIdTorneoIndividual());
                solicitudes.setIdTorneoIndividual(idTorneoIndividualNew);
            }
            Collection<CombatesIndividuales> attachedCombatesIndividualesCollectionNew = new ArrayList<CombatesIndividuales>();
            for (CombatesIndividuales combatesIndividualesCollectionNewCombatesIndividualesToAttach : combatesIndividualesCollectionNew) {
                combatesIndividualesCollectionNewCombatesIndividualesToAttach = em.getReference(combatesIndividualesCollectionNewCombatesIndividualesToAttach.getClass(), combatesIndividualesCollectionNewCombatesIndividualesToAttach.getIdCombatesIndividuales());
                attachedCombatesIndividualesCollectionNew.add(combatesIndividualesCollectionNewCombatesIndividualesToAttach);
            }
            combatesIndividualesCollectionNew = attachedCombatesIndividualesCollectionNew;
            solicitudes.setCombatesIndividualesCollection(combatesIndividualesCollectionNew);
            Collection<CombatesIndividuales> attachedCombatesIndividualesCollection1New = new ArrayList<CombatesIndividuales>();
            for (CombatesIndividuales combatesIndividualesCollection1NewCombatesIndividualesToAttach : combatesIndividualesCollection1New) {
                combatesIndividualesCollection1NewCombatesIndividualesToAttach = em.getReference(combatesIndividualesCollection1NewCombatesIndividualesToAttach.getClass(), combatesIndividualesCollection1NewCombatesIndividualesToAttach.getIdCombatesIndividuales());
                attachedCombatesIndividualesCollection1New.add(combatesIndividualesCollection1NewCombatesIndividualesToAttach);
            }
            combatesIndividualesCollection1New = attachedCombatesIndividualesCollection1New;
            solicitudes.setCombatesIndividualesCollection1(combatesIndividualesCollection1New);
            Collection<CombatesIndividuales> attachedCombatesIndividualesCollection2New = new ArrayList<CombatesIndividuales>();
            for (CombatesIndividuales combatesIndividualesCollection2NewCombatesIndividualesToAttach : combatesIndividualesCollection2New) {
                combatesIndividualesCollection2NewCombatesIndividualesToAttach = em.getReference(combatesIndividualesCollection2NewCombatesIndividualesToAttach.getClass(), combatesIndividualesCollection2NewCombatesIndividualesToAttach.getIdCombatesIndividuales());
                attachedCombatesIndividualesCollection2New.add(combatesIndividualesCollection2NewCombatesIndividualesToAttach);
            }
            combatesIndividualesCollection2New = attachedCombatesIndividualesCollection2New;
            solicitudes.setCombatesIndividualesCollection2(combatesIndividualesCollection2New);
            solicitudes = em.merge(solicitudes);
            if (idTorneoIndividualOld != null && !idTorneoIndividualOld.equals(idTorneoIndividualNew)) {
                idTorneoIndividualOld.getSolicitudesCollection().remove(solicitudes);
                idTorneoIndividualOld = em.merge(idTorneoIndividualOld);
            }
            if (idTorneoIndividualNew != null && !idTorneoIndividualNew.equals(idTorneoIndividualOld)) {
                idTorneoIndividualNew.getSolicitudesCollection().add(solicitudes);
                idTorneoIndividualNew = em.merge(idTorneoIndividualNew);
            }
            for (CombatesIndividuales combatesIndividualesCollectionOldCombatesIndividuales : combatesIndividualesCollectionOld) {
                if (!combatesIndividualesCollectionNew.contains(combatesIndividualesCollectionOldCombatesIndividuales)) {
                    combatesIndividualesCollectionOldCombatesIndividuales.setIdSolicitud1(null);
                    combatesIndividualesCollectionOldCombatesIndividuales = em.merge(combatesIndividualesCollectionOldCombatesIndividuales);
                }
            }
            for (CombatesIndividuales combatesIndividualesCollectionNewCombatesIndividuales : combatesIndividualesCollectionNew) {
                if (!combatesIndividualesCollectionOld.contains(combatesIndividualesCollectionNewCombatesIndividuales)) {
                    Solicitudes oldIdSolicitud1OfCombatesIndividualesCollectionNewCombatesIndividuales = combatesIndividualesCollectionNewCombatesIndividuales.getIdSolicitud1();
                    combatesIndividualesCollectionNewCombatesIndividuales.setIdSolicitud1(solicitudes);
                    combatesIndividualesCollectionNewCombatesIndividuales = em.merge(combatesIndividualesCollectionNewCombatesIndividuales);
                    if (oldIdSolicitud1OfCombatesIndividualesCollectionNewCombatesIndividuales != null && !oldIdSolicitud1OfCombatesIndividualesCollectionNewCombatesIndividuales.equals(solicitudes)) {
                        oldIdSolicitud1OfCombatesIndividualesCollectionNewCombatesIndividuales.getCombatesIndividualesCollection().remove(combatesIndividualesCollectionNewCombatesIndividuales);
                        oldIdSolicitud1OfCombatesIndividualesCollectionNewCombatesIndividuales = em.merge(oldIdSolicitud1OfCombatesIndividualesCollectionNewCombatesIndividuales);
                    }
                }
            }
            for (CombatesIndividuales combatesIndividualesCollection1OldCombatesIndividuales : combatesIndividualesCollection1Old) {
                if (!combatesIndividualesCollection1New.contains(combatesIndividualesCollection1OldCombatesIndividuales)) {
                    combatesIndividualesCollection1OldCombatesIndividuales.setIdSolicitud2(null);
                    combatesIndividualesCollection1OldCombatesIndividuales = em.merge(combatesIndividualesCollection1OldCombatesIndividuales);
                }
            }
            for (CombatesIndividuales combatesIndividualesCollection1NewCombatesIndividuales : combatesIndividualesCollection1New) {
                if (!combatesIndividualesCollection1Old.contains(combatesIndividualesCollection1NewCombatesIndividuales)) {
                    Solicitudes oldIdSolicitud2OfCombatesIndividualesCollection1NewCombatesIndividuales = combatesIndividualesCollection1NewCombatesIndividuales.getIdSolicitud2();
                    combatesIndividualesCollection1NewCombatesIndividuales.setIdSolicitud2(solicitudes);
                    combatesIndividualesCollection1NewCombatesIndividuales = em.merge(combatesIndividualesCollection1NewCombatesIndividuales);
                    if (oldIdSolicitud2OfCombatesIndividualesCollection1NewCombatesIndividuales != null && !oldIdSolicitud2OfCombatesIndividualesCollection1NewCombatesIndividuales.equals(solicitudes)) {
                        oldIdSolicitud2OfCombatesIndividualesCollection1NewCombatesIndividuales.getCombatesIndividualesCollection1().remove(combatesIndividualesCollection1NewCombatesIndividuales);
                        oldIdSolicitud2OfCombatesIndividualesCollection1NewCombatesIndividuales = em.merge(oldIdSolicitud2OfCombatesIndividualesCollection1NewCombatesIndividuales);
                    }
                }
            }
            for (CombatesIndividuales combatesIndividualesCollection2OldCombatesIndividuales : combatesIndividualesCollection2Old) {
                if (!combatesIndividualesCollection2New.contains(combatesIndividualesCollection2OldCombatesIndividuales)) {
                    combatesIndividualesCollection2OldCombatesIndividuales.setIdSolicitudGanador(null);
                    combatesIndividualesCollection2OldCombatesIndividuales = em.merge(combatesIndividualesCollection2OldCombatesIndividuales);
                }
            }
            for (CombatesIndividuales combatesIndividualesCollection2NewCombatesIndividuales : combatesIndividualesCollection2New) {
                if (!combatesIndividualesCollection2Old.contains(combatesIndividualesCollection2NewCombatesIndividuales)) {
                    Solicitudes oldIdSolicitudGanadorOfCombatesIndividualesCollection2NewCombatesIndividuales = combatesIndividualesCollection2NewCombatesIndividuales.getIdSolicitudGanador();
                    combatesIndividualesCollection2NewCombatesIndividuales.setIdSolicitudGanador(solicitudes);
                    combatesIndividualesCollection2NewCombatesIndividuales = em.merge(combatesIndividualesCollection2NewCombatesIndividuales);
                    if (oldIdSolicitudGanadorOfCombatesIndividualesCollection2NewCombatesIndividuales != null && !oldIdSolicitudGanadorOfCombatesIndividualesCollection2NewCombatesIndividuales.equals(solicitudes)) {
                        oldIdSolicitudGanadorOfCombatesIndividualesCollection2NewCombatesIndividuales.getCombatesIndividualesCollection2().remove(combatesIndividualesCollection2NewCombatesIndividuales);
                        oldIdSolicitudGanadorOfCombatesIndividualesCollection2NewCombatesIndividuales = em.merge(oldIdSolicitudGanadorOfCombatesIndividualesCollection2NewCombatesIndividuales);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = solicitudes.getIdSolicitudes();
                if (findSolicitudes(id) == null) {
                    throw new NonexistentEntityException("The solicitudes with id " + id + " no longer exists.");
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
            Solicitudes solicitudes;
            try {
                solicitudes = em.getReference(Solicitudes.class, id);
                solicitudes.getIdSolicitudes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitudes with id " + id + " no longer exists.", enfe);
            }
            TorneosIndividuales idTorneoIndividual = solicitudes.getIdTorneoIndividual();
            if (idTorneoIndividual != null) {
                idTorneoIndividual.getSolicitudesCollection().remove(solicitudes);
                idTorneoIndividual = em.merge(idTorneoIndividual);
            }
            Collection<CombatesIndividuales> combatesIndividualesCollection = solicitudes.getCombatesIndividualesCollection();
            for (CombatesIndividuales combatesIndividualesCollectionCombatesIndividuales : combatesIndividualesCollection) {
                combatesIndividualesCollectionCombatesIndividuales.setIdSolicitud1(null);
                combatesIndividualesCollectionCombatesIndividuales = em.merge(combatesIndividualesCollectionCombatesIndividuales);
            }
            Collection<CombatesIndividuales> combatesIndividualesCollection1 = solicitudes.getCombatesIndividualesCollection1();
            for (CombatesIndividuales combatesIndividualesCollection1CombatesIndividuales : combatesIndividualesCollection1) {
                combatesIndividualesCollection1CombatesIndividuales.setIdSolicitud2(null);
                combatesIndividualesCollection1CombatesIndividuales = em.merge(combatesIndividualesCollection1CombatesIndividuales);
            }
            Collection<CombatesIndividuales> combatesIndividualesCollection2 = solicitudes.getCombatesIndividualesCollection2();
            for (CombatesIndividuales combatesIndividualesCollection2CombatesIndividuales : combatesIndividualesCollection2) {
                combatesIndividualesCollection2CombatesIndividuales.setIdSolicitudGanador(null);
                combatesIndividualesCollection2CombatesIndividuales = em.merge(combatesIndividualesCollection2CombatesIndividuales);
            }
            em.remove(solicitudes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Solicitudes> findSolicitudesEntities() {
        return findSolicitudesEntities(true, -1, -1);
    }

    public List<Solicitudes> findSolicitudesEntities(int maxResults, int firstResult) {
        return findSolicitudesEntities(false, maxResults, firstResult);
    }

    private List<Solicitudes> findSolicitudesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Solicitudes.class));
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

    public Solicitudes findSolicitudes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Solicitudes.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitudes> rt = cq.from(Solicitudes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
