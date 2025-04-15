pipeline {
    // what is the environment that we are going to run this pipeline. ex: windows, linux, mac
    agent any

    stages {
        stage('SCM Checkout') {
            steps {
                retry(3) {
                    git branch: 'main', url: 'https://github.com/sandakalummaleesha99/rempms-candidate-frontend.git'
                }
            }
        }
    //     stage('Build Docker Image') {
    //         steps {
    //             bat 'docker build -t maleeshasa/rempms-candidate-fe:%BUILD_NUMBER% .'
    //         }
    //     }
    //     stage('Login to Docker Hub') {
    //         steps {
    //             withCredentials([string(credentialsId: 'rempmsfe-test-password', variable: 'rempmsfe-test-password')]) {
    //                 script {
    //                     bat "docker login -u maleeshasa -p ${rempmsfe-test-password}"
    //                 }
    //             }
    //         }
    //     }
    // }
    //     stage('Push Image') {
    //         steps {
    //             bat 'docker push maleeshasa/rempms-candidate-fe:%BUILD_NUMBER%'
    //         }
    //     }
}
    post {
        always {
            bat 'docker logout'
        }
    }
