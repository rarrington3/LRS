 package gov.hud.lrs.workflow.task;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.hud.lrs.workflow.service.ReviewService;

@Component
public class SubmitReviewLevelTask implements WorkItemHandler  {

	private Logger logger = LoggerFactory.getLogger(SubmitReviewLevelTask.class);

	@Autowired private ReviewService reviewService;

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		try {
			String reviewLevelId = (String)workItem.getParameter("reviewLevelId");
			if (reviewLevelId == null) {
				throw new RuntimeException("Work item " + workItem.getId() + " (" + workItem.getName() + ") was called without required parameter 'reviewLevelId'");
			}

			String indemnificationTypeCode = (String)workItem.getParameter("indemnificationTypeCode");
			if (indemnificationTypeCode == null) {
				throw new RuntimeException("Work item " + workItem.getId() + " (" + workItem.getName() + ") was called without required parameter 'indemnificationTypeId'");
			}

			reviewService.submitReviewLevel(reviewLevelId, indemnificationTypeCode, null);

			manager.completeWorkItem(workItem.getId(), null);

		} catch (Throwable t) {
			logger.error("Exception while wrapping up work item " + workItem.getId() + " (" + workItem.getName() + ")", t);
			throw new RuntimeException(t);
		}
	}

    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
    	// nothing
    }

}
