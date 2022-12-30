#!/usr/bin/env groovy

package com.example

class Docker implements Serializable {

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
            script.sh "echo $script.password | docker login -u $script.username --password-stdin"
            }
        }

    def dockerPush(String imageName) {
        script.echo 'pushing docker image to dockerhub'
            script.sh "docker push $imageName"
        }

    def githubCommit() {
        script.echo 'commiting all changes to github to reflect new version changes'
        script.withCredentials([script.usernamePassword(credentialsId: 'my-github-credentials', usernameVariable: 'username', passwordVariable: 'password')]) {
            script.sh 'git config ---global user.name "jenkins-user"'
            script.sh 'git config ---global user.email "jenkins-user@local.com"'

            script.sh 'git status'
            script.sh 'git branch'
            script.sh 'git config --list'

            script.sh "git remote set-url origin https://$script.username:$script.password@https://github.com/saint-phard/maven-docker-jenkins.git"
            script.sh 'git add .'
            script.sh 'git commit -m "jenkins-build: app version increment"'
            script.sh 'git push origin HEAD:jenkins-shared-library'
            }
        }
}