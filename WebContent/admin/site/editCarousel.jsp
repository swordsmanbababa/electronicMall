<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="cn">
	<head>
		<meta charset="utf-8" />
		<title>电商网站后台</title>
		<meta name="keywords" content="Bootstrap模版,Bootstrap模版下载,Bootstrap教程,Bootstrap中文" />
		<meta name="description" content="站长素材提供Bootstrap模版,Bootstrap教程,Bootstrap中文翻译等相关Bootstrap插件下载" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->

		<!-- ace styles -->

		<link rel="stylesheet" href="assets/css/ace.min.css" />
		<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="assets/css/ace-skins.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="assets/js/html5shiv.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body>
	   <jsp:include page="navbar.jsp"></jsp:include>

		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				<jsp:include page="sidebar.jsp"></jsp:include>

				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="#">首页</a>
							</li>
							<li class="active">轮播图修改</li>
						</ul><!-- .breadcrumb -->

						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- #nav-search -->
					</div>

					<div class="page-content">
						<div class="page-header">
							<h1>
								轮播图修改
								<small>
									<i class="icon-double-angle-right"></i>
									编辑
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
						<div class="col-xs-4">
							<div>
								<form action="${pageContext.request.contextPath }/updateCarouselFile" method="post" enctype="multipart/form-data">
									<div class="form-group">
										<label for="pro_name">商品id:<span id="show_id"></span></span></label> 
										<input type="hidden" id="car_id" name="car_id"
											class="form-control" required
											placeholder="输入商品名称">
									</div>
									<div class="form-group">
										<label for="pro_name">商品名称</label> <input type="text"
											class="form-control" id="pro_name" name="pro_name" required
											placeholder="输入商品名称">
									</div>
									<div class="form-group">
										<label for="pro_image">要修改的图片:</label><br>
										<img id="pro_image" style="width:180px" />
										<input type="hidden" id="original_image" name="pro_image">
									</div>
									<div class="form-group">
										<label for="pro_image" >点击上传新的图片:</label><br>
										<input type="file" accept=".jpg,.png,.jpeg,.gif" name="upload" id="uploadFile" onchange="loadImage()"/>
									</div>
									<button type="submit" id="btnSubmit" class="btn btn-default">提交</button>
								</form>
							</div>
						</div>
						<!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->

				
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->

		<script src="assets/js/jquery-2.0.3.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
<script src="assets/js/jquery-1.10.2.min.js"></script>
<![endif]-->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"script>");
</script>
<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
		</script>
		<script src="assets/js/bootstrap.min.js"></script>
		<script src="assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="assets/js/excanvas.min.js"></script>
		<![endif]-->

		<script src="assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="assets/js/jquery.slimscroll.min.js"></script>
		<script src="assets/js/flot/jquery.flot.min.js"></script>
		<script src="assets/js/flot/jquery.flot.pie.min.js"></script>
		<script src="assets/js/flot/jquery.flot.resize.min.js"></script>

		<!-- ace scripts -->

		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>
		<script src="../../js/layer.js"></script>
		<script src="../../js/mylayer.js"></script>
		<!-- inline scripts related to this page -->

		<script type="text/javascript">
			//当file控件  选中一张图片的时候  就会触发这个方法  这个方法可以将上面的图片换掉
			var oldImage = null;
			function loadImage(){
				//get(0) 是将JQuery对象  转换为DOM对象
				//document.getElementById("uploadFile");
				var upload = $("#uploadFile").get(0);
				console.dir(upload);
				//表示至少选中了一张图片信息
				if(upload.files.length>0){
					var file = upload.files[0];
					var read = new FileReader();
					read.readAsDataURL(file);
					read.onloadend = function(){
						$("#pro_image").attr("src",read.result);	
					}
				}else{
					console.log("用户没有选中图片")
					$("#pro_image").attr("src",oldImage);
				}
			}
		    
			jQuery(function($) {
					$("#car_id").val(getUrlParam("pro_id"));
					$("#show_id").html(getUrlParam("pro_id"));
					$("#pro_name").val(getUrlParam("pro_name"));
					$("#original_image").val(getUrlParam("pro_img"));
					$("#pro_image").attr("src","../../img/"+getUrlParam("pro_img"));	
			});
			
			function getUrlParam(key) {
			    // 获取参数
			    var url = window.location.search;
			    // 正则筛选地址栏
			    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
			    // 匹配目标参数
			    var result = url.substr(1).match(reg);
			    //返回参数值
			    return result ? decodeURIComponent(result[2]) : null;
			}
		</script>
</body>
</html>

