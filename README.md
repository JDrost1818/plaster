Plaster
=======

Project to bring similar functionality found in Rails to the Spring Boot platform. Currently only supports maven-enabled projects.

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
      -v, --version         fetches the current version of the tool
      -k [KEY], --key [KEY]
                            indicates the following field:type pair should define the key
                            NOTE: should be a trailing param
      -d DIR, --dir DIR     defines a sub path in which to perform actions
                            NOTE: should be a trailing param

  [maven]: https://maven.apache.org/install.html
  
  Customization
  =============
  
  Per default, Plaster will auto-discover necessary configurations and then use best-practices to decide where and how to generate files. However, if you would like to customize the generation of the files, Plater gives you the ability to alter defaults by placing plaster.yml in the root of the project. The following configurations are supported:
  
  Property dir
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
  <td>enabled</td>
  <td>Should we enable generation in lombok mode</td>
  <td>Boolean</td>
  <td>Is lombok a dependency in pom.xml ?</td>
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
  
    [homepage]: https://projectlombok.org/

  