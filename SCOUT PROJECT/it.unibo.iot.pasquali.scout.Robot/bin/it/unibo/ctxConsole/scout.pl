%====================================================================================
% Context ctxConsole  SYSTEM-configuration: file it.unibo.ctxConsole.scout.pl 
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8020" ).  		 
context(ctxconsole, "localhost",  "TCP", "8010" ).  		 
%%% -------------------------------------------
qactor( console , ctxconsole  ).
%%% -------------------------------------------
qactor( robot , ctxrobot  ).

