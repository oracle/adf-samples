function RichTextEditorProxy(a0,a1,a2,a3,a4)
{
this.formName=a0;
this.iframeBeanId=a1+"_IFRAME";
this.dataBeanId=a1;
this.isText=(a2=="T");
this.maxLength=a3;
this.browserFlag=a4;
this.isIE=(this.browserFlag=="IE_Win");
this.isGecko=(this.browserFlag=="Gecko");
this.isWebkit=(this.browserFlag=="Webkit");
this.maxLengthAlertMsgStr="";
var a5=RichTextEditorProxy.prototype.familyOfRTEs;
var a6=a5.toString();
if(a6.indexOf(''+this)==-1)
{
RichTextEditorProxy.prototype.familyOfRTEs
=RichTextEditorProxy.prototype.familyOfRTEs.concat(this);
}
}
RichTextEditorProxy.prototype.onMouseOver=_onMouseOver;
RichTextEditorProxy.prototype.onMouseOut=_onMouseOut;
RichTextEditorProxy.prototype.onMouseDown=_onMouseDown;
RichTextEditorProxy.prototype.onMouseUp=_onMouseUp;
RichTextEditorProxy.prototype.createHyperlink=_createHyperlink;
RichTextEditorProxy.prototype.execHTMLCommand=_execHTMLCommand;
RichTextEditorProxy.prototype.getField=_getField;
RichTextEditorProxy.prototype.setFontBarDropdown=_setFontBarDropdown;
RichTextEditorProxy.prototype.insertHTMLTag=_insertHTMLTag;
RichTextEditorProxy.prototype.setContentOnBlur=_setContentOnBlur;
RichTextEditorProxy.prototype.insertText=_insertText;
RichTextEditorProxy.prototype.insertImageTag=_insertImageTag;
RichTextEditorProxy.prototype.setValue=_setValue;
RichTextEditorProxy.prototype.setHref=_setHref;
RichTextEditorProxy.prototype.viewHtmlSource=_viewHtmlSource;
RichTextEditorProxy.prototype.disableFontDropDowns=_disableFontDropDowns;
RichTextEditorProxy.prototype.checkRTEDataLength=_checkRTEDataLength;
RichTextEditorProxy.prototype.validateContent=_validateContent;
RichTextEditorProxy.prototype.setMaxLengthAlert=_setMaxLengthAlert;
RichTextEditorProxy.prototype.getMaxLengthAlert=_getMaxLengthAlert;
RichTextEditorProxy.prototype.getContentWindow=_getContentWindow;
RichTextEditorProxy.prototype.familyOfRTEs=new Array();
RichTextEditorProxy.prototype.storeState=_storeState;
RichTextEditorProxy.prototype.toString=_toString
function _storeState()
{
var a0=document.getElementById(this.iframeBeanId);
var a1=document.getElementById(this.dataBeanId);
a1.value=_getData(a0.contentWindow);
}
function _toString()
{
return this.dataBeanId;
}
function storeIFrames()
{
var a0=RichTextEditorProxy.prototype.familyOfRTEs;
for(var a1 in a0){
var a2=a0[a1];
a2.storeState();
}
}
function _onMouseOver()
{
if(window.getSelection){
this.style.border="outset 2px";
}
else if(document.selection)
{
var a0=window.event;
var a1=getReal(a0.toElement,"className",this.className);
var a2=getReal(a0.fromElement,"className",this.className);
if(a1==a2)return;
var a3=a1;
var a4=a3.getAttribute("cDisabled");
a4=(a4!=null);
if(a3.className==this.className)
a3.onselectstart=new Function("return false");
if((a3.className==this.className)&&!a4)
{
makeRaised(a3);
}
}
}
function _onMouseOut()
{
if(window.getSelection)
{
this.style.border="solid 2px #C0C0C0";
}
else if(document.selection)
{
var a0=window.event;
var a1=getReal(a0.toElement,"className",this.className);
var a2=getReal(a0.fromElement,"className",this.className);
if(a1==a2)
return;
var a3=a2;
var a4=a3.getAttribute("PressedEx");
var a5=a3.getAttribute("cDisabled");
a5=(a5!=null);
var a6=a3.getAttribute("cToggle");
toggle_disabled=(a6!=null);
if(a6&&a3.value||a4==true)
{
makePressed(a3);
}
else if((a3.className==this.className)&&!a5)
{
makeFlat(a3);
}
}
}
function _onMouseDown(a0)
{
if(window.getSelection)
{
if(this.firstChild["style"])
{
this.firstChild.style.left=2;
this.firstChild.style.top=2;
}
this.style.border="inset 2px";
a0.preventDefault();
}
else if(document.selection)
{
el=getReal(window.event.srcElement,"className",this.className);
var a1=el.getAttribute("cDisabled");
a1=(a1!=null);
if((el.className==this.className)&&!a1)
{
makePressed(el);
}
}
}
function _onMouseUp()
{
if(window.getSelection)
{
if(this.firstChild["style"])
{
this.firstChild.style.left=1;
this.firstChild.style.top=1;
}
this.style.border="outset 2px";
}
else if(document.selection)
{
el=getReal(window.event.srcElement,"className",this.className);
var a0=el.getAttribute("cDisabled");
a0=(a0!=null);
if((el.className==this.className)&&!a0)
{
makeRaised(el);
}
}
}
function getReal(el,type,value)
{
temp=el;
while((temp!=null)&&(temp.tagName!="BODY"))
{
if(eval("temp."+type)==value)
{
el=temp;
return el;
}
temp=temp.parentElement;
}
return el;
}
function makeFlat(a0)
{
with(a0.style)
{
background="";
border="0px solid";
padding="2px";
}
}
function makeRaised(a0)
{
with(a0.style)
{
borderLeft="1px solid #ffffff";
borderRight="1px solid #555533";
borderTop="1px solid #ffffff";
borderBottom="1px solid #555533";
padding="1px";
}
}
function makePressed(a0)
{
with(a0.style)
{
borderLeft="1px solid #555533";
borderRight="1px solid #ffffff";
borderTop="1px solid #555533";
borderBottom="1px solid #ffffff";
paddingTop="2px";
paddingLeft="2px";
paddingBottom="0px";
paddingRight="0px";
}
}
function _createHyperlink(a0)
{
if(this.isText)
return;
var a1=this.getContentWindow(document);
if(this.isGecko||this.isWebkit)
{
var a2=prompt(a0,anchor?anchor.href:"http://");
if((a2!=null)&&(a2!="")&&a2!="http://")
{
a1.document.execCommand("CreateLink",false,a2);
}
}
else
{
a1.focus();
var a3=a1.document.selection;
var a4=a3.type;
var a5;
if(a4=="Control")
{
a5=a3.createRange().item(0);
}
else
{
a5=a3.createRange().parentElement();
}
var a6=getNode("A",a5);
var a2=prompt(a0,a6?a6.href:"http://");
if(a2&&a2!="http://")
{
if(a4=="None")
{
var a7=a3.createRange();
a7.pasteHTML('<A HREF="'+a2+'"></A>');
a7.select();
}
else
{
this.execHTMLCommand("CreateLink",a2);
}
}
}
}
function getNode(a0,a1)
{
while(a1&&a1.tagName!=a0)
{
a1=a1.parentElement;
}
return a1;
}
function _execHTMLCommand(a0)
{
if(this.isText)
return;
var a1=this.getContentWindow(document);
var a2=a1.document;
if(this.isGecko)
{
a2.execCommand('useCSS',false,true);
}
a1.focus();
a2.execCommand(a0,false,arguments[1]);
a1.focus();
}
function _setFontBarDropdown(a0,a1)
{
var a2=this.getField(a0);
this.execHTMLCommand(a1,a2.options[a2.selectedIndex].value);
}
function _insertHTMLTag(a0)
{
if(this.isText)
return;
var a1=this.getContentWindow(document);
if(this.isIE)
{
a1.focus();
var a2=a1.document.selection.createRange();
a2.pasteHTML(a0);
a2.select();
a1.focus();
}
else if(this.isGecko||this.isWebkit)
{
var a3=a1.getSelection();
var a2=a3.getRangeAt(0);
a3.removeAllRanges();
a2.deleteContents();
var a4=a2.startContainer;
var a5=a2.startOffset;
a2=document.createRange();
var a6=a4;
a4=a6.parentNode;
var a7=a6.nodeValue;
var a8=a7.substr(0,a5);
var a9=a7.substr(a5);
var a10=document.createTextNode(a8);
var a11=document.createTextNode(a9);
a4.insertBefore(a11,a6);
var a12=document.createElement(a0);
a4.insertBefore(a12,a11);
a4.insertBefore(a10,a12);
a4.removeChild(a6);
}
}
function _insertText(a0)
{
if(!this.isText)
{
if(this.isIE)
{
var a1=this.getContentWindow(document);
a1.focus();
var a2=a1.document.selection.createRange();
a2.text=a0;
a2.select();
a1.focus();
}
else if(this.isGecko||this.isWebkit)
{
var a1=this.getContentWindow(document);
var a3=a1.getSelection();
var a2=a3.getRangeAt(0);
a3.removeAllRanges();
a2.deleteContents();
var a4=a2.startContainer;
var a5=a2.startOffset;
a2=document.createRange();
a4.insertData(a5,a0);
a2.setEnd(a4,a5+a0.length);
a2.setStart(a4,a5+a0.length);
a3.addRange(a2);
}
else
{
var a6=this.getField(this.dataBeanId);
a6.value=a6.value+" "+a0;
}
}
else
{
var a6=this.getField(this.dataBeanId);
a6.value=a6.value+" "+a0;
}
}
function _getField(p_strObjName)
{
var obj=eval("document.forms[this.formName].elements['"+p_strObjName+"']");
return obj;
}
function _validateContent()
{
this.setContentOnBlur();
return this.checkRTEDataLength();
}
function _setContentOnBlur()
{
var a0=document.forms[this.formName].elements;
for(i=0;i<a0.length;i++)
{
var a1=a0[i].name;
if(a1==this.dataBeanId)
{
var a2=a0[i];
var a3=this.getContentWindow(document);
if(a3!=undefined)
{
a2.value=_getData(a3);
}
}
}
return true;
}
function _insertImageTag(a0)
{
if(this.isText)
return;
var a1=this.getContentWindow(document);
var a2=a1.document;
if(this.isIE)
{
a1.focus();
var a3=a2.selection;
var a4=a3.createRange().parentElement();
var a5=getNode("IMG",a4);
if(a0&&a0!="http://")
{
if(a3.type=="None")
{
var a6=a3.createRange();
a6.pasteHTML('<IMG SRC="'+a0+'">');
a6.select();
}
else
{
this.execHTMLCommand("insertImage",a0);
}
}
}
else if(this.isGecko||this.isWebkit)
{
if((a0!=null)
&&(a0!="")
&&a0!="http://")
{
a2.execCommand('InsertImage',false,a0);
}
}
}
function _setValue(a0)
{
if(this.isGecko||this.isWebkit||this.isIE)
{
_setData(this.getContentWindow(document),a0)
}
else
{
var a1=this.getField(this.dataBeanId);
a1.value=a0;
}
}
function _setHref(a0)
{
if(this.isText)
return;
var a1=this.getContentWindow(document);
if(this.isIE)
{
a1.focus();
var a2=a1.document.selection;
if(a2.type=="None")
{
var a3=a2.createRange();
a3.pasteHTML('<A HREF="'+a0+'"></A>');
a3.select();
}
else
{
this.execHTMLCommand("CreateLink",a0);
}
}
else if(this.isGecko||this.isWebkit)
{
a1.document.execCommand("CreateLink",false,a0);
}
}
function _viewHtmlSource(a0)
{
var a1=this.getContentWindow(document);
var a2=a1.document.body;
this.disableFontDropDowns(a0);
this.isText=a0;
if(a0)
{
if(this.isIE)
{
a2.innerText=a2.innerHTML;
}
else
{
var a3=document.createTextNode(a2.innerHTML);
a2.innerHTML="";
a2.appendChild(a3);
}
}
else
{
if(this.isIE)
{
a2.innerHTML=a2.innerText;
}
else
{
var a3=a2.ownerDocument.createRange();
a3.selectNodeContents(a2);
a2.innerHTML=a3.toString();
}
}
a1.focus();
}
function _disableFontDropDowns(a0)
{
var a1=this.dataBeanId;
var a2=this.getField(a1+'fontFamily');
if(a2!=null)
a2.disabled=a0;
var a3=this.getField(a1+'fontColor');
if(a3!=null)
a3.disabled=a0;
var a4=this.getField(a1+'fontWeight');
if(a4!=null)
a4.disabled=a0;
}
function _checkRTEDataLength()
{
if(this.maxLength==-1)
return true;
data=_getData(this.getContentWindow(document));
if(data.length>this.maxLength)
{
return false;
}
return true;
}
function _setMaxLengthAlert(a0)
{
this.maxLengthAlertMsgStr=a0;
}
function _getMaxLengthAlert()
{
return this.maxLengthAlertMsgStr;
}
function _getContentWindow(a0)
{
return a0.getElementById(this.iframeBeanId).contentWindow;
}
function _setData(a0,a1)
{
var a2=a0.document.body;
a0.focus();
if(this.isText)
a2.innerText=a1;
else
a2.innerHTML=a1;
a0.focus();
}
function _getData(a0)
{
var a1=a0.document.body;
if(this.isText)
return a1.innerText;
return a1.innerHTML;
}
function GraphView(a0,a1,a2,
a3,
a4,a5,
a6)
{
this.source=a0;
this.imageID=a1;
this.graphBounds=a3;
this.viewClip=a6;
this.formName=a2;
this.thumbWidth=a4;
this.thumbHeight=a5;
}
GraphView.prototype.submit=_submit;
GraphView.prototype.move=_move;
GraphView.prototype.moveRel=_moveRel;
GraphView.prototype.thumbClick=_thumbClick;
GraphView.prototype.nodeClick=_nodeClick;
GraphView.prototype.canvasClick=_canvasClick;
function _canvasClick(a0)
{
var a1=document.getElementById(this.imageID);
var a2=_getClickXY(a1,a0);
var a3=this.viewClip;
var a4=new Object();
a4.event="canvas";
a4.x=a2.x+a3.x;
a4.y=a2.y+a3.y;
this.submit(a4);
}
function _nodeClick(a0,a1)
{
var a2=new Object();
a2.event="click";
a2.part=a1;
a2.partID=a0;
this.submit(a2);
}
function _moveRel(a0,a1)
{
var a2=this.viewClip;
var a3=(a2.x+a2.width/2)+(a2.width*a0/2);
var a4=(a2.y+a2.height/2)+(a2.height*a1/2);
this.move(a3,a4);
}
function _move(a0,a1)
{
var a2=new Object();
a2.event="pan";
a2.x=a0;
a2.y=a1;
this.submit(a2);
}
function _submit(a0)
{
a0.source=this.source;
submitForm(this.formName,0,a0);
}
function _thumbClick(a0,a1)
{
var a2=_getClickXY(a0,a1);
var a3=a2.x;
var a4=a2.y;
var a5=this.graphBounds;
var a6=(a3*a5.width/this.thumbWidth)+a5.x;
var a7=(a4*a5.height/this.thumbHeight)+a5.y;
this.move(Math.round(a6),Math.round(a7));
}
function _getClickXY(a0,a1)
{
var a2=_getLocation(a0);
a2.x=a1.clientX-a2.x;
a2.y=a1.clientY-a2.y;
return a2;
}
function _getLocation(a0)
{
var a1=a0;
var a2=0;
var a3=0;
while(a1.tagName!="BODY"){
a2+=a1.offsetLeft;
a3+=a1.offsetTop;
a1=a1.offsetParent;
}
return{x:a2,y:a3};
}
function _getColorFieldFormat(
a0)
{
var a1=a0.name;
if(a1&&_cfs)
{
var a2=_cfs[a1];
var a3=_cfts[a1];
if(a2||a3)
return new RGBColorFormat(a2,a3);
}
return new RGBColorFormat();
}
function _fixCFF(
a0)
{
var a1=_getColorFieldFormat(a0);
if(a0.value!="")
{
var a2=a1.parse(a0.value);
if(a2!=(void 0))
a0.value=a1.format(a2);
}
}
function _wmlPatternFormat(
a0
)
{
return a0;
}
function _wmlPatternParse(
a0
)
{
var a1=this._pattern;
var a2=a1.length;
var a3=a0.length;
var a4="";
var a5=0;
var a6=false;
var a7=1;
var a8=1;
for(var a9=0;a9<a2;a9++)
{
var a10=a1.charAt(a9);
if(a10!="\\")
{
if(a6)
{
if(a0.charAt(a5)!=a10)
{
return(void 0);
}
else
{
a6=false;
a7=1;
a8=1;
a5++;
}
}
else
{
if(a10=="*")
{
a7=0;
a8=Infinity;
}
else
{
if(isDigit(a10))
{
a7=0;
a8=parseDigit(a10);
}
else
{
var a11=a5+a7;
if(a11>a3)
return(void 0);
while(a5<a11)
{
var a12=a0.charAt(a5);
if(!_testWMLChar(a10,a12))
{
return(void 0);
}
a4+=a12;
a5++;
}
var a13=Math.min(a3,
a11+(a8-
a7));
while(a5<a13)
{
var a12=a0.charAt(a5);
if(!_testWMLChar(a10,
a0.charAt(a5)))
{
break;
}
a4+=a12;
a5++;
}
a7=1;
a8=1;
}
}
}
}
else
{
a6=true;
}
}
if(a5==a3)
{
return a4;
}
else
{
return(void 0);
}
}
function _testWMLChar(
a0,
a1
)
{
if(a0=="A")
{
return!isDigit(a1)&&isNotLowerCase(a1);
}
else if(a0=="a")
{
return!isDigit(a1)&&isNotUpperCase(a1);
}
else if(a0=="N")
{
return isDigit(a1);
}
else if(a0=="X")
{
return isUpperCase(a1);
}
else if(a0=="x")
{
return isLowerCase(a1);
}
else if(a0=="M")
{
return true;
}
else if(a0=="m")
{
return true;
}
else
{
return false;
}
}
function WMLPatternFormat(
a0
)
{
this._class="WMLPatternFormat";
this._pattern=a0;
}
WMLPatternFormat.prototype=new Format();
WMLPatternFormat.prototype.format=_wmlPatternFormat;
WMLPatternFormat.prototype.parse=_wmlPatternParse;
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
var _cfTrans;
function _rgbColorFormat(
a0)
{
if(a0.alpha==0)
{
if(this._allowsTransparent)
return _cfTrans;
else
return(void 0);
}
var a1=new Object();
a1.value="";
var a2=this._pattern;
if(typeof a2!="string")
a2=a2[0];
_cfoDoClumping(a2,
_cfoSubformat,
a0,
a1);
return a1.value;
}
function _rgbColorParse(
a0)
{
if(this._allowsTransparent&&_cfTrans==a0)
return new Color(0,0,0,0);
var a1=this._pattern;
if(typeof a1=="string")
{
return _rgbColorParseImpl(a0,
a1);
}
else
{
var a2;
for(a2=0;a2<a1.length;a2++)
{
var a3=_rgbColorParseImpl(a0,
a1[a2]);
if(a3!=(void 0))
return a3;
}
}
}
function _rgbColorParseImpl(
a0,
a1)
{
var a2=new Object();
a2.currIndex=0;
a2.parseString=a0;
a2.parseException=new ParseException();
var a3=new Color(0x00,0x00,0x00);
if(_cfoDoClumping(a1,
_cfoSubParse,
a2,
a3))
{
if(a0.length!=a2.currIndex)
{
return(void 0);
}
return a3;
}
else
{
return(void 0);
}
}
function _cfoDoClumping(
a0,
a1,
a2,
a3
)
{
var a4=a0.length;
var a5=false;
var a6=0;
var a7=void 0;
var a8=0;
for(var a9=0;a9<a4;a9++)
{
var a10=a0.charAt(a9);
if(a5)
{
if(a10=="\'")
{
a5=false;
if(a6!=1)
{
a8++;
a6--;
}
if(!a1(a0,
"\'",
a8,
a6,
a2,
a3))
{
return false;
}
a6=0;
a7=void 0;
}
else
{
a6++;
}
}
else
{
if(a10!=a7)
{
if(a6!=0)
{
if(!a1(a0,
a7,
a8,
a6,
a2,
a3))
{
return false;
}
a6=0;
a7=void 0;
}
if(a10=='\'')
{
a5=true;
}
a8=a9;
a7=a10;
}
a6++;
}
}
if(a6!=0)
{
if(!a1(a0,
a7,
a8,
a6,
a2,
a3))
{
return false;
}
}
return true;
}
function _cfoSubformat(
a0,
a1,
a2,
a3,
a4,
a5
)
{
var a6=null;
if((a1>='A')&&(a1<='Z')||
(a1>='a')&&(a1<='z'))
{
switch(a1)
{
case'r':
a6=_cfoGetPaddedNumber(a4.red,a3,3,10);
break;
case'g':
a6=_cfoGetPaddedNumber(a4.green,a3,3,10);
break;
case'b':
a6=_cfoGetPaddedNumber(a4.blue,a3,3,10);
break;
case'a':
a6=_cfoGetPaddedNumber(a4.alpha,a3,3,10);
break;
case'R':
a6=
_cfoGetPaddedNumber(a4.red,a3,2,16).toUpperCase();
break;
case'G':
a6=
_cfoGetPaddedNumber(a4.green,a3,2,16).toUpperCase();
break;
case'B':
a6=
_cfoGetPaddedNumber(a4.blue,a3,2,16).toUpperCase();
break;
case'A':
a6=
_cfoGetPaddedNumber(a4.alpha,a3,2,16).toUpperCase();
break;
default:
a6="";
}
}
else
{
a6=a0.substring(a2,a2+a3);
}
a5.value+=a6;
return true;
}
function _cfoSubParse(
a0,
a1,
a2,
a3,
a4,
a5
)
{
var a6=a4.currIndex;
if((a1>='A')&&(a1<='Z')||
(a1>='a')&&(a1<='z'))
{
switch(a1)
{
case'r':
a5.red=_cfoAccumulateNumber(a4,a3,3,10);
if(a5.red==(void 0))
{
return false;
}
break;
case'g':
a5.green=_cfoAccumulateNumber(a4,a3,3,10);
if(a5.green==(void 0))
{
return false;
}
break;
case'b':
a5.blue=_cfoAccumulateNumber(a4,a3,3,10);
if(a5.blue==(void 0))
{
return false;
}
break;
case'a':
a5.alpha=_cfoAccumulateNumber(a4,a3,3,10);
if(a5.alpha==(void 0))
{
return false;
}
break;
case'R':
a5.red=_cfoAccumulateNumber(a4,a3,2,16);
if(a5.red==(void 0))
{
return false;
}
break;
case'G':
a5.green=_cfoAccumulateNumber(a4,a3,2,16);
if(a5.green==(void 0))
{
return false;
}
break;
case'B':
a5.blue=_cfoAccumulateNumber(a4,a3,2,16);
if(a5.blue==(void 0))
{
return false;
}
break;
case'A':
a5.alpha=_cfoAccumulateNumber(a4,a3,2,16);
if(a5.alpha==(void 0))
{
return false;
}
break;
default:
}
}
else
{
return _cfoMatchText(a4,
a0.substring(a2,a2+a3));
}
return true;
}
function _cfoMatchText(
a0,
a1
)
{
if(!a1)
return false;
var a2=a1.length;
var a3=a0.currIndex;
var a4=a0.parseString;
if(a2>a4.length-a3)
{
return false;
}
var a5=a4.substring(a3,a3+a2);
if(a5!=a1)
return false;
a0.currIndex+=a2;
return true;
}
function _cfoAccumulateNumber(
a0,
a1,
a2,
a3)
{
var a4=a0.currIndex;
var a5=a4;
var a6=a0.parseString;
var a7=a6.length;
if(a7>a5+a2)
a7=a5+a2;
var a8=0;
while(a5<a7)
{
var a9=parseInt(a6.charAt(a5),a3);
if(!isNaN(a9))
{
a8*=a3;
a8+=a9;
a5++;
}
else
{
break;
}
}
if(a4!=a5&&
(a5-a4)>=a1)
{
a0.currIndex=a5;
return a8;
}
else
{
return(void 0);
}
}
function _cfoGetPaddedNumber(
a0,
a1,
a2,
a3)
{
var a4=a0.toString(a3);
if(a1!=(void 0))
{
var a5=a1-a4.length;
while(a5>0)
{
a4="0"+a4;
a5--;
}
}
if(a2!=(void 0))
{
var a6=a4.length-a2;
if(a6>0)
{
a4=a4.substring(a6,
a6+a2);
}
}
return a4;
}
function RGBColorFormat(
a0,
a1)
{
this._class="RGBColorFormat";
this._allowsTransparent=a1;
if(a0!=(void 0))
{
if(typeof(a0)=="string")
a0=[a0];
}
this._pattern=a0;
}
RGBColorFormat.prototype=new Format();
RGBColorFormat.prototype.format=_rgbColorFormat;
RGBColorFormat.prototype.parse=_rgbColorParse;
function Color(
a0,
a1,
a2,
a3)
{
this._class="Color";
if(a3==(void 0))
a3=0xff;
this.red=(a0&0xff);
this.green=(a1&0xff);
this.blue=(a2&0xff);
this.alpha=(a3&0xff);
}
function _Color_toString()
{
return"rgba("+this.red+
","+this.green+
","+this.blue+
","+this.alpha+")";
}
Color.prototype.toString=_Color_toString;
var _cfBus=new Object();
var _cfTransIconURL;
var _cfOpaqueIconURL;
var _cfBgColor;
function _cfsw(
a0)
{
var a1=_getColorFieldFormat(a0);
var a2=a0.name+"$sw";
var a3=(void 0);
var a4=_getElementById(document,a2);
if(a4!=(void 0))
{
if(a0.value!="")
{
a3=a1.parse(a0.value);
}
if(a3!=(void 0))
{
if(a3.alpha==0)
{
a4.style.backgroundColor=(void 0);
a4.src=_cfTransIconURL;
a4.alt=_cfTrans;
}
else
{
a4.style.backgroundColor=
new RGBColorFormat("#RRGGBB").format(a3);
a4.src=_cfOpaqueIconURL;
a4.alt=a1.format(a3);
}
}
else
{
a4.style.backgroundColor=_cfBgColor;
a4.src=_cfOpaqueIconURL;
a4.alt=(void 0);
}
if(_agent.isGecko)
a4.title=a4.alt;
}
}
function _returnColorPickerValue(
a0,
a1
)
{
var a2=a0.returnValue;
var a3=a0._colorField;
if(a3==(void 0))
{
a3=_savedColorField1879034;
}
_cfUpdate(a3,a2);
}
function _cfbs(
a0)
{
_cfUpdate(_cfBus[a0.source],a0.params.value);
}
function _cfUpdate(
a0,
a1)
{
if(a0!=(void 0)&&a1!=(void 0))
{
var a2=_getColorFieldFormat(a0);
var a3=(a0.type!='hidden');
var a4=a0.value;
var a5=a2.format(a1);
if(a5!=(void 0)&&
a5!=a0.value)
{
if(a0.onchange!=(void 0))
{
if(_agent.isIE)
{
a0.onpropertychange=function()
{
var a6=window.event;
if(a6.propertyName=='value')
{
a0.onpropertychange=function(){};
_cfsw(a0);
a0.onchange(a6);
}
}
a0.value=a5;
}
else
{
a0.value=a5;
if(!_agent.isNav)
_cfsw(a0);
var a6=new Object();
a6.type='change';
a6.target=a0;
a0.onchange(a6);
}
}
else
{
a0.value=a5;
if(!_agent.isNav)
_cfsw(a0);
}
}
if(a3)
{
a0.select();
a0.focus();
}
}
}
function _lcp(
a0,
a1,
a2
)
{
var a3=document.forms[a0][a1];
if(!a2)
{
a2=_jspDir+_getQuerySeparator(_jspDir)+"_t=fred&_red=cp";
}
else
{
var a4=a2.lastIndexOf('?');
var a5="";
if(a4==-1)
{
a4=a2.length;
}
else
{
a5=a2.substr(a4+1);
}
var a6=_jspDir+_getQuerySeparator(_jspDir);
a6+=a5;
a6+=_getQuerySeparator(a6);
a6+="_t=fred";
var a7=a2.substring(0,a4);
a2=a6;
a2+="&redirect="+escape(a7);
}
if(a3.value!="")
{
var a8=_getColorFieldFormat(a3);
var a9=a8.parse(a3.value);
if(a9!=(void 0))
{
a2+="&value=";
if(a9.alpha==0)
a2+=escape("#trans");
else
a2+=escape(new RGBColorFormat("#RRGGBB").format(a9));
}
}
var a10=_cfs[a1];
if(a10!=(void 0))
{
var a11=(typeof a10=="string")?a10:a10[0];
a2+="&pattern="+escape(a11);
}
var a12=_cfts[a1];
if(a12!=(void 0))
{
a2+="&allowsTransparent="+a12;
}
if(_configName.length>0)
{
a2+="&configName="+escape(_configName);
}
a2+="&loc="+_locale;
if(window["_enc"])
{
a2+="&enc="+_enc;
}
if(window["_contextURI"])
{
a2+="&contextURI="+escape(_contextURI);
}
var a13=openWindow(self,
a2,
'colorDialog',
{width:430,height:230},
true,
void 0,
_returnColorPickerValue);
a13._colorField=a3;
_savedColorField1879034=a3;
}
var _savedColorField1879034;
