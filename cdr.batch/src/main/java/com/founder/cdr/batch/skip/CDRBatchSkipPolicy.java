package com.founder.cdr.batch.skip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

import com.founder.cdr.batch.exception.ExpectedException;

/**
 * 当Reader、Processor和Writer抛出例外的时候，SpringBatch会调用skip机制，来判断当前例外发生时，
 * 正在被处理的记录是否被跳过。当在上面的代码中配置<skippable-exception-classes />属性的时候，
 * SpringBatch会默认的调用LimitCheckingItemSkipPolicy类。如果简单的配置skip-limit
 * 和skippable-exception-classes不能满足需求时，也可以定义自己的skip策略。
 * 
 * 当Reader、Processor和Writer抛出例外的时候，SpringBatch处理skip策略的方式是不同的。
 * 当Reader发生可以被skip的例外时，SpringBatch会接着去读下面一条记录，并不会回滚事务。
 * 
 * 当Processor发生可以被skip的例外时，SpringBatch会回滚当前chunk的事务，并将除了引发例外以外的数据传给Writer。
 * 
 * 当Writer发生可以被skip的例外的时，SpringBatch首先回滚事务，因为传给Writer的是一个list，
 * 所以Writer不知道是list中那条记录造成了例外的发生。Writer会将list拆开，一条条的处理，正确的数据提交，错误的数据回滚。
 * @author wen_ruichao
 * 
 */
public class CDRBatchSkipPolicy implements SkipPolicy
{

    private static final Logger logger = LoggerFactory.getLogger(CDRBatchSkipPolicy.class);

    /**
     * 对期望的异常，如：数据结构，数据完整性校验，不需要结束当前step，需要继续处理下一个消息。
     * 对非期望的消息，如：数据库异常，邮件服务异常，MQ服务异常。需要终止当前step！
     * @param t
     * @param skipCount
     * @see com.founder.cdr.batch.exception.ExpectedException
     * @see com.founder.cdr.batch.exception.UnexpectedException
     */
    public boolean shouldSkip(Throwable t, int skipCount)
            throws SkipLimitExceededException
    {
        logger.trace("shouldSkip(): t:{}, skipCount:{}", t, skipCount);
        if (ExpectedException.class.isInstance(t))
        {
            return true;
        }
        return false;
    }
}
