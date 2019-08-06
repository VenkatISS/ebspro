document.getElementById("invid").innerHTML = page_data.sr_no;
var ed = new Date(page_data.inv_date);
document.getElementById("invdate").innerHTML = ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear();
document.getElementById("cnospan").innerHTML = page_data.customer_no;

var pos = dgstin.substr(0, 2);
document.getElementById("dpos").innerHTML = pos;
document.getElementById("dstname").innerHTML = fetchState(pos);

//Process page data
var rowpdhtml = "";
var totalsrow = "";
var dis = 0;
var taxableTotal = 0;
var totalsd = 0;
var cgstTotal = 0;
var sgstTotal = 0;
var spd;var hsncode;var units;var rp;
for(var n=0; n<page_data.detailsList.length; n++){
	var prodId = page_data.detailsList[n].prod_code;
	if(prodId < 100) {
		spd = fetchProductDetails(prod_types, page_data.detailsList[n].prod_code);
		if(prodId < 18) {
			hsncode = fetchProductHSNDetails(equipment_data, page_data.detailsList[n].prod_code);
			units = eus[fetchEQPUnitCode(equipment_data,page_data.detailsList[n].prod_code)];
			rp = fetchGSTPercent(equipment_data, page_data.detailsList[n].prod_code);
		}else if(prodId >= 18){
			hsncode = fetchSACDetails(services_data, page_data.detailsList[n].prod_code);
			units = "NOS";
			
			if(prodId <= 29)
				rp = "18";
			else if(prodId > 29)
				rp = "0";
			
		}	
	} else {
		spd = fetchARBProductDetails(cat_arb_types_data, page_data.detailsList[n].prod_code);
		hsncode = fetchARBHSNCode(cat_arb_types_data, page_data.detailsList[n].prod_code);
		units = eus[fetchARBUnitCode(cat_arb_types_data,page_data.detailsList[n].prod_code)];
		rp = fetchARBGSTPercent(cat_arb_types_data, page_data.detailsList[n].prod_code);
	}

	var ur = page_data.detailsList[n].unit_rate;
	var dur = page_data.detailsList[n].disc_unit_rate;
	var qty = page_data.detailsList[n].quantity;
	var taxable = (ur-dur)*(qty);
	var tgsta = (taxable*rp)/100 ;
	var totalVal = +(taxable)+ +(tgsta);
	
	if(rp != "NA" && rp!=0){
		rowpdhtml = rowpdhtml + "<tr><td align='center'>"+(n+(+1))+"</td><td align='center'>"+spd+"</td><td align='center'>"+hsncode+"</td>"
			+"<td align='center'>"+qty+"</td><td align='center'>"+units+"</td>"
			+"<td align='center'>"+(parseFloat(page_data.detailsList[n].unit_rate)).toFixed(2)+"</td>"
			+"<td align='center'>"+(parseFloat(page_data.detailsList[n].disc_unit_rate)).toFixed(2)+"</td>"+"<td align='center'>"+(round(taxable,2)).toFixed(2)+"</td>"
			+"<td align='center'>"+rp+"%</td>"
			+"<td align='center'>"+(round(tgsta,2)).toFixed(2)+"</td>"
			+"<td align='center'>"+(round(totalVal,2)).toFixed(2)+"</td>"
			+"</tr>";
	    cgstTotal = cgstTotal + parseFloat(page_data.detailsList[n].cgst_amount);
	    sgstTotal = sgstTotal + parseFloat(page_data.detailsList[n].sgst_amount);	    
	}else if(rp == "NA" || rp==0) {
		rowpdhtml = rowpdhtml + "<tr><td align='center'>"+(n+(+1))+"</td><td align='center'>"+spd+"</td><td align='center'>"+hsncode+"</td>"
			+"<td align='center'>"+qty+"</td><td align='center'>"+units+"</td>"
			+"<td align='center'>"+(parseFloat(page_data.detailsList[n].unit_rate)).toFixed(2)+"</td>"
			+"<td align='center'>"+(parseFloat(page_data.detailsList[n].disc_unit_rate)).toFixed(2)+"</td>"+"<td align='center'>"+(round(taxable,2)).toFixed(2)+"</td>"
			+"<td align='center'>"+rp+"</td>"
			+"<td align='center'>0.00</td>"
			+"<td align='center'>0.00</td>"
			+"</tr>";		
	    cgstTotal = cgstTotal + 0.00;
	    sgstTotal = sgstTotal + 0.00;
	}	
    taxableTotal = taxableTotal + taxable;
	var num = parseInt(page_data.inv_amount); 
	var str = toWords(num);
    document.getElementById('invamtwords').innerHTML = str;

}
var totalinvamt = parseFloat(page_data.inv_amount);
totalsd = totalsd + parseFloat(page_data.dep_amount);
var tamt = totalinvamt-totalsd;
document.getElementById("tsd").innerHTML = (round(totalsd,2)).toFixed(2);
document.getElementById("tamt").innerHTML = (round(tamt,2)).toFixed(2);
document.getElementById('m_data_table_body').innerHTML = rowpdhtml;

document.getElementById('taxableVal').innerHTML = (round(taxableTotal,2)).toFixed(2);
document.getElementById('igstVal').innerHTML = "0.00";
document.getElementById('cgstVal').innerHTML = (round(cgstTotal,2)).toFixed(2);
document.getElementById('sgstVal').innerHTML = (round(sgstTotal,2)).toFixed(2);
document.getElementById("tinvamt").innerHTML = (parseFloat(totalinvamt)).toFixed(2);

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
function fetchARBProductDetails(types, pc) {
	
	for(var j=0; j< types.length; j++){
		if(types[j].id == pc) {
			return getARBType(types[j].prod_code)+" - "+types[j].prod_brand+" - "+types[j].prod_name;
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
function fetchARBHSNCode(pc_data, pcid) {
	
	for(var k=0; k< pc_data.length; k++){
		if(pc_data[k].id == pcid)
			return pc_data[k].csteh_no;
	}
	return 0;
}
function fetchProductHSNDetails(types, pc) {
	
	for(var l=0; l< types.length; l++){
		if(types[l].prod_code == pc) {
			return types[l].csteh_no;
		}
	}
}

function fetchSACDetails(types, pc) {
	
	for(var m=0; m< types.length; m++){
		if(types[m].prod_code == pc) {
			return types[m].sac_code;
		}
	}
}

function fetchGSTPercent(pc_data, pcid) {
	
	for(var d=0; d< pc_data.length; d++){
		if(pc_data[d].prod_code == pcid)
			return pc_data[d].gstp;
	}
	return 0;
}

function fetchCustomerDetails(cdata,cid,dr) {
	if(cid==0)
		return "---";
	for(var i=0; i<cdata.length; i++){
		if(cdata[i].id == cid){
			if(dr == 1){ //get name
				return cdata[i].cvo_name;
			} else if (dr == 2) {
				return cdata[i].cvo_address;
			} else if (dr == 3) {
				return cdata[i].cvo_tin;
			}
			
		}
	}
}

function fetchEQPUnitCode(pc_data, pcid) {
	
	for(var p=0; p< pc_data.length; p++){
		if(pc_data[p].prod_code == pcid)
			return pc_data[p].units;
	}
	return 0;
}
function fetchARBUnitCode(pc_data, pcid) {
	
	for(var q=0; q< pc_data.length; q++){
		if(pc_data[q].id == pcid)
			return pc_data[q].units;
	}
	return 0;
}
function fetchARBGSTPercent(pc_data, pcid) {
	
	for(var r=0; r< pc_data.length; r++){
		if(pc_data[r].id == pcid)
			return pc_data[r].gstp;
	}
	return 0;
}

window.close();