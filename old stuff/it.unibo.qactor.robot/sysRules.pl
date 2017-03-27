%==============================================
% DEFINED BY THE SYSTEM DESIGNER
%==============================================
	
%==============================================
% CONTEXT HANDLING UTILTY RULES
%==============================================
getCtxNames(CTXNAMES) :-
	findall( NAME, context( NAME, _, _, _ ), CTXNAMES).
getCtxPortNames(PORTNAMES) :-
	findall( PORT, context( _, _, _, PORT ), PORTNAMES).
getTheContexts(CTXS) :-
	findall( context( CTX, HOST, PROTOCOL, PORT ), context( CTX, HOST, PROTOCOL, PORT ), CTXS).
getTheActors(ACTORS) :-
	findall( qactor( A, CTX ), qactor( A, CTX ), ACTORS).

insertActorOnce( NAME, CTX ) :-
	qactor( NAME , CTX ), !.
insertActorOnce( NAME, CTX ) :-	
 	assert( qactor( NAME , CTX ) ).
 	
getCtxHost( NAME, HOST )  :- context( NAME, HOST, PROTOCOL, PORT ).
getCtxPort( NAME,  PORT ) :- context( NAME, HOST, PROTOCOL, PORT ).
getCtxProtocol( NAME,  PROTOCOL ) :- context( NAME, HOST, PROTOCOL, PORT ).

getMsgId( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , MSGID  ).
getMsgType( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , MSGTYPE ).
getMsgSenderId( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , SENDER ).
getMsgReceiverId( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , RECEIVER ).
getMsgContent( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , CONTENT ).
getMsgNum( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , SEQNUM ).

 	 
%==============================================
% MEMENTO
%==============================================
%%% --------------------------------------------------
% context( NAME, HOST, PROTOCOL, PORT )
% PROTOCOL : "TCP" | "UDP" | "SERIAL" | "PROCESS" | ...
%%% --------------------------------------------------

%%% --------------------------------------------------
% msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
% MSGTYPE : dispatch request answer
%%% --------------------------------------------------


