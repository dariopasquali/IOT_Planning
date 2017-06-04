%====================================================================================
% Context ctxRobot  SYSTEM-configuration: file it.unibo.ctxRobot.simulation.pl 
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "4000" ).  		 
%%% -------------------------------------------
qactor( controller , ctxrobot, "it.unibo.controller.MsgHandle_Controller"   ). %%store msgs 
qactor( controller_ctrl , ctxrobot, "it.unibo.controller.Controller"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------
qactor( robot , ctxrobot, "it.unibo.robot.MsgHandle_Robot" ). 
qactor( robot_ctrl , ctxrobot, "it.unibo.robot.Robot" ). 

