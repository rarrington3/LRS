package gov.hud.lrs.common.dto;

@SuppressWarnings("serial")
public class MuleBinderRequestOutputDTO extends MuleResponseBase {

	private MuleEsbException esbException;

	public MuleEsbException getEsbException() {
		return esbException;
	}

	public void setEsbException(MuleEsbException esbException) {
		this.esbException = esbException;
	}

}
