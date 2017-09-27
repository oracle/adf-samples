var _shuttle_no_name="You must supply the shuttle's name to create a proxy";
var _shuttle_no_form_name_provided="A form name must be provided";
var _shuttle_no_form_available="This shuttle is not in a form";
function ShuttleProxy(
a0,
a1
)
{
if(a0==(void 0))
{
alert(_shuttle_no_name);
this.shuttleName="";
this.formName="";
return;
}
this.shuttleName=a0;
this.formName="";
if(a1==(void 0))
{
var a2=document.forms.length;
var a3=a0+":leading";
for(var a4=0;a4<a2;a4++)
{
if(document.forms[a4][a3]!=(void 0))
{
this.formName=document.forms[a4].name;
break;
}
}
if(this.formName=="")
{
alert(shuttle_no_form_available);
return;
}
}
else
{
this.formName=a1;
}
}
ShuttleProxy.prototype.getItems=_getItems;
ShuttleProxy.prototype.getSelectedItems=_getSelectedItems;
ShuttleProxy.prototype.getItemCount=_getItemCount;
ShuttleProxy.prototype.getSelectedItemCount=_getSelectedItemCount;
ShuttleProxy.prototype.addItem=_addItem;
ShuttleProxy.prototype.deleteItemByValue=_deleteItemByValue;
ShuttleProxy.prototype.deleteSelectedItems=_deleteSelectedItems;
ShuttleProxy.prototype.move=_move;
ShuttleProxy.prototype.reorderList=_reorderList;
ShuttleProxy.prototype.reset=_reset;
function _remove(a0,a1,a2)
{
var a3=a0.length;
if(a2>a3)
return;
for(var a4=a1;a4<a3;a4++)
{
if(a4<a3-a2)
a0[a4]=a0[a4+a2];
else
a0[a4]=void 0;
}
a0.length=a3-a2;
}
function _displayDesc(
a0,
a1
)
{
if(a1==(void 0))
{
alert(_shuttle_no_form_name_provided);
return;
}
if(a1.length==0)
{
alert(shuttle_no_form_available);
return;
}
var a2=document.forms[a1].elements[a0+':desc'];
if(a2==void(0))
{
return;
}
var a3=_getDescArray(a0);
if(a3==(void 0)||a3.length==0)
{
return;
}
var a4=_getSelectedIndexes(a1,a0);
if(a4.length==0)
{
a2.value="";
_setSelected(a0,a4);
return;
}
var a5=_getSelectedDesc(a0,a3,a4);
a2.value=a5;
_setSelected(a0,a4);
}
function _getDescArray
(
a0
)
{
var a1=window[a0.replace(':','_')+'_desc'];
return a1;
}
function _getSelectedDesc
(
a0,
a1,
a2
)
{
var a3=_getSelectedArray(a0);
if(a2.length==1)
return a1[a2[0]];
if(a2.length-a3.length!=1)
return"";
for(var a4=0;a4<a2.length;a4++)
{
if(a4>=a3.length||a3[a4]!=a2[a4])
return a1[a2[a4]];
}
return"";
}
function _getSelectedArray
(
a0
)
{
var a1=window[a0.replace(':','_')+'_sel'];
return a1;
}
function _setSelected
(
a0,
a1
)
{
var a2=_getSelectedArray(a0);
if(a2!=(void 0))
{
var a3=a2.length;
_remove(a2,0,a3);
for(var a4=0;a4<a1.length;a4++)
{
a2[a4]=a1[a4];
}
}
}
function _addDescAtIndex
(
a0,
a1,
a2
)
{
if(a0!=(void 0))
{
var a3=a0.length;
for(var a4=a3-1;a4>=a2;a4--)
{
a0[a4+1]=a0[a4];
}
a0[a2]=a1;
a0.length=a3+1;
}
}
function _deleteDescAtIndex
(
a0,
a1
)
{
if(a0!=(void 0))
_remove(a0,a1,1);
}
function _deleteDescAtIndexes
(
a0,
a1
)
{
if(a0!=(void 0))
{
for(var a2=a1.length-1;a2>=0;a2--)
{
_remove(a0,a1[a2],1);
}
}
}
function _clearDescAreas(
a0,
a1,
a2
)
{
var a3=document.forms[a0].elements[a1+':desc'];
var a4=document.forms[a0].elements[a2+':desc'];
if(a3!=void(0))
{
a3.value="";
}
if(a4!=void(0))
{
a4.value="";
}
}
function _moveItems(
a0,
a1,
a2
)
{
if(a2==(void 0))
{
a2=_findFormNameContaining(a0);
}
if(a2.length==0)
{
alert(shuttle_no_form_available);
return;
}
var a3=document.forms[a2].elements[a0];
var a4=document.forms[a2].elements[a1];
if(a3==(void 0)||a4==(void 0))
return;
var a5=_getSelectedIndexes(a2,a0);
if(a5.length==0)
{
if(_shuttle_no_items_selected.length>0)
alert(_shuttle_no_items_selected);
return;
}
var a6=_getDescArray(a0);
var a7=_getDescArray(a1);
a4.selectedIndex=-1;
var a8=a4.length-1;
var a9=a4.options[a8].text;
for(var a10=0;a10<a5.length;a10++)
{
var a11=a3.options[a5[a10]].text;
var a12=a3.options[a5[a10]].value;
if(a10==0)
{
a4.options[a8].text=a11;
a4.options[a8].value=a12;
}
else
{
a4.options[a8]=new Option(a11,a12,false,false);
}
if(a7!=(void 0)&&a6!=(void 0))
a7[a8]=a6[a5[a10]];
a4.options[a8].selected=true;
a8++;
}
a4.options[a8]=new Option(a9,"",false,false);
a4.options[a8].selected=false;
for(var a10=a5.length-1;a10>=0;a10--)
{
if(a6!=(void 0))
_remove(a6,a5[a10],1);
a3.options[a5[a10]]=null;
}
a3.selectedIndex=-1;
_clearDescAreas(a2,a0);
_displayDesc(a1,a2);
_makeList(a2,a0);
_makeList(a2,a1);
_navDirty=true;
}
function _moveAllItems(
a0,
a1,
a2
)
{
if(a2==(void 0))
{
a2=_findFormNameContaining(a0);
}
var a3=document.forms[a2].elements[a0];
var a4=document.forms[a2].elements[a1];
var a5=
a4.options[document.forms[a2].elements[a1].length-1].text
var a6=a4.length-1;
var a7=_getDescArray(a0);
var a8=_getDescArray(a1);
if(a3.length>1)
{
var a9=a3.length
for(var a10=0;a10<a9-1;a10++)
{
var a11=a3.options[0].text;
var a12=a3.options[0].value;
a3.options[0]=null;
if(a10==0)
{
a4.options[a6].text=a11;
a4.options[a6].value=a12;
}
else
{
a4.options[a6]=new Option(a11,a12,false,false);
}
if(a8!=(void 0)&&a7!=(void 0))
a8[a6]=a7[a10];
a6++;
}
a4.options[a6]=new Option(a5,"",false,false);
a4.options[a6].selected=false;
if(a7!=(void 0))
{
var a13=a7.length;
_remove(a7,0,a13);
}
a3.selectedIndex=-1;
a4.selectedIndex=-1;
_clearDescAreas(a2,a0,a1);
_makeList(a2,a0);
_makeList(a2,a1);
_navDirty=true;
}
else if(_shuttle_no_items.length>0)
{
alert(_shuttle_no_items);
}
}
function _orderList(
a0,
a1,
a2
)
{
if(a2==(void 0))
{
a2=_findFormNameContaining(a1);
}
var a3=document.forms[a2].elements[a1];
var a4=_getSelectedIndexes(a2,a1);
if(a4.length==0)
{
if(_shuttle_no_items_selected.length>0)
alert(_shuttle_no_items_selected);
return;
}
var a5=_getDescArray(a1);
var a6=a4.length-1;
while(a6>=0)
{
var a7=a4[a6];
var a8=a7;
var a9=a6;
while((a9>0)&&((a4[a9]-
a4[a9-1])==1))
{
a9--;
a8--;
}
if(a0==0)
{
if(a8!=0)
{
var a10=a3.options[a8-1].text;
var a11=a3.options[a8-1].value;
if(a5!=(void 0))
var a12=a5[a8-1];
for(var a13=a8;a13<=a7;a13++)
{
a3.options[a13-1].text=a3.options[a13].text;
a3.options[a13-1].value=a3.options[a13].value;
a3.options[a13-1].selected=true;
if(a5!=(void 0))
a5[a13-1]=a5[a13];
}
a3.options[a7].text=a10;
a3.options[a7].value=a11;
a3.options[a7].selected=false;
if(a5!=(void 0))
a5[a7]=a12;
_navDirty=true;
}
}
else
{
if(a7!=a3.length-2)
{
var a10=a3.options[a7+1].text;
var a11=a3.options[a7+1].value;
if(a5!=(void 0))
var a12=a5[a7+1];
for(var a13=a7;a13>=a8;a13--)
{
a3.options[a13+1].text=a3.options[a13].text;
a3.options[a13+1].value=a3.options[a13].value;
a3.options[a13+1].selected=true;
if(a5!=(void 0))
a5[a13+1]=a5[a13];
}
a3.options[a8].text=a10;
a3.options[a8].value=a11;
a3.options[a8].selected=false;
if(a5!=(void 0))
a5[a8]=a12;
_navDirty=true;
}
}
a6=a9-1;
}
_displayDesc(a1,a2);
_makeList(a2,a1);
}
function _orderTopBottomList(
a0,
a1,
a2
)
{
if(a2==(void 0))
{
a2=_findFormNameContaining(a1);
}
var a3=document.forms[a2].elements[a1];
var a4=_getSelectedIndexes(a2,a1);
if(a4.length==0)
{
if(_shuttle_no_items_selected.length>0)
alert(_shuttle_no_items_selected);
return;
}
var a5=_getDescArray(a1);
var a6=new Array();
var a7=new Array();
var a8=new Array();
var a9=new Array();
var a10=0;
if(a0==0)
{
var a11=0;
var a10=0;
for(var a12=0;
a12<a4[a4.length-1];
a12++)
{
if(a12!=a4[a11])
{
a8[a10]=a3.options[a12].text;
a9[a10]=a3.options[a12].value;
if(a5!=(void 0))
a6[a10]=a5[a12];
a10++
}
else
{
if(a5!=(void 0))
a7[a11]=a5[a12];
a11++;
}
}
if(a5!=(void 0))
a7[a11]=a5[a12];
for(var a13=0;a13<a4.length;a13++)
{
a3.options[a13].text=a3.options[a4[a13]].text;
a3.options[a13].value=a3.options[a4[a13]].value;
a3.options[a13].selected=true;
if(a5!=(void 0))
a5[a13]=a7[a13];
}
for(var a14=0;a14<a8.length;a14++)
{
a3.options[a13].text=a8[a14];
a3.options[a13].value=a9[a14];
a3.options[a13].selected=false;
if(a5!=(void 0))
a5[a13]=a6[a14];
a13++
}
}
else
{
var a11=1;
var a10=0;
if(a5!=(void 0))
a7[0]=a5[a4[0]];
for(var a15=a4[0]+1;
a15<=a3.length-2;
a15++)
{
if((a11==a4.length)||
(a15!=a4[a11]))
{
a8[a10]=a3.options[a15].text;
a9[a10]=a3.options[a15].value;
if(a5!=(void 0))
a6[a10]=a5[a15];
a10++;
}
else
{
if(a5!=(void 0))
a7[a11]=a5[a15];
a11++;
}
}
var a14=a3.length-2;
for(var a13=a4.length-1;a13>=0;a13--)
{
a3.options[a14].text=a3.options[a4[a13]].text;
a3.options[a14].value=a3.options[a4[a13]].value;
a3.options[a14].selected=true;
if(a5!=(void 0))
a5[a14]=a7[a13];
a14--;
}
for(var a13=a8.length-1;a13>=0;a13--)
{
a3.options[a14].text=a8[a13];
a3.options[a14].value=a9[a13];
a3.options[a14].selected=false;
if(a5!=(void 0))
a5[a14]=a6[a13];
a14--
}
}
_displayDesc(a1,a2);
_makeList(a2,a1);
_navDirty=true;
}
function _getSelectedIndexes(
a0,
a1
)
{
var a2=document.forms[a0].elements[a1];
var a3=new Array();
var a4=0;
for(var a5=0;a5<a2.length-1;a5++)
{
if(a2.options[a5].selected)
{
a3[a4]=a5;
a4++;
}
}
return a3;
}
function _findFormNameContaining(
a0
)
{
var a1=document.forms.length;
for(var a2=0;a2<a1;a2++)
{
if(document.forms[a2][a0]!=(void 0))
{
return document.forms[a2].name;
}
}
return"";
}
function _makeList(
a0,
a1
)
{
var a2=document.forms[a0].elements[a1];
if(a2==null)
return;
var a3="";
for(var a4=0;a4<a2.length-1;a4++)
{
if(a2.options[a4].value.length>0)
{
a3=a3+
_trimString(a2.options[a4].value)
+';';
}
else
{
a3=a3+
_trimString(a2.options[a4].text)
+';';
}
}
document.forms[a0].elements[a1+':items'].value=a3;
}
function _trimString(
a0
)
{
var a1=a0.length-1;
if(a0.charAt(a1)!=' ')
{
return a0;
}
while((a0.charAt(a1)==' ')&&(a1>0))
{
a1=a1-1;
}
a0=a0.substring(0,a1+1);
return a0;
}
function _getListName(
a0,
a1
)
{
var a2=(a1)?a0+":leading":
a0+":trailing";
return a2;
}
function _resetItems(
a0,
a1)
{
if(a1==(void 0))
{
a1=_findFormNameContaining(from);
}
if(a1.length==0)
{
alert(shuttle_no_form_available);
return;
}
leadingListName=_getListName(a0,true);
trailingListName=_getListName(a0,false);
var a2=document.forms[a1].elements[leadingListName];
var a3=document.forms[a1].elements[trailingListName];
var a4=_getOriginalLists(a0,a1);
var a5=a4.leading;
var a6=a4.trailing;
var a7=_getDescArray(leadingListName);
var a8=_getDescArray(trailingListName);
_resetToOriginalList(a5,a7,a2);
_resetToOriginalList(a6,a8,a3);
_makeList(a1,leadingListName);
_makeList(a1,trailingListName);
return false;
}
function _getOriginalLists
(
a0,
a1
)
{
var a2=window['_'+a1+'_'+a0+'_orig'];
return a2;
}
function _resetToOriginalList
(
a0,
a1,
a2
)
{
if(a0==(void 0)||a2==(void 0))
return;
a2.selectedIndex=a0.selectedIndex;
var a3=0;
for(;a3<a0.options.length;a3++)
{
var a4=a0.options[a3].text;
var a5=a0.options[a3].value;
var a6=a0.options[a3].defaultSelected;
var a7=a0.options[a3].selected;
{
a2.options[a3]=new Option(a4,a5,
a6,a7);
a2.options[a3].defaultSelected=a6;
a2.options[a3].selected=a7;
}
if(a1!=(void 0))
a1[a3]=a0.descriptions[a3];
}
var a8=a2.options.length-1;
while(a8>=a3)
{
if(a1!=(void 0))
a1[a8]=null;
a2.options[a8]=null;
a8--;
}
}
function _copyLists(a0,a1)
{
if(a1==(void 0))
{
a1=_findFormNameContaining(from);
}
if(a1.length==0)
{
alert(shuttle_no_form_available);
return;
}
var a2=new Object();
a2.leading=_copyList(_getListName(a0,true),a1);
a2.trailing=_copyList(_getListName(a0,false),a1);
return a2;
}
function _copyList(a0,a1)
{
if(a1==(void 0)||a0==(void 0))
return;
var a2=document.forms[a1].elements[a0];
if(a2==null)
return;
var a3=_getDescArray(a0);
var a4=new Object();
a4.selectedIndex=a2.selectedIndex;
a4.options=new Array();
a4.descriptions=new Array();
for(var a5=0;a5<a2.options.length;a5++)
{
a4.options[a5]=new Option(a2.options[a5].text,
a2.options[a5].value,
a2.options[a5].defaultSelected,
a2.options[a5].selected);
a4.options[a5].defaultSelected=a2.options[a5].defaultSelected;
a4.options[a5].selected=a2.options[a5].selected;
if(a3!=null)
a4.descriptions[a5]=a3[a5];
}
return a4;
}
function _reset()
{
_resetItems(this.shuttleName,this.formName);
}
function _move(
a0,
a1
)
{
if(a1==(void 0))
{
a1=false;
}
if(a0==(void 0))
{
a0=true;
}
var a2=_getListName(this.shuttleName,a0);
var a3=_getListName(this.shuttleName,!a0);
if(a1)
{
_moveAllItems(a2,a3,this.formName);
}
else
{
_moveItems(a2,a3,this.formName);
}
}
function _reorderList(
a0,
a1,
a2
)
{
if(a2==(void 0))
{
a2=true;
}
if(a1==(void 0))
{
a1=false;
}
if(a0==(void 0))
{
a0=false;
}
var a3=_getListName(this.shuttleName,a2);
if(!a1)
{
_orderList(a0,a3,this.formName);
}
else
{
_orderTopBottomList(a0,a3,this.formName);
}
}
function _getItems(
a0
)
{
if(a0==(void 0))
{
a0=true;
}
var a1=_getListName(this.shuttleName,a0);
var a2=document.forms[this.formName].elements[a1];
var a3=new Array();
for(var a4=0;a4<a2.length-1;a4++)
{
a3[a4]=a2.options[a4];
}
return a3;
}
function _getSelectedItems(
a0
)
{
if(a0==(void 0))
{
a0=true;
}
var a1=_getListName(this.shuttleName,a0);
var a2=document.forms[this.formName].elements[a1];
var a3=new Array();
var a4=0;
for(var a5=0;a5<a2.length-1;a5++)
{
if(a2.options[a5].selected)
{
a3[a4]=a2.options[a5];
a4++;
}
}
return a3;
}
function _getItemCount(
a0
)
{
if(a0==(void 0))
{
a0=true;
}
var a1=_getListName(this.shuttleName,a0);
return document.forms[this.formName].elements[a1].length-1;
}
function _getSelectedItemCount(
a0
)
{
if(a0==(void 0))
{
a0=true;
}
var a1=_getListName(this.shuttleName,a0);
var a2=document.forms[this.formName].elements[a1];
var a3=0;
for(var a4=0;a4<a2.length-1;a4++)
{
if(a2.options[a4].selected)
{
a3++;
}
}
return a3;
}
function _addItem(
a0,
a1,
a2,
a3,
a4
)
{
if(a3==(void 0))
{
a3="";
}
if(a2==(void 0))
{
a2="";
}
if(a4==(void 0))
{
a4="";
}
if(a0==(void 0))
{
a0=true;
}
var a5=_getListName(this.shuttleName,a0);
if(a1==(void 0))
{
a1=document.forms[this.formName].elements[a5].length-1;
}
if(a1<0)
{
a1=0;
}
if(a1>document.forms[this.formName].elements[a5].length-1)
{
a1=document.forms[this.formName].elements[a5].length-1;
}
var a6=document.forms[this.formName].elements[a5];
a6.options[a6.length]=
new Option(a6.options[a6.length-1].text,
a6.options[a6.length-1].value,
false,
false);
for(var a7=a6.length-1;a7>a1;a7--)
{
a6.options[a7].text=a6.options[a7-1].text;
a6.options[a7].value=a6.options[a7-1].value;
a6.options[a7].selected=a6.options[a7-1].selected;
}
a6.options[a1].text=a2;
a6.options[a1].value=a3;
a6.options[a1].selected=false;
var a8=_getDescArray(a5);
_addDescAtIndex(a8,a4,a1);
_makeList(this.formName,a5);
_navDirty=true;
}
function _deleteItemByValue(
a0,
a1
)
{
if(a1==(void 0))
{
return;
}
var a2=_getListName(this.shuttleName,a0);
var a3=document.forms[this.formName].elements[a2];
for(var a4=0;a4<a3.length-1;a4++)
{
var a5=a3.options[a4].value;
if(a5==a1)
{
var a6=_getDescArray(a2);
_deleteDescAtIndex(a6,a4);
_clearDescAreas(this.formName,a2);
a3.options[a4]=null;
_makeList(this.formName,a2);
_navDirty=true;
return;
}
}
}
function _deleteSelectedItems(
a0
)
{
if(a0==(void 0))
{
a0=true;
}
var a1=_getListName(this.shuttleName,a0);
var a2=document.forms[this.formName].elements[a1];
var a3=_getSelectedIndexes(this.formName,a1);
for(var a4=a3.length;a4>=0;a4--)
{
a2.options[a3[a4]]=null;
}
var a5=_getDescArray(a1);
_deleteDescAtIndexes(a5,a3);
_clearDescAreas(this.formName,a1);
_makeList(this.formName,a1);
_navDirty=true;
}
