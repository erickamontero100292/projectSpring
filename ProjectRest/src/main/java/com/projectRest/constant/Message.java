package com.projectRest.constant;

public enum Message {

    EXIST(" ya existe"),
    NOT_EXIST(" no existe"),
    WORKDAY_WITH("La jornada con nombre: "),
    ROL_WITH("El rol con nombre: "),
    ERROR_CREATE_WORKDAY("Error creando la jornada"),
    NOT_GET_INFORMATION_LIST("No se pudo obtener la informacion de la lista"),
    NO_EXIST_WORKDAY("No existen jornadas"),
    NO_EXIST_ROL("No existen roles"),
    NOT_UPDATE(" no se pudo actualizar"),
    NOT_DELETE(" no se pudo eliminar"),
    WORKDAY_DELETE(" Jornada eliminada"),
    FORMAT_REQUEST_WRONG("Formato de petición incorrecto. Debe enviar los datos correctos");


    private final String mesage;

    Message(String mesage) {
        this.mesage = mesage;
    }

    public String getMesage() {
        return mesage;
    }
}
