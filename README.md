## Bank project for Haulmont

- Spring 1.5
- vaadin 8
- hsqldb
- lombok

Run project from the command line - use ``` mvn spring-boot:run``` and open in any browser [localhost:8080](https://localhost:8080/)

# Замечания проверяющего:
## Исправленные:
- Если ввести некорректные данные при создании пользователя, будет совершенно непонятно, где именно они некорректные.
- Отредактировать клиента, не меняя паспорт, невозможно - пишет, что такой паспорт уже существует.
- В номер телефона можно вводить буквы.
- Email тоже не донца правильно валидируется.
- Можно создавать отрицательные кредитные лимиты и проценты. Так точно задумывалось? Можно брать отрицательные кредиты.

#### Сделал проверки на валидность.
- Присутствуют статические изменяемые поля.
- По обработке исключений не всегда понятно, что конкретно за ним скрывается. Ни для пользователя, ни для программиста.
- В сервисах иногда у полей/методов отсутствуют модификаторы доступа.

#### По возможности пофиксил, где нашёл.
## Не исправленные:
- При удалении кредита/клиента вместо этого падает ошибка, если с ними есть связанные сущности. 

#### Считаю это вполне оправданной логикой, что нельзя удалять данные из банка пока у этого клиента есть кредит.
- Встречаются магические константы (1500, например).

## Это просто милисекунды для сообщений, чтобы сами исчезали за приятное глазу время.
- Почему-то период кредита строго ограничен десятью константами.

#### Тоже логично, что дают кредит только на определеный срок.
