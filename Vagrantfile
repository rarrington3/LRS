Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/trusty64"

  config.vm.provider "virtualbox" do |v|
    v.memory = 2048
    v.cpus = 2
  end

  # Virtualbox does not support MMap, which kills all C/C++ installs
  # such as SQLite and Levelup.  Workaround here:
  # This only works with admin access on host
  # config.vm.synced_folder ".", "/vagrant", type: "nfs"
  # config.vm.network "private_network", type: "dhcp"

  # Forward all the local ports that project will use back to the host
  config.vm.network "forwarded_port", guest: 8080, host: 8080
  config.vm.network "forwarded_port", guest: 9000, host: 9000
  config.vm.network "forwarded_port", guest: 9001, host: 9001
  config.vm.network "forwarded_port", guest: 9002, host: 9002
  config.vm.network "forwarded_port", guest: 9003, host: 9003
  config.vm.network "forwarded_port", guest: 9004, host: 9004
  config.vm.network "forwarded_port", guest: 9005, host: 9005
  config.vm.network "forwarded_port", guest: 8000, host: 8000
  config.vm.network "forwarded_port", guest: 8001, host: 8001
  config.vm.network "forwarded_port", guest: 8002, host: 8002
  config.vm.network "forwarded_port", guest: 8003, host: 8003
  config.vm.network "forwarded_port", guest: 8004, host: 8004
  config.vm.network "forwarded_port", guest: 8005, host: 8005

  # https://www.exratione.com/2013/12/angularjs-headless-end-to-end-testing-with-protractor-and-selenium/
  config.vm.provision "shell", inline: "sudo wget -q https://raw.githubusercontent.com/gmonfort/xvfb-init/master/Xvfb -O /etc/init.d/Xvfb"
  config.vm.provision "shell", inline: "sudo chown root:root /etc/init.d/Xvfb"
  config.vm.provision "shell", inline: "sudo chmod a+x /etc/init.d/Xvfb"
  config.vm.provision "shell", inline: "sudo update-rc.d Xvfb defaults"
  config.vm.provision "shell", inline: "wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add -"
  config.vm.provision "shell", inline: "echo 'deb http://dl.google.com/linux/chrome/deb/ stable main' >> /etc/apt/sources.list"
  config.vm.provision "shell", inline: "sudo apt-get update"
  config.vm.provision "shell", inline: "sudo apt-get install -y xvfb"
  config.vm.provision "shell", inline: "sudo apt-get install -y x11-xkb-utils xfonts-100dpi xfonts-75dpi"
  config.vm.provision "shell", inline: "sudo apt-get install -y xfonts-scalable xserver-xorg-core"
  config.vm.provision "shell", inline: "sudo apt-get install -y dbus-x11"
  config.vm.provision "shell", inline: "sudo apt-get install -y google-chrome-stable firefox"
  config.vm.provision "shell", inline: "sudo apt-get install -y imagemagick"
  config.vm.provision "shell", inline: "sudo apt-get install -y default-jdk"
  config.vm.provision "shell", inline: "sudo echo 'XVFB_ARGS=\":10 -extension RANDR -noreset -ac -screen 10 1024x768x16\"' >> /etc/default/Xvfb"
  config.vm.provision "shell", inline: "sudo echo 'export DISPLAY=:10' >> /home/vagrant/.profile"
  config.vm.provision "shell", inline: "sudo /etc/init.d/Xvfb start"

  # Grab any of our global dependencies installed at the OS layer
  config.vm.provision "shell", inline: "wget -qO- https://raw.githubusercontent.com/creationix/nvm/v0.31.0/install.sh | bash", privileged: false
  config.vm.provision "shell", inline: "sudo apt-get update"
  config.vm.provision "shell", inline: "sudo apt-get install -y git"
  config.vm.provision "shell", inline: "sudo apt-get install -y curl"
  config.vm.provision "shell", inline: "sudo apt-get install -y fontconfig"

  # Setup ulimit to avoid issues with modules that have lots of files
  config.vm.provision "shell", inline: "ulimit -n 1024"

  # Fix issue with some networks blocking the git protocol, use https instead
  config.vm.provision "shell", inline: "git config --global url.\"https://\".insteadOf git://", privileged: false

  # http://stackoverflow.com/questions/6842687/the-remote-end-hung-up-unexpectedly-while-git-cloning
  config.vm.provision "shell", inline: "git config --global http.postBuffer 1048576000", privileged: false

  # Make sure on ssh connection we dump user to the project directory which is aliased as 'vagrant'
  config.vm.provision "shell", inline: "echo 'cd /vagrant' >> /home/vagrant/.bashrc"

  # Add the local node executables to the path so we can execute them as if they are global
  config.vm.provision "shell", inline: "echo 'PATH=$PATH:./node_modules/.bin' >> /home/vagrant/.bashrc"

  # Update our project dependencies only if they appear to be out of date
  config.vm.provision "shell", inline: "echo 'nvm install && nvm use' >> /home/vagrant/.bashrc"
  config.vm.provision "shell", inline: "echo 'npm install && bower install' >> /home/vagrant/.bashrc"
end
