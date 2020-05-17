# Continuous Integration Lab

Here we keep a collection of basic tools and services to quickly set up a CI server with one single command.

I highly encourage you to read the story behind this repository. It's available [here](STORY.md)

## Usage

Make sure you have Docker engine and Docker compose installed and present in the PATH, Copy `.env_template` to `.env` and fill in the required [fields](#configuration).

then execute the following command:

```bash
docker-compose up -d
```

### Create an Azure virtual machine image

Using the job called `UbuntuImageBuilder` you can create customized Azure virtual machine images. By default it only installs Android SDK on the image, For further configuration and adding more tools, You can try writing your own Ansible roles and place it under `./ansible`

### Add or update jobs on Jenkins

Modify the file `./jenkins/casc/jobs-seed.groovy` and use [Job DSL](https://jenkinsci.github.io/job-dsl-plugin/) to add more jobs.

### Add or update Jenkins plugins

Locate the file `plugins.txt` under `./jenkins/casc/` and add plugins in the following format:

`plugin-name:SemVer`

### Webhook delivery

We're using Smee for webhook delivery. Create a new channel on [smee.io](smee.io) and replace it with `SMEE_URL` in `.env` file, also do the same thing in you're repository's Webhook settings.

#### Example

```text
job-dsl:1.77
azure-vm-agents:1.5.0
blueocean:1.23.2
```

### Jenkins configuration as code (JCasC)

Update Jenkins configuration not from UI but within the `jenkins.yaml` located under `./jenkins/casc/` to keep track of changes.

#### Cloud configurations

Type and number of Azure virtual machine pools along with several other settings can be adjusted from `jenkins.yaml` file. You can easily add more cloud configurations in different regions and sizes

### Nginx

Nginx configuration is available at `./nginx/default.conf`, you should easily be able to do adjustments (e.g. SSL certificates, ports) in the same file.

### Configuration

|Name|Description|
|---|---|
|`GITHUB_USER`|GitHub username|
|`GITHUB_PASS`|GitHub personal access token|
|`AZURE_TENANT_ID`|Azure Service principal tenant ID |
|`AZURE_CLIENT_ID`|Azure Service principal client ID|
|`AZURE_CLIENT_SECRET`|Azure Service principal secret|
|`AZURE_SUBSCRIPTION_ID`|Azure subscription ID|
|`JENKINS_AGENT_USER`|Jenkins' agents username|
|`JENKINS_AGENT_PASS`|Jenkins' agents password|
|`JENKINS_ADMIN_USER`|Jenkins admin/default username|
|`JENKINS_ADMIN_PASS`|Jenkins admin/default password|
|`SMEE_URL`|Smee channel URL|

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
