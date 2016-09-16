function TreeViewItem () {
    this.isParentOf = function(item) {
        return this == item.parent;
    };

    this.isSubOf = function(item) {
        return this.parent == item;
    };

    this.getLevel = function() {
        return this.code.length / 2;
    };

    this.isFirstLevel = function() {
        if (this.getLevel() == 1) return true;
        else return false;
    };
}

/**
 * TreeView Repository
 */

function TreeViewRepository(source) {	

    this.source = source;

    //this.setMenuRelations();

    this.getItem = function(code) {
        var target = null;

        for (var index in this.source) {
            var item = this.source[index];
            if (item.code == code) {
                target = item;
                break;
            }
        }

        return target;
    };

    this.getItemWrap = function(code) {
        var target = null;
        var position = -1;

        for (var index in this.source) {
            var item = this.source[index];
            if (item.code == code) {
                target = item;
                position = index;
                break;
            }
        }

        return {
            item: target,
            index: position
        };
    };

    this.findItem = function(code) {
        var item = this.getItem(code);
        if (item == null) return false;
        else return true;
    };

    this.deleteItem = function(code) {
        var itemWrap = this.getItemWrap(code);
        var self = this;
        if (itemWrap.item != null) {
            removeItem(itemWrap.item);
        }

        function removeItem(item) {
            removeSubItems(item);
            var itemWrap = self.getItemWrap(item.code);
            self.source.splice(itemWrap.index, 1);
        }

        function removeSubItems(parentItem) {
            var subItems = self.getSubItems(parentItem.code);
            for (var index in subItems) {
                removeItem(subItems[index]);
            }
        }
    };

    this.getSubItems = function(code) {
        var subItems = new Array();
        var parentItem = this.getItem(code);

        if (parentItem != null) {
            for (var index in this.source) {
                var item = this.source[index];
                if (item.isSubOf(parentItem)) subItems[subItems.length] = item;
            }
        }

        return subItems;
    };

	this.getAllSubItems = function(code){
        var itemWrap = this.getItemWrap(code);
        var self = this;
        var itemStr = "";
        if (itemWrap.item != null) {
            processSubItems(itemWrap.item);
        }

        function processItem(item) {
            if(itemStr != "") itemStr += ","
            itemStr += item.code;
            processSubItems(item);
        }

        function processSubItems(parentItem) {
            var subItems = self.getSubItems(parentItem.code);
            for (var index in subItems) {
                processItem(subItems[index]);
            }
        }
        return itemStr;
	};
    
    this.hasSubItems = function(code, status) {
        var isTrue = false;
        var subItems = this.getSubItems(code);        
        if(status == null)
        	isTrue = (Boolean)(subItems.length);
        else 
        {     		   	        	
        	for(var index in subItems)
        	{
        		var item = subItems[index];
        		if(item.status == status)
        		{
        			isTrue = true;
        			break;
        		}
        	}
        }
		return isTrue;
    };

    this.setItemStatus = function(code, status) {
        var item = this.getItem(code);
        if (item != null) item.status = status;
        if(status == "visible") {
        	var parent = item.parent; 
        	while(parent != null){
				parent.status = "visible";
				parent = parent.parent;
			}				
        }
    };

    this.getPrevItems = function(code) {
        var prevItems = [];
        var item = this.getItem(code);
        if (item != null) {
            if (item.isFirstLevel()) {
                var allTopItems = [];
                for (var index in this.source) {
                    if (this.source[index].isFirstLevel()) allTopItems[allTopItems.length] = this.source[index];
                }
                for (var topIndex in allTopItems) {
                    if (allTopItems[topIndex] == item) break;
                }
                if (topIndex != 0) prevItems = allTopItems.slice(0, topIndex);
            } else {
                var subItems = this.getSubItems(item.parent.code);
                for (var index in subItems) {
                    if (subItems[index] == item) break;
                }
                if (index != 0) prevItems = subItems.slice(0, index);
            }
        }

        return prevItems;
    };
}

