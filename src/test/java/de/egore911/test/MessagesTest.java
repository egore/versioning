package de.egore911.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class MessagesTest {

	@Test
	public void testPropertiesKeys() throws IOException {
		Properties messages = new Properties();
		try (InputStream messageProperties = MessagesTest.class
				.getResourceAsStream("/messages.properties")) {
			Assert.assertNotNull(messageProperties);
			messages.load(messageProperties);
		}

		Properties messagesDe = new Properties();
		try (InputStream messageDeProperties = MessagesTest.class
				.getResourceAsStream("/messages_de.properties")) {
			Assert.assertNotNull(messageDeProperties);
			messagesDe.load(messageDeProperties);
		}

		Set<Object> messagesKeySet = messages.keySet();
		Set<Object> messagesDeKeySet = messagesDe.keySet();
		Assert.assertEquals(messagesKeySet.size(), messagesDeKeySet.size());

		// Basically
		// "Assert.assertTrue(messages.keySet().equals(messagesDe.keySet()));"
		// but with nicer error output
		StringBuilder errors = new StringBuilder();
		messagesKeySet.stream()
				.filter(key -> !messagesDeKeySet.contains(key))
				.forEach(key -> {
			errors.append(key);
			errors.append(" exists in messages.properties, but not messages_de.properties");
			errors.append('\n');
		});
		Assert.assertTrue(errors.toString(), errors.length() == 0);

		messagesDeKeySet.stream()
				.filter(key -> !messagesKeySet.contains(key))
				.forEach(key -> {
			errors.append(key);
			errors.append(" exists in messages_de.properties, but not messages.properties");
			errors.append('\n');
		});
		Assert.assertTrue(errors.toString(), errors.length() == 0);
	}
}
