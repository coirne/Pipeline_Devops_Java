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
  SONARQUBE_URL = "http://172.17.128.1"
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
  stage('PMD') {
    agent {
    docker {
      image 'maven:3.6.0-jdk-8-alpine'
      args '-v /root/.m2/repository:/root/.m2/repository'
      reuseNode true
      }
     }
     steps {
      sh ' mvn pmd:pmd'
      // using pmd plugin
      step([$class: 'PmdPublisher', pattern: '**/target/pmd.xml'])
     }
    }
     }
    }
 }
 }
