package ch.di_vendo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import ch.di_vendo.JpaUtil;
import ch.di_vendo.model.Ssb;

public class OlxDAO {

	private static final Logger LOGGER = Logger.getLogger(OlxDAO.class);
	private static OlxDAO INSTANCE = new OlxDAO();

	public static OlxDAO getInstance() {
		return OlxDAO.INSTANCE;
	}

	private OlxDAO() {
	}

	/**
	 * Adds job to database
	 * 
	 * @param ssb
	 *            Job to add
	 */
	public void addJob(Ssb ssb) {
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtil.getEMF().createEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(ssb);
			entityManager.getTransaction().commit();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	/**
	 * Checks if there is a job with same URL in database.
	 * 
	 * @param ssb
	 *            Job with URL to check
	 * @return true, if there is Job with same URL in database, false otherwise
	 */
	public boolean isUrlExist(Ssb ssb) {
		long start = System.currentTimeMillis();
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtil.getEMF().createEntityManager();
			entityManager.getTransaction().begin();
			TypedQuery<Ssb> query = entityManager.createQuery("SELECT pd FROM Ssb pd WHERE pd.detailUrl = ? ",
					Ssb.class);
			query.setParameter(1, ssb.getDetailUrl());
			List<Ssb> resultList = query.getResultList();
			entityManager.getTransaction().commit();
			long end = System.currentTimeMillis();
			LOGGER.info("isUrlExist takes = " + (end - start));
			if (resultList.isEmpty()) {
				return false;// does not exist
			} else {
				return true;
			}
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	/**
	 * Gets all jobs in database, that are not removed.
	 * 
	 * @return List of all jobs in database
	 */
	public List<Ssb> getAllNotRemovedJobs() {
		long start = System.currentTimeMillis();
		List<Ssb> allJobs;
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtil.getEMF().createEntityManager();
			entityManager.getTransaction().begin();
			TypedQuery<Ssb> query = entityManager.createQuery("SELECT pd FROM Ssb pd WHERE pd.isRemovedPost = 0",
					Ssb.class);
			allJobs = query.getResultList();
			entityManager.getTransaction().commit();
			long end = System.currentTimeMillis();
			LOGGER.info("getAllJobs takes = " + (end - start));
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return allJobs;
	}

	/**
	 * Sets removed flag for job. If flag is set up, means job is removed from
	 * ssb.
	 * 
	 * @param ssb
	 *            Job to mark as removed from ssb
	 */
	public void updateIsRemovedPostField(Ssb ssb) {

		long start = System.currentTimeMillis();
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtil.getEMF().createEntityManager();
			entityManager.getTransaction().begin();
			Ssb foundJob = entityManager.find(Ssb.class, ssb.getId());
			foundJob.setRemovedPost(1);
			entityManager.getTransaction().commit();
			long end = System.currentTimeMillis();
			LOGGER.info("updateIsRemovedPostField takes = " + (end - start));
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	/**
	 * Retrieves jobs by paging from database
	 * 
	 * @param skip
	 *            Number of jobs to skip
	 * @param limit
	 *            Number of jobs to return
	 * @return List of jobs in page
	 */
	public List<Ssb> skipLimitQuery(int skip, int limit) {
		long start = System.currentTimeMillis();
		List<Ssb> allJobs;
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtil.getEMF().createEntityManager();
			entityManager.getTransaction().begin();
			TypedQuery<Ssb> query = entityManager
					.createQuery("SELECT pd FROM Ssb pd order by crawledTime desc", Ssb.class).setFirstResult(skip)
					.setMaxResults(limit);
			allJobs = query.getResultList();
			entityManager.getTransaction().commit();
			long end = System.currentTimeMillis();
			LOGGER.info("skipLimitQuery = " + (end - start));
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return allJobs;
	}

	/**
	 * Returns all jobs that where crawled after given timestamp.
	 * 
	 * @param timestamp
	 *            Timestamp, to compare with jobs crawled time
	 * @return List of all jobs, that where crawled after given timestamp.
	 */
	public List<Ssb> syncfromQuery(long timestamp) {
		long start = System.currentTimeMillis();
		List<Ssb> allJobs;
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtil.getEMF().createEntityManager();
			entityManager.getTransaction().begin();
			TypedQuery<Ssb> query = entityManager.createQuery("SELECT pd FROM Ssb pd where crawledTime > " + timestamp
					+ " order by crawledTime desc", Ssb.class);
			allJobs = query.getResultList();
			entityManager.getTransaction().commit();
			long end = System.currentTimeMillis();
			LOGGER.info("syncfromQuery = " + (end - start));
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return allJobs;
	}

	/**
	 * Returns all jobs that where removed from Ssb after given timestamp.
	 * 
	 * @param timestamp
	 *            Timestamp, to compare with jobs crawled time
	 * @return List of all jobs, that where removed from Ssb after given
	 *         timestamp.
	 */
	public List<Ssb> deletedsinceQuery(long timestamp) {
		long start = System.currentTimeMillis();
		List<Ssb> allJobs;
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtil.getEMF().createEntityManager();
			entityManager.getTransaction().begin();
			TypedQuery<Ssb> query = entityManager.createQuery("SELECT pd FROM Ssb pd where crawledTime > " + timestamp
					+ " AND isRemovedPost = 1 order by crawledTime desc", Ssb.class);
			allJobs = query.getResultList();
			entityManager.getTransaction().commit();
			long end = System.currentTimeMillis();
			LOGGER.info("deletedsinceQuery = " + (end - start));
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return allJobs;
	}

	public List<Ssb> skipLimitSyncfromQuery(int skip, int limit, long timestamp) {
		long start = System.currentTimeMillis();
		List<Ssb> allJobs;
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtil.getEMF().createEntityManager();
			entityManager.getTransaction().begin();
			TypedQuery<Ssb> query = entityManager
					.createQuery(
							"SELECT pd FROM Ssb pd where crawledTime > " + timestamp + " order by crawledTime desc",
							Ssb.class).setFirstResult(skip).setMaxResults(limit);
			allJobs = query.getResultList();
			entityManager.getTransaction().commit();
			long end = System.currentTimeMillis();
			LOGGER.info("skipLimitSyncfromQuery = " + (end - start));
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return allJobs;
	}

	public List<Ssb> skipLimitdeletedsinceQuery(int skip, int limit, long timestamp) {
		long start = System.currentTimeMillis();
		List<Ssb> allJobs;
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtil.getEMF().createEntityManager();
			entityManager.getTransaction().begin();
			TypedQuery<Ssb> query = entityManager
					.createQuery(
							"SELECT pd FROM Ssb pd where crawledTime > " + timestamp
									+ " AND isRemovedPost = 1 order by crawledTime desc", Ssb.class)
					.setFirstResult(skip).setMaxResults(limit);
			allJobs = query.getResultList();
			entityManager.getTransaction().commit();
			long end = System.currentTimeMillis();
			LOGGER.info("skipLimitdeletedsinceQuery = " + (end - start));
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return allJobs;
	}

	//
	public int countOfJobs() {
		long start = System.currentTimeMillis();
		List<Ssb> allJobs = new ArrayList<>();
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtil.getEMF().createEntityManager();
			entityManager.getTransaction().begin();
			TypedQuery<Ssb> query = entityManager.createQuery("SELECT pd FROM Ssb pd", Ssb.class);
			allJobs = query.getResultList();
			entityManager.getTransaction().commit();
			long end = System.currentTimeMillis();
			LOGGER.info("countOfJobs = " + (end - start));
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return allJobs.size();
	}

	public Ssb idQuery(int id) {
		long start = System.currentTimeMillis();
		Ssb ssb;
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtil.getEMF().createEntityManager();
			entityManager.getTransaction().begin();
			TypedQuery<Ssb> query = entityManager.createQuery("SELECT pd FROM Ssb pd where id = " + id, Ssb.class);
			try {
				ssb = query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
			entityManager.getTransaction().commit();
			long end = System.currentTimeMillis();
			LOGGER.info("id query = " + (end - start));
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return ssb;
	}

}
