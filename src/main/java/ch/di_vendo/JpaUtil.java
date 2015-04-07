package ch.di_vendo;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaUtil {
	private static final Logger LOG = LoggerFactory.getLogger(JpaUtil.class);

	private static final EntityManagerFactory emf;
	static {
		try {
			emf = Persistence.createEntityManagerFactory("db_tc_jobticker");
			
		} catch (Throwable ex) {
			LOG.error("Initial  EntityManagerFactory creation failed", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static EntityManagerFactory getEMF() {
		return emf;
	}

}
