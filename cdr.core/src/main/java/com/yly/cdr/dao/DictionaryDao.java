package com.yly.cdr.dao;

import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.yly.cdr.dto.DictionaryDto;

/**
 * 
 * @author wen_ruichao
 * @version 1.0
 */
public interface DictionaryDao
{
    /**
     * @return 字典列表
     */
    public List<DictionaryDto> selectDictionaries();

    @Arguments({ "csId" })
    public List<DictionaryDto> selectDictionariesBypara(String csId);

    @Arguments({ "csId" })
    public List<DictionaryDto> selectDictionariesByCodeValuePara(String csId);

}
