//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="WORKFLOW_SERVER_INFO")
@SuppressWarnings("serial")
public class WorkflowServerInfo implements java.io.Serializable {

	private String serverId;
	private String serverName;
	private Integer serverPriority;
	private char active;
	private Date heartbeatTs;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;

	public WorkflowServerInfo() {
	}

	@GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

	@Column(name="SERVER_ID", unique=true, nullable=false, length=36)
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	@Column(name="SERVER_NAME", unique=false, nullable=false, length=64)
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Column(name="SERVER_PRIORITY", nullable=false)
	public Integer getServerPriority() {
		return serverPriority;
	}

	public void setServerPriority(Integer serverPriority) {
		this.serverPriority = serverPriority;
	}

	@Column(name="ACTIVE", nullable=false, length=1)
	public char getActive() {
		return active;
	}

	public void setActive(char active) {
		this.active = active;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="HEARTBEAT_TS", length=23)
	public Date getHeartbeatTs() {
		return heartbeatTs;
	}

	public void setHeartbeatTs(Date heartbeatTs) {
		this.heartbeatTs = heartbeatTs;
	}

	@Column(name="CREATED_BY", nullable=false, length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_TS", nullable=false, length=23)
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	@Column(name="UPDATED_BY", length=6)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_TS", length=23)
	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}
}
