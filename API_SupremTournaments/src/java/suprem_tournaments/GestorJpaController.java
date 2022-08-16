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
public class GestorJpaController implements Serializable {

    public GestorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gestor gestor) {
        if (gestor.getTorneosIndividualesCollection() == null) {
            gestor.setTorneosIndividualesCollection(new ArrayList<TorneosIndividuales>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TorneosIndividuales> attachedTorneosIndividualesCollection = new ArrayList<TorneosIndividuales>();
            for (TorneosIndividuales torneosIndividualesCollectionTorneosIndividualesToAttach : gestor.getTorneosIndividualesCollection()) {
                torneosIndividualesCollectionTorneosIndividualesToAttach = em.getReference(torneosIndividualesCollectionTorneosIndividualesToAttach.getClass(), torneosIndividualesCollectionTorneosIndividualesToAttach.getIdTorneoIndividual());
                attachedTorneosIndividualesCollection.add(torneosIndividualesCollectionTorneosIndividualesToAttach);
            }
            gestor.setTorneosIndividualesCollection(attachedTorneosIndividualesCollection);
            em.persist(gestor);
            for (TorneosIndividuales torneosIndividualesCollectionTorneosIndividuales : gestor.getTorneosIndividualesCollection()) {
                Gestor oldIdGestorOfTorneosIndividualesCollectionTorneosIndividuales = torneosIndividualesCollectionTorneosIndividuales.getIdGestor();
                torneosIndividualesCollectionTorneosIndividuales.setIdGestor(gestor);
                torneosIndividualesCollectionTorneosIndividuales = em.merge(torneosIndividualesCollectionTorneosIndividuales);
                if (oldIdGestorOfTorneosIndividualesCollectionTorneosIndividuales != null) {
                    oldIdGestorOfTorneosIndividualesCollectionTorneosIndividuales.getTorneosIndividualesCollection().remove(torneosIndividualesCollectionTorneosIndividuales);
                    oldIdGestorOfTorneosIndividualesCollectionTorneosIndividuales = em.merge(oldIdGestorOfTorneosIndividualesCollectionTorneosIndividuales);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gestor gestor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gestor persistentGestor = em.find(Gestor.class, gestor.getIdGestor());
            Collection<TorneosIndividuales> torneosIndividualesCollectionOld = persistentGestor.getTorneosIndividualesCollection();
            Collection<TorneosIndividuales> torneosIndividualesCollectionNew = gestor.getTorneosIndividualesCollection();
            Collection<TorneosIndividuales> attachedTorneosIndividualesCollectionNew = new ArrayList<TorneosIndividuales>();
            for (TorneosIndividuales torneosIndividualesCollectionNewTorneosIndividualesToAttach : torneosIndividualesCollectionNew) {
                torneosIndividualesCollectionNewTorneosIndividualesToAttach = em.getReference(torneosIndividualesCollectionNewTorneosIndividualesToAttach.getClass(), torneosIndividualesCollectionNewTorneosIndividualesToAttach.getIdTorneoIndividual());
                attachedTorneosIndividualesCollectionNew.add(torneosIndividualesCollectionNewTorneosIndividualesToAttach);
            }
            torneosIndividualesCollectionNew = attachedTorneosIndividualesCollectionNew;
            gestor.setTorneosIndividualesCollection(torneosIndividualesCollectionNew);
            gestor = em.merge(gestor);
            for (TorneosIndividuales torneosIndividualesCollectionOldTorneosIndividuales : torneosIndividualesCollectionOld) {
                if (!torneosIndividualesCollectionNew.contains(torneosIndividualesCollectionOldTorneosIndividuales)) {
                    torneosIndividualesCollectionOldTorneosIndividuales.setIdGestor(null);
                    torneosIndividualesCollectionOldTorneosIndividuales = em.merge(torneosIndividualesCollectionOldTorneosIndividuales);
                }
            }
            for (TorneosIndividuales torneosIndividualesCollectionNewTorneosIndividuales : torneosIndividualesCollectionNew) {
                if (!torneosIndividualesCollectionOld.contains(torneosIndividualesCollectionNewTorneosIndividuales)) {
                    Gestor oldIdGestorOfTorneosIndividualesCollectionNewTorneosIndividuales = torneosIndividualesCollectionNewTorneosIndividuales.getIdGestor();
                    torneosIndividualesCollectionNewTorneosIndividuales.setIdGestor(gestor);
                    torneosIndividualesCollectionNewTorneosIndividuales = em.merge(torneosIndividualesCollectionNewTorneosIndividuales);
                    if (oldIdGestorOfTorneosIndividualesCollectionNewTorneosIndividuales != null && !oldIdGestorOfTorneosIndividualesCollectionNewTorneosIndividuales.equals(gestor)) {
                        oldIdGestorOfTorneosIndividualesCollectionNewTorneosIndividuales.getTorneosIndividualesCollection().remove(torneosIndividualesCollectionNewTorneosIndividuales);
                        oldIdGestorOfTorneosIndividualesCollectionNewTorneosIndividuales = em.merge(oldIdGestorOfTorneosIndividualesCollectionNewTorneosIndividuales);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gestor.getIdGestor();
                if (findGestor(id) == null) {
                    throw new NonexistentEntityException("The gestor with id " + id + " no longer exists.");
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
            Gestor gestor;
            try {
                gestor = em.getReference(Gestor.class, id);
                gestor.getIdGestor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gestor with id " + id + " no longer exists.", enfe);
            }
            Collection<TorneosIndividuales> torneosIndividualesCollection = gestor.getTorneosIndividualesCollection();
            for (TorneosIndividuales torneosIndividualesCollectionTorneosIndividuales : torneosIndividualesCollection) {
                torneosIndividualesCollectionTorneosIndividuales.setIdGestor(null);
                torneosIndividualesCollectionTorneosIndividuales = em.merge(torneosIndividualesCollectionTorneosIndividuales);
            }
            em.remove(gestor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gestor> findGestorEntities() {
        return findGestorEntities(true, -1, -1);
    }

    public List<Gestor> findGestorEntities(int maxResults, int firstResult) {
        return findGestorEntities(false, maxResults, firstResult);
    }

    private List<Gestor> findGestorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gestor.class));
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

    public Gestor findGestor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gestor.class, id);
        } finally {
            em.close();
        }
    }

    public int getGestorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gestor> rt = cq.from(Gestor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
