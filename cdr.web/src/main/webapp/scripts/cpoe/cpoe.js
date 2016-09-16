//
var outLabMicrobeStatus=[
                      { name: "医嘱已下达", position: { x: 0, y: 0}, code: "100.001" },
                      { name: "已收费", position: { x: 1, y: 0}, code: "110.003" },
                      { name: "标本已采集", position: { x: 2, y: 0}, code: "120.001" },
                      { name: "标本已签收", position: { x: 3, y: 0}, code: "140.001" },
                      { name: "已退检", position: { x: 2, y: -1}, code: "160.008" ,special:"true"},
                      { name: "培养中", position: { x: 4, y: 0}, code: "161.001" },
                      { name: "鉴定中", position: { x: 5, y: 0}, code: "161.003" },
                      { name: "药敏中", position: { x: 6, y: 0}, code: "161.004" },
                      { name: "涂片中", position: { x: 4.5, y: 0.9}, code: "161.002" },
                      { name: "检验报告已审核", position: { x: 7, y: 0}, code: "170.002" },
                      { name: "报告召回", position: { x: 8, y: 0}, code: "990.001" ,special:"true"},
                      { name: "检验已取消", position: { x: 2, y: 1.2}, code: "160.009" ,special:"true"},
                      { name: "医嘱已取消", position: { x: 1, y: 1.2}, code: "990.002" ,special:"true"}
                     ];
	
var outLabMicrobeConns=[
                           { from: 0, to: 1 },
                           { from: 1, to: 2 },
                           { from: 2, to: 3 },
                           { from: 9, to: 10 },
                           { from: 3, to: 4 },
                           { from: 4, to: 2 },
                           { from: 10, to: 9 , type1:"top", type2:"top", path:["L",8,-0.3,"A",24,24,0,0,0,7.8,-0.5,"L",7.2,-0.5,"A",24,24,0,0,0,7,-0.3] ,complex:"true"},
                           { from: 1, to: 12 , note:{x:1.1,y:0.8,content:"退\n费"}},
                           { from: 2, to: 11 },
                           { from: 3, to: 11 , type1:"bottom", type2:"right", path:["L",3,0.8,"A",48,48,0,0,1,2.6,1.2],complex:"true" },
                           { from: 3, to: 5 },
                           { from: 5, to: 6 },
                           { from: 6, to: 7 },
                           { from: 9, to: 11 , type1:"bottom", type2:"right", path:["L",7,0.8,"A",48,48,0,0,1,6.6,1.2],complex:"true" },
                           { from: 5, to: 11  , type1:"bottom", type2:"right", path:["L",4,0.8,"A",48,48,0,0,1,3.6,1.2],complex:"true" },
                           { from: 6, to: 11  , type1:"bottom", type2:"right", path:["L",5,0.8,"A",48,48,0,0,1,4.6,1.2],complex:"true" },
                           { from: 7, to: 11  , type1:"bottom", type2:"right", path:["L",6,0.8,"A",48,48,0,0,1,5.6,1.2],complex:"true" },
                           { from: 8, to: 11  , type1:"bottom", type2:"right", path:["A",48,48,0,0,1,4.1,1.2],complex:"true" },
                           { from: 11, to: 12 , note:{x:1.5,y:1.1,content:"退费"} },
                           { from: 5, to: 9  , type1:"top", type2:"top", path:["L",4,-0.3,"A",24,24,0,0,1,4.2,-0.5,"L",6.8,-0.5,"A",24,24,0,0,1,7,-0.3],complex:"true" },
                           { from: 7, to: 9 },
                           { from: 8, to: 9  , type1:"right", type2:"bottom", path:["L",6.8,0.9,"A",24,24,0,0,0,7,0.7],complex:"true" },
                           { from: 3, to: 8  , type1:"bottom", type2:"left", path:["L",3,0.7,"A",24,24,0,0,0,3.2,0.9],complex:"true" },
                           { from: 3, to: 9  , type1:"top", type2:"top", path:["L",3,-0.3,"A",24,24,0,0,1,3.2,-0.5,"L",6.8,-0.5,"A",24,24,0,0,1,7,-0.3],complex:"true" }
                               ];


var outLabNormalStatus=[
                        { name: "医嘱已下达", position: { x: 0, y: 0}, code: "100.001" },
                        { name: "已收费", position: { x: 1, y: 0}, code: "110.003" },
                        { name: "标本已采集", position: { x: 2, y: 0}, code: "120.001" },
                        { name: "标本已签收", position: { x: 3, y: 0}, code: "140.001" },
                        { name: "已退检", position: { x: 2, y: -1}, code: "160.008" ,special:"true"},
                        { name: "检验报告已审核", position: { x: 4, y: 0}, code: "170.002" },
                        { name: "报告召回", position: { x: 5, y: 0}, code: "990.001" ,special:"true"},
                        { name: "检验已取消", position: { x: 2, y: 1.4}, code: "160.009" ,special:"true"},
                        { name: "医嘱已取消", position: { x: 1, y: 1.4}, code: "990.002" ,special:"true"}
                       ];

var outLabNormalConns=[
                          { from: 0, to: 1 },
                          { from: 1, to: 2 },
                          { from: 2, to: 3 },
                          { from: 3, to: 5 },
                          { from: 5, to: 6 },
                          { from: 3, to: 4 },
                          { from: 4, to: 2 },
                          { from: 6, to: 5 , type1:"top", type2:"top", path:["L",5,-0.3,"A",24,24,0,0,0,4.8,-0.5,"L",4.2,-0.5,"A",24,24,0,0,0,4,-0.3] ,complex:"true"},
                          { from: 1, to: 8 , note:{x:1.1,y:0.9,content:"退\n费"}},
                          { from: 2, to: 7 },
                          { from: 7, to: 8 , note:{x:1.5,y:1.3,content:"退费"} },
                          { from: 3, to: 7 , type1:"bottom", type2:"right", path:["L",3,1,"A",48,48,0,0,1,2.6,1.4] ,complex:"true"},
                          { from: 5, to: 7 , type1:"bottom", type2:"right", path:["L",4,1,"A",48,48,0,0,1,3.6,1.4] ,complex:"true"}
                          
                              ];

var inLabNormalStatus=[
                       { name: "医嘱已下达", position: { x: 0, y: 0}, code: "100.001" }, //0
                       { name: "医嘱已确认", position: { x: 1, y: 0}, code: "110.001" }, //1
                       { name: "标本已采集", position: { x: 2, y: 0}, code: "120.001" }, //2
                       { name: "标本已签收", position: { x: 3, y: 0}, code: "140.001" }, //3
                       { name: "已退检", position: { x: 2, y: -1}, code: "160.008" ,special:"true"}, //4
                       { name: "检验报告已审核", position: { x: 4, y: 0}, code: "170.002" }, //5
                       { name: "报告召回", position: { x: 5, y: 0}, code: "990.001" ,special:"true"}, //6
                       { name: "检验已取消", position: { x: 2, y: 1.4}, code: "160.009" ,special:"true"}, //7
                       { name: "医嘱已取消", position: { x: 1, y: 1.4}, code: "990.002" ,special:"true"}
                      ];

var inLabNormalConns=[
        { from: 0, to: 1 },
        { from: 1, to: 2 },
        { from: 2, to: 3 },
        { from: 3, to: 5 },
        { from: 5, to: 6 },
        { from: 3, to: 4 },
        { from: 4, to: 2 },
        { from: 6, to: 5 , type1:"top", type2:"top", path:["L",5,-0.3,"A",24,24,0,0,0,4.8,-0.5,"L",4.2,-0.5,"A",24,24,0,0,0,4,-0.3] ,complex:"true"},
        { from: 1, to: 8 },
        { from: 2, to: 7 },
        { from: 7, to: 8 },
        { from: 3, to: 7 , type1:"bottom", type2:"right", path:["L",3,1,"A",48,48,0,0,1,2.6,1.4] ,complex:"true"},
        { from: 5, to: 7 , type1:"bottom", type2:"right", path:["L",4,1,"A",48,48,0,0,1,3.6,1.4] ,complex:"true"}
            ];
/*
var inLabNormalConns=[
                      { from: 0, to: 1 },
                      { from: 1, to: 2 },
                      { from: 2, to: 3 },
                      { from: 3, to: 5 },
                      { from: 5, to: 6 },
                      { from: 3, to: 4 },
                      { from: 4, to: 2 },
                      { from: 6, to: 5 , type1:"top", type2:"top", path:["L",5,-0.3,"A",24,24,0,0,0,4.8,-0.5,"L",4.2,-0.5,"A",24,24,0,0,0,4,-0.3] ,complex:"true"},
                      { from: 1, to: 8 },
                      { from: 2, to: 7 },
                      { from: 7, to: 8 },
                      { from: 3, to: 7 , type1:"bottom", type2:"right", path:["L",3,1,"A",48,48,0,0,1,2.6,1.4] ,complex:"true"},
                      { from: 5, to: 7 , type1:"bottom", type2:"right", path:["L",4,1,"A",48,48,0,0,1,3.6,1.4] ,complex:"true"},
                      { from: 5, to: 9 },
                      { from: 9, to: 10 },
                      { from: 9, to: 11 }
                          ];*/


var inLabMicrobeStatus=[
                        { name: "医嘱已下达", position: { x: 0, y: 0}, code: "100.001" },
                        { name: "医嘱已确认", position: { x: 1, y: 0}, code: "110.001" },
                        { name: "标本已采集", position: { x: 2, y: 0}, code: "120.001" },
                        { name: "标本已签收", position: { x: 3, y: 0}, code: "140.001" },
                        { name: "已退检", position: { x: 2, y: -1}, code: "160.008" ,special:"true"},
                        { name: "培养中", position: { x: 4, y: 0}, code: "161.001" },
                        { name: "鉴定中", position: { x: 5, y: 0}, code: "161.003" },
                        { name: "药敏中", position: { x: 6, y: 0}, code: "161.004" },
                        { name: "涂片中", position: { x: 4.5, y: 0.9}, code: "161.002" },
                        { name: "检验报告已审核", position: { x: 7, y: 0}, code: "170.002" },
                        { name: "报告召回", position: { x: 8, y: 0}, code: "990.001" ,special:"true"},
                        { name: "检验已取消", position: { x: 2, y: 1.2}, code: "160.009" ,special:"true"},
                        { name: "医嘱已取消", position: { x: 1, y: 1.2}, code: "990.002" ,special:"true"}
                       ];

var inLabMicrobeConns=[
                       { from: 0, to: 1 },
                       { from: 1, to: 2 },
                       { from: 2, to: 3 },
                       { from: 9, to: 10 },
                       { from: 3, to: 4 },
                       { from: 4, to: 2 },
                       { from: 10, to: 9 , type1:"top", type2:"top", path:["L",8,-0.3,"A",24,24,0,0,0,7.8,-0.5,"L",7.2,-0.5,"A",24,24,0,0,0,7,-0.3] ,complex:"true"},
                       { from: 1, to: 12 },
                       { from: 2, to: 11 },
                       { from: 3, to: 11 , type1:"bottom", type2:"right", path:["L",3,0.8,"A",48,48,0,0,1,2.6,1.2],complex:"true" },
                       { from: 3, to: 5 },
                       { from: 5, to: 6 },
                       { from: 6, to: 7 },
                       { from: 9, to: 11 , type1:"bottom", type2:"right", path:["L",7,0.8,"A",48,48,0,0,1,6.6,1.2],complex:"true" },
                       { from: 5, to: 11  , type1:"bottom", type2:"right", path:["L",4,0.8,"A",48,48,0,0,1,3.6,1.2],complex:"true" },
                       { from: 6, to: 11  , type1:"bottom", type2:"right", path:["L",5,0.8,"A",48,48,0,0,1,4.6,1.2],complex:"true" },
                       { from: 7, to: 11  , type1:"bottom", type2:"right", path:["L",6,0.8,"A",48,48,0,0,1,5.6,1.2],complex:"true" },
                       { from: 8, to: 11  , type1:"bottom", type2:"right", path:["A",48,48,0,0,1,4.1,1.2],complex:"true" },
                       { from: 11, to: 12 },
                       { from: 5, to: 9  , type1:"top", type2:"top", path:["L",4,-0.3,"A",24,24,0,0,1,4.2,-0.5,"L",6.8,-0.5,"A",24,24,0,0,1,7,-0.3],complex:"true" },
                       { from: 7, to: 9 },
                       { from: 8, to: 9  , type1:"right", type2:"bottom", path:["L",6.8,0.9,"A",24,24,0,0,0,7,0.7],complex:"true" },
                       { from: 3, to: 8  , type1:"bottom", type2:"left", path:["L",3,0.7,"A",24,24,0,0,0,3.2,0.9],complex:"true" },
                       { from: 3, to: 9  , type1:"top", type2:"top", path:["L",3,-0.3,"A",24,24,0,0,1,3.2,-0.5,"L",6.8,-0.5,"A",24,24,0,0,1,7,-0.3],complex:"true" }
                           ];



var inExamStatus=[
                  { name: "医嘱已下达", position: { x: 0, y: 0}, code: "100.001" },
                  { name: "医嘱已确认", position: { x: 1, y: 0}, code: "110.001" },
                  { name: "检查已到检", position: { x: 2, y: 0}, code: "140.002" },
                  { name: "检查已完成", position: { x: 3, y: 0}, code: "160.003" },
                  { name: "检查报告已审核", position: { x: 4, y: 0}, code: "170.003" },
                  { name: "报告召回", position: { x: 5, y: 0}, code: "990.001" ,special:"true"},
                  { name: "检查已预约", position: { x: 1, y: -1}, code: "130.001" },
                  { name: "检查已取消", position: { x: 2, y: 1.2}, code: "160.009" ,special:"true"},
                  { name: "医嘱已取消", position: { x: 1, y: 1.2}, code: "990.002" ,special:"true"},
                  { name: "取消预约", position: { x: 2, y: -1}, code: "990.004" ,special:"true"}
                 ];
var inExamConns=[
                 { from: 0, to: 1 },
                 { from: 1, to: 2 },
                 { from: 2, to: 3 },
                 { from: 3, to: 4 },
                 { from: 4, to: 5 },
                 { from: 1, to: 6 },
                 { from: 6, to: 2 },
                 { from: 1, to: 8 , note:{x:1.1,y:0.8,content:"退\n费"}},
                 { from: 2, to: 7 },
                 { from: 7, to: 8 , note:{x:1.5,y:1.1,content:"退费"}},
                 { from: 3, to: 7 , type1:"bottom", type2:"right", path:["L",3,0.8,"A",48,48,0,0,1,2.6,1.2] ,complex:"true"},
                 { from: 4, to: 7 , type1:"bottom", type2:"right", path:["L",4,0.8,"A",48,48,0,0,1,3.6,1.2] ,complex:"true"},
                 { from: 5, to: 4 , type1:"top", type2:"top", path:["L",5,-0.3,"A",24,24,0,0,0,4.8,-0.5,"L",4.2,-0.5,"A",24,24,0,0,0,4,-0.3] ,complex:"true"},
                 { from: 6, to: 9 },
                 { from: 9, to: 6 , type1:"top", type2:"top", path:["L",2,-1.3,"A",24,24,0,0,0,1.8,-1.5,"L",1.2,-1.5,"A",24,24,0,0,0,1,-1.3] ,complex:"true"}
                     ];
var outExamStatus=[
                   { name: "医嘱已下达", position: { x: 0, y: 0}, code: "100.001" },
                   { name: "已收费", position: { x: 1, y: 0}, code: "110.003" },
                   { name: "检查已到检", position: { x: 2, y: 0}, code: "140.002" },
                   { name: "检查已完成", position: { x: 3, y: 0}, code: "160.003" },
                   { name: "检查报告已审核", position: { x: 4, y: 0}, code: "170.003" },
                   { name: "报告召回", position: { x: 5, y: 0}, code: "990.001" ,special:"true"},
                   { name: "检查已预约", position: { x: 1, y: -1}, code: "130.001" },
                   { name: "检查已取消", position: { x: 2, y: 1.2}, code: "160.009" ,special:"true"},
                   { name: "医嘱已取消", position: { x: 1, y: 1.2}, code: "990.002" ,special:"true"},
                   { name: "取消预约", position: { x: 2, y: -1}, code: "990.004" ,special:"true"}
                  ];
var outExamConns=[
                  { from: 0, to: 1 },
                  { from: 1, to: 2 },
                  { from: 2, to: 3 },
                  { from: 3, to: 4 },
                  { from: 4, to: 5 },
                  { from: 1, to: 6 },
                  { from: 6, to: 2 },
                  { from: 1, to: 8 , note:{x:1.1,y:0.8,content:"退\n费"}},
                  { from: 2, to: 7 },
                  { from: 7, to: 8 , note:{x:1.5,y:1.1,content:"退费"}},
                  { from: 3, to: 7 , type1:"bottom", type2:"right", path:["L",3,0.8,"A",48,48,0,0,1,2.6,1.2] ,complex:"true"},
                  { from: 4, to: 7 , type1:"bottom", type2:"right", path:["L",4,0.8,"A",48,48,0,0,1,3.6,1.2] ,complex:"true"},
                  { from: 5, to: 4 , type1:"top", type2:"top", path:["L",5,-0.3,"A",24,24,0,0,0,4.8,-0.5,"L",4.2,-0.5,"A",24,24,0,0,0,4,-0.3] ,complex:"true"},
                  { from: 6, to: 9 },
                  { from: 9, to: 6 , type1:"top", type2:"top", path:["L",2,-1.3,"A",24,24,0,0,0,1.8,-1.5,"L",1.2,-1.5,"A",24,24,0,0,0,1,-1.3] ,complex:"true"}
                      ];
function getLabCPOEPattern(orderStepDtos,patientDomain,orderTypeMinCode,outpatientDomain,inpatientDomain,orderTypeMinLabMicro)
{
	var labCPOEPattern={};
	labCPOEPattern.statusContents = orderStepDtos.length>0?orderStepDtos:[];
	if(outpatientDomain==patientDomain&&orderTypeMinLabMicro==orderTypeMinCode)
    {
		labCPOEPattern.status = outLabMicrobeStatus;
		labCPOEPattern.connections = outLabMicrobeConns;
		labCPOEPattern.canvas = {width:1120,height:445};
		labCPOEPattern.position = {x:60,y:185};
    }
    else if(outpatientDomain==patientDomain)
    {
    	labCPOEPattern.status = outLabNormalStatus;
    	labCPOEPattern.connections = outLabNormalConns;
    	labCPOEPattern.canvas = {width:820,height:440};
    	labCPOEPattern.position = {x:60,y:150};
    }
    else if(inpatientDomain==patientDomain&&orderTypeMinLabMicro==orderTypeMinCode)
    {
    	labCPOEPattern.status = inLabMicrobeStatus;
    	labCPOEPattern.connections = inLabMicrobeConns;
    	labCPOEPattern.canvas = {width:1120,height:445};
    	labCPOEPattern.position = {x:60,y:185};
    }
    else if(inpatientDomain==patientDomain)
    {
    	labCPOEPattern.status = inLabNormalStatus;
    	labCPOEPattern.connections = inLabNormalConns;
    	labCPOEPattern.canvas = {width:820,height:440};
    	labCPOEPattern.position = {x:60,y:150};
    }
	return labCPOEPattern;
}


function getExamCPOEPattern(orderStepDtos,patientDomain,outpatientDomain,inpatientDomain)
{
	var examCPOEPattern={};
	examCPOEPattern.statusContents = orderStepDtos.length>0?orderStepDtos:[];
	if(outpatientDomain==patientDomain)
    {
		examCPOEPattern.status = outExamStatus;
		examCPOEPattern.connections = outExamConns;
    }
    else if(inpatientDomain==patientDomain)
    {
    	examCPOEPattern.status = inExamStatus;
    	examCPOEPattern.connections = inExamConns;
    }
	examCPOEPattern.canvas = {width:720,height:445};
	examCPOEPattern.position = {x:60,y:185};
	return examCPOEPattern;
}

function cpoeTabs(tab_id,canvas_id,animateButton_id,CPOEPattern,cpoeTab,isWithdrawReport)
{
	//$(canvas_id).parent().css({"position":"relative","overflow-y":"hidden","overflow-x":"auto","height":"98%","width":"98%"});
	var height;
	if($(".ui-xlgwr>span#ext").length>0){
		height = $(".ui-xlgwr>span#ext").hasClass("ui-icon-zoomout")?"500px":"440px";
	}else{
		height = "500px";
	}
	$(canvas_id).parent().css({"position":"relative","overflow-y":"auto","overflow-x":"auto","height":height,"width":"98%"});
	$(canvas_id).css("position","absolute");
	if(isWithdrawReport){
		$(tab_id).tabs();
		$(canvas_id).width(CPOEPattern.canvas.width);
		$(canvas_id).height(CPOEPattern.canvas.height);
		$(canvas_id).cpoe($.extend({animateEffect:"off"}, CPOEPattern));		
	}else{
		$(tab_id).tabs({
			show: function(event, ui) {
				if(ui.panel.id === cpoeTab)
				{
					$(canvas_id).width(CPOEPattern.canvas.width);
					$(canvas_id).height(CPOEPattern.canvas.height);
					$(canvas_id).cpoe($.extend({animateEffect:"off"}, CPOEPattern));
					
				}
			}
		}); 		
	}
    $(animateButton_id).click(function (event){
    	   stopBubble(event);
 	   	   this.disabled=true;
 	   	$(canvas_id).cpoe('destroy');
 	    $(canvas_id).cpoe($.extend({animateEffect:"on",controlButton:this}, CPOEPattern));	
    }); 
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//以下为港大部分

var gd_outLabStatus=[
/* 00 */{ name: "医嘱已下达", position: { x: 0, y: 0}, code: "100.001" }, 
/* 01 */{ name: "已收费", position: { x: 1, y: 0}, code: "110.003" },
/* 02 */{ name: "下载条码", position: { x: 2, y: 0}, code: "180.001" },
/* 03 */{ name: "标本已采集", position: { x: 3, y: 0}, code: "120.001" },
/* 04 */{ name: "标本已签收", position: { x: 4, y: 0}, code: "140.001" },
/* 05 */{ name: "检验已完成", position: { x: 5, y: 0}, code: "160.002" },
/* 06 */{ name: "检验报告已审核", position: { x: 6, y: 0}, code: "170.002" },
/* 07 */{ name: "检验已取消", position: { x: 2, y: 1.2}, code: "160.009" ,special:"true"},
/* 08 */{ name: "已退费", position: { x: 1, y: 1.2}, code: "110.004" ,special:"true"},
/* 09 */{ name: "医嘱已取消", position: { x: 0, y: 1.2}, code: "990.002" ,special:"true"},
                        ];
var gd_outLabConns=[
                        { from: 0, to: 1 },
                        { from: 1, to: 2 },
                        { from: 2, to: 3 },
                        { from: 3, to: 4 },
                        { from: 4, to: 5 },
                        { from: 5, to: 6 },
                        { from: 0, to: 9 },
                        { from: 1, to: 8 }
                        ];
// 闭环中包含取消检验执行
var gd_outLabConns_cancelLab=[
                        { from: 0, to: 1 },
                        { from: 1, to: 2 },
                        { from: 2, to: 3 },
                        { from: 3, to: 4 },
                        { from: 4, to: 5 },
                        { from: 5, to: 6 },
                        { from: 2, to: 7 },
                        { from: 3, to: 7 },
                        { from: 4, to: 7 },
                        { from: 5, to: 7 },
                        { from: 6, to: 7 },
                        { from: 7, to: 8 }
                        ];

var gd_inLabStatus=[
/* 00 */{ name: "医嘱已下达", position: { x: 0, y: 0}, code: "100.001" }, 
/* 01 */{ name: "医嘱已确认", position: { x: 1, y: 0}, code: "110.001" },
/* 02 */{ name: "下载条码", position: { x: 2, y: 0}, code: "180.001" },
/* 03 */{ name: "标本已采集", position: { x: 3, y: 0}, code: "120.001" },
/* 04 */{ name: "标本已签收", position: { x: 4, y: 0}, code: "140.001" },
/* 05 */{ name: "检验已完成", position: { x: 5, y: 0}, code: "160.002" },
/* 06 */{ name: "检验报告已审核", position: { x: 6, y: 0}, code: "170.002" },
/* 07 */{ name: "检验已取消", position: { x: 2, y: 1.2}, code: "160.009" ,special:"true"},
/* 08 */{ name: "医嘱已取消", position: { x: 0, y: 1.2}, code: "990.002" ,special:"true"}
                        ];

var gd_inLabConns=[
                        { from: 0, to: 1 },
                        { from: 1, to: 2 },
                        { from: 2, to: 3 },
                        { from: 3, to: 4 },
                        { from: 4, to: 5 },
                        { from: 5, to: 6 },
                        { from: 1, to: 7 },
                        { from: 2, to: 7 },
                        { from: 3, to: 7 },
                        { from: 4, to: 7 },
                        { from: 5, to: 7 },
                        { from: 6, to: 7 },
                        { from: 0, to: 8 },
                        { from: 7, to: 8 }
                        ];

function gd_getLabCPOEPattern(orderStepDtos,visitType,outpatientVisitType,inpatientVisitType,cancelLab){
	var labCPOEPattern={};
	labCPOEPattern.statusContents = orderStepDtos.length>0 ? orderStepDtos : [];
	labCPOEPattern.canvas = {width:1120,height:445};
	labCPOEPattern.position = {x:60,y:185};
	if(visitType == outpatientVisitType){
    	labCPOEPattern.status = gd_outLabStatus;
    	if(cancelLab){
    		labCPOEPattern.connections = gd_outLabConns_cancelLab;
    	} else {
    		labCPOEPattern.connections = gd_outLabConns;
    	}
    	
	} else {
		labCPOEPattern.status = gd_inLabStatus;
    	labCPOEPattern.connections = gd_inLabConns;
	}
	return labCPOEPattern;
}

// 超声放射内镜
var gd_outExamStatus=[
/* 00 */{ name: "医嘱已下达", position: { x: 0, y: 0}, code: "100.001" },
/* 01 */{ name: "已收费", position: { x: 1, y: 0}, code: "110.003" },
/* 02 */{ name: "检查已到检", position: { x: 2, y: 0}, code: "140.002" },
/* 03 */{ name: "检查已完成", position: { x: 3, y: 0}, code: "160.003" },
/* 04 */{ name: "写报告", position: { x: 4, y: 0}, code: "180.003" },
/* 05 */{ name: "检查报告已审核", position: { x: 5, y: 0}, code: "170.003" },
/* 06 */{ name: "检查已取消", position: { x: 2, y: 1.2}, code: "160.009" ,special:"true"},
/* 07 */{ name: "已退费", position: { x: 1, y: 1.2}, code: "110.004" ,special:"true"},
/* 08 */{ name: "医嘱已取消", position: { x: 0, y: 1.2}, code: "990.002" ,special:"true"}
                        ];
var gd_outExamConns=[
                        { from: 0, to: 1 },
                        { from: 1, to: 2 },
                        { from: 2, to: 3 },
                        { from: 3, to: 4 },
                        { from: 4, to: 5 },
                        { from: 1, to: 7 },
                        { from: 0, to: 8 }
                        ];
// 有取消检查执行，已收费到已退费的路径取消
var gd_outExamConns_cancelExam=[
	{ from: 0, to: 1 },
	{ from: 1, to: 2 },
	{ from: 2, to: 3 },
	{ from: 3, to: 4 },
	{ from: 4, to: 5 },
	{ from: 2, to: 6 },
	{ from: 3, to: 6 },
	{ from: 4, to: 6 },
	{ from: 5, to: 6 },
	{ from: 6, to: 7 }
                        ];
// 心电、病理
var gd_outExamStatus_ECG_PATHOLOGY=[
/* 00 */{ name: "医嘱已下达", position: { x: 0, y: 0}, code: "100.001" },
/* 01 */{ name: "已收费", position: { x: 1, y: 0}, code: "110.003" },
/* 02 */{ name: "检查已完成", position: { x: 2, y: 0}, code: "160.003" },
/* 03 */{ name: "检查报告已审核", position: { x: 3, y: 0}, code: "170.003" },
/* 04 */{ name: "检查已取消", position: { x: 2, y: 1.2}, code: "160.009" ,special:"true"},
/* 05 */{ name: "已退费", position: { x: 1, y: 1.2}, code: "110.004" ,special:"true"},
/* 06 */{ name: "医嘱已取消", position: { x: 0, y: 1.2}, code: "990.002" ,special:"true"}
                        ];

var gd_outExamConns_ECG_PATHOLOGY=[
	{ from: 0, to: 1 },
	{ from: 1, to: 2 },
	{ from: 2, to: 3 },
	{ from: 2, to: 4 },
	{ from: 3, to: 4 },
	{ from: 4, to: 5 },
	{ from: 0, to: 6 }
                     ];
// 有取消检查执行，已收费到已退费的路径取消
var gd_outExamConns_ECG_PATHOLOGY_cancelExam=[
                                   { from: 0, to: 1 },
                                   { from: 1, to: 2 },
                                   { from: 2, to: 3 },
                                   { from: 2, to: 4 },
                                   { from: 3, to: 4 },
                                   { from: 4, to: 5 },
                                   { from: 0, to: 6 }
                                   ];

//超声放射内镜
var gd_inExamStatus=[
/* 00 */{ name: "医嘱已下达", position: { x: 0, y: 0}, code: "100.001" },
/* 01 */{ name: "医嘱已确认", position: { x: 1, y: 0}, code: "110.001" },
/* 02 */{ name: "检查已到检", position: { x: 2, y: 0}, code: "140.002" },
/* 03 */{ name: "检查已完成", position: { x: 3, y: 0}, code: "160.003" },
/* 04 */{ name: "写报告", position: { x: 4, y: 0}, code: "180.003" },
/* 05 */{ name: "检查报告已审核", position: { x: 5, y: 0}, code: "170.003" },
/* 06 */{ name: "检查已取消", position: { x: 2, y: 1.2}, code: "160.009" ,special:"true"},
/* 07 */{ name: "医嘱已取消", position: { x: 0, y: 1.2}, code: "990.002" ,special:"true"}
                        ];

var gd_inExamConns=[
	{ from: 0, to: 1 },
	{ from: 1, to: 2 },
	{ from: 2, to: 3 },
	{ from: 3, to: 4 },
	{ from: 4, to: 5 },
	{ from: 1, to: 6 },
	{ from: 2, to: 6 },
	{ from: 3, to: 6 },
	{ from: 4, to: 6 },
	{ from: 5, to: 6 },
	{ from: 0, to: 7 },
	{ from: 6, to: 7 }
	];


var gd_inExamStatus_ECG_PATHOLOGY=[
/* 00 */{ name: "医嘱已下达", position: { x: 0, y: 0}, code: "100.001" },
/* 01 */{ name: "医嘱已确认", position: { x: 1, y: 0}, code: "110.001" },
/* 02 */{ name: "检查已完成", position: { x: 2, y: 0}, code: "160.003" },
/* 03 */{ name: "检查报告已审核", position: { x: 3, y: 0}, code: "170.003" },
/* 04 */{ name: "检查已取消", position: { x: 1, y: 1.2}, code: "160.009" ,special:"true"},
/* 05 */{ name: "医嘱已取消", position: { x: 0, y: 1.2}, code: "990.002" ,special:"true"}
                        ];

var gd_inExamConns_ECG_PATHOLOGY=[
	{ from: 0, to: 1 },
	{ from: 1, to: 2 },
	{ from: 2, to: 3 },
	{ from: 1, to: 4 },
	{ from: 2, to: 4 },
	{ from: 3, to: 4 },
	{ from: 0, to: 5 },
	{ from: 4, to: 5 }
	];

// ultrasonic超声,eradiation放射,endoscope内镜
function gd_getExamCPOEPattern(orderStepDtos,visitType,outpatientVisitType,inpatientVisitType,ecgOrPathology,contiansCancelExecute)
{
	var examCPOEPattern={};
	examCPOEPattern.statusContents = orderStepDtos.length>0?orderStepDtos:[];
	// 门诊
	if(visitType == outpatientVisitType){
		// 超声放射内镜
		if(!ecgOrPathology){			
			examCPOEPattern.status = gd_outExamStatus;
			if(contiansCancelExecute){
				examCPOEPattern.connections = gd_outExamConns_cancelExam;
			} else {
				examCPOEPattern.connections = gd_outExamConns;
			}		
		} else {
			// 心电病理
			examCPOEPattern.status = gd_outExamStatus_ECG_PATHOLOGY;
			if(contiansCancelExecute){
				examCPOEPattern.connections = gd_outExamConns_ECG_PATHOLOGY_cancelExam;
			} else {
				examCPOEPattern.connections = gd_outExamConns_ECG_PATHOLOGY;
			}
		}
	} else { // 住院
		// 超声放射内镜
		if(!ecgOrPathology){	
			examCPOEPattern.status = gd_inExamStatus;
			examCPOEPattern.connections = gd_inExamConns;
		} else {//心电病理
			examCPOEPattern.status = gd_inExamStatus_ECG_PATHOLOGY;
			examCPOEPattern.connections = gd_inExamConns_ECG_PATHOLOGY;
		}
	}
	
	examCPOEPattern.canvas = {width:1120,height:445};
	examCPOEPattern.position = {x:60,y:185};
	return examCPOEPattern;
}

