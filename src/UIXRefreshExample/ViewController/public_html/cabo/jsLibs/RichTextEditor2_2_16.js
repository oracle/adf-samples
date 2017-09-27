function RichTextEditorProxy(a0,a1,a2,a3,a4)
{
this.formName=a0;
this.beanId=a1+"_IFRAME";
this.dataBeanId=a1;
this.mode=a2;
this.maxLength=a3;
this.browserFlag=a4;
this.maxLengthAlertMsgStr="";
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
function _onMouseOver()
{
if(window.getSelection){
this.style.border="outset 2px";
}
else if(document.selection)
{
var a0=getReal(window.event.toElement,"className",this.className);
var a1=getReal(window.event.fromElement,"className",this.className);
if(a0==a1)return;
var a2=a0;
var a3=a2.getAttribute("cDisabled");
a3=(a3!=null);
if(a2.className==this.className)
a2.onselectstart=new Function("return false");
if((a2.className==this.className)&&!a3)
{
makeRaised(a2);
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
var a0=getReal(window.event.toElement,"className",this.className);
var a1=getReal(window.event.fromElement,"className",this.className);
if(a0==a1)
return;
var a2=a1;
var a3=a2.getAttribute("PressedEx");
var a4=a2.getAttribute("cDisabled");
a4=(a4!=null);
var a5=a2.getAttribute("cToggle");
toggle_disabled=(a5!=null);
if(a5&&a2.value||a3==true)
{
makePressed(a2);
}
else if((a2.className==this.className)&&!a4)
{
makeFlat(a2);
}
}
}
function _onMouseDown(a0)
{
if(window.getSelection)
{
this.firstChild.style.left=2;
this.firstChild.style.top=2;
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
this.firstChild.style.left=1;
this.firstChild.style.top=1;
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
if(this.mode=="TEXT_MODE")
return;
var a1=document.getElementById(this.beanId).contentWindow;
if(this.browserFlag=="Mozilla")
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
if(this.mode=="TEXT_MODE")
return;
var a1=document.getElementById(this.beanId).contentWindow;
var a2=a1.document;
if(this.browserFlag=="Mozilla")
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
if(this.mode=="TEXT_MODE")
return;
var a1=document.getElementById(this.beanId).contentWindow;
if(this.browserFlag=="IE_Win")
{
a1.focus();
var a2=a1.document.selection.createRange();
a2.pasteHTML(a0);
a2.select();
a1.focus();
}
else if(this.browserFlag=="Mozilla")
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
var a1=document.getElementById(this.beanId).contentWindow;
if(this.browserFlag=="IE_Win")
{
a1.focus();
var a2=a1.document.selection.createRange();
a2.text=a0;
a2.select();
a1.focus();
}
else if(this.browserFlag=="Mozilla")
{
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
if(document.getElementById(this.beanId).contentWindow!=undefined)
{
if(this.mode=="TEXT_MODE")
a2.value=document.getElementById(this.beanId).contentWindow.document.body.innerText;
else
a2.value=document.getElementById(this.beanId).contentWindow.document.body.innerHTML;
}
}
}
return true;
}
function _insertImageTag(a0)
{
if(this.mode=="TEXT_MODE")
return;
if(this.browserFlag=="IE_Win")
{
document.getElementById(this.beanId).contentWindow.focus();
if(document.getElementById(this.beanId).contentWindow.document.selection.type!="None")
{
var a1=document.getElementById(this.beanId).contentWindow.document.selection.createRange().parentElement();
}
var a1=document.getElementById(this.beanId).contentWindow.document.selection.createRange().parentElement();
var a2=getNode("IMG",a1);
if(a0&&a0!="http://")
{
if(document.getElementById(this.beanId).contentWindow.document.selection.type=="None")
{
var a3=document.getElementById(this.beanId).contentWindow.document.selection.createRange();
a3.pasteHTML('<IMG SRC="'+a0+'">');
a3.select();
}
else
{
this.execHTMLCommand("insertImage",a0);
}
}
}
else if(this.browserFlag=="Mozilla")
{
if((a0!=null)
&&(a0!="")
&&a0!="http://")
{
document.getElementById(this.beanId).contentWindow.document.execCommand('InsertImage',false,a0);
}
}
}
function _setValue(a0)
{
if((this.browserFlag=="Mozilla")||(this.browserFlag=="IE_Win"))
{
document.getElementById(this.beanId).contentWindow.focus();
if(this.mode=="TEXT_MODE")
document.getElementById(this.beanId).contentWindow.document.body.innerText=a0;
else
document.getElementById(this.beanId).contentWindow.document.body.innerHTML=a0;
document.getElementById(this.beanId).contentWindow.focus();
}
else
{
var a1=this.getField(this.dataBeanId);
a1.value=a0;
}
}
function _setHref(a0)
{
if(this.mode=="TEXT_MODE")
return;
var a1=document.getElementById(this.beanId).contentWindow;
if(this.browserFlag=="IE_Win")
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
else if(this.browserFlag=="Mozilla")
{
a1.document.execCommand("CreateLink",false,a0);
}
}
function _viewHtmlSource(a0)
{
var a1=document.getElementById(this.beanId).contentWindow.document.body;
if(a0)
{
this.disableFontDropDowns(true);
this.mode="TEXT_MODE";
if(this.browserFlag=="IE_Win")
{
a1.innerText=a1.innerHTML;
}
else
{
var a2=document.createTextNode(a1.innerHTML);
a1.innerHTML="";
a1.appendChild(a2);
}
}
else
{
this.disableFontDropDowns(false);
this.mode="RICH_TEXT_MODE";
if(this.browserFlag=="IE_Win")
{
a1.innerHTML=a1.innerText;
}
else
{
var a2=a1.ownerDocument.createRange();
a2.selectNodeContents(a1);
a1.innerHTML=a2.toString();
}
}
document.getElementById(this.beanId).contentWindow.focus();
}
function _disableFontDropDowns(a0)
{
var a1=this.getField(this.dataBeanId+'fontFamily');
if(a1!=null)
a1.disabled=a0;
var a2=this.getField(this.dataBeanId+'fontColor');
if(a2!=null)
a2.disabled=a0;
var a3=this.getField(this.dataBeanId+'fontWeight');
if(a3!=null)
a3.disabled=a0;
}
function _checkRTEDataLength()
{
if(this.maxLength==-1)
return true;
if(this.mode=="TEXT_MODE")
data=document.getElementById(this.beanId).contentWindow.document.body.innerText;
else
data=document.getElementById(this.beanId).contentWindow.document.body.innerHTML;
dataLength=data.length;
if(dataLength>this.maxLength)
{
return false;
}
if(dataLength<this.maxLength)
return true;
if(dataLength==this.maxLength)
return true;
return false;
}
function _setMaxLengthAlert(a0)
{
this.maxLengthAlertMsgStr=a0;
}
function _getMaxLengthAlert()
{
return this.maxLengthAlertMsgStr;
}
