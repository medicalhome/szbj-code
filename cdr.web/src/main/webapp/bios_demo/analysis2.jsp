<%@page contentType="text/html; charset=gbk"%>
<html>
    <head>
        <title>
            ��������2
        </title>
	<script type="text/javascript" src="js/drag.js"></script>
        <script type="text/javascript">
//------------------------Utility------------------------
function findPosX(obj) {//�������� �õ�Ԫ��������������ߵı߾�
	var curleft = 0;
	if (obj && obj.offsetParent) {
		while (obj.offsetParent) {
			curleft += obj.offsetLeft;
			obj = obj.offsetParent;
		}
	} else if (obj && obj.x) curleft += obj.x;
	return curleft;// + document.body.scrollLeft - document.body.clientLeft;
}

function findPosY(obj) {//�������� �õ�Ԫ���ϱ���������ϱߵı߾�
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
		if(container.childNodes[i].className == "column"){//ɸѡ�����е��� ff�µ�childNodes���ɿ� :\
			columns.push(container.childNodes[i]);
		}
	}
	for(var i=0;i<columns.length;i++){
		var column = columns[i];
		for(var j=0;j<column.childNodes.length;j++){
			var item = column.childNodes[j];
			if(item.className == "item"){
				item.column = column;//��ÿ����ק����Ҫָ����������һ�� ����������Ի������϶������µ�
				
				new dragItem(item);
			}
		}
	}
}
var isIE = document.all;

//------------------------Drag Item------------------------
function dragItem(item){
	//itemʵ������dragBody(�϶���ʱ���ƶ�������)
	//��������Ҫ����item�ҵ�handle(�ܹ��϶��İ���)
	
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
		//��ʼ�϶���ʱ������͸����
		
		this.style.opacity = "0.5";
		this.style.filter = "alpha(opacity=50)";
		dragGhost.style.height = (isIE?this.offsetHeight:this.offsetHeight - 2) + "px";
		
		//thisָ����item
		
		this.style.width = this.offsetWidth + "px";//��Ϊ��ʼ��widthΪauto
		this.style.left = (findPosX(this) - 5) + "px";
		this.style.top = (findPosY(this) - 5) + "px";
		this.style.position = "absolute";
		//��ghost���뵽��ǰλ��
		dragGhost.style.display = "block";
		this.column.insertBefore(dragGhost,this);
		
		
		//��¼ÿһ�е���߾� ���϶��������ж��϶��������ڵ��л��õ�
		this.columnsX = [];
		for(var i=0;i<columns.length;i++){
			this.columnsX.push(findPosX(columns[i]));
		}
			
	}
	item.onDrag = function(left,top,mouseX,mouseY){
	
		//��Ҫ�ж�����һ���ƶ�
		var columnIndex = 0; 
		
		for(var i=0;i<this.columnsX.length;i++){
			if((left + this.offsetWidth/2) > this.columnsX[i]){
				columnIndex = i;
			}
		}
		//���columnIndex��ѭ����û�б���ֵ ���ʾ��ǰ�϶������ڵ�һ�е����
		//��ʱҲ�����ŵ���һ��
		
		var column = columns[columnIndex];
		
		if(this.column != column){
			//֮ǰ�϶������������
			//��ghost���õ���һ�е����·�
			
			column.appendChild(dragGhost);
			this.column = column;
		}
		
		//Ȼ�����жϷ�����һ�е�ʲôλ��
		
		var currentNode = null;
		for(var i=0;i<this.column.childNodes.length;i++){
			if(this.column.childNodes[i].className == "item"
			&& this.column.childNodes[i] != this//���ܸ��϶�Ԫ���Լ��Ƚ� �������ڱ��������ƶ�
			&& top < findPosY(this.column.childNodes[i])){//���ϵ����ҵ���һ�����϶�Ԫ�ص��ϱ߾���Ԫ��
			
				currentNode = this.column.childNodes[i];
				break;
			}
		}
		if(currentNode)
			this.column.insertBefore(dragGhost,currentNode);
		else//�ϵ����±� û���κ�һ��Ԫ�ص��ϱ߾���϶�Ԫ�ص�top�� ����ӵ��е����
		
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
					<b>&nbsp;&nbsp;��ѡά��</b>
					<div class="item" id='area'>
						<h5>����</h5>
					</div>
					<div class="item" id='year'>
						<h5>���</h5>
					</div>
					<div class="item" id='employee'>
						<h5>����Ա</h5>
					</div>
					<div class="item" id='type'>
						<h5>��Ʒ���</h5>
					</div>
				</div>
				
				<div class="column" id='xd'>
					<b>&nbsp;&nbsp;����ά��</b>
				</div>
				
				<div class="column" id='xd'>
					<b>&nbsp;&nbsp;����ά��</b>
				</div>
       </div>
       
  			<form id="paramForm" method="post"  action='/report/ReportEmitter' method='post' target='rpt_frame'>
  			  <input type="hidden" name="rpt" value="Demo/����Ӧ��/��������2.brt">
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