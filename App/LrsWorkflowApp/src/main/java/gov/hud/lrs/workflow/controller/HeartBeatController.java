package gov.hud.lrs.workflow.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HeartBeatController {
	
	@RequestMapping(value = "/api/v1/unprotected/heartbeat", method = RequestMethod.GET)
	public ResponseEntity<Void> checkWorkflow(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
