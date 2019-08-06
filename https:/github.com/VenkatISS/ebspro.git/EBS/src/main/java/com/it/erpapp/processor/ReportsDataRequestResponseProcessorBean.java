package com.it.erpapp.processor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReportsDataRequestResponseProcessorBean implements ProcessorBean {

	@SuppressWarnings("rawtypes")
	public void process(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String actionId = ((String) request.getParameter("actionId"));
			if ((actionId != null) && (!actionId.equals("0"))
					&& (!actionId.equals(""))) {
				String serviceBean = ServiceMethodsMapProcessor.getInstance().getServiceBeanName(actionId);
				String methodName = ServiceMethodsMapProcessor.getInstance().getServiceBeanMethodName(actionId);

				Object[] paramObjs = { request, response };
				Class[] parameterTypes = { HttpServletRequest.class,
						HttpServletResponse.class };

				Class<?> serviceBeanClass = Class.forName(serviceBean);
				Object sbObj = serviceBeanClass.newInstance();
				Method method = serviceBeanClass.getDeclaredMethod(methodName,
						parameterTypes);

				method.invoke(sbObj, paramObjs);
				serviceBeanClass = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
