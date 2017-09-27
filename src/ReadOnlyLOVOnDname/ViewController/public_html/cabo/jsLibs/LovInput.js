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
