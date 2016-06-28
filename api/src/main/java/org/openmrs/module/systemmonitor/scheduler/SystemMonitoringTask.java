package org.openmrs.module.systemmonitor.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;

/**
 * Sample class of how a scheduled task may look like, this executes nothing for
 * now
 * 
 * @author k-joseph
 *
 */
public abstract class SystemMonitoringTask implements Runnable {
	private transient final Log log = LogFactory.getLog(getClass());
	private volatile Session currentSession;

	// Properties that should be set on this task when it is instantiated,
	// before it is run

	private SessionFactory sessionFactory;
	private long scheduledExecutionTime;

	@Override
	public void run() {
		try {
			log.info("Running system monitoring task: " + getClass().getSimpleName());
			currentSession = sessionFactory.getCurrentSession();
			executeTask();
			log.info("Completed system monitoring task: " + getClass().getSimpleName());
		} catch (Exception e) {
			log.error("An error occurred while running system monitoring s task: " + getClass(), e);
		}
	}

	/**
	 * Subclasses should implement this method with the actual logic for this
	 * task
	 */
	protected abstract void executeTask();

	/**
	 * Attempt to cancel the currently running task by closing the hibernate
	 * session
	 */
	public void cancelTask() {
		log.info("Attempting to cancel " + getClass().getSimpleName());
		Session session = currentSession;
		if (session != null && session.isOpen()) {
			log.info(getClass().getSimpleName() + " task has been cancelled");
		} else {
			log.info("Did not have to cancel " + getClass().getSimpleName() + " task as it was not running");
		}
	}

	public long getScheduledExecutionTime() {
		return scheduledExecutionTime;
	}

	public void setScheduledExecutionTime(long scheduledExecutionTime) {
		this.scheduledExecutionTime = scheduledExecutionTime;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected SystemMonitorService getSystemMonitorService() {
		return Context.getService(SystemMonitorService.class);
	}

}
