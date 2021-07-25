# Testing_Abstract_REST_API
В данном репозитории будет приведено мое видение тестирования в 
финтех сфере, на примере метода создание платежа абстрактного REST API.

При составлении списка проверок и написании кейсов
был сделан ряд допущений относительно изначального задания. 
Данные допущения будут указанны в данном файле в скобочках, 
либо приведены комментариями в проекте.

Так же не учитывался временной ресурс затрачиваемый на прогон тестов, 
т.к. данных по среднему времени ответа API нет 
и данный аспект не озвучен в исходной постановке. 

###Список проверок которые необходимо провести:
* Позитивные:
    * Базовые успешные сценарии с присутствием всех полей / только обязательных в запросе 
    и граничными условиями
    * Провести проверку на спецсимволы и кириллицу для строковых значений, и начало с 0 для числовых 
      (предполагаю что приложение должно корректно реагировать на них)
* Негативные:
    * Неуспешные сценарии с превышением граничных условий (все поля / только обязательные)
    * Неуспешные сценарии с отсутствием обязательных полей
    * Неуспешный сценарий с не специфицированным полем 
      (предполагаю, что такое поведение должно быть корректным исходя из контекста безопасности)
    * Неуспешный сценарий с неверным типом данных в поле
    * Неуспешный запрос http методом отличным от POST (например PUT)
    * Повторный запрос с идентичными данными (предполагаю, что дублирующий запрос должен отбиться с ошибкой)
    
###Проверки, которые не реализовал в проекте, но считаю необходимыми:
* Проверка контекста безопасности (если они предусмотренны архитектурой):
    * Попытка сформировать запрос с неверными сертификатами / идентификаторами для авторизации
    * Попытка сформировать запрос системой у которой нет доступа для данного метода API
    * Попытка сформировать запрос с чужими сертификатами / идентификаторами для авторизации
    
* Проверки бизнес логики
    * Сохранение и изменение объектов в БД, движение средств
    * Проверки на некорректность объектов, например несуществующие расчетные счета или клиент
    * Проверка лимитов, если они предусмотрены архитектурой 
    * Поступление событий для асинхронной обработки, если это предусмотренно логикой
    * Поведение API при невозможности обработать корректный запрос по каким либо причинам  
