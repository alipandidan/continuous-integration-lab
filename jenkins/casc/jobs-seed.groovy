multibranchPipelineJob('TestJob') {
branchSources {
    github {
        // The id option in the Git and GitHub branch source contexts is now mandatory (JENKINS-43693).
        id('12312313') // IMPORTANT: use a constant and unique identifier
        scanCredentialsId('github-credentials')
        repoOwner('pandidan')
        repository('continuous-integration-lab')
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
                github('pandidan/continuous-integration-lab')
                credentials('github-credentials')
            }
            branches('master')
            scriptPath('image-builder/Jenkinsfile')
            extensions { }  // required as otherwise it may try to tag the repo
        }
        }
    }
    }
}