document.getElementById("invid").innerHTML = page_data.sr_no;
var ed = new Date(page_data.qtn_date);
document.getElementById("invdate").innerHTML = ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear();
document.getElementById("tinvamt").innerHTML = page_data.qtn_amount;


var pos = dgstin.substr(0, 2);
document.getElementById("dpos").innerHTML = pos;
document.getElementById("dstname").innerHTML = fetchState(pos);

if(page_data.foot_notes == "EMPTY")
	document.getElementById("nars").innerHTML = "NA";
else
	document.getElementById("nars").innerHTML = page_data.foot_notes;

for(var i=0; i<cvo_data.length;i++){
	var custName = "";
	if(page_data.customer_id == 0)
		custName = page_data.customer_name;
	else
		custName = getCustomerName(cvo_data,page_data.customer_id);
	
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
var igstTotal = 0;
for(var n=0; n<page_data.quotationItemsList.length; n++){
	var spd = fetchProductDetails(cat_cyl_types_data, page_data.quotationItemsList[n].prod_code);
	var hsncode = fetchProductHSNDetails(equipment_data, page_data.quotationItemsList[n].prod_code);
	var tvpp = fetchGSTPercent(equipment_data, page_data.quotationItemsList[n].prod_code);
	var rp = (tvpp)/2;
	var ur = page_data.quotationItemsList[n].unit_rate;
	var dur = page_data.quotationItemsList[n].disc_unit_rate;
	var qty = page_data.quotationItemsList[n].quantity;
	var taxable = (ur-dur)*(qty);
    var units = eus[fetchUnitCode(equipment_data,page_data.quotationItemsList[n].prod_code)];	
	 	
	var tgsta;
	if((parseFloat(page_data.quotationItemsList[n].cgst_amount)==0) && (parseInt(tvpp)!=0) && (parseFloat(page_data.quotationItemsList[n].igst_amount)!=0)) {
		tgsta = page_data.quotationItemsList[n].igst_amount;
	}else if((parseFloat(page_data.quotationItemsList[n].cgst_amount)!=0) && (parseInt(tvpp)!=0) && (parseFloat(page_data.quotationItemsList[n].igst_amount)==0)) {		
		tgsta = +(page_data.quotationItemsList[n].cgst_amount)+ +(page_data.quotationItemsList[n].sgst_amount);
	}
	var totalVal = +(taxable)+ +(tgsta);
    
	rowpdhtml = rowpdhtml + "<tr><td align='center'>"+(n+(+1))+"</td><td align='center'>"+spd+"</td><td align='center'>"+hsncode+"</td>"
		+"<td align='center'>"+qty+"</td><td align='center'>"+units+"</td>"
		+"<td align='center'>"+(parseFloat(page_data.quotationItemsList[n].unit_rate)).toFixed(2)+"</td>"
		+"<td align='center'>"+(parseFloat(page_data.quotationItemsList[n].disc_unit_rate)).toFixed(2)+"</td>"+"<td align='center'>"+(round(taxable,2)).toFixed(2)+"</td>"
		+"<td align='center'>"+tvpp+"%</td>"
		+"<td align='center'>"+(round(tgsta,2)).toFixed(2)+"</td>"
		+"<td align='center'>"+(round(totalVal,2)).toFixed(2)+"</td>"
		+"</tr>";
	
    taxableTotal = taxableTotal + taxable;
    cgstTotal = cgstTotal + parseFloat(page_data.quotationItemsList[n].cgst_amount);
    sgstTotal = sgstTotal + parseFloat(page_data.quotationItemsList[n].sgst_amount);
    igstTotal = igstTotal + parseFloat(page_data.quotationItemsList[n].igst_amount);
	var num = parseInt(page_data.qtn_amount); 
	var str = toWords(num);
    document.getElementById('invamtwords').innerHTML = str;
}

document.getElementById('m_data_table_body').innerHTML = rowpdhtml;
document.getElementById('taxableVal').innerHTML = (round(taxableTotal,2)).toFixed(2);
document.getElementById('igstVal').innerHTML = (round(igstTotal,2)).toFixed(2);
document.getElementById('cgstVal').innerHTML = (round(cgstTotal,2)).toFixed(2);
document.getElementById('sgstVal').innerHTML = (round(sgstTotal,2)).toFixed(2);
document.getElementById('tinvamt').innerHTML = (parseFloat(page_data.qtn_amount)).toFixed(2);


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