%==============================================
% USER DEFINED
% system configuration: robotNaiveKb.pl
%==============================================
context( ctxrobot, "localhost",  "TCP", "8010" ).

qactor("mock", ctxrobot).
qactor("alarmSource", ctxrobot).
