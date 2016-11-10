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

loadMapFromFileProlog(PATH) :-
	havemap, !,
	retractall(mapdata(_,element(_,_))),
	actorPrintln("retract map elements"),
	retract(map(_,_)),
	actorPrintln("retract map size"),
	retract(havemap),
	actorPrintln("retract map flag"),
	actorobj(Actor),
	Actor <- consultFromFile(PATH),
	actorPrintln("consulted map data"),
	map(Xmax, Ymax),
	actorPrintln(Xmax),
	actorPrintln(Ymax),
	Actor <- createMap(Xmax, Ymax),
	getElements(List),
	actorPrintln(List),
	Actor <- setMapElements(List),
	assert(havemap).
	
loadMapFromFileProlog(PATH) :-
	actorobj(Actor),
	Actor <- consultFromFile(PATH),
	actorPrintln("consulted map data"),
	map(Xmax, Ymax),
	actorPrintln(Xmax),
	actorPrintln(Ymax),
	Actor <- createMap(Xmax, Ymax),
	mapdata(ID,element(X,Y)),
	getElements(List),
	actorPrintln(List),
	Actor <- setMapElements(List),
	assert(havemap).

loadMapImage(PATH) :-
	actorobj(Actor),
	actorPrintln("consulted map data"),
	Actor <- loadMapImage(PATH),
	assert(havemap).
	
loadMapButton(PATH) :-
	actorobj(Actor),
	actorPrintln("consulted map data"),
	Actor <- loadMapButton(PATH),
	assert(havemap).
	
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

updateMapAndReplan(position(EL_X , EL_Y), position(CUR_X, CUR_Y) :-
	actorobj(Actor),
	Actor <- updateMap(EL_X , EL_Y),	
	assert(element(EL_X , EL_Y)),
	Actor <- searchNewBestPath(CUR_X, CUR_Y),
	sendNavigationData.
/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initNavConsole  :-  
	actorPrintln("initNavConsole").
 
:- initialization(initNavConsole).