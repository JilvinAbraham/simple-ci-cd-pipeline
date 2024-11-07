# Automated CI/CD Pipeline with Jenkins, Vagrant, Ngrok, and Watchtower
A simple automated ci cd pipeline using Jenkins and other tools

![](diagrams/pipeline.png)

This project sets up a simple CI/CD pipeline to automate the deployment of a web application. The pipeline leverages **Jenkins** for continuous integration, **Vagrant** for managing development environments, **Ngrok** to expose local Jenkins as a publicly accessible URL, and **Watchtower** to automatically update running Docker containers when new images are pushed to Docker Hub.

## Project Overview

1. **Vagrant**: Sets up a local environment with multiple web servers to simulate a production environment.
2. **Jenkins**: Automates the build process, creating a Docker image of the application, and pushes it to Docker Hub.
3. **Ngrok**: Provides a public URL for the Jenkins server, allowing GitHub webhooks to trigger Jenkins builds on code changes.
4. **Watchtower**: Monitors Docker Hub for updated images and automatically pulls the latest version, updating the running containers.

## Prerequisites

- [Vagrant](https://www.vagrantup.com/downloads)
- [VirtualBox](https://www.virtualbox.org/) (or any other supported provider)
- [Docker](https://www.docker.com/get-started)
- [Jenkins](https://www.jenkins.io/download/)
- [Ngrok](https://ngrok.com/download)
- GitHub account (with a [Personal Access Token](https://github.com/settings/tokens) if using private repositories)

## Project Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd <repository-name>
```

### 2. Configure Vagrant

The `Vagrantfile` included in this repository will set up a dummy web server environment. Customize it as needed.

- To start the Vagrant environment:
  ```bash
  vagrant up
  ```
- This will create and configure virtual machines with web servers along with docker.

### 3. Set Up Jenkins

1. **Docker Compose**: Run the docker-compose up command
   - Installs the required plugins
   - Creates a seed job which will be used to create other desired job
   - Run the seed job to build your piepline job

2. **Add Docker Hub Credentials** in Jenkins:
   - Go to **Manage Jenkins** > **Manage Credentials**.
   - Already a credential with id docker-hub would be present update with desired docker hub creds.

### 4. Run Ngrok

To expose Jenkins to the internet for GitHub webhooks:

```bash
ngrok http 8080
```

Note the public URL Ngrok provides (e.g., `https://<random_subdomain>.ngrok.io`).

### 5. Set Up GitHub Webhook

1. Go to your GitHub repository **Settings** > **Webhooks**.
2. Add a new webhook with the following:
   - **Payload URL**: `<Ngrok_URL>/github-webhook/` (replace `<Ngrok_URL>` with the URL Ngrok generated)
   - **Content type**: `application/json`
   - **Event**: `Just the push event`

### 6. Deploy and Monitor with Watchtower

Watchtower will ensure the application is automatically updated when changes are pushed to Docker Hub.

1. Start Watchtower on your vagrant server(s) where the application is deployed:
   ```bash
   docker run -d \
     --name watchtower \
     -v /var/run/docker.sock:/var/run/docker.sock \
     containrrr/watchtower \
     --interval 30
   ```
   - This will check for updates every 30 seconds and automatically pull the latest image if there is a change.

## Workflow

1. **Code Changes**: Push code changes to the GitHub repository.
2. **Webhook Trigger**: The GitHub webhook sends a payload to Jenkins via Ngrok.
3. **Build and Push**: Jenkins builds the Docker image and pushes it to Docker Hub.
4. **Automatic Deployment**: Watchtower detects the updated image and automatically redeploys the container with the latest version.