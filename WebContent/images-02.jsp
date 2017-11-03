<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<!--声明当前页面编码集(中文编码<gbk,gb2312>,国际编码<utf-8>)-->
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="keywords" content="关键词,关键词">
		<meta name="description" content="">
		<title> Java开发网络爬虫实现图片响应式瀑布流系统</title>
        <style type="text/css">
			*{padding:0px;margin:0px;}
			body{font-size:12px;font-family:"Microsoft yahei";background:gray;}
			h1{font-size:20px;color:#806969;text-shadow:1px 1px 10px #111;}
			/*search start*/
			.search{width:360px;height:100px;margin:50px auto;}
			.search .s_box{width:100%;height:30px;padding: 26px;}
			.search .s_box .box_select{width:190px;height:30px;border:1px solid red;outline:none;}
			.search .s_box .box_btn{width:80px;height:30px;background:#ccc;outline:none;border:0;cursor:pointer;transition:1s;}
			.search .s_box .box_btn:hover{background:#666;color:#fff;transition:1s;}
			/*search end*/

			/*wrapper start*/
			#wrapper{width:100%;}
			#container{position:relative;width:900px;margin:0 auto;}
			#grid{width:175px;background:#fff;box-shadow:1px 1px 10px #111;float:left;margin:15px;}
			#grid img{background:url("images/lazyload.gif") no-repeat center;width:100%;}
			#grid .meta{margin:5px 0;}
			#grid .lightbox{text-decoration:none;color:blue;font-size:16px;}
			/*wrapper end*/
		</style>
		<link rel="stylesheet" href="css/jquery.lightbox-0.5.css"/>
	</head>
<body>
	<!--search start-->
	<div class="search">
		<h1>Java开发网络爬虫实现图片响应式瀑布流系统(豆瓣图片)</h1>
		<div class="s_box">
			<select class="box_select">
	   			<option value="all">所有</option>
	   			<option value="sex">大胸妹</option>
	   			<option value="tun">小翘臀</option>
	   			<option value="sw">黑丝袜</option>
	   			<option value="leg">美腿控</option>
	   			<option value="yan">有颜值</option>
	   			<option value="qt">大杂烩</option>
	   		</select>
			<input type="button" value="确定" class="box_btn"/>
		</div>
	</div>
	<!--search end-->

	<!--wrapper start-->
	<div id="wrapper">
		<div id="container">
			<!--  
			<div id	="grid">
				<div><img src="http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f301j20hzxj30dw0dwjtn.jpg"/ width="175" height="200"></div>
				<strong>蒸个男票</strong>
				<div class="meta"><a href="" class="lightbox">高清无码图片</a></div>
			</div>
			-->
		</div>
	</div>
	<!--wrapper end-->

	<!-- 引入js官方类库加jQuery -->
	<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
	<!-- 引入瀑布流效果 -->
	<script type="text/javascript" src="js/blocksit.min.js"></script>
	<!-- 引入图片懒加载 -->
	<script type="text/javascript" src="js/jquery.lazyload.min.js"></script>
	<!-- 引入弹出层 -->
	<script type="text/javascript" src="js/jquery.lightbox-0.5.min.js"></script>
	
	<script type="text/javascript">
		$(function(){
			//需要显示的列数
			var col = 4;
			//页码 页码加载一次为1,加载2次为2
			var pageNum = 0;
			//流式布局
			function blockImage(){
				$("#container").BlocksIt({
					numOfCol:col,//需要显示列
					offsetX:8,//x方向偏移量
					offsetY:8
				});
			}
			
			//给button按钮添加事件
			$(".box_btn").click(function(){
				$("#container").html("");
				pageNum = 0;
				//加载图片
				loadImage();
			});
			
			//加载图片方法
			function loadImage(){
				//获取选中的类型的值
				var category = $(".box_select").val();
				//异步请求 去后台获取数据
				$.ajax({
					type:"post",
					url:"load.do",
					data:{"category":category,"pageNum":pageNum},
					success:function(data){
						//alert(typeof data);
						//var data = eval("("+data+")");
						var data = JSON.parse(data);
						var html = "";
						for(var i=0;i<data.length;i++){
							html += "<div id='grid'>"+
							"	<div><img class='lazy' src='images/pixel.gif' data-original='"+data[i].sUrl+"'  width='175' height='200'></div>"+
							"	<strong>"+data[i].title+"</strong>"+
							"	<div class='meta'><a href='"+data[i].bigUrl+"' class='lightbox'>高清无码图片</a></div>"+
							"	</div>";
						}
						$("#container").append(html);
						pageNum++;//页码++
						blockImage();//图片流式布局
						$("img.lazy").lazyload();//懒加载
						$("a.lightbox").lightBox();//查看大图
					}
				});
			}
			
			//分页
			$(window).scroll(function(){
				//判断滚动条是否到了屏幕底部
				if( $(document).height() - $(window).scrollTop() - $(window).height() < 100){
					//加载下一页
					loadImage();
				}
			});
		});
	</script>
</body>

</html>
