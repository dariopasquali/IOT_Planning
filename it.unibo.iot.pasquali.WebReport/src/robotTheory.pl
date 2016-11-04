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
planName(scout).

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
	

setNavPlan(plan(ALGO,PLAN)) :-
	actorobj(Actor),
	defaultSpeed(SPEED),
	defaultDuration(DUR),
	planName(NAME),
	actorPrintln(NAME),
	actorPrintln("setNavigationPlan!!!!"),
	Actor <- setNavigationPlan(NAME, ALGO, PLAN, SPEED, DUR).
	
setPositions(positions(SX, SY, GX, GY)) :-
	actorobj(Actor),
	Actor <- setPositions(SX, SY, GX, GY).
	
setPosition(position(SX, SY)) :-
	actorobj(Actor),
	Actor <- setPosition(SX, SY).

loadNavigationData(MAP, PLAN, POS) :-
	actorPrintln(MAP),
	actorPrintln(PLAN),
	actorPrintln(POS),
	actorPrintln("caricamento dati in corso...."),	
	loadMapFromData(MAP),
	setNavPlan(PLAN),
	setPositions(POS).
	
loadNavigationData(PLAN, POS) :-
	actorPrintln(PLAN),
	actorPrintln(POS),
	actorPrintln("caricamento dati in corso...."),
	setNavPlan(PLAN),
	setPosition(POS).

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
  
myExecuteCmd(CURPLAN, Actor, move(receiveAndSolve,RECEIVETIME, MSGLIST, PAYLOADLIST, SOLVELIST, SOLVETIME), Events, Plans, RES ):-
	Actor <- receiveMessageAndSolve( RECEIVETIME, MSGLIST, PAYLOADLIST, SOLVELIST, SOLVETIME).
  	
myExecuteCmd(CURPLAN, Actor, move(senseEvent,TIMEOUT,EVENTSLIST, PLANSLIST), Events, Plans, RES ):-
	Actor <- mySenseEvent(TIMEOUT, EVENTSLIST, PLANSLIST).
  	
myExecuteCmd(CURPLAN, Actor, move(robotmove,CMD,SPEED,DURATION,ANGLE), Events, Plans, RES ):-
 	!,
 	mapCmdToMove(CMD,MOVE),
	Actor <- execRobotMove(CURPLAN, MOVE, SPEED, ANGLE, DURATION, Events, Plans),
	Actor <- updateMyPosition(MOVE).

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