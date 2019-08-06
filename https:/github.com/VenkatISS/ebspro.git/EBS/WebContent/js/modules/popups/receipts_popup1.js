document.getElementById("invid").innerHTML = page_data.sr_no;
document.getElementById("cinvid").innerHTML = page_data.sr_no;

var ed = new Date(page_data.rcp_date);
document.getElementById("invdate").innerHTML = ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear();
document.getElementById("cinvdate").innerHTML = ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear();

document.getElementById('invamnt').innerHTML = (parseFloat(page_data.rcp_amount)).toFixed(2);
document.getElementById('cinvamount').innerHTML = (parseFloat(page_data.rcp_amount)).toFixed(2);

var invamount=(parseFloat(page_data.rcp_amount)).toFixed(2);
var str = toWords(invamount);
document.getElementById('invamtwords').innerHTML = str;
document.getElementById('cinvamtwords').innerHTML = str;


var pos = dealergstin.substr(0, 2);
document.getElementById("dpos").innerHTML = pos;
document.getElementById("cdpos").innerHTML = pos;

document.getElementById("dstname").innerHTML = fetchState(pos);
document.getElementById("cdstname").innerHTML = fetchState(pos);


for(var j=0; j<cvo_data.length;j++) {
	var custName = getCustomerName(cvo_data,page_data.rcvd_from);
	document.getElementById("cnamespan1").innerHTML= custName;
	document.getElementById("ccnamespan1").innerHTML= custName;
	document.getElementById("cnamespan2").innerHTML= custName;
	document.getElementById("ccnamespan2").innerHTML= custName;

	if(custName.localeCompare(cvo_data[j].cvo_name) == 0){
		document.getElementById("caddrspan1").innerHTML =cvo_data[j].cvo_address; 
		document.getElementById("ccaddrspan1").innerHTML =cvo_data[j].cvo_address; 

		var GSTIN =cvo_data[j].cvo_tin;
		var statecode =GSTIN.substring(0,2);
		document.getElementById("cstatespan1").innerHTML = statecode;
		document.getElementById("ccstatespan1").innerHTML = statecode;

		document.getElementById("cgstinspan1").innerHTML = GSTIN;
		document.getElementById("ccgstinspan1").innerHTML = GSTIN;

		document.getElementById("caddrspan2").innerHTML =cvo_data[j].cvo_address; 
		document.getElementById("ccaddrspan2").innerHTML =cvo_data[j].cvo_address; 

		document.getElementById("cstatespan2").innerHTML = statecode;
		document.getElementById("ccstatespan2").innerHTML = statecode;

		document.getElementById("cgstinspan2").innerHTML = GSTIN;
		document.getElementById("ccgstinspan2").innerHTML = GSTIN;

	}
}
function getCustomerName(data,id) {
	if(id==0)
		return "Cash Sales";
	for(var z=0; z<data.length; z++){
		if(data[z].id==id) {
			return data[z].cvo_name;
		}
	}
}




window.close();