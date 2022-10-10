package com.juansanta.disney.data;

import com.juansanta.disney.dto.Message;


public class FactoryMessageTestData {

    public static Message getMessageNotExistsCharacter() {

        return new Message("El personaje no existe");

    }

    public static Message getMessageMissingFieldUrl() {

        return new Message("La URL de la imagen es obligatoria");

    }

    public static Message getMessageErrorDate() {

        return new Message("La URL de la imagen es obligatoria");

    }

}
