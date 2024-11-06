# Use the official Jenkins LTS image
FROM jenkins/jenkins:lts

# Optionally set the user to root to install additional tools or plugins
USER root

# Install prerequisites and Docker CLI
RUN apt-get update && \
    apt-get install -y \
        ca-certificates \
        curl \
        gnupg \
        lsb-release && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add - && \
    echo "deb [arch=$(dpkg --print-architecture)] https://download.docker.com/linux/debian $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list && \
    apt-get update && \
    apt-get install -y docker-ce-cli && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copy the plugins.txt file to the container
# COPY plugins.txt /usr/share/jenkins/plugins.txt

# Install the plugins listed in the plugins.txt file
# RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/plugins.txt

# Expose the Jenkins port
EXPOSE 8080

# Switch back to the jenkins user
USER jenkins
