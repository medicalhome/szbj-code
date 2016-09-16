; (function($) {
    var methods = {
        init: function(options) {
            return this.each(function() {
                var $this = $(this);

                var defaultOptions = {
                    canvas: { width: 1150, height: 410},
                    position: { x: 100, y: 200 }, //起始位置
                    draw: { type: "circle", radius: 12, attr: {stroke: '#616161', 'stroke-width': 2} }, //circle
                    distance: { xDis: 120, yDis: 120},//点之间的单位间距
                    statusColor:{unactive:"90-#70706b-#a9aaa1-#ebece5",active:"90-#196606-#4cbf2f-#a8e281",now:"90-#b2ad2d-#f5ec0e-#f1eb46"},//节点在不同情景下的颜色
                    processLineColor:{unactive:"#aaa",active:"#00b3ff"},//箭头在不同情景下的颜色
                    animateEffect:"off"//动画效果开启或关闭 on

                    /*statusContents: [
                             { time: '2012-08-04 09:00', person: '王华', dept: '肾内科', name: "医嘱已下达" ,code:"10" },
                             { time: '2012-08-04 10:00', person: '李鸣', dept: '收费处', name: "已收费" ,code:"02" },
                             { time: '2012-08-04 11:00', person: '张兰', dept: '检验科', name: "标本已采集" ,code:"30" },
                             { time: '2012-08-04 12:00', person: '冯小峰', dept: '检验科', name: "标本已签收" ,code:"50" },
                             { time: '2012-08-04 14:00', person: '杨幂', dept: '检验科', name: "已退检" ,code:"78" },
                             { time: '2012-08-04 15:00', person: '陈红', dept: '检验科', name: "标本已采集" ,code:"30" },
                             { time: '2012-08-04 16:00', person: '贾岛', dept: '检验科', name: "标本已签收" ,code:"50" },
                             { time: '2012-08-05 14:00', person: '贾玉', dept: '检验科', name: "已退检" ,code:"78" },
                             { time: '2012-08-05 15:00', person: '戴斌', dept: '检验科', name: "标本已采集" ,code:"30" },
                             { time: '2012-08-05 16:00', person: '王茹', dept: '检验科', name: "标本已签收" ,code:"50" },
                             { time: '2012-08-06 10:00', person: '李胜', dept: '检验科', name: "培养中" ,code:"720" },
                             { time: '2012-08-06 11:00', person: '杨小龙', dept: '检验科', name: "涂片中" ,code:"721" },
                             { time: '2012-10-21 12:00', person: '尼坤', dept: '检验科', name: "药敏中" ,code:"723" },
                             { time: '2012-10-21 12:00', person: '陈坤', dept: '检验科', name: "鉴定中" ,code:"722" },
                             { time: '2012-10-21 12:00', person: '冯绍峰', dept: '检验科', name: "检验已完成" ,code:"72" },
                             { time: '2012-10-21 12:00', person: '冯绍峰', dept: '检验科', name: "检验已完成" ,code:"72" },
                             { time: '2012-10t-21 12:00', person: '李寻欢', dept: '检验科', name: "检验报告已审核" ,code:"91" },
                             { time: '2012-10-21 12:00', person: '江小鱼', dept: '检验科', name: "报告召回" ,code:"100" },
                             { time: '2012-10-21 12:00', person: '江小鱼', dept: '检验科', name: "报告召回" ,code:"100" },
                             { time: '2012-10-21 12:00', person: '段誉', dept: '检验科', name: "检验报告已审核" ,code:"91" },
                             { time: '2012-08-06 13:00', person: '李晓斌', dept: '检验科', name: "已取消" ,code:"51" },
                             { time: '2012-08-06 13:00', person: '李晓斌', dept: '检验科', name: "已取消" ,code:"51" },
                             { time: '2012-08-06 14:00', person: '吴国华', dept: '检验科', name: "医嘱已取消" ,code:"73" },
                             { time: '2012-08-06 14:00', person: '吴国华', dept: '检验科', name: "医嘱已取消" ,code:"73" },
                             { time: '2012-08-06 15:00', person: '周树人', dept: '收费处', name: "已收费" ,code:"92" },
                             { time: '2012-08-06 15:00', person: '周树人', dept: '收费处', name: "已收费" ,code:"40" },
                             { time: '2012-08-06 17:00', person: '周作人', dept: '检验科', name: "医嘱已取消" ,code:"130" },
                             { time: '2012-08-06 17:00', person: '周作人', dept: '检验科', name: "医嘱已取消" ,code:"40" }
                                   ]*/

                };

                var opts = $.extend(defaultOptions, options);
                var $cpoe = $this.data('cpoe');
                if (typeof($cpoe) == 'undefined') {
                    $cpoe = new $.cpoe(this, opts);
                    $cpoe.init();
                    $this.data('cpoe', $cpoe);
                }
            });
        },
        destroy: function() {
            return this.each(function() {
                var $this = $(this);
                var $cpoe = $this.data('cpoe');
                if (typeof($cpoe) != 'undefined') {
                    $cpoe.destroy();
                    $this.removeData('cpoe');  
                }
            });
        }
    };

    $.fn.cpoe = function(method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.cpoe');
        }
    };

    $.cpoe = function(holder, opts) {
    	function getLinefeedString(originalString,wordsNum){
    		var linefeedString='';
    		for (var i = 0; i < Math.ceil(originalString.length/wordsNum); i++) {
    			if((i+1)*wordsNum > originalString.length)
    				linefeedString += originalString.substr(i*wordsNum);
    			else
    				linefeedString += originalString.substr(i*wordsNum,wordsNum)+'\n';
    		}
    		return linefeedString;
    	}
        this.init = function() {
            // 存储已经激活的节点编号
            var stateActivePath = [];//不包含微生物涂片节点及其后的检验已审核节点
            var stateActivePathAll = [];//所有节点(包含微生物涂片节点及其后的检验已审核节点的)
            var stateActivePathSmearPart = [];//微生物涂片节点及其后的检验已审核节点部分
            var animateCorrectConns = [];//保存用于修正动画效果的路径
            var draw = options.draw;

            for (var index in options.status) {
                var shape = {};
                var textContentPerson = [];
                var textContentTime = [];
                var textContentDept = [];
                var labelContent = [];
                // 初始化特殊显示信息对象
                var statusContent = paper.set();
                // 初始化label对象
                var label = paper.set();
                var customShape = {x:"",y:"",person:"",time:"",dept:"",color:options.statusColor.unactive};
                customShape.x=options.position.x+options.status[index].position.x*options.distance.xDis;
                customShape.y=options.position.y+options.status[index].position.y*options.distance.yDis;

                var statusName = options.status[index].name;

                // 使用用户数据进行标签匹配
                for (var i = 0; i < options.statusContents.length; i++) {
                    if (options.statusContents[i].code == options.status[index].code) {
                        //设置节点的颜色和节点下的注释信息
                        customShape.person = options.statusContents[i].person;
                        customShape.time = options.statusContents[i].time=='null'?'':options.statusContents[i].time;
                        customShape.dept = options.statusContents[i].dept;
                        customShape.color = options.statusColor.active;
                        // 最后一个节点为当前节点，修改其颜色
                        if(i == options.statusContents.length-1)
                        {
                            customShape.color = options.statusColor.now;
                        }
                        //label.push(paper.text(customShape.x, customShape.y-label.length*(label[0]?label[0].getBBox().height+2:0), customShape.person+"  "+customShape.time+"  "+customShape.dept)).attr({fill: 'black','font-size': '12','font-family':'微软雅黑'});
                        labelContent.push(customShape.person);
                        labelContent.push(customShape.time+"  "+customShape.dept);
                        //label.push(paper.text(customShape.x, customShape.y-label.length/2*(label[0]?label[0].getBBox().height+2:0), customShape.person)).attr({fill: 'black','font-size': '12','font-family':'微软雅黑','text-anchor':'start'});
                        //label.push(paper.text(customShape.x+50, customShape.y-(label.length-1)/2*(label[0]?label[0].getBBox().height+2:0), customShape.time+"  "+customShape.dept)).attr({fill: 'black','font-size': '12','font-family':'微软雅黑','text-anchor':'start'});
                        //label.hide();
                        //时间格式化
                        customShape.time = customShape.time.substring(0,customShape.time.indexOf(" "))+"\n"+customShape.time.substring(customShape.time.indexOf(" "));
                        textContentPerson.push(paper.text(customShape.x, customShape.y+22,customShape.person).attr({fill: 'black','font-size': '12','font-family':'微软雅黑',opacity: 0}));
                        textContentTime.push(paper.text(customShape.x, customShape.y+45,customShape.time).attr({fill: 'black','font-size': '12','font-family':'微软雅黑',opacity: 0}));
                        //科室长度格式化
                        var linefeedString = getLinefeedString(customShape.dept,8);
                        var customShapeDept = paper.text(customShape.x, customShape.y+60,linefeedString).attr({fill: 'black','font-size': '12','font-family':'微软雅黑',opacity: 0});
                        textContentDept.push(customShapeDept.transform('t0,'+customShapeDept.getBBox().height/2));
                        //添加特殊显示信息
                        if(options.statusContents[i].statusContent&&statusContent.length==0)
                        {
                            statusContent.push(paper.text(customShape.x, customShape.y, options.statusContents[i].statusContent).attr({fill: 'black','font-size': '12','font-family':'微软雅黑','text-anchor':'start'}));
                            statusContent.hide();
                        }  
                    }
                }

                // 绘制节点和文字信息
                if (options.status[index].special != "true"||customShape.color === options.statusColor.active||customShape.color === options.statusColor.now) {
                    shape.shapeNode = paper.circle(customShape.x, customShape.y, draw.radius).attr(draw.attr).attr({ fill: customShape.color});
                    shape.title = paper.text(customShape.x, customShape.y-23,statusName).attr({fill: customShape.color===options.statusColor.unactive?'#45433e':'black','font-size': '14','font-family':'微软雅黑','font-weight':'bold'});
                    /*shape.textContentPerson = paper.text(customShape.x, customShape.y+22,customShape.person).attr({fill: 'black','font-size': '12','font-family':'微软雅黑'});
                    shape.textContentTime = paper.text(customShape.x, customShape.y+45,customShape.time).attr({fill: 'black','font-size': '12','font-family':'微软雅黑'});
                    shape.textContentDept = paper.text(customShape.x, customShape.y+68,customShape.dept).attr({fill: 'black','font-size': '12','font-family':'微软雅黑'});*/
                    
                    // 显示最后一次执行信息，隐藏其他次信息,三个在数组中的位置一样
                    if(textContentPerson.length>0){
                        textContentPerson[textContentPerson.length-1].attr({opacity: 1});
                        textContentTime[textContentTime.length-1].attr({opacity: 1});
                        textContentDept[textContentDept.length-1].attr({opacity: 1});
                    }
                    shape.textContentPerson = textContentPerson;
                    shape.textContentDept = textContentDept;
                    shape.textContentTime = textContentTime;
                    labelContent.pop();labelContent.pop();
                    var maxPersonLength=0;
                    for (var i in labelContent) {
                        if(Number(i)%2==0){
                            if(labelContent[i].length>maxPersonLength){
                                maxPersonLength=labelContent[i].length;
                            }   
                        }
                    };
                    for(var i in labelContent){
                        if(Number(i)%2==0){
                            label.push(paper.text(customShape.x, customShape.y-label.length/2*(label[0]?label[0].getBBox().height+2:0), labelContent[i])).attr({fill: 'black','font-size': '12','font-family':'微软雅黑','text-anchor':'start'});
                            label.push(paper.text(customShape.x+maxPersonLength*12+5, customShape.y-(label.length-1)/2*(label[0]?label[0].getBBox().height+2:0), labelContent[Number(i)+1])).attr({fill: 'black','font-size': '12','font-family':'微软雅黑','text-anchor':'start'});
                            label.hide();
                        }
                    }
                    shape.shapeNode.label = label;
                    shape.shapeNode.statusContent = statusContent;
                    shape.shapeNode.labelNum = paper.text(customShape.x, customShape.y+1,label.length>0?label.length/2+1:"").attr({fill: customShape.color===options.statusColor.now?'black':'white','font-size': '14','font-family':'微软雅黑','font-weight':'bold'});
                    shape.shapeNode.labelNum.data('shapeNode',shape.shapeNode);
                }
                shapes.push(shape);

            }

            // 设置激活的曲线的路径
            var isFirstReportInMicro = false;// 记录微生物模版中第一个报告，此报告默认为涂片出的报告
            for (var i = 0; i < options.statusContents.length; i++) {
                for (var index in options.status) {
                    // 使用用户数据进行标签匹配
                    if(options.status[index].code == options.statusContents[i].code)
                    {
                        // 在微生物类型的报告中，去掉涂片节点和第一次检验报告已审核节点。
                        // begin
                        if(options.statusContents[i].code=='721')
                        {
                            // 将激活的节点编号存入集合中
                            stateActivePathAll.push(Number(index));
                            stateActivePathSmearPart.push(3);
                            stateActivePathSmearPart.push(Number(index));
                            isFirstReportInMicro = true;
                            break;
                        }else if(isFirstReportInMicro && options.statusContents[i].code=='91'){
                            // 将激活的节点编号存入集合中
                            stateActivePathAll.push(Number(index));
                            stateActivePathSmearPart.push(Number(index));
                            isFirstReportInMicro = false;
                            break;
                        }
                        //end
                        else{
                            // 将激活的节点编号存入集合中
                            stateActivePath.push(Number(index));
                            stateActivePathAll.push(Number(index));
                            break;
                        }
                        
                    }
                }                
            }
            // 设置节点间连接线的颜色
            for (var index in options.connections) {
                var conn = options.connections[index];
                options.connections[index].color = options.processLineColor.unactive;
                for (var i = 0; i < stateActivePath.length-1; i++) {
                    if(conn.from == stateActivePath[i] && conn.to == stateActivePath[i+1])
                    {
                        options.connections[index].color = options.processLineColor.active;
                        break;
                    }
                }
                // 如果节点被激活，且它的上一个节点也是激活的，且指向它的所有路径中仅有这个路径的from节点被激活，那么这条路径一定是被激活的。
                if(options.connections[index].color==options.processLineColor.unactive && $.inArray(conn.to, stateActivePathAll)>=0 && $.inArray(conn.from, stateActivePathAll)>=0)
                {
                    // 是否激活该路径标识
                    var connectionActivated = true;
                    for(var innerIndex in options.connections){
                        if(options.connections[innerIndex].to == conn.to && options.connections[innerIndex].from != conn.from && $.inArray(options.connections[innerIndex].from, stateActivePathAll)>=0){
                            connectionActivated = false;
                            break;
                        }
                    }
                    if(connectionActivated)
                    {
                        options.connections[index].color = options.processLineColor.active;
                        animateCorrectConns.push(conn);
                    }
                }
                // 对微生物检验报告的涂片中到检验报告已审核的路径进行特殊处理
                // begin
                if(options.connections[index].color==options.processLineColor.unactive && stateActivePathSmearPart.length>1)
                {
                    for (var i = 0; i < stateActivePathSmearPart.length-1; i++) {
                        if(conn.from == stateActivePathSmearPart[i] && conn.to == stateActivePathSmearPart[i+1])
                        {
                            options.connections[index].color = options.processLineColor.active;
                            break;
                        }
                    }
                }
                //end
            }

            // 通过补正连接集合，修正激活序列中部分错序处
            for (var i = 0; i < animateCorrectConns.length; i++) {
                var conn = animateCorrectConns[i];
                if($.inArray(conn.to, stateActivePath)>=0 && $.inArray(conn.from, stateActivePath)>=0){
                    var insertPointFlag = false;
                    for(var index in stateActivePath){
                        if(index>0 && stateActivePath[index]==conn.to && stateActivePath[index-1]==conn.from){
                            break;
                        }else if(stateActivePath[index]==conn.to){
                            stateActivePath.splice(index,1);
                            insertPointFlag = true;
                            break;
                        }
                    }
                    if(insertPointFlag){
                        for(var index in stateActivePath){
                            if(stateActivePath[index]==conn.from){
                                stateActivePath.splice(index+1,0,conn.to);
                                break;
                            }
                        }
                    }
                }
            };

            // 绘制连接线
            for (var index in options.connections) {
                var conn = options.connections[index];
                if(conn.color === options.processLineColor.active||(options.status[conn.from].special!="true"&&options.status[conn.to].special!="true")){
                    connections.push(paper.drawArr({ obj1: shapes[conn.from].shapeNode, obj2: shapes[conn.to].shapeNode, connection:conn,distance:options.distance,position:options.position}));
                }
            }
            // 将激活的连接线放置在最上层
            for (var index = 0; index < connections.length; index++) {
                var conn = connections[index];
                if(options.processLineColor.active === conn.connection.color)
                {
                    conn.path.toFront();
                    conn.arrows.toFront();
                }
            }
            // 将节点放在最上层
            for (var i = 0; i < shapes.length; i++) {
                if(shapes[i].title){
                    shapes[i].title.toFront();
                    shapes[i].shapeNode.toFront();
                    shapes[i].shapeNode.labelNum.toFront();
                    if(shapes[i].textContentPerson.length > 0){
                        shapes[i].textContentPerson[shapes[i].textContentPerson.length-1].toFront();
                        shapes[i].textContentTime[shapes[i].textContentTime.length-1].toFront();
                        shapes[i].textContentDept[shapes[i].textContentDept.length-1].toFront();
                    }
                }
            }
            // 设置节点tip信息
            var frame;
            var label;
            for (var i = 0; i < shapes.length; i++) {
                if(shapes[i].title&&(shapes[i].shapeNode.label.length>0||shapes[i].shapeNode.statusContent.length>0)){
                	shapes[i].shapeNode.hover(function(){
                		if(this.labelNum)
                			this.labelNum.hide();
                        label.hide();
                        var x = this.attrs.cx + options.draw.radius + 5;
                        var y = this.attrs.cy;
                        var side = "right";
                        if(frame)
                        {
                        	frame.hide();
                            if(x + frame.getBBox().width > options.canvas.width)
                            {
                                side = "left";
                                x = this.attrs.cx - options.draw.radius - 5;
                            }
                        }else if(x+300 > options.canvas.width){
                        	side = "left";
                        }
                        this.animate({r:options.draw.radius + 5},300,"bounce");
                        if(this.statusContent.length > 0){
                            frame = paper.popup(x,y,this.statusContent,side).attr({ fill: this.attrs.gradient.substring(19), stroke: "#283130", "stroke-width": 2, "fill-opacity": .8 });
                            this.statusContent.show();
                            label = this.statusContent;
                        }else{
                            frame = paper.popup(x,y,this.label,side).attr({ fill: this.attrs.gradient.substring(19), stroke: "#283130", "stroke-width": 2, "fill-opacity": .8 });
                            this.label.show();
                            label = this.label;
                        }
                        frame.toFront();
                        label.toFront();
                    },function(){
                        this.animate({r:options.draw.radius},300,"bounce");
                        frame.hide();
                        label.hide();
                        if(this.label.length>0)
                            this.labelNum.show();
                    });

                    shapes[i].shapeNode.labelNum.hover(function(){
                    	this.hide();
                        label.hide();
                    	var x = this.data('shapeNode').attrs.cx + options.draw.radius + 5;
                        var y = this.data('shapeNode').attrs.cy;
                        var side = "right";
                        if(frame)
                        {
                            frame.hide();
                            if(x + frame.getBBox().width > options.canvas.width)
                            {
                                side = "left";
                                x = this.data('shapeNode').attrs.cx - options.draw.radius - 5;
                            }
                        }else if(x+300 > options.canvas.width){
                        	side = "left";
                        }
                        this.data('shapeNode').animate({r:options.draw.radius + 5},300,"bounce");
                        if(this.data('shapeNode').statusContent.length > 0){
                            frame = paper.popup(x,y,this.data('shapeNode').statusContent,side).attr({ fill: this.data('shapeNode').attrs.gradient.substring(19), stroke: "#283130", "stroke-width": 2, "fill-opacity": .8 });
                            this.data('shapeNode').statusContent.show();
                            label = this.data('shapeNode').statusContent;
                        }else{
                            frame = paper.popup(x,y,this.data('shapeNode').label,side).attr({ fill: this.data('shapeNode').attrs.gradient.substring(19), stroke: "#283130", "stroke-width": 2, "fill-opacity": .8 });
                            this.data('shapeNode').label.show();
                            label = this.data('shapeNode').label;
                        }
                        frame.toFront();
                        label.toFront();
                    });
                }
                
            };

            if(options.animateEffect === "on")
            {
                // 动画效果
                //所有信息不可见
                setOpacity(shapes,connections,0);
                // 微生物动画开始显示标识
                var smearAnimateStartFlag = false;

                // 激活节点顺序连线
                for (var index = 0; index < stateActivePath.length*2-1; index++) {
                    (function(index){
                        setTimeout(function(){
                            if(index%2==0)
                            {
                                index=index/2;
                                shapes[stateActivePath[index]].shapeNode.animate({r: options.draw.radius}, 500,"bounce");
                                shapes[stateActivePath[index]].title.animate({opacity: 1}, 500);
                                if(shapes[stateActivePath[index]].hidePerson){
                                    shapes[stateActivePath[index]].hidePerson.hide();
                                    shapes[stateActivePath[index]].hideTime.hide();
                                    shapes[stateActivePath[index]].hideDept.hide();
                                }
                                shapes[stateActivePath[index]].textContentPerson[0].animate({opacity: 1}, 500);
                                shapes[stateActivePath[index]].textContentTime[0].animate({opacity: 1}, 500);
                                shapes[stateActivePath[index]].textContentDept[0].animate({opacity: 1}, 500);
                                if(shapes[stateActivePath[index]].textContentPerson.length>1)
                                {
                                    shapes[stateActivePath[index]].hidePerson = shapes[stateActivePath[index]].textContentPerson.shift();
                                    shapes[stateActivePath[index]].hideTime = shapes[stateActivePath[index]].textContentTime.shift();
                                    shapes[stateActivePath[index]].hideDept = shapes[stateActivePath[index]].textContentDept.shift();
                                }
                                // 针对微生物涂片节点动画特殊处理
                                // begin
                                if(smearAnimateStartFlag && stateActivePathSmearPart.length>0){
                                    // 两个检验报告已审核在同一时刻进行动画
                                    if(stateActivePath[index]==stateActivePathSmearPart[0]){
                                        shapes[stateActivePath[index]].hidePerson.hide();
                                        shapes[stateActivePath[index]].hideTime.hide();
                                        shapes[stateActivePath[index]].hideDept.hide();
                                        shapes[stateActivePath[index]].textContentPerson[0].animate({opacity: 1}, 500);
                                        shapes[stateActivePath[index]].textContentTime[0].animate({opacity: 1}, 500);
                                        shapes[stateActivePath[index]].textContentDept[0].animate({opacity: 1}, 500);
                                        stateActivePathSmearPart.shift();
                                    }else{
                                        shapes[stateActivePathSmearPart[0]].shapeNode.animate({r: options.draw.radius}, 500,"bounce");
                                        shapes[stateActivePathSmearPart[0]].title.animate({opacity: 1}, 500);
                                        shapes[stateActivePathSmearPart[0]].textContentPerson[0].animate({opacity: 1}, 500);
                                        shapes[stateActivePathSmearPart[0]].textContentTime[0].animate({opacity: 1}, 500);
                                        shapes[stateActivePathSmearPart[0]].textContentDept[0].animate({opacity: 1}, 500);
                                        if(shapes[stateActivePathSmearPart[0]].textContentPerson.length>1)
                                        {
                                            shapes[stateActivePathSmearPart[0]].hidePerson = shapes[stateActivePathSmearPart[0]].textContentPerson.shift();
                                            shapes[stateActivePathSmearPart[0]].hideTime = shapes[stateActivePathSmearPart[0]].textContentTime.shift();
                                            shapes[stateActivePathSmearPart[0]].hideDept = shapes[stateActivePathSmearPart[0]].textContentDept.shift();
                                        }
                                        if(stateActivePathSmearPart.length==1){
                                            stateActivePathSmearPart.shift();
                                        }
                                    }
                                }
                                if(stateActivePath[index]==3 && stateActivePathSmearPart.length>0){
                                    smearAnimateStartFlag = true;
                                }
                                //end
                            }else{
                                index=(index-1)/2;
                                for (var i =0 ;i<connections.length ;i++) {
                                    if(connections[i].connection.from == stateActivePath[index]&&connections[i].connection.to == stateActivePath[index+1])
                                    {
                                        connections[i].path.animate({opacity: 1}, 700).toFront();
                                        connections[i].arrows.animate({opacity: 1}, 700).toFront();
                                        if(connections[i].note)
                                            connections[i].note.animate({opacity: 1}, 700).toFront();
                                        break;
                                    }
                                }
                                // 针对微生物涂片节点动画特殊处理
                                // begin
                                if(smearAnimateStartFlag && stateActivePathSmearPart.length>1){
                                    for (var i =0 ;i<connections.length ;i++) {
                                       if(connections[i].connection.from == stateActivePathSmearPart[0]&&connections[i].connection.to == stateActivePathSmearPart[1])
                                       { 
                                            connections[i].path.animate({opacity: 1}, 700).toFront();
                                            connections[i].arrows.animate({opacity: 1}, 700).toFront();
                                            if(connections[i].note)
                                                connections[i].note.animate({opacity: 1}, 700).toFront();
                                            stateActivePathSmearPart.shift();
                                            break;
                                       }
                                    }
                                }
                                // end
                            }
                        },600*index);
                    })(index);
                }

                // 显示所有节点和线路
                setTimeout(function(){
                    //所有信息可见
                    setOpacity(shapes,connections,1,1000);  
                    // 将节点放在最上层
                    for (var i = 0; i < shapes.length; i++) {
                        if(shapes[i].title){
                            shapes[i].shapeNode.toFront();
                            shapes[i].shapeNode.labelNum.toFront();
                        }
                    };                     
                },600*(stateActivePath.length*2-1));
                // 将按钮设置为可用
                setTimeout(function(){
                    opts.controlButton.disabled=false;  
                },600*(stateActivePath.length*2-1)+1200);
                // 设置所有节点和连接线的透明度
                function setOpacity(shapes,connections,opacity,time)
                {
                    if(time)
                    {
                        for(var index = 0; index <shapes.length;index++)
                        {
                            if(shapes[index].title){
                                shapes[index].shapeNode.animate({r: opacity*options.draw.radius},time,"elastic");
                                shapes[index].title.animate({opacity: opacity},time).toFront();
                                if(shapes[index].textContentPerson.length > 0)
                                {
                                    shapes[index].textContentPerson[shapes[index].textContentPerson.length-1].animate({opacity: opacity},time).toFront();
                                    shapes[index].textContentTime[shapes[index].textContentTime.length-1].animate({opacity: opacity},time).toFront();
                                    shapes[index].textContentDept[shapes[index].textContentDept.length-1].animate({opacity: opacity},time).toFront();
                                }
                                shapes[index].shapeNode.labelNum.animate({opacity: opacity},time);
                            }
                        }
                        for(var index = 0; index <connections.length;index++)
                        {
                            connections[index].path.animate({opacity: opacity},time);
                            connections[index].arrows.animate({opacity: opacity},time);
                            if(connections[index].note)
                                connections[index].note.animate({opacity: opacity},time);
                        }
                    }else{
                        for(var index = 0; index <shapes.length;index++)
                        {
                            if(shapes[index].title){
                                shapes[index].shapeNode.attr({r: opacity*options.draw.radius});
                                shapes[index].title.attr({opacity: opacity});
                                if(shapes[index].textContentPerson.length > 0)
                                {
                                    shapes[index].textContentPerson[shapes[index].textContentPerson.length-1].attr({opacity: opacity});
                                    shapes[index].textContentTime[shapes[index].textContentTime.length-1].attr({opacity: opacity});
                                    shapes[index].textContentDept[shapes[index].textContentDept.length-1].attr({opacity: opacity});
                                }
                                shapes[index].shapeNode.labelNum.attr({opacity: opacity});
                            }
                        }
                        for(var index = 0; index <connections.length;index++)
                        {
                            connections[index].path.attr({opacity: opacity});
                            connections[index].arrows.attr({opacity: opacity});
                            if(connections[index].note)
                                connections[index].note.attr({opacity: opacity});
                        }
                    }
                    
                }
            }
        }
            

        this.destroy = function() {
            paper.remove();
        };


        var $holder = $(holder);
        var options = opts;

        var paper = new Raphael(holder, options.canvas.width, options.canvas.height);
        var shapes = [], connections = [];

        return this;
    }
})(jQuery);