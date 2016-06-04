<!-- Main Header -->
<header class="main-header" style="">
    <!-- Logo -->
    <a href="${base}" class="logo"><b>${appName}</b>&nbsp;© ${.now?string('yyyy')}</a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>
        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <!-- User Account Menu -->

                <li class="notifications-menu">
                    <a href="javascript:N.confirm('刷新页面吗？',function(){window.location.reload()});">
                        <i class="fa fa-refresh"></i>
                    </a>
                </li>
                <!-- Notifications: style can be found in dropdown.less -->
                <li class="dropdown notifications-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-bell-o"></i>

                    </a>
                    <ul class="dropdown-menu">

                        <li class="header">亲爱哒，暂无未读消息</li>

                        <li>
                            <!-- inner menu: contains the actual data -->
                            <ul class="menu">


                            </ul>
                        </li>
                        <li class="footer"><a href="/admin/manage/noticeManage/m/systemNotice">View all</a></li>
                    </ul>
                </li>
                <li class="dropdown user user-menu">
                    <!-- Menu Toggle Button -->
                    <a href="" class="dropdown-toggle" data-toggle="dropdown">
                        <img alt="User Image" class="user-image" src="${base}/defaultlogo.png">
                        <span class="hidden-xs" id="adminUserName">${(Session.LOGIN_USER.name)!'请先登录'}</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="user-header">
                            <img src="${base}/defaultlogo.png" class="img-circle" alt="User Image">
                        </li>
                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-right">
                                <a href="/admin/logout" class="btn btn-default btn-flat">退出</a>
                            </div>
                        </li>
                    </ul>
                </li>

            </ul>
        </div>
    </nav>
</header>