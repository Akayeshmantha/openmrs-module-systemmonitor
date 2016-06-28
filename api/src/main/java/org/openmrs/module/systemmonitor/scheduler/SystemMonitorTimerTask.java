package org.openmrs.module.systemmonitor.scheduler;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.api.context.Daemon;

public class SystemMonitorTimerTask extends TimerTask {
	private final Log log = LogFactory.getLog(getClass());
	private static boolean enabled = false;
	private SystemMonitoringTask task;

	// ***** PROPERTIES THAT NEED TO BE SET ON EACH INSTANCE

	private Class<? extends SystemMonitoringTask> taskClass;
	private SessionFactory sessionFactory;

	/**
	 * @see TimerTask#run()
	 */
	@Override
	public final void run() {
		if (enabled) {
			createAndRunTask();
		} else {
			log.debug("Not running scheduled task; enabled = " + enabled);
		}
	}

	/**
	 * Construct a new instance of the configured task and execute it
	 */
	public void createAndRunTask() {
		try {
			log.info("Running system monitoring task: " + getTaskClass().getSimpleName());
			task = getTaskClass().newInstance();
			task.setScheduledExecutionTime(System.currentTimeMillis());
			task.setSessionFactory(sessionFactory);
			Daemon.runInNewDaemonThread(task);
		} catch (Exception e) {
			log.error("An error occurred while running scheduled system monitoring task", e);
		}
	}

	@Override
	public boolean cancel() {
		boolean ret = super.cancel();
		if (task != null) {
			task.cancelTask();
		}
		return ret;
	}

	public Class<? extends SystemMonitoringTask> getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(Class<? extends SystemMonitoringTask> taskClass) {
		this.taskClass = taskClass;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	public static boolean isEnabled() {
		return enabled;
	}

	public static void setEnabled(boolean enabled) {
		SystemMonitorTimerTask.enabled = enabled;
	}
}
