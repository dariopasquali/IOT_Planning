%========================================================
% USER DEFINED system configuration: robotavatar.pl
%========================================================
%%% context(  mind,  "localhost",  "TCP",  "8050" ).  		% 192.168.43.229 is PC
context(  ctxrobotavatar, "localhost",  "TCP", "8070" ).  		 

 
%%% qactor( robterminal, mind ).	
%%% qactor( mind,   mind ).
qactor( robotavatar ,   ctxrobotavatar ).
