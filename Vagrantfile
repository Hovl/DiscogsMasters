ENV['VAGRANT_DEFAULT_PROVIDER'] = 'docker'
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

  config.vm.synced_folder ".", "/usr/local/discogs"

#  config.vm.define "discogs-web" do |a|
#    a.vm.provider "docker" do |d|
#      d.build_dir = "."
#      d.build_args = ["-t=discogs-web"]
#      d.ports = ["8080:8080"]
#      d.name = "discogs-web"
#      d.remains_running = true
#      d.cmd = ["java", "-cp",
#      "web/target/discogs/WEB-INF/classes:web/target/discogs/WEB-INF/lib/*",
#      "ebs.web.Boot"]
#      d.volumes = ["/usr/local/discogs"]
#      d.vagrant_vagrantfile = "./Vagrantfile.proxy"
#    end
#  end

#  config.vm.define "discogs-worker" do |a|
#    a.vm.provider "docker" do |d|
#      d.build_dir = "."
#      d.build_args = ["-t=discogs-worker"]
#      d.name = "discogs-worker"
#      d.remains_running = false
#      d.cmd = ["sh", "worker/target/bin/localDataMastersWorker"]
#      d.volumes = ["/usr/local/discogs"]
#      d.vagrant_vagrantfile = "./Vagrantfile.proxy"
#    end
#  end

  config.vm.define "discogs-scheduler" do |a|
    a.vm.provider "docker" do |d|
      d.build_dir = "."
      d.build_args = ["-t=discogs-scheduler"]
      d.name = "discogs-scheduler"
      d.remains_running = false
      d.cmd = ["sh", "worker/target/bin/mastersScheduler"]
      d.volumes = ["/usr/local/discogs"]
      d.vagrant_vagrantfile = "./Vagrantfile.proxy"
    end
  end

#  config.vm.define "redis" do |v|
#    v.vm.provider "docker" do |d|
#      d.image = "dockerfile/redis"
#      d.volumes = ["/var/docker/redis:/data"]
#      d.ports = ["6379:6379"]
#      d.vagrant_vagrantfile = "./Vagrantfile.proxy"
#    end
#  end

#  config.vm.define "postgres" do |v|
#    v.vm.provider "docker" do |d|
#      d.image = "paintedfox/postgresql"
#      d.volumes = ["/var/docker/postgresql:/data"]
#      d.ports = ["5432:5432"]
#      d.env = {
#        USER: "root",
#        PASS: "abcdEF123456",
#        DB: "root"
#      }
#      d.vagrant_vagrantfile = "./Vagrantfile.proxy"
#    end
#  end

end
