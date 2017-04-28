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

%% INIT NAVIGATION ---------------------------------------------------

initialConfigNavRobot :-
	defaultSpeed(S),
	defaultDuration(T),
	actorobj(Actor),
	Actor <- initialConfigRobot(S,T,S,S).
	
configEngine(position(SX, SY)) :-
	actorPrintln("confingEngine!!!!"),
	actorobj(Actor),
	Actor <- configEngine(SX, SY).
	
configFileEngine(position(SX, SY), FILENAME) :-
	actorPrintln("configFileEngine!!!!"),
	actorobj(Actor),
	Actor <- configFileEngine(SX, SY, FILENAME).

%% PLAN MANAGEMENT ----------------------------------------

setNavPlan(plan(PLAN)) :-
	actorobj(Actor),
	planName(NAME),
	actorPrintln(NAME),
	actorPrintln("setNavigationPlan!!!!"),
	Actor <- setNavigationPlan(NAME, PLAN).
	
	
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

	
%% PLAN EXECUTION -----------------------------------------
	
myRunPlan(PLANNAME) :-
	runPlan(PLANNAME).


myExecPlan(CURPLAN,Actor,P,PC) :-
	plan(PC, P, S),
	actorPrintln(S),
	myRunTheSentence(CURPLAN,Actor,S),
	PC1 is PC + 1,
	myExecPlan(CURPLAN,Actor,P,PC1).


executeCmd(Actor,  move(switchplan,PNAME), Events, Plans, done(switchplan) ):-
	actorPrintln(  PNAME ),
	Actor <- switchPlan(PNAME).
	
	
executeCmd(Actor, move(senseEvent,TIMEOUT,EVENTSLIST, PLANSLIST), Events, Plans, RES ):-
	actorPrintln(sense(EVENTLIST)),
	Actor <- senseEvent(TIMEOUT, EVENTSLIST, PLANSLIST).

%% WORLD INTERACTION ------------------------------------------------------

notifyUnexpectedObstacle :-
	actorobj(Actor),
	Actor <- notifyObstacle.

updateSimulationWorld :-
	actorobj(Actor),
	Actor <- updateSimulationWorld.

%% OLD ---------------------------------------------------

/*
runResumablePlan(PLAN) :-
	actorobj(Actor),
	myExecPlan(PLAN,Actor,PLAN,1).
*/

/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initNavRobot  :-  
	actorPrintln("initRobot - Navigatin Theory loaded").
 
:- initialization(initNavRobot).