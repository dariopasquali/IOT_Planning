%====================================================================================
% Context ctxRobot  SYSTEM-configuration: file it.unibo.ctxRobot.scout.pl 
%====================================================================================
context(ctxrobot, "192.168.43.61",  "TCP", "8020" ).  		 
context(ctxconsole, "192.168.43.17",  "TCP", "8010" ).  		 
%%% -------------------------------------------
qactor( console , ctxconsole, "it.unibo.console.MsgHandle_Console"   ). %%store msgs 
qactor( console_ctrl , ctxconsole, "it.unibo.console.Console"   ). %%control-driven 
qactor( robot , ctxrobot, "it.unibo.robot.MsgHandle_Robot"   ). %%store msgs 
qactor( robot_ctrl , ctxrobot, "it.unibo.robot.Robot"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

