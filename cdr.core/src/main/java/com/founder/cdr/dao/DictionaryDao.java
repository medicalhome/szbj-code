package com.founder.cdr.dao;

import java.util.List;

import com.founder.cdr.dto.DictionaryDto;
import com.founder.fasf.core.util.daohelper.annotation.Arguments;

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
