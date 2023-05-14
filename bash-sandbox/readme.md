### Docker
 - Build Docker image: 
_docker build -t nixx/bash-sandbox ._

 - Run Docker:
_docker run --name BashSandbox -d nixx/bash-sandbox_

-d - detached mode 

 - Stop Docker:
_docker stop BashSandbox_

 - Remove Docker:
_docker rm BashSandbox_

 - Active list:
_docker ps > t_

 - Console for Application
_docker exec -it BashSandbox /bin/sh_

- Получение информации о контейнерах: _docker ps_


