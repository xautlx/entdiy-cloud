
## 环境准备

整个项目已在 Mac OS 和 CentOS 7 环境做过构建部署运行验证，项目配置默认全部采用Docker容器化部署模式，
理论上安装最新版本Docker（已验证 docker 19.X及20.X 版本）及Docker-Compose（已验证 docker-compose 1.28.X及1.29.X 版本）
即可正常执行一键运行脚本全自动完成基于当前源码工程构建、打包、部署、运行过程。

根据Docker跨平台化特性，只要支持Docker/Docker-Compose安装的操作系统如MacOS/Linux/Windows理论皆可正常执行项目构建部署。
请自行根据熟悉的操作系统环境，对应安装Docker和Docker-Compose最新版本，具体过程请自行参考搜索引擎或官网指南，在此不再赘述。
完成Docker基础环境的安装和运行后，请参照如下过程检查版本和安装必要的插件：

* 检查docker及docker-compose版本（版本无需严格一致，相似接近即可）

```shell script
docker --version

Docker version 19.03.9, build 9d988398e7
```

```shell script
docker-compose --version

docker-compose version 1.28.6, build 5db8d86f
```

* 按需设置docker镜像的国内私服，加快pull image速度

```shell script
vi /etc/docker/daemon.json

{
  "registry-mirrors": ["http://hub-mirror.c.163.com","https://docker.mirrors.ustc.edu.cn"]
}
```

* 安装日志组件Loki的docker plugin插件

由于项目所有基础服务及应用微服务全部采用Docker运行模式，因此使用Loki docker drive plugin进行日志的采集聚合，详见：
[Loki Docker Driver Client](https://grafana.com/docs/loki/latest/clients/docker-driver/)

插件安装：

```shell script
docker plugin install grafana/loki-docker-driver:latest --alias loki --grant-all-permissions

```

插件验证：

```shell script
docker plugin ls

ID                  NAME         DESCRIPTION           ENABLED
ac720b8fcfdb        loki         Loki Logging Driver   true
```

* 整个项目部署运行需要占用 80 8080 91XX 92XX 93XX 等多个端口，详见项目首页的服务及端口列表。
请确保运行主机这些端口未被占用，或者如果熟悉调整方式定制调整相关运行端口亦可。

## 一键运行

上述Docker环境安装运行起来后，即可进入quick-start目录，执行一键运行脚本：

```shell script
./quick-start.sh
```

运行结束看到如下提示内容：

```shell script
**********************************************
*** All service build and startup success.  **
*** Please visit http://127.0.0.1/admin/    **
**********************************************
```

即可打开浏览器访问体验了，当然如果不是在本机运行，只需改成目标机器IP地址访问即可。

注：

* 目前只提供了 Mac OS/Linux 系统的shell执行脚本，至于Windows系统请直接参考quick-start.sh文件内容按步骤含义执行对应Windows命令即可。
* 执行过程基于源码构建涉及到Maven在线下载依赖jar组件、Node构建在线下载npm组件，整个执行过程耗时依赖于运行电脑的带宽以及硬件配置有关，请耐心等待。
线上体验应用就是完全基于Jenkins自动化构建调用quick-start.sh一键构建部署运行的，正常情况相关执行脚本和配置是能完成从源码到构建部署运行本地访问的。


## 资源说明

正常情况8G内存应该能流畅部署和运行整套服务，以下是演示环境Docker运行资源情况参考：

```shell script

CONTAINER ID   NAME             CPU %     MEM USAGE / LIMIT    MEM %     NET I/O           BLOCK I/O     PIDS
816bd3240e29   nginx            0.00%     1.539MiB / 11.3GiB   0.01%     2.56kB / 841B     0B / 0B       2
c9e8efd0c710   entdiy-gateway   0.87%     379.3MiB / 11.3GiB   3.28%     29.2kB / 25.4kB   0B / 0B       74
0e16fc0b31f3   entdiy-auth      1.20%     403.5MiB / 11.3GiB   3.49%     13.3kB / 11.8kB   0B / 0B       57
27ccd982fbc9   entdiy-system    1.11%     459.8MiB / 11.3GiB   3.97%     88.3kB / 98.3kB   0B / 0B       59
19abe18e8f4a   entdiy-druid     0.32%     299.9MiB / 11.3GiB   2.59%     9.3kB / 19.6kB    0B / 0B       34
75ba5594eb08   entdiy-job       1.18%     446.7MiB / 11.3GiB   3.86%     92.5kB / 73.3kB   0B / 0B       81
f5d5c52cf4f3   entdiy-monitor   0.31%     367.1MiB / 11.3GiB   3.17%     24.2kB / 19.8kB   0B / 0B       66
6a57c7023bff   entdiy-gen       0.94%     446MiB / 11.3GiB     3.85%     67.2kB / 51.4kB   0B / 0B       60
363e99264a7f   entdiy-file      0.37%     343.7MiB / 11.3GiB   2.97%     18.7kB / 21kB     0B / 0B       51
648afa0bdcad   grafana          0.03%     28.36MiB / 11.3GiB   0.25%     7.82kB / 2.92kB   0B / 36.9kB   9
7e34ce60b831   nacos            3.02%     691.3MiB / 11.3GiB   5.97%     173kB / 139kB     0B / 0B       117
d3a4477e9801   loki             0.08%     21.32MiB / 11.3GiB   0.18%     1.54kB / 0B       0B / 0B       9
7faa466097e4   redis            0.07%     8.375MiB / 11.3GiB   0.07%     3.44kB / 1.26kB   0B / 0B       4
5ed8845e4389   mysql            0.83%     234.8MiB / 11.3GiB   2.03%     228kB / 271kB     0B / 634MB    44

```
