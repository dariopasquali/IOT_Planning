%====================================================================================
% Context ctxRobot  SYSTEM-configuration: file it.unibo.ctxRobot.scout.pl 
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8020" ).  		 
context(ctxconsole, "localhost",  "TCP", "8010" ).  		 
%%% -------------------------------------------
qactor( console , ctxconsole, "it.unibo.console.MsgHandle_Console"   ). %%store msgs 
qactor( console_ctrl , ctxconsole, "it.unibo.console.Console"   ). %%control-driven 
qactor( guimanager , ctxrobot, "it.unibo.guimanager.MsgHandle_Guimanager"   ). %%store msgs 
qactor( guimanager_ctrl , ctxrobot, "it.unibo.guimanager.Guimanager"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxrobot,"it.unibo.ctxRobot.Evh","updateSimulation").  
%%% -------------------------------------------
qactor( robot , ctxrobot, "it.unibo.robot.MsgHandle_Robot" ). 
qactor( robot_ctrl , ctxrobot, "it.unibo.robot.Robot" ). 

