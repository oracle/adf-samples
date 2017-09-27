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
