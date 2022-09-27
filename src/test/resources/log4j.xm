<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration xmlns:log4j='http://jakarta.apache.org/log4j/'>

    <appender name="file" class="org.apache.log4j.RollingFileAppender">
        <param name="append" value="false"/>
        <param name="maxFileSize" value="10MB"/>
        <param name="maxBackupIndex" value="2"/>
        <param name="file" value="C:\\Users\\USER\\Desktop\\student-project\\target\\log_file.log"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern"
                   value="%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n"/>
        </layout>
    </appender>

    <category name="ebu.javacourse.student_order" additivity="false">
        <priority value="all"/>
        <appender-ref ref="file"/>
    </category>
    <root>
        <level value="DEBUG"/>
        <appender-ref ref="file"/>
    </root>

</log4j:configuration>