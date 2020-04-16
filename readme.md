# Picturerama

Image database project in Systemutvikling 1 at NTNU

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
- Java 12 SDK
- IntelliJ IDEA or another code editor

### Installing
```
git clone git@gitlab.stud.idi.ntnu.no:gruppe-12/picturerama.git
```

In order to be able to connect to our database, and be able to upload local images, you'll need a ```config.properties``` file in the project root. It should look like this:
```
username=your_username
password=your_password
database_url=jdbc:mysql://your_database_url
cloudinary_cloud_name=your_cloud_name
cloudinary_api_key=your_api_key
cloudinary_api_secret=your_api_secret
google_maps_api_key=your_google_maps_api_key
```


Contact one of the developers to get the username and password.

If you want to setup your own database for the application. Run the SetupDatabase file in the Main folder in the project, and use your own info in the config.properties file.

You should now have the files you need. In order to be able to run, you have to compile JavaFX
**Steps to compile JavaFX in IntelliJ IDEA:**
1. MAVEN
2. JavaFxApplication
3. Plugins
4. Double click on javafx:compile
5. Build project (hammer)
6. Right click javafx:run and click "Run 'JavaFxApplication'"

If you don't have a JavaFxApplication.iml file in the project root, you'll have to add it. Copy and paste the JavaFxApplication.iml file at the bottom of this readme.md

## Rules

These rules are to be updated...

1. Use the IntelliJ IDEA shortcut ```CTRL+ALT+L``` in all changed files before committing. This will make all code stay formatted in the same way.
2. Branch should use the format: ```type/work-done```, where type is ```feat```, ```fix```, ```refactor``` or another appropriate typename. The work done should describe in a short way what you have done and will usually be the same as the issue-name


## JavaFxApplication.iml
```
<?xml version="1.0" encoding="UTF-8"?>
<module org.jetbrains.idea.maven.project.MavenProjectsManager.isMavenModule="true" version="4">
  <component name="FacetManager">
    <facet type="jpa" name="JPA">
      <configuration>
        <setting name="validation-enabled" value="true" />
        <datasource-mapping />
        <naming-strategy-map />
        <deploymentDescriptor name="persistence.xml" url="file://$MODULE_DIR$/src/main/resources/META-INF/persistence.xml" />
      </configuration>
    </facet>
  </component>
  <component name="NewModuleRootManager" LANGUAGE_LEVEL="JDK_11">
    <output url="file://$MODULE_DIR$/target/classes" />
    <output-test url="file://$MODULE_DIR$/target/test-classes" />
    <content url="file://$MODULE_DIR$">
      <sourceFolder url="file://$MODULE_DIR$/src/main/App" isTestSource="false" />
      <sourceFolder url="file://$MODULE_DIR$/src/main/resources" type="java-resource" />
      <sourceFolder url="file://$MODULE_DIR$/Test" isTestSource="true" />
      <excludeFolder url="file://$MODULE_DIR$/target" />
    </content>
    <orderEntry type="inheritedJdk" />
    <orderEntry type="sourceFolder" forTests="false" />
    <orderEntry type="library" name="Maven: org.openjfx:javafx-controls:11" level="project" />
    <orderEntry type="library" name="Maven: org.openjfx:javafx-controls:win:11" level="project" />
    <orderEntry type="library" name="Maven: org.openjfx:javafx-graphics:11" level="project" />
    <orderEntry type="library" name="Maven: org.openjfx:javafx-graphics:win:11" level="project" />
    <orderEntry type="library" name="Maven: org.openjfx:javafx-base:11" level="project" />
    <orderEntry type="library" name="Maven: org.openjfx:javafx-base:win:11" level="project" />
    <orderEntry type="library" name="Maven: org.openjfx:javafx-fxml:11" level="project" />
    <orderEntry type="library" name="Maven: org.openjfx:javafx-fxml:win:11" level="project" />
    <orderEntry type="library" name="Maven: mysql:mysql-connector-java:8.0.16" level="project" />
    <orderEntry type="library" name="Maven: com.google.protobuf:protobuf-java:3.6.1" level="project" />
    <orderEntry type="library" name="Maven: javaxt:javaxt-core:1.7.8" level="project" />
    <orderEntry type="library" name="Maven: org.hibernate:hibernate-core:5.4.1.Final" level="project" />
    <orderEntry type="library" name="Maven: org.jboss.logging:jboss-logging:3.3.2.Final" level="project" />
    <orderEntry type="library" name="Maven: javax.persistence:javax.persistence-api:2.2" level="project" />
    <orderEntry type="library" name="Maven: org.javassist:javassist:3.24.0-GA" level="project" />
    <orderEntry type="library" name="Maven: net.bytebuddy:byte-buddy:1.9.5" level="project" />
    <orderEntry type="library" name="Maven: antlr:antlr:2.7.7" level="project" />
    <orderEntry type="library" name="Maven: org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final" level="project" />
    <orderEntry type="library" name="Maven: org.jboss:jandex:2.0.5.Final" level="project" />
    <orderEntry type="library" name="Maven: com.fasterxml:classmate:1.3.4" level="project" />
    <orderEntry type="library" name="Maven: javax.activation:javax.activation-api:1.2.0" level="project" />
    <orderEntry type="library" name="Maven: org.dom4j:dom4j:2.1.1" level="project" />
    <orderEntry type="library" name="Maven: org.hibernate.common:hibernate-commons-annotations:5.1.0.Final" level="project" />
    <orderEntry type="library" name="Maven: javax.xml.bind:jaxb-api:2.3.1" level="project" />
    <orderEntry type="library" name="Maven: org.glassfish.jaxb:jaxb-runtime:2.3.1" level="project" />
    <orderEntry type="library" name="Maven: org.glassfish.jaxb:txw2:2.3.1" level="project" />
    <orderEntry type="library" name="Maven: com.sun.istack:istack-commons-runtime:3.0.7" level="project" />
    <orderEntry type="library" name="Maven: org.jvnet.staxex:stax-ex:1.8" level="project" />
    <orderEntry type="library" name="Maven: com.sun.xml.fastinfoset:FastInfoset:1.2.15" level="project" />
    <orderEntry type="library" name="Maven: org.hibernate:hibernate-agroal:5.4.12.Final" level="project" />
    <orderEntry type="library" name="Maven: io.agroal:agroal-pool:1.7" level="project" />
    <orderEntry type="library" name="Maven: io.agroal:agroal-api:1.7" level="project" />
    <orderEntry type="library" name="Maven: org.hibernate:hibernate-entitymanager:5.4.12.Final" level="project" />
    <orderEntry type="library" scope="TEST" name="Maven: junit:junit:4.12" level="project" />
    <orderEntry type="library" scope="TEST" name="Maven: org.hamcrest:hamcrest-core:1.3" level="project" />
    <orderEntry type="library" scope="TEST" name="Maven: org.junit.jupiter:junit-jupiter-api:5.6.0" level="project" />
    <orderEntry type="library" scope="TEST" name="Maven: org.apiguardian:apiguardian-api:1.1.0" level="project" />
    <orderEntry type="library" scope="TEST" name="Maven: org.opentest4j:opentest4j:1.2.0" level="project" />
    <orderEntry type="library" scope="TEST" name="Maven: org.junit.platform:junit-platform-commons:1.6.0" level="project" />
  </component>
</module>
```