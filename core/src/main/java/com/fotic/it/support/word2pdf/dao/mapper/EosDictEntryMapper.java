package com.fotic.it.support.word2pdf.dao.mapper;

import com.fotic.it.support.word2pdf.dao.entity.SignConfigurationInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: mfh
 * @Date: 2019-05-06 14:02
 **/
@Mapper
@Component
public interface EosDictEntryMapper {
    /**
     *
     * @param typeId
     * @param id
     * @return
     */
    String getDictName(@Param(value = "typeId") String typeId, @Param(value = "id") String id);

    /**
     * 获取 eos_dict_entry 表的
     * 1、ftp_address
     * 2、ftp_port
     * 3、ftp_user
     * 4、ftp_pwd
     * 5、wordpdf_url_rt
     * 6、wordpdf_url_xin
     * 条件为 dicid 分别等于以上值
     * @return
     */
    SignConfigurationInfoEntity getSignConfigurationInfoEntity();
}
