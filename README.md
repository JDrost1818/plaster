[![plaster](http://jakedrost.me/plaster/img/plaster-logo-with-text.png)](http://jakedrost.me/plaster)

-----

[![Version for JDrost1818/plaster](https://img.shields.io/badge/semver-2.2.0-brightgreen.svg)]()
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/854f4669211b4c30b988010544488fa3)](https://www.codacy.com/app/Drost011/plaster?utm_source=github.com&utm_medium=referral&utm_content=JDrost1818/plaster&utm_campaign=Badge_Coverage)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/854f4669211b4c30b988010544488fa3)](https://www.codacy.com/app/Drost011/plaster?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=JDrost1818/plaster&amp;utm_campaign=Badge_Grade)
[![Codeship Status for JDrost1818/plaster](https://app.codeship.com/projects/184069b0-1c9a-0135-6e0f-0e8dc4a678fe/status?branch=master)](https://app.codeship.com/projects/220058)

Visit Plaster's [website] for more information 

Project to bring similar functionality found in Rails to the Spring Boot platform. Currently only supports 
maven-enabled projects.

For Example:

    plaster g scaffold User name:string age:integer

Will create the following files:

-   Model - root/model/User.java
-   Repository - root/repository/UserRepository.java
-   Service - service/UserService.java
-   Controller - controller/UsersController.java

The root is calculated upon script start. For example, if being run on a project with the following in \`pom.xml\`:

    <groupId>com.example</groupId>

the root will be src/main/com/example/

Coming Features
---------------
Plaster is integrated with [waffle.io] and new features are tracked [here]. Below is the up-to-date snapshot of 
feature development.

[![Waffle.io - Columns and their card count](https://badge.waffle.io/JDrost1818/plaster.svg?columns=all)](https://waffle.io/JDrost1818/plaster)

Installation
------------

To install, you must have [maven] installed. Then on linux machines, simply run:
    
    ./setup.sh

[maven]: https://maven.apache.org/install.html
[homepage]: https://projectlombok.org/
[plaster.yml]: https://github.com/JDrost1818/plaster-java/blob/master/src/test/resources/testProject/root2/plaster.yml
[waffle.io]: https://waffle.io/
[here]: https://waffle.io/JDrost1818/plaster
[website]: http://jakedrost.me/plaster
  
