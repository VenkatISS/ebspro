package com.it.erpapp.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ProcessorBean {

	public void process(HttpServletRequest request,
			HttpServletResponse response);
}
