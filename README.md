# Итоговое задание

## Описание модулей приложения

### Controllers
Модуль контроллеры нужен для реализации веб части, для получения 
запросов и их обработки.

### Dto
Модуль dto (data transfer object) нужен для корректного обмена данными
между контроллерами и бизнес слоем. В нем будут содержаться объекты оболочки
для пересылки объектов.

### Business
В этом модуле будет описана вся бизнес логика приложения, реализованы сервисы
дл каждой из сушностей и остальной необходимый функционал.

### Dao
В модуле dao (data access object) будут реализованы объекты для связи с базой
данных, получения информации.

### Models
В модуле Models будут описаны модели, которые нужны для обмена данными между
бизнем слоем и dao(слоем доступа к данным). Для каждой из сущностей будет написана
своя модель.



### Commons
В данном модуле будут храниться утилитный и вспомогательный инструментарий
и дополнительные классы, перечисления необходимые для остальных модулей. Например,
статусы.
## Диаграмма архитектуры приложения

![App diagram](https://github.com/SsDp812/finalJavaTask/blob/main/diagram.png)

#Тестирование
##Ссылка на отчет по функциональному тестированию
https://docs.google.com/spreadsheets/d/1cdgyPRl3j5yEhDI6uSoP2qaPgu6IgF3pCiMT7qQT1zM/edit?usp=sharing