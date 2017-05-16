%====================================================================================
% Context ctxSimulation  SYSTEM-configuration: file it.unibo.ctxSimulation.scout.pl 
%====================================================================================
context(ctxsimulation, "localhost",  "TCP", "8021" ).  		 
context(ctxrobot, "localhost",  "TCP", "8020" ).  		 
context(ctxconsole, "localhost",  "TCP", "8010" ).  		 
%%% -------------------------------------------
qactor( console , ctxconsole, "it.unibo.console.MsgHandle_Console"   ). %%store msgs 
qactor( console_ctrl , ctxconsole, "it.unibo.console.Console"   ). %%control-driven 
qactor( guimanager , ctxsimulation, "it.unibo.guimanager.MsgHandle_Guimanager"   ). %%store msgs 
qactor( guimanager_ctrl , ctxsimulation, "it.unibo.guimanager.Guimanager"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------
qactor( robot , ctxrobot, "it.unibo.robot.MsgHandle_Robot" ). 
qactor( robot_ctrl , ctxrobot, "it.unibo.robot.Robot" ). 

