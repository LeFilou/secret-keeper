#!/usr/bin/env groovy

pipeline {
    agent any
    environment {
        namespace = "Hermes"
        app_name = "secret-keeper"
        hash_commit = "${env.GIT_COMMIT}".substring(0, 7)
        image_version = ""
    }

    tools {
        maven 'Maven 3.5'
        jdk 'jdk11'
    }

    triggers {
        // Configure Gitlab trigger
        gitlab(
                triggerOnPush: false,
                triggerOnMergeRequest: true,
                triggerOnPipelineEvent: false,
                triggerOnAcceptedMergeRequest: false,
                triggerOnClosedMergeRequest: false
        )
    }

    stages {
        stage('print env') {
            steps {
                script {
                    sh 'printenv'
                }
            }
        }

        stage('Clean') {
            steps {
                gitlabCommitStatus(name: 'Clean') {
                    sh 'mvn clean'
                }
            }
        }

        stage('Build') {
            steps {
                gitlabCommitStatus(name: 'Build') {
                    sh 'mvn install -Dmaven.test.skip=true'
                }
            }
        }

        stage('Unit tests') {

            steps {
                gitlabCommitStatus(name: 'Unit tests') {
                    sh 'mvn test'
                }
            }

            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Initialize version') {
            steps {
                gitlabCommitStatus(name: 'Initialize version') {
                    script {
                        image_version = "${hash_commit}"
                        if (env.gitlabTargetBranch != 'master') {
                            image_version += "-SNAPSHOT"
                        }
                        echo "Image version : ${image_version}"
                    }
                }
            }
        }

    }
}