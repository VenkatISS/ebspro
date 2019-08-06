var refreshAndbackUrl = 'https://www.mylpgbooks.com/ebs/AppControlServlet';
//var refreshAndbackUrl = 'http://localhost:8080/ebs/AppControlServlet';
//var refreshAndbackUrl = 'http://192.168.100.101:8080/ebs/AppControlServlet';
function winClose() {
	if (window.history && window.history.pushState) {
		var hash = window.location.hash;
		var count = 0;
		window.addEventListener("popstate", function() {
			var hashLocation = location.hash;
			var hashSplit = hashLocation.split("#!/history");
			var hashName = hashSplit[0];
			var hash2 = window.location.hash;
			if (hashName === '') {
				backbutton(count,hash2);
			}
		});
		if(hash.localeCompare( "" ) == 0){
			window.history.pushState('forward', null, refreshAndbackUrl);
		}
	}
}
function backbutton(count,hash2) {
	if (hash2 === '') {
		if(confirm("You will be logged out of this application. Do you want to leave?")) {
			window.open(refreshAndbackUrl, '_self');
		}else {
			if(document.referrer != refreshAndbackUrl)
				history.forward();
			else 
				window.history.pushState('forward', null, refreshAndbackUrl);
		}
	}
}

/*//var refreshAndbackUrl = 'https://www.mylpgbooks.com/ebs/AppControlServlet';
var refreshAndbackUrl = 'http://localhost:8080/ebs/AppControlServlet';
function winClose() {
	if (window.history && window.history.pushState) {
		var hash = window.location.hash;
		var count = 0;
		window.addEventListener("popstate", function() {
			var hashLocation = location.hash;
			var hashSplit = hashLocation.split("#!/history");
			var hashName = hashSplit[1];
			var hash2 = window.location.hash;
			if (hashName !== '') {
				backbutton(count,hash2);
			}
		});
		if(hash.localeCompare( "" ) == 0)
			window.history.pushState('forward', null, refreshAndbackUrl);
	}
}

function backbutton(count,hash2) {
	if((hash2.localeCompare( "#hm" ) == 0)) {
		window.history.pushState('forward', null, refreshAndbackUrl);
		window.open(refreshAndbackUrl, '_self');
	}
	else if (hash2 === '') {
		if(count == 0) {
			window.open(refreshAndbackUrl, '_self');
		}
		if(count > 0){
			window.open(refreshAndbackUrl, '_self');
		}
	}else {
		if((hash2.localeCompare( "#logt" ) == 0)) {
			window.history.pushState('forward', null,refreshAndbackUrl);
			window.open(refreshAndbackUrl, '_self');
		}
		count = count+1;
	}
}*/

/*
//var refreshAndbackUrl = 'https://www.mylpgbooks.com/ebs/AppControlServlet';
var refreshAndbackUrl = 'http://localhost:8080/ebs/AppControlServlet';
function winClose() {
	if (window.history && window.history.pushState) {
		var hash = window.location.hash;
		var count = 0;
		window.addEventListener("popstate", function() {
			var hashLocation = location.hash;
			var hashSplit = hashLocation.split("#!/history");
			var hashName = hashSplit[1];
			var hash2 = window.location.hash;
			if (hashName !== '') {
				backbutton(count,hash2);
			}
		});
		if(hash.localeCompare( "" ) == 0){
			window.history.pushState('forward', null, refreshAndbackUrl);
		}
	}
}
function backbutton(count,hash2) {
	if (hash2 === '') {
		if(count == 0) {
			if(confirm("Do you want to leave this page?")) {
				window.open(refreshAndbackUrl, '_self');
			}else {
				if(document.referrer != refreshAndbackUrl)
					history.forward();
				else 
					window.history.pushState('forward', null, refreshAndbackUrl);
			}
		}
		if(count > 0){
			if(confirm("Do you want to leave this page?")) {
				window.open(refreshAndbackUrl, '_self');
			}else {
				if(document.referrer != refreshAndbackUrl)
					history.forward();
				else 
					window.history.pushState('forward', null, refreshAndbackUrl);
			}
		}
	}else {
		if((hash2.localeCompare( "#logt" ) == 0)) {
			window.history.pushState('forward', null,refreshAndbackUrl);
			window.open(refreshAndbackUrl, '_self');
		}
		count = count+1;
	}
}*/



/*
//var refreshAndbackUrl = 'https://www.mylpgbooks.com/ebs/AppControlServlet';
var refreshAndbackUrl = 'http://localhost:8080/ebs/AppControlServlet';
function winClose() {
	if (window.history && history.pushState) {
	    addEventListener('load', function() {
	        history.pushState(null, null, null); // creates new history entry with same URL
	        addEventListener('popstate', function() {
	            var stayOnPage = confirm("Do you want to leave this page?");
	            if (!stayOnPage) { // no
	                history.back(); 
	            } else { // yes
	                history.pushState(null, null, null);
	            }
	        });    
	    });
	}
}
*/


/*
var refreshAndbackUrl = 'https://www.mylpgbooks.com/ebs/AppControlServlet';
function winClose() {
	if (window.history && window.history.pushState) {
		var hash = window.location.hash;
		var flag = "NO";
		window.addEventListener("popstate", function() {
			var loc = window.location.href;
			var locSplit = loc.split("/");
			flag = "YES";
			if((flag.localeCompare( "YES" ) == 0) && ((locSplit[4]).localeCompare( "login" ) == 0) ) {
				window.open(refreshAndbackUrl, '_self');
			}
		});
	}
}*/



/*//var refreshAndbackUrl = 'https://www.mylpgbooks.com/ebs/AppControlServlet';
var refreshAndbackUrl = 'http://localhost:8080/ebs/AppControlServlet';
function winClose() {
	if (window.history && window.history.pushState) {
		var hash = window.location.hash;
		var count = 0;
		window.addEventListener("popstate", function() {
			var hashLocation = location.hash;
			var hashSplit = hashLocation.split("#!/history");
			var hashName = hashSplit[0];
			var hash2 = window.location.hash;
			if (hashName !== '') {
				backbutton(count,hash2);
			}
		});
		if(hash.localeCompare( "" ) == 0)
			window.history.pushState('forward', null, refreshAndbackUrl);
	}
}

function backbutton(count,hash2) {
	if((hash2.localeCompare( "#hm" ) == 0)) {
		window.history.pushState('forward', null, refreshAndbackUrl);
		window.open(refreshAndbackUrl, '_self');
	}
	else if (hash2 === '') {
		if(count == 0) {
			window.open(refreshAndbackUrl, '_self');
		}
		if(count > 0){
			window.open(refreshAndbackUrl, '_self');
		}
	}else {
		if((hash2.localeCompare( "#logt" ) == 0)) {
			window.history.pushState('forward', null,refreshAndbackUrl);
			window.open(refreshAndbackUrl, '_self');
		}
		count = count+1;
	}
}*/