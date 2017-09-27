function getTableName(
a0
)
{
var a1=a0.name;
if(a1==(void 0))
return;
var a2=a1.indexOf(":");
if(a2>=0)
return a1.substring(0,a2);
}
function getTableRow(
a0
)
{
var a1=a0.name;
if(a1==(void 0))
return;
var a2=a1.lastIndexOf(":");
if(a2>=0)
{
var a3=a1.substring(a2+1);
return Number(a3);
}
}
function getTableElementName(
a0
)
{
var a1=a0.name;
if(a1==(void 0))
return;
var a2=a1.indexOf(":");
if(a2>=0)
{
var a3=a1.lastIndexOf(":");
if(a3>=0)
{
return a1.substring((a2+1),a3);
}
}
}
function tableSelectAll(
a0
)
{
new TableProxy(a0).selectAll();
}
function tableSelectNone(
a0
)
{
var a1=new TableProxy(a0);
a1.selectNone();
}
function TableProxy(
a0,
a1,
a2
)
{
this.tableName=a0;
var a3=a0+":length";
var a4=document;
if(a2!=(void 0))
{
a4=a2.document;
}
if(a1!=(void 0))
{
this.formName=a1;
}
else
{
var a5=a4.forms.length;
for(var a6=0;a6<a5;a6++)
{
if(a4.forms[a6][a3]!=(void 0))
{
this.formName=a4.forms[a6].name;
break;
}
}
}
this._form=a4.forms[this.formName];
this.length=this._form[a3].value;
}
TableProxy.prototype.getValue=_getTableValue;
TableProxy.prototype.getLength=_getTableLength;
TableProxy.prototype.getSelectedRow=_getSelectedRow;
TableProxy.prototype.getSelectedRows=_getSelectedRows;
TableProxy.prototype.setSelectedRow=_setSelectedRow;
TableProxy.prototype.getFormElement=_getFormElement;
TableProxy.prototype.isMultiSelect=_isMulti;
TableProxy.prototype.selectAll=_selectAll;
TableProxy.prototype.getSelectAll=_getSelectAll;
TableProxy.prototype.selectNone=_selectNone;
TableProxy.prototype.getSelectNone=_getSelectNone;
TableProxy.prototype._multiSelect=_multiSelect;
function _hasSelection()
{
var a0=this._form;
var a1=this.tableName+":selected";
if(a0[a1]!=(void 0))
return true;
a1=this.tableName+":selectMode";
return(a0[a1]!=(void 0));
}
function _getTableValue()
{
var a0="_"+this.tableName+"_value";
if(window[a0]!=(void 0))
return window[a0]-1;
return 0;
}
function _getSelectedRow()
{
if(!(this._hasSelection()))
return-1;
var a0=this._form[this.tableName+":selected"];
if(a0==(void 0))
return-1;
else if(a0.length!=(void 0))
{
var a1;
for(var a2=0;a2<a0.length;a2++)
{
a1=a0[a2];
if((a1!=(void 0))&&a1.checked)
return a2;
}
}
else if(a0.checked)
{
return a0.value;
}
return-1;
}
function _getSelectedRows()
{
if(!(this._hasSelection()))
return-1;
var a0;
var a1=new Array();
var a2=0;
for(var a3=0;a3<this.length;a3++)
{
a0=this.tableName+":selected:"+a3;
a0=this._form[a0];
if((a0!=(void 0))&&a0.checked)
{
a1[a2]=a3;
a2++;
}
}
return a1;
}
function _getTableLength()
{
return this.length;
}
function _getFormElement(
a0,
a1
)
{
var a2=this.tableName+":"+a0+":"+a1;
return this._form[a2];
}
function _isMulti()
{
var a0=this.tableName+":selected:0";
a0=this._form[a0];
return(a0!=(void 0));
}
function _selectAll()
{
this._multiSelect(true);
}
function _getSelectAll()
{
if(!(this._hasSelection()))
return false;
var a0=this.tableName+":selectMode";
var a1=this._form[a0];
if(a1!=(void 0))
{
return a1.value=="all";
}
return false;
}
function _selectNone()
{
this._multiSelect(false);
}
function _getSelectNone()
{
if(!(this._hasSelection()))
return false;
var a0=this.tableName+":selectMode";
var a1=this._form[a0];
if(a1!=(void 0))
{
return a1.value=="none";
}
return false;
}
function _multiSelect(
a0
)
{
if(!(this._hasSelection()))
return;
var a1;
var a2=0;
for(var a3=0;a3<this.length;a3++)
{
a1=this.tableName+":selected:"+a3;
a1=this._form[a1];
if((a1!=(void 0))&&
(!a1.disabled))
{
a1.checked=a0;
}
}
var a4=this.tableName+":selectMode";
var a5=this._form[a4];
if(a5!=(void 0))
{
if(a0)
a5.value="all";
else
a5.value="none";
}
}
function _tableProxyToString()
{
var a0="TableProxy, tableName="+this.tableName+"\n";
a0+="form="+this.formName+"\n";
a0+="_hasSelection="+this._hasSelection()+"\n";
a0+="selectedRow="+this.getSelectedRow()+"\n";
a0+="selectedRows="+this.getSelectedRows()+"\n";
a0+="getLength="+this.getLength()+"\n";
a0+="selectMode=";
var a1=this.tableName+":selectMode";
var a2=this._form[a1];
if(a2!=null)
a0+=a2.value+"\n";
else
a0+="NULL\n";
return a0;
}
function _setSelectedRow(a0)
{
if(a0==(void 0))
return;
var a1=this._form[this.tableName+":selected"];
if(a1==(void 0))
{
a1=this._form[this.tableName+":selected:"+a0];
}
if(a1==(void 0))
{
return;
}
else if(a1.length!=(void 0))
{
for(var a2=0;a2<a1.length;a2++)
{
if((a1[a2]!=(void 0))&&(a1[a2].value==a0))
a1[a2].checked=true;
}
}
else if(a1.value==a0)
{
a1.checked=true;
}
return;
}
TableProxy.prototype._hasSelection=_hasSelection;
TableProxy.prototype.toString=_tableProxyToString;
