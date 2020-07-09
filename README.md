# MySpringBoot


# 在应用根路径下执行
# build 镜像
mvn clean package docker:build

# 通过docker-compose后台启动docker镜像容器
docker-compose up -d

#grpc编译java类
参考这个地址配置properties，配置见下https://github.com/trustin/os-maven-plugin
<properties>
    <os.detected.name>osx</os.detected.name>
    <os.detected.arch>x86_64</os.detected.arch>
    <os.detected.classifier>osx-x86_64</os.detected.classifier>
</properties>

从maven下载这两个exe并进行安装
mvn install:install-file -DgroupId=com.google.protobuf -DartifactId=protoc -Dversion=3.12.0 -Dclassifier=osx-x86_64 -Dpackaging=exe -Dfile=/Users/huoershuai/programtools/protoc-3.12.0-osx-x86_64.exe
mvn install:install-file -DgroupId=io.grpc -DartifactId=protoc-gen-grpc-java -Dversion=1.30.2 -Dclassifier=osx-x86_64 -Dpackaging=exe -Dfile=/Users/huoershuai/programtools/protoc-gen-grpc-java-1.30.0-osx-x86_64.exe

安装完成后执行：mvn package  即可将proto打印到target的class中，可以在java类中直接引用
