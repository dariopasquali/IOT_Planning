/*
===============================================================
navRobotTheory.pl
===============================================================
*/

%% Mappa nel formato
%% map(Xmax, Ymax). 
%% mapdata(ID, element(X,Y)).
%% ...
%% ...
%% ... 
%% bordi e ostacoli sono equivalenti e trattati come element

%% ROBOT CONFIG -------------------------------

planFilename("scout.txt").
planName(scout).

defaultSpeed(60).
defaultDuration(500).

initialConfigNavRobot :-
	defaultSpeed(S),
	defaultDuration(T),
	actorobj(Actor),
	Actor <- initialConfigRobot(S,T,S,S).

%% LIST OF ELEMENT MANAGEMENT -----------------------------------------------

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


%% NAVIGATION PLAN MANAGEMENT ----------------------------------------

setNavPlan(plan(PLAN)) :-
	actorobj(Actor),
	planName(NAME),
	actorPrintln(NAME),
	actorPrintln("setNavigationPlan!!!!"),
	Actor <- setNavigationPlan(NAME, PLAN).
	
setStateManager(position(SX, SY), MODE) :-
	actorobj(Actor),
	Actor <- configNavigationEngine(SX, SY, MODE).
/*
loadNavigationData(MAP, PLAN, POS) :-
	actorPrintln(MAP),
	actorPrintln(PLAN),
	actorPrintln(POS),
	actorPrintln("caricamento dati in corso...."),	
	loadMapFromData(MAP),
	setNavPlan(PLAN),
	setPosition(POS).
*/
	
loadNavigationData(PLAN, POS, MODE) :-
	actorPrintln(PLAN),
	actorPrintln(POS),
	actorPrintln("caricamento dati in corso...."),
	setNavPlan(PLAN),
	setStateManager(POS, MODE).

loadThePlan( FName ):-
	actorobj(Actor),
	Actor <-  consultFromFile(  FName  ).

myRunPlan(PLANNAME) :-
	runPlan(PLANNAME).

runResumablePlan(PLAN) :-
	actorobj(Actor),
	myExecPlan(PLAN,Actor,PLAN,1).

myExecPlan(CURPLAN,Actor,P,PC) :-
	plan(PC, P, S),
	actorPrintln(S),
	myRunTheSentence(CURPLAN,Actor,S),
	PC1 is PC + 1,
	myExecPlan(CURPLAN,Actor,P,PC1).

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
initNavRobot  :-  
	actorPrintln("------------------------------------------------------------initNavRobot").
 
:- initialization(initNavRobot).