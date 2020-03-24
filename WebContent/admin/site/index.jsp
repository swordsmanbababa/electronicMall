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
							<li class="active">商品管理</li>
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
								商品管理
								<small>
									<i class="icon-double-angle-right"></i>
									 查看
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<table class="table table-bordered" id="content">
									
								</table>
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
						<div id="nav">
						
						</div>
						
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
		<script src="${pageContext.request.contextPath }/js/layer.js"></script>
		<script src="${pageContext.request.contextPath }/js/mylayer.js"></script>

		<!-- inline scripts related to this page -->

		<script type="text/javascript">
			var start = 0; //开始位置
			var length = 10;//每次请求的数据条数
			var total = 0;  //总的数据长度
			var currentPage = 1; //存储当前选中的页数
			var pageCount = 0; //总的页数
			jQuery(function($) {
			     request_total();//获取数据库的总条数
				 getProductIndex(start,length);			  
			});
			
			//获取总的条目数
			function request_total(){
				$.ajax({
					type:"GET",
					url:"${pageContext.request.contextPath}/totalCountProduct",
					dataType:"json",
					success:function(data){
						console.log(data);
						if(data.retcode==="0"){
							total = data.data;
							//显示分页的控件
							showNav();
						}
					},
					error:function(xhr,error,status){
						console.log(error);
					}
				})
			}
			
			//显示底部的分页标签
			function showNav(){
				var nav = $("<nav aria-label='Page navigation'>");
				var ul = $("<ul class='pagination'>");
				var li = $("<li id='first' class='disabled'>");
				var span = $("<span aria-hidden='true'>&laquo;</span>");
				var firstArrow = $("<a aria-label='Previous'>")
				firstArrow.append(span);
				li.append(firstArrow);
				ul.append(li);
				pageCount =Math.ceil(total/length);
				for(var i=1;i<=pageCount;i++){
					if(i==1){
						var templi = $("<li class='active page'><a>"+i+"</a> </li>")
						ul.append(templi);
					}else{
						ul.append($("<li class='page'><a>"+i+"</a></li>"))
					}
				}
				
				var nextLi = $("<li id='next'>");
				var nextArrow = $("<a aria-label='Next'>");
	            var span = $("<span aria-hidden='true'>&raquo;</span>");
	            nextArrow.append(span);
	            nextLi.append(nextArrow);
	            ul.append(nextLi);
	            nav.append(ul);
	            $("#nav").append(nav);
	            
	            //填写 不同的 li的监听事件  
	            addLiEvent();
			}
			
			//给li元素 添加不同的监听事件  上一页和下一页的逻辑 不一样的实现
			function addLiEvent(){
				$(".pagination li.page a").click(function(){
					//清空上一次的active状态 
					$(".active").attr("class","page");
					//当前的元素 选中状态
					$(this).parent().attr("class","active");
					//判断当前点击的是相邻的第一个或者最后一个
					currentPage = $(this).html();
					console.log($(this).html());
					if(currentPage>1&&currentPage<pageCount){
						$("#first").attr("class","");
						$("#next").attr("class","");
					}else if(currentPage==1){
						$("#first").attr("class","disabled");
					}else if(currentPage==pageCount){
						$("#next").attr("class","disabled");
					}
					//开始请求数据了
					getProductIndex((currentPage-1)*length,length);
				});
				
				//监听上一页
				$("#first a").click(function(){
					if(currentPage>1){
						var currentElement = $(".active");
						var previousElement = $(".active").prev();
						currentElement.attr("class","page");
						previousElement.attr("class","active");
						currentPage = parseInt(currentPage)-1;
						if(currentPage>=1){
							getProductIndex((currentPage-1)*length,length);
							if(currentPage==1){
								$("#first").attr("class","disabled");
							}
						}					
					}
				})
				
				//监听下一页
				$("#next a").click(function(){
					if(currentPage<pageCount){
						var currentElement = $(".active");
						var nextElement = $(".active").next();
						currentElement.attr("class","page");
						nextElement.attr("class","active");
						currentPage = parseInt(currentPage)+1;
						if(currentPage<=pageCount){
							getProductIndex((currentPage-1)*length,length);
							if(currentPage==pageCount){
								$("#next").attr("class","disabled");
							}
						}
					}
				});
			}
		
			 //商品列表显示出来
			 function getProductIndex(start,length){
		    	 $.ajax({
		    		 type:"GET",
		    		 url:"${pageContext.request.contextPath}/selectProductJSON",
		    		 data:{
		    			 start:start,
		    			 length:length
		    		 },
		    		 dataType:"json",
		    		 success:function(data){
		    			    console.log(data);
		    			    if(data.retcode==="0"){
		    			    	var content = $("#content");
		    			    	content.empty();  //清空所有的内容部分
		    			    	var thead = $("<tr>\
				    			    	        <th>商品id</th>\
				    			    	        <th>商品名称</th>\
				    			    	        <th>商品价格</th>\
				    			    	        <th>商品描述</th>\
												<th>图片</th>\
												<th>添加时间</th>\
												<th>商品操作</th>\
											</tr>");
		    			    	content.append(thead);
								for(var i=0;i<data.data.length;i++){
									var tr = $("<tr>");
									var pro_id_td = $("<td>"+data.data[i].pro_id+"</td>"); 
									tr.append(pro_id_td);
									
									var pro_id_name = $("<td style='width:100px'>"+data.data[i].pro_name+"</td>"); 
									tr.append(pro_id_name);
									
									var pro_id_price = $("<td>"+data.data[i].pro_price+"</td>"); 
									tr.append(pro_id_price);
									
									var pro_id_desc = $("<td style='width:200px;'>"+data.data[i].pro_desc+"</td>"); 
									tr.append(pro_id_desc);
									
									var pro_image_td = $("<td><img style='width:180px' src='../../images/"+data.data[i].pro_image+"'/></td>");
									tr.append(pro_image_td);
									
									var pro_td_create = $("<td>"+data.data[i].pro_create+"</td>")
									tr.append(pro_td_create);
									
									var editBtn = $("<a href='editProduct.jsp?pro_id="+data.data[i].pro_id+"'>编辑</a>");
									var span = $("<span>&nbsp;&nbsp</span>");
									var deleteBtn = $("<a href='javascipt:void(0)' onclick='deleteBtn("+data.data[i].pro_id+")'>删除</a>");
									
									var operate_td = $("<td>");
									
									operate_td.append(editBtn);
									operate_td.append(span);
									operate_td.append(deleteBtn);
									
									tr.append(operate_td);
									
									content.append(tr);	
								}
		    			    }else{	
		    			    	var ii = layer.open({
						    		  type: 1, //Page层类型
						    		  title:false,
						    		  area: ['150px', '50px'],
						    		  closeBtn: 0,
						    		  shadeClose: true,
						    		  shade: 0.6, //遮罩透明度
						    		  content: "<p style='text-align:center;line-height:30px'>"+data.data+"</p>"
						    	}); 
		    			    }
		    		 },
		    		 error:function(xhr,status,error){
		    			 console.log(error);
		    			 console.log(status);
		    			 var ii = layer.open({
				    		  type: 1, //Page层类型
				    		  title:false,
				    		  area: ['150px', '50px'],
				    		  closeBtn: 0,
				    		  shadeClose: true,
				    		  shade: 0.6, //遮罩透明度
				    		  content: "<p style='text-align:center;color:red;line-height:30px'>"+status+"</p>"
				    	}); 
		    		 }
		    	 })
		     }	
			 function deleteBtn(pro_id){
		    	 //console.log(pro_id);
		    	 // confirm 问下用户是否确定删除
		    	 layer.confirm('确定是否要删除该商品', {
		    		  btn: ['确定','取消'] //按钮
		    		}, function(){
		    			console.log("ok");
		    		  //layer.msg('的确很重要', {icon: 1});
		    		  //开始Loading
		    		  mylayer.showLoading("玩命加载中...");
		    		    $.ajax({
		    		    	type:"GET",
		    		    	url:"${pageContext.request.contextPath}/deleteProductJSON",
		    		    	data:{
		    		    		pro_id:pro_id
		    		    	},
		    		    	dataType:"json",
		    		    	
		    		    	success:function(data){
		    		    		mylayer.closeLoading();
		    		    		console.log(data);
		    		    		if(data.retcode==="0"){
		    		    			mylayer.showCallBackMessage(data.data,function(){
		    							window.location.href  = "index.jsp";
		    						});
		    		    		}else{
		    		    			mylayer.showMessage(data.data);
		    		    		}
		    		    	},
		    		    	error:function(xhr,error,status){
		    		    		mylayer.closeLoading();
		    		    		console.log(error);
		    		    		mylayer.showMessage(error);
		    		    	}
		    		    })
		    		}, function(){
		    		    console.log("fail");
		    		});
		     }
		</script>
</body>
</html>

