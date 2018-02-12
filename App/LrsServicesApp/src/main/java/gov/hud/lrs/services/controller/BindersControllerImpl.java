package gov.hud.lrs.services.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.BinderDTO;
import gov.hud.lrs.common.entity.BinderRequest;
import gov.hud.lrs.services.bizservice.BinderService;

@Controller
public class BindersControllerImpl {

	@Autowired private BinderService binderService;

	public ResponseEntity<List<BinderDTO>> getBinderFha(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<List<BinderDTO>>(binderService.getFhaBinderDTOs(), HttpStatus.OK);
	}

	public ResponseEntity<List<BinderDTO>> getBinderLender(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<List<BinderDTO>>(binderService.getLenderBinderDTOs(), HttpStatus.OK);
	}

	public ResponseEntity<BinderDTO> putBinderFhaReceive(String binderRequestId, HttpServletRequest request, HttpServletResponse response) {
		BinderRequest binder = binderService.receiveBinder(binderRequestId);
		return new ResponseEntity<BinderDTO>(binderService.convertBinderRequestToBinderDTO(binder, true), HttpStatus.OK);
	}

	public ResponseEntity<BinderDTO> putBinderLenderSend(String binderRequestId, BinderDTO binderDTO, HttpServletRequest request, HttpServletResponse response) {
		BinderRequest binder = binderService.sendBinder(binderRequestId, binderDTO);
		return new ResponseEntity<BinderDTO>(binderService.convertBinderRequestToBinderDTO(binder, false), HttpStatus.OK);
	}

}
