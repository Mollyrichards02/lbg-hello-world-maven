pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                script {
                    echo "Building the project on branch: ${env.BRANCH_NAME}"
                    sh './mvnw clean install'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    echo "Running tests on branch: ${env.BRANCH_NAME}"
                    sh './mvnw test'
                }
            }
        }
        stage('Run') {
            steps {
                script {
                    echo "Running the application on branch: ${env.BRANCH_NAME}"
                    sh './mvnw spring-boot:run &'
                    sleep 10  // Wait for the application to start
                }
            }
        }
    }
    post {
        success {
            echo "Build succeeded on branch: ${env.BRANCH_NAME}"
        }
        failure {
            echo "Build failed on branch: ${env.BRANCH_NAME}"
        }
    }
}
