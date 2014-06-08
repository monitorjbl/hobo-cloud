# Hobo Cloud

Uses Docker to build simple, temporary shelters for your wandering code to execute. Very useful if you've got some compute resources laying around and some work to do that doesn't require external access. Compiling code, running batch jobs, the kind of thing you need to run somewhere, but where doesn't actually matter.

Hobo Cloud is built using Java, Hibernate, and the [Docker Java client](https://github.com/kpelykh/docker-java). The application is designed with a RESTful interface that allows you to easily interact with it from any platform. 

# Get code

You'll need Git installed on your computer. Once you've got it, run this command to get a copy of the source:

`git clone https://github.com/monitorjbl/hobo-cloud.git`

# Start hobo controller

There are two ways to do this. Pick the one that is right for you.

## Embedded (easy)

This application is built using Gradle. One of the very nice features about Gradle is that you can use it to start an embedded Jetty server, and because of the lovely Gradle wrapper, it can run anywhere! To invoke it, simply run the following command:

**Windows/Linux/OSX**

`gradlew jettyRun`

Congrats! You can now access the hobo controller on `http://localhost:8000`! To stop the server, just hit `Ctrl-c`. 

## WAR (advanced)

If you want to run this on your own servlet container (Tomcat, JBoss, etc), you certainly can. However, with the extra control you get from doing so comes more work. You can build the WAR by running the following command:

**Windows/Linux/OSX**

`gradlew war`

Once you've done so, the WAR will be available in the `build/libs` directory. By default, it will be called something like `hobo-cloud-1.0.war`. You can take this WAR and drop into the webapp directory of your servlet container. Feel free to rename it as you do so; the default name can be a bit of a mouthful.

# Add a node

## Install Docker

To make your hobo cloud, you need some machines capable of running Docker. It doesn't matter what kind of machines these are; they can be desktops, laptops, bladdes, or even VMs as long as they run Docker. You will need to have these machines run the Docker remote API on a port visible to the hobo controller.

Here's an example script that will install and configure Docker on Ubuntu 14.04:

```bash
# Install docker
apt-get install docker.io

# Expose API on port 3333
sed -i 's/#DOCKER_OPTS="-dns 8.8.8.8 -dns 8.8.4.4"/DOCKER_OPTS="-H=tcp:\/\/0.0.0.0:3333 -H=unix:\/\/\/var\/run\/docker.sock"/' /etc/default/docker.io

# Restart docker
service docker.io restart
```

## Add node to Hobo Cloud

Once you've got Docker running on the node, you need to add it to the Hobo Cloud. You can do this in two ways: with the web UI or with the REST interface.

**Web UI**

Once you've started the hobo controller, you can add nodes by going to `http://localhost:8000/#/node` and clicking the lovely green '+' button.

**REST**

Run this script anywhere that has cURL installed (the node itself probably will):

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

# Start a container

Once you've got at least one node in the system, you can start a container. A Docker container can  be defined from a Dockerfile and then referenced by name later on. Since your container can be running anywhere, you'll probably want to define it this way. However, if you configure your Docker nodes to use a personal repository (or if you have a container in the public repo), you can simply refer to the containers repo/tag identifier.

**Web UI**

Go to `http://localhost:8000/#/container` and click the green '+' button. A popup will appear with fields for you to fill in. Remember that while the Dockerfile section is optional, the repository and tag you refer to must be visible to the nodes if you leave it blank. 

**REST**

Run this script anywhere that has cURL installed:

```
# Hobo controller hostname or IP address
HOBO_CONTROLLER=192.168.1.87

# Required. Container to start up.
REPOSITORY=mycontainer

# Required. Tag in this repository to use.
TAG=latest

# Optional. Dockerfile to use to build a container.
BUILD='
FROM     ubuntu

RUN echo "deb http://archive.ubuntu.com/ubuntu precise main universe" > /etc/apt/sources.list
RUN apt-get update

RUN apt-get install -y openssh-server
RUN mkdir /var/run/sshd
RUN echo 'root:screencast' | chpasswd

EXPOSE 22
CMD    /usr/sbin/sshd -D'

# Optional: Maximum memory to devote to hobo containers (in bytes). Defaults to 256m.
MEM=536870912

# Optional: Maximum CPU shares to devote to hobo containers. Defaults to 1.
CPU=1

# Optional. Maximum tome to keep this container running. Defaults to 60 seconds
EXPIRY=120000

curl -XPUT -H "Content-Type: application/json" -d '{
  "repository":"'$REPOSITORY'",
  "tag":"'$TAG'",
  "build":"'$BUILD'",
  "memory":"'$MEM'",
  "cpu":"'$CPU'",
  "expiry":"'$EXPIRY'"
}' http://$HOBO_CONTROLLER:8080/api/node
```

# Configuring

The configuration options and their default values can be reviewed [here](src/main/resources/defaults.conf). To override these, simply pass in your own config file as a JVM arg.

