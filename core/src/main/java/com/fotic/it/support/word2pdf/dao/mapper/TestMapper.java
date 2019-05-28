package com.fotic.it.support.word2pdf.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TestMapper {

    Integer findCount();
}
