FROM java

LABEL maintainer="448725235@qq.com"

ARG WORK_PORT=80
ARG APP_HOME="/var/app"

ENV APP_HOME="${APP_HOME}" \
    APP_LOGS="${APP_HOME}/logs" \
    APP_BIN="${APP_HOME}/bin" \
    APP_LIB="${APP_HOME}/lib"

# 创建程序目录（日志目录、启动目录、应用目录）
RUN mkdir -p ${APP_LOGS}/ ${APP_BIN}/ ${APP_LIB}/

# 拷贝脚本以及应用文件
ADD *.sh ${APP_BIN}/
ADD *.jar ${APP_LIB}/

# 授权
RUN chmod -R +x ${APP_HOME}/

EXPOSE ${WORK_PORT}/tcp
VOLUME ["${APP_LOGS}"]
WORKDIR ${APP_HOME}
ENTRYPOINT ["/var/app/bin/service.sh", "start"]