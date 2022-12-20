#!/usr/bin/env groovy

def call(String imageName) {
    echo 'building the docker image'
    withCredentials([usernamePassword(credentialsId: 'my-dockerhub-credentials', usernameVariable: 'username', passwordVariable: 'password')]) {
        sh "docker build -t $imageName ."
        sh "echo $password | docker login -u $username --password-stdin"
        sh "docker push $imageName"
    }
}