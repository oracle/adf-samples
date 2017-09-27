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
