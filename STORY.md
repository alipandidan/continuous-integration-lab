# Jenkins and friends

Here we demonstrate a Jenkins setup utilizing configuration as a code (CasC),
 a bit of JobDSL, Packer for creating VM templates, Ansible as the main provisioner for VM templates, Docker Compose for container orchestration, Docker itself for isolation of builds, build agents on Azure, Groovy scrips, Nginx reverse proxy, smee webhook delivery service and GitHub CI checks.

## The Story

### Docker Compose and Nginx

Everything starts from the Docker Compose file, There we try to set up a single Jenkins instance with Nginx in front as a reverse proxy. Everything is set up using Docker containers with the help of Docker Compose for orchestration. We also have a smee container that we'll come back to it later.

### Jenkins plugin maintainability

As one of the very beginning steps we started with enabling plugin versions to be maintained in the code and that of course brings proper version history, tracking, review capability, and reproducibility. To be able to achieve that it was needed to get a dump of current plugins and versions to be able to feed them to Jenkins' docker container.

Note: Plugins can not be managed by Jenkins CasC as of now.

### Jenkins configuration as a code

Moving forward we've enabled and configured CasC plugin with initial configuration loaded into Jenkins. The path to read the YAML configuration is passed to Jenkins as `CASC_JENKINS_CONFIG` environment variable and the configuration is mounted to the docker container.

### Job DSL

Our purpose is to be able to solely rely on the code for our setup, therefore we've used JobDSL plugin for seeding a couple of test jobs.


### Jenkins credential management

Next, Github credentials were added to `jenkins.yaml` while not keeping the plain text secret in the code rather in a `.env` file which is git ignored.
Probably not the most secure way, it's just there for the sake of demonstration.

### Autoscaled Azure cloud

So far we have a Jenkins instance configured from code, It's time to have some build agents. We're going to utilize our beloved Azure with the help of `azure-vm-agents` plugins. Our goal is to have auto-provisioned and auto-decommissioned builds agents Mainly for three reasons:

- Scalability
- Cost-effectiveness
- Less dirty build environment

After creating an "Azure service principal" to use for plugin authentication and a resource group, We've added the cloud configuration to `jenkins.yaml` with `maxVirtualMachinesLimit` of 10 machines and `idleTerminationMinutes` set to 10 minutes.

Our cloud is configured to install Docker engine on the agents upon creation so we can actually use Docker for our builds! later we'll demonstrate how we build and use the docker images in our pipeline.

![Azure Cloud](https://i.imgur.com/uXUxljW.png)

### Packer and Azure VM image creation

Right now we're using Azure's default images but we probably want to have our custom images too!, so we've created a Jenkins pipeline (`image-builder`) to execute Packer fed with an Ubuntu JSON template.

### Ansible Automation

In Packer we use Ansible as the main provisioner for the image and there I used one of my recently developed Ansible roles to add [Android SDK](https://github.com/pandidan/ansible-android-sdk) to the image. The role is cross-platform, so it can be used with Linux, Windows, and macOS machines. One probably does more than just installing one role.

### Docker

To be able to build the image we need packer and ansible executables. We've created a Dockerfile with instructions to have them installed. We build that Dockerfile in one of the steps in the pipeline and use it right away for packer image creation.
It is indeed not a good practice to always build a time-consuming docker image. It is possible to make it a one-time thing by pushing it to a docker registry. While keeping the Dockerfile in the repo, we'll make it build once there's a change to that file.

### Groovy

Groovy scripts are used everywhere, Either in the Jenkins shared library attached to our Jenkins or the one (`enable-security.groovy`) that utilizes Groovy extension of CasC plugin to create an admin user and enable a security realm.

### Smee webhook delivery service

It's time to start integrating Jenkins and Github, and since our Jenkins is not accessible from GitHub we need some sort of a proxy to receive webhook requests. Here we use [smee](smee.io)

That'll help our jobs to get triggered when there's a change (e.g. commits or branches)

![Smee webhook delivery](https://i.imgur.com/Pr7IbrA.png)

### Github CI checks

Github CI checks are in place after integrating Github and Jenkins, Although it's not possible to have branch protection in free version.

![CI Checks failed](https://i.imgur.com/pekUJvB.png)

More photos are available [here](https://imgur.com/a/NCTnkKB)