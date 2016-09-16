/** @type undefined */
var undefined;

/** @type Object */
var Node = Node ? Node : {};
/** @type number */
Node.ELEMENT_NODE 					= 1;
/** @type number */
Node.ATTRIBUTE_NODE 				= 2;
/** @type number */
Node.TEXT_NODE 						= 3;
/** @type number */
Node.CDATA_SECTION_NODE 			= 4;
/** @type number */
Node.ENTITY_REFERENCE_NODE 			= 5;
/** @type number */
Node.ENTITY_NODE 					= 6;
/** @type number */
Node.PROCESSING_INSTRUCTION_NODE 	= 7;
/** @type number */
Node.COMMENT_NODE 					= 8;
/** @type number */
Node.DOCUMENT_NODE 					= 9;
/** @type number */
Node.DOCUMENT_TYPE_NODE 			= 10;
/** @type number */
Node.DOCUMENT_FRAGMENT_NODE 		= 11;
/** @type number */
Node.NOTATION_NODE 					= 12;


/**
 *	String convenience method to trim leading and trailing whitespace.
 *
 *	@returns String
 */
String.prototype.trim = function () {
	return this.replace(/^\s*(.+)/gi,"$1").replace(/\s*$/gi,"");
};

/**
 *	String convenience method for checking if the end of this string equals
 *	a given string.
 *
 *	@returns String
 */
String.prototype.endsWith = function (s) {
	if ("string" != typeof s) {
		throw("IllegalArgumentException: Must pass a string to " +
				"String.endsWith()");
	}
	var start = this.length - s.length;
	return this.substring(start) == s;
};

/**
 *	Array convenience method to check for membership.
 *
 *	@param object element
 *	@returns boolean
 */
Array.prototype.contains = function (element) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == element) {
			return true;
		}
	}
	return false;
};

/**
 *	Array convenience method to remove element.
 *
 *	@param object element
 *	@returns boolean
 */
Array.prototype.remove = function (element) {
	var result = false;
	var array = [];
	for (var i = 0; i < this.length; i++) {
		if (this[i] == element) {
			result = true;
		} else {
			array.push(this[i]);
		}
	}
	this.clear();
	for (var i = 0; i < array.length; i++) {
		this.push(array[i]);
	}
	array = null;
	return result;
};

/**
 *	Array convenience method to clear membership.
 *
 *	@param object element
 *	@returns void
 */
Array.prototype.clear = function () {
	this.length = 0;
};

/**
 *	Array convenience method to add stack functionality to <code>Array</code>s
 *	in browsers that do not support ECMAScript v3.
 *
 *	@param object element
 *	@returns number
 */
Array.prototype.push = function (element) {
	this[this.length] = element;
	return this.length;
};

/**
 *	Array convenience method to add set functionality to <code>Array</code>s
 *	... if the element is already a member of this array, return false. 
 *	Otherwise, add it and return true.
 *
 *	@param object element
 *	@returns boolean
 */
Array.prototype.add = function (element) {
	if (this.contains(element)) {
		return false;
	}
	this.push(element);
	return false;
};

/**
 *	Array convenience method to add set functionality to <code>Array</code>s
 *	... Uniquely adds all elements in the array parameter to this array. 
 *	Returns true if any new elements were added to this array. False otherwise.
 *
 *	@param Array that
 *	@returns boolean
 */
Array.prototype.addAll = function (that) {
	var result = false;
	for (var i = 0; i < that.length; i++) {
		if (this.add(that[i])) {
			result = true;
		}
	}
	return true;
};


var bios = bios ? bios : {};
	bios.report = bios.report ? bios.report : {};
	bios.report.dom   = bios.report.dom   ? bios.report.dom 	 : {};
	bios.report.jsp   = bios.report.jsp   ? bios.report.jsp   : {};
	bios.report.util  = bios.report.util  ? bios.report.util  : {};
	bios.report.event = bios.report.event ? bios.report.event : {};

/**
 *	A utility class that exists to combine functionality for the Tabs
 *	into a single class as static methods and constants. This class is not 
 *	meant to be subclassed.
 *
 *	@constructor 
 */
bios.report.jsp.TabUtils = function () {};

/** @type string */
bios.report.jsp.TabUtils.TAB_CONTAINER_CLASS_NAME	= "bios-tab-container";
/** @type string */
bios.report.jsp.TabUtils.TAB_WRAP_CLASS_NAME	    = "bios-tab-wrap";
/** @type string */
bios.report.jsp.TabUtils.TAB_CLASS_NAME		    = "bios-tab";
/** @type string */
bios.report.jsp.TabUtils.TAB_BG_LEFT_CLASS_NAME	= "bios-tab-bg-left";
/** @type string */
bios.report.jsp.TabUtils.TAB_PANE_WRAP_CLASS_NAME	= "bios-tab-pane-wrap";
/** @type string */
bios.report.jsp.TabUtils.TAB_PANE_CLASS_NAME	    = "bios-tab-pane";

/** @type HTMLElement */
bios.report.jsp.TabUtils.tabContainer;
/** @type HTMLElement */
bios.report.jsp.TabUtils.tabWrap;
/** @type HTMLElement */
bios.report.jsp.TabUtils.tab;
/** @type Array */
bios.report.jsp.TabUtils.tabs;
/** @type Array */
bios.report.jsp.TabUtils.tabPanes;
/** @type number */
bios.report.jsp.TabUtils.selectedIndex;


/**
 *	A utility class that exists to combine common DOM algorithms and utilities
 *	into a single class as static methods and constants. This class is not 
 *	meant to be subclassed.
 *
 *	@constructor 
 */
bios.report.dom.DomUtils = function () {};

/** @type string */
bios.report.dom.DomUtils.FOCUSED_CLASS_NAME	= "bios-focused";
/** @type string */
bios.report.dom.DomUtils.UNFOCUSED_CLASS_NAME	= "bios-unfocused";

/**
 *	@param Element target
 *	@returns void
 */
bios.report.dom.DomUtils.show = function (target) {
	target.style.display = "";
};

/**
 *	@param Element target
 *	@returns void
 */
bios.report.dom.DomUtils.hide = function (target) {
	target.style.display = "none";
};

/**
 *	@param Element target
 *	@returns void
 */
bios.report.dom.DomUtils.setClassNameAsFocused = function (target) {
	with (bios.report.dom.DomUtils) {
		removeClassName( target,UNFOCUSED_CLASS_NAME );
		addClassName(    target,FOCUSED_CLASS_NAME   );
	}
};

/**
 *	@param Element target
 *	@returns void
 */
bios.report.dom.DomUtils.setClassNameAsUnFocused = function (target) {
	with (bios.report.dom.DomUtils) {
		removeClassName( target,FOCUSED_CLASS_NAME   );
		addClassName(    target,UNFOCUSED_CLASS_NAME );
	}
};

/**
 *	@param Node target
 *	@param string k
 *	@returns void
 */
bios.report.dom.DomUtils.addClassName = function (target,k) {
	with (bios.report.dom.DomUtils) {
		if(!isElementNode(target)) {
			throw new Error("Attempting to add a className to a non-Element" +
								" Node");
		}
		var classNames = target.className.split(/\s+/g);
		if (classNames.contains(k)) {
			return;
		} else {
			classNames.push(k);
		}
		target.className = classNames.join(" ");
		target.className = target.className.trim();
	}
}

/**
 *	@param Node target
 *	@param string k
 *	@returns void
 */
bios.report.dom.DomUtils.removeClassName = function (target,k) {
	with (bios.report.dom.DomUtils) {
		if(!isElementNode(target)) {
			throw new Error("Attempting to remove a className to a " +
								"non-Element Node");
		}
		var classNames = target.className.split(/\s+/g);
		if (!classNames.contains(k)) {
			return;
		} else {
			classNames.remove(k);
		}
		target.className = classNames.join(" ");
		target.className = target.className.trim();
	}
}

/**
 *	Tests to see if <code>target</code>'s <code>getNodeType()</code> 
 *	method returns <code>Node.ELEMENT_NODE</code>.
 *	@param Element target
 *	@returns boolean
 */
bios.report.dom.DomUtils.isElementNode = function (target) {
	return Node.ELEMENT_NODE == target.nodeType;
};

/**
 *	@param Element target
 *	@param string id
 *	@returns boolean
 */
bios.report.dom.DomUtils.hasId = function (target,id) {
	return target.id == id;
};

/**
 *	@param Element target
 *	@param string className
 *	@returns boolean
 */
bios.report.dom.DomUtils.hasClassName = function (target,className) {
	
	function _isLastOfMultpleClassNames(all,className) {
		var spaceBefore = all.lastIndexOf(className)-1;
		return all.endsWith(className) && 
			all.substring(spaceBefore,spaceBefore+1) == " ";
	}

	className = className.trim();
	var cn = target.className;
	if (!cn) {
		return false;
	}
	cn = cn.trim();
	if (cn == className) {
		return true;
	}
	if (cn.indexOf(className + " ") > -1) {
		return true;
	}
	if (_isLastOfMultpleClassNames(cn,className)) {
		return true;
	}
	return false;
};

/**
 *	@param Node target
 *	@param string className
 *	@returns Element
 */
bios.report.dom.DomUtils.getFirstAncestorOrSelfByClassName = function (target,
															   className) {
	with (bios.report.dom.DomUtils) {
		var parent = target;
		do {
			if (isElementNode(parent) && hasClassName(parent,className)) {
				return parent;
			}
		} while (parent = parent.parentNode);
	}
	return null;
};

/**
 *	@param Node target
 *	@param string className
 *	@returns Element
 */
bios.report.dom.DomUtils.getFirstAncestorByClassName = function (target,className) {
	with (bios.report.dom.DomUtils) {
		var parent = target;
		while (parent = parent.parentNode) {
			if (isElementNode(parent) && hasClassName(parent,className)) {
				return parent;
			}
		}
	}
	return null;
};

/**
 *	@param Node target
 *	@param string className
 *	@returns Element
 */
bios.report.dom.DomUtils.getFirstChildByClassName = function (target,className) {
	with (bios.report.dom.DomUtils) {
		var kids = target.childNodes;
		for (var i = 0; i < kids.length; i++) {
			var kid = kids[i];
			if (isElementNode(kid) && hasClassName(kid,className)) {
				return kid;
			}
		}
	}
	return null;
};

/**
 *	@param Node target
 *	@param string className
 *	@returns Array
 */
bios.report.dom.DomUtils.getChildrenByClassName = function (target,className) {
	var result = [];
	with (bios.report.dom.DomUtils) {
		var kids = target.childNodes;
		for (var i = 0; i < kids.length; i++) {
			var kid = kids[i];
			if (isElementNode(kid) && hasClassName(kid,className)) {
				result.push(kid);
			}
		}
	}
	return result;
};



/**
 *	Retreives <code>target</code>'s first descendant element with an
 *	HTML <code>class</code> attribute value that includes <code>
 *	className</code> using a breadth-first algorithm.
 *	
 *	@param Element target
 *	@param string className
 *	@returns Element
 */
bios.report.dom.DomUtils.getFirstDescendantByClassNameBreadthFirst = function (
																target,
																className) {
	var result;
	with (bios.report.dom.DomUtils) {
		if (result = getFirstChildByClassName(target,className)) {
			return result;
		}
		for (var i = 0; i < target.childNodes.length; i++) {
			result = getFirstDescendantByClassNameBreadthFirst(
										target.childNodes.item(i),
										className );
			if (result) {
				return result;
			}
		}
		return null;
	}
};

/**
 *	Retreives <code>target</code>'s first descendant element with an
 *	HTML <code>class</code> attribute value that includes <code>
 *	className</code> using a depth-first algorithm.
 *	
 *	@param Element target
 *	@param string className
 *	@returns Element
 */
bios.report.dom.DomUtils.getFirstDescendantByClassNameDepthFirst = function (
																target,
																className) {
	var child;
	var result;
	with (bios.report.dom.DomUtils) {
		for (var i = 0; i < target.childNodes.length; i++) {
			child = target.childNodes.item(i);
			if (isElementNode(child) && hasClassName(child,className)) {
				return child;
			}
			result = getFirstDescendantByClassNameDepthFirst(
													target.childNodes.item(i),
													className );
			if (result) {
				return result;
			}
		}
		return null;
	}
};

/**
 *	Returns all descendant elements of <code>target</code> with HTML <code>
 *	class</code> attribute values that contain <code>className</code>
 *	as an Array. NOTE that unlike the
 *	algorithms specified in the DOM spec, a <code>NodeList</code> is NOT
 *	returned. This method searched for all descendants of <code>target
 *	</code> meeting the criteria using a breadth-first algorithm.
 *
 *	@param Element target
 *	@param string className
 *	@returns Element
 */
bios.report.dom.DomUtils.getDescendantsByClassName = function (target,className) {
	var result = [];
	with (bios.report.dom.DomUtils) {
		result.addAll(getChildrenByClassName(target,className));
		for (var i = 0; i < target.childNodes.length; i++) {
			result.addAll(getDescendantsByClassName(
											target.childNodes.item(i),
											className));
		}
		return result;
	}
};

/**
 *	@param MouseEvent evt
 *	@returns void
 */
bios.report.jsp.TabUtils.tabClicked = function (evt, ifUrl) {
	var eventSource;
	with (bios.report) {
		evt = new event.Event(evt);
		eventSource = evt.getSource();
		eventSource = dom.DomUtils.getFirstAncestorOrSelfByClassName(
												eventSource,
												jsp.TabUtils.TAB_CLASS_NAME );
		jsp.TabUtils.switchTab(eventSource, ifUrl);
		evt.consume();
	}
};

/**
 *	@param HTMLElement eventSource
 *	@returns void
 */
bios.report.jsp.TabUtils.switchTab = function (eventSource, ifUrl) {
	with (bios.report.jsp.TabUtils) {
		findTabElements(eventSource);
		selectedIndex = determineSelectedIndex(eventSource);
		toggleTabVisibility(selectedIndex, ifUrl);
		setTabCookie(selectedIndex);
	}
};

/**
 *	@param number selectedIndex
 *	@returns void
 */
bios.report.jsp.TabUtils.setTabCookie = function (selectedIndex) {
	with (bios.report) {
		var name  = "bios.report.jsp.tabs:"+jsp.TabUtils.tabContainer.id;
		var value = selectedIndex;
		var c = new util.Cookie(name,value);
		util.Cookie.addPageCookie(c);
	}
};

/**
 *	@param HTMLElement eventSource
 *	@returns void
 */
bios.report.jsp.TabUtils.findTabElements = function (eventSource) {
	with (bios.report) {
		jsp.TabUtils.tab		  = eventSource;
		jsp.TabUtils.tabContainer = dom.DomUtils.getFirstAncestorByClassName(
										eventSource,
										jsp.TabUtils.TAB_CONTAINER_CLASS_NAME );
		jsp.TabUtils.tabWrap 	  	= dom.DomUtils.getFirstChildByClassName(
										jsp.TabUtils.tabContainer,
										jsp.TabUtils.TAB_WRAP_CLASS_NAME );
		jsp.TabUtils.tabs 		  = dom.DomUtils.getChildrenByClassName(
										jsp.TabUtils.tabWrap,
										jsp.TabUtils.TAB_CLASS_NAME );
		jsp.TabUtils.tabPaneWrap  = dom.DomUtils.getFirstChildByClassName(
										jsp.TabUtils.tabContainer,
										jsp.TabUtils.TAB_PANE_WRAP_CLASS_NAME );
		jsp.TabUtils.tabPanes  	  = dom.DomUtils.getChildrenByClassName(
										jsp.TabUtils.tabPaneWrap,
										jsp.TabUtils.TAB_PANE_CLASS_NAME );
	}
};

/**
 *	@param HTMLElement eventSource
 *	@returns number
 */
bios.report.jsp.TabUtils.determineSelectedIndex = function (eventSource) {
	with (bios.report.jsp.TabUtils) {
		for (var i = 0; i < tabs.length; i++) {
			if (tabs[i] == eventSource) {
				return i;
			}
		}
	}
	//throw new Error("Should not reach here: Did not find tab selectedIndex");
};

/**
 *	@param number selectedIndex
 *	@returns void
 */
bios.report.jsp.TabUtils.toggleTabVisibility = function (selectedIndex, ifUrl) {
	with (bios.report) {
		for (var i = 0; i < jsp.TabUtils.tabPanes.length; i++) {
			var tabPane = jsp.TabUtils.tabPanes[i];
			if (i == selectedIndex) {
				dom.DomUtils.show(tabPane);
				dom.DomUtils.setClassNameAsFocused(jsp.TabUtils.tabs[i]);
				
				var ifobj = tabPane.childNodes.item(0);
				if(ifobj == undefined)
					return;
				if(ifobj.src == null || ifobj.src == ""){
					tabPane.childNodes.item(0).src = ifUrl;
				}
				
			} else {
				dom.DomUtils.hide(tabPane);
				dom.DomUtils.setClassNameAsUnFocused(jsp.TabUtils.tabs[i]);
			}
		}
	}
};

/**
 *	@param Event evt
 *	@param string tabContainerId
 *	@param number selectedIndex
 *	@returns void
 */
bios.report.jsp.TabUtils.tabLinkClicked = function (evt,selectedTabPaneId) {
	var tabContainer,selectedTabPane,tabWrap,tabs,tabPaneWrap,tabPanes,
		selectedIndex;
	with (bios.report) {
		evt = new event.Event(evt);
		selectedTabPane = document.getElementById(selectedTabPaneId);
		tabContainer    = dom.DomUtils.getFirstAncestorByClassName(
										selectedTabPane,
										jsp.TabUtils.TAB_CONTAINER_CLASS_NAME );
		tabWrap			= dom.DomUtils.getFirstChildByClassName(
										tabContainer,
										jsp.TabUtils.TAB_WRAP_CLASS_NAME );
		tabs			= dom.DomUtils.getChildrenByClassName(
										tabWrap,
										jsp.TabUtils.TAB_CLASS_NAME );
		tabPaneWrap		= dom.DomUtils.getFirstAncestorByClassName(
										selectedTabPane,
										jsp.TabUtils.TAB_PANE_WRAP_CLASS_NAME );
		tabPanes		= dom.DomUtils.getChildrenByClassName(
										tabPaneWrap,
										jsp.TabUtils.TAB_PANE_CLASS_NAME );
		for (var i = 0; i < tabPanes.length; i++) {
			if (tabPanes[i] == selectedTabPane) {
				selectedIndex = i;
				break;
			}
		}
		jsp.TabUtils.switchTab(tabs[selectedIndex], "");
		window.scrollTo(0,0);
		evt.consume();
	}
};

/**
 *	@param Event evt
 *	@param string tabContainerId
 *	@returns void
 */
bios.report.jsp.TabUtils.prevTabButtonClicked = function (evt,tabContainerId) {
	bios.report.jsp.TabUtils.tabButtonClicked(evt,tabContainerId,true);
};

/**
 *	@param Event evt
 *	@param string tabContainerId
 *	@returns void
 */
bios.report.jsp.TabUtils.nextTabButtonClicked = function (evt,tabContainerId) {
	bios.report.jsp.TabUtils.tabButtonClicked(evt,tabContainerId,false);
};

/**
 *	@param Event evt
 *	@param string tabContainerId
 *	@returns void
 */
bios.report.jsp.TabUtils.tabButtonClicked = function (evt,tabContainerId,isPrev) {
	var tabContainer,tabWrap,tabs,selectedIndex;
	with (bios.report) {
		evt = new event.Event(evt);
		tabContainer = document.getElementById(tabContainerId);
		tabWrap 	 = dom.DomUtils.getFirstChildByClassName(
											tabContainer,
											jsp.TabUtils.TAB_WRAP_CLASS_NAME );
		tabs		 = dom.DomUtils.getChildrenByClassName(
											tabWrap,
											jsp.TabUtils.TAB_CLASS_NAME );
		for (var i = 0; i < tabs.length; i++) {
			if (dom.DomUtils.hasClassName(tabs[i],dom.DomUtils.FOCUSED_CLASS_NAME)) {
				selectedIndex = (isPrev) ? i-1 : i+1;
				selectedIndex = (selectedIndex >= tabs.length) ? 0 : 
								(0 > selectedIndex) ? tabs.length - 1  : 
								selectedIndex;
				break;
			}
		}
		jsp.TabUtils.switchTab(tabs[selectedIndex], "");
		evt.consume();
	}
};

/**
 *	@constructor
 *	@param HTMLElement
 */
bios.report.jsp.TabEvent = function (source) {
	this._source = source;
	with (bios.report.jsp.TabUtils) {
		this._tabContainer = tabContainer;
		this._selectedIndex = selectedIndex;
		this._tabPane = tabPanes[selectedIndex];
	}
};

/**	@return HTMLElement */
bios.report.jsp.TabEvent.prototype.getSource = function () {
	return this._source;
};
/**	@return number */
bios.report.jsp.TabEvent.prototype.getSelectedIndex = function () {
	return this._selectedIndex;
};
/**	@return HTMLElement */
bios.report.jsp.TabEvent.prototype.getTab = function () {
	return this._source;
};
/**	@return HTMLElement */
bios.report.jsp.TabEvent.prototype.getTabContainer = function () {
	return this._tabContainer;
};
/**	@return HTMLElement */
bios.report.jsp.TabEvent.prototype.getTabPane = function () {
	return this._tabPane;
};
/**	@return string */
bios.report.jsp.TabEvent.prototype.toString = function () {
	return "[ bios.report.jsp.TabEvent object ]";
};

/**
 *	@constructor
 *	@param MouseEvent evt
 */
bios.report.event.Event = function (evt) {
	this._evt 	 = evt ? evt : window.event;
	this._source = this._evt.currentTarget ? 
				   this._evt.currentTarget : this._evt.srcElement;
};

/**
 *	@returns Element
 */
bios.report.event.Event.prototype.getSource = function () {
	return this._source;
};

/**
 *	@returns void
 */
bios.report.event.Event.prototype.consume = function () {
	if (this._evt.stopPropagation) {
		this._evt.stopPropagation();
		this._evt.preventDefault();
	} else if (this._evt.cancelBubble) {
		this._evt.cancelBubble = true;
		this._evt.returnValue  = false;
	}
};







/**
 *	<p>Bean that represents individual Cookie objects, packaging their 
 *	attributes into a convenient API that can more easily be manipulated and
 *	set.</p><p>Also contains 2 static convenience methods for setting and
 *	the cookies for the current page.
 *	@constructor	Accepts initial values for the <code>name</code> and 
 *				<code>value</code> attribute values.
 *	@param	String	name		Initial <code>name</code> attribute value.	
 *	@param	String	value	Initial <code>value</code> attribute value.	
 */	
bios.report.util.Cookie = function (name,value) {
	this._name;
	this._value;
	this._path;
	this._secure;
	this._domain;
	this._expires;
	if (name) {
		this.setName(name);
	}
	if (value) {
		this.setValue(value);
	}
};

// Static attributes
bios.report.util.Cookie.EQUALS 			= "=";
bios.report.util.Cookie.DELIM 				= "; ";
bios.report.util.Cookie.PATH 				= "path";
bios.report.util.Cookie.SECURE 			= "secure";
bios.report.util.Cookie.DOMAIN 			= "domain";
bios.report.util.Cookie.EXPIRES 			= "expires";
bios.report.util.Cookie.SUB_VALUE_DELIM 	= ":"

/**
 *	Static convenience method that sets the given cookie for the current page.
 *	@param	Cookie	c	<code>Cookie</code> object to be set.
 *	@returns	void
 */
bios.report.util.Cookie.addPageCookie = function (c) {
/*	if (!(c instanceof bios.report.util.Cookie)) {
		throw new Error("IllegalArgumentException: Cookie.set() accepts only" +
			" one parameter of type Cookie");
	}*/
	document.cookie = c.toCookieString();
};

/**
 *	Static convenience method that returns all the <code>document.cookie</code>
 *	String for the current page.
 *	@returns	String
 */
bios.report.util.Cookie.getPageCookieString = function () {
	return document.cookie;
};

/**
 *	Static convenience method that checks the <code>document.cookie</code>
 *	String for the current page to see if the give <code>Cookie</code> exists.
 *	@param	Cookie	c
 *	@returns	boolean
 */
bios.report.util.Cookie.pageHasCookieWithNameAndValue = function (c) {
	var s = c.toNameValueString();
	return (Cookie.get().indexOf(s) > -1);
};

bios.report.util.Cookie.pageHasCookieWithName = function (c) {
	var s = c.getName();
	return (Cookie.get().indexOf(s) > -1);
};

/**
 *	Returns a String representation of this <code>Cookie</cookie> in the 
 *	standard cookie syntax.
 *	@returns	String
 */
bios.report.util.Cookie.prototype.toCookieString = function () {
	with (bios.report.util) {
		var buff = new StringBuffer();
		// name=value;
		buff.append(this.getName()).append(Cookie.EQUALS);
//		if (this.getValue() instanceof Array) {
		if (typeof this.getValue() == "object" && 
			this.getValue().constructor == Array) {
			var values = this.getValue();
			for (var i = 0; i < values.length; i++) {
				buff.append(values[i])
					.append(i == values.length-1 ? "" : Cookie.SUB_VALUE_DELIM);
			}
		} else {
			buff.append(this.getValue())
		}
		buff.append(Cookie.DELIM);
		// path=pathvalue;
		buff.append(Cookie.PATH).append(Cookie.EQUALS)
			.append(this.getPath()).append(Cookie.DELIM);
		// expires=expiresvalue
		buff.append(Cookie.EXPIRES).append(Cookie.EQUALS)
			.append(this.getExpires()).append(Cookie.DELIM);
		if (this.getDomain()) {
			// domain=domainvalue
			buff.append(Cookie.DOMAIN).append(Cookie.EQUALS)
				.append(this.getDomain()).append(Cookie.DELIM);
		}
		if (this.isSecure()) {
			// secure;
			buff.append(Cookie.SECURE).append(Cookie.DELIM);
		}
	}
	return buff.toString();
};

/**
 *	Returns a String representation of this <code>Cookie</cookie> in the 
 *	standard cookie syntax -- but only the name and value with no trailing
 *	semicolon.
 *	@returns	String
 */
bios.report.util.Cookie.prototype.toNameValueString = function () {
	var buff = new bios.report.util.StringBuffer();
	// name=value -- no trailing semicolon
	buff.append(this.getName()).append(Cookie.EQUALS)
		.append(this.getValue());
	return buff.toString();
};

/**	
 *	Get the <code>name</code> attribute value for this <code>Cookie</code> 
 *	object.
 *	@returns String
 */	
bios.report.util.Cookie.prototype.getName = function () {
	return this._name;
};

/**	
 *	Set the <code>name</code> attribute value for this <code>Cookie</code> 
 *	object.
 *	@param	String 	name
 *	@returns	void
 */	
bios.report.util.Cookie.prototype.setName = function (name) {
	this._name = name;
};

/**	
 *	Get the <code>value</code> attribute value for this <code>Cookie</code> 
 *	object.
 *	@returns String
 */	
bios.report.util.Cookie.prototype.getValue = function () {
	return this._value;
};

/**	
 *	Set the <code>value</code> attribute value for this <code>Cookie</code> 
 *	object.
 *	@param	String 	value
 *	@returns	void
 */	
bios.report.util.Cookie.prototype.setValue = function (value) {
	this._value = value;
};

/**	
 *	Get the <code>path</code> attribute value for this <code>Cookie</code> 
 *	object.
 *	@returns String
 */	
bios.report.util.Cookie.prototype.getPath = function () {
	if (this._path === undefined) {
		return "/";
	} else {
		return this._path;
	}
};

/**	
 *	Set the <code>path</code> attribute value for this <code>Cookie</code> 
 *	object.
 *	@param	String 	path
 *	@returns	void
 */	
bios.report.util.Cookie.prototype.setPath = function (path) {
	this._path = path;
};

/**	
 *	Get the <code>secure</code> attribute value for this <code>Cookie</code> 
 *	object.
 *	@returns boolean
 */	
bios.report.util.Cookie.prototype.isSecure = function () {
	return this._secure;
};

/**	
 *	Set the <code>secure</code> attribute value for this <code>Cookie</code> 
 *	object.
 *	@param	boolean 	secure
 *	@returns	void
 */	
bios.report.util.Cookie.prototype.setSecure = function (secure) {
	this._secure = secure;
};

/**	
 *	Get the <code>domain</code> attribute value for this <code>Cookie</code> 
 *	object.
 *	@returns String
 */	
bios.report.util.Cookie.prototype.getDomain = function () {
	return this._domain;
};

/**	
 *	Set the <code>domain</code> attribute value for this <code>Cookie</code> 
 *	object.
 *	@param	String 	domain
 *	@returns	void
 */	
bios.report.util.Cookie.prototype.setDomain = function (domain) {
	this._domain = domain;
};

/**	
 *	Get the <code>expires</code> attribute value for this <code>Cookie</code> 
 *	object.
 *	@returns String
 */	
bios.report.util.Cookie.prototype.getExpires = function () {
	if (this._expires === undefined) {
		return this._getDefaultExpires();
	} else {
		return this._expires;
	}
};

/**	
 *	Set the <code>expires</code> attribute value for this <code>Cookie</code> 
 *	object.
 *	@param	String 	expires
 *	@returns	void
 */	
bios.report.util.Cookie.prototype.setExpires = function (expires) {
	this._expires = expires;
};

/**	
 *	Private method to get the devault value for the <code>domain</code>
 *	attribute value for this <code>Cookie</code> 
 *	object.
 *	@returns String
 */	
bios.report.util.Cookie.prototype._getDefaultDomain = function () {
	return window.location.hostname;
};

/**	
 *	Private method to get the devault value for the <code>expires</code>
 *	attribute value for this <code>Cookie</code> 
 *	object.
 *	@returns String
 */	
bios.report.util.Cookie.prototype._getDefaultExpires = function () {
	var date = new Date();
	date.setFullYear(date.getFullYear()+10);
	return date.toGMTString();
};

/**
 *	A utility class that mirrors the <code>java.util.StringBuffer</code> class.
 *	Improves performance of string concatenation by storing strings in a
 *	private internal array.
 *	@constructor			Accepts optional inital parameter to store in the 
 *						buffer.
 *	@param	String	s	Initial buffer value.
 */
bios.report.util.StringBuffer = function (s) {
	this._array = [];
	if (s && typeof s != "string") {
		throw new Error("IllegalArgumentException: StringBuffer's " +
			"constructor accepts an optional String argument. Given:" +
			(typeof s));
	}
	if (s) {
		this.append(s);
	}
};

/**
 *	Appends an additional String to the buffer and returns a reference to this
 *	object for method chaining.
 *	@param	String	s	New String to be added to buffer.
 *	@returns	StringBuffer
 */
bios.report.util.StringBuffer.prototype.append = function (s) {
	this._array.push(s);
	return this;
};

/**
 *	Returns a String representation of this <code>StringBuffer</code>
 *	@returns	String
 */
bios.report.util.StringBuffer.prototype.toString = function () {
	return this._array.join("");
};

/**
 *	An object that maps keys to values. A map cannot contain duplicate keys;
 *	each key can map to at most one value.
 */
bios.report.util.Map = function (o) {
/*	if (o && !(o instanceof Object)) {
		throw new Error("IllegalArgumentException: Map's " +
					"constructor's only argument must be an Object");
	}*/
	this._obj = (o) ? (o) : new Object();
};

/**
 *	Associates the specified value with the specified key in this map.
 *	@param	Object key	New key to enter,
 *	@param	Object Value	New value to enter.
 *	@returns Object
 */
bios.report.util.Map.prototype.put = function (key,value) {
	this._obj[key] = value;
};

/**
 *	Returns the value to which this map maps the specified key.
 *	@param	Object key	Get the value for this key in this Map.
 *	@returns Object
 */
bios.report.util.Map.prototype.get = function (key) {
	if (!this._obj[key]) return null;
	return this._obj[key];
};

/**
 *	Returns the number of key-value mappings in this map.
 *	@returns number
 */
bios.report.util.Map.prototype.size = function () {
	var count = 0;
	for (var key in this._obj)
		count++;
	return count;
};

/**
 *	Returns true if this map contains no key-value mappings.
 *	@returns	boolean
 */
bios.report.util.Map.prototype.isEmpty = function () {
	return this.size() == 0;
};

/**
 *	Returns a string representation of this map.
 *	@returns String
 */
bios.report.util.Map.prototype.toString = function () {
	var buff = new bios.report.util.StringBuffer();
	count = 0;
	for ( var key in this._obj ) {
		buff.append(key).append(" = ").append(this._obj[key])
			.append(count == this.size() - 1 ? "" : "\r\n");
		count++;
	}
	return buff.toString();
};

/**
 *	Returns true if this map contains a mapping for the specified  key.
 *	@param	Object	key	Testing this Map for key with this value.
 *	@returns	boolean
 */
bios.report.util.Map.prototype.containsKey = function (key) {
	if (this._obj[key] !== undefined) {
		return true;
	}
	return false;
};

/**
 *	Returns true if this map maps one or more keys to the  specified value.
 *	@param	Object	value	Testing this Map for key with this value.
 *	@returns	boolean
 */
bios.report.util.Map.prototype.containsValue = function (value) {
	for (var key in this._obj) {
		if (this._obj[key] == value) {
			return true;
		}
	}
	return false;
};

/**
 *	Removes the mapping for this key from this map if it is present
 *	@param	Object	key	Testing this Map for key with this value.
 *	@returns	boolean
 */
bios.report.util.Map.prototype.remove = function (key) {
	if (this.containsKey(key)) {
		delete this._obj[key];
	}
};

/**
 *	Returns a set view of the values contained in this map.
 *	@returns	Array
 */
bios.report.util.Map.prototype.keySet = function () {
	var keys = [];
	for (var key in this._obj) {
		keys.push(key);
	}
	return keys;
};

/**
 *	Returns a collection view of the values contained in this map.
 *	@returns	Collection
 */
bios.report.util.Map.prototype.values = function () {
	var values = new Collection();
	for (var key in this._obj) {
		values.add(this._obj[key]);
	}
	return values;
};

/**
 *	Map implementation that makes working with URL params easy.
 *	@constructor		should not be called directly
 */
bios.report.util.ParameterMap = function (q) {
	this._map = new bios.report.util.Map();
	this._q;
	if (q) {
		this._q = q.substring(1);
	}
	var pairs = this._q.split(/&+/g);
	var a;
	for (var i = 0; i < pairs.length; i++) {
		a = pairs[i].split(/=+/g);
		this._map.put(a[0],a[1]);
	}
	/*this._re = /(\w+)\=([^&]+)&?/g;
	this._current = [];
	if(!isIE5Mac && !isIEWin50) {
		while (this._current = this._re.exec(this._q)) {
			this._map.put(this._current[1],this._current[2]);
		}
	}*/
};

/**
 *	Static convenience method to get a parameter map containing the current
 *	pages URL params.
 *	@returns	ParameterMap
 */
bios.report.util.ParameterMap.getPageParameterMap = function () {
	return new bios.report.util.ParameterMap(
								window.location.search.toString());
};

/**
 *	Returns string representation of this parameter map in the standard URL 
 *	query string format (e.g. '?name=value&name2=value'
 *	@returns	String
 */
bios.report.util.ParameterMap.prototype.toQueryString = function () {
	var buff = new bios.report.util.StringBuffer("?");
	var count = 0;
	var key,value;
	var keys = this._map.keySet();
	for (var i = 0; i < keys.length; i++) {
		key = keys[i];
		value = this._map.get(key);
		buff.append(key).append("=").append(value)
			.append(count == this._map.size() - 1 ? "" : "&");
		count++;
	}
	return buff.toString();
};