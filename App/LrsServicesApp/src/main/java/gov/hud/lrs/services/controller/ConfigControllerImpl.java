package gov.hud.lrs.services.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.ConfigDTO;

@Controller
public class ConfigControllerImpl {

	@Value("${lrs.devMode}")
	private String devModeFlag;

	@Value("${lrs.ui.microstrategyUrl}")
	private String microStrategyUrl;

	@Value("${lrs.ui.electronicCaseBinder}")
	private String electronicCaseBinder;

	@Value("${lrs.ui.electronicAppraisal}")
	private String electronicAppraisal;

	@Value("${lrs.ui.totalScorecardEmulator}")
	private String totalScorecardEmulator;

	@Value("${lrs.ui.totalScorecardEmulatorComparison}")
	private String totalScorecardEmulatorComparison;

	public ResponseEntity<ConfigDTO> getConfig(HttpServletRequest request, HttpServletResponse response) {
		ConfigDTO dto = new ConfigDTO();
		dto.setLrsUiDev(Boolean.valueOf(devModeFlag));
		dto.setMicrostrategyUrl(microStrategyUrl);
		dto.setElectronicCaseBinder(electronicCaseBinder);
		dto.setElectronicAppraisal(electronicAppraisal);
		dto.setTotalScorecardEmulator(totalScorecardEmulator);
		dto.setTotalScorecardEmulatorComparison(totalScorecardEmulatorComparison);

		return new ResponseEntity<ConfigDTO>(dto, HttpStatus.OK);
	}
}
