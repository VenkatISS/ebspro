document.getElementById("invid").innerHTML = page_data.sid;
document.getElementById("invrefid").innerHTML = page_data.ref_no;
var ed = new Date(page_data.note_date);
document.getElementById("invdate").innerHTML = ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear();


var pos = dgstin.substr(0, 2);
document.getElementById("dpos").innerHTML = pos;
document.getElementById("dstname").innerHTML = fetchState(pos);

for(var i=0; i<cvo_data.length;i++) {
	var custName = getCustomerName(cvo_data,page_data.cvo_id);
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
		return "HPCL";
	for(var z=0; z<data.length; z++){
		if(data[z].id==id) {
			return data[z].cvo_name;
		}
	}
}

var rowpdhtml = "";
var totalsrow = "";
var dis = 0;
var taxableTotal = 0;
var cgstTotal = 0;
var sgstTotal = 0;
var igstTotal = 0;    
var hsncode;var vpv;var tvpp;var spd;
var pidv = page_data.prod_code;var units;
if(pidv < 100) {
	spd = fetchProductDetails(prod_types, page_data.prod_code);
	if(pidv < 18){
		hsncode = fetchProductHSNDetails(equipment_data, page_data.prod_code);
		units = eus[fetchEQPUnitCode(equipment_data,page_data.prod_code)];
//		tvpp = fetchGSTPercent(equipment_data, pidv);
	}	
} else {
	spd = fetchARBProductDetails(arb_data, page_data.prod_code);
	hsncode = fetchARBHSNCode(arb_data, page_data.prod_code);
	units = eus[fetchARBUnitCode(arb_data,page_data.prod_code)];
//	tvpp = fetchARBGSTPercent(arb_data,page_data.prod_code);
}

	var ur = page_data.taxable_amount;
	var qty = 1;
	var taxable = (ur)*qty;
	var gstv = ((taxable * (page_data.gstp)) / 100);
	var totalVal = +(taxable)+ +(gstv);
	
	rowpdhtml = rowpdhtml + "<tr><td align='center'>1</td><td align='center'>"+spd+"</td><td align='center'>"+hsncode+"</td>"
		+"<td align='center'>"+qty+"</td><td align='center'>"+units+"</td>"
		+"<td align='center'>"+parseFloat(page_data.taxable_amount).toFixed(2)+"</td>"
		+"<td align='center'>0.00</td><td align='center'>"+parseFloat(taxable).toFixed(2)+"</td>"
		+"<td align='center'>"+page_data.gstp+"%"+"</td>"
		+"<td align='center'>"+(round(gstv,2)).toFixed(2)+"</td>"
		+"<td align='center'>"+(round(totalVal,2)).toFixed(2)+"</td>"
		+"</tr>";
	
    taxableTotal = taxableTotal + taxable;
    cgstTotal = cgstTotal + parseFloat(page_data.cgst_amount);
    sgstTotal = sgstTotal + parseFloat(page_data.sgst_amount);
    igstTotal = igstTotal + parseFloat(page_data.igst_amount);
    var num = parseInt(page_data.debit_amount);
    var str = toWords(num);
    document.getElementById('invamtwords').innerHTML = str;
    
    document.getElementById('m_data_table_body').innerHTML = rowpdhtml;
    document.getElementById('taxableVal').innerHTML = (round(taxableTotal,2)).toFixed(2);
    document.getElementById('igstVal').innerHTML = (round(igstTotal,2)).toFixed(2);
    document.getElementById('cgstVal').innerHTML = (round(cgstTotal,2)).toFixed(2);
    document.getElementById('sgstVal').innerHTML = (round(sgstTotal,2)).toFixed(2);
    document.getElementById("tinvamt").innerHTML = parseInt(page_data.debit_amount).toFixed(2);

function fetchGSTPercent(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid)
			return pc_data[i].gstp;
	}
	return 0;
}
function calculateVATPrice(ebp, spc) {
	var cv = 0;
	for (var z = 0; z < equipment_data.length; z++) {
		if (equipment_data[z].prod_code == spc) {
			var vatv = equipment_data[z].vatp;
			cv = ((ebp * vatv) / 100);
		}
	}
	return cv;
}

/*function inWords (num) {
	var a = ['','one ','two ','three ','four ', 'five ','six ','seven ','eight ','nine ','ten ','eleven ','twelve ','thirteen ','fourteen ','fifteen ','sixteen ','seventeen ','eighteen ','nineteen '];
	var b = ['', '', 'twenty','thirty','forty','fifty', 'sixty','seventy','eighty','ninety'];

    if ((num = num.toString()).length > 9) return 'overflow';
    n = ('000000000' + num).substr(-9).match(/^(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
    if (!n) return; var str = '';
    str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'crore ' : '';
    str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'lakh ' : '';
    str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'thousand ' : '';
    str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'hundred ' : '';
    str += (n[5] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'only ' : '';
    return str;
}

function toWords(s) {
	var th = ['','THOUSAND','MILLION', 'BILLION','TRILLION'];
	var dg = ['ZERO','ONE','TWO','THREE','FOUR', 'FIVE','SIX','SEVEN','EIGHT','NINE'];
	 var tn = ['TEN','ELEVEN','TWELVE','THIRTEEN', 'FOURTEEN','FIFTEEN','SIXTEEN', 'SEVENTEEN','EIGHTEEN','NINETEEN'];
	 var tw = ['TWENTY','THIRTY','FORTY','FIFTY', 'SIXTY','SEVENTY','EIGHTY','NINETY'];

    s = s.toString();
    s = s.replace(/[\, ]/g,'');
    if (s != parseFloat(s)) return 'not a number';
    var x = s.indexOf('.');
    if (x == -1)
        x = s.length;
    if (x > 15)
        return 'too big';
    var n = s.split(''); 
    var str = '';
    var sk = 0;
    for (var i=0;   i < x;  i++) {
        if ((x-i)%3==2) { 
            if (n[i] == '1') {
                str += tn[Number(n[i+1])] + ' ';
                i++;
                sk=1;
            } else if (n[i]!=0) {
                str += tw[n[i]-2] + ' ';
                sk=1;
            }
        } else if (n[i]!=0) { // 0235
            str += dg[n[i]] +' ';
            if ((x-i)%3==0) str += 'HUNDRED ';
            sk=1;
        }
        if ((x-i)%3==1) {
            if (sk)
                str += th[(x-i-1)/3] + ' ';
            sk=0;
        }
    }

    if (x != s.length) {
        var y = s.length;
        str += 'point ';
        for (var i=x+1; i<y; i++)
            str += dg[n[i]] +' ';
    }
    return str.replace(/\s+/g,' ');
}*/

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
function fetchARBHSNCode(pc_data, pcid) {
	
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].id == pcid)
			return pc_data[i].csteh_no;
	}
	return 0;
}
function fetchEQPUnitCode(pc_data, pcid) {
	
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

function fetchARBGSTPercent(pc_data, pcid) {
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].id == pcid)
			return pc_data[i].gstp;
	}
	return 0;
}

window.close();