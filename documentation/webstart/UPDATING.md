# Updating your Fork

All platform and project forks should have an upstream base that they can pull changes from. Your project fork will have
an upstream platform fork and platform forks will have Webstart core upstream. As new feature enhancements and bug fixes
are added to the core or platform forks, we want those changes to be propogated back downstream.

In Stash, most forks have "Automatic Synchronization" activated. This will allow forks to automatically suck in the
latest upstream changes with little effort. Usually you will see a "Synchronize" button appear on the Stash page
for your fork when there are pending changes. If there are no conflicts it will simply update the project automatically
when clicked. If there are conflicts, you will get some instructions on how to proceed manually.

If the "Automatic Synchronization" is not available (disabled or you are not running your project on Stash) or it has
failed due to conflicts, you can always pull in the changes manually into your project fork:

* Add a new git upstream remote repo to your project fork (if you haven't done so already). For example, 
if "webstart-angular" is your upstream:
```
git remote add webstart-angular https://stash.cynergy.com/scm/kpmgwf/webstart-angular.git
```
* Now create a new branch to do your upgrade in safely:
```
git branch feature/webstart-update
git checkout feature/webstart-update
```
* Now pull the latest changes from webstart-angular's develop into your branch:
```
git pull webstart-angular develop
```
* Resolve any conflicts and then commit and push your changes
```
git commit-m"updated to latest webstart changes"
git push
```
* Finally, merge your new `feature/webstart-update` branch into your development or master branch as you would any other
change, likely via a pull request.
