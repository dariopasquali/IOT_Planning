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
	Actor <-  sendNavigationData.


updateMapAndReplan(position(ELx , ELy), position(CURx, CURy)) :-
	actorobj(Actor),
	Actor <- updateMap(ELx , ELy),	
	assert(element(ELx , ELy)),
	Actor <- searchNewBestPath(CURx, CURy),
	Actor <- sendNavigationData.

/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initNavConsole  :-  
	actorPrintln("initConsole - navigation theory loaded").
 
:- initialization(initNavConsole).