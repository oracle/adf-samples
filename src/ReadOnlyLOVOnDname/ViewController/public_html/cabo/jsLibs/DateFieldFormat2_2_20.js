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
