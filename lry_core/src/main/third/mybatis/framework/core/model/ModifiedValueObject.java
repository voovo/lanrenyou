package mybatis.framework.core.model;

import java.util.Date;

public abstract class ModifiedValueObject implements IValueObject{
	private static final long serialVersionUID = 1L;
	
	protected Date createTime;
	protected Date updateTime;
	protected String updateIp;
	protected Integer updateUid;
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateIp() {
		return updateIp;
	}
	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}
	public Integer getUpdateUid() {
		return updateUid;
	}
	public void setUpdateUid(Integer updateUid) {
		this.updateUid = updateUid;
	}

}
