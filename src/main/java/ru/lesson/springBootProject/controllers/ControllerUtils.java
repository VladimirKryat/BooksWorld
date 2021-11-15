package ru.lesson.springBootProject.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class ControllerUtils {
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
    public static boolean checkFilename(MultipartFile file){
        return file != null && !file.getOriginalFilename().isEmpty();
    }
    public static String saveFileWithNewFilename(MultipartFile file, String uploadPath) throws IOException {
        File uploadDir = new File(uploadPath);
        //проверяем наличие директории для загрузки
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String resultFilename = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFilename));
        return resultFilename;
    }
}
