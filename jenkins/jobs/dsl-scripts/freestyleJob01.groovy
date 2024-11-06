job('simple-ci-cd') {
    description('A simple job to pull from GitHub and run shell commands')

    scm {
        git {
            remote {
                url('https://github.com/JilvinAbraham/simple-ci-cd-pipeline') // Replace with your GitHub repo URL
            }
            branch('main') // Replace with the branch you want to pull
        }
    }

    // Define build environment variables using credentials
    wrappers {
        credentialsBinding {
            usernamePassword('DOCKER_USERNAME', 'DOCKER_PASSWORD', 'docker-hub-id') // Replace 'github-credentials-id' with the actual credentials ID
        }
    }

    steps {
        shell('''
            cd app/
            echo "$DOCKER_USERNAME"
            docker build -t "fastapi-image" .
            docker tag "fastapi-image" "29061999jilvin/fastapi-image:latest"
            echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
            docker push "29061999jilvin/fastapi-image:latest"
            echo "Image is Pushed to Hub"
        ''')
    }
}
