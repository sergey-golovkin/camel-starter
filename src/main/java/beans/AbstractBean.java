package arina.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AbstractBean implements DisposableBean, InitializingBean, ApplicationContextAware, BeanNameAware
{
    protected ApplicationContext context;
	protected String beanId;

	public void init() throws Exception
	{
	}

	@Override
	public void destroy() throws Exception
	{
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		init();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
        this.context = applicationContext;
	}

	@Override
	public void setBeanName(String id)
	{
		this.beanId = id;
	}
}
