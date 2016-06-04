/**
 * Created by zengxm on 2015/12/21 0021.
 */
'use strict';
window.ROOT_=ROOT_||'';
var N=(function(){
    return{
        /*
         对话框交互信息
         */
        toast:function(data,title){
            layer.msg(data,{title:title});
        },
        alert:function(data,title){
            layer.alert(data,{title:title,icon:0});
        },
        success:function(data,title){
            layer.alert(data,{title:title,icon:1});
        },
        error:function(data,title){
            if(typeof(data)!='string')
                data = JSON.stringify(data);
            layer.msg(data||'', {
                closeBtn:1,shade:0.5,title:title,
                shift: 6,icon:2,time:0
            });
        },
        /**
         * 确认框
         * @param content
         * @param title
         * @param cb
         */
        confirm:function(content,cb,title,btns){
            var i=layer.confirm(content, {
                btn: btns //按钮
                ,title:title||'',
                icon:3
            }, function(){
                if(cb) cb();
                layer.close(i);
            });
        },
        prompt:function(title,cb,textarea,dv){
            var istt=typeof(textarea)=='string';
            dv=istt?textarea:dv||'';
            textarea = istt?false:textarea;
            var i=layer.prompt({title: title||"输入内容", formType: textarea?2:0}, function(text){
                if(cb) cb(text,i);
            });
            $(".layui-layer-input").val(dv);
        },
        password:function(title,cb){
            var i=layer.prompt({title: title||"输入密码", formType: 1}, function(text){
                if(cb) cb(text,i);
            });
        },
        loading:function(type){
            if(type === 0 || type===false) layer.closeAll('loading');
            else layer.load();
        },
        dialog:function(ps,yesCb,noCb){
            layer.open({
                shift:ps.shift||5,
                type: 1,
                title: ps.title||false,
                closeBtn: 1,
                btn:["确定","关闭"],
                yes:function(index){
                    if(!yesCb||yesCb()!=false) layer.close(index);
                },
                cancel:function(index){
                    if(!noCb||noCb()!=false)   layer.close(index);
                },
                area: (ps.width||600)+'px',
                shadeClose: true,
                content: $('#'+ps.id)
            });
        },
        /**
         * 弹出加载中信息（此信息不会自动关闭），返回id，利用此id来关闭遮罩层
         * @param msg 提示信息
         * @returns {*}
         */
        loadingMsg:function(msg){
            if(typeof(msg)==='number') layer.close(msg);
            else return layer.msg(msg, {icon:16,shade:0.5,time:0});
        },
        /**
         * 获取表单内容，返回对象
         * @param id
         *  params 设置为true的话，会把string型"true"和"false"字符串值转化为boolean型。
         */
        getFormData:function(id, params){
            var formArray = $(id).serializeArray();
            var oRet = {};
            for (var i in formArray) {
                if (typeof(oRet[formArray[i].name]) == 'undefined') {
                    if (params) {
                        oRet[formArray[i].name] = (formArray[i].value == "true" || formArray[i].value == "false") ? formArray[i].value == "true" : formArray[i].value;
                    }
                    else {
                        oRet[formArray[i].name] = formArray[i].value;
                    }
                }
                else {
                    if (params) {
                        oRet[formArray[i].name] = (formArray[i].value == "true" || formArray[i].value == "false") ? formArray[i].value == "true" : formArray[i].value;
                    }
                    else {
                        oRet[formArray[i].name] += "," + formArray[i].value;
                    }
                }
            }
            return oRet;
        },
        //以window.open 方式打开本地的一个新页面
        open: function(url, title, w, h){
            window.open(ROOT_ + url, title, 'scrollbars=yes,height=' + h + ',width=' + w);
        },
        open2:function(url,title,w,h){
            layer.open({
                type: 2,
                area: [w+'px', h+'px'],
                fix: false, //不固定
                title:title,
                maxmin: true,
                content: ROOT_+url
            });
        },
        /**
         * 用于接收 服务器返回的Result对象
         * @param url   请求地址
         * @param data  请求数据
         * @param callBack  回调函数
         * @param method    get 或者 post ，默认是post
         */
        ajaxResult:function(url, data, callBack, method){
            method = method||"post";
            $.ajax({
                url: url,
                type: method,
                data: data,
                dataType: 'json',
                beforeSend:function(){
                    N.loading();
                },
                success: function (d) {
                    if (d.error===false|| d.success===true) {
                        if(callBack) callBack(d);
                    }  else {
                        N.error(d.message);
                    }
                }
            });
        },
        /**
         * 加载数据
         * @param url
         * @param data
         * @param callBack
         */
        loadData:function(url,data,callBack,method){
            method = method || 'get';
            $.ajax({
                url: url,
                type: method,
                data: data,
                beforeSend:function(){
                    N.loading();
                },
                success: function (d) {
                    if(callBack) callBack(d);
                }
            });
        }
    }
})();

var nerveApp = angular.module('nerveApp', [
        'ui.router',
        'ngSanitize',
        'angular-loading-bar',
        'nerve.services'
    ])
    .config(function ($stateProvider,$urlRouterProvider,$locationProvider,$httpProvider){
        //$locationProvider.hashPrefix("!!");

        $httpProvider.defaults.transformRequest = function(data){
            if (data === undefined)
                return data;
            console.log("transformRequest",data,$.param(data));
            return $.param(data);
        };
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';

        $stateProvider
            .state('master_user',{
                url:'/master/user',
                templateUrl:"/master/user",
                controller:"UserCtrl"
            })
        ;
    })
    .run(function($state,$rootScope,$http){
        //$rootScope.$on('$viewContentLoaded',function(){
        //    console.log("由scope绑定的事件 $viewContentLoaded");
        //});

        //$http.get('https://fake-response.appspot.com/?sleep=5')
        //    .then(function(d){
        //        console.log(d);
        //    });
    })
    .filter('cut', function () {
        return function (value, max, tail) {
            if (!value) return '';

            max = parseInt(max, 10);
            if (!max) return value;
            if (value.length <= max) return value;

            value = value.substr(0, max);
            return value + (tail || ' …');
        };
    })
;
nerveApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);
angular.module('nerve.services', []);