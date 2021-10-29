package ru.lesson.springBootProject.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
    //преобразуем отображение ключей мапы passwordError, usernameError..., а значениями будут заданные в моделях сообщения
    //если при валидации поля возникает две ошибки, то конкатенируем их сообщения
    static Map<String, String> getErrorMap(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> errorCollector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage,
                ((message1, message2) -> message1+", "+message2)
        );
        Map<String, String> errorMap = bindingResult.getFieldErrors().stream().collect(errorCollector);
        return errorMap;
    }
}
