# Vagrantfile to launch two Ubuntu machines
VAGRANT_API_VERSION = "2"

Vagrant.configure(VAGRANT_API_VERSION) do |config|
  # Set base box for Ubuntu 22.04
  config.vm.box = "ubuntu/jammy64" # or use "ubuntu/focal64" for Ubuntu 20.04

  # Machine 1 Configuration
  config.vm.define "ubuntu-server-1" do |machine1|
    machine1.vm.hostname = "ubuntu-server-1"
    machine1.vm.network "private_network", ip: "192.168.56.10"
    machine1.vm.provider "virtualbox" do |vb|
      vb.memory = "1024"
      vb.cpus = 1
    end

    # Provisioning script to install Docker
    machine1.vm.provision "shell", inline: <<-SHELL
      # Update package list
      sudo apt-get update
      # Install dependencies
      sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common
      # Add Docker’s official GPG key
      curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
      # Add Docker repository
      sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
      # Install Docker
      sudo apt-get update
      sudo apt-get install -y docker-ce
      # Add the vagrant user to the docker group
      sudo usermod -aG docker vagrant
    SHELL
  end
end
