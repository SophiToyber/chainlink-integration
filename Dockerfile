FROM ubuntu:18.04
EXPOSE 8514
COPY . .
COPY ./src/main/resources/wrapper/chainlink/ chainlink

RUN apt-get update &&\
    apt-get install -y software-properties-common &&\ 
    apt-get -y install curl gnupg
    
RUN curl -sL https://deb.nodesource.com/setup_11.x  | bash -
RUN apt-get -y install nodejs

RUN add-apt-repository ppa:openjdk-r/ppa
RUN apt-get update && apt install openjdk-11-jdk -y

RUN ./gradlew build
RUN cp build/libs/*.jar .
    
CMD bash startup.sh