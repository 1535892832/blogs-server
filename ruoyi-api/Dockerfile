FROM ascdc/jdk8
MAINTAINER tryCatch<1535892832@qq.com>

COPY blogs-api.jar /app.jar

CMD ["--server.port=8081"]

EXPOSE 8080

# 执行java命令启动jar包
ENTRYPOINT ["java","-jar","/app.jar"]

