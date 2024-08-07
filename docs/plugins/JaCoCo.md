# Jacoco Maven Plugin

Этот плагин предоставляет метрики покрытия кода с использованием Jacoco для проектов Maven.

---

## Требования

Для данного проекта используется плагин версии 0.8.8

**Перед использованием проверьте, чтобы путь к вашему проекту НЕ содержал кириллических либо пробельных символов!**

---

## Использование

Добавьте jacoco-maven-plugin в блок `plugins` в файле `POM.xml`

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>${jacoco.version}</version>
    <executions>
        <execution>
            <id>jacoco-initialize</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>jcc-report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
        <execution>
            <id>jacoco-check</id>
            <goals>
                <goal>check</goal>
            </goals>
            <phase>test</phase>
            <configuration>
                <rules>
                    <rule>
                        <element>CLASS</element>
                        <limits>
                            <limit>
                                <counter>INSTRUCTION</counter>
                                <value>MISSEDRATIO</value>
                                <maximum>20%</maximum>
                            </limit>
                        </limits>
                    </rule>
                </rules>
            </configuration>
        </execution>
    </executions>
    <configuration>
        <excludes>
            <exclude>ru/gorchanyuk/securitywithjwt/dto/**</exclude>
            <exclude>ru/gorchanyuk/securitywithjwt/entity/**</exclude>
            <exclude>ru/gorchanyuk/securitywithjwt/props/**</exclude>
            <exclude>ru/gorchanyuk/securitywithjwt/security/**</exclude>
            <exclude>ru/gorchanyuk/securitywithjwt/repository/**</exclude>
            <exclude>ru/gorchanyuk/securitywithjwt/exception/**</exclude>
            <exclude>ru/gorchanyuk/securitywithjwt/config/SwaggerConfiguration.class</exclude>
            <exclude>ru/gorchanyuk/securitywithjwt/util/GenerateKeys.class</exclude>
            <exclude>ru/gorchanyuk/securitywithjwt/service/impl/RefreshTokenService.class</exclude>
            <exclude>ru/gorchanyuk/securitywithjwt/SecurityWithJwtApplication.class</exclude>
        </excludes>
    </configuration>
</plugin>
```

Плагин срабатывает на фазе `test` жизненного цикла сборки проекта, поэтому при запуске используйте команду не ниже 
`mvn clean test`
После выполнения проверки, сгенерированный отчет будет расположен в файле `target/site/jacoco/index.html`

---

## Настраиваемые параметры

### Свойства в секции rules:
В секции `<rules>` можно задать правила, которые определяют минимальные уровни покрытия кода, которые должны быть 
достигнуты. Эти правила могут быть применены к различным элементам кода, таким как пакеты, классы, методы и т.д.
- BUNDLE - правило применяется ко всему проекту или модулю
- PACKAGE - правило применяется к каждому пакету в проекте
- SOURCEFILE - правило применяется к каждому исходному файлу
- METHOD - правило применяется к каждому методу
- CLASS - правило применяется к каждому классу

### Свойства в секции limits:
Секция `<limits>` в конфигурации `jacoco-maven-plugin` позволяет вам задать ограничения на минимальные уровни покрытия 
кода для различных метрик.

- INSTRUCTION - базовые единицы выполнения кода. Покрытие инструкций показывает, какая часть всех инструкций в коде 
была выполнена
- LINE - покрытие строк показывает, какие строки исходного кода были выполнены. Это более высокоуровневая метрика по 
сравнению с инструкциями
- BRANCH - покрытие ветвей показывает, какие ветви в условных операторах (например, if-else) были выполнены. Это помогает 
убедиться, что все возможные пути выполнения кода были протестированы
- COMPLEXITY - сложность (Cyclomatic Complexity) измеряет количество независимых путей через программу. Покрытие 
сложности показывает, насколько хорошо протестированы различные пути выполнения
- METHOD - покрытие методов показывает, какие методы были выполнены. Это помогает убедиться, что все методы в коде были 
вызваны во время тестирования
- CLASS - покрытие классов показывает, какие классы были выполнены. Это помогает убедиться, что все классы в коде были 
использованы во время тестирования

Кроме этого для каждого правила можно задать значение для метрик покрытия кода, используя элемент `<value>`. Эти 
значения определяют, как будут интерпретироваться и проверяться метрики покрытия.

- MISSEDRATIO - oтношение пропущенных элементов (инструкций, строк, ветвей и т.д.) к общему числу элементов
- MISSEDCOUNT - общее количество пропущенных элементов
- COVEREDRATIO - отношение покрытых элементов к общему числу элементов
- COVEREDCOUNT - общее количество покрытых элементов
- TOTALCOUNT - общее количество элементов (покрытых и пропущенных)

### Параметры в секции configuration:

В секции `<configuration>` находится блок `<excludes>`, в котором можно исключить нежелательные для анализа покрытия 
пакеты и файлы.

