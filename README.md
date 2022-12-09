# WebAPI home task

<h3> How to launch tests: </h3>

1. Install java jdk 17: https://www.oracle.com/java/technologies/downloads/
2. Install maven: https://maven.apache.org/index.html
3. Install allure: https://docs.qameta.io/allure/#_installing_a_commandline
4. Make sure that java, maven and allure are added to your PATH variable
5. Add your dropbox app's ACCESS_TOKEN to src/test/resources/config.properties
6. Open terminal in project folder
7. Launch tests by executing the command:
    ```
    mvn test
    ```
8. Check allure report by executing the command:
    ```
    allure serve allure-results
    ```