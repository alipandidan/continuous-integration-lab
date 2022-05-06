multibranchPipelineJob('makefile-example') {
    branchSources {
        github {
            id('12312313') // IMPORTANT: use a constant and unique identifier
            scanCredentialsId('github-credentials')
            repoOwner('alipandidan')
            repository('makefile-example')
        }
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(10)
        }
    }
    // triggers {
    //     periodic(5)
    // }
}

pipelineJob('UbuntuImageBuilder') {
    parameters {
        stringParam('imageName', null, 'Image name to be created')
    }
    definition {
    cpsScm {
        scm {
        git {
            remote {
                github('alipandidan/continuous-integration-lab')
                credentials('github-credentials')
            }
            branches('ea')
            scriptPath('image-builder/Jenkinsfile')
        }
        }
    }
    }
}