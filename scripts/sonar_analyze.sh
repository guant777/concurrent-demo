#!/bin/sh

mvn clean sonar:sonar \
    -Dsonar.host.url=http://sonar.ziroom.com:8080 \
    -Dsonar.login=bd6b798d6c9ccc4fb1b2e78e8623748fa7244f31 \
    -Dsonar.analysis.mode=preview \
    -Dsonar.gitlab.commit_sha=$CI_COMMIT_SHA \
    -Dsonar.gitlab.unique_issue_per_inline=true \
    -Dsonar.gitlab.project_id=$CI_PROJECT_ID \
    -Dsonar.gitlab.ref_name=$CI_COMMIT_REF_NAME\
    -Dsonar.java.binaries=target

if [ $? -eq 0 ]; then
    echo "sonarqube code-analyze-preview over."
fi
