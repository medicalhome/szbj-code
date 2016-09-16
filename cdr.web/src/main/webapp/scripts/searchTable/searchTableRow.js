function SearchTableRow(table, field, values)
{
	//alert(table + "  " + field.getField().text + "  " +values);
    this.type = ST_RowType.single;

	this.table = table;
	this.field = field;
	
	this.values = values;

	this.row = null;
	this.getRowIndex = STR_getRowIndex;
	this.getRowCss = STR_getRowCss;
	
	this.init = STR_init;
	this.setRow = STR_setRow;
	this.resetRow = STR_resetRow;
	this.removeRow = STR_removeRow;
	this.validateRow = STR_validateRow;
	
	this.equals = STR_equals;
	this.getSql = STR_getSql;
	this.getCustomSql = STR_getCustomSql;
	this.getValues = STR_getValues;

	this.init();
}

function STR_init()
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

function STR_getRowCss()
{
	var index = this.table.findRow(this);
	if(index == -1)
	{
		var rowCount = this.table.rows.length;
		if(rowCount % 2 == 0)		
			return "even";
		else
			return "odd";
	}
	else
	{
		if(index % 2 == 0)		
			return "even";
		else
			return "odd";
	}	
}

//设置每一行的属性
function STR_setRow()
{
	var customField = this.field.getField();
	var rowIndex = this.getRowIndex();
	
	var selectControl = this.row.find("select.control");
	var selectField = this.row.find("select.field"); 
	var selectOpt = this.row.find("select.opt"); 
	var inputValue = this.row.find("input.inputValue");
	var selectValue = this.row.find("select.selectValue");
	var inputDate = this.row.find("input.datepickerOther");

	this.row.attr("id", "row" + rowIndex);

	this.row.attr("class", "");
	this.row.addClass(this.getRowCss());
	//this.row.children("td:first").css("text-align", "left");
	//this.row.children("td:first").css("padding-left", "2px");
	this.row.children("td:first").html(this.field.text + "条件");
		
	selectControl.attr("id","control" + rowIndex);  
	if(this.values != null && this.values.control != null && this.values.control != "")
		selectControl.val(this.values.control);
	selectControl.show();
	
	selectField.attr("id","field" + rowIndex);  
	selectField.hide();
	selectField.parent().css("text-align", "left");  
	selectField.after(customField.text)

	selectOpt.attr("id","opt" + rowIndex);  
	selectOpt[0].options.length = 0;
	var typeOpts = null;
	if(customField.type == "string")
		typeOpts = stringOpts;
	else if(customField.type == "list")
		typeOpts = listOpts;
	else if(customField.type == "number")
		typeOpts = numberOpts;
	else if(customField.type == "date")
		typeOpts = dateOpts;
	else
		typeOpts = new Array();

	for(var i = 0; i < typeOpts.length; i++)
	{
		selectOpt[0].options[selectOpt[0].options.length] = new Option(typeOpts[i].text, typeOpts[i].value); 				
	}						
	if(this.values != null && this.values.opt != null && this.values.opt != "")
		selectOpt.val(this.values.opt);
	selectOpt.show();
	
	if(customField.type == "string" || customField.type == "number")
	{
		inputValue.attr("id","inputValue" + rowIndex);  	
		if(this.values != null && this.values.value != null && this.values.value != "")
			inputValue.val(this.values.value);
		inputValue.show();
		selectValue.hide();
		inputDate.hide();
	}
	else if(customField.type == "list")
	{
		selectValue.attr("id","selectValue" + rowIndex);
		if(this.field.suggest)
		{
			$("#" + customField.source).children("option").each(function(){
				var newOption = new Option(this.text, this.value);
				$(newOption).attr("searchtext", $(this).attr("searchtext"));
				selectValue[0].options[selectValue[0].options.length] = newOption; 
			});  

			if(this.values != null && this.values.value != null && this.values.value != "")
				selectValue.val(this.values.value);

			selectValue.htmlSelectSuggest({
				width: selectValue.width(),
				pageSize: 5,
				maxHeight: "120"
			});
		}
		else
		{
			$("#" + customField.source).children("option").each(function(){
				selectValue[0].options[selectValue[0].options.length] = new Option(this.text, this.value); 
			});  
			if(this.values != null && this.values.value != null && this.values.value != "")
				selectValue.val(this.values.value);
			selectValue.show();
		}
		
		inputValue.hide();
		inputDate.hide();
	}
	else if(customField.type == "date")
	{
		inputDate.attr("id","inputDate" + rowIndex);  	
		setDatePicker(inputDate);		
		if(this.values != null && this.values.value != null && this.values.value != "")
			inputDate.val(this.values.value);
		inputDate.show();
		inputValue.hide();
		selectValue.hide();
	}	
	
	this.row.find("a.addbtn").attr("id","addbtn" + rowIndex);  
	this.row.find("a.addbtn").hide();  
	this.row.find("a.deletebtn").attr("id","deletebtn" + rowIndex);  
	this.row.find("a.deletebtn").show();  
}

function STR_resetRow()
{
	var rowIndex = this.getRowIndex();
		
	this.row.addClass(this.getRowCss());
	
	this.row.attr("id", "row" + rowIndex);
	this.row.find("select.control").attr("id","control" + rowIndex);  

	this.row.find("select.field").attr("id","field" + rowIndex);  
	
	this.row.find("select.opt").attr("id","opt" + rowIndex);  

	this.row.find("input.inputValue").attr("id","inputValue" + rowIndex);  
	this.row.find("select.selectValue").attr("id","selectValue" + rowIndex);  
	if(this.field.suggest)
		this.row.find("div.suggest").attr("id", "selectValue" + rowIndex + "_suggest");
		  
	this.row.find("input.datepickerOther").attr("id","inputDate" + rowIndex);  

	this.row.find("a.addbtn").attr("id","addbtn" + rowIndex);  
	this.row.find("a.deletebtn").attr("id","deletebtn" + rowIndex);  
}

function STR_removeRow()
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

function STR_validateRow()
{
	var customField = this.field.getField();

	//验证连接条件
	if(checkList(this.row.find("select.control"),
			this.row.find("select.control").val(),
			"连接条件") == false)
		return false;
	//验证操作符		
	if(checkList(this.row.find("select.opt"),
			this.row.find("select.opt").val(),
			"操作符") == false)
		return false;

	if(customField.type == "string")
	{
		if(checkEmpty(this.row.find("input.inputValue"), 
			this.row.find("input.inputValue").val(),
			customField.text) == false)
			return false;
		if(checkSqlText(this.row.find("input.inputValue"), 
			this.row.find("input.inputValue").val(),
			customField.text) == false)
			return false;
	}
	else if(customField.type == "list")
	{
		return checkList(this.row.find("select.selectValue"),
			this.row.find("select.selectValue").val(),
			customField.text);
	}
	else if(customField.type == "date")
	{
		return checkDate(this.row.find("input.datepickerOther"),
			this.row.find("input.datepickerOther").val(),
			customField.text);
	}
	else if(customField.type == "number")
	{
		if(checkNumber(this.row.find("input.inputValue"), 
			this.row.find("input.inputValue").val(),
			customField.text) == false)
			return false;
	}	
}

function STR_equals(obj)
{
	if(this == obj)
		return true;
	else
	{
		if(this.row.length != 0 && obj.length != 0 && this.row[0] == obj[0])
			return true;
	}
	
	return false;
}

function STR_getSql()
{
	var customField = this.field.getField();
	var customTable = this.field.getTable();

	//连接符
	var controlValue = this.row.find("select.control").val();
	var controlText = this.row.find("select.control").find("option:selected").text();

	//操作符
	var optValue = this.row.find("select.opt").val();
	var optText = this.row.find("select.opt").find("option:selected").text();
	var optSql = getOptSql(this.row.find("select.opt").val(), customField.type);
	
	//输入值
	var inputValue = "";
	var inputText = "";
	if(customField.type == "string" || customField.type == "number")
	{
		inputValue = this.row.find("input.inputValue").val().trim();
		inputText = inputValue;
	}
	else if(customField.type == "list")
	{
		inputValue = this.row.find("select.selectValue").val();		
		inputText = this.row.find("select.selectValue").find("option:selected").text();		
	}
	else if(customField.type == "date")
	{
		inputValue = this.row.find("input.datepickerOther").val().trim();
		inputText = inputValue;
	}
	var	inputSql = getInputValueSql(customField.type, optValue, inputValue);

	//字段名称
	var fieldText = "";
	var fieldSql = "";
	if(customTable.alias != null && customTable.alias != "")
		fieldSql = customTable.alias + "." + customField.getName();
	else
	    fieldSql = customField.getName();
	if(customTable.text != null && customTable.text != "")
		fieldText = customTable.text + "." + customField.text;
	else
	    fieldText = customField.text;
	
	var sqlText = fieldText + " " + optText + " " + inputText;
	var sql = this.getCustomSql();
	
	if(sql == "")
	{
		if(customField.type == "date" && optValue == "equal")
			sql = fieldSql + " >= " + inputSql.lowValue + " and " + fieldSql + " <= " + inputSql.highValue;
		else if(customField.type == "date" && optValue == "notEqual")
		{
			sql = fieldSql + " < " + inputSql.lowValue + " or " + fieldSql + " > " + inputSql.highValue;
			sql = "(" + sql + ")";	
		}
		else
			sql = fieldSql + " " + optSql + " " + inputSql;
	}
	
	return new TableRowSql(customTable.name, controlValue, sql, sqlText);
	//return {table: customTable.name, type: controlValue, sql: sql, sqlText: sqlText};
}

function STR_getCustomSql()
{
	return "";
}

function STR_getValues()
{
	var controlValue = this.row.find("select.control").val();
	var optValue = this.row.find("select.opt").val();

	var customField = this.field.getField();
	var inputValue = "";
	if(customField.type == "string" || customField.type == "number")
		inputValue = this.row.find("input.inputValue").val().trim();
	else if(customField.type == "list")
		inputValue = this.row.find("select.selectValue").val();			
	else if(customField.type == "date")
		inputValue = this.row.find("input.datepickerOther").val().trim();
	return new SearchTableRowValue(controlValue, "", optValue, inputValue);
}

function STR_getRowIndex()
{
	for(var index in this.table.htmlRows)
	{
		var row = this.table.htmlRows[index];
		if(this.row == row)
			return index;
	}
}