function splitterClick(evt) 
{ 
  AdfCustomEvent.queue(evt.getSource(), "handleSplitterClick",{}, true);         
  evt.cancel();
}
