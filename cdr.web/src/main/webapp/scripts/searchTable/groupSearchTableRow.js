function GroupSearchTableRow(table, field, values)
{
    this.type = ST_RowType.group;
    
    this.table = table;
    this.field = field;
    this.values = values;
    this.rows = new Array();
    this.getRowCss = GSTR_getRowCss;
    this.setRow = GSTR_setRow;
    this.resetRow = GSTR_resetRow;
    this.removeRow = GSTR_removeRow;
    this.validateRow = GSTR_validateRow;
    this.equals = GSTR_equals;
    this.getSql = GSTR_getSql;
    this.init = GSTR_init;

    this.init(options);
}

function GSTR_init(options)
{
    for (var index in this.field.fields) 
    {
        var field = this.field.fields[index];
        var row = new SearchTableRow(this.table, field);
        this.rows[this.rows.length] = row;
    }
    this.setRow();
}

function GSTR_setRow()
{
    var self = this;
    var groupLength = this.rows.length;
    for (var index in this.rows) 
    {
        var row = this.rows[index].row;
        //row.attr("class", "").addClass(self.getRowCss());
        if (index == 0) 
        {
            row.children("td:first").attr("rowspan", groupLength);
            row.children("td:first").html(this.field.text + "条件");
            row.children("td:first").css("vertical-align", "top").css("padding-top", "5px");
            row.children("td").eq(1).attr("rowspan", groupLength);
            row.children("td").eq(1).css("vertical-align", "top").css("padding-top", "3px");
            row.children("td:last").attr("rowspan", groupLength);
            row.children("td:last").css("vertical-align", "top").css("padding-top", "3px");
        }
        else 
        {
            row.children("td:first").remove();
            row.children("td:first").remove();
            row.children("td:last").remove();
        }
    }
}

function GSTR_getRowCss()
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

function GSTR_resetRow()
{
    for (var index in this.rows) {
        this.rows[index].resetRow();
    }
}

function GSTR_removeRow()
{
    for (var index in this.rows) {
        this.rows[index].removeRow();
    }
}

function GSTR_validateRow()
{
    for (var index in this.rows) {
        if (this.rows[index].validateRow() == false) {
            return false;
        }
    }
    return true;
}

function GSTR_equals(obj)
{
    if (this == obj) {
        return true;
    }
    else 
    {
        for (var index in this.rows) {
            var row = this.rows[index];
            if (row.equals(obj)) {
                return true;
            }
        }
        return false;
    }
}

function GSTR_getSql()
{
    var sqlText = "";
    var sql = "";
    for (var index in this.rows) 
    {
        var rowSql = this.rows[index].getSql();
        if (sqlText != "") {
            sqlText += " and ";
        }
        sqlText += rowSql.sqlText;
        if (sql != "") {
            sql += " and ";
        }
        sql += rowSql.sql;
    }
    var firstRowSql = this.rows[0].getSql();
    return new TableRowSql(firstRowSql.table, firstRowSql.type, sql, sqlText);
}
