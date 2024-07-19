# PMD Maven Plugin
Плагин для Apache Maven, который интегрирует PMD в процесс сборки вашего проекта. 
Он позволяет анализировать ваш код на наличие потенциальных ошибок, плохих практик и нарушений код-стиля. 

---

## Требования

Для данного проекта используется плагин версии 3.24.0

---

## Использование

Добавьте pmd-plugin в блок `plugins` в файле `POM.xml`

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-pmd-plugin</artifactId>
    <version>${pmd.version}</version>
    <configuration>

        <rulesets>
            <ruleset>/category/java/errorprone.xml</ruleset>
            <ruleset>/category/java/multithreading.xml</ruleset>
            <ruleset>/category/java/bestpractices.xml</ruleset>
            <ruleset>/category/java/codestyle.xml</ruleset>
            <ruleset>/category/java/performance.xml</ruleset>
            <ruleset>/category/java/design.xml</ruleset>
        </rulesets>
        <failOnViolation>false</failOnViolation>
        <printFailingErrors>true</printFailingErrors>
    </configuration>
    <executions>
        <execution>
            <id>pmd_check</id>
            <phase>validate</phase>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
        <execution>
            <id>cpd_check</id>
            <phase>validate</phase>
            <goals>
                <goal>cpd-check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Плагин срабатывает на фазе `validate` жизненного цикла сборки проекта, поэтому при запуске используйте команду не ниже 
`mvn clean validate`
После выполнения проверки, сгенерированный отчет PMD будет расположен в файле `/target/site/pmd.html`, 
а также будет сгенерирован отчет CPD о выявленных дубликатов в коде, расположенный в файле `/target/site/cpd.html`.

---

## Настраиваемые параметры в секции configuration:

`<rulesets>` - определяем какие правила должны быть включены в процесс анализа. Можно добавлять/удалять требуемые правила. 
Детальный список всех правил можно посмотреть в [официальной документации](https://pmd.github.io/pmd/pmd_rules_java.html) 

`<minimumTokens>` - определяет минимальное количество токенов, которые должны совпадать, чтобы фрагменты кода считались 
дублирующимися. Токены — это основные элементы кода, такие как ключевые слова, операторы, идентификаторы и т.д. 

`<targetDirectory>` - позволяет задавать целевую директорию для отчетов

`<failOnViolation>` - определяет, будет ли сборка Maven завершаться с ошибкой, если будут обнаружены нарушения правил PMD 

`<printFailingErrors>` - определяет, будут ли выведены сообщения о нарушениях в консоль
