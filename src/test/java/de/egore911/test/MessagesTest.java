package de.egore911.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessagesTest {

	@Test
	public void testDeAndEnMatch() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		Map<String, String> messagesEn;
		try (InputStream messageFile = new FileInputStream("src/main/webapp/app/scripts/i18n/en.json")) {
			Assert.assertNotNull(messageFile);
			messagesEn = mapper.readValue(messageFile, new TypeReference<Map<String, String>>() {});
		}

		Map<String, String> messagesDe;
		try (InputStream messageFile = new FileInputStream("src/main/webapp/app/scripts/i18n/de.json")) {
			Assert.assertNotNull(messageFile);
			messagesDe = mapper.readValue(messageFile, new TypeReference<Map<String, String>>() { });
		}

		Set<String> messagesEnKeySet = messagesEn.keySet();
		Set<String> messagesDeKeySet = messagesDe.keySet();
		assertThat(messagesEnKeySet, hasSize(messagesDeKeySet.size()));

		assertThat(messagesEnKeySet, containsInAnyOrder(messagesDeKeySet.toArray(new String[messagesDeKeySet.size()])));
		assertThat(messagesDeKeySet, containsInAnyOrder(messagesEnKeySet.toArray(new String[messagesEnKeySet.size()])));
	}
}
