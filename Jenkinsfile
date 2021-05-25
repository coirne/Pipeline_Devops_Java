
---Version 1 ---- SCM / Build
pipeline {
 agent any
 environment {
  // This can be nexus3 or nexus2
  NEXUS_VERSION = "nexus3"
  // This can be http or https
  NEXUS_PROTOCOL = "http"
  // Where your Nexus is running. In my case:
  NEXUS_URL = "172.27.208.1:8081"
  // Repository where we will upload the artifact
  NEXUS_REPOSITORY = "maven-snapshots"
  // Jenkins credential id to authenticate to Nexus OSS
  NEXUS_CREDENTIAL_ID = "nexus-credentials"
  SONARQUBE_URL = "http://172.27.208.1"
  SONARQUBE_PORT = "9000"
 }
 options {
  skipDefaultCheckout()
 }
 stages {
  stage('SCM') {
   steps {
    checkout scm
   }
  }
  stage('Build') {
    agent {
     docker {
      image 'maven:3.6.0-jdk-8-alpine'
      args '-v /root/.m2/repository:/root/.m2/repository'
      reuseNode true
      }
     }
     steps {
      sh ' mvn clean compile'
     }
    }
 }
 }





---Version 2 ---- SCM / Build / Unit Tests
pipeline {
 agent any
 environment {
  // This can be nexus3 or nexus2
  NEXUS_VERSION = "nexus3"
  // This can be http or https
  NEXUS_PROTOCOL = "http"
  // Where your Nexus is running. In my case:
  NEXUS_URL = "172.27.208.1:8081"
  // Repository where we will upload the artifact
  NEXUS_REPOSITORY = "maven-snapshots"
  // Jenkins credential id to authenticate to Nexus OSS
  NEXUS_CREDENTIAL_ID = "nexus-credentials"
  SONARQUBE_URL = "http://172.27.208.1"
  SONARQUBE_PORT = "9000"
 }
 options {
  skipDefaultCheckout()
 }
 stages {
  stage('SCM') {
   steps {
    checkout scm
   }
  }
  stage('Build') {
    agent {
     docker {
      image 'maven:3.6.0-jdk-8-alpine'
      args '-v /root/.m2/repository:/root/.m2/repository'
      reuseNode true
      }
     }
     steps {
      sh ' mvn clean compile'
     }
    }
  stage('Unit Tests') {
   agent {
    docker {
     image 'maven:3.6.0-jdk-8-alpine'
     args '-v /root/.m2/repository:/root/.m2/repository'
     reuseNode true
    }
   }
   steps {
    sh 'mvn test'
   }
   post {
    always {
     junit 'target/surefire-reports/**/*.xml'
    }
   }
  }
 }
 }




---Version 3 ---- SCM / Build / Integration Tests 
 pipeline {
 agent any
 environment {
  // This can be nexus3 or nexus2
  NEXUS_VERSION = "nexus3"
  // This can be http or https
  NEXUS_PROTOCOL = "http"
  // Where your Nexus is running. In my case:
  NEXUS_URL = "172.27.208.1:8081"
  // Repository where we will upload the artifact
  NEXUS_REPOSITORY = "maven-snapshots"
  // Jenkins credential id to authenticate to Nexus OSS
  NEXUS_CREDENTIAL_ID = "nexus-credentials"
  SONARQUBE_URL = "http://172.27.208.1"
  SONARQUBE_PORT = "9000"
 }
 options {
  skipDefaultCheckout()
 }
 stages {
  stage('SCM') {
   steps {
    checkout scm
   }
  }
  stage('Build') {
    agent {
     docker {
      image 'maven:3.6.0-jdk-8-alpine'
      args '-v /root/.m2/repository:/root/.m2/repository'
      reuseNode true
      }
     }
     steps {
      sh ' mvn clean compile'
     }
    }

  stage('Integration Tests') {
   agent {
    docker {
     image 'maven:3.6.0-jdk-8-alpine'
     args '-v /root/.m2/repository:/root/.m2/repository'
     reuseNode true
    }
   }
   steps {
    sh 'mvn verify -Dsurefire.skip=true'
   }
   post {
    always {
     junit 'target/failsafe-reports/**/*.xml'
    }
    success {
     stash(name: 'artifact', includes: 'target/*.war')
     stash(name: 'pom', includes: 'pom.xml')
     // to add artifacts in jenkins pipeline tab (UI)
     archiveArtifacts 'target/*.war'
    }
   }
  }

 }
 }





---Version 4 ---- SCM / Build / SonarQube 
pipeline {
 agent any
 environment {
  // This can be nexus3 or nexus2
  NEXUS_VERSION = "nexus3"
  // This can be http or https
  NEXUS_PROTOCOL = "http"
  // Where your Nexus is running. In my case:
  NEXUS_URL = "172.27.208.1:8081"
  // Repository where we will upload the artifact
  NEXUS_REPOSITORY = "maven-snapshots"
  // Jenkins credential id to authenticate to Nexus OSS
  NEXUS_CREDENTIAL_ID = "nexus-credentials"
  SONARQUBE_URL = "http://172.27.208.1"
  SONARQUBE_PORT = "9000"
 }
 options {
  skipDefaultCheckout()
 }
 stages {
  stage('SCM') {
   steps {
    checkout scm
   }
  }
  stage('Build') {
    agent {
     docker {
      image 'maven:3.6.0-jdk-8-alpine'
      args '-v /root/.m2/repository:/root/.m2/repository'
      reuseNode true
      }
     }
     steps {
      sh ' mvn clean compile'
     }
    }
  
    stage('SonarQube') {
     agent {
      docker {
       image 'maven:3.6.0-jdk-8-alpine'
       args "-v /root/.m2/repository:/root/.m2/repository"
       reuseNode true
      }
     }
     steps {
      sh " mvn sonar:sonar -Dsonar.host.url=$SONARQUBE_URL:$SONARQUBE_PORT"
     }
    
 
   post {
    always {
     // using warning next gen plugin
     recordIssues aggregatingResults: true, tools: [javaDoc(), checkStyle(pattern: '**/target/checkstyle-result.xml'), findBugs(pattern: '**/target/findbugsXml.xml', useRankAsPriority: true), pmdParser(pattern: '**/target/pmd.xml')]
    }
   }
  }
 }
}
