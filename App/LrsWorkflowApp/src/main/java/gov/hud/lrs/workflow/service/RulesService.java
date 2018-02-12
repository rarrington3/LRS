package gov.hud.lrs.workflow.service;

import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RulesService {

	@Autowired private KieContainer kieContainer;

	public void fireRules(List<? extends Object> parameters) {
		StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession();
		statelessKieSession.execute(parameters);
	}

}
