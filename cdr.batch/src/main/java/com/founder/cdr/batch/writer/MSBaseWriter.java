package com.founder.cdr.batch.writer;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.cdr.cache.DictionaryCache;
import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.CodeSystem;
import com.founder.cdr.hl7.dto.CodeValueDto;
import com.founder.cdr.hl7.dto.CodeValues;
import com.founder.cdr.hl7.dto.MSBaseDto;
import com.founder.cdr.service.DictionaryService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;
import com.founder.hl7.util.PropertiesUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * 通用术语
 * 
 * @author tong_meng
 */
@Component(value = "msbaseWriter")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public abstract class MSBaseWriter<T> implements
        BusinessWriter<MSBaseDto<CodeValueDto>>
{

    public Logger logger = LoggerFactory.getLogger(MSBaseWriter.class);

    @Autowired
    private BeanFactory beanFactory;

    // 要更新的CodeSystem的实体
    public CodeSystem codeSystem;

    // 要更新的集合
    public List<Object> updateList;

    // 要删除的集合
    public List<Object> deleteList;

    // 要新增的集合
    public List<Object> insertList;

    // 系统时间
    public Date systemDate;

    @Autowired
    public DictionaryService dictionaryService;

    /**
     * 关联code_system表
     * 
     * @param t
     * @return 是否找到code_system
     */
    @Override
    public boolean validate(MSBaseDto<CodeValueDto> t)
    {
        // 关联code_system表
        List<CodeSystem> codeSystems = dictionaryService.selectCodeSystems(t.getMessageId());
        logger.debug("关联的编码体系管理表是否存在编码代码为    {}  这样的数据，是否可以对字典表操作！ ：  {}",
                t.getMessageId(), codeSystems != null
                    && codeSystems.size() != 0);
        // 看关联记录是否存在
        if (codeSystems == null || codeSystems.size() == 0)
            return false;
        // 取出code_system记录
        codeSystem = codeSystems.get(0);
        return true;
    }

    /**
     * 如果消息传过来没有数据，不做处理。
     */
    @Override
    public boolean checkDependency(MSBaseDto<CodeValueDto> t,boolean flag)
    {
        return true;
    }

    /**
     * 先找到消息在CDR中是否存在记录， 然后对消息的类型进行分类
     */
    @Override
    public void saveOrUpdate(MSBaseDto<CodeValueDto> t)
    {
        // 将message分门别类
        this.setMessageList(t);
        systemDate = DateUtils.getSystemTime();
        // 从消息中解析出来entity
        List<Object> insertEntities = getInsertEntities();
        List<Object> updateEntities = getUpdateEntities();
        List<Object> deleteEntities = getDeleteEntities();
        // 和code_system数据比版本号
        compareVersionWithCodeSystem(t);
        dictionaryService.saveAllList(insertEntities, updateEntities,
                deleteEntities, codeSystem);
        // $Author :wu_biao
        // $Date : 2012/10/10 14:44$
        // [BUG]9449 ADD BEGIN
        String csId = t.getMessageId();
        try
        {
            DictionaryCache dictionaryCache = (DictionaryCache) beanFactory.getBean("dictionaryCache");
            dictionaryCache.update(csId);
            logger.debug("CDR Batch字典缓存更新成功。");
        }
        catch (Exception e)
        {
            logger.debug("CDR Batch字典缓存更新失败。");
            e.printStackTrace();
        }
        try
        {
            WebResource webResource = Client.create().resource(
                    UriBuilder.fromUri(PropertiesUtils.getProperty("cdr.web")).build());
            logger.debug("CDR Portal字典缓存，正在更新...");
            if (this.checkURL(webResource.toString())) {
				webResource.path("webservice").path("dictionarycache")
						.path(csId).put(ClientResponse.class);
				logger.debug("CDR Portal字典缓存，更新成功。");
			}
            logger.debug("CDR Portal字典缓存，更新成功。");
        }
        catch (Exception e)
        {
            logger.error("CDR Portal字典缓存，更新失败！");
            e.printStackTrace();
        }
        // [BUG]9449 ADD END
    }

    /**
     * 消息与code_system比较版本号
     */
    private void compareVersionWithCodeSystem(MSBaseDto<CodeValueDto> t)
    {
        // 如果消息的版本号高于code_system的版本号，设置code_system的版本
//        if (messageVersionToCodeSystem(t.getMessageVersionNo(),
//                codeSystem.getVersionNo()))
//        {
//            logger.debug("消息版本比数据库版本高，更新code_system表的版本。");
//            codeSystem.setVersionNo(t.getMessageVersionNo());
//            codeSystem.setUpdateTime(systemDate);
//        }
//        // 如果低于，将code_syetem的值设置为空，后台不做处理
//        else
//        {
        logger.debug("消息版本比数据库版本低，不更新code_system表的版本。");
        codeSystem = null;
//        }
    }

    /**
     * 解析删除集合里的消息数据 将要更新的数据，放到要删除实体的集合中
     * 
     * @return
     */
    private List<Object> getDeleteEntities()
    {
        List<Object> deleteEntities = new ArrayList<Object>();
        for (Object object : deleteList)
        {
            CodeValueDto codeValue = (CodeValueDto) object;
            deleteEntities.add(getDeleteCodeValue(codeValue));
        }
        logger.debug("需要删除{}条记录！",
                deleteEntities == null || deleteEntities.size() == 0 ? 0
                        : deleteEntities.size());
        return deleteEntities;
    }

    /**
     * 解析更新集合里的消息数据 将要更新的数据，放到要更新实体的集合中
     * 
     * @return
     */
    private List<Object> getUpdateEntities()
    {
        List<Object> updateEntities = new ArrayList<Object>();
        for (Object object : updateList)
        {
            CodeValueDto codeValue = (CodeValueDto) object;
            updateEntities.add(getUpdateCodeValue(codeValue));
        }
        logger.debug("需要更新{}条记录！",
                updateEntities == null || updateEntities.size() == 0 ? 0
                        : updateEntities.size());
        return updateEntities;
    }

    /**
     * 解析新增集合里的消息数据 将新增的数据，添加到新增实体集合中
     * 
     * @return
     */
    private List<Object> getInsertEntities()
    {
        List<Object> insertEntities = new ArrayList<Object>();
        for (Object object : insertList)
        {
            CodeValueDto codeValue = (CodeValueDto) object;
            insertEntities.add(getNewCodeValue(codeValue));
        }
        logger.debug("需要新增{}条记录！",
                insertEntities == null || insertEntities.size() == 0 ? 0
                        : insertEntities.size());
        return insertEntities;
    }

    /**
     * 根据操作类型找到要更新或者要删除的数据，放到相应的List里
     */
    public void setMessageList(MSBaseDto<CodeValueDto> t)
    {
        // 取出消息中要操作的数据
        List<CodeValueDto> codeValueDtos = t.getMesageRecords();
        insertList = new ArrayList<Object>();
        updateList = new ArrayList<Object>();
        deleteList = new ArrayList<Object>();
        for (CodeValueDto codeValueDto : codeValueDtos)
        {
            // $Author :tong_meng
            // $Date : 2012/11/05 11:00$
            // [BUG]0011055 ADD BEGIN
            // 从DB中找到关联的数据
            com.founder.cdr.dto.CodeValueDto record = getMessageRecordFromDB(
                    getTableName(), codeValueDto.getUniqueId(),
                    codeValueDto.getForeignKey());
            /*
             * $Author: yu_yzh
             * $Date: 2015/8/28 13:52
             * 操作类型为新增，但DB中检索到记录，做更新操作
             * MODIFY BEGIN
             * */
            
            // 如果是新增的消息
			if (isNewMessage(codeValueDto.getOptType()) && record == null) {
				processNewMessage(t, codeValueDto); // 处理新增消息，保存信息到相应的insertEntity
			}
			// 如果是更新消息
			else if ((isNewMessage(codeValueDto.getOptType()) && record != null ) || isUpdateMessage(codeValueDto.getOptType())){
				processUpdateMessage(t, record, codeValueDto);// 处理更新消息，保存信息到相应的updateEntity
			}
			// MODIFY END
			// 如果是删除的消息
			else if (isDeleteMessage(codeValueDto.getOptType())) {
				processDeleteMessage(t, record, codeValueDto); // 处理要删除的消息，保存信息到deleteEntity
			}
        }
    }

    /**
     * 对新增或者更新的消息进行逻辑处理。
     * 
     * @param t 消息bean
     * @param record 数据库中检索出的对应的字典记录
     * @param codeValueDto 消息中的字典bean
     */
    private void processUpdateMessage(MSBaseDto<CodeValueDto> t,
            com.founder.cdr.dto.CodeValueDto record, CodeValueDto codeValueDto)
    {
    	/*
    	 * $Author: yu_yzh
    	 * $Date: 2015/8/28 10:31
    	 * 港大不发版本号，根据时间戳判断时间进行更新操作
    	 * DELETE BEGIN
    	 * */ 
    	/*Class classlocal;
    	String version = null;
		try {
			classlocal = Class.forName(codeValueDto.getClass().getName().toString());
			Object obj = codeValueDto;
			Method setinfo = classlocal.getMethod("getVersionNo");
			version = (String)setinfo.invoke(obj) ;
		}catch (Exception e)
        {
            logger.error("字典类型匹配错误");
            e.printStackTrace();
        }
		
        logger.debug("新增或者更新消息时");
        if (record != null)
        {
            logger.debug("数据库中消息记录存在code为{}数据。", codeValueDto.getUniqueId());
            if (messageVersionToCodeValue(version,record.getVersionNo()))
            {
                logger.debug("消息中的版本比数据库中的消息版本高，可以更新code为{}数据。",
                        codeValueDto.getUniqueId());
                updateList.add(codeValueDto);
            }
            else
                logger.debug("消息中的版本比数据库中的消息版本低，不能新增和更新code为{}数据。",
                        codeValueDto.getUniqueId());
        }
        else
        {
            logger.debug("数据库中消息记录不存在code为{}数据。", codeValueDto.getUniqueId());
            insertList.add(codeValueDto);
            logger.debug("新增code为{}的消息。", codeValueDto.getUniqueId());
        }*/
    	// DELETE END
    	
    	// 通过时间戳判断是否进行更新操作
    	String msgCreateTime = getMessageCreateTime(t);
    	logger.debug("更新消息操作：");
    	if(record != null){
    		logger.debug("数据库中消息记录存在code为{}数据。", codeValueDto.getUniqueId());
    		Date updateTime = record.getUpdateTime();
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    		Date oTime = null;
    		if(msgCreateTime != null){
    			try {
    				oTime = sdf.parse(msgCreateTime);
    			} catch (ParseException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    		if(oTime != null){
    			if(oTime.compareTo(updateTime) > 0){
    				logger.debug("消息创建时间大于数据库中的更新时间，可以更新code为{}数据。",
                            codeValueDto.getUniqueId());
                    updateList.add(codeValueDto);
    			} else {
    				logger.debug("消息创建时间不大于数据库中的更新时间，不更新code为{}数据。",
                            codeValueDto.getUniqueId());
    			}
    		} else {
    			logger.debug("消息中无创建时间，不能更新code为{}数据。",
                        codeValueDto.getUniqueId());
    		}
    	} else {
    		logger.debug("数据库中消息记录不存在code为{}数据。", codeValueDto.getUniqueId());
            insertList.add(codeValueDto);
            logger.debug("新增code为{}的消息。", codeValueDto.getUniqueId());
    	}
    }

    /**
	 * 对新增的消息进行逻辑处理。
	 * 
	 * @param t
	 * @param record
	 * @param codeValueDto
	 */
	private void processNewMessage(MSBaseDto<CodeValueDto> t, CodeValueDto codeValueDto) {
//		logger.debug("新增或者更新消息时");
		logger.debug("新增消息：");
		logger.debug("数据库中消息记录不存在code为{}数据。", codeValueDto.getUniqueId());
		insertList.add(codeValueDto);
		logger.debug("新增code为{}的消息。", codeValueDto.getUniqueId());
	}
	
    /**
     * 对删除的消息进行逻辑处理
     * 
     * @param t
     * @param record
     * @param codeValueDto
     */
    private void processDeleteMessage(MSBaseDto<CodeValueDto> t,
            com.founder.cdr.dto.CodeValueDto record, CodeValueDto codeValueDto)
    {
    	/*
    	 * $Author: yu_yzh
    	 * $Date: 2015/8/28 13:37
    	 * 港大不发送版本号，根据时间戳判断是否进行删除操作
    	 * DELETE BEGIN
    	 * */
    	/*Class classlocal;
    	String version = null;
		try {
			classlocal = Class.forName(codeValueDto.getClass().getName().toString());
			Object obj = codeValueDto;
			Method setinfo = classlocal.getMethod("getVersionNo");
			version = (String)setinfo.invoke(obj) ;
		}catch (Exception e)
        {
            logger.error("字典类型匹配错误");
            e.printStackTrace();
        }
		
        logger.debug("删除消息时");
        if (record != null)
        {
            logger.debug("数据库中消息记录存在code为{}数据。", codeValueDto.getUniqueId());
            if (Constants.NO_DELETE_FLAG.equals(record.getDeleteFlag()))
            {
                logger.debug("该消息未被删除。");
                if (messageVersionToCodeValue(version,record.getVersionNo()))
                {
                    logger.debug("消息中的版本比数据库中的消息版本高，可以删除code为{}数据。",
                            codeValueDto.getUniqueId());
                    deleteList.add(codeValueDto);
                }
                else
                    logger.debug("消息中的版本比数据库中的消息版本低，不能删除code为{}数据。",
                            codeValueDto.getUniqueId());
            }
            else
                logger.debug("该消息已被删除。");
        }
        else
        {
            logger.debug("数据库中消息记录不存在code为{}数据。", codeValueDto.getUniqueId());
            insertList.add(codeValueDto);
            logger.debug("新增code为{}的消息。", codeValueDto.getUniqueId());
        }*/
        // DELETE END
    	
    	// 根据时间戳判断是否进行删除操作
    	String msgCreateTime = getMessageCreateTime(t);
    	logger.debug("删除消息：");
    	if(record != null){
    		
    		if(Constants.DELETE_FLAG.equals(record.getDeleteFlag())){
    			logger.debug("该消息已被删除。");
    		} else {
    			logger.debug("数据库中消息记录存在code为{}数据。", codeValueDto.getUniqueId());
        		Date updateTime = record.getUpdateTime();
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        		Date oTime = null;
        		if(msgCreateTime != null){
	        		try {
	    				oTime = sdf.parse(msgCreateTime);
	    			} catch (ParseException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
        		}
        		if(oTime != null){
        			if(oTime.compareTo(updateTime) > 0){
        				logger.debug("消息创建时间大于数据库中的更新时间，可以删除code为{}数据。",
                                codeValueDto.getUniqueId());
        				deleteList.add(codeValueDto);
        			} else {
        				logger.debug("消息创建时间不大于数据库中的更新时间，不删除code为{}数据。",
                                codeValueDto.getUniqueId());
        			}
        		} else {
        			logger.debug("消息中无创建时间，不删除code为{}数据。",
                            codeValueDto.getUniqueId());
        		}
    		}
    	}
    }

    /**
     * 得到新增的code_value 实体
     * 
     * @param codeValue
     * @return
     */
    abstract public Object getNewCodeValue(CodeValueDto codeValue);

    /**
     * 得到删除的实体
     * 
     * @param codeValue
     * @return
     */
    abstract public Object getDeleteCodeValue(CodeValueDto codeValue);

    /**
     * 得到要更新的实体
     * 
     * @param codeValue
     * @return
     */
    abstract public Object getUpdateCodeValue(CodeValueDto codeValue);

    /**
     * 表名
     * 
     * @return
     */
    abstract public String getTableName();

    // $Author :tong_meng
    // $Date : 2012/11/05 11:00$
    // [BUG]0011055 MODIFY BEGIN
    // $Author :tong_meng
    // $Date : 2012/10/25 15:00$
    // [BUG]0010742 MODIFY BEGIN
    /**
     * 根据表名找到表中的记录
     * 
     * @param tableName 表名
     * @param codeValue 单条dto中的数据
     * @return true：找到记录；false：未找到记录
     */
    public com.founder.cdr.dto.CodeValueDto getMessageRecordFromDB(
            String tableName, String code, String foreignKey)
    {
        com.founder.cdr.dto.CodeValueDto codeValue = dictionaryService.selectRecordExistByTableName(
                tableName, code, codeSystem.getCsId(), codeSystem.getCsCode(),
                foreignKey);
        return codeValue;
    }

    // [BUG]0010742 MODIFY END
    // [BUG]0011055 MODIFY END

    /**
     * 是否是删除消息
     * 
     * @param c
     * @return
     */
    public boolean isDeleteMessage(String newUpFlag)
    {
        return Constants.DELETE_MESSAGE_FLAG.equals(newUpFlag);
    }

    /**
     * 是否是新增消息
     * 
     * @param c
     * @return
     */
    public boolean isNewMessage(String newUpFlag)
    {
        return Constants.INSERT_MESSAGE_FLAG.equals(newUpFlag);
    }

    /**
     * 是否是更新消息
     * 
     * @param c
     * @return
     */
    public boolean isUpdateMessage(String newUpFlag)
    {
        return Constants.UPDATE_MESSAGE_FLAG.equals(newUpFlag);
    }

    /**
     * 消息中的数据与数据库中的数据版本比较
     * 
     * @param m
     * @return true 消息中的版本高 false 数据库中的版本高
     */
    public boolean messageVersionToCodeValue(String messageVersionNo,
            String versionNo)
    {
        // $Author :tong_meng
        // $Date : 2012/9/13 14:44$
        // [BUG]0009699 ADD BEGIN 判断数据库中的版本为null的情况。
        if (versionNo == null || StringUtils.isEmpty(versionNo))
            return true;
        // [BUG]0009699 ADD END
        return StringUtils.strToDouble(messageVersionNo) > StringUtils.strToDouble(versionNo);
    }

    /**
     * 与code_system 比较版本
     * 
     * @param versionNo code_system 的版本
     * @param messageVersionNo 消息中的版本
     * @return true：消息中的版本高 false:消息中的版本低
     */
    private boolean messageVersionToCodeSystem(String messageVersionNo,
            String versionNo)
    {
        // $Author :tong_meng
        // $Date : 2012/9/13 14:44$
        // [BUG]0009699 ADD BEGIN 判断数据库中的版本为null的情况。
        if (StringUtils.isEmpty(versionNo))
            return true;
        // [BUG]0009699 ADD END
        return StringUtils.strToDouble(messageVersionNo) > StringUtils.strToDouble(versionNo);
    }
    
    public boolean checkURL(String rs) {
		boolean flag = false;

		try {
			URL url = new URL(rs);
			InputStream in = url.openStream();
			flag = true;
		} catch (Exception e1) {
			logger.debug("CDR Portal页面连接{}打不开", rs);
			flag = false;
		}
		return flag;
	}
    /*
     * $Author: yu_yzh
     * $Date: 2015/8/28 10:43
     * ADD BEGIN
     * */
    /**
     * 获取消息创建时间
     * */
    private String getMessageCreateTime(MSBaseDto<CodeValueDto> t){
    	Class clazz;
    	String createTime = null;
    	try{
    		clazz = t.getClass();
    		Method getOperationTimeMethod = clazz.getMethod("getCreateTime");
    		createTime = (String) getOperationTimeMethod.invoke(t);
    	} catch(Exception e){
    		logger.error("字典类型匹配错误");
            e.printStackTrace();
    	}
    	return createTime;
    }
    // ADD END
}
