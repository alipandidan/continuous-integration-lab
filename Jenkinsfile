pipeline {
    agent any

    stages {
        stage ('Build Image') {
            steps {   
                sh 'ls'
            }
            post {
                always {
                    cleanWs()
                }
            }
        }
    }
}