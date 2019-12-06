package com.projectRest.constant;

public enum Message {

    EXIST(" ya existe"),
    NOT_EXIST(" no existe"),
    WORKDAY_WITH("La jornada con nombre: "),
    WORKDAY("Jornada "),
    ROLE_WITH("El rol con nombre: "),
    ROLE("Rol"),
    ERROR_CREATE_WORKDAY("Error creando la jornada"),
    NOT_GET_INFORMATION_LIST("No se pudo obtener la informacion de la lista"),
    NO_EXIST_WORKDAY("No existen jornadas"),
    NO_EXIST_ROL("No existen roles"),
    NOT_UPDATE(" no se pudo actualizar"),
    NOT_DELETE(" no se pudo eliminar"),
    WORKDAY_DELETE(" Jornada eliminada"),
    FORMAT_REQUEST_WRONG("Formato de petición incorrecto. Debe enviar los datos correctos"),
    ROLE_DELETE("Rol eliminado"),
    USER_WITH("El usuario con nombre: "),
    USER("Usuario ");


    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
