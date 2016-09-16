function SearchTablePairRow(table, field, values)
{
    this.type = ST_RowType.pair;
    this.table = table;
    this.field = field;
    this.values = values;
    this.row = null;
    this.getRowIndex = STPR_getRowIndex;
    this.getRowCss = STPR_getRowCss;
    this.init = STPR_init;
    this.setRow = STPR_setRow;
    this.resetRow = STPR_resetRow;
    this.removeRow = STPR_removeRow;
    this.validateRow = STPR_validateRow;
    this.equals = STPR_equals;
    this.getSql = STPR_getSql;
	this.getCustomSql = STPR_getCustomSql;
	this.getValues = STPR_getValues;
	
    this.init();
}

function STPR_init()
{
	var htmlRowCount = this.table.htmlRows.length;
	
	//$Author :jin_peng
	//$Date : 2013/01/22 17:47$
	//[BUG]0013147 MODIFY BEGIN
	var rowTemplate = $(this.table.rowTemplateId);
	//[BUG]0013147 MODIFY END
	
	var newRow = rowTemplate.clone(true);//创建最后一行的一个副本			
	newRow.removeAttr("id");
	
	var lastRow = null;
	if(htmlRowCount == 0)
		lastRow = rowTemplate;
	else
		lastRow = this.table.htmlRows[htmlRowCount - 1];
	newRow.insertAfter(lastRow);
	
	this.row = newRow;
	this.table.htmlRows[this.table.htmlRows.length] = newRow;	

	this.setRow();
}

function STPR_getRowCss()
{
    var index = this.table.findRow(this);
    if (index == - 1) 
    {
        var rowCount = this.table.rows.length;
        if (rowCount % 2 == 0) {
            return "even";
        }
        else {
            return "odd";
        }
    }
    else {
        if (index % 2 == 0) {
            return "even";
        }
        else {
            return "odd";
        }
    }
}

function STPR_setRow()
{    
    var keyField = this.field.keyField;
    var customKeyField = keyField.getField();
    var customValueField = this.field.valueField.getField();
    var customKeyFieldSource = customKeyField.source;
    var customValueFieldType = customValueField.type;
 
    var selectControl = this.row.find("select.control");
    var selectField = this.row.find("select.field");
    var selectOpt = this.row.find("select.opt");
    var inputValue = this.row.find("input.inputValue");
    var selectValue = this.row.find("select.selectValue");
    var inputDate = this.row.find("input.datepickerOther");
 
 	var rowIndex = this.getRowIndex();
 	
    this.row.attr("id", "row" + rowIndex);
    this.row.attr("class", "");
    this.row.addClass(this.getRowCss());
 
// 	this.row.children("td:first").css("text-align", "left");
    this.row.children("td:first").html(this.field.text + "条件");
 
    selectControl.attr("id", "control" + rowIndex);
    if (this.values != null && this.values.control != null && this.values.control != "") {
        selectControl.val(this.values.control);
    }
    selectControl.show();
 
    selectField.attr("id", "field" + rowIndex);
    selectField[0].options.length = 0;

 	if(keyField.suggest)
	{
		$("#" + customKeyFieldSource).children("option").each(function(){
			var newOption = new Option(this.text, this.value);
			$(newOption).attr("searchtext", $(this).attr("searchtext"));
			selectField[0].options[selectField[0].options.length] = newOption; 
		});  
	    if (this.values != null && this.values.field != null && this.values.field != "") {
	        selectField.val(this.values.field);
	    }
		selectField.htmlSelectSuggest({
			width: selectField.width(),
				pageSize: 5,
				maxHeight: "120"
		});
	}
    else 
    {
	    $("#" + customKeyFieldSource).children("option").each(function ()
	    {
	        selectField[0].options[selectField[0].options.length] = new Option(this.text, this.value);
	    });
	    if (this.values != null && this.values.field != null && this.values.field != "") {
	        selectField.val(this.values.field);
	    }
    }
 	 
    selectOpt.attr("id", "opt" + rowIndex);
    selectOpt[0].options.length = 0;
    var typeOpts = null;
    if (customValueFieldType == "string") {
        typeOpts = stringOpts;
    }
    else if (customValueFieldType == "list") {
        typeOpts = listOpts;
    }
    else if (customValueFieldType == "number") {
        typeOpts = numberOpts;
    }
    else if (customValueFieldType == "date") {
        typeOpts = dateOpts;
    }
    else {
        typeOpts = new Array();
    }
    for (var i = 0; i < typeOpts.length; i++) 
    {
        selectOpt[0].options[selectOpt[0].options.length] = new Option(typeOpts[i].text, typeOpts[i].value);
    }
    if (this.values != null && this.values.opt != null && this.values.opt != "") {
        selectOpt.val(this.values.opt);
    }
    selectOpt.show();
 
    if (customValueFieldType == "string" || customValueFieldType == "number") 
    {
        inputValue.attr("id", "inputValue" + rowIndex);
        if (this.values != null && this.values.value != null && this.values.value != "") {
            inputValue.val(this.values.value);
        }
        inputValue.show();
        selectValue.hide();
        inputDate.hide();
    }
    else if (customValueFieldType == "list") 
    {
        selectValue.attr("id", "selectValue" + rowIndex);
        $("#" + customValueFieldSource).children("option").each(function ()
        {
            selectValue[0].options[selectValue[0].options.length] = new Option(this.text, this.value);
        });
        if (this.values != null && this.values.value != null && this.values.value != "") {
            selectValue.val(this.values.value);
        }
        selectValue.show();
        inputValue.hide();
        inputDate.hide();
    }
    else if (customValueFieldType == "date") 
    {
        inputDate.attr("id", "inputDate" + rowIndex);
        setDatePicker(inputDate);
        if (this.values != null && this.values.value != null && this.values.value != "") {
            inputDate.val(this.values.value);
        }
        inputDate.show();
        inputValue.hide();
        selectValue.hide();
    }
 
    this.row.find("a.addbtn").attr("id", "addbtn" + rowIndex);
    this.row.find("a.addbtn").hide();
    this.row.find("a.deletebtn").attr("id", "deletebtn" + rowIndex);
    this.row.find("a.deletebtn").show();
}

function STPR_resetRow()
{
	var rowIndex = this.getRowIndex();
    this.row.addClass(this.getRowCss());
    this.row.attr("id", "row" + rowIndex);
    this.row.find("select.control").attr("id", "control" + rowIndex);
    this.row.find("select.field").attr("id", "field" + rowIndex);
	if(this.field.keyField.suggest)
		this.row.find("div[id^='field'].suggest").attr("id", "field" + rowIndex + "_suggest");
    this.row.find("select.opt").attr("id", "opt" + rowIndex);
    this.row.find("input.inputValue").attr("id", "inputValue" + rowIndex);
    this.row.find("select.selectValue").attr("id", "selectValue" + rowIndex);
	if(this.field.valueField.suggest)
		this.row.find("div[id^='selectValue'].suggest").attr("id", "selectValue" + rowIndex + "_suggest");
    this.row.find("input.datepickerOther").attr("id", "inputDate" + rowIndex);
    this.row.find("a.addbtn").attr("id", "addbtn" + rowIndex);
    this.row.find("a.deletebtn").attr("id", "deletebtn" + rowIndex);
}

function STPR_removeRow()
{
	var newHtmlRows = new Array();
	for(var index in this.table.htmlRows)
	{
		var row = this.table.htmlRows[index];
		if(row != this.row)
			newHtmlRows[newHtmlRows.length] = row;	
	}
	this.table.htmlRows = newHtmlRows;
		
	this.row.remove();
}

function STPR_validateRow()
{
    var customKeyField = this.field.keyField.getField();
    var customValueField = this.field.valueField.getField();
	//验证连接条件
	if(checkList(this.row.find("select.control"),
			this.row.find("select.control").val(),
			"连接条件") == false)
		return false;

	//验证字段		
	if(checkList(this.row.find("select.field"),
			this.row.find("select.field").val(),
			customKeyField.text) == false)
		return false;

	//验证操作符		
	if(checkList(this.row.find("select.opt"),
			this.row.find("select.opt").val(),
			"操作符") == false)
		return false;

	if(customValueField.type == "string")
	{
		if(checkEmpty(this.row.find("input.inputValue"), 
			this.row.find("input.inputValue").val(),
			customValueField.text) == false)
			return false;
		if(checkSqlText(this.row.find("input.inputValue"), 
			this.row.find("input.inputValue").val(),
			customValueField.text) == false)
			return false;
	}
	else if(customValueField.type == "list")
	{
		return checkList(this.row.find("select.selectValue"),
			this.row.find("select.selectValue").val(),
			customValueField.text);
	}
	else if(customValueField.type == "date")
	{
		return checkDate(this.row.find("input.datepickerOther"),
			this.row.find("input.datepickerOther").val(),
			customValueField.text);
	}
	else if(customValueField.type == "number")
	{
		if(checkNumber(this.row.find("input.inputValue"), 
			this.row.find("input.inputValue").val(),
			customValueField.text) == false)
			return false;
	}	
}

function STPR_equals(obj)
{
    if (this == obj) {
        return true;
    }
    else {
        if (this.row.length != 0 && obj.length != 0 && this.row[0] == obj[0]) {
            return true;
        }
    }
}

function STPR_getSql()
{
	var customKeyField = this.field.keyField.getField();
	var customValueField = 	this.field.valueField.getField();
	var customKeyTable = this.field.keyField.getTable();
	var customValueTable = this.field.valueField.getTable();
	
	//连接符
	var controlValue = this.row.find("select.control").val();
	var controlText = this.row.find("select.control").find("option:selected").text();

	//操作符
	var optValue = this.row.find("select.opt").val();
	var optText = this.row.find("select.opt").find("option:selected").text();
	var optSql = getOptSql(this.row.find("select.opt").val(), customValueField.type);
	
	//输入值--字段
	var keyFieldInputValue = this.row.find("select.field").val();
	var keyFieldInputText = this.row.find("select.field").find("option:selected").text();
	var	keyFieldInputSql = getInputValueSql("list", "", keyFieldInputValue); //默认是list
	
	//输入值--值
	var inputValue = "";
	var inputText = "";
	if(customValueField.type == "string" || customValueField.type == "number")
	{
		inputValue = this.row.find("input.inputValue").val().trim();
		inputText = inputValue;
	}
	else if(customValueField.type == "list")
	{
		inputValue = this.row.find("select.selectValue").val();		
		inputText = this.row.find("select.selectValue").find("option:selected").text();		
	}
	else if(customValueField.type == "date")
	{
		inputValue = this.row.find("input.datepickerOther").val().trim();
		inputText = inputValue;
	}
	var	inputSql = getInputValueSql(customValueField.type, optValue, inputValue);

	//Key字段名称
	var keyFieldText = "";
	var keyFieldSql = "";
	if(customKeyTable.alias != null && customKeyTable.alias != "")
		keyFieldSql = customKeyTable.alias + "." + customKeyField.getName();
	else
	    keyFieldSql = customKeyField.getName();
	if(customKeyTable.text != null && customKeyTable.text != "")
		keyFieldText = customKeyTable.text + "." + customKeyField.text;
	else
	    keyFieldText = customKeyField.text;

	//Value字段名称
	var valueFieldText = "";
	var valueFieldSql = "";
	if(customValueTable.alias != null && customValueTable.alias != "")
		valueFieldSql = customValueTable.alias + "." + customValueField.getName({field: keyFieldInputText});
	else
	    valueFieldSql = customValueField.getName({field: keyFieldInputText});
	if(customValueTable.text != null && customValueTable.text != "")
		valueFieldText = customValueTable.text + "." + customValueField.text;
	else
	    valueFieldText = customValueField.text;
	
	var sqlText = "(" + keyFieldText + " " + "等于" + " " + keyFieldInputText + " and " + 
		valueFieldText + " " + optText + " " + inputText + ")";
	var sql = this.getCustomSql();
	if(sql == "")
		sql = "(" + keyFieldSql + " " + "in" + " " + keyFieldInputSql + " and " + 
			valueFieldSql + " " + optSql + " " + inputSql + ")";
	
	return new TableRowSql(customKeyTable.name, controlValue, sql, sqlText);	
	//return {table: customKeyTable.name, type: controlValue, sql: sql, sqlText: sqlText}; //默认是同一个表
}

function STPR_getCustomSql()
{
	return "";
}

function STPR_getValues()
{
	var controlValue = this.row.find("select.control").val();
	var fieldValue = this.row.find("select.field").val();
	var optValue = this.row.find("select.opt").val();

	var customValueField = 	this.field.valueField.getField();
	var inputValue = "";
	if(customValueField.type == "string" || customValueField.type == "number")
		inputValue = this.row.find("input.inputValue").val().trim();
	else if(customValueField.type == "list")
		inputValue = this.row.find("select.selectValue").val();		
	else if(customValueField.type == "date")
		inputValue = this.row.find("input.datepickerOther").val().trim();
	return new SearchTableRowValue(controlValue, fieldValue, optValue, inputValue);
}

function STPR_getRowIndex()
{
	for(var index in this.table.htmlRows)
	{
		var row = this.table.htmlRows[index];
		if(this.row == row)
			return index;
	}
}
