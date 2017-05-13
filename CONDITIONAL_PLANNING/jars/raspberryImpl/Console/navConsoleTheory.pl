/*
===============================================================
navConsoleTheory.pl
===============================================================
*/

%% Mappa nel formato
%% map(Xmax, Ymax). 
%% mapdata(ID, element(X,Y)).
%% ...
%% ...
%% ... 
%% bordi e ostacoli sono equivalenti e trattati come element


	
createPlan(position(Sx,Sy) , position(Gx,Gy), MODE) :-
	actorPrintln(position(Sx,Sy)),
	actorPrintln(position(Gx,Gy)),	
	havemap,
	actorobj(Actor),
	Actor <- createPlan(Sx,Sy,Gx,Gy, MODE),
	Actor <- showPlan,
	assert(haveplan).

startNavigation :-
	havemap,
	actorPrintln(havemap),
	haveplan,
	actorPrintln(haveplan),
	actorobj(Actor),
	Actor <-  sendNavigationData(robot).

startNavigationFile :-
	havemap,
	actorPrintln(havemap),
	haveplan,
	actorPrintln(haveplan),
	actorobj(Actor),
	Actor <-  sendNavigationData(simulated).


/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initNavConsole  :-  
	actorPrintln("initConsole - navigation theory loaded").
 
:- initialization(initNavConsole).