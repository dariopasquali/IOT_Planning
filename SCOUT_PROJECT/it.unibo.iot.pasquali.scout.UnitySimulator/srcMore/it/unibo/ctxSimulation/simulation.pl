%====================================================================================
% Context ctxSimulation  SYSTEM-configuration: file it.unibo.ctxSimulation.simulation.pl 
%====================================================================================
context(ctxsimulation, "localhost",  "TCP", "5000" ).  		 
%%% -------------------------------------------
qactor( simulator , ctxsimulation, "it.unibo.simulator.MsgHandle_Simulator"   ). %%store msgs 
qactor( simulator_ctrl , ctxsimulation, "it.unibo.simulator.Simulator"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

