/**
 * 通用services
 * Created by zengxm on 2015/12/25 0025.
 */
'use strict;'

angular.module("nerve.services")
.factory("NA", function ($http,$q) {
    var _initPageAttr = function($scope,defaultParams){
        if(!$scope.api)
            throw new Error("请先设置$scope的api属性，此属性用于数据交互");
        if(typeof($scope.api)=='string'){
            var suffix=window.apiSuffix||"";
            var a = {
                get:$scope.api+"/#id#",     //获取指定id的对象数据，#id#会被替换成真实的id
                list:$scope.api+"/list"+suffix,
                del:$scope.api+"/delete"+suffix,
                add:$scope.api+"/add"+suffix,
                addDo:$scope.api+"/add"+suffix,
                edit:$scope.api+"/edit"+suffix,
                editDo:$scope.api+"/edit"+suffix
            }
            $scope.api=a;
        }
        $scope.page={
            options:[
                {name:'20',value : '20'},
                {name:'50',value : '50'},
                {name:'100',value : '100'}
            ],
            pageSize: '20', //
            current: 1,     //当前分页
            max:1,      //总分页数
            total:1,    //总记录数
            start:1,
            pages:[],
            defaultParams:defaultParams||{}
        }
        $scope.keyword = "";    //关键字
        $scope.datas = [];
    }
    /**
     * 初始化分页函数
     * @param $scope
     * @private
     */
    var _initPageMethod=function($scope){
        $scope.loadPage = function(p,params){
            $scope.page.startT=(new Date()).getTime();
            $scope.page.current = p || $scope.page.current;
            //获取数据
            _httpPost(
                $scope.api.list,
                $.extend({
                    page:$scope.page.current,
                    rows:Number($scope.page.pageSize)
                }, $scope.page.defaultParams, params||{})
            ).then(function(d){
                $scope.datas = d.rows;
                _fixupPage($scope, d.total);

                $scope.page.endT=(new Date()).getTime();
                $scope.loadInfo="加载"+$scope.datas.length+"条数据，耗时"+($scope.page.endT-$scope.page.startT)/1000+"秒(数据总量"+$scope.page.total+")";
                if($scope.onPageLoaded) $scope.onPageLoaded();
            }, function(){

            });
        };
        $scope.nextPage = function(step){
            step = step || 1;
            $scope.loadPage($scope.page.current+step);
        };
        $scope.prePage=function(step){
            step = step || 1;
            $scope.loadPage($scope.page.current-step);
        }
        $scope.searchDo=function(formId){
            this.loadPage(1, N.getFormData(formId));
            return false;
        }
        $scope.pageSize=function(){
            this.loadPage();
        }
    }
    var _fixupPage=function($scope, total){
        $scope.page.total = total;
        //获取总页数
        $scope.page.max = Math.ceil(total / $scope.page.pageSize);
        //生成数字链接
        var p =  Number($scope.page.current);
        if (p > 1 && p < $scope.page.max) {
            $scope.page.pages = [
                p - 1,
                p,
                p + 1
            ];
        }
        else if (p == 1 && $scope.page.max == 1) {
            $scope.page.pages = [
                p
            ];
        }
        else if (p == 1 && $scope.page.max > 1) {
            $scope.page.pages = [
                p,
                p + 1
            ];
        } else if (p == $scope.page.max && $scope.page.max > 1) {
            $scope.page.pages = [
                p - 1,
                p
            ];
        }
    }
    /**
     * 初始化弹出对话框
     * @param $scope    向scope添加以下方法：saveDo (保存数据到服务器), saveResult （保存的结果回调函数，可以重写）
     * @param mid
     * @private
     */
    var _initModal=function($scope, mid, defaultEntity){
        $scope.entity = defaultEntity||{}; //实体对象
        if($scope.entity) $scope._defaultEntity=function(){return $.extend({}, defaultEntity);};
        //使用bootstrap的modal对话框
        $(mid).on('show.bs.modal', function (event) {
            var obj = $(event.relatedTarget);
            var editId = obj.data('id');
            // 如果不为空则为编辑状态
            if(editId){
                //获取数据
                console.log("api="+$scope.api.get);
                $http.get($scope.api.get.replace("#id#", editId)).success(function(d){
                   $scope.entity = d;
                    if($scope.onEntityLoaded) $scope.onEntityLoaded();
                });
            }else{
                $scope.entity = $scope._defaultEntity();
                $scope.$apply();
            }
            console.log($scope.entity);
        }).on('hidden.bs.modal', function (e) {
            _cleanData($scope, this);
        });

        $scope.saveDo=function(){
            var ME = this;
            var d={};
            var url = $.isEmptyObject(ME.entity.id)?ME.api.addDo:ME.api.editDo;
            console.log("formName="+ME.formName, $scope.entity);
            //提交数据
            if(ME.formName!==undefined){
                d=$(document.getElementsByName(ME.formName)).serializeArray();
                if(ME.entity.id)    d.push({name:"id",value:ME.entity.id});
                if(ME.onBeforeEditPost) ME.onBeforeEditPost(d);
            }else{
                for(var k in ME.entity){
                    var type=typeof(ME.entity[k]);
                    if(type==='boolean' || (type==='string'&& ME.entity[k]) || true)
                        d[(ME.postPrefix||'')+k]=ME.entity[k];
                }
                if(!$.isEmptyObject(ME.entity.id) && ME.onBeforeEditPost)
                    d = ME.onBeforeEditPost(d);
                console.log(ME.entity, d,(!$.isEmptyObject(ME.entity.id) && ME.onBeforeEditPost));
            }
            $http({
                method  : 'POST',
                url     : url,
                data    : d  // pass in data as strings
                //,headers : { 'Content-Type': 'application/x-www-form-urlencoded' }  // set the headers so angular passing info as form data (not request payload)
            })
                .success(function(d) {
                    if (d.success===true || d.error===false) {
                        //  关闭所有模态窗口
                        $('.modal').each(function(){
                            $(this).modal("hide");
                        });
                        ME.loadPage();
                        _cleanData(ME);
                        N.toast(d.message||"操作成功");
                        if(ME.saveResult) ME.saveResult(false, d);
                    }  else {
                        if(ME.saveResult) ME.saveResult(false, d);
                        N.error(d.message);
                    }
                })
                .error(function(e){
                    N.error(e, "网络请求出错");
                    if(ME.saveResult)  ME.saveResult(true, e);
                });
        }
        $scope.saveResult=function(err,data){
        }
    }
    var _initDelete=function($scope,tips){
        $scope.delItems=function(id){
            var ME = this;
            var ids=new Array();
            id=id||0;
            if(id==0){
                if(ME.getCheckSelect) ids = ME.getCheckSelect();
                else console.log("找不到getCheckSelect方法，请使用 initMultiDelete 进行初始化");
            }
            else
                ids.push(id);
            if(ids.length==0){
                N.error("请先选择想要删除的记录"); return false;
            }
            N.confirm(tips||"确定删除选中吗？",function(){
               _httpPost($scope.api.del,{ids:ids}).then(function(d){
                   if (d.success===true|| d.error===false) {
                       if(ME.afterDelete) ME.afterDelete(); else ME.loadPage();
                       N.toast(d.message||"操作成功");
                   }  else {
                       N.error(d.message);
                   }
               });
            });
        }
        $scope.delItem=function(id){
            var ME=this;
            if(!id){
                N.error("请先选择想要删除的对象！");return ;
            }
            N.confirm(tips||"确定删除选中吗？",function(){
                _httpPost($scope.api.del,{id:id}).then(function(d){
                    if (d.success===true|| d.error===false) {
                        if(ME.afterDelete) ME.afterDelete(); else ME.loadPage();
                        N.toast(d.message||"操作成功");
                    }  else {
                        N.error(d.message);
                    }
                });
            });
        }
        console.log($scope.delItem);
    }
    var _initMultiDelete=function($scope,tips){
        _initDelete($scope, tips);
        $scope.checkSelect=function(t){
            if(typeof(t) === "number"){
                switch (t){
                    //全选
                    case 1:for(var i=0;i<$scope.datas.length;i++) $scope.datas[i].$checked = true; break;
                    //全不选
                    case -1:for(var i=0;i<$scope.datas.length;i++) $scope.datas[i].$checked = false; break;
                    //反选
                    case 0:for(var i=0;i<$scope.datas.length;i++) $scope.datas[i].$checked = !$scope.datas[i].$checked; break;
                }
            }else{
                var check = $(t.target).prop('checked');
                for(var i=0;i<$scope.datas.length;i++) $scope.datas[i].$checked = check;
            }
        }
        $scope.getCheckSelect=function(){
            var ids=new Array();
            for(var i=0;i<$scope.datas.length;i++){
                if($scope.datas[i].$checked)
                    ids.push($scope.datas[i].id);
            }
            return ids;
        }
    }
    var _beforeRequest=function(id){
        if(!id) return;
        if($(id+" .overlay").length>0){
            $(id+" .overlay").removeClass("hidden");
        }else{
            $(id).append($('<div class="overlay"><i class="fa fa-refresh fa-spin"></i></div>'));
        }
    }
    var _afterRequest=function(id){
        $(id+" .overlay").addClass("hidden");
    }
    var _cleanData=function($scope,modalId){
        // 清空数据
        $scope.entity = {};
        if(modalId) $(modalId).find(".form-control").val("");
    }
    var _httpGet=function(url,loadingId){
        var deferred = $q.defer();                                                  // 声明延后执行，表示要去监控后面的执行
        _beforeRequest(loadingId);
        $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            _afterRequest(loadingId);
            deferred.resolve(data, status, headers, config);                   // 声明执行成功，即http请求数据成功，可以返回数据了
        }).
        error(function(data, status, headers, config) {
            _afterRequest(loadingId);
            deferred.reject(data, status, headers, config);                      // 声明执行失败，即服务器返回错误
            N.error(data,'获取数据出错[status='+status+']');
        });
        return deferred.promise;                                                    // 返回承诺，这里并不是最终数据，而是访问最终数据的API
    }
    var _httpPost=function(url,params,loadingId){
        params = params || {};
        var deferred = $q.defer();                                                  // 声明延后执行，表示要去监控后面的执行
        _beforeRequest(loadingId);
        $http({method: 'POST', data:params, url: url}).
        success(function(data, status, headers, config) {
            _afterRequest(loadingId);
            deferred.resolve(data, status, headers, config);                   // 声明执行成功，即http请求数据成功，可以返回数据了
        }).
        error(function(data, status, headers, config) {
            _afterRequest(loadingId);
            deferred.reject(data, status, headers, config);                      // 声明执行失败，即服务器返回错误
            N.error(data);
        });
        return deferred.promise;                                                    // 返回承诺，这里并不是最终数据，而是访问最终数据的API
    }
    return{
        /**
         * log:
         * 1.增加了后台默默加载的开关，mute = true 时，不会显示loading     on 二〇一五年五月二十五日 09:22:42
         * @param url
         * @param mute
         * @returns {*}
         */
        httpGet:_httpGet,
        /**
         * 向服务器post数据
         * @param url
         * @param params
         */
        httpPost:_httpPost,
        /**
         *
         * @param url
         * @param data
         * @param callback
         * @param params
         */
        httpResult:function(url,data,callback,errorBack,params){
            var eb, ps;
            //如果定义了错误时调用的方法
            if(typeof(errorBack)==='function'){
                eb=errorBack;
                ps=params||{};
            }else ps=errorBack||{};

            data = data || {};
            //判断是否需要显示加载中的提示信息
            var mid=ps.message? N.loadingMsg(ps.message):-1;
            _beforeRequest(ps.loadingId);

            $http({method: ps.method||'POST', data:data, url: url}).
                success(function(d) {
                    if (d.success===true|| d.error===false) {
                        callback && callback(d);
                    }  else {
                        eb && eb(d);
                        N.error(d.message);
                    }
                    if(mid>0)   N.loadingMsg(mid);
                    _afterRequest(ps.loadingId);
                }).
                error(function(data, status) {
                    N.error(data,"status="+status);
                    if(mid>0)   N.loadingMsg(mid);
                    _afterRequest(ps.loadingId);
                });
        },
        /**
         * 初始化分页
         * @param $scope
         * @param $http
         */
        initPage:function($scope,defaultParams){
            _initPageAttr($scope,defaultParams);
            _initPageMethod($scope);
        },
        /**
         * 从数据数组中删除指定id的元素
         * @param datas
         * @param id
         */
        spliceById: function (datas, id) {
            for(var i=0;i<datas.length;i++){
                if(datas[i].id && datas[i].id==id){
                    datas.splice(i,1);
                    return ;
                }
            }
        },
        fixupPage:_fixupPage,
        initDelete:_initDelete,
        initMultiDelete:_initMultiDelete,
        /**
         * 初始化对象编辑对话框
         */
        initModal:_initModal,
        cleanData:_cleanData
    }
})
;