package gov.hud.lrs.workflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.hud.lrs.workflow.service.BinderService;

@RestController
@RequestMapping("/api/v1/binders")
public class BinderController {

	@Autowired BinderService binderService;

	@RequestMapping(value = "/{binderId}/receive", method = RequestMethod.PUT)
	public void receiveBinder(@PathVariable("binderId") String binderId) {
		binderService.receiveBinder(binderId, null);
	}

}
