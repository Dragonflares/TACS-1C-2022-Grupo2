
# TP-TACS-UTN-GROUP-2



### Class Diagram

![image](https://media.discordapp.net/attachments/958543527153901580/961981089994383381/unknown.png)


### Requierments for build up the project
- Have docker installed

### Build up the project
- Download the repository, and enter to the TACS-1C-2022-Grupo2\backend\TP-TACS backend root
- Execute the following comand inside the backend folder:  
``` docker build --build-arg JAR_FILE=build/libs/*.jar -t tacs/backend . ```
- Then run: 
``` docker run  -d -p 8080:8080 -t tacs/backend ```

### Urls

- For getting the rest endpoints you need the application running and access to the following url:

  - http://localhost:8080/swagger-ui 
 
  - http://localhost:8080/api-docs
