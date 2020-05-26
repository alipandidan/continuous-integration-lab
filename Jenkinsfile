pipeline {
    agent any

    stages {
        stage ('Build Image') {
            steps {   
                sh 'whomai'
            }
            post {
                always {
                    cleanWs()
                }
            }
        }
    }
}