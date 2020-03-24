<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="sidebar" id="sidebar">
		<script type="text/javascript">
			try {
				ace.settings.check('sidebar', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="sidebar-shortcuts" id="sidebar-shortcuts">
			<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
				<button class="btn btn-success">
					<i class="icon-signal"></i>
				</button>

				<button class="btn btn-info">
					<i class="icon-pencil"></i>
				</button>

				<button class="btn btn-warning">
					<i class="icon-group"></i>
				</button>

				<button class="btn btn-danger">
					<i class="icon-cogs"></i>
				</button>
			</div>

			<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
				<span class="btn btn-success"></span> <span class="btn btn-info"></span>

				<span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
			</div>
		</div>
		<!-- #sidebar-shortcuts -->

		<ul class="nav nav-list">
			<li class="active"><a href="index.html"> <i
					class="icon-dashboard"></i> <span class="menu-text"> 控制台 </span>
			</a></li>

			<li><a href="#" class="dropdown-toggle"> <i
					class="icon-desktop"></i> <span class="menu-text">商品管理 </span> <b
					class="arrow icon-angle-down"></b>
			</a>

				<ul class="submenu">
					<li><a href="index.jsp"> <i
							class="icon-double-angle-right"></i> 商品列表
					</a></li>

					<li><a href="addProduct.jsp"> <i
							class="icon-double-angle-right"></i> 商品添加
					</a></li>



				</ul></li>

			<li><a href="#" class="dropdown-toggle"> <i
					class="icon-list"></i> <span class="menu-text"> 分类管理 </span> <b
					class="arrow icon-angle-down"></b>
			</a>

				<ul class="submenu">
					<li><a href="tables.html"> <i
							class="icon-double-angle-right"></i> 分类列表
					</a></li>

					<li><a href="jqgrid.html"> <i
							class="icon-double-angle-right"></i> 添加分类
					</a></li>
				</ul></li>
			<li><a href="#" class="dropdown-toggle"> <i
					class="icon-edit"></i> <span class="menu-text"> 首页轮播 </span> <b
					class="arrow icon-angle-down"></b>
			</a>

				<ul class="submenu">
					<li><a href="form-elements.html"> <i
							class="icon-double-angle-right"></i> 广告列表
					</a></li>

					<li><a href="form-wizard.html"> <i
							class="icon-double-angle-right"></i> 添加广告
					</a></li>
				</ul></li>
			<li><a href="#" class="dropdown-toggle"> <i
					class="icon-edit"></i> <span class="menu-text"> 订单管理 </span> <b
					class="arrow icon-angle-down"></b>
			</a>

				<ul class="submenu">
					<li><a href="form-elements.html"> <i
							class="icon-double-angle-right"></i> 订单列表
					</a></li>
				</ul></li>
		</ul>
		<!-- /.nav-list -->
		<div class="sidebar-collapse" id="sidebar-collapse">
			<i class="icon-double-angle-left" data-icon1="icon-double-angle-left"
				data-icon2="icon-double-angle-right"></i>
		</div>

		<script type="text/javascript">
			try {
				ace.settings.check('sidebar', 'collapsed')
			} catch (e) {
			}
		</script>
	</div>


</body>
</html>