; (function($) {
    var methods = {
        init: function(options) {
            return this.each(function() {
                var $this = $(this);
                var settings = $this.data('treeView');

                if (typeof(settings) == 'undefined') {
                    var defaults = {
                        source: []
                    }
                    settings = $.extend({},
                    defaults, options);
                } else {
                    settings = $.extend({},
                    settings, options);
                }

                $this.data('treeView', settings);

                var $treeview = new $.treeView(this);
                $treeview.init();
            });
        },

        destroy: function() {
            return this.each(function() {
                var $treeview = new $.treeView(this);
                $treeview.destroy();
            });
        },

        removeItems: function() {
            return this.each(function() {
                var $treeview = new $.treeView(this);
                $treeview.removeItems();
            });
        },

        removeAllItems: function() {
            return this.each(function() {
                var $treeview = new $.treeView(this);
                $treeview.removeAllItems();
            });
        },

        refresh: function(options) {
            return this.each(function() {
                var $this = $(this);
                var settings = $this.data('treeView');

                if (typeof(settings) == 'undefined') {
                    var defaults = {
                        source: []
                    }
                    settings = $.extend({},
                    defaults, options);
                } else {
                    settings = $.extend({},
                    settings, options);
                }

                $this.data('treeView', settings);

                var $treeview = new $.treeView(this);
                $treeview.refresh();
            });
        },

        getSource: function() {
            var source = new Array();
            this.each(function() {
                var $this = $(this);
                var settings = $this.data('treeView');

                if (typeof(settings) != 'undefined') {
                    source = settings.source;
                }
            });
            return source;
        },

        getSelectedItemCount: function() {
            var num = 0;
            this.each(function() {
                var $treeview = new $.treeView(this);
                var $selectedItems = $treeview.getSelectedItems();
                num = $selectedItems.length;
            });
            return num;
        }
    };

    $.fn.treeView = function(method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.treeView');
        }
    };

    $.treeView = function(holder) {
        this.init = function() {
            createTreeView();
            preSet();
        };

        this.destroy = function() {
            settings.treeview.remove();
            $holder.removeData('treeView');
        };

        this.removeItems = function() {
            $treeview.find("li").find("div.hitarea.checked").each(function() {
                var $this = $(this);
                var itemCode = $this.parent().attr("code");
                repository.setItemStatus(itemCode, "hide");
                $this.parent().remove();
            });

            $treeview.find("li").removeClass("last").filter(":last-child:not(ul)").addClass("last");
        };

        this.removeAllItems = function() {
            $treeview.find("li").each(function() {
                var $this = $(this);
                var itemCode = $this.attr("code");
                repository.setItemStatus(itemCode, "hide");
                $this.remove();                
            });
        };

        this.refresh = function() {
            for (var index in repository.source) {
                var item = repository.source[index];
                if (item.status == "visible") {
                    if ($treeview.find("li[code='" + item.code + "']").length == 0) {
                        var prevItems = repository.getPrevItems(item.code);                        
                        var $prevItem = null;
                        for (var pindex in prevItems) {
                            var pitem = prevItems[pindex];
                            var $pitem = $treeview.find("li[code='" + pitem.code + "']");
                            if ($pitem.length) $prevItem = $pitem;
                        }
                        insertItem(item, $prevItem);
                    }
                }
            }

            $treeview.find("li").removeClass("last").filter(":last-child:not(ul)").addClass("last");
        };

        this.getSelectedItems = function() {
            return $treeview.find("li").has("div.hitarea.checked");
        }

        function createTreeView() {
            $treeview = $(document.createElement("ul"));
            $treeview.attr("class", "treeview");
            $holder.append($treeview);

            settings = $.extend({},
            settings, {
                treeview: $treeview
            });
            $holder.data('treeView', settings);
        }

        function preSet() {
            for (var index in repository.source) {
                var item = repository.source[index];
                if (item.status == "visible") {
                    addItem(item);
                }
            }

            $treeview.find("li").filter(":last-child:not(ul)").addClass("last");
        }

        function addItem(item, $prevItem) {
            var $item = $(document.createElement("li"));
            $item.attr("code", item.code);

            var $icon = $(document.createElement("div"));
            $icon.addClass("hitarea unchecked").click(function() {
                var that = this;
                itemChecked(that);
            });

            $item.append($icon);

            var $span = $(document.createElement("span"));
            $span.html(item.name);
            $span.click(function() {
                var $this = $(this);
                if ($this.parent().find(">div").length) itemChecked($this.parent().find(">div")[0]);
            }).hover(function() {
                var $this = $(this);
                $this.addClass("hover");
            },
            function() {
                var $this = $(this);
                $this.removeClass("hover");
            });

            $item.append($span);

            if (item.isFirstLevel()) {    
                $treeview.append($item);
            } else {
                var $li = $treeview.find("li[code='" + item.parent.code + "']");

                if ($li.find('>ul').length == false) {
                    var $ul = $(document.createElement("ul"));
                    $li.append($ul);
                }
                $li.find('>ul').append($item);
            }
        }

        function insertItem(item, $prevItem) {
            var $item = $(document.createElement("li"));
            $item.attr("code", item.code);

            var $icon = $(document.createElement("div"));
            $icon.addClass("hitarea unchecked").click(function() {
                var that = this;
                itemChecked(that);
            });

            $item.append($icon);

            var $span = $(document.createElement("span"));
            $span.html(item.name);
            $span.click(function() {
                var $this = $(this);
                if ($this.parent().find(">div").length) itemChecked($this.parent().find(">div")[0]);
            }).hover(function() {
                var $this = $(this);
                $this.addClass("hover");
            },
            function() {
                var $this = $(this);
                $this.removeClass("hover");
            });

            $item.append($span);

            if (item.isFirstLevel()) {
                if ($prevItem == null) {
                    if ($treeview.find(">li").length) {
                        $treeview.find(">li:first").before($item);
                    } else $treeview.append($item);
                } else $prevItem.after($item);
            } else {
                if ($prevItem == null) {
                    var $li = $treeview.find("li[code='" + item.parent.code + "']");
                    if ($li.find('>ul').length == false) {
                        var $ul = $(document.createElement("ul"));
                        $li.append($ul);
                        $li.find('>ul').append($item);
                    } else {
                        if ($li.find('>ul>li').length) {
                            $li.find('>ul>li:first').before($item);
                        } else $li.find('>ul').append($item);
                    }
                } else {
                    $prevItem.after($item);
                }
            }
        }

        function itemChecked(element) {
            var $this = $(element);
            var $item = $this.parent();

            if ($this.hasClass("unchecked")) {
                $item.find("div.hitarea").removeClass("unchecked").addClass("checked");
                var item = repository.getItem($item.attr("code"));
                while (item.parent != null) {
                    var uncheckedNum = $treeview.find("li[code='" + item.parent.code + "']").find("ul").find("div.hitarea.unchecked").length;
                    if (uncheckedNum == 0) $treeview.find("li[code='" + item.parent.code + "']").find(">div.hitarea").removeClass("unchecked").addClass("checked");
                    item = item.parent;
                }
            } else if ($this.hasClass("checked")) {
                $item.find("div.hitarea").removeClass("checked").addClass("unchecked");
                var item = repository.getItem($item.attr("code"));
                while (item.parent != null) {
                    var checkedNum = $treeview.find("li[code='" + item.parent.code + "']").find("ul").find("div.hitarea.checked").length;
                    if (checkedNum == 0) $treeview.find("li[code='" + item.parent.code + "']").find(">div.hitarea").removeClass("checked").addClass("unchecked");
                    item = item.parent;
                }
            }
        }

        var $holder = $(holder);
        var settings = $holder.data("treeView");
        var $treeview = settings.treeview;
		var repository = new TreeViewRepository(settings.source);
		
        return this;
    }
})(jQuery);