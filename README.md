
# TP-TACS-UTN-GROUP-2

[![CI TACS](https://github.com/Dragonflares/TACS-1C-2022-Grupo2/actions/workflows/gradle.yml/badge.svg?branch=main)](https://github.com/Dragonflares/TACS-1C-2022-Grupo2/actions/workflows/gradle.yml)


### Class Diagram

![image](https://media.discordapp.net/attachments/958543527153901580/961981089994383381/unknown.png)


### Requirements for build up the project
- Have docker installed

### Build up the Backend project
- Download the repository, and enter to the TACS-1C-2022-Grupo2\backend\TP-TACS backend root
- Execute the following comand inside the backend folder:  
``` docker build  -t tacs/backend . ```
- Then run: 
``` docker run  -d -p 8080:8080 -t tacs/backend ```

### Build up the Frontend project
- Download the repository, and enter to the TACS-1C-2022-Grupo2\frontend\ frontend root
- Execute the following comand inside the backend folder:  
``` docker build  -t tacs/frontend . ```
- Then run: 
``` docker run  -d -p 8080:8080 -t tacs/frontend ```

### Build up the entire project
- Download the repository, and enter to the TACS-1C-2022-Grupo2 
- Execute the following comand inside the backend folder:  
``` docker compose up -d```


### Urls

- For getting the rest endpoints you need the application running and access to the following url:

  ## Backend
- http://localhost:8080/swagger-ui 
 
- http://localhost:8080/api-docs
- http://localhost:8080
  ## Frontend
- http://localhost:3000
