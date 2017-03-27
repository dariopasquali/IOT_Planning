/*
===============================================================
robotTheory.pl
===============================================================
*/

%% SENTENCES EXECUTION ------------------------------------------------------

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

%% DEBUG CONTROL -----------------------------------------------

enableDebugSensing :-
	actorobj(Actor),
	actorPrintln("enable debug sensing"),
	Actor <- enableDebugSensing.
	
disableDebugSensing :-
	actorobj(Actor),
	actorPrintln("disable debug sensing"),
	Actor <- disableDebugSensing.	


/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initRobot :-  
	actorPrintln("-----------------------------------------------initRobot").
 
:- initialization(initRobot).