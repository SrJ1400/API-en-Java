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
public class TorneosIndividualesJpaController implements Serializable {

    public TorneosIndividualesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TorneosIndividuales torneosIndividuales) {
        if (torneosIndividuales.getCombatesIndividualesCollection() == null) {
            torneosIndividuales.setCombatesIndividualesCollection(new ArrayList<CombatesIndividuales>());
        }
        if (torneosIndividuales.getSolicitudesCollection() == null) {
            torneosIndividuales.setSolicitudesCollection(new ArrayList<Solicitudes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gestor idGestor = torneosIndividuales.getIdGestor();
            if (idGestor != null) {
                idGestor = em.getReference(idGestor.getClass(), idGestor.getIdGestor());
                torneosIndividuales.setIdGestor(idGestor);
            }
            Collection<CombatesIndividuales> attachedCombatesIndividualesCollection = new ArrayList<CombatesIndividuales>();
            for (CombatesIndividuales combatesIndividualesCollectionCombatesIndividualesToAttach : torneosIndividuales.getCombatesIndividualesCollection()) {
                combatesIndividualesCollectionCombatesIndividualesToAttach = em.getReference(combatesIndividualesCollectionCombatesIndividualesToAttach.getClass(), combatesIndividualesCollectionCombatesIndividualesToAttach.getIdCombatesIndividuales());
                attachedCombatesIndividualesCollection.add(combatesIndividualesCollectionCombatesIndividualesToAttach);
            }
            torneosIndividuales.setCombatesIndividualesCollection(attachedCombatesIndividualesCollection);
            Collection<Solicitudes> attachedSolicitudesCollection = new ArrayList<Solicitudes>();
            for (Solicitudes solicitudesCollectionSolicitudesToAttach : torneosIndividuales.getSolicitudesCollection()) {
                solicitudesCollectionSolicitudesToAttach = em.getReference(solicitudesCollectionSolicitudesToAttach.getClass(), solicitudesCollectionSolicitudesToAttach.getIdSolicitudes());
                attachedSolicitudesCollection.add(solicitudesCollectionSolicitudesToAttach);
            }
            torneosIndividuales.setSolicitudesCollection(attachedSolicitudesCollection);
            em.persist(torneosIndividuales);
            if (idGestor != null) {
                idGestor.getTorneosIndividualesCollection().add(torneosIndividuales);
                idGestor = em.merge(idGestor);
            }
            for (CombatesIndividuales combatesIndividualesCollectionCombatesIndividuales : torneosIndividuales.getCombatesIndividualesCollection()) {
                TorneosIndividuales oldIdTorneoIndividualOfCombatesIndividualesCollectionCombatesIndividuales = combatesIndividualesCollectionCombatesIndividuales.getIdTorneoIndividual();
                combatesIndividualesCollectionCombatesIndividuales.setIdTorneoIndividual(torneosIndividuales);
                combatesIndividualesCollectionCombatesIndividuales = em.merge(combatesIndividualesCollectionCombatesIndividuales);
                if (oldIdTorneoIndividualOfCombatesIndividualesCollectionCombatesIndividuales != null) {
                    oldIdTorneoIndividualOfCombatesIndividualesCollectionCombatesIndividuales.getCombatesIndividualesCollection().remove(combatesIndividualesCollectionCombatesIndividuales);
                    oldIdTorneoIndividualOfCombatesIndividualesCollectionCombatesIndividuales = em.merge(oldIdTorneoIndividualOfCombatesIndividualesCollectionCombatesIndividuales);
                }
            }
            for (Solicitudes solicitudesCollectionSolicitudes : torneosIndividuales.getSolicitudesCollection()) {
                TorneosIndividuales oldIdTorneoIndividualOfSolicitudesCollectionSolicitudes = solicitudesCollectionSolicitudes.getIdTorneoIndividual();
                solicitudesCollectionSolicitudes.setIdTorneoIndividual(torneosIndividuales);
                solicitudesCollectionSolicitudes = em.merge(solicitudesCollectionSolicitudes);
                if (oldIdTorneoIndividualOfSolicitudesCollectionSolicitudes != null) {
                    oldIdTorneoIndividualOfSolicitudesCollectionSolicitudes.getSolicitudesCollection().remove(solicitudesCollectionSolicitudes);
                    oldIdTorneoIndividualOfSolicitudesCollectionSolicitudes = em.merge(oldIdTorneoIndividualOfSolicitudesCollectionSolicitudes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TorneosIndividuales torneosIndividuales) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TorneosIndividuales persistentTorneosIndividuales = em.find(TorneosIndividuales.class, torneosIndividuales.getIdTorneoIndividual());
            Gestor idGestorOld = persistentTorneosIndividuales.getIdGestor();
            Gestor idGestorNew = torneosIndividuales.getIdGestor();
            Collection<CombatesIndividuales> combatesIndividualesCollectionOld = persistentTorneosIndividuales.getCombatesIndividualesCollection();
            Collection<CombatesIndividuales> combatesIndividualesCollectionNew = torneosIndividuales.getCombatesIndividualesCollection();
            Collection<Solicitudes> solicitudesCollectionOld = persistentTorneosIndividuales.getSolicitudesCollection();
            Collection<Solicitudes> solicitudesCollectionNew = torneosIndividuales.getSolicitudesCollection();
            if (idGestorNew != null) {
                idGestorNew = em.getReference(idGestorNew.getClass(), idGestorNew.getIdGestor());
                torneosIndividuales.setIdGestor(idGestorNew);
            }
            Collection<CombatesIndividuales> attachedCombatesIndividualesCollectionNew = new ArrayList<CombatesIndividuales>();
            for (CombatesIndividuales combatesIndividualesCollectionNewCombatesIndividualesToAttach : combatesIndividualesCollectionNew) {
                combatesIndividualesCollectionNewCombatesIndividualesToAttach = em.getReference(combatesIndividualesCollectionNewCombatesIndividualesToAttach.getClass(), combatesIndividualesCollectionNewCombatesIndividualesToAttach.getIdCombatesIndividuales());
                attachedCombatesIndividualesCollectionNew.add(combatesIndividualesCollectionNewCombatesIndividualesToAttach);
            }
            combatesIndividualesCollectionNew = attachedCombatesIndividualesCollectionNew;
            torneosIndividuales.setCombatesIndividualesCollection(combatesIndividualesCollectionNew);
            Collection<Solicitudes> attachedSolicitudesCollectionNew = new ArrayList<Solicitudes>();
            for (Solicitudes solicitudesCollectionNewSolicitudesToAttach : solicitudesCollectionNew) {
                solicitudesCollectionNewSolicitudesToAttach = em.getReference(solicitudesCollectionNewSolicitudesToAttach.getClass(), solicitudesCollectionNewSolicitudesToAttach.getIdSolicitudes());
                attachedSolicitudesCollectionNew.add(solicitudesCollectionNewSolicitudesToAttach);
            }
            solicitudesCollectionNew = attachedSolicitudesCollectionNew;
            torneosIndividuales.setSolicitudesCollection(solicitudesCollectionNew);
            torneosIndividuales = em.merge(torneosIndividuales);
            if (idGestorOld != null && !idGestorOld.equals(idGestorNew)) {
                idGestorOld.getTorneosIndividualesCollection().remove(torneosIndividuales);
                idGestorOld = em.merge(idGestorOld);
            }
            if (idGestorNew != null && !idGestorNew.equals(idGestorOld)) {
                idGestorNew.getTorneosIndividualesCollection().add(torneosIndividuales);
                idGestorNew = em.merge(idGestorNew);
            }
            for (CombatesIndividuales combatesIndividualesCollectionOldCombatesIndividuales : combatesIndividualesCollectionOld) {
                if (!combatesIndividualesCollectionNew.contains(combatesIndividualesCollectionOldCombatesIndividuales)) {
                    combatesIndividualesCollectionOldCombatesIndividuales.setIdTorneoIndividual(null);
                    combatesIndividualesCollectionOldCombatesIndividuales = em.merge(combatesIndividualesCollectionOldCombatesIndividuales);
                }
            }
            for (CombatesIndividuales combatesIndividualesCollectionNewCombatesIndividuales : combatesIndividualesCollectionNew) {
                if (!combatesIndividualesCollectionOld.contains(combatesIndividualesCollectionNewCombatesIndividuales)) {
                    TorneosIndividuales oldIdTorneoIndividualOfCombatesIndividualesCollectionNewCombatesIndividuales = combatesIndividualesCollectionNewCombatesIndividuales.getIdTorneoIndividual();
                    combatesIndividualesCollectionNewCombatesIndividuales.setIdTorneoIndividual(torneosIndividuales);
                    combatesIndividualesCollectionNewCombatesIndividuales = em.merge(combatesIndividualesCollectionNewCombatesIndividuales);
                    if (oldIdTorneoIndividualOfCombatesIndividualesCollectionNewCombatesIndividuales != null && !oldIdTorneoIndividualOfCombatesIndividualesCollectionNewCombatesIndividuales.equals(torneosIndividuales)) {
                        oldIdTorneoIndividualOfCombatesIndividualesCollectionNewCombatesIndividuales.getCombatesIndividualesCollection().remove(combatesIndividualesCollectionNewCombatesIndividuales);
                        oldIdTorneoIndividualOfCombatesIndividualesCollectionNewCombatesIndividuales = em.merge(oldIdTorneoIndividualOfCombatesIndividualesCollectionNewCombatesIndividuales);
                    }
                }
            }
            for (Solicitudes solicitudesCollectionOldSolicitudes : solicitudesCollectionOld) {
                if (!solicitudesCollectionNew.contains(solicitudesCollectionOldSolicitudes)) {
                    solicitudesCollectionOldSolicitudes.setIdTorneoIndividual(null);
                    solicitudesCollectionOldSolicitudes = em.merge(solicitudesCollectionOldSolicitudes);
                }
            }
            for (Solicitudes solicitudesCollectionNewSolicitudes : solicitudesCollectionNew) {
                if (!solicitudesCollectionOld.contains(solicitudesCollectionNewSolicitudes)) {
                    TorneosIndividuales oldIdTorneoIndividualOfSolicitudesCollectionNewSolicitudes = solicitudesCollectionNewSolicitudes.getIdTorneoIndividual();
                    solicitudesCollectionNewSolicitudes.setIdTorneoIndividual(torneosIndividuales);
                    solicitudesCollectionNewSolicitudes = em.merge(solicitudesCollectionNewSolicitudes);
                    if (oldIdTorneoIndividualOfSolicitudesCollectionNewSolicitudes != null && !oldIdTorneoIndividualOfSolicitudesCollectionNewSolicitudes.equals(torneosIndividuales)) {
                        oldIdTorneoIndividualOfSolicitudesCollectionNewSolicitudes.getSolicitudesCollection().remove(solicitudesCollectionNewSolicitudes);
                        oldIdTorneoIndividualOfSolicitudesCollectionNewSolicitudes = em.merge(oldIdTorneoIndividualOfSolicitudesCollectionNewSolicitudes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = torneosIndividuales.getIdTorneoIndividual();
                if (findTorneosIndividuales(id) == null) {
                    throw new NonexistentEntityException("The torneosIndividuales with id " + id + " no longer exists.");
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
            TorneosIndividuales torneosIndividuales;
            try {
                torneosIndividuales = em.getReference(TorneosIndividuales.class, id);
                torneosIndividuales.getIdTorneoIndividual();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The torneosIndividuales with id " + id + " no longer exists.", enfe);
            }
            Gestor idGestor = torneosIndividuales.getIdGestor();
            if (idGestor != null) {
                idGestor.getTorneosIndividualesCollection().remove(torneosIndividuales);
                idGestor = em.merge(idGestor);
            }
            Collection<CombatesIndividuales> combatesIndividualesCollection = torneosIndividuales.getCombatesIndividualesCollection();
            for (CombatesIndividuales combatesIndividualesCollectionCombatesIndividuales : combatesIndividualesCollection) {
                combatesIndividualesCollectionCombatesIndividuales.setIdTorneoIndividual(null);
                combatesIndividualesCollectionCombatesIndividuales = em.merge(combatesIndividualesCollectionCombatesIndividuales);
            }
            Collection<Solicitudes> solicitudesCollection = torneosIndividuales.getSolicitudesCollection();
            for (Solicitudes solicitudesCollectionSolicitudes : solicitudesCollection) {
                solicitudesCollectionSolicitudes.setIdTorneoIndividual(null);
                solicitudesCollectionSolicitudes = em.merge(solicitudesCollectionSolicitudes);
            }
            em.remove(torneosIndividuales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TorneosIndividuales> findTorneosIndividualesEntities() {
        return findTorneosIndividualesEntities(true, -1, -1);
    }

    public List<TorneosIndividuales> findTorneosIndividualesEntities(int maxResults, int firstResult) {
        return findTorneosIndividualesEntities(false, maxResults, firstResult);
    }

    private List<TorneosIndividuales> findTorneosIndividualesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TorneosIndividuales.class));
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

    public TorneosIndividuales findTorneosIndividuales(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TorneosIndividuales.class, id);
        } finally {
            em.close();
        }
    }

    public int getTorneosIndividualesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TorneosIndividuales> rt = cq.from(TorneosIndividuales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
