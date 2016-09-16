<%@page contentType="text/html; charset=gbk"%>
<html>
    <head>
        <title>
            分析报表2
        </title>
	<script type="text/javascript" src="js/drag.js"></script>
        <script type="text/javascript">
//------------------------Utility------------------------
function findPosX(obj) {//辅助函数 得到元素左边与浏览器左边的边距
	var curleft = 0;
	if (obj && obj.offsetParent) {
		while (obj.offsetParent) {
			curleft += obj.offsetLeft;
			obj = obj.offsetParent;
		}
	} else if (obj && obj.x) curleft += obj.x;
	return curleft;// + document.body.scrollLeft - document.body.clientLeft;
}

function findPosY(obj) {//辅助函数 得到元素上边与浏览器上边的边距
	var curtop = 0;
	if (obj && obj.offsetParent) {
		while (obj.offsetParent) {
			curtop += obj.offsetTop;
			obj = obj.offsetParent;
		}
	} else if (obj && obj.y) curtop += obj.y;
	return curtop;// + document.body.scrollTop - document.body.clientTop;
}


var dragGhost = document.createElement("div");
dragGhost.style.border = "dashed 1px #CCCCCC";
dragGhost.style.background = "white";
dragGhost.style.display = "none";
dragGhost.style.margin = "10px";

var container;
var columns = [];
//------------------------Start Here------------------------
window.onload = function(){
	container = document.getElementById("container");
	
	for(var i=0;i<container.childNodes.length;i++){
		if(container.childNodes[i].className == "column"){//筛选出所有的列 ff下的childNodes不可靠 :\
			columns.push(container.childNodes[i]);
		}
	}
	for(var i=0;i<columns.length;i++){
		var column = columns[i];
		for(var j=0;j<column.childNodes.length;j++){
			var item = column.childNodes[j];
			if(item.className == "item"){
				item.column = column;//给每个拖拽对象要指明它属于哪一列 而且这个属性会随着拖动而更新的
				
				new dragItem(item);
			}
		}
	}
}
var isIE = document.all;

//------------------------Drag Item------------------------
function dragItem(item){
	//item实际上是dragBody(拖动的时候移动的整体)
	//在这里需要根据item找到handle(能够拖动的把手)
	
	var handle;
	for(var i=0;i<item.childNodes.length;i++){
		if(item.childNodes[i].nodeName.toLowerCase() == "h5"){
			handle = item.childNodes[i];
			break;
		}
	}
	if(!handle)return;
	Drag.init(handle,item);
	item.onDragStart = function(left,top,mouseX,mouseY){
		//开始拖动的时候设置透明度
		
		this.style.opacity = "0.5";
		this.style.filter = "alpha(opacity=50)";
		dragGhost.style.height = (isIE?this.offsetHeight:this.offsetHeight - 2) + "px";
		
		//this指的是item
		
		this.style.width = this.offsetWidth + "px";//因为初始的width为auto
		this.style.left = (findPosX(this) - 5) + "px";
		this.style.top = (findPosY(this) - 5) + "px";
		this.style.position = "absolute";
		//将ghost插入到当前位置
		dragGhost.style.display = "block";
		this.column.insertBefore(dragGhost,this);
		
		
		//记录每一列的左边距 在拖动过程中判断拖动对象所在的列会用到
		this.columnsX = [];
		for(var i=0;i<columns.length;i++){
			this.columnsX.push(findPosX(columns[i]));
		}
			
	}
	item.onDrag = function(left,top,mouseX,mouseY){
	
		//先要判断在哪一列移动
		var columnIndex = 0; 
		
		for(var i=0;i<this.columnsX.length;i++){
			if((left + this.offsetWidth/2) > this.columnsX[i]){
				columnIndex = i;
			}
		}
		//如果columnIndex在循环中没有被赋值 则表示当前拖动对象在第一列的左边
		//此时也把它放到第一列
		
		var column = columns[columnIndex];
		
		if(this.column != column){
			//之前拖动对象不在这个列
			//将ghost放置到这一列的最下方
			
			column.appendChild(dragGhost);
			this.column = column;
		}
		
		//然后在判断放在这一列的什么位置
		
		var currentNode = null;
		for(var i=0;i<this.column.childNodes.length;i++){
			if(this.column.childNodes[i].className == "item"
			&& this.column.childNodes[i] != this//不能跟拖动元素自己比较 否则不能在本列向下移动
			&& top < findPosY(this.column.childNodes[i])){//从上到下找到第一个比拖动元素的上边距大的元素
			
				currentNode = this.column.childNodes[i];
				break;
			}
		}
		if(currentNode)
			this.column.insertBefore(dragGhost,currentNode);
		else//拖到最下边 没有任何一个元素的上边距比拖动元素的top大 则添加到列的最后
		
			this.column.appendChild(dragGhost);
	}
	item.onDragEnd = function(left,top,mouseX,mouseY){
		this.style.opacity = "1";
		this.style.filter = "alpha(opacity=100)";
		
		this.column.insertBefore(this,dragGhost);
		
		this.style.position = "static";
		this.style.display = "block";
		this.style.width = "auto";
		dragGhost.style.display = "none";
		
		query();
	}
}

function query(){
	setSize();
	var xdarr = '';
	for(var i=0;i<columns[1].childNodes.length;i++){
		if(columns[1].childNodes[i].className == "item"){
			if(xdarr != '')
				xdarr += ',';
			xdarr += columns[1].childNodes[i].id;
		}
	}
	var ydarr = '';
	for(var i=0;i<columns[2].childNodes.length;i++){
		if(columns[2].childNodes[i].className == "item"){
			if(ydarr != '')
				ydarr += ',';
			ydarr += columns[2].childNodes[i].id;
		}
	}
		paramForm.params.value = "xd=" + xdarr + ";yd=" + ydarr;
		paramForm.submit();	
}

function setSize(){
	var dragArea = document.getElementById('container');
	var rpt_div = document.getElementById('rpt_div');
	var frm = document.getElementById('paramForm');
	rpt_div.style.height = document.body.clientHeight - dragArea.clientHeight - frm.clientHeight - 50;
}
        </script>
        
        <style type="text/css">
			#container{width:100%;}
			.column{width:100px;border:solid 1px #CCCCCC;background:#FCFCFC;padding:5px;float:left;margin:10px;}
			.item{text-align:center;padding:0px;margin:5px;border:solid 1px #CCCCCC;background:white;width:auto;}
			.item h5{margin:0px;height:16px;background:#ACCCCC;color:white;cursor:move;}
        </style>
    </head>
    <body style="font-size:13px;" onresize="setSize();">
      <div id="container">
				<div class="column">
					<b>&nbsp;&nbsp;可选维度</b>
					<div class="item" id='area'>
						<h5>地区</h5>
					</div>
					<div class="item" id='year'>
						<h5>年份</h5>
					</div>
					<div class="item" id='employee'>
						<h5>销售员</h5>
					</div>
					<div class="item" id='type'>
						<h5>产品类别</h5>
					</div>
				</div>
				
				<div class="column" id='xd'>
					<b>&nbsp;&nbsp;横向维度</b>
				</div>
				
				<div class="column" id='xd'>
					<b>&nbsp;&nbsp;纵向维度</b>
				</div>
       </div>
       
  			<form id="paramForm" method="post"  action='/report/ReportEmitter' method='post' target='rpt_frame'>
  			  <input type="hidden" name="rpt" value="Demo/更多应用/分析报表2.brt">
				  <input type="hidden" name="params" value="">
				  <input type="hidden" name="vars" value="">
				  <input type="hidden" name="toolbardisplay" value="top">
				  <input type="hidden" name="fitwidth" value="true">
  			</form>
  			
						<script language='javascript'>
							function test(){
								var targetFrame = document.getElementById("rpt_frame_id");
								if(targetFrame) {
									query();
									clearInterval(interval)
								}
							}
							var interval = setInterval(test, 200);
						</script>
  			
  			<div id="rpt_div" style="width:100%;">
					<iframe src="" id="rpt_frame_id" name="rpt_frame" frameborder="0" style='width:100%;height:100%'>
    		</div>
    </body>
</html>