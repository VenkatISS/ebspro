document.getElementById("invid").innerHTML = page_data.sr_no;
var ed = new Date(page_data.si_date);
document.getElementById("invdate").innerHTML = ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear();
document.getElementById("tinvamt").innerHTML = page_data.si_amount;

var pos = dgstin.substr(0, 2);
document.getElementById("dpos").innerHTML = pos;
document.getElementById("dstname").innerHTML = fetchState(pos);

for(var i=0; i<cvo_data.length;i++) {
	var custName = getCustomerName(cvo_data,page_data.customer_id);
	document.getElementById("cnamespan1").innerHTML= custName;
	document.getElementById("cnamespan2").innerHTML= custName;
	if(custName.localeCompare(cvo_data[i].cvo_name) == 0){
		document.getElementById("caddrspan1").innerHTML =cvo_data[i].cvo_address; 
		var GSTIN =cvo_data[i].cvo_tin;
		var statecode =GSTIN.substring(0,2);
		document.getElementById("cstatespan1").innerHTML = statecode;
		document.getElementById("cgstinspan1").innerHTML = GSTIN;
		document.getElementById("caddrspan2").innerHTML =cvo_data[i].cvo_address; 
		document.getElementById("cstatespan2").innerHTML = statecode;
		document.getElementById("cgstinspan2").innerHTML = GSTIN;
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

//Process page data
var rowpdhtml = "";
var totalsrow = "";
var taxableTotal = 0;
var cgstTotal = 0;
var sgstTotal = 0;
var tgsta = 0;var igstTotal = 0;
for(var n=0; n<page_data.detailsList.length; n++){
	var spd = fetchARBProductDetails(arb_data, page_data.detailsList[n].prod_code);
	var hsncode = fetchProductHSNDetails(arb_data, page_data.detailsList[n].prod_code);
	var rp = fetchARBGSTPercent(arb_data, page_data.detailsList[n].prod_code);
	var ur = page_data.detailsList[n].unit_rate;
	var dur = page_data.detailsList[n].disc_unit_rate;
	var qty = page_data.detailsList[n].quantity;
	var taxable = (ur-dur)*(qty);
    var units = eus[fetchUnitCode(arb_data,page_data.detailsList[n].prod_code)];

	if((parseFloat(page_data.detailsList[n].cgst_amount)==0) && (parseInt(rp)!=0) && (parseFloat(page_data.detailsList[n].igst_amount)!=0)) {
		tgsta = page_data.detailsList[n].igst_amount;
	}else if((parseFloat(page_data.detailsList[n].cgst_amount)!=0) && (parseInt(rp)!=0) && (parseFloat(page_data.detailsList[n].igst_amount)==0)) {
		tgsta = +(page_data.detailsList[n].cgst_amount)+ +(page_data.detailsList[n].sgst_amount);
	}else if(parseInt(rp)==0) {
		tgsta = 0;
	}
	
	var totalVal = +(taxable)+ +(tgsta);
	
	rowpdhtml = rowpdhtml + "<tr><td align='center'>"+(n+(+1))+"</td><td align='center'>"+spd+"</td><td align='center'>"+hsncode+"</td>"
		+"<td align='center'>"+qty+"</td><td align='center'>"+units+"</td>"
		+"<td align='center'>"+(round((page_data.detailsList[n].unit_rate),2)).toFixed(2)+"</td>"
		+"<td align='center'>"+(round((page_data.detailsList[n].disc_unit_rate),2)).toFixed(2)+"</td>"+"<td align='center'>"+(round(taxable,2)).toFixed(2)+"</td>"
		+"<td align='center'>"+rp+"%</td>"
		+"<td align='center'>"+(round(tgsta,2)).toFixed(2)+"</td>"
		+"<td align='center'>"+(round(totalVal,2)).toFixed(2)+"</td>"
		+"</tr>";

    taxableTotal = taxableTotal + taxable;
    igstTotal = igstTotal + parseFloat(page_data.detailsList[n].igst_amount);    
    cgstTotal = cgstTotal + parseFloat(page_data.detailsList[n].cgst_amount);
    sgstTotal = sgstTotal + parseFloat(page_data.detailsList[n].sgst_amount);
	var num = parseInt(page_data.si_amount); 
	var str = toWords(num);
    document.getElementById('invamtwords').innerHTML = str;
}
document.getElementById('m_data_table_body').innerHTML = rowpdhtml;
document.getElementById('taxableVal').innerHTML = (round(taxableTotal,2)).toFixed(2);
document.getElementById('igstVal').innerHTML = (round(igstTotal,2)).toFixed(2);
document.getElementById('cgstVal').innerHTML = (round(cgstTotal,2)).toFixed(2);
document.getElementById('sgstVal').innerHTML = (round(sgstTotal,2)).toFixed(2);
document.getElementById('tinvamt').innerHTML = parseFloat(page_data.si_amount).toFixed(2);


function fetchARBProductDetails(types, pc) {
	
	for(var i=0; i< types.length; i++){
		if(types[i].id == pc) {
			return getARBType(types[i].prod_code)+" - "+types[i].prod_brand+" - "+types[i].prod_name;
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

function fetchProductHSNDetails(types, pc) {
	
	for(var i=0; i< types.length; i++){
		if(types[i].id == pc) {
			return types[i].csteh_no;
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

function getCustomerName(data,id) {
	if(id==0)
		return "Cash Sales";
	for(var z=0; z<data.length; z++){
		if(data[z].id==id) {
			return data[z].cvo_name;
		}
	}
}

function fetchARBGSTPercent(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].id == pcid)
			return pc_data[i].gstp;
	}
	return 0;
}

function fetchUnitCode(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].id == pcid)
			return pc_data[i].units;
	}
	return 0;
}

window.close();