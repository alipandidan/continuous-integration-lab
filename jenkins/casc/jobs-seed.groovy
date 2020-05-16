multibranchPipelineJob('TestJob') {
branchSources {
    github {
        // The id option in the Git and GitHub branch source contexts is now mandatory (JENKINS-43693).
        id('12312313') // IMPORTANT: use a constant and unique identifier
        scanCredentialsId('github-credentials')
        repoOwner('pandidan')
        repository('swedbank-exercise')
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