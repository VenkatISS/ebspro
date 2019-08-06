var pos = dgstin.substr(0, 2);
document.getElementById("dpos").innerHTML = pos;
document.getElementById("dstname").innerHTML = fetchState(pos);
document.getElementById("cdpos").innerHTML = pos;
document.getElementById("cdstname").innerHTML = fetchState(pos);

// Process page data
var rowpdhtml = "";
var totalsrow = "";
var dis = 0;
var taxableTotal = 0;
var cgstTotal = 0;
var sgstTotal = 0;
var totalsd = 0;
var invamt = 0;
var invamt1 = 0;
var tgsta = 0;
var totalVal = 0;
var taxable = 0;
for (var n = 0; n < page_data.length; n++) {
	var spd = fetchProductDetails(cat_cyl_types_data, page_data[n].prod_code);
	var hsncode = fetchProductHSNDetails(equipment_data, page_data[n].prod_code);

	var pidv = page_data[n].prod_code;
	var up = fetchRefillUnitPrice(refill_prices_data, pidv);
	invamt = parseInt(page_data[n].tv_amount);
	invamt1 = parseFloat(page_data[n].admin_charges)
			+ parseFloat(page_data[n].cgst_amount)
			+ parseFloat(page_data[n].sgst_amount)
			+ parseFloat(page_data[n].igst_amount);
	var invamt2 = round(invamt1, 2);
	/*
	 * var taxable =
	 * invamt-parseInt(page_data[n].cyl_deposit)-parseInt(page_data[n].reg_deposit)-parseInt(page_data[n].cgst_amount)-parseInt(page_data[n].sgst_amount)-parseInt(page_data[n].igst_amount);
	 */
	var units = eus[fetchUnitCode(equipment_data, page_data[n].prod_code)];
	taxable = page_data[n].admin_charges;
	tgsta = +(page_data[n].cgst_amount) + +(page_data[n].sgst_amount);
	totalVal = parseFloat(page_data[n].tv_amount);

	rowpdhtml = rowpdhtml + "<tr><td align='center'>" + (n + (+1))
			+ "</td><td align='center'>" + spd + "</td><td align='center'>"
			+ hsncode + "</td>" + "<td align='center'>"
			+ page_data[n].no_of_cyl + "</td><td align='center'>" + units
			+ "</td>" + "<td align='center'>" + (parseFloat(up)).toFixed(2)
			+ "</td>" + "<td align='center'>"
			+ (parseFloat(page_data[n].imp_amount)).toFixed(2) + "</td>"
			+ "<td align='center'>"
			+ (parseFloat(page_data[n].admin_charges)).toFixed(2) + "</td>"
			+ "<td align='center'>" + (round(taxable, 2)).toFixed(2) + "</td>"
			+ "<td align='center'>18%</td>" + "<td align='center'>"
			+ (round(tgsta, 2)).toFixed(2) + "</td>" + "<td align='center'>"
			+ (round(totalVal, 2)).toFixed(2) + "</td>" + "</tr>";

	taxableTotal = taxableTotal + taxable;
	cgstTotal = cgstTotal + parseFloat(page_data[n].cgst_amount);
	sgstTotal = sgstTotal + parseFloat(page_data[n].sgst_amount);

	var str = toWords(invamt);

	document.getElementById("invid").innerHTML = page_data[n].sr_no;
	document.getElementById("cinvid").innerHTML = page_data[n].sr_no;

	var ed = new Date(page_data[n].tv_date);
	document.getElementById("invdate").innerHTML = ed.getDate() + "-"
			+ months[ed.getMonth()] + "-" + ed.getFullYear();
	document.getElementById("cinvdate").innerHTML = ed.getDate() + "-"
			+ months[ed.getMonth()] + "-" + ed.getFullYear();

	document.getElementById("impa").innerHTML = (parseFloat(page_data[n].imp_amount)).toFixed(2);
	document.getElementById("cimpa").innerHTML = (parseFloat(page_data[n].imp_amount)).toFixed(2);

	document.getElementById("cnospan").innerHTML = page_data[n].customer_no;
	document.getElementById("ccnospan").innerHTML = page_data[n].customer_no;

	totalsd = totalsd
			+ (parseFloat(page_data[n].cyl_deposit) + parseFloat(page_data[n].reg_deposit));
}

document.getElementById('invamtwords').innerHTML = str;
document.getElementById('cinvamtwords').innerHTML = str;

document.getElementById("tsda").innerHTML = (round(totalsd, 2)).toFixed(2);
document.getElementById("ctsda").innerHTML = (round(totalsd, 2)).toFixed(2);

document.getElementById("tamt").innerHTML = (parseInt(invamt2)).toFixed(2);
document.getElementById("tamt1").innerHTML = (parseInt(invamt2)).toFixed(2);
document.getElementById("ctamt").innerHTML = (parseInt(invamt2)).toFixed(2);
document.getElementById("ctamt1").innerHTML = (parseInt(invamt2)).toFixed(2);

document.getElementById('m_data_table_body').innerHTML = rowpdhtml;
document.getElementById('cm_data_table_body').innerHTML = rowpdhtml;

document.getElementById('taxableVal').innerHTML = (round(taxableTotal, 2)).toFixed(2);
document.getElementById('igstVal').innerHTML = "0.00";
document.getElementById('cgstVal').innerHTML = (round(cgstTotal, 2)).toFixed(2);
document.getElementById('sgstVal').innerHTML = (round(sgstTotal, 2)).toFixed(2);
document.getElementById("tinvamt").innerHTML = (parseInt(invamt)).toFixed(2);

document.getElementById('ctaxableVal').innerHTML = (round(taxableTotal, 2)).toFixed(2);
document.getElementById('cigstVal').innerHTML = "0.00";
document.getElementById('ccgstVal').innerHTML = (round(cgstTotal, 2)).toFixed(2);
document.getElementById('csgstVal').innerHTML = (round(sgstTotal, 2)).toFixed(2);
document.getElementById("ctinvamt").innerHTML = (parseInt(invamt)).toFixed(2);

function fetchRefillUnitPrice(pc_data, pcid) {
	for (var i = 0; i < pc_data.length; i++) {
		if (pc_data[i].prod_code == pcid)
			return pc_data[i].base_price;
	}
	return 0;
}

function fetchProductDetails(types, pc) {

	for (var i = 0; i < types.length; i++) {
		if (types[i].id == pc) {
			if (types[i].cat_type == "1" || types[i].cat_type == "2"
					|| types[i].cat_type == "3") {
				return types[i].cat_name + "-" + types[i].cat_desc;
			} else {
				return types[i].cat_desc;
			}
		}
	}
}

function fetchProductHSNDetails(types, pc) {

	for (var i = 0; i < types.length; i++) {
		if (types[i].prod_code == pc) {
			return types[i].csteh_no;
		}
	}
}

function fetchGSTPercent(pc_data, pcid) {

	for (var i = 0; i < pc_data.length; i++) {
		for (var p = 0; p < pcid.length; p++) {
			if (pc_data[i].prod_code == pcid[p].prod_code)
				return pc_data[i].gstp;
		}
	}
	return 0;
}

function fetchCustomerDetails(cdata, cid, dr) {
	if (cid == 0)
		return "---";
	for (var i = 0; i < cdata.length; i++) {
		if (cdata[i].id == cid) {
			if (dr == 1) { // get name
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

	for (var i = 0; i < pc_data.length; i++) {
		if (pc_data[i].prod_code == pcid)
			return pc_data[i].units;
	}
	return 0;
}

window.close();