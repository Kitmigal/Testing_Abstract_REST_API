package helpers

class getRequestApi {

    static Map ReqPaymentCreation() {
        return [
                accountDtId       : [min: 1, max: 100, type: "int", defaultValue: '123456',  required: true],
                accountKtId       : [min: 1, max: 100, type: "int", defaultValue: '123456',  required: true],
                clientId          : [min: 1, max: 100, type: "str", defaultValue: '123qwe',  required: true],//в ТЗ не указанно прямо, принимаю данные ограничения
                notificationId    : [min: 1, max: 100, type: "str", defaultValue: 'qwerty',  required: true],
                paymentDescription: [min: 1, max: 100, type: "str", defaultValue: 'qwerty',  required: false],
                sum               : [min: 1, max: 25,  type: "BD2", defaultValue: '1000.25', required: true],
        ]
    }

    //Допустим, тут приведены мапы возвращающие параметры запросов для других методов

    //Методы для
    static getRequest(Map<String, Map> method, boolean onlyRequired = false, String len = null) {
        Map result = [:]
        method.forEach((key, mapValue) -> {
            if (onlyRequired) {
                mapValue.required ? (result[key] = getMapValue(mapValue, len)) : null
            } else {
                result[key] = getMapValue(mapValue, len)
            }
        })
        return result
    }

    static getMapValue(mapValue, len) {
        switch (len) {
            case 'max': mapValue.defaultValue.padLeft(mapValue.max, '1') //
                break
            case 'min': mapValue.defaultValue.substring(0, mapValue.min)
                break
            default: mapValue.defaultValue
        }
    }
}
