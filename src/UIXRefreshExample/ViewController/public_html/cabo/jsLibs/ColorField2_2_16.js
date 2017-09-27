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
