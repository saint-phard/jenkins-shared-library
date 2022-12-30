#!/usr/bin/env groovy

import com.example.Docker

def call() {
    echo "building the application with maven for $BRANCH_NAME branch"
    sh 'mvn clean package'
}