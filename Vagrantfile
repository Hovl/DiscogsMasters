ENV['VAGRANT_DEFAULT_PROVIDER'] = 'docker'
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

  config.vm.synced_folder ".", "/usr/local/discogs"

  config.vm.define "discogs-web" do |a|
    a.vm.provider "docker" do |d|
      d.build_dir = "."
      d.build_args = ["-t=discogs-web"]
      d.ports = ["8080:8080"]
      d.name = "discogs-web"
      d.remains_running = true
      d.cmd = ["java", "-cp",
      "web/target/discogs/WEB-INF/classes:web/target/discogs/WEB-INF/lib/*",
      "ebs.web.Boot"]
      d.volumes = ["/usr/local/discogs"]
      d.vagrant_vagrantfile = "./Vagrantfile.proxy"
    end
  end

end
