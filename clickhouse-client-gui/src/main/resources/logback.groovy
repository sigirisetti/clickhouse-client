// always a good idea to add an on console status listener
statusListener(OnConsoleStatusListener)

def appenderList = ["ROLLING"]
def WEBAPP_DIR = "."
def consoleAppender = true

LOG_DIR = "/tmp"
// does hostname match pixie or orion?
if (hostname =~ /pixie|orion/) {
    consoleAppender = false
} else {
    appenderList.add("CONSOLE")
}

if (consoleAppender) {
    appender("CONSOLE", ConsoleAppender) {
        encoder(PatternLayoutEncoder) {
            pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
        }
    }
}

appender("ROLLING", RollingFileAppender) {
    addInfo("Setting [file] property to [${LOG_DIR}/efx-data-generator.log]")
    file = "${LOG_DIR}/efx-data-generator.log"
    encoder(PatternLayoutEncoder) {
        Pattern = "%d %level %thread %mdc %logger - %m%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        FileNamePattern = "${LOG_DIR}/log/efx-data-generator.log-%d{yyyy-MM}.zip"
    }
}

logger("com.ssk", DEBUG)

logger("org.apache.http", ERROR)

logger("org.springframework", WARN)
logger("org.springframework.web", INFO)
logger("org.springframework.web.socket", INFO)
logger("org.springframework.web.servlet", INFO)
logger("org.springframework.web.filter.CommonsRequestLoggingFilter", DEBUG)

root(INFO, appenderList)