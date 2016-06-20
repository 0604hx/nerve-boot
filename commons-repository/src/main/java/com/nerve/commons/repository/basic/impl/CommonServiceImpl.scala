package com.nerve.commons.repository.basic.impl

import java.lang.reflect.Field
import java.util

import com.nerve.commons.repository.basic.CommonService
import com.nerve.commons.repository.tools.Pagination
import com.nerve.commons.repository.utils.reflection.MyBeanUtils
import com.nerve.commons.repository.utils.{ConvertUtils, ReflectionUtils}
import com.nerve.commons.repository.{CommonRepository, IdEntity, TrashEntity}
import com.nerve.commons.tools.GenericItem
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.{Criteria, Query}
import org.springframework.data.repository.CrudRepository

/**
  * 继承于GenericItem，方便获取泛型的class
  * Created by zengxm on 2016/4/25.
  */
class CommonServiceImpl[T<:IdEntity, R<:CrudRepository[T,String]] extends GenericItem[T] with CommonService[T,R]{
  val log:Logger = LoggerFactory.getLogger(getClass())

  @Autowired
  var repo:R =_

  /**
    * 获取指定id的实体对象
    * @param id   id
    * @return     entity with special id
    */
  override def get(id: String): T = repo.findOne(id)

  /**
    * 保存前进行的操作
    * @param t    entity
    * @return 返回false时，保存操作将终止
    */
  override def onBeforeSave(t: T): Boolean = true

  /**
    * 更新指定的属性
    * @param id       id
    * @param field    field name
    * @param value    new value
    */
  override def modifyField(id: String, field: String, value: String): Unit = {
    val e:T = get(id)
    println("查询出来的数据:"+e)
    if(e!=null){
      val f:Field = ReflectionUtils.getAccessibleField(e, field)
      ReflectionUtils.setFieldValue(e, field, ConvertUtils.convertStringToObject(value, f.getType))

      save(e)
    }
  }

  override def delete(t: T): Unit = {
    if(onBeforeDelete(t)){
      //如果是TrashEntity对象则更新trash属性
      if(t.isInstanceOf[TrashEntity]){
        val tt=t.asInstanceOf[TrashEntity]
        tt.setTrash(true)

        repo.save(tt.asInstanceOf[T])
      }
      else
        repo.delete(t)
    }
  }

  override def delete(id: String): Unit = delete(get(id))

  override def list(c: Criteria, p: Pagination): util.List[T] = list(new Query(c), p)

  override def list(q: Query, p: Pagination): util.List[T] = {
    if(repo.isInstanceOf[CommonRepository[T,String]])
      return repo.asInstanceOf[CommonRepository[T,String]].find(q,p)

    log.warn("please Use CommonRepository, because We need to call find() method! Here will return emptyList.")
    return new util.ArrayList[T]()
  }

  /**
    * 删除对象前的操作
    * @param t    entity
    * @return 返回false终止删除
    */
  override def onBeforeDelete(t: T): Boolean = true

  /**
    * 保存实体
    * @param t    entity to be saved
    */
  override def save(t: T): Unit = {
    if(t.isUsed){
      val t2:T = repo.findOne(t.getId)
      copyBean(t,t2)
      if(onBeforeSave(t2))
        repo save(t2)
    }else{
      if(onBeforeSave(t))
        repo.save(t)
    }
  }

  protected def copyBean(fromB:T,toT:T)=MyBeanUtils copyBeanNotNull2Bean(fromB, toT)

  protected def idQuery(t:T): Query =return idQuery(t.getId)

  protected def idQuery(id:String)=new Query(Criteria.where("_id").is(id))

  override def count: Long = repo.count()

  override def count(criteria: Criteria): Long = {
    if(repo.isInstanceOf[CommonRepository[T,String]])
      return repo.asInstanceOf[CommonRepository[T,String]].count(criteria)
    log.warn("please Use CommonRepository, because We need to call count(Criteria) method! Here will return -1.")
    return -1;
  }
}
