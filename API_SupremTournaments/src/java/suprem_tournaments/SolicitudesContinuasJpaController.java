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
public class SolicitudesContinuasJpaController implements Serializable {

    public SolicitudesContinuasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SolicitudesContinuas solicitudesContinuas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(solicitudesContinuas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SolicitudesContinuas solicitudesContinuas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            solicitudesContinuas = em.merge(solicitudesContinuas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = solicitudesContinuas.getIdSolicitudesContinuas();
                if (findSolicitudesContinuas(id) == null) {
                    throw new NonexistentEntityException("The solicitudesContinuas with id " + id + " no longer exists.");
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
            SolicitudesContinuas solicitudesContinuas;
            try {
                solicitudesContinuas = em.getReference(SolicitudesContinuas.class, id);
                solicitudesContinuas.getIdSolicitudesContinuas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitudesContinuas with id " + id + " no longer exists.", enfe);
            }
            em.remove(solicitudesContinuas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SolicitudesContinuas> findSolicitudesContinuasEntities() {
        return findSolicitudesContinuasEntities(true, -1, -1);
    }

    public List<SolicitudesContinuas> findSolicitudesContinuasEntities(int maxResults, int firstResult) {
        return findSolicitudesContinuasEntities(false, maxResults, firstResult);
    }

    private List<SolicitudesContinuas> findSolicitudesContinuasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SolicitudesContinuas.class));
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

    public SolicitudesContinuas findSolicitudesContinuas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SolicitudesContinuas.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudesContinuasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SolicitudesContinuas> rt = cq.from(SolicitudesContinuas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
