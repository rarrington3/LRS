# Contributing back to Webstart

Please be sure to follow the instructions for [setting up](./SETUP.md) this project.

All tasks and issues are tracked in [Jira](https://jira.cynergy.com/jira/browse/KPMGWF) and tagged by
one of the webstart component labels.

All changes should be made at the lowest level or on the highest upstream fork applicable. For example, if the change 
is generic and not specific to a platform, it should go to Webstart core. If it is platform specific, then it should 
go to the platform fork.

* If the issue is already in Jira, mark the task as "In Progress" by clicking the "Start Progress" action.
* Create a [new branch](/plugins/servlet/create-branch)
	* Select this repository
	* Select appropriate branch type of `bugfix` or `feature`
	* Choose branch from of `develop`
	* Provide a branch name that contains the Jira ticket number (if applicable) and short name
* Pull down your new branch
	* `git checkout <branch>`
* Perform your work as normal, commit and push to your branch often until work is complete. Make sure
to add new files to Git that you have created as people often forget this step. Try to keep commits to
a single task and add a short meaningful comment.
	* `git add -A`
	* `git commit -m"This is what I did"`
* Make sure to update [CHANGELOG.md](./CHANGELOG.md) to reflect your changes. Use the existing content for format reference.
* Make sure not to commit any unintended changes that may have been a result of running scaffolds or project initialization.
* Test your code and fix any unit test failures and eslint issues. If your change was in the scaffolds, you'll want to 
run that scaffold and ensure that the output passes all tests.
	* `grunt server:test`
* Commit any changes and push all your pending changes back to server.
	* `git commit-m"Fixed broken test"`
	* `git push`
* Submit a [new pull request](../../../pull-requests?create)
	* Select YOUR branch as the source from this repo
	* Select development branch as the destination from this repo
	* Provide a short description and select an appropriate reviewer (ie Jeff Ardilio and Robert Levy). Please also include additional 
team members as reviewers to keep a knowledge share as to promote peer reviews.
	* Submit
* Mark Jira task as "Resolved" by clicking the "Resolve" action.	

Once submitted, your PR will be reviewed and additional comments and tasks may be created. Please follow the same 
commit/test/push workflow to address any of these issues and be sure to close out the issues on the PR page that are 
complete. Once all issues are resolved and the PR is satisfactory, it will be accepted and merged.

Once the PR is accepted and merged the changes may be synched downstream to any platform or project forks.
