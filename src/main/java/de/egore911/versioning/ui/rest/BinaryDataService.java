package de.egore911.versioning.ui.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.egore911.appframework.ui.rest.AbstractService;
import de.egore911.persistence.util.EntityManagerUtil;
import de.egore911.versioning.persistence.dao.BinaryDataDao;
import de.egore911.versioning.persistence.model.BinaryDataEntity;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.persistence.selector.ServerSelector;

@Path("binary_data")
public class BinaryDataService extends AbstractService {

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public byte[] getRaw(@PathParam("id") Integer id) {
		BinaryDataEntity binaryData = new BinaryDataDao().findById(id);
		return binaryData.getData();
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") Integer id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			List<ServerEntity> servers = new ServerSelector().withIconId(id)
					.findAll();
			servers.stream().forEach(server -> {
				server.setIcon(null);
				em.persist(server);
			});
			BinaryDataDao binaryDataDao = new BinaryDataDao();
			BinaryDataEntity binaryData = binaryDataDao.findById(id);
			binaryDataDao.remove(binaryData);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}
}
