pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "leorahuldas/intrabank"
        DOCKER_TAG = "${BUILD_NUMBER}"
    }

    tools {
        maven 'mvn_creatte'
    }

    stages {

        stage('Clone Code') {
            steps {
                git branch: 'master', url: 'https://github.com/LeoninRahulDas/Intrabank.git'
            }
        }

        stage('Build with Maven') {
            steps {
                dir('user_service') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('user_service') {
                    sh """
                    docker build -t $DOCKER_IMAGE:$DOCKER_TAG .
                    docker tag $DOCKER_IMAGE:$DOCKER_TAG $DOCKER_IMAGE:latest
                    """
                }
            }
        }

        stage('Login to DockerHub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker_cred',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                    '''
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                sh """
                docker push $DOCKER_IMAGE:$DOCKER_TAG
                docker push $DOCKER_IMAGE:latest
                """
            }
        }
    }

    post {
        success {
            echo 'Build & Push Successful!'
        }
        failure {
            echo 'Build Failed!'
        }
    }
}
