package com.nerve.sample.web.controller

import java.util.Date

import com.nerve.commons.web.controller.BaseController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{ResponseBody, RequestMapping}

/**
  * Created by zengxm on 2016/6/3.
  */
@Controller
class IndexController extends BaseController{
  override protected def getTemplatePath: String = ""

  @RequestMapping(Array(""))
  def index={
    view(INDEX)
  }

  @RequestMapping(Array("test"))
  @ResponseBody
  def test={
    new Date
  }
}