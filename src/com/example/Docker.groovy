#!/usr/bin/env groovy

package com.example

class Docker implements serializable {

    def script

    Docker(script) {
        this.script = script
    }

    def buildImage(String imageName) {
        script.echo 'building the docker image'
            script.sh "docker build -t $imageName ."
        }

    def dockerLogin() {
        script.echo 'logging in to dockerhub'
        script.withCredentials([script.usernamePassword(credentialsId: 'my-dockerhub-credentials', usernameVariable: 'username', passwordVariable: 'password')]) {
            script.sh "echo $scriptpassword | docker login -u $script.username --password-stdin"
            }
        }

    def dockerPush(String imageName) {
        script.echo 'pushing docker image to dockerhub'
            script.sh "docker push $imageName"
        }
}