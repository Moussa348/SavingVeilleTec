pipeline{
    agent any
    
   stages {
    stage('maven install') {
      steps {
        withMaven(maven: 'Maven3') {
         sh 'mvn -f /SpendingControl/pom.xml clean install'
        }
      }
    }

  }
}
