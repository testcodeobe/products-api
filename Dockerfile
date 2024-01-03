FROM openjdk:17-jdk-slim
COPY target/*.jar /demo.jar
COPY application.properties /application.properties
CMD ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-jar", "/demo.jar"]


#DO NOT MODIFY FOLLOWING
ARG carg1=x1
ARG carg2=x2
ARG carg3=x3
ARG carg4=x4
ARG carg5=x5
ARG carg6=x6
ARG carg61=x61
ARG carg7=x7
ARG carg8=x8
ARG carg9=x9
ENV cenv1=${carg1}
ENV cenv2=${carg2}
ENV cenv3=${carg3}
ENV cenv4=${carg4}
ENV cenv5=${carg5}
ENV cenv6=${carg6}
ENV cenv61=${carg61}
ENV cenv7=${carg7}
ENV cenv8=${carg8}
ENV cenv9=${carg9}