package com.founder.cdr.batch.writer;

/**
 * 业务消息Writer接口
 * 
 * @author wen_ruichao
 */
public interface BusinessWriter<T>
{

    /**
     * dto数据校验是否通过。业务场景为：dto中某个属性，不同的场景可为空，也可为非空。这样，在公共校验时，不对此此属性做校验，所以需要业务自己去校验。
     * 
     * @param t
     * @return true:通过；false:未通过。
     */
    public boolean validate(T t);

    /**
     *  1：如果当前状态为新增
     *      1.1：如果当前消息不是关联消息：返回true
     *      1.2：如果当前消息是关联消息：判断被关联消息是否存在，存在:true；不存在:false。
     *  2：如果当前状态为更新：判断新增消息是否存在，存在:true；不存在:false。
     *  
     * @param t
     * @return 
     */
    public boolean checkDependency(T t,boolean falg);

    /**
     * 保存
     * 
     * @param t
     * @return true:是；false:否。
     */
    public void saveOrUpdate(T t);
}
