pipeline {
    agent any

    parameters {
        choice(
            name: 'ACTION',
            choices: ['START', 'STOP'],
            description: 'Choose whether to start or stop SmartBank'
        )
    }

    stages {

        stage('Build Images') {
            when {
                expression {
                    params.ACTION == 'START'
                }
            }
            steps {
                sh 'docker compose -f docker-compose-services.yml build'
            }
        }

        stage('Start Infrastructure') {
            when {
                expression {
                    params.ACTION == 'START'
                }
            }
            steps {
                sh 'docker compose -f docker-compose-infra.yml up -d'
            }
        }

        stage('Start Services') {
            when {
                expression {
                    params.ACTION == 'START'
                }
            }
            steps {
                sh 'docker compose -f docker-compose-services.yml up -d'
            }
        }

        stage('Health Check') {
            when {
                expression {
                    params.ACTION == 'START'
                }
            }
            steps {
                sh '''
                    echo "Checking containers..."

                    containers="redis my-zookeeper kafka nginx eureka-server api-gateway auth-service account-service transaction-service notification-service"

                    for container in $containers
                    do
                        status=$(docker inspect -f '{{.State.Status}}' $container 2>/dev/null || echo "missing")

                        echo "$container -> $status"

                        if [ "$status" != "running" ]; then
                            echo "ERROR: $container is not running!"
                            exit 1
                        fi
                    done

                    echo "All containers are running!"
                '''
            }
        }

        stage('Stop') {
            when {
                expression {
                    params.ACTION == 'STOP'
                }
            }
            steps {
                sh '''
                    docker compose -f docker-compose-services.yml down
                    docker compose -f docker-compose-infra.yml down
                '''
            }
        }

        stage('Show Running Containers') {
            steps {
                sh 'docker ps'
            }
        }
    }
}