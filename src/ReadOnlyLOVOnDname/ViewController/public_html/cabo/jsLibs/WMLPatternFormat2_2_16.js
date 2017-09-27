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
