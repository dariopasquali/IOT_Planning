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
defaultDuration(1000).

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
	
configEngine(position(SX, SY)) :-
	actorPrintln("confingEngine!!!!"),
	actorobj(Actor),
	Actor <- configEngine(SX, SY).
	
configFileEngine(position(SX, SY), FILENAME) :-
	actorPrintln("configFileEngine!!!!"),
	actorobj(Actor),
	Actor <- configFileEngine(SX, SY, FILENAME).
	
loadNavigationData(PLAN, POS) :-
	actorPrintln(PLAN),
	actorPrintln(POS),
	actorPrintln("caricamento dati in corso...."),
	configEngine(POS),
	setNavPlan(PLAN).
	
	
loadNavigationData(PLAN, POS, FILENAME) :-
	actorPrintln(PLAN),
	actorPrintln(POS),
	actorPrintln(FILENAME),
	actorPrintln("caricamento dati in corso...."),
	configFileEngine(POS, FILENAME),
	setNavPlan(PLAN).
	

loadThePlan( FName ):-
	actorobj(Actor),
	actorPrintln(FName),
	Actor <- consultFromFile(  FName  ).

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

executeCmd( Actor,  move(switchplan,PNAME), Events, Plans, done(switchplan) ):-
	actorPrintln(  PNAME ),
	Actor <- switchPlan(PNAME).
	
executeCmd(Actor, move(senseEvent,TIMEOUT,EVENTSLIST, PLANSLIST), Events, Plans, RES ):-
	actorPrintln(sense(EVENTLIST)),
	Actor <- senseEvent(TIMEOUT, EVENTSLIST, PLANSLIST).

notifyUnexpectedObstacle :-
	actorobj(Actor),
	Actor <- notifyObstacle.

myClearPlan(P) :-
	actorPrintln(clear),
	%%retractall( plan(_, P, _) ),
	myClearPlan(1,P),
	actorPrintln( clearPlan( P,done ) ).

myClearPlan(PC,P):-
	plan(PC,P,S),
	actorPrintln(PC),
	retract(plan(PC,P,S)),
	PC1 is PC + 1,
	myClearPlan(PC1,P).

myClearPlan(_,_).

updateSimulationWorld :-
	actorobj(Actor),
	Actor <- updateSimulationWorld.
	


/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initNavRobot  :-  
	actorPrintln("------------------------------------------------------------initNavRobot").
 
:- initialization(initNavRobot).