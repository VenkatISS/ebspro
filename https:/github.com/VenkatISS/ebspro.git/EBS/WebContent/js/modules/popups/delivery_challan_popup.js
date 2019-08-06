document.getElementById("invid").innerHTML = page_data.sr_no;
document.getElementById("invrefid").innerHTML = page_data.inv_no;
var ed = new Date(page_data.dc_date);
document.getElementById("invdate").innerHTML = ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear();
document.getElementById("dats").innerHTML = ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear();
var irefd = new Date(page_data.dc_ref_date);
document.getElementById("invrefdate").innerHTML = irefd.getDate()+"-"+months[irefd.getMonth()]+"-"+irefd.getFullYear();
document.getElementById("nars").innerHTML = page_data.delivery_instructions;
if(page_data.fleet_id != 0) {
	document.getElementById("vno").innerHTML = getFleetDetails(fleet_data,page_data.fleet_id);
}else {
	document.getElementById("vno").innerHTML = "";
}

var pos = dgstin.substr(0, 2);
document.getElementById("dpos").innerHTML = pos;
document.getElementById("dstname").innerHTML = fetchState(pos);

for(var i=0; i<cvo_data.length;i++) {
	var custName = getCustomerName(cvo_data,page_data.customer_id);
	document.getElementById("cnamespan1").innerHTML= custName;
	document.getElementById("cnamespan2").innerHTML= custName;
	if(custName.localeCompare(cvo_data[i].cvo_name) == 0) {
		document.getElementById("caddrspan1").innerHTML =cvo_data[i].cvo_address; 
		var GSTIN =cvo_data[i].cvo_tin;
		var statecode =GSTIN.substring(0,2);
		document.getElementById("cstatespan1").innerHTML = statecode;
		document.getElementById("cgstinspan1").innerHTML = GSTIN;
		document.getElementById("caddrspan2").innerHTML =cvo_data[i].cvo_address; 
		document.getElementById("cstatespan2").innerHTML = statecode;
		document.getElementById("cgstinspan2").innerHTML = GSTIN;
		
		/*if(GSTIN != "NA")
			document.getElementById("revc").innerHTML = "NO";
		else 
			document.getElementById("revc").innerHTML = "YES";*/
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

for(var i=0;i<cat_cyl_types_data.length;i++) {
	if(cat_cyl_types_data[i].id < 10) {
		var obj = {};
		obj["cat_code"] = cat_cyl_types_data[i].cat_code;
		obj["cat_desc"] = cat_cyl_types_data[i].cat_desc;
		obj["cat_name"] = cat_cyl_types_data[i].cat_name;
		obj["cat_type"] = cat_cyl_types_data[i].cat_type;
		obj["deleted"] = cat_cyl_types_data[i].deleted;
		obj["id"] = (cat_cyl_types_data[i].id) + "e" ;
		cat_cyl_types_data.push(obj);
	}
}

//Process page data
var rowpdhtml = "";
var totalsrow = "";
var taxableTotal = 0;
var cgstTotal = 0;
var sgstTotal = 0;
var igstTotal = 0;
var tgsta = 0;
var totalVal = 0;
for(var n=0; n<page_data.dcItemsList.length; n++){
	var prodCode = page_data.dcItemsList[n].prod_code;
	var spd = fetchProductDetails(cat_cyl_types_data, prodCode);
	var rp = fetchGSTPercent(equipment_data, prodCode);

	if((parseFloat(page_data.dcItemsList[n].cgst_amount)==0) && (parseInt(rp)!=0) && (parseFloat(page_data.dcItemsList[n].igst_amount)!=0)) {
		tgsta = page_data.dcItemsList[n].igst_amount;
	}else if((parseFloat(page_data.dcItemsList[n].cgst_amount)!=0) && (parseInt(rp)!=0) && (parseFloat(page_data.dcItemsList[n].igst_amount)==0)) {		
		tgsta = +(page_data.dcItemsList[n].cgst_amount)+ +(page_data.dcItemsList[n].sgst_amount);
	}
	
	if(prodCode.includes("e")){
		prodCode = prodCode.replace('e','');
		rp = 0;
		tgsta = 0;
	}
	
	var hsncode = fetchProductHSNDetails(equipment_data, prodCode);
	var ur = page_data.dcItemsList[n].unit_rate;
	var dur = page_data.dcItemsList[n].disc_unit_rate;
	var qty = page_data.dcItemsList[n].quantity;
	var taxable = (ur)*(qty);
    var units = eus[fetchUnitCode(equipment_data,prodCode)];	

	totalVal = +(taxable)+ +(tgsta);
    
	rowpdhtml = rowpdhtml + "<tr><td align='center'>"+(n+(+1))+"</td><td align='center'>"+spd+"</td><td align='center'>"+hsncode+"</td>"
	+"<td align='center'>"+qty+"</td><td align='center'>"+units+"</td>"
	+"<td align='center'>"+parseFloat(page_data.dcItemsList[n].unit_rate).toFixed(2)+"</td>"
	+"<td align='center'>"+"-"+"</td>"+"<td align='center'>"+parseFloat(round(taxable,2)).toFixed(2)+"</td>"+"<td align='center'>"+rp+"%</td>"
	+"<td align='center'>"+parseFloat(round(tgsta,2)).toFixed(2)+"</td>"
	+"<td align='center'>"+(round(totalVal,2)).toFixed(2)+"</td>"
	+"</tr>";

	taxableTotal = taxableTotal + taxable;
    cgstTotal = cgstTotal + parseFloat(page_data.dcItemsList[n].cgst_amount);
    sgstTotal = sgstTotal + parseFloat(page_data.dcItemsList[n].sgst_amount);
    igstTotal = igstTotal + parseFloat(page_data.dcItemsList[n].igst_amount);
	var num = parseInt(page_data.dc_amount); 
	var str = toWords(num);
    document.getElementById('invamtwords').innerHTML = str;
}
document.getElementById('m_data_table_body').innerHTML = rowpdhtml;
document.getElementById('taxableVal').innerHTML = parseFloat(round(taxableTotal,2)).toFixed(2);
document.getElementById('igstVal').innerHTML = parseFloat(round(igstTotal,2)).toFixed(2);
document.getElementById('cgstVal').innerHTML = parseFloat(round(cgstTotal,2)).toFixed(2);
document.getElementById('sgstVal').innerHTML = parseFloat(round(sgstTotal,2)).toFixed(2);
document.getElementById('tinvamt').innerHTML = parseFloat(page_data.dc_amount).toFixed(2);




function getFleetDetails(data,id){
	for(var z=0; z<data.length; z++){
		if(data[z].id==id) {
			return data[z].vehicle_no;
		}
	}
}

/*function fetchProductDetails(types, pc) {
	
	for(var i=0; i< types.length; i++){
		if(types[i].id == pc) {
			if(types[i].cat_type=="1" || types[i].cat_type=="2" || types[i].cat_type=="3") {
				return types[i].cat_name+"-"+types[i].cat_desc;
			} else {
				return types[i].cat_desc;
			}
		}
	}
}*/

function fetchProductDetails(types, pc) {
	 for(var i=0; i< types.length; i++){
		 if(types[i].id == pc) {
			 if(types[i].cat_type=="1" || types[i].cat_type=="2" || types[i].cat_type=="3") {
				 if(pc.includes("e"))
					 return types[i].cat_name+"-"+types[i].cat_desc+" (Empties)";
				 else
					 return types[i].cat_name+"-"+types[i].cat_desc;
			 }else {
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