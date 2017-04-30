package de.egore911.versioning.ui.rest;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;

import de.egore911.appframework.ui.rest.AbstractBinaryDataService;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.persistence.selector.ServerSelector;

@Path("binary_data")
public class BinaryDataService extends AbstractBinaryDataService {

	@Override
	protected void nullAndPersistUsers(@Nonnull EntityManager em, @Nonnull Integer id) {
		List<ServerEntity> servers = new ServerSelector().withIconId(id)
				.findAll();
		servers.forEach(server -> {
			server.setIcon(null);
			em.persist(server);
		});
	}

}
