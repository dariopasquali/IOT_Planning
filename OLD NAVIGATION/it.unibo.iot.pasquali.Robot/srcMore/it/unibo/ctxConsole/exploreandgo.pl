%====================================================================================
% Context ctxConsole  SYSTEM-configuration: file it.unibo.ctxConsole.exploreAndGo.pl 
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8020" ).  		 
context(ctxconsole, "localhost",  "TCP", "8010" ).  		 
%%% -------------------------------------------
%%% -------------------------------------------
qactor( robot , ctxrobot  ).

