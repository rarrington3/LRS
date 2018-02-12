package gov.hud.lrs.workflow.service;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import gov.hud.lrs.common.security.FhacUser;
import gov.hud.lrs.common.security.SecurityService;

@Service
public class AsyncService {

	@Autowired private SecurityService securityService;

	// this is a hack to workaround the fact that spring @Async methods use AOP and so cannot be called within a class
	// we also cannot use a ThreadPoolTaskExecutor directly as that doesn't setup the necessary hibernate session and whatnot inside a thread
	// that said, I do kind of like it because it forces all threading to go through one place so we can never forget to set up the fhac user
	@Async
	public <T> Future<T> call(Callable<T> callable, FhacUser fhacUser) {
		securityService.setFhacUser(fhacUser);
		try {
			return new AsyncResult<T>(callable.call());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
