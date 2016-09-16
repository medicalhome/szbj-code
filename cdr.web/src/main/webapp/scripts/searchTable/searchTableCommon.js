var stringOpts = new Array(
	{value:"-1", text:"请选择" },
	{value:"equal", text:"等于" },
	{value:"notEqual", text:"不等于"},
	{value:"like", text:"类似于"}
);

var listOpts = new Array(
	{value:"-1", text:"请选择" },
	{value:"equal", text:"等于" },
	{value:"notEqual", text:"不等于"}
);

var numberOpts = new Array(
	{value:"-1", text:"请选择" },
	{value:"equal", text:"等于" },
	{value:"notEqual", text:"不等于"},
	{value:"more", text:"大于"},
	{value:"less", text:"小于"},
	{value:"moreEq", text:"大于等于"},
	{value:"lessEq", text:"小于等于"}
);

var dateOpts = new Array(
	{value:"-1", text:"请选择" },
	{value:"equal", text:"等于" },
	{value:"notEqual", text:"不等于"},
	{value:"more", text:"大于"},
	{value:"less", text:"小于"},
	{value:"moreEq", text:"大于等于"},
	{value:"lessEq", text:"小于等于"}
);

function setDatePicker(inputDate)
{
    inputDate.datepicker(
    {
        showMonthAfterYear : true, changeMonth : true, changeYear : true, buttonImageOnly : true, //maxDate: '+0',
        dateFormat : 'yy-mm-dd',
        onSelect : function (dateText, inst) 
        {
            $(this).val(dateText);
        }
    });
}

var ST_FieldType = {
    single : 1, group : 2, pair : 3
};

var ST_RowType = {
    single : 1, group : 2, pair : 3
};

function getOptSql(optValue, type)
{
    var str = "";
    if (type == "string")
    {
        //equal, notEqual, like

        {
            if (optValue == "equal") {
                str = "=";
            }
            else if (optValue == "notEqual") {
                str = "!=";
            }
            else if (optValue == "like") {
                str = "like";
            }
        }
    }
    else if (type == "list")
    {
        //equal, notEqual
        {
            if (optValue == "equal") {
                str = "in";
            }
            else if (optValue == "notEqual") {
                str = "not in";
            }
        }
    }
    else if (type == "date" || type == "number") 
    {
        if (optValue == "equal") {
            str = "=";
        }
        else if (optValue == "notEqual") {
            str = "!=";
        }
        else if (optValue == "more") {
            str = ">";
        }
        else if (optValue == "less") {
            str = "<";
        }
        else if (optValue == "moreEq") {
            str = ">=";
        }
        else if (optValue == "lessEq") {
            str = "<=";
        }
    }
    return str;
}

function getInputValueSql(type, optValue, valueText)
{
    var str = "";
    if (type == "string")
    {
        //like, equal, notEqual
        {
            if (optValue == "like") {
                str = "''%" + valueText + "%''";
            }
            else {
                str = "''" + valueText + "''";
            }
        }
    }
    else if (type == "list")
    {
        // equal, notEqual

        {
            var values = valueText.split(",");
            for (var index in values) {
                if (str != "") {
                    str += ",";
                }
                str += "''" + values[index] + "''";
            }
            str = "(" + str + ")";
        }
    }
    else if (type == "date") 
    {
        if (optValue == "equal" || optValue == "notEqual") 
        {
            var lowValue = "to_date(''" + valueText + " 00-00-00'', ''YYYY-MM-DD hh24:mi:ss'')";
            var highValue = "to_date(''" + valueText + " 23-59-59'', ''YYYY-MM-DD hh24:mi:ss'')";
            str = {
                lowValue : lowValue, highValue : highValue
            };
        }
        else if (optValue == "more") 
        {
            var highValue = "to_date(''" + valueText + " 23-59-59'', ''YYYY-MM-DD hh24:mi:ss'')";
            str = highValue;
        }
        else if (optValue == "moreEq") 
        {
            var lowValue = "to_date(''" + valueText + " 00-00-00'', ''YYYY-MM-DD hh24:mi:ss'')";
            str = lowValue;
        }
        else if (optValue == "less") 
        {
            var lowValue = "to_date(''" + valueText + " 00-00-00'', ''YYYY-MM-DD hh24:mi:ss'')";
            str = lowValue;
        }
        else if (optValue == "lessEq") 
        {
            var highValue = "to_date(''" + valueText + " 23-59-59'', ''YYYY-MM-DD hh24:mi:ss'')";
            str = highValue;
        }
    }
    else if (type == "number") {
        str = valueText;
    }
    return str;
}

function TableRowSql(table, type, sql, sqlText)
{
    this.table = table;
    this.type = type;
    this.sql = sql;
    this.sqlText = sqlText;
}

function TableSql(table)
{
    this.table = table;
    this.orSql = "";
    this.orSqlText = "";
    this.andSql = "";
    this.andSqlText = "";
    this.getSql = TS_getSql;
    this.getSqlText = TS_getSqlText;
}

function TS_getSql()
{
    var sql = "";
    if (this.orSql != "") {
        sql = "(" + this.orSql + ")";
    }
    if (sql != "" && this.andSql != "") {
        sql += " and ";
    }
    if (this.andSql != "") {
        sql += this.andSql;
    }
    return sql;
}

function TS_getSqlText()
{
    var sqlText = "";
    if (this.orSqlText != "") {
        sqlText = "(" + this.orSqlText + ")";
    }
    if (sqlText != "" && this.andSqlText != "") {
        sqlText += " and ";
    }
    if (this.andSqlText != "") {
        sqlText += this.andSqlText;
    }
    return sqlText;
}

function SearchTableRowValue(control, field, opt, value)
{
	this.control = control;
	this.field = field;
	this.opt = opt;
	this.value = value;
} 

function SearchTableValue(name, values)
{
	this.name = name;
	this.values = values;
}