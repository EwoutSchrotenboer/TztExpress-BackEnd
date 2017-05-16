FROM java:8

RUN apt-get update -qq
RUN apt-get install -y wget unzip

# Installing gradle
RUN wget https://services.gradle.org/distributions/gradle-3.5-bin.zip
RUN mkdir /opt/gradle
RUN unzip -d /opt/gradle gradle-3.5-bin.zip
ENV PATH $PATH:/opt/gradle/gradle-3.5/bin

# Creating app directory and assigning this directory
RUN mkdir /app
WORKDIR /app

COPY build.gradle /app/build.gradle
COPY gradlew /app/gradlew
COPY gradlew.bat /app/gradlew.bat
COPY settings.gradle /app/setting.gradle

RUN gradle assemble

COPY . /app
