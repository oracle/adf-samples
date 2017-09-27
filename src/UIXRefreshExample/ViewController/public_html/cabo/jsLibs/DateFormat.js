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
