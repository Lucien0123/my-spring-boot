# MySpringBoot


# 在应用根路径下执行
# build 镜像
mvn clean package docker:build

# 通过docker-compose后台启动docker镜像容器
docker-compose up -d
