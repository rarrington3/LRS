//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SYSTEM_PARAMETERS")
@SuppressWarnings("serial")
public class SystemParameters implements java.io.Serializable {
	private String parameterName;
	private Integer parameterValue;

	public SystemParameters() {
	}

    @Id 
    
    @Column(name="PARAMETER_NAME", unique=true, nullable=false, length=100)
	public String getParameterName() {
		return this.parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

    
    @Column(name="PARAMETER_VALUE")
	public Integer getParameterValue() {
		return this.parameterValue;
	}

	public void setParameterValue(Integer parameterValue) {
		this.parameterValue = parameterValue;
	}

}
