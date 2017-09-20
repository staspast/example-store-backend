package com.example.store.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class StoreMapper extends ModelMapper {


    public <DTO> List<DTO> mapList(List<?> sourceList, Class<DTO> itemClass) {
        return sourceList.stream()
                .map(e -> map(e, itemClass))
                .collect(Collectors.toList());
    }

//    @SuppressWarnings("unchecked")
//    public <DTO> PageDto<DTO> mapPage(Page<?> page, Class<DTO> itemClass) {
//        PageDto pageDto = map(page, PageDto.class);
//        pageDto.setContent(mapList(page.getContent(), itemClass));
//        return pageDto;
//    }

}
