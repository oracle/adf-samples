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
