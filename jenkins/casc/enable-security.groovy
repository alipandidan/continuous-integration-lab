// https://github.com/thbkrkr/jks/blob/master/init.groovy.d/1-create-admin-user.groovy

import jenkins.model.*
import hudson.security.*

def adminUsername = System.getenv("JENKINS_ADMIN_USER")
def adminPassword = System.getenv("JENKINS_ADMIN_PASS")
assert adminPassword != null : "No JENKINS_ADMIN_USER env var provided, but required"
assert adminPassword != null : "No JENKINS_ADMIN_PASS env var provided, but required"

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount(adminUsername, adminPassword)
Jenkins.instance.setSecurityRealm(hudsonRealm)
def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(false)
Jenkins.instance.setAuthorizationStrategy(strategy)

Jenkins.instance.save()
