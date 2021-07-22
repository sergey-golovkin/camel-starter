package arina.beans;

public abstract class AbstractDataSourceRunnableBean extends AbstractDataSourceBean implements Runnable
{
    Thread thread = null;
	protected long delay = 60000;

	public void setDelay(long delay)
	{
        if(delay >= 0)
		    this.delay = delay;
	}

	@Override
    public void init() throws Exception
    {
	    super.init();

        if(thread == null)
        {
            thread = new Thread(this);
            thread.setContextClassLoader(this.context.getClassLoader());
            thread.start();
        }
    }

    @Override
    public void destroy() throws Exception
    {
        if(thread != null)
        {
            thread.interrupt();
            thread.join();
            thread = null;
        }

        super.destroy();
    }

    @Override
    public void run()
    {
        try
        {
	        Thread.currentThread().setName(this.beanId);

            for( ; ! Thread.currentThread().isInterrupted(); )
            {
                if( ! Thread.currentThread().isInterrupted())
                    Thread.sleep(delay);

	            this.doWork();
            }
        }
        catch (InterruptedException ignore)
        {

        }
    }

	protected void doWork() throws InterruptedException
	{
	}
}
