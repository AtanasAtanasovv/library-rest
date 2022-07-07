package com.example.libraryrest.mappers;

import com.example.libraryrest.dto.responses.DeactivationReasonResponse;
import com.example.libraryrest.models.DeactivationReason;

public class DeactivationReasonMapper {

    public DeactivationReasonResponse entityToResponse(DeactivationReason deactivationReason){

        DeactivationReasonResponse response=new DeactivationReasonResponse();
        response.setId(deactivationReason.getId());
        response.setName(deactivationReason.getName());

        return response;
    }
}
