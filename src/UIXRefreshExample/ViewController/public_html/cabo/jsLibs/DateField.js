function _dfsv(
a0,
a1
)
{
if((a0==(void 0))||(a1==(void 0)))
return;
a1+=_getTimePortion(a0);
var a2=new Date(a1);
var a3=_getDateFieldFormat(a0);
var a4=a0.value;
var a1=a3.format(a2);
if(a0.value!=a1)
{
if(a0.onchange!=(void 0))
{
if(_agent.isIE)
{
a0.onpropertychange=function()
{
var a5=window.event;
if(a5.propertyName=='value')
{
a0.onpropertychange=function(){};
a0.onchange(a5);
}
}
a0.value=a1;
}
else
{
a0.value=a1;
var a5=new Object();
a5.type='change';
a5.target=a0;
a0.onchange(a5);
}
}
else
{
a0.value=a1;
}
}
a0.select();
a0.focus();
}
function _returnCalendarValue(
a0,
a1
)
{
var a2=a0.returnValue;
if(a2!=(void 0))
{
var a3=a0._dateField;
if(a3==(void 0))
{
a3=_savedField1879034;
}
_dfsv(a3,a2);
}
}
function _ldp(
a0,
a1,
a2,
a3,
a4
)
{
var a5=document.forms[a0][a1];
var a6=_dfgv(a5);
if(!a6)
{
a6=new Date();
}
if(!a4)
{
a4=_jspDir+_getQuerySeparator(_jspDir);
a4+="_t=fred&_red=cd";
}
else
{
var a7=a4.lastIndexOf('?');
var a8="";
if(a7==-1)
{
a7=a4.length;
}
else
{
a8=a4.substr(a7+1);
}
var a9=a4.lastIndexOf('/',a7);
var a10=a4.substring(0,a9+1);
a10+=_jspDir+_getQuerySeparator(_jspDir);
a10+=a8;
a10+=_getQuerySeparator(a10);
a10+="_t=fred";
var a11=a4.substring(a9+1,a7);
a4=a10;
a4+="&redirect="+escape(a11);
}
a4+="&value="+a6.getTime();
if(_configName.length>0)
{
a4+="&configName="+escape(_configName);
}
a4+="&loc="+_locale;
if(window["_enc"])
{
a4+="&enc="+_enc;
}
if(window["_contextURI"])
{
a4+="&contextURI="+escape(_contextURI);
}
var a12=-1*a6.getTimezoneOffset();
a4+="&tzOffset="+a12;
if(a2!=(void 0))
{
a4+="&minValue="+a2;
}
if(a3!=(void 0))
{
a4+="&maxValue="+a3;
}
var a13=openWindow(self,
a4,
'uix_2807778',
{width:350,height:370},
true,
void 0,
_returnCalendarValue);
a13._dateField=a5;
_savedField1879034=a5;
}
function _dfgv(a0)
{
if(a0.value!="")
return _getDateFieldFormat(a0).parse(a0.value);
return null;
}
function _getTimePortion(a0)
{
var a1=_dfgv(a0);
if(!a1)
a1=new Date();
var a2=new Date(a1.getFullYear(),
a1.getMonth(),
a1.getDate());
return a1-a2;
}
function _getLocaleTimeZoneDifference()
{
var a0=new Date();
var a1=a0.getTimezoneOffset()*-1;
var a2=0;
if(_uixLocaleTZ)
a2=(_uixLocaleTZ-a1)*60*1000;
return a2;
}
function _dfb(a0,a1,a2)
{
if(a1)
_fixDFF(a0);
}
function _dff(a0,a1)
{
_dfa(a0,a1);
}
function _dfa(a0,a1)
{
if(a1!=(void 0))
{
if(window._calActiveDateFields===(void 0))
window._calActiveDateFields=new Object();
if(typeof(a0)=="string")
{
a0=_getElementById(document,a0);
}
window._calActiveDateFields[a1]=a0;
}
}
function _calsd(a0,a1)
{
if(window._calActiveDateFields!=(void 0))
{
var a2=window._calActiveDateFields[a0];
if(a2)
_dfsv(a2,a1);
}
return false;
}
function _updateCal(a0,a1,a2)
{
a1+=('&scrolledValue='+a0.options[a0.selectedIndex].value);
if(a2)
_firePartialChange(a1);
else
document.location.href=a1;
}
function _doCancel()
{
top.returnValue=(void 0);
top.close();
return false;
}
function _selectDate(a0)
{
var a1=new Date(a0);
var a2=a1.getHours();
if(a2>=12)
{
a2=24;
}
a1.setHours(a2);
top.returnValue=a1.getTime();
top._unloadUIXDialog(window.event);
top.close();return false;
}
var _savedField1879034;
