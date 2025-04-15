package com.pdev.rempms.candidateservice.builder.samples;

public class Test1 {

//    public CommonResponse searchTravelAgent(TravelAgentSearchDTO travelAgentSearchDTO) {
//        CommonResponse commonResponse = new CommonResponse();
//        List<TravelAgent> travelAgents = new ArrayList<>();
//        Page<TravelAgent> travelAgentPage;
//
//        List<SearchCriteria.Filter> filters = new ArrayList<>();
//        LocalDateTime parsedFromDate = null;
//        LocalDateTime parsedToDate = null;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        if (travelAgentSearchDTO.getFromDate() != null && !travelAgentSearchDTO.getFromDate().isEmpty()) {
//            parsedFromDate = LocalDate.parse(travelAgentSearchDTO.getFromDate(), formatter).atStartOfDay();
//        }
//
//        if (travelAgentSearchDTO.getToDate() != null && !travelAgentSearchDTO.getToDate().isEmpty()) {
//            parsedToDate = LocalDate.parse(travelAgentSearchDTO.getToDate(), formatter).atTime(23, 59, 59);
//        }
//        if (travelAgentSearchDTO.getBrNumber() != null && !travelAgentSearchDTO.getBrNumber().isEmpty()) {
//            filters.add(SearchCriteria.Filter.builder()
//                    .field("brNumber")
//                    .operator(
//                            SearchCriteria.Filter.QueryOperator.EQUALS_IGNORE_CASE)
//                    .value(travelAgentSearchDTO.getBrNumber())
//                    .build());
//        }
//
//        if (travelAgentSearchDTO.getTravelAgentName() != null && !travelAgentSearchDTO.getTravelAgentName().isEmpty()) {
//            filters.add(SearchCriteria.Filter.builder()
//                    .field("agencyName")
//                    .operator(
//                            SearchCriteria.Filter.QueryOperator.EQUALS_IGNORE_CASE)
//                    .value(travelAgentSearchDTO.getTravelAgentName())
//                    .build());
//        }
//
//        if (travelAgentSearchDTO.getFromDate() != null && !travelAgentSearchDTO.getFromDate().isEmpty()) {
//            filters.add(SearchCriteria.Filter.builder()
//                    .field("createdDateTime")
//                    .operator(
//                            SearchCriteria.Filter.QueryOperator.GREATER_THAN_DATE)
//                    .dateValue(parsedFromDate)
//                    .build());
//        }
//
//        if (travelAgentSearchDTO.getToDate() != null && !travelAgentSearchDTO.getToDate().isEmpty()) {
//            filters.add(SearchCriteria.Filter.builder()
//                    .field("createdDateTime")
//                    .operator(
//                            SearchCriteria.Filter.QueryOperator.LESS_THAN_DATE)
//                    .dateValue(parsedToDate)
//                    .build());
//        }
//        SearchCriteria searchCriteria = SearchCriteria.builder().filters(filters)
//                .build();
//
//        TravelAgentPageResponseDTO travelAgentPageResponseDTO = new TravelAgentPageResponseDTO();
//
//        travelAgentPage = (Page<TravelAgent>) travelAgentRepository.findAll(entitySpecification.specificationBuilder(searchCriteria), PageRequest.of(travelAgentSearchDTO.getPageNo(), travelAgentSearchDTO.getSize()));
//        travelAgentPageResponseDTO.setTotalCount(travelAgentPage.getTotalElements());
//        travelAgents.addAll(travelAgentPage.getContent());
//
//        List<TravelAgentResponseDTO> travelAgentResponseList = new ArrayList<>();
//        travelAgents.forEach(
//                travelAgent -> travelAgentResponseList.add(travelAgentMapper.toDto(new TravelAgentResponseDTO(), travelAgent))
//        );
//
//        travelAgentPageResponseDTO.setTravelAgentResponseDTOList(travelAgentResponseList);
//
//        if (!travelAgents.isEmpty()) {
//            commonResponse.setData(travelAgentPageResponseDTO);
//            commonResponse.setMessage("Travel Agent detail fetched successfully");
//        } else {
//            commonResponse.setData(new ArrayList<>());
//            commonResponse.setMessage("Travel Agent details not found");
//        }
//        commonResponse.setTimestamp(LocalDateTime.now());
//        commonResponse.setStatus(HttpStatus.OK);
//        return commonResponse;
//    }

}
