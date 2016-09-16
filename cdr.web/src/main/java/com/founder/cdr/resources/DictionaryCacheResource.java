package com.founder.cdr.resources;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.founder.cdr.cache.DictionaryCache;

/**
 * 字典缓存WebService接口实现。
 */
@Path("/dictionarycache")
public class DictionaryCacheResource
{

    private static final Logger logger = LoggerFactory.getLogger(DictionaryCacheResource.class);

    @Autowired
    private DictionaryCache dictionaryCache;

    /**
     * 更新字典缓存。
     * 
     * @return Response
     */
    @PUT
    @Path("{csId}")
    public Response update(@PathParam("csId") String csId)
    {
        try
        {
            logger.debug("CDR Portal字典缓存，正在更新...");
            dictionaryCache.update(csId);
            logger.debug("CDR Portal字典缓存，更新成功。");
        }
        catch (Exception e)
        {
            logger.error("CDR Portal字典缓存，更新失败！");
            e.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok().build();
    }
}
