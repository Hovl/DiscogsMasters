package ebs.discogs.schedulers;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.logging.Logger;

/**
 * Created by Aleksey Dubov
 * Date: 15/01/11
 * Time: 23:45
 * Copyright (c) 2015
 */
public class MastersScheduler {
	private static Logger logger = Logger.getLogger(MastersScheduler.class.getName());

	public static void main(String[] args) throws SchedulerException {
		int interval = 24;

		String intervalSt = System.getenv("DISCOGS_REFRESH_INTERVAL");
		if(intervalSt != null) {
			interval = Integer.parseInt(intervalSt);
		}

		logger.info("Interval is set to " + interval);

		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();

		JobDetail detail = JobBuilder.newJob(MastersCheckJob.class).build();

		Trigger trigger = TriggerBuilder.newTrigger()
				.startNow()
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInHours(interval)
								.repeatForever())
				.build();

		scheduler.scheduleJob(detail, trigger);

		logger.info("Masters check scheduler is scheduled");
	}

}
