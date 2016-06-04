package com.nerve.commons.web.controller

import java.io.{BufferedInputStream, BufferedOutputStream, File, FileInputStream}
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.nerve.commons.repository.IdEntity
import com.nerve.commons.repository.basic.CommonService
import com.nerve.commons.repository.tools.Pagination
import com.nerve.commons.web.bean.{PageResult, Result}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation._

/**
  * Created by zengxm on 2015/12/28 0028.
  */
abstract class AbstractController[T<:IdEntity,R<:CrudRepository[T,String],S<:CommonService[T,R]] extends BaseController{
  @Autowired var service:S = _

  @RequestMapping(Array(""))
  def index(map:ModelMap):String = {
    return view(INDEX)
  }

  @ResponseBody
  @RequestMapping(value=Array("/{id}"), method = Array(RequestMethod.GET))
  def get(@PathVariable("id")id:String):T = service.get(id)

  @ResponseBody
  @RequestMapping(Array("list"))
  def list(req:HttpServletRequest, p:Pagination):Any={
    val datas = service.list(buildMongoQueryFromRequest(req, DEFAULT_SEARCH_PREFIX), p)
    return new PageResult(p.total, datas)
  }

  @ResponseBody
  @RequestMapping(value=Array("add","edit"), method = Array(RequestMethod.POST))
  def add(t:T):Result={
    val re:Result = new Result()
    try{
      service.save(t)
    }catch {
      case e:Exception => re.error(e)
    }
    return re
  }

  @ResponseBody
  @RequestMapping(Array("delete"))
  def delete(@RequestParam("ids") ids:Array[String]):Result = {
    val re:Result = new Result()
    try{
      ids.foreach(service.delete(_))
    }catch {
      case e:Exception => re.error(e)
    }
    return re
  }

  @RequestMapping(Array("modify"))
  @RequestBody
  def modifyField(id:String, field:String, value:String):Result={
    val re:Result = new Result
    try{
      service.modifyField(id, field, value)
    }catch {
      case e:Exception => {
        re.error(e)
        e.printStackTrace()
      }
    }
    re
  }

  /**
    * 下载文件到浏览器
    * @param file
    * @param fileName
    * @param onDone
    */
  def downloadFile(request:HttpServletRequest, response:HttpServletResponse,file:File, fileName:String,onDone:()=>Unit=()=>Unit):Unit={
    var bis:BufferedInputStream=null;
    var bos:BufferedOutputStream=null;

    try{
      //设置 response 的头信息
      response.setContentType("text/html;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      response.setContentType("application/x-msdownload;");

      val fName=if(fileName == null) file.getName else fileName

      response.setHeader("Content-disposition", "attachment; filename="+new String(fName.getBytes("utf-8"), "ISO-8859-1"))
      println(toJson(response.getHeader("Content-disposition")))

      //获取文件以及 输入输出流
      bis=new BufferedInputStream(new FileInputStream(file));
      bos=new BufferedOutputStream(response.getOutputStream());

      val blen=1024
      //将文件的 字节传递出去
     val buff = new Array[Byte](blen)
      var bytesRead =bis.read(buff, 0, buff.length)
      while (bytesRead != -1 ) {
        bos.write(buff, 0, bytesRead)
        bytesRead = bis.read(buff, 0, bytesRead)
      }
      println("传输完成")
      bos.flush()
    }catch {
      case e:Exception=>{throw e}
    }finally {
      if (bis != null)
        bis.close();
      if (bos != null)
        bos.close();
      onDone()
    }
  }
}
