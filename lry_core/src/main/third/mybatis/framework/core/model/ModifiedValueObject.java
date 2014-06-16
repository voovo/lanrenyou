/**
 * 
 */
package mybatis.framework.core.model;

import java.util.Date;

/**

 */
public abstract class ModifiedValueObject implements IValueObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Date createDate;
	protected Date modifyDate;
	protected String modifyIp;
	protected Long modifyUserId;
	
	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public Date getModifyDate()
	{
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate)
	{
		this.modifyDate = modifyDate;
	}

	public String getModifyIp()
	{
		return modifyIp;
	}

	public void setModifyIp(String modifyIp)
	{
		this.modifyIp = modifyIp;
	}

	public Long getModifyUserId()
	{
		return modifyUserId;
	}

	public void setModifyUserId(Long modifyUserID)
	{
		this.modifyUserId = modifyUserID;
	}

}
