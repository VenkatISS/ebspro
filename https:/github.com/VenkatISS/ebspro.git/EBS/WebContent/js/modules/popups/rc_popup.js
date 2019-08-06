var pos = dgstin.substr(0, 2);
document.getElementById("dpos").innerHTML = pos;
document.getElementById("dstname").innerHTML = fetchState(pos);
document.getElementById("cdpos").innerHTML = pos;
document.getElementById("cdstname").innerHTML = fetchState(pos);

//Process page data
var rowpdhtml = "";
var totalsrow = "";
var dis = 0;
var taxableTotal = 0;
var cgstTotal = 0;
var sgstTotal = 0;
var igstTotal = 0;
var totalsd = 0;
var invamt1 = 0;
var invamt=0;
var tgsta = 0;
var totalVal = 0;var taxable=0;
for(var n=0; n<page_data.length; n++){
	var spd = fetchProductDetails(cat_cyl_types_data, page_data[n].prod_code);
	var hsncode = fetchProductHSNDetails(equipment_data, page_data[n].prod_code);
	var rp1 = fetchGSTPercent(equipment_data, page_data);
	var rp=(+rp1)+(+36);
	var ur = page_data[n].unit_rate;
	//var dur = page_data[i].disc_unit_rate;
	var pidv = page_data[n].prod_code;
	var up = fetchRefillUnitPrice(refill_prices_data,pidv);
	invamt = parseInt(page_data[n].rc_amount);
//	var taxable1 = invamt-parseInt(page_data[n].cyl_deposit)-parseInt(page_data[n].reg_deposit)-parseFloat(page_data[n].cgst_amount)-parseFloat(page_data[n].sgst_amount)-parseFloat(page_data[n].igst_amount);

	var taxable1 = +(up*page_data[n].no_of_cyl)+ +(page_data[n].dgcc_amount)+ +(page_data[n].admin_charges);
	
	taxable=round(taxable1,2);
    var units = eus[fetchUnitCode(equipment_data,page_data[n].prod_code)];
    tgsta = +(page_data[n].cgst_amount)+ +(page_data[n].sgst_amount);
	totalVal = +(taxable)+ +(tgsta);
        
	rowpdhtml = rowpdhtml + "<tr><td align='center'>"+(n+(+1))+"</td><td align='center'>"+spd+"</td><td align='center'>"+hsncode+"</td>"
		+"<td align='center'>"+page_data[n].no_of_cyl+"</td><td align='center'>"+units+"</td>"
		+"<td align='center'>"+(parseFloat(up)).toFixed(2)+"</td>"
		+"<td align='center'>"+(parseFloat(page_data[n].dgcc_amount)).toFixed(2)+"</td>"
		+"<td align='center'>"+(parseFloat(page_data[n].admin_charges)).toFixed(2)+"</td>"
		+"<td align='center'>"+(parseFloat(taxable)).toFixed(2)+"</td>"
		+"<td align='center'>"+rp+"</td>"
		+"<td align='center'>"+(round(tgsta,2)).toFixed(2)+"</td>"
		+"<td align='center'>"+(round(totalVal,2)).toFixed(2)+"</td>"
		+"</tr>";
    
	taxableTotal = taxableTotal + taxable;
    
    cgstTotal = cgstTotal + parseFloat(page_data[n].cgst_amount);
    sgstTotal = sgstTotal + parseFloat(page_data[n].sgst_amount);
    igstTotal = igstTotal + parseFloat(page_data[n].igst_amount);
    
    var invamt2=taxableTotal +parseFloat(page_data[n].cgst_amount)+parseFloat(page_data[n].sgst_amount)+parseFloat(page_data[n].igst_amount);
    invamt1=round(invamt2,2);
	var str = toWords(invamt);
    
    document.getElementById("invid").innerHTML = page_data[n].sr_no;
    document.getElementById("cinvid").innerHTML = page_data[n].sr_no;

    var ed = new Date(page_data[n].rc_date);
    document.getElementById("invdate").innerHTML = ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear();
    document.getElementById("cinvdate").innerHTML = ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear();

    document.getElementById("cnospan").innerHTML = page_data[n].customer_no;
    document.getElementById("ccnospan").innerHTML = page_data[n].customer_no;

    totalsd = totalsd + (parseFloat(page_data[n].cyl_deposit) + parseFloat(page_data[n].reg_deposit));
}

document.getElementById('invamtwords').innerHTML = str;
document.getElementById('cinvamtwords').innerHTML = str;

document.getElementById("tsda").innerHTML = (round(totalsd,2)).toFixed(2);
document.getElementById("ctsda").innerHTML = (round(totalsd,2)).toFixed(2);

document.getElementById("tamt").innerHTML = (parseInt(invamt)).toFixed(2);
document.getElementById("ctamt").innerHTML = (parseInt(invamt)).toFixed(2);

document.getElementById('m_data_table_body').innerHTML = rowpdhtml;
document.getElementById('cm_data_table_body').innerHTML = rowpdhtml;

document.getElementById('taxableVal').innerHTML = (parseFloat(round(taxableTotal,2))).toFixed(2);
document.getElementById('igstVal').innerHTML = (parseFloat(round(igstTotal,2))).toFixed(2);
document.getElementById('cgstVal').innerHTML = (parseFloat(round(cgstTotal,2))).toFixed(2);
document.getElementById('sgstVal').innerHTML = (parseFloat(round(sgstTotal,2))).toFixed(2);
document.getElementById("tinvamt").innerHTML = invamt1.toFixed(2);
document.getElementById("tinvamt1").innerHTML = invamt1.toFixed(2);

document.getElementById('ctaxableVal').innerHTML = (parseFloat(round(taxableTotal,2))).toFixed(2);
document.getElementById('cigstVal').innerHTML = (parseFloat(round(igstTotal,2))).toFixed(2);
document.getElementById('ccgstVal').innerHTML = (parseFloat(round(cgstTotal,2))).toFixed(2);
document.getElementById('csgstVal').innerHTML = (parseFloat(round(sgstTotal,2))).toFixed(2);
document.getElementById("ctinvamt").innerHTML = invamt1.toFixed(2);
document.getElementById("ctinvamt1").innerHTML = invamt1.toFixed(2);


function fetchRefillUnitPrice(pc_data, pcid) {
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid)
			return pc_data[i].base_price;
		
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

function fetchGSTPercent(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		for(var p=0; p< pcid.length;p++)
			{
		if(pc_data[i].prod_code == pcid[p].prod_code)
			return pc_data[i].gstp;
	}
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

