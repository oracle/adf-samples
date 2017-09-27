function GraphView(a0,a1,a2,
a3,
a4,a5,
a6)
{
this.source=a0;
this.imageID=a1;
this.graphBounds=a3;
this.viewClip=a6;
this.formName=a2;
this.thumbWidth=a4;
this.thumbHeight=a5;
}
GraphView.prototype.submit=_submit;
GraphView.prototype.move=_move;
GraphView.prototype.moveRel=_moveRel;
GraphView.prototype.thumbClick=_thumbClick;
GraphView.prototype.nodeClick=_nodeClick;
GraphView.prototype.canvasClick=_canvasClick;
function _canvasClick(a0)
{
var a1=document.getElementById(this.imageID);
var a2=_getClickXY(a1,a0);
var a3=this.viewClip;
var a4=new Object();
a4.event="canvas";
a4.x=a2.x+a3.x;
a4.y=a2.y+a3.y;
this.submit(a4);
}
function _nodeClick(a0,a1)
{
var a2=new Object();
a2.event="click";
a2.part=a1;
a2.partID=a0;
this.submit(a2);
}
function _moveRel(a0,a1)
{
var a2=this.viewClip;
var a3=(a2.x+a2.width/2)+(a2.width*a0/2);
var a4=(a2.y+a2.height/2)+(a2.height*a1/2);
this.move(a3,a4);
}
function _move(a0,a1)
{
var a2=new Object();
a2.event="pan";
a2.x=a0;
a2.y=a1;
this.submit(a2);
}
function _submit(a0)
{
a0.source=this.source;
submitForm(this.formName,0,a0);
}
function _thumbClick(a0,a1)
{
var a2=_getClickXY(a0,a1);
var a3=a2.x;
var a4=a2.y;
var a5=this.graphBounds;
var a6=(a3*a5.width/this.thumbWidth)+a5.x;
var a7=(a4*a5.height/this.thumbHeight)+a5.y;
this.move(Math.round(a6),Math.round(a7));
}
function _getClickXY(a0,a1)
{
var a2=_getLocation(a0);
a2.x=a1.clientX-a2.x;
a2.y=a1.clientY-a2.y;
return a2;
}
function _getLocation(a0)
{
var a1=a0;
var a2=0;
var a3=0;
while(a1.tagName!="BODY"){
a2+=a1.offsetLeft;
a3+=a1.offsetTop;
a1=a1.offsetParent;
}
return{x:a2,y:a3};
}
