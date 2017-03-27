//robotWebUI.js
var curSpeed = "low";
	
    console.log("robotWebUi.js : server IP= "+document.location.host);
 /*
 * WEBSOCKET
 */
    var sock = new WebSocket("ws://"+document.location.host, "protocolOne");
    sock.onopen = function (event) {
         //console.log("robotWebUi.js : I am connected to server.....");
         document.getElementById("connection").value = 'CONNECTED';
    };
    sock.onmessage = function (event) {
        //console.log("robotWebUi.js : "+event.data);
        //alert( "onmessage " + event);
        document.getElementById("state").value = ""+event.data;
    }
    sock.onerror = function (error) {
        //console.log('WebSocket Error %0',  error);
        document.getElementById("state").value = ""+error;
    };
    
	function setSpeed(val){
		curSpeed = val;
		document.getElementById("speed").value = curSpeed;
	}
	function send(message) {
		document.getElementById("sending").value = ""+message;
		sock.send(message);
	};

/*	
	document.onkeydown = function(event) {
 	alert("event="+event);
		    event = event || window.event;
		    switch (event.keyCode || event.which) {
		        case 65:
		            send('a(high)');
		            break;
		        case 68:
		            send('d(high)');
		            break;
		        case 87:
		            send('w(high)');
		            break;
		        case 83:
		            send('s(high)');
		            break;
		        default:
		            //send('h(high)');
		    }
	};
 */
 	//alert("loaded");
