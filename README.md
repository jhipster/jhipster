JHipster BOM and server-side library
----------------------------

[![Azure DevOps Build Status][azure-devops-image]][azure-devops-url-main] [![Build Status][travis-image]][travis-url] [![Maven Central][maven-image]][maven-url]

Full documentation and information is available on our website at [https://www.jhipster.tech/][jhipster-url]

This project is used by the JHipster generator. This is the Bill of Materials and server-side library:
- jhipster-dependencies
- jhipster-framework

If the current version is SNAPSHOT then to use this SNAPSHOT version:
- clone this project
- run `./mvnw clean install -Dgpg.skip=true`, on Windows run `.\mvnw.cmd clean install -D"gpg.skip=true"`

[travis-image]: https://travis-ci.org/jhipster/jhipster.svg?branch=master
[travis-url]: https://travis-ci.org/jhipster/jhipster

[maven-image]: https://maven-badges.herokuapp.com/maven-central/io.github.jhipster/jhipster-parent/badge.svg
[maven-url]: https://maven-badges.herokuapp.com/maven-central/io.github.jhipster/jhipster-parent

[azure-devops-image]: https://dev.azure.com/jhipster/jhipster/_apis/build/status/jhipster.jhipster?branchName=master
[azure-devops-url-main]: https://dev.azure.com/jhipster/jhipster/_build

[jhipster-url]: https://www.jhipster.tech/


## Analysis of the JHipster server-side parent POM project

[![sonar-quality-gate][sonar-quality-gate]][sonar-url] [![sonar-coverage][sonar-coverage]][sonar-url] [![sonar-bugs][sonar-bugs]][sonar-url] [![sonar-vulnerabilities][sonar-vulnerabilities]][sonar-url]

[sonar-url]: https://sonarcloud.io/dashboard?id=jhipster-framework
[sonar-quality-gate]: https://sonarcloud.io/api/project_badges/measure?project=jhipster-framework&metric=alert_status
[sonar-coverage]: https://sonarcloud.io/api/project_badges/measure?project=jhipster-framework&metric=coverage
[sonar-bugs]: https://sonarcloud.io/api/project_badges/measure?project=jhipster-framework&metric=bugs
[sonar-vulnerabilities]: https://sonarcloud.io/api/project_badges/measure?project=jhipster-framework&metric=vulnerabilities
