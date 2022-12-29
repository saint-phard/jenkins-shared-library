#!/usr/bin/env groovy

package com.example

class Docker implements serializable {

    def script

    Docker(script) {
        this.script = script
    }

    def buildImage(string imageName) {
        script.echo 'building the docker image'
        script.withCredentials([script.usernamePassword(credentialsId: 'my-dockerhub-credentials', usernameVariable: 'username', passwordVariable: 'password')]) {
            script.sh "docker build -t $imageName ."
            script.sh "echo $scriptpassword | docker login -u $script.username --password-stdin"
            script.sh "docker push $imageName"
    }
    }
}