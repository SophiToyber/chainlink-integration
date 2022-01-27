def project = 'cryptex-stage-251715'
def appName = 'wrapper-chainlink'
def branchName = 'develop'
def imageTag = "gcr.io/${project}/${appName}:${branchName}.${env.BUILD_NUMBER}"
pipeline {
  agent {
    kubernetes {
      label 'wrapper-chainlink'
      defaultContainer 'jnlp'
      yaml """
apiVersion: v1
kind: Pod
metadata:
labels:
  component: ci
spec:
  # Use service accountchainlinkGridResult that can deploy to all namespaces
  serviceAccountName: cd-jenkins
  containers:
  - name: ubuntu
    image: ubuntu:18.04
    command:
    - cat
    tty: true
  - name: gcloud
    image: gcr.io/cloud-builders/gcloud
    command:
    - cat
    tty: true
  - name: kubectl
    image: gcr.io/cloud-builders/kubectl
    command:
    - cat
    tty: true
"""
}
  }
  stages {
    stage('Test') {
      steps {
            sh 'echo test'
      }
    }
    stage('Build and push image with Container Builder') {
      steps {
      container('gcloud'){
           sh "gcloud builds submit -t ${imageTag} ."
        }
      }
    }
    stage('Deploy Production') {
      // Production branch
      steps{
        container('kubectl') {
        // Change deployed image in canary to the one we just built
          sh("sed -i.bak 's#gcr.io/cloud-solutions-images/wrapper-chainlink:1.0.0#${imageTag}#' ./k8s/production/*.yml")
          sh("kubectl apply -f k8s/services/")
          sh("kubectl apply -f k8s/production/")
        }
      }
    }
  }
}