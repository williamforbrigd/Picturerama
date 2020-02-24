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

In order to be able to connect to our database you'll need a ```config.properties``` file in the project root. It should look like this:
```
username=your_username
password=your_password
```
Contact one of the developers to get the username and password.

You should now have the files you need. In order to be able to run, you have to add the libraries.
**Steps to add libraries in IntelliJ IDEA:**
1. File
2. Project Structure
3. Libraries
4. Plus Icon (+)
5. Java
5. Select all .jar files and the lib folder inside lib-folder
6. Press OK

## Rules

These rules are to be updated...

1. Use the IntelliJ IDEA shortcut ```CTRL+ALT+L``` in all changed files before committing. This will make all code stay formatted in the same way.
2. Branch should use the format: ```type/work-done```, where type is ```feat```, ```fix```, ```refactor``` or another appropriate typename. The work done should describe in a short way what you have done and will usually be the same as the issue-name
