/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of 
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.egore911.versioning.ui.converter;

import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@FacesConverter("jodaTimeConverter")
public class JodaTimeConverter implements Converter {

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forStyle("MS").withLocale(Locale.getDefault());

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		try {
			return dateTimeFormatter.parseDateTime(value);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		LocalDateTime localDateTime = (LocalDateTime) value;
		return localDateTime != null ? dateTimeFormatter.print(localDateTime)
				: null;
	}

}
