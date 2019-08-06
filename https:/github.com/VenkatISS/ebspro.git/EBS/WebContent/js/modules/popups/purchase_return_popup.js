document.getElementById("invrefid").innerHTML = page_data.inv_ref_no;
document.getElementById("invid").innerHTML = page_data.sid;
var ed = new Date(page_data.inv_date);
document.getElementById("invdate").innerHTML = ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear();
document.getElementById("datr").innerHTML = ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear();
var irefd = new Date(page_data.inv_ref_date);
document.getElementById("invrefdate").innerHTML = irefd.getDate()+"-"+months[irefd.getMonth()]+"-"+irefd.getFullYear();
document.getElementById("nars").innerHTML = page_data.narration;

var pos = dgstin.substr(0, 2);
document.getElementById("dpos").innerHTML = pos;
document.getElementById("dstname").innerHTML = fetchState(pos);

var custName;var cvo_cat=-1;
for(var i=0; i<cvo_data.length;i++) {
	if(page_data.cvo_id == cvo_data[i].id) {
		cvo_cat = cvo_data[i].cvo_cat;
	}
	custName = fetchVendorName(cvo_data,page_data.cvo_id);
	if(custName.localeCompare(cvo_data[i].cvo_name) == 0) {
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
if(cvo_cat == 0)
	custName = fetchVendorName(cvo_data,page_data.cvo_id);
else if(cvo_cat == 2)
	custName = omc_name + "-" + fetchVendorName(cvo_data,page_data.cvo_id);

document.getElementById("cnamespan1").innerHTML= custName;
document.getElementById("cnamespan2").innerHTML= custName;

/*function getCustomerName(data,id) {
	if(id==0)
		return "HPCL";
	for(var z=0; z<data.length; z++){
		if(data[z].id==id) {
			return data[z].cvo_name;
		}
	}
}
*/

function fetchVendorName(cvo_data, cvoid) {
	for(var cv=0; cv< cvo_data.length; cv++){
		if(cvo_data[cv].id == cvoid)
			return cvo_data[cv].cvo_name;
	}
}

//Construct Cylinder list
ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(cat_cyl_types_data.length>0) {
	for(var z=0; z<cat_cyl_types_data.length; z++){
		if(cat_cyl_types_data[z].cat_name=='DOMESTIC') {
			//ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"'>"+cat_cyl_types_data[z].cat_code+"-"
			//+cat_cyl_types_data[z].cat_name+"-"+cat_cyl_types_data[z].cat_desc+"</OPTION>";
			for(var y=0; y<equipment_data.length; y++){
				if(equipment_data[y].prod_code == cat_cyl_types_data[z].id) {
					ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"'>"
						+cat_cyl_types_data[z].cat_name+"-"+cat_cyl_types_data[z].cat_desc+"</OPTION>";
					break;
				}
			}
		}
	}
}

//Construct Customer Type html
custdatahtml = "<OPTION VALUE='0'>CASH SALES / HOUSEHOLDS</OPTION>";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
		if(cvo_data[z].cvo_cat==1)
			custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
	}
}
//document.getElementById("cSpan").innerHTML = "<select id='cust_id' name='cust_id'>"+custdatahtml+"</select>";

//Construct Staff html
staffdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(staff_data.length>0) {
	for(var z=0; z<staff_data.length; z++){
		staffdatahtml=staffdatahtml+"<OPTION VALUE='"+staff_data[z].id+"'>"+staff_data[z].emp_name+"</OPTION>";
	}
}

//Area codes html
areacodeshtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(area_codes_data.length>0) {
	for(var z=0; z<area_codes_data.length; z++){
		areacodeshtml=areacodeshtml+"<OPTION VALUE='"+area_codes_data[z].id+"'>"+area_codes_data[z].area_code+" - "+area_codes_data[z].area_name+"</OPTION>";
	}
}


var rowpdhtml = "";
var totalsrow = "";
var dis = 0;
var taxableTotal = 0;
var cgstTotal = 0;
var sgstTotal = 0;
var igstTotal = 0;
var spd;var hsncode;var units;var rp;var prodId;
for(var n=0; n < page_data.detailsList.length; n++) {
	prodId = page_data.detailsList[n].prod_code;
	if(prodId < 100) {
		spd = fetchProductDetails(cat_cyl_types_data, prodId);
		hsncode = fetchProductHSNDetails(equipment_data, page_data.detailsList[n].prod_code);
		units = eus[fetchEQPUnitCode(equipment_data,page_data.detailsList[n].prod_code)];
		rp = fetchGSTPercent(equipment_data, page_data.detailsList[n].prod_code);	
	} else {
		spd = fetchARBProductDetails(arb_data, page_data.detailsList[n].prod_code);
		hsncode = fetchARBHSNCode(arb_data, page_data.detailsList[n].prod_code);
		units = eus[fetchARBUnitCode(arb_data,page_data.detailsList[n].prod_code)];
		rp = fetchARBGSTPercent(arb_data, page_data.detailsList[n].prod_code);
	}
	
	var ur = page_data.detailsList[n].unit_rate;
	var retQty = page_data.detailsList[n].rtn_qty;
	var taxable = (ur)*(page_data.detailsList[n].rtn_qty);
	var gstv = ((taxable * rp) / 100);

	var totalVal = +(taxable)+ +(gstv);
	
	rowpdhtml = rowpdhtml + "<tr><td align='center'>"+(n+(+1))+"</td><td align='center'>"+spd+"</td><td align='center'>"+hsncode+"</td>"
		+"<td align='center'>"+retQty+"</td><td align='center'>"+units+"</td>"
		+"<td align='center'>"+page_data.detailsList[n].net_weight+"</td>"
		+"<td align='center'>"+(parseFloat(page_data.detailsList[n].unit_rate)).toFixed(2)+"</td>"
		+"<td align='center'>0.00</td><td align='center'>"+(round(taxable,2)).toFixed(2)+"</td>"
		+"<td align='center'>"+rp+"%</td><td align='center'>"+(round(gstv,2)).toFixed(2)+"</td>"
		+"<td align='center'>"+(round(totalVal,2)).toFixed(2)+"</td>"
		+"</tr>";
	
    taxableTotal = taxableTotal + taxable;
    cgstTotal = cgstTotal + parseFloat(page_data.detailsList[n].cgst_amount);
    sgstTotal = sgstTotal + parseFloat(page_data.detailsList[n].sgst_amount);
    igstTotal = igstTotal + parseFloat(page_data.detailsList[n].igst_amount);    
	var num = parseInt(page_data.inv_amt); 
	var str = toWords(num);
    document.getElementById('invamtwords').innerHTML = str;
}
document.getElementById('m_data_table_body').innerHTML = rowpdhtml;
document.getElementById('taxableVal').innerHTML = (round(taxableTotal,2)).toFixed(2);
document.getElementById('igstVal').innerHTML = (round(igstTotal,2)).toFixed(2);
document.getElementById('cgstVal').innerHTML = (round(cgstTotal,2)).toFixed(2);
document.getElementById('sgstVal').innerHTML = (round(sgstTotal,2)).toFixed(2);
document.getElementById("tinvamt").innerHTML = (parseFloat(page_data.inv_amt)).toFixed(2);


function fetchGSTPercent(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid)
			return pc_data[i].gstp;
	}
	return 0;
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

function fetchProductHSNDetails(types, pc) {
	
	for(var i=0; i< types.length; i++){
		if(types[i].prod_code == pc) {
			return types[i].csteh_no;
		}
	}
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
function fetchARBGSTPercent(pc_data, pcid) {
	
	for(var r=0; r< pc_data.length; r++){
		if(pc_data[r].id == pcid)
			return pc_data[r].gstp;
	}
	return 0;
}

window.close();