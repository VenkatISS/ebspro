/*-------------------------For Finding IP Address of the system on which this app is running -------------------------*/
// NOT USING ...
//we will get call to this function from global.js and global.jsp
function findIP(onNewIP) { //  onNewIp - your listener function for new IPs
  var myPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection; //compatibility for firefox and chrome
  var pc = new myPeerConnection({iceServers: []}),
    noop = function() {},
    localIPs = {},
    ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g,
    key;

  function ipIterate(ip) {
    if (!localIPs[ip]) onNewIP(ip);
    localIPs[ip] = true;
  }
  pc.createDataChannel(""); //create a bogus data channel
  pc.createOffer(function(sdp) {
    sdp.sdp.split('\n').forEach(function(line) {
      if (line.indexOf('candidate') < 0) return;
      line.match(ipRegex).forEach(ipIterate);
    });
    pc.setLocalDescription(sdp, noop, noop);
  }, noop); // create offer and set local description
  pc.onicecandidate = function(ice) { //listen for candidate events
    if (!ice || !ice.candidate || !ice.candidate.candidate || !ice.candidate.candidate.match(ipRegex)) return;
    ice.candidate.candidate.match(ipRegex).forEach(ipIterate);
  };
 }

//--------------------------

function showPage(page, actionId)
{
	href= "ShowPagesControlServlet";	
	document.getElementById('forwardForm').action = href;
	document.getElementById('forwardForm').page.value = page;   
	document.getElementById('forwardForm').actionId.value = actionId;		
	document.getElementById('forwardForm').submit();
}
function doAction(servlet,page, actionId)
{
	href= servlet;	
	document.getElementById('forwardForm').action = href;
	document.getElementById('forwardForm').page.value = page;   
	document.getElementById('forwardForm').actionId.value = actionId;		
	document.getElementById('forwardForm').submit();
}
function logoutDealer(formobj){
	formobj.submit();
}
function doRowDelete(tdobject) {
	tdobject.parentNode.parentNode.remove();
}
function doRowDeleteInTranxs(tdobject,amt,inv_amt,tbody) {
	var count = tbody.getElementsByTagName('tr').length;
	var x = tdobject.parentNode.parentNode;

	if(count==1)
		inv_amt.value = 0;
	else if(count>1 && (amt[x.rowIndex-1].value)!= "")
		inv_amt.value = parseInt(inv_amt.value)-parseInt(amt[x.rowIndex-1].value);
	tdobject.parentNode.parentNode.remove();
}

function fetchProductDetailsOld(types, pc) {
	
	for(var i=0; i< types.length; i++){
		if(types[i].id == pc) {
			if(types[i].cat_type=="1") {
				return types[i].cat_code+"-"+types[i].cat_name+"-"+types[i].cat_desc;
			} else {
				return types[i].cat_desc;
			}
		}
	}
}

function fetchARBProductDetails(types, pc) {
	
	for(var i=0; i< types.length; i++){
		if(types[i].id == pc) {
			return getARBType(types[i].prod_code)+" - "+types[i].prod_brand+" - "+types[i].prod_name;
		}
	}
}

function fetchProductDetails(types, pc) {
	
	for(var i=0; i< types.length; i++){
		if(types[i].id == pc) {
			if(types[i].cat_type=="1" || types[i].cat_type=="2" || types[i].cat_type=="3") {
				return types[i].cat_name+"-"+types[i].cat_desc;
			} else {
				return types[i].cat_desc;
			}
		}
	}
}

function fetchProductName(types, pc) {
	
	for(var i=0; i< types.length; i++){
		if(types[i].id == pc)
			return types[i].cat_desc;
	}
}

function fetchVendorName(cvo_data, cvoid) {
	
	for(var i=0; i< cvo_data.length; i++){
		if(cvo_data[i].id == cvoid)
			return cvo_data[i].cvo_name;
		
	}
}

function fetchVendorGSTN(cvo_data, cvoid) {
	
	for(var i=0; i< cvo_data.length; i++){
		if(cvo_data[i].id == cvoid)
			return cvo_data[i].cvo_tin;
		
	}
}

function fetchVATPercent(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid)
			return pc_data[i].vatp;
	}
	return 0;
}

function fetchGSTPercent(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid)
			return pc_data[i].gstp;
	}
	return 0;
}

function fetchHSNCode(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid)
			return pc_data[i].csteh_no;
	}
	return 0;
}

function fetchARBHSNCode(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].id == pcid)
			return pc_data[i].csteh_no;
	}
	return 0;
}

function fetchUnitCode(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid)
			return pc_data[i].units;
	}
	return 0;
}

function fetchARBUnitCode(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].id == pcid)
			return pc_data[i].units;
	}
	return 0;
}

function fetchSecurityDeposit(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid)
			return pc_data[i].security_deposit;
	}
	return 0;
}

function fetchARBGSTPercent(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].id == pcid)
			return pc_data[i].gstp;
	}
	return 0;
}

function fetchRefillUnitPrice(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid)
			return pc_data[i].base_price;
		
	}
	return 0;
}

function fetchRefillOfferPriceBp(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid) {
			if(pc_data[i].op_base_price)
				return pc_data[i].op_base_price;
			else 
				return pc_data[i].base_price;
		}
	}
	return 0;
}

function fetchARBUnitPrice(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].arb_code == pcid)
			return pc_data[i].base_price;
		
	}
	return 0;
}

function fetchARBOfpBasicPrice(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].arb_code == pcid) {
			if(pc_data[i].op_base_price) 
				return pc_data[i].op_base_price;
			else
				return pc_data[i].base_price;
		}		
	}
	return 0;
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

function getCustomerGSTIN(data,id) {
	if(id==0)
		return "NA";
	for(var z=0; z<data.length; z++){
		if(data[z].id==id) {
			return data[z].cvo_tin;
		}
	}
}

function getStaffName(data,id){
	for(var z=0; z<data.length; z++){
		if(data[z].id==id) {
			return data[z].emp_name;
		}
	}
}

function getFleetDetails(data,id){
	for(var z=0; z<data.length; z++){
		if(data[z].id==id) {
			return data[z].vehicle_no;
		}
	}
}


function getBankName(data,id){
	for(var z=0; z<data.length; z++){
		if(data[z].id==id) {
			return data[z].bank_code+"-"+data[z].bank_acc_no;
		}
	}
}

function getPartnerName(data,id){
	for(var z=0; z<data.length; z++){
		if(data[z].id==id) {
			return data[z].partner_name;
		}
	}
}

function getPartnerOB(data,id){
	for(var z=0; z<data.length; z++){
		if(data[z].id==id) {
			return data[z].opening_balance;
		}
	}
}

function getAreaCodeName(data,id){
	for(var z=0; z<data.length; z++){
		if(data[z].id==id) {
			return data[z].area_name;
		}
	}
}

function getExpenditureSubHead(id){
	for(var a=0; a<eshvalues.length; a++){
		if(eshvalues[a]==id){
			return eshnames[a];
		}
	}
}

function getARBType(prod_code){
	if(prod_code==13)
		return "STOVE";
	if(prod_code==14)
		return "SURAKSHA";
	if(prod_code==15)
		return "LIGHTER";
	if(prod_code==16)
		return "KITCHENWARE";
	if(prod_code==17)
		return "OTHERS";
}

function fetchRefillDeposit(equip_data,pid) {
	for(var i=0; i<equip_data.length; i++) {
		if(equip_data[i].prod_code == pid)
			return equip_data[i].security_deposit;
	}
	return 0;
}

function fetchFinancialYear(fy){
	if(fy=="2017")
		return "2017-18";
	return 0;
}


function makeEleReadOnly(efrz,len){
	for(var i=0;i<len;i++) {
		efrz[i].setAttribute("readOnly","true");
	}
}

function remEleReadOnly(efrz,len){
	for(var i=0;i<len;i++){
		efrz[i].readOnly = false;
	}
}

function disableSelect(sfrz,len){
	for( var i=0;i<len;i++) {
		if(sfrz[i].selected==false)
			sfrz[i].setAttribute("disabled","true");
	}
}

function enableSelect(sfrz,len){
	for( var i=0;i<len;i++) {
			sfrz[i].disabled = false;
	}
}

function checkRowData(saddv,eaddv) {
	for(var i=0;i<saddv.length;i++){
		var selv=saddv[i].options;
		if(selv.selectedIndex==0){
			return false;
		}
	}
	for(var z=0;z<eaddv.length;z++){
		if(eaddv[z].value=="") {
			return false;
		}
	}	
}

function restrictChangingValues(freez) {
	$(freez).change(function(){
		document.getElementById("save_data").disabled = true;
	});
}

function restrictChangingAllValues(freez,fetch) {
	$(freez).change(function(){
		document.getElementById("save_data").disabled = true;
	});
	$(fetch).change(function() {
		document.getElementById("calc_data").disabled = true;
		document.getElementById("save_data").disabled = true;
	});
}


function disableSelectGstn(regstn,len){
	for( var i=0;i<len;i++) {
		if(regstn[i].selected==false)
			regstn[i].setAttribute("disabled","true");
	}
}

function enableSelectGstn(regstn,len){
	for( var i=0;i<len;i++) {
		regstn[i].disabled = false;
	}
}

function alertdialogue(){
	$( function() {
		$( "#dialog-1" ).dialog({
			modal: true,
			resizable: false,
			buttons: {
				Ok: function() {
					$( this ).dialog( "close" );
				}
			}
		});
	} );
}

function alertdialogueWithCollapse(){
	$( function() {
		$( "#dialog-1" ).dialog({
			modal: true,
			resizable: false,
			buttons: {
				Ok: function() {
					$( this ).dialog( "close" );
					$("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');
				}
			}
		});
	} );
}

function confirmDialogue(formobj,delactionId,id){
	$("#dialog-confirm").dialog({
        resizable: false,
        modal: true,
        title: "Confirm",
        height: 150,
        width: 400,
           buttons: {
            "Yes": function () {
                $(this).dialog('close');
        		formobj.actionId.value = delactionId;
        		if(formobj.itemId){
        			formobj.itemId.value = id;
        		}else{
        			formobj.dataId.value = id;	
        		}
        		formobj.submit();
            },
            "No": function () {
                $(this).dialog('close');
            }
        }
  });
}


function confirmTxtDialogue(formobj){
	$("#dialog-confirm").dialog({
        resizable: false,
        modal: true,
        title: "Confirm",
        height: 150,
        width: 400,
           buttons: {
            "Yes": function () {
                $(this).dialog('close');
                formobj.submit();
            },
            "No": function () {
                $(this).dialog('close');
                return;
            }
        }
  });
}

$(document).ready(function() {
	$(function() { $(".modal-content").draggable(); });
	$("#modal-content").draggable({ containment: 'cursor' });
	
	$("#modal-content").on("dblclick mousedown", function(e){
		$('#modal-content').css( 'cursor', '-webkit-grabbing' );
	});
	$("#modal-content").on("click mouseup", function(e){
		$('#modal-content').css( 'cursor', 'default' );
	});		
});
