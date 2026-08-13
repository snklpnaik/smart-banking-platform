pipeline {
    agent any

    stages {

        stage('Build Images') {
            steps {
                sh 'docker compose -f docker-compose-services.yml build'
            }
        }

        stage('Start Infrastructure') {
            steps {
                sh 'docker compose -f docker-compose-infra.yml up -d'
            }
        }

        stage('Start Services') {
            steps {
                sh 'docker compose -f docker-compose-services.yml up -d'
            }
        }

        stage('Show Running Containers') {
            steps {
                sh 'docker ps'
            }
        }
    }

    post {
        always {
            sh 'docker ps'
        }
    }
}