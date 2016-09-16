/**
 * Menu Constructor
 */

function Menu(code, name, parent, status) {

    this.code = code;
    this.name = name;
    //父菜单
    this.parent = parent;

    if (status == null) this.status = "visible"; //visible, hide
    else this.status = status;
}

Menu.prototype = new TreeViewItem();

/**
 * Menu Repository
 */

function MenuRepository(source) {

    this.source = source || [];

    this.addMenu = function(code, name, parent, status) {
        var menu = new Menu(code, name, null, status);

        if (menu.isFirstLevel()) this.source[this.source.length] = menu;
        else {
            menu.parent = this.getItem(code.substr(0, code.length - 2));
            this.source[this.source.length] = menu;
        }
    };

    this.setMenuRelations = function() {
        for (var index in this.source) {
            var menu = this.source[index];
            if (menu.isFirstLevel()) menu.parent = null;
            else {
                menu.parent = this.getItem(menu.code.substr(0, menu.code.length - 2));
            }
        }
    };

}

MenuRepository.prototype = new TreeViewRepository(); 