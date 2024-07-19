# PIT Mutation Testing Maven Plugin
Плагин для Apache Maven, который интегрирует PIT в процесс сборки вашего проекта. 
Он позволяет автоматически запускать тестирование мутаций, генерировать отчеты и анализировать результаты для улучшения 
качества ваших тестов.

---

## Требования

Для данного проекта используется плагин версии 1.16.1, версия используемой плагином зависимости 
**pitest-junit5-plugin 1.2.1**

---

## Использование

Добавьте pitest-maven в блок `plugins` в файле `POM.xml`

```xml
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>${pitest.plugin.version}</version>
    <executions>
        <execution>
            <phase>test</phase>
            <goals>
                <goal>mutationCoverage</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <targetClasses>
            <pattern>ru.gorchanyuk.securitywithjwt.service.impl.*</pattern>
        </targetClasses>
        <targetTests>
            <pattern>ru.gorchanyuk.securitywithjwt.service.impl.*</pattern>
        </targetTests>
        <mutators>ALL</mutators>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>org.pitest</groupId>
            <artifactId>pitest-junit5-plugin</artifactId>
            <version>${pitest.dependency.version}</version>
        </dependency>
    </dependencies>
</plugin>
```
Плагин срабатывает на фазе `test` жизненного цикла сборки проекта, поэтому при запуске используйте команду не ниже 
`mvn clean test`
После выполнения проверки, сгенерированный отчет будет расположен в файле `/target/pit-reports/index.html`

---

## Настраиваемые параметры в секции configuration:

`<targetClasses>` - определяем какие классы должны быть включены в процесс мутационного тестирования

`<targetTests>` - указываем тестовые классы, которые будут использованы для проверки мутаций

`<mutators>` - мутационный оператор `ALL` включает все доступные мутационные операторы. Это означает, что при 
использовании `ALL` будут применены все возможные мутации к коду, чтобы максимально проверить качество тестов.
Другие возможные группы мутатров: `OLD_DEFAULTS`, `DEFAULTS`, `STRONGER`. 
Детальный список всех мутаторов можно посмотреть в [официальной документации](https://pitest.org/quickstart/mutators/)