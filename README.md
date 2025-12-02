# PROYECTO BOLSA DE TRANSPORTES

## Resumen del proyecto

Este proyecto es una bolsa de cargas con una entrada para conductor (el que reserva la carga), otra para gerente (el que
crea la carga) y
una para administrador (controla toda la bbdd)

## Ideas para una V2

Si un conductor hace una petición que no encuentra nada de carga, al meterese ese tipo de carga ser notifica al
conductor

Si ponemos el nombre de la calle es capaz de buscar el código postal

## Dockerización 🚀

Sobre una carpeta vacia en la que se encuentre un archivo Dockerfile con el siguiente contenido

```
FROM ubuntu:20.04
ENV TZ=Asia/Kolkata \
    DEBIAN_FRONTEND=noninteractive



RUN  apt-get update -y
RUN  apt-get install -y

RUN  apt install gnupg ca-certificates curl -y
RUN curl -s https://repos.azul.com/azul-repo.key | gpg --dearmor -o /usr/share/keyrings/azul.gpg
RUN echo "deb [signed-by=/usr/share/keyrings/azul.gpg] https://repos.azul.com/zulu/deb stable main" |  tee /etc/apt/sources.list.d/zulu.list
RUN  apt update -y
RUN  apt install zulu23-ca-jdk -y
RUN  apt install maven -y
RUN apt-get update && apt-get install -y git
RUN apt-get update && apt-get install -y \
    libxss1 \
    libx11-6 \
    libxext6 \
    libxi6 \
    libxrender1 \
    libxrandr2 \
    libxxf86vm1 \
    libglu1-mesa \
    libfontconfig1 \
    libgdk-pixbuf2.0-0 \
    libgtk-3-0 \
    libgl1-mesa-glx 

RUN git clone https://github.com/israel05/transporteBeta1.git

WORKDIR /transporteBeta1

RUN mvn package   

CMD  java -jar /transporteBeta1/target/transportes-1.beta.jar
```

Creamos el contenedor

```
docker build --tag transportes .
```

Teniendo vcxsrv instalado

Teniendo C:\Program Files\VcXsrv\xLaunch.exe ejecutandose

habiendo quitado la opción de "disable Access control"

Lanzamos el contenedor que busca la pantalla activa

```
docker run -e DISPLAY=host.docker.internal:0 -v /tmp/.X11-unix:/tmp/.X11-unix transportes
```

