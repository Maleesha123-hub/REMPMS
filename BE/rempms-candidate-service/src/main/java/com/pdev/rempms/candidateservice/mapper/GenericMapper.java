package com.pdev.rempms.candidateservice.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <DTO>
 * @param <MODEL>
 * @author @maleeshasa
 * @Date 2024/02/09
 */
public abstract class GenericMapper<DTO, MODEL> {

    /**
     * This method is allowed to convert model to dto
     *
     * @param model {@link MODEL} - entity
     * @return {@link DTO}
     * @author @maleeshasa
     */
    public abstract DTO modelToDto(MODEL model);

    /**
     * This method is allowed to convert model to dto
     *
     * @param dto {@link DTO} - dto
     * @return {@link MODEL}
     * @author @maleeshasa
     */
    public abstract MODEL dtoToModel(DTO dto);

    /**
     * This method is allowed to convert list of dtoS to list of models
     *
     * @param dtoList {@link List<DTO>} - dto list
     * @return {@link List<MODEL>}
     * @author @maleeshasa
     */
    public List<MODEL> listOfDtoSToListOfModels(List<DTO> dtoList) {

        List<MODEL> modelList = new ArrayList<>();

        if (!dtoList.isEmpty()) {

            modelList = dtoList.stream()
                    .map(this::dtoToModel).toList();

        }

        return modelList;

    }

    /**
     * This method is allowed to convert list of models to list of dtoS
     *
     * @param modelList {@link List<MODEL>} - model list
     * @return {@link List<DTO>}
     * @author @maleeshasa
     */
    public List<DTO> listOfModelsToListOfDtoS(List<MODEL> modelList) {

        List<DTO> dtoList = new ArrayList<>();

        if (!modelList.isEmpty()) {

            dtoList = modelList.stream()
                    .map(this::modelToDto).toList();

        }

        return dtoList;

    }

}
