package com.pdev.rempms.recruitmentservice.mapper.generics;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericMapper<MODEL, RequestDTO, ResponseDTO> {

    public abstract MODEL dtoToModel(MODEL model, RequestDTO dto) throws ParseException;

    public abstract ResponseDTO modelToDto(MODEL model);

    public abstract List<MODEL> dtoToModelList(List<RequestDTO> dtoList);

    public List<ResponseDTO> modelToDtoList(List<MODEL> modelList) {
        List<ResponseDTO> dtoList = new ArrayList<>();
        if (!modelList.isEmpty()) {
            for (MODEL model : modelList) {
                dtoList.add(modelToDto(model));
            }
            return dtoList;

        } else {
            return null;
        }
    }

}
