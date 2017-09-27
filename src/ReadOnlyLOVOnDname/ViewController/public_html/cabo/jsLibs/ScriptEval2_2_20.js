if(!_pprBackRestoreInlineScripts)
{
var iFrameElement=document.getElementById("_pprIFrame");
if(iFrameElement!=null)
{
var code=
_getCommentedScript(iFrameElement.contentWindow.document,"_pprScripts");
if(code==null)
{
code=_getCommentedScript(document,"_pprScripts");
}
if(code!=null)
{
_eval(window,code);
}
}
}
else
{
var saveScriptElement=_getElementById(document,"_pprSaveScript");
if(saveScriptElement!=null&&saveScriptElement.value!="")
{
_eval(window,saveScriptElement.value);
}
_pprBackRestoreInlineScripts=false;
}
