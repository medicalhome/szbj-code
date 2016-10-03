package com.yly.cdr.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.NursingOperation;
import com.yly.cdr.entity.PhysicalExamination;
import com.yly.cdr.service.CareService;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DateUtils;

@Component
public class CareServiceImpl implements CareService
{
    @Autowired
    private CommonService commonService;

    @Autowired
    private EntityDao entityDao;

    /**
     * 新增护理操作、体检检查
     * @param newUpdFlag 更新新增标志
     * @param nursingOperation 护理操作
     * @param phyExamAllList 体检检查List
     */
    @Transactional
    public void saveNurPhyExam(List<Object> nursingOperationList,
            List<Object> physicalExamList)
    {
        // 护理操作新增
        commonService.saveList(nursingOperationList);

        // 体检检查(XML文件中取得到体检检查信息)
        commonService.saveList(physicalExamList);
    }

    @Override
    @Transactional
    public void saveNursingPhyExam(NursingOperation nursingOpreation,
            List<List<Object>> physicalExamList)
    {
        // 护理操作新增
        commonService.save(nursingOpreation);
        // 体检检查
        commonService.saveAllList(physicalExamList);
    }
    
    // Author :jia_yanqing
    // Date : 2013/1/17 13:33
    // [BUG]0013379 ADD BEGIN
    /**
     * 新增护理操作、体检检查
     * @param newUpdFlag 更新新增标志
     * @param nursingOperation 护理操作
     * @param phyExamAllList 体检检查List
     */
    @Transactional
    public void updateNurPhyExam(List<Object> nurOperationDelList,List<Object> physicalExamDelList,
            List<Object> nursingOperationList,
            List<Object> physicalExamList,String serviceId)
    {
        // 更新数据库中原有的护理操作
        if(nurOperationDelList != null && nurOperationDelList.size() != 0)
        {
            for (Object nurOperation : nurOperationDelList)
            {
                NursingOperation nurOperationUpd = (NursingOperation) nurOperation;               
                                
                entityDao.update(nurOperationUpd);
            }
        }
        
        // 更新除数据库中原有的体格检查
        if(physicalExamDelList != null && physicalExamDelList.size() != 0)
        {
            for (Object physicalExam : physicalExamDelList)
            {
                PhysicalExamination physicalExamUpd = (PhysicalExamination) physicalExam;
                                
                entityDao.update(physicalExamUpd);
            }
        }
        /*// 护理操作新增
        commonService.saveList(nursingOperationList);

        // 体检检查(XML文件中取得到体检检查信息)
        commonService.saveList(physicalExamList);*/
    }
    // [BUG]0013379 ADD END
    // Author :chang_xuewen
    // Date : 2013/11/27 15:00
    // [BUG]0039754 ADD BEGIN
    /**
     * 新增护理操作、体检检查
     * @param nurOperationDelList 需要删除的操作记录
     * @param physicalExamDelList 需要删除的体格检查
     * @param serviceId 服务号
     */
    @Transactional
    public void deleteNurPhyExam(List<Object> nurOperationDelList,List<Object> physicalExamDelList,String serviceId)
    {
    	Date sysdate;
    	sysdate = DateUtils.getSystemTime();
/*        // 删除数据库中原有的护理操作
        if(nurOperationDelList != null && nurOperationDelList.size() != 0)
        {
            for (Object nurOperation : nurOperationDelList)
            {
                NursingOperation nurOperationUpd = (NursingOperation) nurOperation;
                nurOperationUpd.setDeleteFlag(Constants.DELETE_FLAG);
                
                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                nurOperationUpd.setDeleteby(serviceId); // 设置删除人
                // [BUG]0036757 ADD END
                // $Author: chang_xuewen
                // $Date: 2013/11/20 16:00
                // [BUG]0039754 ADD BEGIN
                // 删除时间
                nurOperationUpd.setDeleteTime(sysdate);
                nurOperationUpd.setUpdateTime(sysdate);
                // [BUG]0039754 ADD END
                
                entityDao.update(nurOperationUpd);
            }
        }*/
        
        // 删除数据库中原有的体格检查
        if(physicalExamDelList != null && physicalExamDelList.size() != 0)
        {
            for (Object physicalExam : physicalExamDelList)
            {
                PhysicalExamination physicalExamUpd = (PhysicalExamination) physicalExam;
                physicalExamUpd.setDeleteFlag(Constants.DELETE_FLAG);
                
                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                physicalExamUpd.setDeleteby(serviceId); // 设置删除人
                // [BUG]0036757 ADD END
                // $Author: chang_xuewen
                // $Date: 2013/11/20 16:00
                // [BUG]0039754 ADD BEGIN
                // 删除时间
                physicalExamUpd.setDeleteTime(sysdate);
                physicalExamUpd.setUpdateTime(sysdate);
                // [BUG]0039754 ADD END
                
                entityDao.update(physicalExamUpd);
            }
        }
    }
    // [BUG]0039754 ADD END
}
