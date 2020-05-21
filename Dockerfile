FROM java:8
VOLUME /tmp
RUN mkdir /app
ADD my-spring-boot-1.0.1-SNAPSHOT.jar /app/my-spring-boot.jar
ADD runboot.sh /app
RUN bash -c 'touch /app/my-spring-boot.jar'
WORKDIR /app
RUN chmod a+x runboot.sh
EXPOSE 9090
CMD /app/runboot.sh