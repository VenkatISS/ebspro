package com.it.erpapp.processor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class RequestResponseProcessorBean implements ProcessorBean {

	Logger logger = Logger.getLogger(RequestResponseProcessorBean.class.getName());
	
	@SuppressWarnings("rawtypes")
	public void process(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String actionId = ((String) request.getParameter("actionId"));
			if ((actionId != null) && (!("0").equals(actionId))
					&& (!("").equals(actionId))) {
				String serviceBean = ServiceMethodsMapProcessor.getInstance().getServiceBeanName(actionId);
				String methodName = ServiceMethodsMapProcessor.getInstance().getServiceBeanMethodName(actionId);
				System.out.println("method name is:"+methodName);

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
			logger.error(e);
			e.printStackTrace();
		}
	}
}
