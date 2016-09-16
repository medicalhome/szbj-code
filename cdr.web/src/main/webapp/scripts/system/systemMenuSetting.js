function getAllMenus()
{
    var menus = new Array();
    $("#allMenus").find("li").each(function(){
    	menus[menus.length++] = new Menu($(this).attr("code"), $(this).html(), null); 
    });
	return menus;
}

function getDisplayMenus()
{
	var menus = new Array()
	var displayMenus = $("#displayMenus").val();
    if (displayMenus != "") {
    	menus = displayMenus.split(",");
    }
	//alert($("#displayMenus").val());
    return menus;
}

function initMenuTreeViews()
{
	var possibleSource = new Array();
	var chosenSource = new Array();
	var allMenus = getAllMenus();
	
	for(var a_index in allMenus) {
		possibleSource[possibleSource.length] = new Menu(allMenus[a_index].code, allMenus[a_index].name, null, "hide"); 
		chosenSource[chosenSource.length] = new Menu(allMenus[a_index].code, allMenus[a_index].name, null, "hide"); 
	}
	
	var r_possible = new MenuRepository(possibleSource);
	r_possible.setMenuRelations();
	var r_chosen = new MenuRepository(chosenSource);
	r_chosen.setMenuRelations();

	var visibleSource = new Array();
	var displayMenus = getDisplayMenus();
	
	for(var d_index in displayMenus){
		var menu = r_possible.getItem(displayMenus[d_index]);
		if(menu != null)		 
			visibleSource[visibleSource.length] = new Menu(menu.code, menu.name);
	}
	 
	var r_visible = new MenuRepository(visibleSource);

	for(var k_index in allMenus){
		var menu = allMenus[k_index];
		if(r_visible.findItem(menu.code)) {
			r_chosen.setItemStatus(menu.code, "visible");  //只显示它和它的父亲菜单
		}
		else {                                             
			var subMenus = r_possible.getAllSubItems(menu.code).split(",");
			if(subMenus.length == 0)
				r_possible.setItemStatus(menu.code, "visible");
			else {
				for(var y_index in subMenus){
					if(r_visible.findItem(subMenus[y_index]) == false)
						r_possible.setItemStatus(menu.code, "visible");										
				}				
			}
		}						
	}
		
	if(possibleSource.length)
		$("#possible_tip").hide();
	else
		$("#possible_tip").show();
	
	$("#possible").treeView({
		source: possibleSource
	});

	$("#chosen").treeView({
		source: chosenSource
	});	

	if($("#possible").find("li").length)
		$("#possible_tip").hide();
	else
		$("#possible_tip").show();

	if($("#chosen").find("li").length)
		$("#chosen_tip").hide();
	else
		$("#chosen_tip").show();	
}					


function refreshMenuSource(source1, source2) 
{
	var r_source2 = new MenuRepository(source2);
	
	for(var index in source1){
		var menu = source1[index];
		if(menu.status == "visible") {
			r_source2.setItemStatus(menu.code, "hide");
		}
		else {
			r_source2.setItemStatus(menu.code, "visible");
		}	
	}
}

function copyToList(from, to)
{
	if(from == "possible"){
		if($("#possible").find("li").length == 0){
	    	showMsg("提示","可选菜单为空！");
			return false;
		}		
		
		var num = $("#possible").treeView("getSelectedItemCount");
		if(num == 0) {
	    	showMsg("提示","请从可选菜单中选择菜单项目！");
			return false;
		}		
		
		$("#possible").treeView("removeItems");
		var possibleSource = $("#possible").treeView("getSource");
		var chosenSource = $("#chosen").treeView("getSource");
		refreshMenuSource(possibleSource, chosenSource);

		$("#chosen").treeView("refresh", {
			source: chosenSource
		});	
	}
	else {
		if($("#chosen").find("li").length == 0){
	    	showMsg("提示","已选菜单为空！");
			return false;
		}		

		var num = $("#chosen").treeView("getSelectedItemCount");
		if(num == 0) {
	    	showMsg("提示","请从已选菜单中选择菜单项目！");
			return false;
		}		

		$("#chosen").treeView("removeItems");
		var possibleSource = $("#possible").treeView("getSource");
		var chosenSource = $("#chosen").treeView("getSource");
		refreshMenuSource(chosenSource, possibleSource);

		$("#possible").treeView("refresh", {
			source: possibleSource
		});			
	}	
	if($("#possible").find("li").length)
		$("#possible_tip").hide();
	else
		$("#possible_tip").show();

	if($("#chosen").find("li").length)
		$("#chosen_tip").hide();
	else
		$("#chosen_tip").show();	
}

function copyAll(from, to)
{
	if(from == "possible"){
		if($("#possible").find("li").length == 0){
	    	showMsg("提示","可选菜单为空！");
			return false;
		}		
		
		$("#possible").treeView("removeAllItems");
		var possibleSource = $("#possible").treeView("getSource");
		var chosenSource = $("#chosen").treeView("getSource");
		refreshMenuSource(possibleSource, chosenSource);

		$("#chosen").treeView("refresh", {
			source: chosenSource
		});	
	}
	else {
		if($("#chosen").find("li").length == 0){
	    	showMsg("提示","已选菜单为空！");
			return false;
		}		

		$("#chosen").treeView("removeAllItems");
		var possibleSource = $("#possible").treeView("getSource");
		var chosenSource = $("#chosen").treeView("getSource");
		refreshMenuSource(chosenSource, possibleSource);

		$("#possible").treeView("refresh", {
			source: possibleSource
		});			
	}	
	if($("#possible").find("li").length)
		$("#possible_tip").hide();
	else
		$("#possible_tip").show();

	if($("#chosen").find("li").length)
		$("#chosen_tip").hide();
	else
		$("#chosen_tip").show();	
}
