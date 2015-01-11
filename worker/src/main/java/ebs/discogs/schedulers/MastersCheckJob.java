package ebs.discogs.schedulers;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.logging.Logger;

/**
 * Created by Aleksey Dubov
 * Date: 15/01/11
 * Time: 23:47
 * Copyright (c) 2015
 */
public class MastersCheckJob implements Job {
	private static Logger logger = Logger.getLogger(MastersCheckJob.class.getName());

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		logger.info("Masters check job executed.");
	}
}
