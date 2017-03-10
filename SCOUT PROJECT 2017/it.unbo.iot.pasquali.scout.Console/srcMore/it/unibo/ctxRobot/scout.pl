%====================================================================================
% Context ctxRobot standalone= SYSTEM-configuration: file it.unibo.ctxRobot.scout.pl 
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8020" ).  		 
%%% -------------------------------------------
qactor( robot , ctxrobot, "it.unibo.robot.MsgHandle_Robot"   ). %%store msgs 
qactor( robot_ctrl , ctxrobot, "it.unibo.robot.Robot"   ). %%control-driven 
