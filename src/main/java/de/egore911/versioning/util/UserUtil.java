/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.egore911.versioning.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.User;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class UserUtil {

	private UserUtil() {
	}

	/**
	 * Calculate the SHA-1 sum for a given password (or any string for that
	 * matter).
	 * 
	 * @param password
	 *            the password to be hashed
	 * @return the SHA-1 sum for the given password
	 */
	public static String hashPassword(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return byteArrayToHexString(md.digest(password.getBytes(Charset
				.forName("UTF-8"))));
	}

	/**
	 * Convert a byte array to a hexadecimal represenation.
	 * 
	 * @param b
	 *            the byte array
	 * @return the hexadecimal represenation
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuilder result = new StringBuilder(b.length);
		for (int i = 0; i < b.length; i++) {
			result.append(Integer.toString((b[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return result.toString();
	}

	public static String getStartpage(User user) {
		if (user.hasPermission(Permission.CREATE_VERSIONS)) {
			return "/versions.xhtml";
		} else if (user.hasPermission(Permission.DEPLOY)) {
			return "/deployments.xhtml";
		} else if (user.hasPermission(Permission.ADMIN_SETTINGS)) {
			return "/projects.xthml";
		} else if (user.hasPermission(Permission.ADMIN_USERS)) {
			return "/users.xthml";
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = SessionUtil.getBundle();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("missing_permission"),
					bundle.getString("missing_permission_detail"));
			facesContext.addMessage("main:user_login", message);
			return "";
		}
	}
}
