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
