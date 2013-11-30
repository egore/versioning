package de.egore911.versioning.util.vcs;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Provider {

	private static final Logger log = LoggerFactory.getLogger(Provider.class);

	boolean tagExists(final String tagName) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Boolean> future = executor.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() {
				return tagExistsImpl(tagName);
			}
		});
		try {
			return future.get(10, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			log.error(e.getMessage(), e);
			return false;
		} finally {
			executor.shutdownNow();
		}
	}

	protected abstract boolean tagExistsImpl(String tagName);

}
