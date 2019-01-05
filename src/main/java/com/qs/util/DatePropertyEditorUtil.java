package com.qs.util;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.beans.PropertyEditorSupport;

public class DatePropertyEditorUtil extends PropertyEditorSupport{

	@Override
	public void setAsText(String arg0) throws IllegalArgumentException {
		LocalDate localDate = LocalDate.parse(arg0,DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
		this.setValue(localDate.toDate());
	}

}
