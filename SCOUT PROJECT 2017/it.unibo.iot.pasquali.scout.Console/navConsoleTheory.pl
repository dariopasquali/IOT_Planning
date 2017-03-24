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


	
searchBestPath(position(Sx,Sy) , position(Gx,Gy)) :-
	actorPrintln(position(Sx,Sy)),
	actorPrintln(position(Gx,Gy)),	
	havemap,
	actorobj(Actor),
	Actor <- searchBestPath(Sx,Sy,Gx,Gy),
	Actor <- showPathOnGui,
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


updateMapAndReplan(position(ELx , ELy), position(CURx, CURy)) :-
	actorPrintln(object(ELx , ELy)),
	actorPrintln(current(CURx, CURy)),
	actorobj(Actor),
	Actor <- updateMap(ELx , ELy, object),
	actorPrintln(update),
	Actor <- searchNewBestPath(CURx, CURy),	
	Actor <- showPathOnGui,
	actorPrintln(search),
	Actor <- sendNavigationData.

/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initNavConsole  :-  
	actorPrintln("initConsole - navigation theory loaded").
 
:- initialization(initNavConsole).