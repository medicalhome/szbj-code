function CustomTable(text, name, alias)
{
    this.text = text;
    this.name = name;
    this.alias = alias;
}

function CustomTableField(name, text, type, source, table)
{
    this.name = name;
    this.text = text;
    this.type = type;
    this.source = source;
    this.table = table;
	this.getName = CTF_getName;
}

function CTF_getName(paras)
{
	return this.name;
}

function CustomBusinessField(name, text, field)
{
    this.type = ST_FieldType.single;

    this.name = name;
    this.text = text;
    this.field = field;
	this.suggest = false;

    this.getTable = CBF_getTable;
    this.getField = CBF_getField;
}

function CBF_getTable()
{
    if (this.field == null || this.field.table == null) {
        return new CustomTable("", "", "");
    }
    else {
        return this.field.table;
    }
}

function CBF_getField()
{
    if (this.field == null) {
        return new CustomTableField("", "", "", "", new CustomTable("", "", ""));
    }
    else {
        return this.field;
    }
}

function CustomBusinessFieldPair(name, text, keyField, valueField)
{
    this.type = ST_FieldType.pair;

    this.name = name;
    this.text = text;
    this.keyField = keyField;
    this.valueField = valueField;
    this.getTable = CBFP_getTable;
}

function CBFP_getTable()
{
    return this.keyField.getTable();
}

function CustomGroupBusinessField(name, text, fields)
{
    this.name = name;
    this.text = text;
    this.fields = fields;
    this.type = ST_FieldType.group;
}

function CustomBusinessFields()
{
    this.fields = new Array();
    // include CustomGroupBField && CustomBField
    this.tables = new Array();
    this.getField = CBFS_getField;
    this.isGroupField = CBFS_isGroupField;
    this.isPairField = CBFS_isPairField;
}

function CBFS_getField(name)
{
    for (var index in this.fields) {
        var field = this.fields[index];
        if (field.name == name) {
            return field;
        }
    }
}

function CBFS_isGroupField(name)
{
    for (var index in this.fields) 
    {
        var field = this.fields[index];
        if (field.name == name) {
            if (field.type == ST_FieldType.group) {
                return true;
            }
            else {
                return false;
            }
        }
    }
    return false;
}

function CBFS_isPairField(name)
{
    for (var index in this.fields) 
    {
        var field = this.fields[index];
        if (field.name == name) {
            if (field.type == ST_FieldType.pair) {
                return true;
            }
            else {
                return false;
            }
        }
    }
    return false;
}
