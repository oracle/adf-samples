var _digits;
var _decimalSep;
var _groupingSep;
function isDigit(
a0
)
{
return(_getDigits()[a0]!=(void 0));
}
function _getDigits()
{
if(_digits==(void 0))
{
var a0=[
0x0030,
0x0660,
0x06F0,
0x0966,
0x09E6,
0x0A66,
0x0AE6,
0x0B66,
0x0BE7,
0x0C66,
0x0CE6,
0x0D66,
0x0E50,
0x0ED0,
0x0F20,
0xFF10
];
_digits=new Object();
for(var a1=0;a1<a0.length;a1++)
{
for(var a2=0;a2<10;a2++)
{
var a3=String.fromCharCode(a0[a1]+a2);
_digits[a3]=a2;
}
}
}
return _digits;
}
function parseDigit(
a0
)
{
var a1=_getDigits()[a0];
if(a1==(void 0))
{
return NaN;
}
else
{
return a1;
}
}
function isNotLowerCase()
{
var a0=alphaChar.charCodeAt(0);
if(a0>0xFF)
{
return true;
}
else
{
return!_isLowerCaseStrict(alphaChar);
}
}
function isLowerCase(
a0
)
{
var a1=a0.charCodeAt(0);
if(a1>0xFF)
{
return!isDigit(a0);
}
else
{
return _isLowerCaseStrict(a0);
}
}
function _isLowerCaseStrict(
a0
)
{
var a1=a0.charCodeAt(0);
return(((a1>=0x61)&&(a1<=0x7A))||
((a1>=0xDF)&&(a1<=0xFF)));
}
function isUpperCase(
a0
)
{
var a1=a0.charCodeAt(0);
if(a1>0xFF)
{
return!isDigit(a0);
}
else
{
return _isUpperCaseStrict(a0);
}
}
function isNotUpperCase(
a0
)
{
var a1=a0.charCodeAt(0);
if(a1>0xFF)
{
return true;
}
else
{
return!_isUpperCaseStrict(a0);
}
}
function _isUpperCaseStrict(
a0
)
{
var a1=a0.charCodeAt(0);
return(((a1>=0x41)&&(a1<=0x5A))||
((a1>=0xC0)&&(a1<=0xDe)));
}
function isLetter(
a0
)
{
return isLowerCase(a0)|isUpperCase(a0);
}
function getUserLanguage()
{
var a0=_locale;
if(a0==(void 0))
{
a0=window.navigator.userLanguage;
if(a0==(void 0))
{
a0=window.navigator.language;
}
}
return a0;
}
function getJavaLanguage(
a0
)
{
if(a0==(void 0))
{
a0=getUserLanguage();
}
var a1=a0.indexOf("-",0);
if(a1==-1)
return a0;
var a2=a0.length;
var a3=a0.substring(0,a1);
a3+="_";
a1++;
var a4=a0.indexOf("-",a1);
if(a4==-1)
{
a4=a2;
}
var a5=a0.substring(a1,
a4);
a3+=a5.toUpperCase();
if(a4!=a2)
{
a3+="_";
a3+=a0.substring(a4+1,
a2);
}
return a3;
}
function getLocaleSymbols(
a0
)
{
var a1=getJavaLanguage(a0);
while(true)
{
var a2=window["LocaleSymbols_"+a1];
if(a2!=(void 0))
{
return a2;
}
else
{
var a3=a1.lastIndexOf("_");
if(a3!=-1)
{
a1=a1.substring(0,a3);
}
else
{
break;
}
}
}
}
function _getEras()
{
return this.getLocaleElements()["Eras"];
}
function _getMonths()
{
return this.getLocaleElements()["MonthNames"];
}
function _getShortMonths()
{
return this.getLocaleElements()["MonthAbbreviations"];
}
function _getWeekdays()
{
return this.getLocaleElements()["DayNames"];
}
function _getShortWeekdays()
{
return this.getLocaleElements()["DayAbbreviations"];
}
function _getAmPmStrings()
{
return this.getLocaleElements()["AmPmMarkers"];
}
function _getZoneStrings()
{
return this.getLocaleElements()["zoneStrings"];
}
function _getLocalPatternChars()
{
return this.getLocaleElements()["localPatternChars"];
}
function _getDecimalSeparator()
{
if(_decimalSep!=(void 0))
return _decimalSep;
return this.getLocaleElements()["NumberElements"][0];
}
function _getGroupingSeparator()
{
if(_groupingSep!=(void 0))
return _groupingSep;
return this.getLocaleElements()["NumberElements"][1];
}
function _getPatternSeparator()
{
return this.getLocaleElements()["NumberElements"][2];
}
function _getPercent()
{
return this.getLocaleElements()["NumberElements"][3];
}
function _getZeroDigit()
{
return this.getLocaleElements()["NumberElements"][4];
}
function _getDigit()
{
return this.getLocaleElements()["NumberElements"][5];
}
function _getMinusSign()
{
return this.getLocaleElements()["NumberElements"][6];
}
function _getExponential()
{
return this.getLocaleElements()["NumberElements"][7];
}
function _getPerMill()
{
return this.getLocaleElements()["NumberElements"][8];
}
function _getInfinity()
{
return this.getLocaleElements()["NumberElements"][9];
}
function _getNaN()
{
return this.getLocaleElements()["NumberElements"][10];
}
function _getCurrencySymbol()
{
return this.getLocaleElements()["CurrencyElements"][0];
}
function _getInternationalCurrencySymbol()
{
return this.getLocaleElements()["CurrencyElements"][1];
}
function _getMonetaryDecimalSeparator()
{
var a0=this.getLocaleElements()["CurrencyElements"][2];
if(a0.length!=0)
{
return a0;
}
else
{
return this.getDecimalSeparator();
}
}
function _getLocaleElements()
{
return this["LocaleElements"];
}
function _getFullTimePatternString()
{
return this.getLocaleElements()["DateTimePatterns"][0];
}
function _getLongTimePatternString()
{
return this.getLocaleElements()["DateTimePatterns"][1];
}
function _getMediumTimePatternString()
{
return this.getLocaleElements()["DateTimePatterns"][2];
}
function _getShortTimePatternString()
{
return this.getLocaleElements()["DateTimePatterns"][3];
}
function _getFullDatePatternString()
{
return this.getLocaleElements()["DateTimePatterns"][4];
}
function _getLongDatePatternString()
{
return this.getLocaleElements()["DateTimePatterns"][5];
}
function _getMediumDatePatternString()
{
return this.getLocaleElements()["DateTimePatterns"][6];
}
function _getShortDatePatternString()
{
return this.getLocaleElements()["DateTimePatterns"][7];
}
function _getDateTimeFormatString()
{
return this.getLocaleElements()["DateTimePatterns"][8];
}
function LocaleSymbols(
a0
)
{
this["LocaleElements"]=a0;
}
LocaleSymbols.prototype.getFullTimePatternString=_getFullTimePatternString;
LocaleSymbols.prototype.getLongTimePatternString=_getLongTimePatternString;
LocaleSymbols.prototype.getMediumTimePatternString=_getMediumTimePatternString;
LocaleSymbols.prototype.getShortTimePatternString=_getShortTimePatternString;
LocaleSymbols.prototype.getFullDatePatternString=_getFullDatePatternString;
LocaleSymbols.prototype.getLongDatePatternString=_getLongDatePatternString;
LocaleSymbols.prototype.getMediumDatePatternString=_getMediumDatePatternString;
LocaleSymbols.prototype.getShortDatePatternString=_getShortDatePatternString;
LocaleSymbols.prototype.getDateTimeFormatString=_getDateTimeFormatString;
LocaleSymbols.prototype.getEras=_getEras;
LocaleSymbols.prototype.getMonths=_getMonths;
LocaleSymbols.prototype.getShortMonths=_getShortMonths;
LocaleSymbols.prototype.getWeekdays=_getWeekdays;
LocaleSymbols.prototype.getShortWeekdays=_getShortWeekdays;
LocaleSymbols.prototype.getAmPmStrings=_getAmPmStrings;
LocaleSymbols.prototype.getZoneStrings=_getZoneStrings;
LocaleSymbols.prototype.getLocalPatternChars=_getLocalPatternChars;
LocaleSymbols.prototype.getDecimalSeparator=_getDecimalSeparator;
LocaleSymbols.prototype.getGroupingSeparator=_getGroupingSeparator;
LocaleSymbols.prototype.getPatternSeparator=_getPatternSeparator;
LocaleSymbols.prototype.getPercent=_getPercent;
LocaleSymbols.prototype.getZeroDigit=_getZeroDigit;
LocaleSymbols.prototype.getDigit=_getDigit;
LocaleSymbols.prototype.getMinusSign=_getMinusSign;
LocaleSymbols.prototype.getExponential=_getExponential;
LocaleSymbols.prototype.getPerMill=_getPerMill;
LocaleSymbols.prototype.getInfinity=_getInfinity;
LocaleSymbols.prototype.getNaN=_getNaN;
LocaleSymbols.prototype.getCurrencySymbol=_getCurrencySymbol;
LocaleSymbols.prototype.getInternationalCurrencySymbol=_getInternationalCurrencySymbol;
LocaleSymbols.prototype.getMonetaryDecimalSeparator=_getMonetaryDecimalSeparator;
LocaleSymbols.prototype.getLocaleElements=_getLocaleElements;
function _formatValidate(
a0
)
{
var a1=this.parse(a0);
if(a1==(void 0))
{
return"Failed:"+this._class+" for value:"+a0;
}
else if(_instanceof(a1,ParseException))
{
return a1;
}
else
{
return(void 0);
}
}
function Format()
{
this._class="Format";
}
Format.prototype.format=(void 0);
Format.prototype.parse=(void 0);
Format.prototype.validate=_formatValidate;
function ParseException(
a0
)
{
this.parseString=a0;
}
ParseException.prototype.errorOffset=(void 0);
ParseException.prototype.parseString=(void 0);
function _noopFormat(
a0
)
{
return a0;
}
function _cjkParse(
a0
)
{
var a1=0;
var a2=this._length;
while(a1<a0.length)
{
var a3=a0.charCodeAt(a1);
if((a3<0x80)||((0xFF60<a3)&&(a3<0xFFA0)))a2--;
else a2-=2;
if(a2<0)
{
_setFailedPos(a1);
return(void 0);
}
a1++;
}
return a0;
}
function CjkFormat(
a0
)
{
this._class="CjkFormat";
this._length=a0;
}
CjkFormat.prototype=new Format();
CjkFormat.prototype.format=_noopFormat;
CjkFormat.prototype.parse=_cjkParse;
function _utf8Format(
a0
)
{
return a0;
}
function _utf8Parse(
a0
)
{
var a1=0;
var a2=this._length;
while(a1<a0.length)
{
var a3=a0.charCodeAt(a1);
if(a3<0x80)a2--;
else if(a3<0x800)a2-=2;
else
{
if((a3&0xF800)==0xD800)
a2-=2;
else
a2-=3;
}
if(a2<0)
{
_setFailedPos(a1);
return(void 0);
}
a1++;
}
return a0;
}
function Utf8Format(
a0
)
{
this._class="Utf8Format";
this._length=a0;
}
Utf8Format.prototype=new Format();
Utf8Format.prototype.format=_noopFormat;
Utf8Format.prototype.parse=_utf8Parse;
function _sbParse(
a0
)
{
if(this._length<a0.length)
{
_setFailedPos(this._length);
return(void 0);
}
return a0;
}
function SBFormat(
a0
)
{
this._class="SBFormat";
this._length=a0;
}
SBFormat.prototype=new Format();
SBFormat.prototype.format=_noopFormat;
SBFormat.prototype.parse=_sbParse;
function _setFailedPos(a0)
{
window["_failedPos"]=a0;
}
function _getDateFieldFormat(a0)
{
var a1=a0.name;
if(a1&&_dfs)
{
var a2=_dfs[a1];
if(a2)
return new SimpleDateFormat(a2);
}
return new SimpleDateFormat();
}
function _fixDFF(a0)
{
var a1=_getDateFieldFormat(a0);
if(a0.value!="")
{
var a2=a1.parse(a0.value);
if(a2!=(void 0))
a0.value=a1.format(a2);
}
}
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
var _LovDA="LovDA";
var _LovDP="LovDP";
var _LovEN="eventNames";
var _LovEV="event";
var _LovFI="fieldId";
var _LovFL="field";
var _LovFR="form";
var _LovHR;
var _LovIV=null;
var _LovLD=0;
var _LovNM=null;
var _LovPT="partialTargets";
var _LovSF=null;
var _LovSR="source";
var _LovST="searchText";
var _LovWN='lovWindow';
function _LovInputVTF(a0,
a1,
a2,
a3,
a4,
a5,
a6)
{
var a7=(a5!=(void 0));
var a8=new Date();
var a9=null;
if(a7)
{
if(_LovLD)
{
var a10=a8-_LovLD;
if((a10>=0)&&(a10<2000))
{
return false;
}
}
_LovLD=a8;
}
else if(_agent.isIE)
{
if(_LovIV==a2.value)
return true;
}
var a11=false;
var a12=true;
if(a2)
{
if(a2.value)
{
a12=(a2.value.search(/\S/)<0);
_LovIV=a2.value;
}
if(a12)
{
if((a3!=(void 0))&&a3)
{
a11=!a7;
}
}
else
{
a11=!a7;
}
if(a11)
{
if(a0)
{
var a13=new Object();
var a14=0;
var a15=new Object();
if(a2.form!=(void 0))
{
a9=a2.form;
if(a9.action!=(void 0))
a14=a9.action;
}
a14=_LovInputDDP(a14,a13);
for(var a16 in a13)
a15[a16]=a13[a16];
if(a2.id)
{
if(a6==(void 0))
a6=a2.id;
a13[_LovSR]=a6;
}
else
a13[_LovSR]=a2.name;
a13[_LovST]=a2.value;
a13[_LovPT]=a0;
var a17;
if(a9!=null)
a17=_lovInputEEN(a9.name);
var a18=_LovInputGPF(a17,'v','lovValidate');
var a19=null;
if(a1)
{
a19=function(a13,preencoded)
{
return a1(a13,a2.id);
}
}
if(_LovInputMPC(a19,a13,a18,false,null))
{
var a20=_LovInputUSF();
for(a16 in a13)
{
if((a15[a16]!=null)
&&(a15[a16]==a13[a16]))
{
delete a13[a16];
}
else
{
if(a15[a16]!=null)
{
delete a15[a16];
}
if(!(a20||a4))
a13[a16]=_LovInputENC(a13[a16]);
}
}
var a21=false;
if(a20&&!a4)
{
var a22=_LovInputUAA(a14,a15,a21);
a13[_getPartialParameter()]=true;
_LovInputSFS(a13,a9,a22);
}
else
{
for(a16 in a15)
a13[a16]=a15[a16];
_delayedEventParams=new Object();
var a23='_lovInputSFE(';
if(a4)
{
a23+="1);";
_delayedEventParams[_LovDP]=a13;
_delayedEventParams[_LovDA]=a9;
}
else
{
a23+="0);";
_delayedEventParams[_LovDA]=_LovInputUAA(a14,a13,
a21);
}
var a24=250;
_setRequestedFocusNode(document,a2.id,true,window);
window.setTimeout(a23,a24);
}
}
}
}
if(a7)
_LovInputOLW(a0,a5,1);
else
_setNavDirty(window,a2.name);
}
return(a12&&!a7);
}
function _lovInputSFE(a0)
{
if(_delayedEventParams==(void 0))
return;
var a1=_delayedEventParams[_LovDA];
var a2=_delayedEventParams[_LovDP];
_delayedEventParams=new Object();
if((a1==(void 0))||(a1==null))
return;
if(a0)
{
_submitPartialChange(a1,0,a2);
}
else
{
_firePartialChange(a1);
}
_LovIV=null;
return;
}
function _lovInputEEN(a0)
{
return window['_lovEvents_'+a0];
}
function _LovInputOLW(a0,a1,a2)
{
if((_pprBackRestoreInlineScripts!=(void 0))
&&(_pprBackRestoreInlineScripts==true))
{
return;
}
var a3=_getDependent(window,_LovWN);
var a4=a1;
var a5=(a2==1);
if(a4==(void 0))
{
a4=new Object();
}
var a6=_LovInputGPF(a4,'A',false);
var a7=_LovInputGPF(a4,'F',0);
var a8=_LovInputGPF(a4,'N',0);
if(!((a6||a8)&&a7))
return;
var a9=_LovInputGPF(a4,'L',0);
var a10=_LovInputGPF(a4,'E',a9);
var a11=_LovInputGPF(a4,'T',a8);
var a12=_LovInputGPF(a4,'I',0);
var a13=_LovInputGPF(a4,'S',0);
var a14=_LovEV;
var a15=_LovInputGPF(a4,'D',location.href);
var a16;
if(!a6)
{
var a17=document[a7][a11];
if(!a17)
return;
a16=a17.value;
}
else
{
a16=_LovInputGPF(a4,'R','');
}
_LovSF=a13;
_LovNM=new Object();
_LovNM[_LovFR]=a7;
_LovNM[_LovFL]=a11;
if(a10)
_LovNM[_LovSR]=a10;
else
_LovNM[_LovSR]=a8;
if(a9)
_LovNM[_LovFI]=a9;
else
_LovNM[_LovFI]=_LovNM[_LovSR];
_LovNM[_LovPT]=a0;
_LovNM[_LovEN]=_lovInputEEN(a7);
if(a3!=(void 0))
{
return false;
}
var a18=_LovInputGPF(a4,'P',false);
var a19=new Object();
var a20=new Object();
if(a18&&a5)
{
a19[_LovEV]=_LovInputGPF(_LovNM[_LovEN],'p','lovPrepare');
a19[_LovSR]=_LovNM[_LovSR];
a19[_LovST]=a16;
if(a0)
{
a19[_LovPT]=a0;
_submitPartialChange(a7,0,a19);
}
else
{
submitForm(a7,0,a19);
}
}
else
{
_LovInputUUP(_LovSR,_LovNM[_LovFI],a15,a19,false);
_LovInputUUP(_LovST,a16,a15,a19,false);
var a21=_LovInputGPF(_LovNM[_LovEN],'f','lovFilter');
var a22=new Object();
if(a18)
{
_LovInputUUP(_LovEV,a21,a15,a19,false);
}
else
{
_LovInputDDP(a15,a19);
for(var a23 in a19)
{
if((a23!=_LovST)&&(a23!=_LovSR))
a20[a23]=a19[a23];
}
if(!_LovInputMPC(a12,a19,a21,true,a22))
return false;
}
for(var a24 in a19)
{
var a25=a19[a24];
if((a20[a24]==(void 0))
||(a20[a24]!=a25))
{
a22[_LovInputENC(a24)]=_LovInputENC(a25);
}
else
a22[a24]=a25;
}
a19=a22;
_LovHR=location.href;
if(!a18)
{
var a26=new Object();
for(var a24 in a19)
{
var a25=a19[a24];
var a27=!((a24==_LovST)
||((a24==_LovSR)&&(a25==_LovNM[_LovSR]))
||((a24==_LovEV)&&(a25==a21)));
a15=_LovInputUUP(a24,a25,a15,a26,a27);
}
a19=a26;
}
_LovInputOMW(a15,_LovInputWCB,a19);
}
return false;
}
function _LovInputUUP(a0,a1,a2,a3,a4)
{
var a5=new RegExp("[?&]"+a0+"(=|&|$)","i");
var a6=a2.match(a5);
var a7=(a6==null);
if(!a7&&(a6.length==1))
a7=(a6[0].length<1);
if(a7)
{
a3[a0]=a1;
}
else if(a4)
{
a2=_addParameter(a2,a0,a1);
}
return a2;
}
function _LovInputGPF(a0,a1,a2)
{
var a3;
if(a0!=(void 0))
{
a3=a0[a1];
}
if(a3==(void 0))
a3=a2;
return a3;
}
function _LovInputMPC(a0,a1,a2,a3,a4)
{
var a5=_LovEV;
var a6=null;
if(a3)
a6=a1[a5];
delete a1[a5];
if(a0)
{
var a7=a0(a1,a4);
if(!a7)
return false;
}
if(!a1[a5])
{
if(a6)
{
a1[a5]=a6;
}
else
{
a1[a5]=a2;
}
}
return true;
}
function _LovInputOMW(a0,a1,a2)
{
var a3="";
if(_jspDir.search(/^http[s]?:/)<0)
a3+=location.protocol+'//'+location.host;
a3+=_jspDir+_getQuerySeparator(_jspDir)+'_t=fredRC';
if(_enc)
a3+="&enc="+_enc;
a3+='&_minWidth=750&_minHeight=550';
if(_configName)
a3+="&configName="+_configName;
if(_contextURI)
a3+="&contextURI="+_contextURI;
a3+='&redirect=';
if(a0.charAt(0)!='/')
{
var a4=location.pathname;
a0=(a4.substr(0,a4.lastIndexOf('/')+1)
+a0);
}
a3+=_LovInputENC(a0);
for(var a5 in a2)
{
var a6=a2[a5];
a3+="&"+a5+"="+a6;
}
lovw=openWindow(window,a3,_LovWN,{width:750,height:550},
true,'dialog',a1);
if(lovw!=null)
{
lovw._LovSL=false;
lovw._LovSF=_LovSF;
lovw._LovNM=_LovNM;
}
}
function _LovInputPWP(a0,a1)
{
var a2=window[a0];
if(a2==(void 0))
{
if((a1!=(void 0))&&a1[a0])
{
a2=a1[a0];
}
else if(top[a0])
{
a2=top[a0];
}
else if((a1.opener!=(void 0))&&(a1.opener[a0]))
{
a2=a1.opener[a0];
}
}
return a2;
}
function _LovInputWCB(a0,a1)
{
var a2=true;
if(!a0._LovSL)
{
_LovInputSTC(a0.opener);
return false;
}
_LovNM=_LovInputPWP('_LovNM',a0);
if(_LovNM==null)
return false;
_setNavDirty(a0.opener,_LovNM[_LovFL]);
if(a2)
{
var a3=new Object();
a3[_LovEV]=_LovInputGPF(_LovNM[_LovEN],'u','lovUpdate');
a3[_LovSR]=_LovNM[_LovSR];
var a4=_LovNM[_LovPT];
_setRequestedFocusNode(a0.opener.document,_LovNM[_LovFI],
false,window);
if(a4)
{
a3[_LovPT]=a4;
}
var a5=_LovInputPWP('_lovClose',a0);
var a6=_LovNM[_LovFR];
if((a5!=(void 0))&&(a6!=(void 0)))
{
a5(a6,a3,(a4));
}
else
{
var a7=_LovInputDDP(0,a3);
if(a4)
{
a7=_LovInputUAA(a7,a3,true);
_firePartialChange(a7);
}
else
{
a7=_LovInputUAA(a7,a3,true);
location=a7;
}
}
}
return false;
}
function _LovInputDDP(a0,a1)
{
var a2=a0;
if(!a0)
{
if(_LovHR!=(void 0))
{
a2=_LovHR;
}
else if((location!=(void 0))
&&(location.href!=(void 0)))
{
a2=location.href;
}
else
{
return"#";
}
}
if(a2.charAt(a2.length-1)=='#')
{
a2=a2.substr(0,a2.length-1);
}
var a3=a2;
var a4=a2.indexOf('?');
if(a4>0)
{
a3=a2.substr(0,a4);
var a5=a2.substr(a4+1);
var a6=a5.split('&');
for(var a7=0;a7<a6.length;a7++)
{
var a8=a6[a7].indexOf('=');
if(a8>=0)
{
a1[a6[a7].substr(0,a8)]=a6[a7].substr(a8+1);
}
else
{
a1[a6[a7]]="";
}
}
}
return a3;
}
function _LovInputUAA(a0,a1,a2)
{
var a3=((a0.search('\\\?')>=0)?'&':'?');
var a4=a0;
if(a4)
{
for(var a5 in a1)
{
var a6=a1[a5];
a4+=(a3
+(a2?_LovInputENC(a5):a5)
+'=');
if(a6)
a4+=(a2?_LovInputENC(a6):a6);
a3='&';
}
}
return a4;
}
function _LovInputSTC(a0)
{
var a1=_LovInputPWP('_LovNM',a0);
var a2=null;
var a3=null;
if(a1&&a1[_LovFR]&&a1[_LovFL])
{
if((a0!=null)&&(a0["document"]))
{
a2=a0.document;
a3=a2[a1[_LovFR]][a1[_LovFL]];
}
}
if(a3)
{
_pprFocus(a3,a2);
if(a3["select"]!=null)
a3.select();
}
}
function _LovInputCBF()
{
top.close();
return false;
}
function _LovInputSBF(event)
{
top._LovSL=true;
var nameObj=_LovInputPWP('_LovNM',top);
var opn=top.opener;
_setNavDirty(opn,nameObj[_LovFL]);
var stateCheck=_LovInputPWP('_LovSC',window);
var closeWin=true;
if(stateCheck!=(void 0)&&stateCheck!='')
{
closeWin=eval(stateCheck+'(window, "_LOVResFrm")');
}
var selFunc=_LovInputPWP('_LovSF',top);
if(selFunc)
{
top._LovSL=selFunc(window,
opn.document[nameObj[_LovFR]][nameObj[_LovFL]],
event,opn);
}
if(closeWin)
{
window.onunload=function(){top._unloadUIXDialog(window.event);
top.close();};
}
if(_agent.isNav&&_agent.isSolaris)
{
window.onunload=function(){_LovInputWCB(top,event);};
}
var eventNames=_LovInputPWP('_LovEN',window);
var lovSelect=_LovInputGPF(nameObj[eventNames],'s','lovSelect');
submitForm(0,0,{'event':lovSelect,'source':_LovLI});
if(_agent.isSafari)
top.close();
}
function _LovInputENC(a0)
{
var a1;
var a2=_agent.isNav||_agent.isMac||_agent.atMost("ie",5.49);
if(!a2)
{
a1=encodeURIComponent(a0);
}
else
{
a1=escape(a0);
}
return a1;
}
function _LovInputQSF(a0,a1)
{
var a2=new TableProxy(a0);
a2.setSelectedRow(a1);
_LovInputSBF();
}
function _LovInputUSF()
{
return _enc.toUpperCase()!="UTF-8";
}
function _LovInputSFS(a0,a1,a2)
{
var a3=window.document;
var a4="_LovInput";
if(a1.id)
a4+=a1.id;
else if(a1.name)
a4+=a1.name;
else
a4+="DummyForm";
var a5=a3.createElement("form");
a5.id=a4;
a5.name=a4;
a5.target=a1.target;
a5.method=a1.method;
if(a2)
a5.action=a2;
else
a5.action=a1.action;
for(var a6 in a0)
{
var a7=a3.createElement("input");
a7.type="hidden";
a7.name=a6;
a7.value=a0[a6];
a5.appendChild(a7);
}
a3.body.appendChild(a5);
var a8="_"+a5.name+"Validater";
var a9=false;
if(window[a8]==(void 0))
{
a9=true;
window[a8]=1;
}
_submitPartialChange(a5,0,a0);
if(a9&&(!_agent.isIE))
delete window[a8];
a3.body.removeChild(a5);
}
function _LovInputSOE(a0,a1,a2,a3)
{
if(_getKC(a0)==13)
{
submitForm(a1,1,{'event':a2,'source':a3});
return false;
}
return true;
}
function _decimalFormat(
a0
)
{
return""+a0;
}
function _decimalParse(
a0
)
{
if(!a0)
return(void 0);
var a1=getLocaleSymbols();
if(a1)
{
var a2=a1.getGroupingSeparator();
if((a0.indexOf(a2)==0)||
(a0.lastIndexOf(a2)==(a0.length-1)))
return(void 0);
var a3=new RegExp("\\"+a2,"g");
a0=a0.replace(a3,"");
var a4=new RegExp("\\"+a1.getDecimalSeparator(),"g");
a0=a0.replace(a4,".");
}
var a5=a0.length-1;
while(a5>=0)
{
if(a0.charAt(a5)!=' ')
break;
a5--;
}
if(a5>=0)
{
if((a0.indexOf('e')<0)&&
(a0.indexOf('E')<0)&&
(((a0*a0)==0)||
((a0/a0)==1)))
{
var a6=parseFloat(a0);
if(!isNaN(a6))
{
var a7=a0.length;
var a8=0;
var a9=a0.lastIndexOf('.');
if(a9!=-1)
{
a7=a9;
a8=a0.length-a9-1;
}
var a10;
if((this._maxValue!=(void 0))&&
(a6>this._maxValue))
{
a10=DecimalFormat.LV;
}
else if((this._minValue!=(void 0))&&
(a6<this._minValue))
{
a10=DecimalFormat.MV;
}
else if((this._maxPrecision!=(void 0))&&
(a7>this._maxPrecision))
{
a10=DecimalFormat.LID;
}
else if((this._maxScale!=(void 0))&&
(a8>this._maxScale))
{
a10=DecimalFormat.LFD;
}
if(a10!=(void 0))
{
var a11=this._messages;
if((a11==(void 0))||
(a11[a10]==(void 0)))
return(void 0);
else
return new ParseException(a11[a10]);
}
return a6;
}
}
}
return(void 0);
}
function DecimalFormat(
a0,
a1,
a2,
a3,
a4)
{
this._messages=a0;
this._maxPrecision=a1;
this._maxScale=a2;
this._maxValue=a3;
this._minValue=a4;
this._class="DecimalFormat";
}
DecimalFormat.prototype=new Format();
DecimalFormat.prototype.format=_decimalFormat;
DecimalFormat.prototype.parse=_decimalParse;
DecimalFormat.LFD='LFD';
DecimalFormat.LID='LID';
DecimalFormat.LV='LV';
DecimalFormat.MV='MV';
function _regExpFormat(
a0
)
{
return a0;
}
function _regExpParse(
a0
)
{
var a1=a0.match(this._pattern);
if((a1!=(void 0))&&(a1[0]==a0))
{
return a0;
}
else
{
return(void 0);
}
}
function RegExpFormat(
a0
)
{
this._class="RegExpFormat";
this._pattern=a0;
}
RegExpFormat.prototype=new Format();
RegExpFormat.prototype.format=_regExpFormat;
RegExpFormat.prototype.parse=_regExpParse;
var _agent=new Object();
var _lastDateSubmitted;
var _lastDateReset=0;
var _lastDateValidated=0;
var _lastValidationFailure=0;
var _pprSubmitCount=0;
var _pprSomeAction=false;
var _pprRequestCount=0;
var _pprUnloaded=false;
var _pprBackRestoreInlineScripts=false;
var _pprSavedCursorFlag=false;
var _pprBlocking=false;
var _blockOnEverySubmit=false;
var _pprBlockStartTime=0;
var _pprIframeName="_pprIFrame";
var _pprFirstClickPass=false;
var _pprdivElementName='_pprBlockingDiv';
var _pprBlockingTimeout=null;
var _pprEventElement=null;
var _pprChoiceChanged=false;
_delayedEventParams=new Object();
var _initialFormState;
var _initialFormExclude=new Object();
var _initialFormStateName;
var _navDirty;
var _initialFocusID=null;
var _UixFocusRequestDoc=null;
var _UixFocusRequestID=null;
var _UixFocusRequestNext=false;
var _blockCheckUnloadFromDialog=false;
var _saveForm=null;
var _saveDoValidate=null;
var _saveParameters=null;
var _submitRejected=false;
var _inPartialSubmit=false;
_radioActionScript=null;
_radioUserDefScript=null;
_lastEventTime=null;
function _atLeast(
a0,
a1
)
{
return(!a0||(a0==_agent.kind))&&
(!a1||(a1<=_agent.version));
}
function _atMost(
a0,
a1
)
{
return(a0==_agent.kind)&&(a1>=_agent.version);
}
function _supportsDOM()
{
var a0=false;
if(_agent.isIE)
{
a0=_agent.version>=5.5;
}
else if(_agent.isNav)
{
a0=false;
}
else if(_agent.isGecko||_agent.isSafari)
{
a0=true;
}
return a0;
}
function _agentInit()
{
var a0=navigator.userAgent.toLowerCase();
var a1=parseFloat(navigator.appVersion);
var a2=false;
var a3=false;
var a4=false;
var a5=false;
var a6=false;
var a7="unknown";
var a8=false;
var a9=false;
var a10=false;
if(a0.indexOf("msie")!=-1)
{
a3=true;
var a11=a0.match(/msie (.*);/);
a1=parseFloat(a11[1]);
a7="ie";
}
else if(a0.indexOf("opera")!=-1)
{
a2=true
a7="opera";
}
else if((a0.indexOf("applewebkit")!=-1)||
(a0.indexOf("safari")!=-1))
{
a6=true
a7="safari";
}
else if(a0.indexOf("gecko/")!=-1)
{
a5=true;
a7="gecko";
a1=1.0;
}
else if((a0.indexOf('mozilla')!=-1)&&
(a0.indexOf('spoofer')==-1)&&
(a0.indexOf('compatible')==-1))
{
if(a1>=5.0)
{
a5=true;
a7="gecko";
a1=1.0;
}
else
{
a4=true;
a7="nn";
}
}
if(a0.indexOf('win')!=-1)
{
a8=true;
}
else if(a0.indexOf('mac')!=-1)
{
a10=true;
}
else if(a0.indexOf('sunos')!=-1)
{
a9=true;
}
_agent.isIE=a3;
_agent.isNav=a4;
_agent.isOpera=a2;
_agent.isGecko=a5;
_agent.isSafari=a6;
_agent.version=a1
_agent.kind=a7;
_agent.isWindows=a8;
_agent.isSolaris=a9;
_agent.isMac=a10;
_agent.atLeast=_atLeast;
_agent.atMost=_atMost;
}
_agentInit();
var _ieFeatures=
{
channelmode:1,
copyhistory:1,
directories:1,
fullscreen:1,
height:1,
location:1,
menubar:1,
resizable:1,
scrollbars:1,
status:1,
titlebar:1,
toolbar:1,
width:1
};
var _nnFeatures=
{
alwayslowered:1,
alwaysraised:1,
copyhistory:1,
dependent:1,
directories:1,
height:1,
hotkeys:1,
innerheight:1,
innerwidth:1,
location:1,
menubar:1,
outerwidth:1,
outerheight:1,
resizable:1,
scrollbars:1,
status:1,
titlebar:1,
toolbar:1,
width:1,
"z-lock":1
}
var _modelessFeatureOverrides=
{
};
var _modalFeatureOverrides=
{
};
var _featureDefaults=
{
document:
{
channelmode:false,
copyhistory:true,
dependent:false,
directories:true,
fullscreen:false,
hotkeys:false,
location:true,
menubar:true,
resizable:true,
scrollbars:true,
status:true,
toolbar:true
},
dialog:
{
channelmode:false,
copyhistory:false,
dependent:true,
directories:false,
fullscreen:false,
hotkeys:true,
location:false,
menubar:false,
resizable:true,
scrollbars:true,
status:true
}
}
var _signedFeatures=
{
alwayslowered:1,
alwaysraised:1,
titlebar:1,
"z-lock":1
};
var _booleanFeatures=
{
alwayslowered:1,
alwaysraised:1,
channelmode:1,
copyhistory:1,
dependent:1,
directories:1,
fullscreen:1,
hotkeys:1,
location:1,
menubar:1,
resizable:1,
scrollbars:1,
status:1,
titlebar:1,
toolbar:1,
"z-lock":1
};
function _getContentWidth(
a0,
a1,
a2
)
{
var a3=a0.childNodes;
var a4=_agent.isGecko;
var a5=(a4)
?"tagName"
:"canHaveHTML"
var a6=0;
for(var a7=0;a7<a3.length;a7++)
{
var a8=a3[a7];
if(a8[a5]&&(a8.offsetWidth>0))
{
var a9=0;
var a10=a8["offsetWidth"];
if(a4)
{
if(a10==a1)
{
a9=_getContentWidth(a8,
a10,
a8.offsetLeft);
}
else
{
a9=a10;
}
}
else
{
a9=a8["clientWidth"];
if(a9==0)
{
a9=_getContentWidth(a8,
a10,
a8.offsetLeft);
}
}
if(a9>a6)
{
a6=a9;
}
}
}
if(a6==0)
a6=a1;
var a11=10;
if(_isLTR()||(a2<=5))
{
a11=2*a2;
}
return a6+a11;
}
function _getTop(
a0
)
{
if(!(_agent.isGecko||_agent.isSafari))
{
return top;
}
else
{
var a1=(a0)
?a0.window
:window;
while(a1.parent&&(a1.parent!=a1))
{
a1=a1.parent;
}
return a1;
}
}
function _sizeWin(
a0,
a1,
a2,
a3
)
{
var a4=_agent.isGecko;
var a5=_agent.isIE;
if(!(a4||(a5&&_agent.isWindows)))
return;
var a6=a0.window.document.body;
if(a6)
{
var a7=(!a5&&(a6.scrollWidth>a6.clientWidth))
?a6.scrollWidth
:_getContentWidth(a6,a6.offsetWidth,a6.offsetLeft);
var a8=0;
if(a4)
{
a8=a6.offsetHeight+(window.outerHeight-window.innerHeight);
a8+=30;
a7+=(window.outerWidth-a6.offsetWidth);
}
else
{
a8=a6.scrollHeight+(a6.offsetHeight-a6.clientHeight);
a8+=21;
a7+=a6.offsetWidth-a6.clientWidth+16;
a8+=parseInt(a6.topMargin)+parseInt(a6.bottomMargin);
a7+=parseInt(a6.leftMargin)+parseInt(a6.rightMargin);
}
if(a1)
a7+=a1;
if(a2)
a8+=a2;
if(a3!=(void 0))
{
if(a3['W'])
{
var a9=0+a3['W'];
if(a7<a9)
a7=a9;
}
if(a3['H'])
{
var a10=0+a3['H'];
if(a8<a10)
a8=a10;
}
}
var a11=_getTop(a0);
var a12=a5?0:a11.screen.availLeft;
var a13=a5?0:a11.screen.availTop;
var a14=a11.screen.availHeight*0.95;
var a15=a11.screen.availWidth*0.95;
if(a8>a14)
a8=a14;
if(a7>a15)
a7=a15;
a11.resizeTo(a7,a8);
var a16=a5?a11.screenLeft:a11.screenX;
var a17=a5?a11.screenTop:a11.screenY;
var a18=false;
if((a16+a7)>(a12+a15))
{
a16=(a11.screen.availWidth-a7)/2;
a18=true;
}
if((a17+a8)>(a13+a14))
{
a17=(a11.screen.availHeight-a8)/2;
a18=true;
}
if(a18)
{
a11.moveTo(a16,a17);
}
}
}
function _onModalClickNN(
a0
)
{
if(_getValidModalDependent(self))
{
return false;
}
else
{
self.routeEvent(a0);
return true;
}
}
var _mozClickEH=new Object();
function _onModalClickMoz(
a0
)
{
dump(a0);
}
_mozClickEH["handleEvent"]=_onModalClickMoz;
function _onModalFocus()
{
var a0=self.document.body;
var a1=_getValidModalDependent(self);
var a2=_agent.atLeast("ie",5)&&_agent.isWindows;
if(a1)
{
a1.focus();
if(a2)
{
a0.setCapture();
}
}
else
{
if(a2)
{
a0.onlosecapture=null;
a0.releaseCapture();
}
}
}
function _onModalLoseCapture()
{
var a0=_getValidModalDependent(self);
if(a0)
{
window.setTimeout("_onModalFocus()",1);
}
}
function t(a0,a1)
{
if(_tURL!=void 0)
{
document.write('<img src="'+_tURL+'"');
if(a0!=void 0)
document.write(' width="'+a0+'"');
if(a1!=void 0)
document.write(' height="'+a1+'"');
if(_axm!=void 0)
document.write(' alt=""');
document.write('>');
}
}
function openWindow(
a0,
a1,
a2,
a3,
a4,
a5,
a6
)
{
if(a0)
{
if(a4==(void 0))
a4=false;
if(!a5)
{
a5=(a4)?"dialog":"document";
}
if(!a2)
a2="_blank";
var a7=_featureDefaults[a5];
if(a7==(void 0))
{
a5="document";
a7=_featureDefaults[a5];
}
var a8=(a4)
?_modalFeatureOverrides
:_modelessFeatureOverrides;
var a9=(_agent.isIE)
?_ieFeatures
:_nnFeatures;
var a10=null;
if(a3)
{
a10=new Object();
for(var a11 in a3)
{
a10[a11]=a3[a11];
}
}
var a12="";
for(var a13 in a9)
{
var a14=a8[a13];
if(a14==(void 0))
{
if(a10)
{
a14=a10[a13];
delete a10[a13];
}
if(a14==(void 0))
a14=a7[a13];
}
if(a14!=(void 0))
{
var a15=_booleanFeatures[a13]!=(void 0);
if(a14||!a15)
{
a12+=a13;
if(!a15)
{
a12+="="+a14;
}
a12+=",";
}
}
}
for(var a11 in a10)
{
a12+=a11;
if(a10[a11])
a12+="="+a10[a11];
a12+=",";
}
if(a12.length!=0)
{
a12=a12.substring(0,a12.length-1);
}
if(a6)
{
_setDependent(a0,a2,a6);
}
var a16=a0.open(a1,a2,a12);
var a17=false;
if(a16!=null)
{
if(_agent.atLeast("ie",5)||_agent.isGecko)
{
propCheckContent=("try{"
+"var pc=0;"
+"for(p in win)"
+"{pc++;break;}"
+"if(pc>0)return true;"
+"}catch(e){}"
+"return false;");
propCheck=new Function("win",propCheckContent);
a17=propCheck(a16);
}
else
a17=true;
}
if(!a17)
{
_setDependent(a0,a2,(void 0));
_clearBodyModalEffects('alpha');
if(_UixWindowOpenError!=null)
alert(_UixWindowOpenError);
return;
}
var a18=_agent.atMost("ie",4.99);
var a19=false;
var a20=a0.document.body;
if(a4&&!a18)
{
if(_agent.atLeast("ie",4))
{
a20.style.filter="alpha(opacity=50)";
a19=true;
}
if(_agent.isNav)
{
a0.captureEvents(Event.CLICK);
a0.onclick=_onModalClickNN;
}
else if(_agent.isGecko)
{
a20.addEventListener(Event.CLICK,_mozClickEH,true);
}
a0.onfocus=_onModalFocus;
}
if(a4&&(_agent.atLeast("ie",5)&&_agent.isWindows))
{
a20.setCapture();
a0.setTimeout("_clearBodyModalEffects('capture')",1000);
a20.onlosecapture=_onModalLoseCapture;
}
if(a4&&!a18)
{
_setDependent(a0,"modalWindow",a16);
}
a16.focus();
if(a19)
{
a0.setTimeout("_clearBodyModalEffects('alpha')",1000);
}
return a16;
}
else
{
return null;
}
}
function _getDependents(
a0,
a1
)
{
var a2;
if(a0)
{
a2=a0["_dependents"];
if(a2==(void 0))
{
if(a1)
{
a2=new Object();
a0["_dependents"]=a2;
}
}
}
return a2;
}
function _getDependent(
a0,
a1
)
{
var a2=_getDependents(a0);
var a3;
if(a2)
{
a3=a2[a1];
}
return a3;
}
function _setDependent(
a0,
a1,
a2
)
{
var a3=_getDependents(a0,true);
if(a3)
{
a3[a1]=a2;
}
}
function _getModalDependent(
a0
)
{
return _getDependent(a0,"modalWindow");
}
function _getValidModalDependent(
a0
)
{
var a1=_getModalDependent(a0);
if(a1)
{
if(a1.closed)
{
_setDependent(a0,"modalWindow",(void 0));
a1=(void 0);
}
}
return a1;
}
function _isModalDependent(
a0,
a1
)
{
return(a1==_getModalDependent(a0));
}
function _clearBodyModalEffects(a0)
{
if(_getValidModalDependent(self)!=null)
{
self.setTimeout("_clearBodyModalEffects('"+a0+"')",1000);
}
else
{
if(a0=='alpha')
{
self.document.body.style.filter=null;
}
else if(a0=='capture')
{
self.document.body.releaseCapture();
}
}
}
function _unloadUIXDialog(
a0
)
{
_blockCheckUnloadFromDialog=false;
_checkUnload(a0);
_blockCheckUnloadFromDialog=true;
}
function _checkUnload(
a0
)
{
if(_blockCheckUnloadFromDialog)
{
_blockCheckUnloadFromDialog=false;
return;
}
if(_isModalAbandoned())
return;
var a1=_getModalDependent(window);
if(a1!=null)
{
_setModalAbandoned(a1);
a1.close();
}
_pprUnloaded=true;
var a2=_getTop();
if(!a2)
return;
var a3=a2["opener"];
if(!a3)
return;
var a4=_getDependent(a3,self.name);
if(_isModalDependent(a3,self))
{
_setDependent(a3,"modalWindow",(void 0));
a3.onfocus=null;
var a5=a3.document.body;
if(_agent.atLeast("ie",4))
{
if(_agent.atLeast("ie",5)&&_agent.isWindows)
{
a5.onlosecapture=null;
a5.releaseCapture();
}
a5.style.filter=null;
}
if(_agent.isNav)
{
a3.releaseEvents(Event.CLICK);
a3.onclick=null;
}
if(_agent.isGecko)
{
a5.removeEventListener(Event.CLICK,
_mozClickEH,
true);
}
}
if(a4!=(void 0))
{
_setDependent(a3,self.name,(void 0));
if(a0==(void 0))
a0=self.event;
a4(a2,a0);
}
}
function _isModalAbandoned()
{
var a0=_getTop();
return a0._abandoned;
}
function _setModalAbandoned(
a0
)
{
a0._abandoned=true;
}
function _focusChanging()
{
if(_agent.isIE)
{
return(window.event.srcElement!=window.document.activeElement);
}
else
{
return true;
}
}
function _getKeyValueString(
a0,
a1,
a2
)
{
var a3=a0[a1];
if(typeof(a3)=="function")
{
a3="[function]";
}
var a4=(_agent.isGecko)
?((a2+1)%3==0)
?'\n'
:'    '
:'\t';
return a1+':'+a3+a4;
}
function _dump(
a0
)
{
dump(a0,{innerText:1,outerText:1,outerHTML:1,innerHTML:1});
}
function dump(
a0,
a1,
a2
)
{
var a3="";
if(a0)
{
if(!a2)
{
a2=a0["name"];
}
var a4="return _getKeyValueString(target, key, index);";
if(_agent.atLeast("ie",5)||_agent.isGecko||_agent.isSafari)
a4="try{"+a4+"}catch(e){return '';}";
var a5=new Function("target","key","index",a4);
var a6=0;
var a7=new Array();
for(var a8 in a0)
{
if((!a1||!a1[a8])&&!a8.match(/DOM/))
{
a7[a6]=a8;
a6++;
}
}
a7.sort();
for(var a9=0;a9<a7.length;a9++)
{
a3+=a5(a0,a7[a9],a9);
}
}
else
{
a2="(Undefined)";
}
if(a3=="")
{
a3="No properties";
}
alert(a2+":\n"+a3);
}
function _validateForm(
a0
)
{
var a1='_'+a0.name+'Validater';
var a2=window[a1];
if(a2)
return a2(a0);
return false;
}
function _getNextNonCommentSibling(
a0,
a1
)
{
var a2=a0.children;
for(var a3=a1+1;a3<a2.length;a3++)
{
var a4=a2[a3];
if(a4&&(a4.tagName!="!"))
{
return a4;
}
}
return null;
}
function _valField(
formName,
nameInForm
)
{
if(nameInForm)
{
var target=document.forms[formName][nameInForm];
var blurFunc=target.onblur;
if(blurFunc)
{
var valFunc=blurFunc.toString();
var valContents=valFunc.substring(valFunc.indexOf("{")+1,
valFunc.lastIndexOf("}"));
var targetString="document.forms['"+
formName+
"']['"+
nameInForm+
"']";
valContents=valContents.replace(/this/,targetString);
var lastArg=valContents.lastIndexOf(",");
valContents=valContents.substring(0,lastArg)+")";
eval(valContents);
}
}
}
function _validationAlert(a0)
{
_recordValidation(true,0);
alert(a0);
_recordValidation(true,0);
}
function _recordValidation(a0,a1)
{
if(!a1)
a1=new Date();
_lastDateValidated=a1;
if(a0)
_lastValidationFailure=a1;
}
function _recentValidation(a0)
{
var a1=false;
var a2=250;
if(_agent.isMac)
{
a2=600;
}
var a3=new Date();
var a4;
a4=a3-_lastValidationFailure;
if((a4>=0)&&(a4<a2))
{
a1=true;
}
else if(!a0)
{
a4=a3-_lastDateValidated;
if((a4>=0)&&(a4<a2))
{
a1=true;
}
}
return a1;
}
function _validateField(
a0,
a1,
a2,
a3,
a4
)
{
var a5=_agent.isNav;
if(a5&&a4)
{
return;
}
if(a5||_agent.isMac||_agent.isGecko)
{
if(_recentValidation(false))
return;
}
var a6=a3||(_getValue(a0)!="");
if(a6&&!window._validating&&_focusChanging())
{
if(a4)
{
var a7=window.document.activeElement;
if(a7)
{
var a8=a0.parentElement;
if(a8==a7.parentElement)
{
var a9=a8.children;
for(var a10=0;a10<a9.length;a10++)
{
if(a0==a9[a10])
{
a6=(a7!=_getNextNonCommentSibling(a8,a10));
}
}
}
}
}
if(a6)
{
var a11=_getValidationError(a0,a1);
if(a11)
{
var a12=_isShowing(a0);
window._validating=a0;
if(a12)
a0.select();
if(!a5&&a12)
{
a0.focus();
if(window["_failedPos"]!=(void 0))
{
if(a0.createTextRange)
{
var a13=a0.createTextRange();
a13.moveStart("character",window["_failedPos"]);
a13.select();
}
else if(a0.selectionStart!=(void 0))
{
a0.selectionStart=window["_failedPos"];
}
window["_failedPos"]=(void 0);
}
}
var a14=_getErrorString(a0,a2,
a11);
if(a14)
{
_validationAlert(a14);
}
if(a5&&a12)
{
a0.focus();
}
}
}
}
}
function _unvalidateField(
a0
)
{
if(window._validating==a0)
{
window._validating=void 0;
}
}
function submitForm(
a0,
a1,
a2
)
{
var a3=true;
if(_agent.isIE)
{
a3=false;
for(var a4 in _delayedEventParams)
{
a3=true;
break;
}
}
if(a3)
{
_delayedEventParams=new Object();
_delayedEventParams["reset"]=true;
}
if((typeof a0)=="string")
{
a0=document[a0];
}
else if((typeof a0)=="number")
{
a0=document.forms[a0];
}
if(!a0)
return false;
var a5=window["_"+a0.name+"Validater"];
if(a5==(void 0))
{
_saveFormForLaterSubmit(a0,a1,a2);
return false;
}
var a6=new Date();
if(_recentSubmit(a6))
{
if(_pprFirstClickPass&&_pprBlocking)
{
_saveFormForLaterSubmit(a0,a1,a2);
}
return;
}
_submitRejected=false;
_inPartialSubmit=false;
_lastDateSubmitted=a6;
if(a1==(void 0))
a1=true;
var a7=true;
if(a1&&!_validateForm(a0))
a7=false;
var a8=window["_"+a0.name+"_Submit"];
if(a8!=(void 0))
{
var a9=new Function("doValidate",a8);
a0._tempFunc=a9;
var a10=a0._tempFunc(a1);
a0._tempFunc=(void 0);
if(a1&&(a10==false))
{
a7=false;
}
}
if(a7)
{
_resetHiddenValues(a0);
var a11=_supportsDOM();
var a12=new Object();
if(a2)
{
for(var a13 in a2)
{
var a14=a2[a13];
if(a14!=(void 0))
{
var a15=a0[a13];
if(a15)
{
a15.value=a14;
}
else if(a11)
{
var a16=document.createElement("input");
a16.type="hidden";
a16.name=a13;
a16.value=a2[a13];
a0.appendChild(a16);
a12[a13]=a16;
}
}
}
}
a0.submit();
if(_blockOnEverySubmit)
_pprStartBlocking(window);
if(!_agent.isIE)
{
for(var a13 in a12)
a0.removeChild(a12[a13]);
}
}
return a7;
}
function _saveFormForLaterSubmit(a0,a1,a2)
{
_saveForm=a0;
_saveDoValidate=a1;
_saveParameters=a2;
if(a0.target==_pprIframeName)
{
_inPartialSubmit=true;
}
_submitRejected=true;
}
function _submitFormCheck()
{
if(_submitRejected)
{
if(_inPartialSubmit)
{
_submitPartialChange(_saveForm,_saveDoValidate,_saveParameters);
_inPartialSubmit=false;
}
else
{
submitForm(_saveForm,_saveDoValidate,_saveParameters);
}
_saveForm=null;
_saveDoValidate=null;
_saveParameters=null;
}
}
function resetForm(
form
)
{
var doReload=false;
if((typeof form)=="string")
{
form=document[form];
}
else if((typeof form)=="number")
{
form=document.forms[form];
}
if(!form)
return false;
var resetCallbacks=window["_"+form.name+"_Reset"];
if(resetCallbacks&&!doReload)
{
for(var i=0;i<resetCallbacks.length;i++)
{
var trueResetCallback=unescape(resetCallbacks[i]);
doReload=(eval(trueResetCallback));
}
}
if(doReload)
{
window.document.location.reload();
}
else
{
form.reset();
}
_lastDateReset=new Date();
return doReload;
}
function _resetHiddenValues(
a0
)
{
var a1=window["_reset"+a0.name+"Names"];
if(a1)
{
for(var a2=0;a2<a1.length;a2++)
{
var a3=a0[a1[a2]];
if(a3)
{
a3.value='';
}
}
}
}
function _getValue(a0)
{
var a1=a0;
var a2=a0.type
if(!a2&&a0.length)
{
for(var a3=0;a3<a0.length;a3++)
{
a2=a0[a3].type;
if(a2!=(void 0))
{
a1=a0[a3];
break;
}
}
}
if(a2=="checkbox")
{
return a0.checked;
}
else if(a2.substring(0,6)=="select")
{
a0=a1;
var a4=a0.selectedIndex;
if(a4!=(void 0)&&
a4!=null&&
a4>=0)
{
var a5=a0.options[a4];
var a6=a5.value;
if(!a6)
{
for(var a3=0;a3<a0.options.length;a3++)
{
if(a0.options[a3].value)
return a6;
}
return a5.text;
}
return a6;
}
return"";
}
else if(a2=="radio")
{
if(a0.length)
{
for(var a3=0;a3<a0.length;a3++)
{
if(a0[a3].type=="radio"&&
a0[a3].checked)
{
return a0[a3].value;
}
}
}
else
{
if(a0.checked)
{
return a0.value;
}
}
return"";
}
else
{
return a0.value;
}
}
function _setSelectIndexById(a0,a1)
{
_getElementById(document,a0).selectedIndex=a1;
}
function _multiValidate(
a0,
a1
)
{
var a2="";
if(a1&&!_recentValidation(true))
{
var a3=_getValidations(a0);
if(a3)
{
var a4=true;
for(var a5=0;a5<a1.length;a5+=4)
{
var a6=a0[a1[a5+1]];
var a7=a6.type;
if(!a7&&a6.length)
{
if(a6[0].type!="radio")
{
a6=a6[0];
}
}
var a8=a1[a5+3];
var a9=_getValue(a6);
if(!(a8&&(a9=="")))
{
var a10=_getValidationError(a6,a1[a5],
a3);
if(a10)
{
if(a4)
{
if(_isShowing(a6))
{
a6.focus();
if((a6.type=="text")
&&(a6["value"]!=(void 0))
&&(a6["value"]!=null)
&&(a6["value"].length>0))
{
if(true!=_delayedEventParams["reset"])
a6.select();
}
}
a4=false;
}
var a11=_getErrorString(a6,
a1[a5+2],
a10);
a2+='\n'+a11;
}
}
}
}
_recordValidation((a2.length>0),0);
}
return a2;
}
function _isShowing(
a0)
{
if(!a0.focus||(a0.type=='hidden'))
return false;
if(_agent.isIE)
{
var a1=a0;
while(a1!=(void 0))
{
computedStyle=a1.currentStyle;
if((computedStyle!=(void 0))&&
((computedStyle["visibility"]=="hidden")||
(computedStyle["display"]=="none")))
{
return false;
}
a1=a1.parentNode;
}
return true;
}
else if(!_agent.isNav&&!_agent.isSafari)
{
var a2=a0.ownerDocument.defaultView.getComputedStyle(a0,null);
return((a2["visibility"]!="hidden")&&
(a2["display"]!="none"));
}
}
function _getID(
a0
)
{
if(!_agent.isNav)
{
var a1=a0.id;
var a2=a0.type;
if(!a2&&a0.length)
a2=a0[0].type;
if(a2=="radio")
{
var a3;
if(a0.length)
{
a3=a0[0].parentNode;
}
else
{
a3=a0.parentNode;
}
a1=a3.id;
}
return a1;
}
else
{
var a4=_getForm(a0);
var a5=window["_"+a4.name+"_NameToID"];
if(a5)
{
var a6=_getName(a0);
return a5[a6];
}
}
}
function _getForm(
a0
)
{
var a1=a0.form;
if(a1==(void 0))
{
var a2=a0.type;
if(!a2&&a0.length)
a2=a0[0].type;
if(a2=="radio"&&a0.length)
{
a1=a0[0].form;
}
}
return a1;
}
function _getName(
a0
)
{
var a1=a0.name;
if(a1==(void 0))
{
var a2=a0.type;
if(!a2&&a0.length)
a2=a0[0].type;
if(a2=="radio"&&a0.length)
{
a1=a0[0].name;
}
}
return a1;
}
function _instanceof(
a0,
a1
)
{
if(a1==(void 0))
return false;
while(typeof(a0)=="object")
{
if(a0.constructor==a1)
return true;
a0=a0.prototype;
}
return false;
}
function _getErrorString(
a0,
a1,
a2
)
{
var a3;
var a4=_getForm(a0);
var a5=_getValue(a0);
if(_instanceof(a2,window["ParseException"]))
{
a3=a2.parseString;
}
else
{
var a6=window["_"+a4.name+"_Formats"];
if(a6)
{
a3=a6[a1];
}
}
if(a3)
{
var a7=window["_"+a4.name+"_Labels"];
var a8;
if(a7)
{
a8=a7[_getID(a0)];
}
var a9=window["_"+a4.name+"_Patterns"];
var a10;
if(a9)
{
a10=a9[_getID(a0)];
}
var a11=_formatErrorString(a3,
{
"value":a5,
"label":a8,
"pattern":a10
});
return a11;
}
}
function _getValidations(
a0
)
{
return window["_"+a0.name+"_Validations"];
}
function _getValidationError(
input,
validationIndex,
validations
)
{
if(!validations)
{
validations=_getValidations(input.form);
}
if(validations)
{
var validator=validations[validationIndex];
if(validator)
{
var trueValidator=validator.replace(/%value%/g,"_getValue(input)");
return(eval(trueValidator));
}
}
return(void 0);
}
function _formatErrorString(
a0,
a1
)
{
var a2=a0;
for(var a3 in a1)
{
var a4=a1[a3];
if(!a4)
{
a4="";
}
var a5="%"+a3+"%";
a2=a2.replace(new RegExp('{'+a3+'}','g'),
a5);
var a6=a2.indexOf(a5);
if(a6>=0)
{
a2=a2.substring(0,a6)+
a4+
a2.substring(a6+a5.length);
}
}
return a2;
}
function _chain(
a0,
a1,
a2,
a3,
a4
)
{
var a5=_callChained(a0,a2,a3);
if(a4&&(a5==false))
return false;
var a6=_callChained(a1,a2,a3);
return!((a5==false)||(a6==false));
}
function _callChained(
a0,
a1,
a2
)
{
if(a0&&(a0.length>0))
{
if(a2==(void 0))
{
a2=a1.window.event;
}
var a3=new Function("event",a0);
a1._tempFunc=a3;
var a4=a1._tempFunc(a2);
a1._tempFunc=(void 0);
return!(a4==false);
}
else
{
return true;
}
}
function _checkLength(a0,a1,a2)
{
elementLength=a0.value.length;
if(elementLength>a1)
{
a0.value=a0.value.substr(0,a1);
return true;
}
if(elementLength<a1)
return true;
if(_agent.isIE)
{
if(a2["type"]=="hidden")
a2=window.event;
}
if(a2.type=='change')
return true;
if(a2)
{
if((a2.which<32)
||((a2.which==118)&&(a2["ctrlKey"])))
return true;
}
return false;
}
function _getElementById(
a0,
a1
)
{
if((_agent.kind!="ie")||(_agent.version>=5))
{
var a2=a0.getElementById(a1);
if((a2==null)||(a2.id==a1))
return a2;
return _findElementById(a0,a1);
}
else
{
return a0.all[a1];
}
}
function _findElementById(
a0,
a1
)
{
if(a0.id==a1)
return a0;
if(a0.childNodes)
{
var a2=a0.childNodes;
for(var a3=0;a3<a2.length;a3++)
{
var a4=_findElementById(a2.item(a3),a1);
if(a4!=null)
return a4;
}
}
return null;
}
function _getQuerySeparator(a0)
{
var a1=a0.charAt(a0.length-1);
if((a1=='&')||(a1=='?'))
return"";
return(a0.indexOf('?')>=0)?'&':'?';
}
function _addParameter(
a0,
a1,
a2
)
{
var a3=a0.indexOf('?');
if(a3==-1)
{
return a0+'?'+a1+'='+a2;
}
else
{
var a4=a0.indexOf('?'+a1+'=',a3);
if(a4==-1)
a4=a0.indexOf('&'+a1+'=',a3+1);
if(a4==-1)
{
return a0+'&'+a1+'='+a2;
}
else
{
var a5=a4+a1.length+2;
var a6=a0.substring(0,a5);
a6+=a2;
var a7=a0.indexOf('&',a5);
if(a7!=-1)
{
a6+=a0.substring(a7);
}
return a6;
}
}
}
function _addFormParameter(
a0,
a1,
a2
)
{
var a3=new Object();
if(a0)
{
for(var a4 in a0)
a3[a4]=a0[a4];
}
a3[a1]=a2;
return a3;
}
function _firePCUpdateMaster(
a0,
a1,
a2,
a3
)
{
var a4=a1+'_dt';
var a5=window[a4];
if(a5!=a0.id)
{
window[a4]=a0.id;
if(a5)
{
var a6=_getElementById(document,a5);
if(a6)
{
_updateDetailIcon(a6,'/marlin/cabo/images/cache/c-sdtl.gif');
}
}
_updateDetailIcon(a0,'/marlin/cabo/images/cache/c-dtl.gif');
_firePartialChange(a2,a3);
}
}
function _updateDetailIcon(
a0,
a1
)
{
a0.firstChild.src=a1;
}
function _firePartialChange(a0)
{
var a1=_addParameter(a0,
_getPartialParameter(),
"true");
var a2=_getElementById(document,_pprIframeName);
_pprRequestCount++;
_pprStartBlocking(window);
if(_agent.isIE)
{
a2.contentWindow.location.replace(a1);
}
else
{
a2.contentDocument.location.replace(a1);
}
}
function _submitPartialChange(
a0,
a1,
a2
)
{
if((typeof a0)=="string")
a0=document[a0];
if(!a0)
return false;
a2=_addFormParameter(a2,_getPartialParameter(),"true");
var a3=a0.target;
a0.target=_pprIframeName;
_pprRequestCount++;
var a4=0;
if((!_agent.isIE)||_pprSomeAction)
{
a4=1;
}
_pprSubmitCount+=a4;
_pprSomeAction=true;
_pprStartBlocking(window);
var a5=submitForm(a0,a1,a2);
if(!a5)
{
_pprStopBlocking(window);
_pprRequestCount--;
_pprSubmitCount-=a4;
}
a0.target=a3;
}
function _getPartialParameter()
{
if(window._pprPartialParam)
return window._pprPartialParam;
return"partial";
}
function _setOuterHTML(
a0,
a1,
a2
)
{
var a3=a2.tagName;
if(_agent.isIE||_agent.isSafari)
{
var a4=true;
var a5=((a3=="TD")||
(a3=="TH")||
(a3=="CAPTION"));
var a6=!a5&&
((a3=="COL")||
(a3=="COLGROUP")||
(a3=="TR")||
(a3=="TFOOT")||
(a3=="THEAD")||
(a3=="TBODY"));
if(a5||a6)
{
var a7=a0.createElement(a3);
a7.mergeAttributes(a2,false);
if(a5)
{
a7.innerHTML=a2.innerHTML;
}
else
{
if(a6)
{
var a8=a2.firstChild;
while(a8!=null)
{
while(a8!=null&&a8.tagName=="!")
{
a8=a8.nextSibling;
}
a7.appendChild(_setOuterHTML(a0,
null,
a8));
a8=a8.nextSibling;
}
}
}
if(a1)
{
a1.parentNode.replaceChild(a7,a1);
}
else
{
a1=a7;
}
a4=false;
}
if(a4)
{
a1.outerHTML=a2.outerHTML;
}
}
else
{
var a7;
if(a3!='TR')
{
a7=a0.createElement(a3);
if(a3=='SELECT')
{
if(a2.multiple)
{
a7.multiple=a2.multiple;
}
for(var a9=0;a9<a2.options.length;a9++)
{
var a10=a2.options[a9];
var a11=new Option();
a11.value=a10.value;
a11.text=a10.text;
a11.selected=a10.selected;
a7.options[a9]=a11;
}
}
else
{
var a12=a2.innerHTML;
if((a12!=null)&&(a12.length>0))
{
a7.innerHTML=a2.innerHTML;
}
}
var a13=a2.attributes;
for(var a9=0;a9<a13.length;a9++)
{
a7.setAttribute(a13[a9].name,a13[a9].value);
}
}
else
{
a7=a0.importNode(a2,true);
}
a1.parentNode.insertBefore(a7,a1);
a1.parentNode.removeChild(a1);
}
return a1;
}
function _partialUnload()
{
if((parent._pprRequestCount<=0)&&!parent._pprUnloaded)
{
_pprStopBlocking(parent);
if(!(_agent.isIE)&&(parent.document.referrer!=null))
{
parent.history.go(parent.document.referrer);
}
else
{
var a0=-1;
if(_agent.isIE)
{
if(parent._pprSomeAction)
{
a0=-(parent._pprSubmitCount);
}
}
else if(parent._pprSubmitCount&&(parent._pprSubmitCount>0))
{
a0-=parent._pprSubmitCount;
}
parent._pprSubmitCount=0;
parent._pprSomeAction=false;
if(a0<0)
{
parent.history.go(a0);
}
}
}
}
function _partialRedirect(a0)
{
if(a0&&(parent._pprRequestCount>0))
{
if(((typeof a0)=="string")&&(a0.length>0))
{
parent._pprRequestCount--;
parent._pprSubmitCount=0;
parent._pprSomeAction=false;
parent.location.href=a0;
_pprStopBlocking(parent);
}
}
}
function _pprLibraryStore(a0)
{
this.loadedStatus=new Array(a0);
for(var a1=0;a1<a0;a1++)
this.loadedStatus[a1]=false;
this.total=a0;
this.allLibraries=new Array(a0);
}
var _pprLibStore;
function _pprExecScript(a0,a1)
{
if(_pprLibStore&&_pprLibStore.allLibraries!=(void 0))
{
_pprLibStore.allLibraries[a0]=a1;
_pprLibStore.loadedStatus[a0]=true;
for(var a0=_pprLibStore.total-1;a0>=0;a0--)
{
if(!_pprLibStore.loadedStatus[a0])
return;
}
for(var a2=0;a2<_pprLibStore.total;a2++)
{
var a3=parent;
if("_pprIFrame"!=window.name)
{
a3=window;
}
a3.execScript(_pprLibStore.allLibraries[a2]);
}
_pprLibStore=null;
}
}
function _createToLoadArray()
{
var a0=new Array();
var a1=0;
if(window["_pprLibraries"]!=(void 0))
{
for(var a2=0;a2<_pprLibraries.length;a2++)
{
if((parent._cachedLibs==null)
||(parent._cachedLibs.indexOf(_pprLibraries[a2])==-1))
{
a0[a1++]=_pprLibraries[a2];
}
}
}
return a0;
}
function _addLibraryToCache(a0)
{
if((a0.indexOf("ScriptEval"))==-1)
{
if(parent._cachedLibs==null)
parent._cachedLibs=""+a0;
else
parent._cachedLibs+=","+a0;
}
}
function _loadScriptLibrariesIE(a0,a1)
{
if(a1==null)return;
var a2=_getElementById(a0,"_uixDownload");
if(a2==null)return;
var a3=a1.length;
_pprLibStore=new _pprLibraryStore(a3);
for(var a4=0;a4<a3;a4++)
{
var a5="_pprExecScript("+a4+", s);"
var a6=new Function("s",a5);
a2.startDownload(a1[a4],a6);
_addLibraryToCache(a1[a4]);
}
}
function _loadScriptLibrariesGecko(a0,a1)
{
var a2=_getElementById(a0,_pprIframeName);
if(a2)
{
for(var a3=0;(a3<a1.length);a3++)
{
var a4=a0.createElement("script");
a4.setAttribute('src',a1[a3]);
a2.parentNode.insertBefore(a4,a2);
_addLibraryToCache(a1[a3]);
}
}
}
function _loadScriptLibraries(a0)
{
if(window["_pprLibraries"]!=(void 0))
{
var a1=_createToLoadArray();
if(a1.length>0)
{
if(_agent.isIE)
{
_loadScriptLibrariesIE(a0,a1);
}
else
{
_loadScriptLibrariesGecko(a0,a1);
}
}
}
}
function _pprCopyObjectElement(a0,a1)
{
var a2=0;
while(true)
{
var a3="_pprObjectScript"+a2;
var a4=_getElementById(a0,
a3);
if(a4==null)
break;
else
{
var a5=_getCommentedScript(a0,
a3);
}
if(a5!=null)
{
var a6="_pprObjectSpan"+a2;
var a7=_getElementById(a1,
a6);
if(a7!=null)
a7.outerHTML=a5;
}
a2++;
}
}
function _partialChange(a0)
{
if(parent._pprRequestCount<=0)
return;
parent._pprRequestCount--;
parent._pprSomeAction=true;
if(a0)
_fixAllLinks(a0,parent);
var a1=document;
var a2=parent.document;
var a3=_getParentActiveElement();
var a4=null;
var a5=false;
for(var a6=0;a6<_pprTargets.length;a6++)
{
var a7=_pprTargets[a6];
var a8=_getElementById(a1,a7);
var a9=_getElementById(a2,a7);
if(a8&&a9)
{
var a10=_isDescendent(a3,a9);
_setOuterHTML(a2,a9,a8);
if((a10)&&(a4==null))
{
a9=_getElementById(a2,a9.id);
a4=_getNewActiveElement(a2,
a9,
a3);
if(a4==null)
{
a4=_getFirstFocusable(a9);
if(a4!=null)
a5=true;
}
parent._pprEventElement=null;
}
}
}
_pprCopyObjectElement(a1,a2);
_loadScriptLibraries(a2);
_saveScripts(a2);
var a11=_getElementById(a2,"_pprSaveFormAction");
if(a11)
a11.value=document.forms[0].action;
_pprStopBlocking(parent);
var a12=_getRequestedFocusNode(parent);
if(a12!=null)
a4=a12;
_restoreFocus(a4,a5,a2);
_setRequestedFocusNode(null,null,false,parent);
_updateFormActions(a1,a2);
if(_pprFirstClickPass||parent._pprFirstClickPass)
{
_eval(parent,"_submitFormCheck();");
}
}
function _setRequestedFocusNode(a0,a1,a2,a3)
{
if(a3==(void 0))
a3=window;
a3._UixFocusRequestDoc=a0;
a3._UixFocusRequestID=a1;
a3._UixFocusRequestNext=(a2==true);
}
function _getRequestedFocusNode(a0)
{
if(a0==(void 0))
a0=window;
if((a0._UixFocusRequestDoc!=null)
&&(a0._UixFocusRequestID!=null))
{
var a1=_getElementById(a0._UixFocusRequestDoc,
a0._UixFocusRequestID);
if(!a1)
return null;
if(a0._UixFocusRequestNext)
{
for(var a2=a1.nextSibling;
a2!=null;
a2=a2.nextSibling)
{
if(_isFocusable(a2)
||((_agent.isIE)&&(a2.nodeName.toLowerCase()=='a')))
{
a1=a2;
break;
}
}
}
return a1;
}
return null;
}
function _fullChange()
{
if(parent._pprRequestCount>0)
{
parent._pprRequestCount--;
var a0=_getElementById(document,"_pprDisableWrite");
a0.text="var _pprDocumentWrite = document.write;"+
"var _pprDocumentWriteln = document.writeln;"+
"document.write = new Function('return;');"+
"document.writeln = new Function('return;');";
var a1=_getElementById(document,"_pprEnableWrite");
a1.text="document.write = _pprDocumentWrite;"+
"document.writeln = _pprDocumentWriteln";
var a2=document.body;
var a3=a2.getAttribute("onload");
var a4=a2.getAttribute("onunload");
a2.setAttribute("onload",
_getCommentedScript(document,("_pprFullOnload")));
a2.setAttribute("onunload",
_getCommentedScript(document,("_pprFullOnunload")));
var a5=_getDocumentContent();
var a6=
new RegExp("<script id=[\"]*_pprFullChange.*>_fullChange\\(\\)</script>","i");
a5=a5.replace(a6,"");
a2.setAttribute("onload",a3);
a2.setAttribute("onunload",a4);
var a7=parent.document;
if(_agent.isIE)
{
var a8=document.charset;
a7.open();
a7.charset=a8;
}
a7.write(a5);
a7.close();
}
}
function _updateFormActions(a0,a1)
{
var a2=a0.forms;
for(var a3=0;a3<a2.length;a3++)
{
var a4=a2[a3];
if(a4.hasChildNodes())
{
var a5=a4.name;
var a6=a4.action;
var a7=a1.forms[a5];
if(a7)
{
var a8=a7.action;
if(a8!=a6)
a7.action=a6;
}
}
}
}
function _getParentActiveElement()
{
if(parent.document.activeElement)
{
_eval(parent,"_saveActiveElement()");
return parent._pprActiveElement;
}
return null;
}
function _saveActiveElement()
{
if(window._pprEventElement)
window._pprActiveElement=window._pprEventElement;
else if(document.activeElement)
window._pprActiveElement=document.activeElement;
else
window._pprActiveElement=null;
}
function _getNewActiveElement(a0,a1,a2)
{
if(a2.id)
{
var a3=_getElementById(a0,
a2.id);
if(_isFocusable(a3))
return a3;
}
return null;
}
function _getFirstFocusable(a0)
{
if((a0==null)||_isFocusable(a0))
return a0;
if(a0.hasChildNodes)
{
var a1=a0.childNodes;
for(var a2=0;a2<a1.length;a2++)
{
var a3=a1[a2];
var a4=_getFirstFocusable(a3);
if(a4!=null)
return a4;
}
}
return null;
}
function _restoreFocus(a0,a1,a2)
{
if(a0==null)
return;
var a3=_getAncestorByName(a0,"DIV");
if(!a3)
{
_pprFocus(a0,a2);
}
else
{
var a4=a3.scrollTop;
var a5=a3.scrollLeft;
if(((a4==0)&&(a5==0))||!a1)
{
_pprFocus(a0,a2);
}
}
if((_agent.isIE)
&&(a0.tagName=='INPUT')
&&(_getAncestorByName(a0,'TABLE')))
{
_pprFocus(a0,a2);
}
}
function _getAncestorByName(
a0,
a1
)
{
a1=a1.toUpperCase();
while(a0)
{
if(a1==a0.nodeName)
return a0;
a0=a0.parentNode;
}
return null;
}
function _isDescendent(
a0,
a1
)
{
if(a0==null)
return false;
while(a0.parentNode)
{
if(a0==a1)
return true;
a0=a0.parentNode;
}
return false;
}
function _isFocusable(a0)
{
if(a0==null)
return false;
var a1=a0.nodeName.toLowerCase();
if(('a'==a1)&&(a0.href))
{
if(!_agent.isIE||(a0.id))
return true;
var a2=a0.childNodes;
if((a2)&&(a2.length==1))
{
var a3=a2[0].nodeName;
if('img'==a3.toLowerCase())
return false;
}
return true;
}
if(a0.disabled)
return false;
if('input'==a1)
{
return(a0.type!='hidden');
}
return(('select'==a1)||
('button'==a1)||
('textarea'==a1));
}
function _getCommentedScript(a0,a1)
{
var a2=_getElementById(a0,a1);
if(a2!=null)
{
var a3;
if(_agent.isSafari)
a3=a2.innerHTML;
else
a3=a2.text;
var a4=0;
var a5=a3.length-1;
while(a4<a5)
{
if(a3.charAt(a4)=='*')
break;
a4++;
}
while(a5>a4)
{
if(a3.charAt(a5)=='*')
break;
a5--;
}
return a3.substring(a4+1,a5);
}
return null;
}
function _eval(targetWindow,code)
{
if(code==null)
return;
if(_agent.isIE)
targetWindow.execScript(code);
else
targetWindow.eval(code);
}
function _getDocumentContent()
{
if(_agent.isIE)
return document.documentElement.outerHTML;
var a0="<html"
var a1=document.documentElement.attributes;
for(var a2=0;a2<a1.length;a2++)
{
a0+=" ";
a0+=a1[a2].name;
a0+="=\""
a0+=a1[a2].value;
a0+="\"";
}
a0+=">";
a0+=document.documentElement.innerHTML;
a0+="</html>";
return a0;
}
function _fixAllLinks(a0,a1,a2)
{
_initialFormState=_getFormState(a0,a2);
_initialFormStateName=a0;
if(a2!=(void 0))
_initialFormExclude=a2;
if(window!=a1)
{
if(a1._initialFormState==null)
a1._initialFormState=new Object();
var a3=_initialFormState;
var a4=a1._initialFormState;
for(var a5 in a3)
a4[a5]=a3[a5];
}
var a6=document.links;
var a7=a1.location.href+'#';
var a8=location.href+'#';
for(var a9=0;a9<a6.length;a9++)
{
var a10=a6[a9].href;
if(!a10
||(a10.substr(0,a8.length)==a8)
||(a10.substr(0,a7.length)==a7)
||(a10.substr(0,11).toLowerCase()=="javascript:")
||(a10.substr(0,7).toLowerCase()=="mailto:")
||(a10.indexOf("_noSv=M")>=0))
{
continue;
}
if(a6[a9].target)
{
continue;
}
var a11=a10.split("'");
a10=a11[0];
for(var a12=1;a12<a11.length;a12++)
a10=a10+"\\'"+a11[a12];
if(!_agent.isNav)
a10=escape(a10);
a6[a9].href="javascript:_submitNav('"+a0+"','"+a10+"')";
}
}
function _isInExclude(a0,a1)
{
if(a0!=(void 0))
{
if(a0[a1]!=(void 0))
return true;
var a2=a1.lastIndexOf(':');
if(a2<0)
return false;
return _isInExclude(a0,a1.substring(0,a2));
}
return false;
}
function _getFormState(a0,a1)
{
var a2=new Object();
var a3=document[a0];
for(var a4=0;a4<a3.length;a4++)
{
var a5=a3.elements[a4].name;
if(a5)
{
var a6=a3[a5];
if(a6)
{
if((a1!=(void 0))&&_isInExclude(a1,a5))
continue;
if(!a6.type||(a6.type!='hidden'))
a2[a5]=_getValue(a6);
}
}
}
return a2;
}
function isNavDirty()
{
var a0=false;
if(_navDirty)
a0=true;
else
{
var a1=_getFormState(_initialFormStateName,_initialFormExclude);
for(var a2 in a1)
{
if(a1[a2]!=_initialFormState[a2])
{
a0=true;
break;
}
}
}
return a0;
}
function _addNavExclude(a0)
{
if(_initialFormExclude!=(void 0))
_initialFormExclude[a0]=1;
else
{
_initialFormExclude=new Object();
_initialFormExclude[a0]=1;
}
}
function _submitNav(a0,a1)
{
if(isNavDirty())
{
var a2=window["_onNavigate"];
if((a2==(void 0))||!(a2(a0,a1)==false))
{
var a3=window['_navEvent'];
if(a3==(void 0))
a3='navigate';
submitForm(a0,0,{'event':a3,'uri':a1});
}
}
else
document.location.href=a1;
}
function _getInputField(a0)
{
var a1=(void 0);
var a2=(void 0);
if(window.event)
{
kc=window.event.keyCode;
a2=window.event.srcElement;
}
else if(a0)
{
kc=a0.which;
a2=a0.target;
}
if(a2!=(void 0)
&&(a2.tagName=="INPUT"||
a2.tagName=="TEXTAREA"))
a1=a2;
return a1;
}
function _enterField(
a0
)
{
var a1;
var a2;
var a3=true;
var a1=_getInputField(a0);
if(a1!=(void 0))
{
a1.form._mayResetByInput=false;
if(a1!=window._validating)
{
a1._validValue=a1.value;
}
a3=false;
}
return a3;
}
function _resetOnEscape(a0)
{
var a1;
var a2=_getInputField(a0);
if(a2!=(void 0))
{
var a3=a2.form;
if(a1==27)
{
var a4=false;
if((a2.selectionStart!=(void 0))&&
(a2.selectionEnd!=(void 0)))
{
a4=(a2.selectionStart!=a2.selectionEnd);
}
else if(document.selection)
{
a4=(document.selection.createRange().text.length!=0);
}
if(!a4)
{
a2.value=a2._validValue;
if(a3._mayResetByInput==true)
{
a3.reset();
a3._mayResetByInput=false;
}
else
{
a3._mayResetByInput=true;
}
}
return false;
}
else
{
a3._mayResetByInput=false;
}
}
return true;
}
function _checkLoad(
a0,
a1,
a2
)
{
restorePartialPageState();
for(var a3=0;a3<document.forms.length;a3++)
{
var a4=document.forms[a3];
if(a4.addEventListener)
{
a4.addEventListener('focus',_enterField,true);
a4.addEventListener('keydown',_resetOnEscape,true);
}
else if(a4.attachEvent)
{
a4.attachEvent('onfocusin',_enterField);
a4.attachEvent('onkeydown',_resetOnEscape);
}
}
if(a1!=(void 0))
{
var a5;
if(_initialFormExclude!=(void 0))
a5=_initialFormExclude;
else
a5=new Object();
if(a2!=(void 0))
{
for(var a6=0;a6<a2.length;a6++)
a5[a2[a6]]=1;
}
_fixAllLinks(a1,window,a5);
}
if((self!=top)&&top["_blockReload"])
{
if((_agent.isIE)
&&(document.onkeydown!=null)
&&(((document.onkeydown).toString().indexOf('_monitor'))>0))
{
document.onkeydown=_monitorNoReload;
}
else
{
document.onkeydown=_noReload;
}
}
if((!_agent.isNav)&&(_initialFocusID!=null))
{
var a7=_getElementById(document,_initialFocusID);
if(a7&&a7.focus)
{
a7.focus();
if(a7.type=='text')
a7.select();
}
}
if(!_agent.isNav)
_loadScriptLibraries(document);
}
function _noReload(a0)
{
if(!a0)a0=window.event;
var a1=a0.keyCode;
if((a1==116)||(a1==82&&a0.ctrlKey))
{
if(a0.preventDefault)a0.preventDefault();
a0.keyCode=0;
return false;
}
}
function _monitorNoReload(a0)
{
if(_agent.isIE)
_monitor(a0);
return _noReload(a0);
}
function _handleClientEvent(a0,a1,a2,a3)
{
var a4=new Object();
a4.type=a0;
a4.source=a1;
a4.params=a2;
var a5=new Function("event",a3);
return a5(a4);
}
function _getCookie(a0)
{
var a1=document.cookie;
var a2="";
var a3=a0+"=";
if(a1)
{
var a4=a1.indexOf("; "+a3);
if(a4<0)
{
a4=a1.indexOf(a3);
if(a4>0)
a4=-1;
}
else
a4+=2;
if(a4>=0)
{
var a5=a1.indexOf(";",a4);
if(a5<0)
a5=a1.length;
a2=unescape(a1.substring(a4+a0.length+1,a5));
}
}
return a2;
}
function _setCookie(a0,a1)
{
var a2=window.location.host;
var a3=a2.indexOf(":");
if(a3>=0)
a2=a2.substr(0,a3);
var a4=new Date();
a4.setFullYear(a4.getFullYear()+10);
var a5=a0+"="+a1+
"; path=/;domain="+a2+"; expires="+a4.toGMTString();
document.cookie=a5;
}
function _setUIXCookie(a0,a1)
{
var a2=_getUIXCookie();
a2[a0]=a1;
var a3=a2[0];
for(var a4=1;a4<a2.length;a4++)
{
a3=a3+"^"+a2[a4];
}
_setCookie("oracle.uix",a3);
}
function _getUIXCookie()
{
var a0=_getCookie("oracle.uix");
var a1;
if(a0)
a1=a0.split("^");
else
a1=new Array("0","","");
return a1;
}
function _defaultTZ()
{
var a0=_getUIXCookie()[2];
if(a0&&(a0.indexOf("GMT")!=0))
{
return;
}
_setUIXCookie(2,_getTimeZoneID());
}
function _getTimeZoneID()
{
var a0=-(new Date()).getTimezoneOffset();
var a1;
if(a0>0)
a1="GMT+";
else
{
a1="GMT-";
a0=-a0;
}
var a2=""+a0%60;
if(a2.length==1)
a2="0"+a2;
return(a1+(Math.floor(a0/60))+":"+a2);
}
function _monitor(a0)
{
var a1=window.event;
if((a1.altKey==true)&&(a1.ctrlKey==false)&&
(a1.keyCode!=null)&&(a1.keyCode!=18)
&&(!a1.repeat))
{
var a2=String.fromCharCode(window.event.keyCode);
var a3=_getNodeWithAccessKey(document,a2);
if(a3!=null&&(a3.getAttribute("uixbtn")!=null))
{
if(a3.htmlFor)
{
var a4=a3.htmlFor;
a3=(a4!=null)
?window.document.getElementById(a4)
:null;
}
if(a3!=null)
{
a3.focus();
a3.click();
}
}
}
return true;
}
function _getNodeWithAccessKey(a0,a1)
{
var a2=a1.toUpperCase();
var a3=a1.toLowerCase();
var a4=
{
activeFound:false,
firstAccessKeyNode:null,
accessKeyNode:null
}
a4=_findAccessKey(document,
a4,
a2,
a3);
var a5=a4.accessKeyNode;
var a6=a4.firstAccessKeyNode;
if((a5==null)&&(a6!=null))
{
a5=a6;
}
return a5;
}
function _findAccessKey(a0,a1,a2,a3)
{
if(a0.nodeType==1)
{
if((a0.accessKey==a2)||
(a0.accessKey==a3))
{
if(a1.activeFound==true)
{
a1.accessKeyNode=a0;
return a1;
}
else if(a1.firstAccessKeyNode==null)
{
a1.firstAccessKeyNode=a0;
}
}
if(a0==document.activeElement)
{
a1.activeFound=true;
}
}
var a4=a0.childNodes;
for(var a5=0;a5<a4.length;a5++)
{
var a1=
_findAccessKey(a4[a5],
a1,
a2,
a3);
if(a1.accessKeyNode!=null)
{
return a1;
}
}
return a1;
}
function _isEmpty(a0)
{
var a1=""+a0;
var a2=0;
while(a2<a1.length)
{
if(a1.charAt(a2)!=' ')
return false;
a2++;
}
return true;
}
function _isLTR()
{
return document.documentElement["dir"].toUpperCase()=="LTR";
}
function _pprConsumeFirstClick(a0)
{
if(_agent.isIE)
{
_pprControlCapture(window,true);
window.document.detachEvent('onclick',_pprConsumeFirstClick);
}
return false;
}
function _pprControlCapture(a0,a1)
{
if(_agent.isIE)
{
var a2=a0.document;
var a3=a2.body;
var a4=_getElementById(a2,_pprdivElementName);
if(a4)
{
if(a1)
{
a4.setCapture();
if(a0._pprEventElement)
a4.focus();
a0._pprSavedCursor=a3.style.cursor;
a3.style.cursor="wait";
a0._pprSavedCursorFlag=true;
}
else if(a0._pprSavedCursorFlag)
{
a4.releaseCapture();
if(a0._pprEventElement)
a0._pprEventElement.focus();
a3.style.cursor=a0._pprSavedCursor;
a0._pprSavedCursor=null;
a0._pprSavedCursorFlag=false;
}
}
}
return;
}
function _pprConsumeBlockedEvent(a0)
{
var a1=true;
if(_pprBlocking)
{
var a2=true;
if(window._pprFirstClickPass)
{
var a3=new Date();
var a4=a3-_pprBlockStartTime;
var a5=150;
if((a4<a5)&&(a0.type=='click'))
{
var a6=a0.explicitOriginalTarget;
a2=!_isSubmittingElement(a6);
}
}
if(a2)
{
a0.stopPropagation();
a0.preventDefault();
a1=false;
}
}
return a1;
}
function _isSubmittingElement(a0)
{
var a1=false;
var a2=a0.nodeName.toUpperCase();
if(a2=="BUTTON")
{
a1=true;
}
else if(a2=="IMG")
{
var a3=a0.parentNode;
var a4=a3.nodeName.toUpperCase();
if(('A'==a4)&&(a3.href))
{
var a5=""+a3["onclick"];
if((a5!=(void 0))&&(a5!=null))
{
a1=((a5.indexOf("submitForm")>0)
||(a5.indexOf("_uixspu")>0)
||(a5.indexOf("_addRowSubmit")>0));
}
}
}
return a1;
}
function _pprConsumeClick(a0)
{
if(_agent.isIE)
{
var a1=document.body;
if((a0.x<a1.offsetLeft)||(a0.y<a1.offsetTop)
||(a0.x>a1.offsetWidth)||(a0.y>a1.offsetHeight))
{
_pprStopBlocking(window);
}
}
return false;
}
function _pprInstallBlockingHandlers(a0,a1)
{
var a2=a0.document;
if(a2==(void 0))
return;
if(_agent.isIE)
{
var a3=a0._pprConsumeFirstClick;
if(a1)
{
var a4=a0.event;
if(a4!=(void 0))
{
var a5=document.elementFromPoint(a4.x,a4.y);
if(!a0._pprFirstClickPass
||(((a4.type=='change')||(a4.type=='blur'))
&&(a4.srcElement==a5))
||(!_isSubmittingElement(a5)))
{
_pprControlCapture(a0,true);
return;
}
}
a2.attachEvent('onclick',a3);
}
else
{
a2.detachEvent('onclick',a3);
_pprControlCapture(a0,false);
}
}
else
{
var a3=a0._pprConsumeBlockedEvent;
var a6={'click':1,'keyup':1,'keydown':1,'keypress':1};
for(var a7 in a6)
{
if(a1)
a2.addEventListener(a7,a3,true);
else
a2.removeEventListener(a7,a3,true);
}
}
}
function _pprStartBlocking(a0)
{
if(!a0._pprBlocking)
{
var a1=a0.document.body;
a0._pprBlockStartTime=new Date();
if(_agent.isGecko)
{
if(a0._pprBlockingTimeout!=null)
{
a0.clearTimeout(a0._pprBlockingTimeout);
}
a0._pprBlockingTimeout=a0.setTimeout("_pprStopBlocking(window);",
8000);
}
else if(_agent.isIE)
{
_pprEventElement=window.document.activeElement;
}
_pprInstallBlockingHandlers(a0,true);
a0._pprBlocking=true;
}
}
function _pprStopBlocking(a0)
{
var a1=a0.document;
if(a0._pprBlocking)
{
if(_agent.isGecko)
{
if(a0._pprBlockingTimeout!=null)
{
a0.clearTimeout(a0._pprBlockingTimeout);
a0._pprBlockingTimeout==null;
}
}
_pprInstallBlockingHandlers(a0,false);
a0._pprEventElement=null;
a0._pprBlocking=false;
}
a0._pprBlocking=false;
}
function _pprChoiceAction()
{
if(!_agent.isIE)
return true;
var a0=false;
if((!window._pprBlocking)&&(_pprChoiceChanged))
{
_pprChoiceChanged=false;
a0=true;
}
return a0;
}
function _pprChoiceChangeEvent(a0)
{
if(!_agent.isIE)
return true;
if(!window._pprBlocking)
_pprChoiceChanged=true;
return true;
}
function _getKC(a0)
{
if(window.event)
return window.event.keyCode;
else if(a0)
return a0.which;
return-1;
}
function _isRecent(a0,a1)
{
if(a0)
{
var a2=a1-a0;
if((a2>=0)&&(a2<200))
return true;
}
return false;
}
function _recentSubmit(a0)
{
_isRecent(_lastDateSubmitted,a0);
}
function _recentReset(a0)
{
return _isRecent(_lastDateReset,a0);
}
function _pprFocus(a0,a1)
{
if(_agent.isIE)
{
var a2=_getElementById(a1,_pprdivElementName);
if((a2)&&(a2["focus"]))
a2.focus();
}
a0.focus();
}
function _savePageStateIE()
{
if(!_agent.isIE)
return;
var a0=_getElementById(document,"_pprPageContent");
if(a0==null)
return;
var a1=_getElementById(document,"_pprSaveLib");
if(!(a1!=null&&a1.value!=""))
{
return;
}
var a2=_getElementById(document,"_pprSavePage");
if(a2==null)
return;
a2.value=a0.outerHTML;
}
function _saveScripts(a0)
{
if(!_agent.isIE)
return;
var a1=_getElementById(a0,"_pprSaveScript");
if(a1!=null)
{
var a2=_getCommentedScript(document,"_pprScripts");
a1.value=
a1.value+" "+a2;
}
var a3=_getElementById(a0,"_pprSaveLib");
if(a3!=null&&(window["_pprLibraries"]!=(void 0)))
{
for(var a4=0;(a4<_pprLibraries.length);a4++)
{
if(a3.value.indexOf(_pprLibraries[a4])==-1)
{
if(a3.value!="")
a3.value+=","+_pprLibraries[a4];
else
a3.value+=_pprLibraries[a4];
}
}
}
}
function restorePartialPageState()
{
if(!_agent.isIE)
return;
var a0=_getElementById(document,"_pprSavePage");
if(a0==null||a0.value=="")
return;
var a1=_getElementById(document,"_pprPageContent");
if(a1==null)
return;
a1.outerHTML=a0.value;
var a2=_getElementById(document,"_pprSaveFormAction");
if(a2==null)
{
_pprBackRestoreInlineScripts=true;
var a3=_getElementById(document,"_pprSaveLib");
if(a3!=null&&a3.value!="")
{
var a4=a3.value.split(",");
_loadScriptLibrariesIE(document,a4);
}
}
else
{
if(a2.value)
document.forms[0].action=a2.value;
submitForm(0,0,{'event':'stateSynch','source':'__unknown__'});
}
}
function _setNavDirty(a0,a1)
{
var a2=a0;
if(a2==(void 0)||!a2)
{
a2=window;
}
var a3=a2._initialFormExclude;
if((a1==(void 0))
||!a1
||!_isInExclude(a3,a1))
{
a2._navDirty=true;
}
}
function _radio_uixSpuOnClickHandler(a0,a1)
{
var a2=new Date();
if(_isRecent(_lastEventTime,a2))
{
return;
}
else
{
_lastEventTime=a2;
_radioUserDefScript=a0;
_radioActionScript=a1;
if(a0||a1)
{
var a3;
if(a0&&a1)
{
a3=("_chain(_radioUserDefScript,_radioActionScript,"+
"this,(void 0),true);");
}
else
{
a3=("_callChained( "
+((a0)
?"_radioUserDefScript"
:"_radioActionScript")
+",this,(void 0))");
}
window.setTimeout(a3,200);
}
}
}
var _AD_ERA=void 0;
var _dfLenient;
function _getADEra()
{
if(_AD_ERA==(void 0))
{
_AD_ERA=new Date(0);
_AD_ERA.setFullYear(1);
}
return _AD_ERA;
}
function _simpleDateFormat(
a0
)
{
var a1=new Object();
a1.value="";
var a2=this._pattern;
if(typeof a2!="string")
a2=a2[0];
_doClumping(a2,
this._localeSymbols,
_subformat,
a0,
a1);
return a1.value;
}
function _simpleDateParse(
a0
)
{
var a1=this._pattern;
if(typeof a1=="string")
{
return _simpleDateParseImpl(a0,
a1,
this._localeSymbols);
}
else
{
var a2;
for(a2=0;a2<a1.length;a2++)
{
var a3=_simpleDateParseImpl(a0,
a1[a2],
this._localeSymbols);
if(a3!=(void 0))
return a3;
}
}
}
function _simpleDateParseImpl(
a0,
a1,
a2)
{
var a3=new Object();
a3.currIndex=0;
a3.parseString=a0;
a3.parsedHour=(void 0);
a3.parsedMinutes=(void 0);
a3.parsedSeconds=(void 0);
a3.parsedMilliseconds=(void 0);
a3.isPM=false;
a3.parsedBC=false;
a3.parsedFullYear=(void 0);
a3.parsedMonth=(void 0);
a3.parsedDate=(void 0);
a3.parseException=new ParseException();
var a4=new Date(0);
a4.setDate(1);
if(_doClumping(a1,
a2,
_subparse,
a3,
a4))
{
if(a0.length!=a3.currIndex)
{
return(void 0);
}
var a5=a3.parsedFullYear;
if(a5!=(void 0))
{
if(a3.parsedBC)
{
a5=_getADEra().getFullYear()-a5;
}
a4.setFullYear(a5);
a3.parsedFullYear=a5;
}
var a6=a3.parsedMonth;
if(a6!=(void 0))
a4.setMonth(a6);
var a7=a3.parsedDate;
if(a7!=(void 0))
a4.setDate(a7);
var a8=a3.parsedHour;
if(a8!=(void 0))
{
if(a3.isPM&&(a8<12))
{
a8+=12;
}
a4.setHours(a8);
a3.parsedHour=a8;
}
var a9=a3.parsedMinutes;
if(a9!=(void 0))
a4.setMinutes(a9);
var a10=a3.parsedSeconds;
if(a10!=(void 0))
a4.setSeconds(a10);
var a11=a3.parsedMilliseconds;
if(a11!=(void 0))
a4.setMilliseconds(a11);
if(!_isStrict(a3,a4))
{
return(void 0);
}
return a4;
}
else
{
return(void 0);
}
}
function _isStrict(
a0,
a1)
{
var a2=["FullYear","Month","Date","Hours","Minutes",
"Seconds","Milliseconds"];
for(var a3=0;a3<a2.length;a3++)
{
var a4="parsed"+a2[a3];
if(a0[a4]!=(void 0)&&
a0[a4]!=a1["get"+a2[a3]]())
{
return false;
}
}
return true;
}
function _doClumping(
a0,
a1,
a2,
a3,
a4
)
{
var a5=a0.length;
var a6=false;
var a7=0;
var a8=void 0;
var a9=0;
for(var a10=0;a10<a5;a10++)
{
var a11=a0.charAt(a10);
if(a6)
{
if(a11=="\'")
{
a6=false;
if(a7!=1)
{
a9++;
a7--;
}
if(!a2(a0,
a1,
"\'",
a9,
a7,
a3,
a4))
{
return false;
}
a7=0;
a8=void 0;
}
else
{
a7++;
}
}
else
{
if(a11!=a8)
{
if(a7!=0)
{
if(!a2(a0,
a1,
a8,
a9,
a7,
a3,
a4))
{
return false;
}
a7=0;
a8=void 0;
}
if(a11=='\'')
{
a6=true;
}
a9=a10;
a8=a11;
}
a7++;
}
}
if(a7!=0)
{
if(!a2(a0,
a1,
a8,
a9,
a7,
a3,
a4))
{
return false;
}
}
return true;
}
function _subformat(
a0,
a1,
a2,
a3,
a4,
a5,
a6
)
{
var a7=null;
var a8=false;
if((a2>='A')&&(a2<='Z')||
(a2>='a')&&(a2<='z'))
{
switch(a2)
{
case'D':
a7="(Day in Year)";
break;
case'E':
{
var a9=a5.getDay();
a7=(a4<=3)
?a1.getShortWeekdays()[a9]
:a1.getWeekdays()[a9];
}
break;
case'F':
a7="(Day of week in month)";
break;
case'G':
{
var a10=a1.getEras();
a7=(a5.getTime()<_getADEra().getTime())
?a10[0]
:a10[1];
}
break;
case'M':
{
var a11=a5.getMonth();
if(a4<=2)
{
a7=_getPaddedNumber(a11+1,a4);
}
else if(a4==3)
{
a7=a1.getShortMonths()[a11];
}
else
{
a7=a1.getMonths()[a11];
}
}
break;
case'S':
a7=_getPaddedNumber(a5.getMilliseconds(),a4);
break;
case'W':
a7="(Week in Month)";
break;
case'a':
{
var a12=a1.getAmPmStrings();
a7=(_isPM(a5.getHours()))
?a12[1]
:a12[0];
}
break;
case'd':
a7=_getPaddedNumber(a5.getDate(),a4);
break;
case'h':
hours=a5.getHours();
if(_isPM(hours))
hours-=12;
if(hours==0)
hours=12;
a7=_getPaddedNumber(hours,a4);
break;
case'K':
hours=a5.getHours();
if(_isPM(hours))
hours-=12;
a7=_getPaddedNumber(hours,a4);
break;
case'k':
hours=a5.getHours();
if(hours==0)
hours=24;
a7=_getPaddedNumber(hours,a4);
break;
case'H':
a7=_getPaddedNumber(a5.getHours(),a4);
break;
case'm':
a7=_getPaddedNumber(a5.getMinutes(),a4);
break;
case's':
a7=_getPaddedNumber(a5.getSeconds(),a4);
break;
case'w':
a7="(Week in year)";
break;
case'y':
{
var a13=a5.getFullYear();
var a14=(a4<=2)
?a4
:(void 0);
a7=_getPaddedNumber(a13,a4,a14);
}
break;
case'z':
{
var a15=-1*a5.getTimezoneOffset()/60;
a7="GMT";
if(a15>0)a7+="+";
a7+=_getPaddedNumber(a15,2);
}
break;
default:
a7="";
}
}
else
{
a7=a0.substring(a3,a3+a4);
}
a6.value+=a7;
return true;
}
function _getLocaleTimeZoneDifferenceInHours()
{
var a0=new Date();
var a1=a0.getTimezoneOffset()*-1;
var a2=0;
if(_uixLocaleTZ)
a2=(_uixLocaleTZ-a1)/60;
return a2;
}
function _subparse(
a0,
a1,
a2,
a3,
a4,
a5,
a6
)
{
var a7=a5.currIndex;
if((a2>='A')&&(a2<='Z')||
(a2>='a')&&(a2<='z'))
{
switch(a2)
{
case'D':
if(_accumulateNumber(a5,3)==(void 0))
{
return false;
}
break;
case'E':
{
var a8=_matchArray(a5,
(a4<=3)
?a1.getShortWeekdays()
:a1.getWeekdays());
if(a8==(void 0))
{
return false;
}
}
break;
case'F':
if(_accumulateNumber(a5,2)==(void 0))
{
return false;
}
break;
case'G':
{
var a9=_matchArray(a5,a1.getEras());
if(a9!=(void 0))
{
if(a9==0)
{
a5.isBC=true;
}
}
else
{
return false;
}
}
break;
case'M':
{
var a10;
var a11=0;
if(a4<=2)
{
a10=_accumulateNumber(a5,2);
a11=-1;
}
else
{
var a12=(a4==3)
?a1.getShortMonths()
:a1.getMonths();
a10=_matchArray(a5,a12);
}
if(a10!=(void 0))
{
a5.parsedMonth=(a10+a11);
}
else
{
return false;
}
}
break;
case'S':
{
var a13=_accumulateNumber(a5,3);
if(a13!=(void 0))
{
a5.parsedMilliseconds=a13;
}
else
{
return false;
}
}
break;
case'W':
if(_accumulateNumber(a5,2)==(void 0))
{
return false;
}
break;
case'a':
{
var a14=_matchArray(a5,
a1.getAmPmStrings());
if(a14==(void 0))
{
return false;
}
else
{
if(a14==1)
{
a5.isPM=true;
}
}
}
break;
case'd':
{
var a15=_accumulateNumber(a5,2);
if(a15!=(void 0))
{
a5.parsedDate=a15;
}
else
{
return false;
}
}
break;
case'h':
case'k':
case'H':
case'K':
{
var a16=_accumulateNumber(a5,2);
if(a16!=(void 0))
{
if((a2=='h')&&(a16==12))
a16=0;
if((a2=='k')&&(a16==24))
a16=0;
a5.parsedHour=a16;
}
else
{
return false;
}
}
break;
case'm':
{
var a17=_accumulateNumber(a5,2);
if(a17!=(void 0))
{
a5.parsedMinutes=a17;
}
else
{
return false;
}
}
break;
case's':
{
var a18=_accumulateNumber(a5,2);
if(a18!=(void 0))
{
a5.parsedSeconds=a18;
}
else
{
return false;
}
}
break;
case'w':
if(_accumulateNumber(a5,2)==(void 0))
{
return false;
}
break;
case'y':
{
var a19=_accumulateNumber(a5,4);
var a20=a5.currIndex-a7;
if(a19!=(void 0))
{
if((a20>2)&&
(a4<=2)&&
(a19<=999))
{
return false;
}
else if((a4<=2)&&(a19>=0)&&(a19<=100))
{
a19=_fix2DYear(a19);
}
else if(a4==4)
{
if(a20==3)
return false;
if(a20<=2)
a19=_fix2DYear(a19);
}
if(a19==0)
return false;
a5.parsedFullYear=a19;
}
else
{
return false;
}
}
break;
case'z':
{
if(!_matchText(a5,"GMT"))
{
return false;
}
if(_matchArray(a5,["-","+"])==(void 0))
{
return false;
}
if(_accumulateNumber(a5,2)==(void 0))
{
return false;
}
}
break;
default:
}
}
else
{
return _matchText(a5,
a0.substring(a3,a3+a4));
}
return true;
}
function _fix2DYear(a0)
{
var a1;
if(_df2DYS!=(void 0))
{
var a2=_df2DYS;
a1=a2-(a2%100);
a0+=a1;
if(a0<a2)
a0+=100;
}
else
{
var a3=new Date().getFullYear();
a1=a3-(a3%100)-100;
a0+=a1;
if(a0+80<a3)
{
a0+=100;
}
}
return a0;
}
function _matchArray(
a0,
a1
)
{
for(var a2=0;a2<a1.length;a2++)
{
if(_matchText(a0,a1[a2]))
{
return a2;
}
}
return(void 0);
}
function _matchText(
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
var a6=a5.toLowerCase();
var a7=a1.toLowerCase();
if(a6!=a7)
return false;
a0.currIndex+=a2;
return true;
}
function _accumulateNumber(
a0,
a1
)
{
var a2=a0.currIndex;
var a3=a2;
var a4=a0.parseString;
var a5=a4.length;
if(a5>a3+a1)
a5=a3+a1;
var a6=0;
while(a3<a5)
{
var a7=parseDigit(a4.charAt(a3));
if(!isNaN(a7))
{
a6*=10;
a6+=a7;
a3++;
}
else
{
break;
}
}
if(a2!=a3)
{
a0.currIndex=a3;
return a6;
}
else
{
return(void 0);
}
}
function _isPM(
a0
)
{
return(a0>=12);
}
function _getPaddedNumber(
a0,
a1,
a2
)
{
var a3=a0.toString();
if(a1!=(void 0))
{
var a4=a1-a3.length;
while(a4>0)
{
a3="0"+a3;
a4--;
}
}
if(a2!=(void 0))
{
var a5=a3.length-a2;
if(a5>0)
{
a3=a3.substring(a5,
a5+a2);
}
}
return a3;
}
function SimpleDateFormat(
a0,
a1
)
{
this._class="SimpleDateFormat";
this._localeSymbols=getLocaleSymbols(a1);
if(a0==(void 0))
a0=this._localeSymbols.getShortDatePatternString();
var a2=new Array();
if(a0)
a2=a2.concat(a0);
if(_dfLenient)
{
var a3=a2.length;
for(var a4=0;a4<a3;a4++)
{
if(a2[a4].indexOf('MMM')!=-1)
{
a2[a2.length]=a2[a4].replace(/MMM/g,'MM');
a2[a2.length]=a2[a4].replace(/MMM/g,'M');
}
}
var a3=a2.length;
for(var a4=0;a4<a3;a4++)
{
if(a2[a4].indexOf('/')!=-1)
{
a2[a2.length]=a2[a4].replace(/\//g,'-');
a2[a2.length]=a2[a4].replace(/\//g,'.');
}
if(a2[a4].indexOf('-')!=-1)
{
a2[a2.length]=a2[a4].replace(/-/g,'/');
a2[a2.length]=a2[a4].replace(/-/g,'.');
}
if(a2[a4].indexOf('.')!=-1)
{
a2[a2.length]=a2[a4].replace(/\./g,'-');
a2[a2.length]=a2[a4].replace(/\./g,'/');
}
}
}
this._pattern=a2;
}
SimpleDateFormat.prototype=new Format();
SimpleDateFormat.prototype.format=_simpleDateFormat;
SimpleDateFormat.prototype.parse=_simpleDateParse;
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
