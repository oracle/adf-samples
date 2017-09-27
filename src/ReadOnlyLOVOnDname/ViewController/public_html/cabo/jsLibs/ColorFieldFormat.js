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
