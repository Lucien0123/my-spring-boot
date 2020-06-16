# MySpringBoot

# build 镜像
mvn clean package docker:build

# 通过docker-compose后台启动docker镜像容器
docker-compose up -d
