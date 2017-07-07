<a href=http://jakedrost.me/plaster>
	<img src=http://jakedrost.me/img/plaster/plaster-logo-with-text.png />
</a>

-----

[![Version for JDrost1818/plaster](https://img.shields.io/SemVer/1.1.1.png)]()
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/854f4669211b4c30b988010544488fa3)](https://www.codacy.com/app/Drost011/plaster?utm_source=github.com&utm_medium=referral&utm_content=JDrost1818/plaster&utm_campaign=Badge_Coverage)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/854f4669211b4c30b988010544488fa3)](https://www.codacy.com/app/Drost011/plaster?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=JDrost1818/plaster&amp;utm_campaign=Badge_Grade)
[![Codeship Status for JDrost1818/plaster](https://app.codeship.com/projects/184069b0-1c9a-0135-6e0f-0e8dc4a678fe/status?branch=master)](https://app.codeship.com/projects/220058)

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

[![Stories in Backlog](https://badge.waffle.io/JDrost1818/plaster.png?label=backlog&title=Backlog)](https://waffle.io/JDrost1818/plaster?utm_source=badge)
[![Stories in Ready](https://badge.waffle.io/JDrost1818/plaster.png?label=ready&title=Ready)](https://waffle.io/JDrost1818/plaster?utm_source=badge)
[![Stories in Development](https://badge.waffle.io/JDrost1818/plaster.png?label=in%20progress&title=In%20Development)](https://waffle.io/JDrost1818/plaster?utm_source=badge)
[![Stories in Development](https://badge.waffle.io/JDrost1818/plaster.png?label=in%20review&title=Coming%20Next%20Release)](https://waffle.io/JDrost1818/plaster?utm_source=badge)

Installation
------------

To install, you must have [maven] installed. Then on linux machines, simply run:
    
    ./setup.sh

Usage
-----

    usage: plaster [-h] [-v] [-k [KEY]] [-d DIR]
                   {generate,g,delete,d}
                   {scaffold,model,repository,service,controller,field} model_name
                   [fields [fields ...]]

    Generate files for Spring Boot

    positional arguments:
      {generate,g,delete,d}
                            whether to generate or delete
                                generate, g - create files
                                delete, d - remove files
      {scaffold,model,repository,service,controller}
                            how to generate or delete content
                                scaffold - all files associated to the model
                                model - the entire model
                                repository - the entire repository
                                service - the entire service
                                controller - the entire controller
                                field - individual field(s)
      model_name            name of model for which to perform actions
      fields                fields to perform actions listed as name:type pairs

    optional arguments:
      -h, --help            show this help message and exit
      -k [KEY], --key [KEY]
                            indicates the following field:type pair should define the key
                            NOTE: should be a trailing param
      -d DIR, --dir DIR     defines a sub path in which to perform actions
                            NOTE: should be a trailing param
  
  Customization
  =============
  
  Per default, Plaster will auto-discover necessary configurations and then use best-practices to decide where and 
  how to generate files. However, if you would like to customize the generation of the files, Plaster gives you the 
  ability to alter defaults by placing [plaster.yml] in the root of the project. The following configurations are 
  supported:
  
  Property property
  ------------
  
  <table style="width:100%;">
      <colgroup>
          <col width="16%" />
          <col width="53%" />
          <col width="12%" />
          <col width="16%" />
      </colgroup>
      <thead>
          <tr class="header">
              <th>Property</th>
              <th>Description</th>
              <th>Type</th>
              <th>Default</th>
          </tr>
      </thead>
      <tbody>
          <tr class="even">
              <td>enablePrimitives</td>
              <td>If true, Integer -> int where possible</td>
              <td>Boolean</td>
              <td>false</td>
          </tr>
          <tr class="odd">
              <td>key</td>
              <td>name:type definition for ids in models</td>
              <td>String</td>
              <td>id:string</td>
          </tr>
      </tbody>
  </table>
  
  Property directory
  ------------
  
  <table style="width:100%;">
      <colgroup>
          <col width="16%" />
          <col width="53%" />
          <col width="12%" />
          <col width="16%" />
      </colgroup>
      <thead>
          <tr class="header">
              <th>Property</th>
              <th>Description</th>
              <th>Type</th>
              <th>Default</th>
          </tr>
      </thead>
      <tbody>
          <tr class="even">
              <td>base</td>
              <td>Path to project in which to generate</td>
              <td>String</td>
              <td></td>
          </tr>
          <tr class="odd">
              <td>model</td>
              <td>Directory in which to generate models</td>
              <td>String</td>
              <td>model</td>
          </tr>
          <tr class="even">
              <td>repository</td>
              <td>Directory in which to generate repositories</td>
              <td>String</td>
              <td>repository</td>
          </tr>
          <tr class="odd">
              <td>controller</td>
              <td>Directory in which to generate controllers</td>
              <td>String</td>
              <td>controller</td>
          </tr>
          <tr class="even">
              <td>service</td>
              <td>Directory in which to generate services</td>
              <td>String</td>
              <td>service</td>
          </tr>
      </tbody>
  </table>
  
  Property lombok
  ---------------
  
  <table style="width:100%;">
      <colgroup>
          <col width="20%" />
          <col width="51%" />
          <col width="12%" />
          <col width="14%" />
      </colgroup>
      <thead>
          <tr class="header">
              <th>Property</th>
              <th>Description</th>
              <th>Type</th>
              <th>Default</th>
          </tr>
      </thead>
      <tbody>
          <tr class="odd">
              <td>enable</td>
              <td>Should we enable generation in lombok mode</td>
              <td>Boolean</td>
              <td>Is lombok a dependency in pom.xml?</td>
          </tr>
      </tbody>
  </table>
  
  Lombok Support
  ==============
  
  Generation of models will change if a lombok dependency is found in pom.xml. This will import lombok and annotate the model differently. For example:
  
  ```Java
  //NO LOMBOK
  @Entity
  public class Example {

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private Integer id;

      public Integer getId() {
          return this.id;
      }

      public void setId(Integer id) {
          this.id = id;
      }

  }
  ```
  ```Java
  // Lombok
  import lombok.AllArgsConstructor;
  import lombok.Builder;
  import lombok.Data;
  import lombok.NoArgsConstructor;

  @AllArgsConstructor
  @Builder
  @Data
  @NoArgsConstructor
  public class Example {

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private Integer id;

  }
  ```
  
  For lombok information, visit the project’s [homepage].
  
[maven]: https://maven.apache.org/install.html
[homepage]: https://projectlombok.org/
[plaster.yml]: https://github.com/JDrost1818/plaster-java/blob/master/src/test/resources/testProject/root2/plaster.yml
[waffle.io]: https://waffle.io/
[here]: https://waffle.io/JDrost1818/plaster
  
