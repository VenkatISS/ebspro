document.getElementById("invid").innerHTML = page_data.sr_no;
var ed = new Date(page_data.si_date);
document.getElementById("invdate").innerHTML = ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear();
document.getElementById("tinvamt").innerHTML = page_data.si_amount;

var pos = dealergstin.substr(0, 2);
document.getElementById("dpos").innerHTML = pos;
document.getElementById("dstname").innerHTML = fetchState(pos);

for(var j=0; j<cvo_data.length;j++) {
	var custName = getCustomerName(cvo_data,page_data.customer_id);
	document.getElementById("cnamespan1").innerHTML= custName;
	document.getElementById("cnamespan2").innerHTML= custName;
	if(custName.localeCompare(cvo_data[j].cvo_name) == 0){
		document.getElementById("caddrspan1").innerHTML =cvo_data[j].cvo_address; 
		var GSTIN =cvo_data[j].cvo_tin;
		var statecode =GSTIN.substring(0,2);
		document.getElementById("cstatespan1").innerHTML = statecode;
		document.getElementById("cgstinspan1").innerHTML = GSTIN;
		document.getElementById("caddrspan2").innerHTML =cvo_data[j].cvo_address; 
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

var rowpdhtml = "" ;
var totalsrow = "" ;
var dis = 0 ;
var taxableTotal = 0 ;
var cgstTotal = 0 ;var igstTotal = 0 ;
var sgstTotal = 0 ;	var totalVal = 0;var tgsta = 0;
for(var n=0; n<page_data.detailsList.length; n++) {
	var spd = fetchProductDetails(cat_cyl_types_data, page_data.detailsList[n].prod_code);
	var hsncode = fetchProductHSNDetails(equipment_data, page_data.detailsList[n].prod_code);
	var rp = fetchGSTPercent(equipment_data, page_data.detailsList[n].prod_code);
	var ur = page_data.detailsList[n].unit_rate;
	var dur = page_data.detailsList[n].disc_unit_rate;
	var ppc = page_data.detailsList[n].psv_cyls;
	var qty = page_data.detailsList[n].quantity-ppc;
	var taxable = (ur-dur)*(qty);
    var units = eus[fetchUnitCode(equipment_data,page_data.detailsList[n].prod_code)];	
    
    if(qty == 0){
    	tgsta = 0;
    }else {	
    	if((parseFloat(page_data.detailsList[n].cgst_amount)==0) && (parseInt(rp)!=0) && (parseFloat(page_data.detailsList[n].igst_amount)!=0)) {
    		tgsta = page_data.detailsList[n].igst_amount;
    	}else if((parseFloat(page_data.detailsList[n].cgst_amount)!=0) && (parseInt(rp)!=0) && (parseFloat(page_data.detailsList[n].igst_amount)==0)) {
    		tgsta = +(page_data.detailsList[n].cgst_amount)+ +(page_data.detailsList[n].sgst_amount);
    	}else if(parseInt(rp)==0) {
    		tgsta = 0;
    	}
    }	
	totalVal = +(taxable)+ +(tgsta);
	
    rowpdhtml = rowpdhtml + "<tr><td align='center'>"+(n+(+1))+"</td><td align='center'>"+spd+"</td><td align='center'>"+hsncode+"</td>"
	+"<td align='center'>"+qty+"</td><td align='center'>"+units+"</td>"
	+"<td align='center'>"+parseFloat(page_data.detailsList[n].unit_rate).toFixed(2)+"</td>"
	+"<td align='center'>"+parseFloat(page_data.detailsList[n].disc_unit_rate).toFixed(2)+"</td>"+"<td align='center'>"+parseFloat(round(taxable,2)).toFixed(2)+"</td>"
	+"<td align='center'>"+rp+"</td>"
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
document.getElementById('taxableVal').innerHTML = parseFloat(round(taxableTotal,2)).toFixed(2);
document.getElementById('igstVal').innerHTML = parseFloat(round(igstTotal,2)).toFixed(2);
document.getElementById('cgstVal').innerHTML = parseFloat(round(cgstTotal,2)).toFixed(2);
document.getElementById('sgstVal').innerHTML = parseFloat(round(sgstTotal,2)).toFixed(2);
document.getElementById('tinvamt').innerHTML = (parseFloat(page_data.si_amount)).toFixed(2);

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

function fetchProductHSNDetails(types, pc) {
	
	for(var i=0; i< types.length; i++){
		if(types[i].prod_code == pc) {
			return types[i].csteh_no;
		}
	}
}

function fetchGSTPercent(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid)
			return pc_data[i].gstp;
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

function fetchUnitCode(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid)
			return pc_data[i].units;
	}
	return 0;
}

window.close();