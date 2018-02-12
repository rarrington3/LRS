# Setting up your Environment

You can choose to use Vagrant or manual setup process to run the project on your local machine:

## Using Vagrant Environment

Note that when using the Vagrant Environment, the server tasks listed later in this document will not spawn
the browser automatically on your host machine, you will see a warning. You must open those URL's in the browser
on your host manually.

* Make sure you have Vagrant installed from https://www.vagrantup.com/downloads.html
* Run the following commands:

```
    cd /path/to/project
    vagrant up
    vagrant ssh
```

That's it, your environment will be setup automatically and when you connect via ssh, it will automatically startup
in your project directory and ensure that node and bower dependencies are up to date. This will also run the default
grunt task (see more on Grunt tasks below).

Please note that there is currently a known issue with NPM when run on a shared device such as that used between
Vagrant and the host. See [here](https://github.com/npm/npm/issues/9633) for more information about this issue.

## Manually Setup Environment

Make sure you have the following prerequisites installed:

* Install NVM to manage multiple instances of NodeJS
    * OSX/Linux - https://github.com/creationix/nvm
    * Windows - https://github.com/coreybutler/nvm-windows
* Windows users must also install msysgit from https://github.com/msysgit/msysgit/releases/
    * Download the "full installer for official Git for Windows" version
    * During installation, choose the option "Run Git from the Windows Command Prompt"
    * Restart your machine
* (Optional) Add `./node_modules/.bin` to your PATH variable before all other paths

Now, navigate to your project directory in your terminal and issue the following command. This will read your local 
`.nvmrc` file for the expected version of NodeJS required and ensure its locally installed and set as default.

```
	nvm install && nvm use
```

* First time setup of global dependencies on your machine (if you didn't add `./node_modules/.bin` to your PATH):

```
    npm install grunt-cli -g
    npm install bower -g
```

* Finally, setup your local dependencies for the project and run the default grunt task:

```
    npm install && bower install
    grunt
```

If you run into an error during `npm install`, this could be permissions based. See http://stackoverflow.com/questions/16151018/npm-throws-error-without-sudo
for more information about how to fix npm permissions on your machine. You will need to run `npm install` again after fixing
this.
