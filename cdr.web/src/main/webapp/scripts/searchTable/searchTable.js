function SearchTableConfig()
{
    this.dialect = "oracle";
    this.basePath = "searchTable/";
    this.version = "0.1";
}

var STConfig = new SearchTableConfig();

function SearchTable(tableId, fields, rowTemplateId)
{
    this.tableId = tableId;
    this.fields = fields;
	this.rowTemplateId = rowTemplateId;
    this.rows = new Array();
	this.htmlRows = new Array();
	
    this.addRow = ST_addRow;
    this.deleteRow = ST_deleteRow;
    this.findRow = ST_findRow;
    this.reset = ST_reset;
    this.validate = ST_validate;
    this.getSql = ST_getSql;
	this.saveCache = ST_saveCache;
}

function ST_addRow(name, values)
{
	var searchRow = null;
	var field = this.fields.getField(name);
	if(field != null)
	{
		if(this.fields.isGroupField(name))
			searchRow = new GroupSearchTableRow(this, field, values);
		else if(this.fields.isPairField(name))
			searchRow = new SearchTablePairRow(this, field, values);
		else
			searchRow = new SearchTableRow(this, field, values);
	}
	if(searchRow != null)
		this.rows[this.rows.length] = searchRow;
	
	return searchRow;
}

function ST_deleteRow(obj)
{
    var newRows = new Array();
    for (var index in this.rows) 
    {
        var row = this.rows[index];
        if (row.equals(obj)) {
            row.removeRow();
        }
        else {
            newRows[newRows.length++] = row;
        }
    }
    this.rows = newRows;
    this.reset();
}

function ST_findRow(row)
{
    for (var index in this.rows) {
        var item = this.rows[index];
        if (item.equals(row)) {
            return index;
        }
    }
    return - 1;
}

function ST_reset()
{
    for (var index in this.rows) {
        this.rows[index].resetRow();
    }
}

function ST_validate()
{
    for (var index in this.rows) {
        if (this.rows[index].validateRow() == false) {
            return false;
        }
    }
    return true;
}

function ST_getSql()
{
    var tableSqls = new Array();
    var tableRowSqls = new Array();
    var tables = this.fields.tables;
    for (var index in this.rows) {
        tableRowSqls[tableRowSqls.length] = this.rows[index].getSql();
    }
    for (var index in tables) 
    {
        var tableName = tables[index].name;
        tableSqls[tableSqls.length] = new TableSql(tableName);
    }
    for (var index in tableSqls) 
    {
        var tableSql = tableSqls[index];
        for (var i in tableRowSqls) 
        {
            var rowSql = tableRowSqls[i];
            if (rowSql.table == tableSql.table) 
            {
                if (rowSql.type == "or") 
                {
                    if (tableSql.orSql != "") {
                        tableSql.orSql += " or ";
                        tableSql.orSqlText += " or ";
                    }
                    tableSql.orSql += rowSql.sql;
                    tableSql.orSqlText += rowSql.sqlText;
                }
                else if (rowSql.type == "and") 
                {
                    if (tableSql.andSql != "") {
                        tableSql.andSql += " and ";
                        tableSql.andSqlText += " and ";
                    }
                    tableSql.andSql += rowSql.sql;
                    tableSql.andSqlText += rowSql.sqlText;
                }
            }
        }
    }
    return tableSqls;
}

function ST_saveCache()
{
	var tableValues = new Array();
	for(var index in this.rows)
	{
		var row = this.rows[index];
		var values = row.getValues();
		tableValues[tableValues.length] = new SearchTableValue(row.field.name, values);  
	}
	
	return tableValues;
}
