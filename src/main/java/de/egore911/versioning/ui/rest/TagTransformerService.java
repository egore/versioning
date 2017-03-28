package de.egore911.versioning.ui.rest;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.subject.Subject;

import de.egore911.appframework.ui.exceptions.BadArgumentException;
import de.egore911.appframework.ui.rest.AbstractResourceService;
import de.egore911.versioning.persistence.dao.TagTransformerDao;
import de.egore911.versioning.persistence.model.TagTransformerEntity;
import de.egore911.versioning.persistence.selector.TagTransformerSelector;
import de.egore911.versioning.ui.dto.TagTransformer;

@Path("tag_transformer")
public class TagTransformerService
		extends AbstractResourceService<TagTransformer, TagTransformerEntity> {

	@Override
	protected Class<TagTransformer> getDtoClass() {
		return TagTransformer.class;
	}

	@Override
	protected Class<TagTransformerEntity> getEntityClass() {
		return TagTransformerEntity.class;
	}

	@Override
	protected TagTransformerSelector getSelector(Subject subject) {
		return new TagTransformerSelector();
	}

	@Override
	protected TagTransformerDao getDao() {
		return new TagTransformerDao();
	}

	@Override
	protected void validate(TagTransformer dto, TagTransformerEntity entity) {
		super.validate(dto, entity);

		try {
			Pattern.compile(entity.getSearchPattern());
		} catch (PatternSyntaxException e) {
			throw new BadArgumentException("Invalid search pattern: " + e.getMessage());
		}
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("test")
	public String testPattern(@QueryParam("testInput") String testInput,
			@QueryParam("searchPattern") String searchPattern,
			@QueryParam("replacementPattern") String replacementPattern) {
		try {
			return testInput.replaceAll(searchPattern, replacementPattern);
		} catch (PatternSyntaxException e) {
			return e.getMessage();
		}
	}

}
