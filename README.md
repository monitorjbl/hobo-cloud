# Hobo Cloud

Uses Docker to build simple, temporary shelters for your wandering code to execute.

## How to Run

### Starting up controller

You can run this from a Tomcat server by dropping the WAR into the servlet container.

### Add a node

To make your hobo cloud, you need some machines capable of running Docker. It doesn't matter what kind of machines these are; they can be desktops, laptops, bladdes, or even VMs as long as they run Docker. You will need to have these machines run the Docker remote API on a port visible to the Hobo Cloud.

Here's an example script that will install and configure Docker on Ubuntu 14.04:

```bash
# Install docker
apt-get install docker.io

# Expose API on port 3333
sed -i 's/#DOCKER_OPTS="-dns 8.8.8.8 -dns 8.8.4.4"/DOCKER_OPTS="-H=tcp:\/\/0.0.0.0:3333 -H=unix:\/\/\/var\/run\/docker.sock"/' /etc/default/docker.io

# Restart docker
service docker.io restart
```

Once you've got Docker running on the node, you need to add it to the Hobo Cloud. You can do this by running this script on the node (remember to change the variable values to suit this node):

```
# Hobo controller hostname or IP address
HOBO_CONTROLLER=192.168.1.87

# Hostname or IP address visible to hobo controller
HOSTNAME=192.168.1.100

# Port Docker API is exposed on
DOCKER_PORT=3333

# Maximum memory to devote to hobo containers (in bytes)
MAX_MEM=536870912

# Maximum CPU shares to devote to hobo containers
MAX_CPU=5

curl -XPUT -H "Content-Type: application/json" -d '{
  "hostname":"'$HOSTNAME'",
  "port":"'$DOCKER_PORT'",
  "maxMemory":"'$MAX_MEM'",
  "maxCpu":"'$MAX_CPU'"
}' http://$HOBO_CONTROLLER:8080/api/node
```

### Building

Windows

`gradlew war`


Linux/OSX

`./gradlew war`