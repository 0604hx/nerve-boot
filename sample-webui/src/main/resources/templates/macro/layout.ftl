<#--使用AdminLTE+angularJs on 2016年3月4日14:51:05-->
<#macro body_header fixed=true title="" bodyClass="">
<!DOCTYPE html>
<html lang="en">
<head>
    <script>
        ROOT_='${base}/';
        window.U = window.U||{};
        <#--window.U.id=${(Session.LOGIN_USER.id)!0};-->
        <#--window.U.name="${(Session.LOGIN_USER.name)!''}";-->
    </script>
    <meta charset="UTF-8">
    <title>${title!''}</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <link href="${base}/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- Font Awesome Icons -->
    <link href="${base}/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/libs/font-awesome/css/font-awesome-animation.min.css" rel="stylesheet" type="text/css"/>

    <!-- Select2 -->
    <link rel="stylesheet" href="${base}/libs/plugins/select2/select2.min.css">

    <!-- Theme style -->
    <link href="${base}/libs/adminLTE/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
    <link href="${base}/libs/adminLTE/css/skins/_all-skins.min.css" rel="stylesheet" type="text/css" />

    <script src="${base}/libs/jquery/jquery-2.2.1.min.js" type="text/javascript"></script>

    <script src="${base}/libs/jquery/jquery.slimscroll.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="${base}/libs/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${base}/libs/adminLTE/app.min.js" type="text/javascript"></script>

    <#--<!-- DataTables &ndash;&gt;-->
    <#--<link rel="stylesheet" href="${base}/libs/datatables/dataTables.bootstrap.css">-->
    <#--<script src="${base}/libs/datatables/jquery.dataTables.min.js"></script>-->
    <#--<script src="${base}/libs/datatables/dataTables.bootstrap.min.js"></script>-->

    <!--datepicker-->
    <link rel="stylesheet" href="${base}/libs/datepicker/bootstrap-datepicker3.min.css">
    <script src="${base}/libs/datepicker/bootstrap-datepicker.min.js"></script>
    <script src="${base}/libs/datepicker/bootstrap-datepicker.zh-CN.min.js"></script>

    <script src="${base}/libs/moment.min.js"></script>

    <link rel="stylesheet" href="${base}/libs/layer/skin/layer.css">
    <script src="${base}/libs/layer/layer.js"></script>
    <script src="${base}/libs/layer/extend/layer.ext.js"></script>

    <@angularJS />

    <link rel="stylesheet" href="${base}/libs/nerve.common.css">

    <!--加入自定义controller-->
</head>
<body class="hold-transition sidebar-mini skin-blue <#if fixed>fixed</#if> ${bodyClass}" ng-app="nerveApp">
</#macro>

<#macro body title="" fixed=true router=false rightSidebar=true>
<@body_header fixed=fixed title=title />
<div class="wrapper">
    <#include "header.ftl"/>
    <!-- Content Wrapper. Contains page content -->
    <#if router>


        <#nested 0>
        <div class="content-wrapper" ui-view>
        </div><!-- /.content-wrapper -->
        <#--显示右边的sidebar-->
        <#if rightSidebar>
            <#nested 1>
        </#if>
    <#else>
        <div class="content-wrapper">
            <#nested >
        </div><!-- /.content-wrapper -->
    </#if>

    <@footer/>
</div>
</body>
<@body_after/>
</#macro>

<#macro body_after>
<script src="${base}/libs/plugins/select2/select2.full.min.js"></script>
<script>
    $(document).ready(function(){
        //ajax的初始化
        $.ajaxSetup({
            traditional: true,
            timeout : 30000 ,
//                beforeSend: function(){
//
//                },
            complete: function(){
                N.loading(0);
            },
            error: function(xhr, status, e){
                N.loading(0);
                //N.error("ajax请求出错：status="+status+" ,"+e);
                N.error(xhr.responseText);
            }
        });
    });
</script>
</html>
</#macro>

<#macro body_simple fixed=true title="">
    <@body_header fixed=fixed title=title bodyClass="simple-body"/>
    <div class="">
        <#nested >
    </div>
    </body>
    <@body_after/>
    </html>
</#macro>

<#macro pageHeader h1="" h2="">
<section class="content-header">
    <h1>
    ${h1}<small>${h2}</small>
    </h1>
</section>
</#macro>

<#macro angularJS>
<!--引用angularjs 相关-->
<#--<script src="${base}/libs/angular/angular1.3.13.min.js" type="text/javascript"></script>-->
<script src="${base}/libs/angular/angular1.4.8.min.js" type="text/javascript"></script>
<script src="${base}/libs/angular/angular-sanitize.min.js" type="text/javascript"></script>
<script src="${base}/libs/angular/angular-ui-router.min.js" type="text/javascript"></script>

<link rel='stylesheet' href='${base}/libs//angular/loading/loading-bar.min.css' type='text/css' media='all' />
<script src="${base}/libs/angular/loading/loading-bar.min.js" type="text/javascript"></script>
<script src="${base}/libs/nerve.angular.js" type="text/javascript"></script>

<!--加入services-->
<script src="${base}/libs/nerve.CommonService.js"></script>
</#macro>

<#macro menu title="Main Menu" controller="">
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar" <#if controller?length gt 0>ng-controller="${controller}"</#if>>
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- search form (Optional) -->
        <form action="#" method="get" class="sidebar-form ng-pristine ng-valid">
            <div class="input-group">
                <input type="text" name="searchKey" class="form-control" placeholder="Search..." value="">
              <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i></button>
              </span>
            </div>
        </form>
        <!-- /.search form -->

        <!-- Sidebar Menu -->
        <ul class="sidebar-menu">
            <li class="header">${title}</li>
            <#nested >
            <#--<li class="treeview" cid="sysTemManage">-->
                <#--<a href="#"><i class="fa fa-laptop"></i><span>系统管理</span> <i class="fa fa-angle-left pull-right"></i></a>-->
                <#--<ul class="treeview-menu">-->
                    <#--<li cid="sysTemManage_user"><a href="#/master/user"><i class="fa fa-user"></i>用户管理</a></li>-->
                    <#--<li cid="sysTemManage_logs"><a href="#/master/log"><i class="fa fa-fw fa-clipboard"></i> 系统日志管理</a></li>-->
                <#--</ul>-->
            <#--</li>-->
        </ul><!-- /.sidebar-menu -->

    </section>
    <!-- /.sidebar -->
</aside>
</#macro>

<#macro footer content="">
<!-- Main Footer -->
<footer class="main-footer">
    <!-- Default to the left -->
    <div>${content!''}</div>
    <strong>Copyright © ${.now?string('yyyy')} All rights reserved.
</footer>
</#macro>

<#macro modal id="" class="" title="">
<div class="modal fade ${class}" id="${id}">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">${title}</h4>
            </div>
            <#nested />
        </div>
    </div>
</div>
</#macro>

<#macro fileinput name="file" id="fileInput" max=10 url="" init=true>
<link rel='stylesheet' href='${base}/libs/fileinput/fileinput.min.css' type='text/css' media='all' />
<script src="${base}/libs/fileinput/fileinput.min.js"></script>
<script src="${base}/libs/fileinput/fileinput_locale_zh.js"></script>
<label class="control-label">请选择文件(最多支持${max}个)</label>

<div id="fileInputDiv">
<#if init>
<input type="file" name="${name}" id="${id}" multiple class="file-loading" />
<script>
    $(document).on('ready', function() {
        $("#${id}").fileinput({
            language: 'zh', //设置语言
            showUpload: true,
            maxFileCount: ${max},
//            mainClass: "input-group-lg",
            uploadUrl: "${url}", //上传的地址
            //allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
            enctype: 'multipart/form-data',
//            validateInitialCount:true,
//            browseClass: "btn btn-primary", //按钮样式
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"
        });
        //导入文件上传完成之后的事件
        $("#${id}").on("fileuploaded", function (event, data, previewId, index) {
            if(onFileUploaded) onFileUploaded(event,data,previewId,index);
        }).on("filepreupload",function(event,data){
        });
    });
</script>
</#if>
</div>
</#macro>

<#macro videjs>
<link rel='stylesheet' href='${base}/libs/plugins/videojs/video-js.min.css' type='text/css' media='all' />
<script src="${base}/libs/plugins/videojs/video.min.js"></script>
</#macro>

<#--
通用的表格底部
multi 是否显示批量操作的按钮
-->
<#macro table_footer multi=false>
<!--表格底部-->
<div class="box-footer">
    <!--分页模块-->
    <div class="row">
        <div class="col-xs-5">
            <#if multi>
            <div class="btn-group" ng-show="getCheckSelect">
                <button type="button" class="btn" ng-click="checkSelect(1)">全选</button>
                <button type="button" class="btn" ng-click="checkSelect(-1)">全不选</button>
                <button type="button" class="btn" ng-click="checkSelect(0)">反选</button>
                <button type="button" class="btn btn-danger" ng-click="delItems()"><span class="fa fa-fw fa-trash-o" aria-hidden="true"></span>批量删除</button>
            </div>
            </#if>
        </div>
        <div class="col-xs-7">
            <div class="pull-left">
                每页 <select ng-model="page.pageSize" ng-options="x.name as x.value for x in page.options" ng-change="pageSize()"></select> 条，
                共 {{page.total}} 条记录
            </div>
            <div class="dataTables_paginate paging_bootstrap">
                <ul class="pagination pagination-sm ">
                    <li ng-class="{true:'disabled'}[page.current==1]"><a href="javascript:void(0);" ng-click="prePage()">上一页</a></li>
                    <li ng-repeat="p in page.pages" ng-class="{true:'active'}[page.current==p]"><a href="javascript:void(0);" ng-click="loadPage(p)">{{ p }}</a></li>
                    <li ng-class="{true:'disabled'}[page.current==page.max]"><a href="javascript:void(0);" ng-click="nextPage()">下一页 </a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="overlay hide" id="dataLoading">
    <i class="fa fa-refresh fa-spin"></i>
</div>
</#macro>

<#macro form_date name="" ngModel="" format="yyyy-mm-dd" holder="" width=0>
<input type="text" name="${name}" <#if ngModel?length gt 0>ng-model="${ngModel}"</#if> class="form-control"
    data-provide="datepicker"
    data-date-format="${format}" placeholder="${holder!''}" <#if width gt 0>style="width:${width?trim}px"</#if>>
</#macro>

<#macro form_text name="" ngModel="" ngPattern="" required=true error="" form="" minLen=0 maxLen=0 holder="">
<input type="text" class="form-control" name="${name}" placeholder="${holder}"
       <#if minLen gt 0>ng-minlength="${minLen}"</#if> <#if maxLen gt 0>ng-maxlength="${maxLen}"</#if>
       <#if ngModel?length gt 0>ng-model="${ngModel}"</#if>
       <#if ngPattern?length gt 0>ng-pattern="${ngPattern}"</#if> <#if required>required</#if>/>
    <#if error?length gt 0>
        <#assign form2=form />
        <#if form?length gt 0><#assign form2=form+"." /></#if>
    <label class="control-label text-danger" ng-show="${form2}${name}.$invalid && !${form2}${name}.$pristine"><i class="fa fa-times-circle-o"></i>${error}</label>
    </#if>
</#macro>

<#macro form_line class="" label="" labelCol=2>
    <#assign contentLength = 12-labelCol />
<div class="form-group ${class}">
    <label class="control-label col-sm-${labelCol}">${label}</label>
    <div class="col-sm-${contentLength}">
        <#nested />
    </div>
</div>
</#macro>

<#macro box class="box-info" title="" refresh="" nopadding=false id="" bodyCls="" close=false style="">
<div class="box ${class} <#if close>collapsed-box</#if>" id="${id}" style="${style}">
    <div class="box-header with-border">
        <h3 class="box-title">${title}</h3>
        <div class="box-tools pull-right">
            <#if refresh?length gt 0>
                <button class="btn btn-box-tool" ng-click="${refresh}"><i class="fa fa-refresh"></i></button>
            </#if>
            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-<#if close>plus<#else>minus</#if>"></i></button>
            <button class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
        </div>
    </div>
    <div class="box-body <#if nopadding>no-padding</#if> ${bodyCls}">
        <#nested >
    </div>
</div>
</#macro>

<#macro dashboard>
<#--总览模板-->
<script type="text/ng-template" id="dashboard.html">
    <div class="pad margin no-print">
        <div class="callout callout-info" style="margin-bottom: 0!important;">
            <h4><i class="fa fa-info"></i> Note:</h4>
            本页面数据每半小时刷新一次!
        </div>
    </div>
    <section class="invoice">
        <div class="row">
            <div class="col-xs-12">
                <h2 class="page-header">
                    <i class="fa fa-globe"></i> {{title}} <span class="text-sm">版本：{{version}}</span>
                    <small class="pull-right">最后更新于: {{updateDate|date : "yyyy-MM-dd HH:mm:ss"}}</small>
                </h2>
            </div>
            <!-- /.col -->
        </div>
        <div class="row invoice-info">
            <#nested >
        </div>

        <div class="row">
            <div class="col-xs-12"><h3>最近操作记录</h3></div>
            <div class="col-xs-12 table-responsive">
                <table class="table table-striped">
                    <thead>
                    <th width="80px">对象ID</th>
                    <th width="90px">操作者</th>
                    <th>操作</th>
                    <th width="120px">时间</th>
                    </thead>
                    <tbody>
                    <tr ng-repeat="o in operations">
                        <td>{{o.appId}}</td>
                        <td>{{o.userName}}</td>
                        <td>{{o.remark}}</td>
                        <td>{{o.date|date:"MM-dd HH:mm:ss"}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </section>
    <div class="clearfix"></div>
</script>
</#macro>