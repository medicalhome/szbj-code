// 获取开始和结束点坐标
function getStartEnd(obj1, obj2, type1, type2) {

    var result = {};
    result.start = {};
    result.end = {};
    if(!type1&&!type2)
    {
        // 默认情况下，使用两个圆的圆心连线进行路线绘制
        var x1 = obj1.attrs.cx;
        var y1 = obj1.attrs.cy;
        var x2 = obj2.attrs.cx;
        var y2 = obj2.attrs.cy;
        var r = obj2.attrs.r + 4;


        var angle = Raphael.angle(x1, y1, x2, y2); //得到两点之间的角度
        var rand = Raphael.rad(angle); //角度转换成弧度
        result.start.x = x1 - Math.cos(rand) * r;
        result.start.y = y1 - Math.sin(rand) * r;
        result.end.x = x2 + Math.cos(rand) * r;
        result.end.y = y2 + Math.sin(rand) * r;
    }
    

    // 如果传递了连接点的信息，则使用连接点进行连接
    var bb1 = obj1.getBBox(),bb2 = obj2.getBBox();
    var p = [
                { x: bb1.x + bb1.width / 2, y: bb1.y - 4 }, // 上
                { x: bb1.x + bb1.width / 2, y: bb1.y + bb1.height + 4 }, //下
                { x: bb1.x - 4, y: bb1.y + bb1.height / 2 },  //左
                { x: bb1.x + bb1.width + 4, y: bb1.y + bb1.height / 2 }, // 右

                { x: bb2.x + bb2.width / 2, y: bb2.y - 4 },
                { x: bb2.x + bb2.width / 2, y: bb2.y + bb2.height + 4 },
                { x: bb2.x - 4, y: bb2.y + bb2.height / 2 },
                { x: bb2.x + bb2.width + 4, y: bb2.y + bb2.height / 2 }
            ];
    
    
    if (type1 == "left") {
        result.start.x = p[2].x;
        result.start.y = p[2].y;
    }
    else if (type1 == "top") {
        result.start.x = p[0].x;
        result.start.y = p[0].y;
    }
    else if (type1 == "right") {
        result.start.x = p[3].x;
        result.start.y = p[3].y;
    }
    else if (type1 == "bottom") {
        result.start.x = p[1].x;
        result.start.y = p[1].y;
    }

    if (type2 == "left") {
        result.end.x = p[6].x;
        result.end.y = p[6].y;
    }
    else if (type2 == "top") {
        result.end.x = p[4].x;
        result.end.y = p[4].y;
    }
    else if (type2 == "right") {
        result.end.x = p[7].x;
        result.end.y = p[7].y;
    }
    else if (type2 == "bottom") {
        result.end.x = p[5].x;
        result.end.y = p[5].y;
    }
    
    return result;
}

//获取节点间的路径
function getArr(x1, y1, x2, y2, size, paper, connection,distance,position) {

	var arrPath = {};
    var arrows = [];
    var angle = Raphael.angle(x1, y1, x2, y2); //得到两点之间的角度
    var aline = Raphael.rad(angle);
    var x2_end = x2 + Math.cos(aline) *12;
    var y2_end = y2 + Math.sin(aline) *12;

    var result = ["M", x1, y1, "L", x2_end, y2_end]; 
    
    if(connection.path)
    {
        result = ["M",x1,y1];
        if(connection.complex==="true"){
        	for (var idx in connection.path) {
        		var index = Number(idx);
        		if(connection.path[index]==="L"){
        			result.push(connection.path[index],connection.path[index+1]*distance.xDis+position.x,connection.path[index+2]*distance.yDis+position.y);
        		}else if(connection.path[index]==="C")
    			{
        			result.push(connection.path[index],connection.path[index+1]*distance.xDis+position.x,connection.path[index+2]*distance.yDis+position.y,connection.path[index+3]*distance.xDis+position.x,connection.path[index+4]*distance.yDis+position.y,connection.path[index+5]*distance.xDis+position.x,connection.path[index+6]*distance.yDis+position.y);
    			}else if(connection.path[index]==="A"){
        			result.push(connection.path[index],connection.path[index+1],connection.path[index+2],connection.path[index+3],connection.path[index+4],connection.path[index+5],connection.path[index+6]*distance.xDis+position.x,connection.path[index+7]*distance.yDis+position.y);
        		}
        		/*if(Number(index)%3==1){
        			result.push(connection.path[index-1],connection.path[index]*distance.xDis+position.x);
        		}else if(Number(index)%3==2){
        			result.push(connection.path[index]*distance.yDis+position.y);
        		}*/
        	}  
        }else{
        	for (var index in connection.path) {
        		if(Number(index)%2==0){
        			result.push("L",connection.path[index]*distance.xDis+position.x);
        		}else{
        			result.push(connection.path[index]*distance.yDis+position.y);
        		}
        	}        	
        }
        angle = Raphael.angle(result[result.length-2], result[result.length-1], x2, y2); //得到两点之间的角度
        aline = Raphael.rad(angle);
        x2_end = x2 + Math.cos(aline) *12;
        y2_end = y2 + Math.sin(aline) *12;
        result.push("L",x2_end,y2_end);
        arrows = getArrows(result[result.length-5], result[result.length-4], x2, y2, size, paper);
    }else
    {
        arrows = getArrows(x1, y1, x2, y2, size, paper);
    }
    arrPath.path = result;
    arrPath.arrows = arrows;
    
    return arrPath;
}
//获取箭头的路径并绘制箭头
function getArrows(x1, y1, x2, y2, size, paper)
{
    var angle = Raphael.angle(x1, y1, x2, y2); //得到两点之间的角度
    var a45 = Raphael.rad(angle - 15); //角度转换成弧度
    var a45m = Raphael.rad(angle + 15);
    var x2a = x2 + Math.cos(a45) * size;
    var y2a = y2 + Math.sin(a45) * size;
    var x2b = x2 + Math.cos(a45m) * size;
    var y2b = y2 + Math.sin(a45m) * size;
    var arrows = ["M",x2a,y2a,"L",x2,y2,"L",x2b,y2b,"Z"];
    return arrows;
}

Raphael.fn.drawArr = function(obj) {
    connection = obj.connection;
    var point = getStartEnd(obj.obj1, obj.obj2, connection.type1, connection.type2);
    var arrPath = getArr(point.start.x, point.start.y, point.end.x, point.end.y, 18, this, connection,obj.distance,obj.position);
    
    /*if (obj.arrPath) {
        obj.arrPath.attr({ path: path1 });
    } else {
        obj.arrPath = this.path(path1).attr({"stroke":connection.color,"stroke-width":"2.2",fill:connection.color});
    }*/
    //linkPath = this.path(arrPath.path).attr({"stroke":connection.color,"stroke-width":"2.2"});
    //arrowsPath = this.path(arrPath.arrows).attr({ fill: connection.color,stroke: connection.color});
    obj.path = this.path(arrPath.path).attr({"stroke":connection.color,"stroke-width":"5"});
    obj.arrows = this.path(arrPath.arrows).attr({ fill: connection.color,stroke: connection.color});
    if(connection.note){
        obj.note = this.text(connection.note.x*obj.distance.xDis+obj.position.x,connection.note.y*obj.distance.yDis+obj.position.y,connection.note.content).attr({fill: connection.color,'font-size': '12','font-family':'微软雅黑','font-weight':'bold'});;
    }
    return obj;
};
