pipeline {
    agent {
        node {
            label 'ubuntu'
            customWorkspace 'ci_check'
        }
    }

    stages {
        stage ('Build Image') {
            steps {   
                sh 'date'
            }
            post {
                always {
                    cleanWs()
                }
            }
        }
    }
}