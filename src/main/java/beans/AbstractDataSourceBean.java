package arina.beans;

import javax.sql.*;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.sql.*;
import java.util.*;

public class AbstractDataSourceBean extends AbstractBean
{
	protected String dataSource;
	protected String dataSchema = "";
    protected CommonDataSource ds = null;

    public class aXid implements javax.transaction.xa.Xid, Serializable
    {
        private int formatId = 0;
        private byte gtrid[];
        private byte bqual[];
        public static final int MAXGTRIDSIZE = 64;
        public static final int MAXBQUALSIZE = 64;

        public aXid(int formatId, byte gtrid[], byte bqual[]) throws XAException
        {
            if(gtrid != null && gtrid.length > MAXGTRIDSIZE || bqual != null && bqual.length > MAXBQUALSIZE)
                throw new XAException(-4);

            this.formatId = formatId;
            this.gtrid = gtrid;
            this.bqual = bqual;
        }

        public aXid(int fId, long gId, long bId) throws XAException
        {
            this(fId, ByteBuffer.allocate(64).putLong(gId).array(), ByteBuffer.allocate(64).putLong(bId).array());
        }

        public int getFormatId()
        {
            return formatId;
        }

        public byte[] getGlobalTransactionId()
        {
            return gtrid;
        }

        public byte[] getBranchQualifier()
        {
            return bqual;
        }
    }

    public class XAC
    {
        private XAConnection xac;
        private XAResource xar;
        private aXid xid;
        private int status = 0;

        public XAC(XAConnection xac, XAResource xar, aXid xid)
        {
            this.xac = xac;
            this.xar = xar;
            this.xid = xid;
        }

        public void start(int flag) throws XAException
        {
            this.xar.start(this.xid, flag);
        }

        public void end(int flag) throws XAException
        {
            this.xar.end(this.xid, flag);
        }

        public int prepare() throws XAException
        {
            this.status = this.xar.prepare(this.xid);
            return this.status;
        }

        public void commit(boolean flag) throws XAException
        {
            this.xar.commit(this.xid, flag);
        }

        public void rollback() throws XAException
        {
            this.xar.rollback(this.xid);
        }

        public void close() throws SQLException
        {
            this.xac.close();

            this.xac = null;
            this.xar = null;
            this.xid = null;
        }

        public Connection getConnection() throws SQLException
        {
            return this.xac.getConnection();
        }

        public int getStatus()
        {
            return this.status;
        }
    }

    public AbstractDataSourceBean()
    {
        Locale.setDefault(Locale.US);
    }

    public void setDataSource(String dataSource)
	{
		this.dataSource = dataSource;
	}

	public void setDataSchema(String dataSchema)
	{
		if(dataSchema != null && dataSchema.length() > 0)
			this.dataSchema = dataSchema + ".";
	}

    protected List<XAC> createXACs(int count) throws Exception
    {
        UUID uuid = UUID.randomUUID();
        List<XAC> xacs = new ArrayList<>(count);

        for(int i = 0; i < count; i++)
        {
            XAConnection con = this.getXAConnection();
            XAC xac = new XAC(con, con.getXAResource(), new aXid(uuid.hashCode(), uuid.getMostSignificantBits(), UUID.randomUUID().getMostSignificantBits()));
            xacs.add(xac);
        }

        return xacs;
    }

	protected Connection getConnection() throws Exception
	{
        if(this.ds == null)
            this.ds = (DataSource) context.getBean(this.dataSource);

        return ((DataSource)this.ds).getConnection();
	}

    protected XAConnection getXAConnection() throws Exception
    {
        if(this.ds == null)
            this.ds = (XADataSource) context.getBean(this.dataSource);

        return ((XADataSource)this.ds).getXAConnection();
    }

    protected boolean isXADataSource()
    {
        return (ds instanceof XADataSource);
    }

    protected void runSQLScript(String sql, String delimiter) throws Exception
	{
		Connection con = null;
		Statement stmt = null;

		try
		{
			con = this.getConnection();
			con.setAutoCommit(false);

			stmt = con.createStatement();
			for(String cmd : sql.split(delimiter))
			{
				if(cmd.trim().length() > 0)
					stmt.execute(cmd);
			}
			con.commit();
		}
		finally
		{
			try { stmt.close(); } catch (Exception ignore) { }
			try { con.close(); } catch (Exception ignore) { }
		}
	}
}
