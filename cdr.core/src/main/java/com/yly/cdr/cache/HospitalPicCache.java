package com.yly.cdr.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yly.cdr.core.Constants;
import com.yly.cdr.util.StringUtils;

/**
 * 将医院定制的logo图片，在启动web服务的时候，加载到项目中配置的临时目录。
 * @author yang_jianbo
 * @time 2014-8-7 17:09
 */
public class HospitalPicCache
{

    private static final Logger logger = LoggerFactory.getLogger(HospitalPicCache.class);

    private HospitalPicCache()
    {
    	
    }

    /**
     * 复制logo图片到项目临时目录。
     */
    public void init()
    {
        try
        {
        	logger.debug("复制定制的logo图片到项目中的临时目录，初始化操作开始...");
            initPic();
            logger.debug("复制定制的logo图片到项目中的临时目录，初始化操作完成!");
        }
        catch (Exception e)
        {
            logger.error("复制医院定制的logo图片到项目中临时目录，初始化操作失败！");
            e.printStackTrace();
        }
    }
    
    //允许医院定制个性化logo,将定制化logo复制到项目中
    public void initPic(){
        try {
        	String loginPicName = Constants.HOSPITAL_LOGIN_PIC;
            String mainPicName = Constants.HOSPITAL_MAIN_PIC;
	        File loginPicFile = new File("");
	        //获取tomcat根路径
	        String filePath = loginPicFile.getCanonicalPath();
	        //处理windows与linux之间的差异
            if(filePath.contains("bin")){
            	filePath = filePath.substring(0, filePath.indexOf("bin")-1);
            }
            
            //获取cdr项目所在的根路径
            String webPath = new File(this.getClass().getResource("/").getPath()).getAbsolutePath();
            //处理windows与linux之间的差异
            if(webPath.contains("WEB-INF")){
            	webPath = webPath.substring(0,webPath.indexOf("WEB-INF"));
            }
            
	        //logger.debug(webPath);
	        //得到定制图片的临时路径
	    	String newPath = webPath + Constants.HOSPITAL_PIC_FOLDER;
	    	
	    	/**为保障用户修改了图片能够及时体现最新，先对项目中存在的临时目录文件做清除操作。
	    	 * 如果满足条件：(1)当指定路径(properties目录)是否存在该文件，(2)登录定制背景图片配置不为空,(3)文件名与配置名一致。先清理再做插入操作
	    	 */
	    	deleteFile(newPath);
	    	
	    	//然后再执行插入操作
	        if(!StringUtils.isEmpty(loginPicName)||!StringUtils.isEmpty(mainPicName)){
	        	File mainPicFile = new File("");
	        	String loginPicPath = "";
	        	String mainPicPath = "";

	            if(!StringUtils.isEmpty(loginPicName)){
            		loginPicPath = filePath + Constants.HOSPITAL_PIC_PATH + loginPicName;
            		loginPicFile = new File(loginPicPath);
            		if(loginPicFile.exists()){
            			copyFile(loginPicPath,newPath,loginPicName);
            		}
            	}
            	if(!StringUtils.isEmpty(mainPicName)){
            		mainPicPath = filePath + Constants.HOSPITAL_PIC_PATH + mainPicName;
    	            mainPicFile = new File(mainPicPath);
    	            if(mainPicFile.exists()){
    	            	copyFile(mainPicPath,newPath,mainPicName);
    	            }
            	}
	        }
		} catch (IOException e){
			e.printStackTrace();
		}
    }
    
    //删除临时目录下的所有文件，保障定制图片是最新的
    public void deleteFile(String path){
    	File file = new File(path);
    	if(file.exists()){
    		if(file.isDirectory()){
    			File files[] = file.listFiles();
    			logger.debug(String.valueOf(file.listFiles().length));
    			for(int i=0;i<files.length;i++){
    				if(files[i].isDirectory()){
    					deleteFile(files[i].getPath());
    				}else if(files[i].isFile()){
    					logger.debug(files[i].getPath());
    					files[i].delete();
    				}
    			}
    		}else{
    			file.delete();
    		}
    	}
    }
    
    
    //将properties目录下的文件，复制到项目中的临时目录
    public void copyFile(String oldPath, String newPath,String fileName) { 
        try { 
        	int bytesum = 0; 
            int byteread = 0; 
            File oldfile = new File(oldPath); 
            if (oldfile.exists()) { //文件存在时 
                InputStream inStream = new FileInputStream(oldPath); //读入原文件 
                File file = new File(newPath);
                //目录要先创建，不然会报异常
                if(!file.exists()){
                	file.mkdirs();
                }
                newPath = newPath + "/" + fileName;
                FileOutputStream fs = new FileOutputStream(newPath); 
                byte[] buffer = new byte[1024]; 
                while ( (byteread = inStream.read(buffer)) != -1) { 
                	bytesum += byteread; //字节数 文件大小 
                    System.out.println(bytesum); 
                    fs.write(buffer, 0, byteread); 
                    
                } 
                inStream.close(); 
            } 
        } 
        catch (Exception e) { 
            logger.debug("复制图片文件操作出错"); 
            e.printStackTrace(); 
        } 

    } 
}
