/*
===============================================================
robotTheory.pl
===============================================================
*/


%% Mappa nel formato
%% map(Xmax, Ymax). 
%% mapdata(ID, element(X,Y)).
%% ...
%% ...
%% ... 
%% bordi e ostacoli sono equivalenti e trattati come element

defaultSpeed(60).
defaultDuration(500).


add_tail([],X,[X]).
add_tail([H|T],X,[H|L]) :-
	add_tail(T,X,L).

getElements(List) :-
	getElements([], 1, List).
	
getElements(List, ID, ToList) :-
	mapdata(ID, E), !,
	add_tail(List, E, List1),
	ID1 is ID + 1,
	getElements(List1, ID1, ToList).
	
getElements(List, _, List).

notContainsElement(E, []).
notContainsElement(E, [E|_]) :- !, fail.
notContainsElement(E, [H|T]) :-
	containsElement(E,T).
	

loadMapFromFile(PATH) :-
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
	
loadMapFromFile(PATH) :-
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

loadMapFromData(mapdata(Map , E)) :-
	actorobj(Actor),
	assert(Map),
	map(Xmax,Ymax),
	actorPrintln(Xmax),
	actorPrintln(Ymax),
	Actor <- createMap(Xmax, Ymax),
	loadMapFromData(E).
	
loadMapFromData([]) :-
	actorobj(Actor),
	getElements(List),
	actorPrintln(List),
	Actor <- setMapElements(List),
	actorPrintln("Map Data loaded"),
	assert(havemap).
	
loadMapFromData([E | T]) :-
	assert(E),	
	loadMapFromData(T).
	
setNavPlan(plan(ALGO,PLAN)) :-
	actorobj(Actor),
	defaultSpeed(SPEED),
	defaultDuration(DUR),
	actorPrintln("setNavigationPlan!!!!"),
	Actor <- setNavigationPlan(ALGO, PLAN, SPEED, DUR).
	
setPositions(positions(SX, SY, GX, GY)) :-
	actorobj(Actor),
	Actor <- setPositions(SX, SY, GX, GY).

loadNavigationData(MAP, PLAN, POS) :-
	actorPrintln(MAP),
	actorPrintln(PLAN),
	actorPrintln(POS),
	actorPrintln("caricamento dati in corso...."),	
	loadMapFromData(MAP),
	setNavPlan(PLAN),
	setPositions(POS).

checkValidState( X , Y) :-
	X >= 0,
	Y >= 0,
	map(Xmax, Ymax),
	X =< Xmax,
	Y =< Ymax,
	getElements(List),
	notContainsElement(element(X,Y), List).

%% START -> position(X,Y)

searchBestPath(position(Sx,Sy) , position(Gx,Gy), ALGO) :-
	actorPrintln(position(Sx,Sy)),
	actorPrintln(position(Gx,Gy)),	
	havemap,
	actorobj(Actor),
	Actor <- searchBestPath(Sx,Sy,Gx,Gy,ALGO),
	Actor <- showPathOnGui,
	assert(haveplan).

loadThePlan( FName ):-
	actorobj(Actor),
	Actor <-  consultFromFile(  FName  ).

runResumablePlan(PLAN) :-
	actorobj(Actor),
	myExecPlan(PLAN,Actor,PLAN,1).

myExecPlan(CURPLAN,Actor,P,PC) :-
	plan(PC, P, S),
	actorPrintln(S),
	myRunTheSentence(CURPLAN,Actor,S),
	PC1 is PC + 1,
	myExecPlan(CURPLAN,Actor,P,PC1).


myRunTheSentence(CURPLAN, Actor, sentence( GUARD, MOVE, EVENTS, PLANS ) ):-
  	( GUARD = - G , !, retract(G),  ! ; GUARD, ! ),
  	 myExecuteCmd(CURPLAN, Actor, MOVE, EVENTS, PLANS, RESULT).
  	
  	
myExecuteCmd(CURPLAN, Actor, move(robotmove,CMD,SPEED,DURATION,ANGLE), Events, Plans, RES ):-
 	!,
 	mapCmdToMove(CMD,MOVE),
	Actor <- execRobotMove(CURPLAN, MOVE, SPEED, ANGLE, DURATION, Events, Plans).

myExecuteCmd(CURPLAN, Actor,  move(print,ARG), Events, Plans, done(print) ):-
	text_term(ARGS,ARG),
	actorPrintln(  ARGS ).
	
	
myExecuteCmd(CURPLAN, Actor, move(solve,GOAL,DURATION), Events, Plans, RES ):-
	Actor <- solveSentence(sentence(true, GOAL, 0, '', '', '')) returns AAR,
	AAR <- getResult returns RES.
	

continueProgram :-
	actorobj(Actor),
	Actor <- switchToPlan('notifyEndOfNavigation').
	
notifyEnd :-
	actorPrintln("CRISTO DIO").

/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initRobot :-  
	actorPrintln("initRobot").
 
:- initialization(initRobot).