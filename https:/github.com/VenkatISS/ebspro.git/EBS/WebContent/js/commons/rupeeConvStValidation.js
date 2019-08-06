function toWords(junkVal) {
    junkVal=Math.floor(junkVal);
    var obStr=new String(junkVal);
    numReversed=obStr.split("");
    actnumber=numReversed.reverse();

    if(Number(junkVal) >=0){
        //do nothing
    }
    else{
        return ' WRONG NUMBER(VALUE LESS THAN ZERO) CANNOT BE CONVERTED';
    }
    if(Number(junkVal)==0){
//        document.getElementById('container').innerHTML=obStr+''+'Rupees Zero Only';
        return ' RUPEES ZERO ONLY';
    }
    if(actnumber.length>9){
        return ' THE VALUE IS TOO BIG TO CONVERT';
    }

    var iWords=[" ZERO", " ONE", " TWO", " THREE", " FOUR", " FIVE", " SIX", " SEVEN", " EIGHT", " NINE"];
    var ePlace=[' TEN', ' ELEVEN', ' TWELVE', ' THIRTEEN', ' FOURTEEN', ' FIFTEEN', ' SIXTEEN', ' SEVENTEEN', ' EIGHTEEN', ' NINETEEN'];
    var tensPlace=[' DUMMY', ' TEN', ' TWENTY', ' THIRTY', ' FORTY', ' FIFTY', ' SIXTY', ' SEVENTY', ' EIGHTY', ' NINETY' ];

    var iWordsLength=numReversed.length;
    var totalWords="";
    var inWords=new Array();
    var finalWord="";
    j=0;
    for(i=0; i<iWordsLength; i++){
        switch(i)
        {
        case 0:
            if(actnumber[i]==0 || actnumber[i+1]==1 ) {
                inWords[j]='';
            }
            else {
                inWords[j]=iWords[actnumber[i]];
            }
            inWords[j]=inWords[j]+' RUPEES ONLY ';
            break;
        case 1:
            tens_complication();
            break;
        case 2:
            if(actnumber[i]==0) {
                inWords[j]='';
            }
            else if(actnumber[i-1]!=0 && actnumber[i-2]!=0) {
                inWords[j]=iWords[actnumber[i]]+' HUNDRED AND ';
            }
            else {
                inWords[j]=iWords[actnumber[i]]+' HUNDRED ';
            }
            break;
        case 3:
            if(actnumber[i]==0 || actnumber[i+1]==1) {
                inWords[j]='';
            }
            else {
                inWords[j]=iWords[actnumber[i]];
            }
            if(actnumber[i+1] != 0 || actnumber[i] > 0){
                inWords[j]=inWords[j]+" THOUSAND";
            }
            break;
        case 4:
            tens_complication();
            break;
        case 5:
            if(actnumber[i]==0 || actnumber[i+1]==1) {
                inWords[j]='';
            }
            else {
                inWords[j]=iWords[actnumber[i]];
            }
            if(actnumber[i+1] != 0 || actnumber[i] > 0){
                inWords[j]=inWords[j]+" LAKH";
            }
            break;
        case 6:
            tens_complication();
            break;
        case 7:
            if(actnumber[i]==0 || actnumber[i+1]==1 ){
                inWords[j]='';
            }
            else {
                inWords[j]=iWords[actnumber[i]];
            }
            inWords[j]=inWords[j]+" CRORE";
            break;
        case 8:
            tens_complication();
            break;
        default:
            break;
        }
        j++;
    }

    function tens_complication() {
        if(actnumber[i]==0) {
            inWords[j]='';
        }
        else if(actnumber[i]==1) {
            inWords[j]=ePlace[actnumber[i-1]];
        }
        else {
            inWords[j]=tensPlace[actnumber[i]];
        }
    }
    inWords.reverse();
    for(i=0; i<inWords.length; i++) {
        finalWord+=inWords[i];
    }
    return finalWord;
}


function fetchState(stcode){
	var state;
	switch(parseInt(stcode)) {
	case 1: state="JAMMU & KASHMIR";
			  	break;	
	case 2: state="HIMACHAL PRADESH";
	  			break;
	case 3: state="PUNJAB";
	  			break;
	case 4: state="CHANDIGARH";
	  			break;
	case 5: state="UTTARAKHAND";
	  			break;
	case 6: state="HARYANA";
	  			break;
	case 7: state="DELHI";
	  			break;
	case 8: state="RAJASTHAN";
	  			break;
	case 9: state="UTTAR PRADESH";
	  			break;
	case 10: state="BIHAR";
	  			break;
	case 11: state="SIKKIM";
	  			break;
	case 12: state="ARUNACHAL PRADESH";
	  			break;
	case 13: state="NAGALAND";
	  			break;
	case 14: state="MANIPUR";
	  			break;
	case 15: state="MIZORAM";
	  			break;
	case 16: state="TRIPURA";
	  			break;
	case 17: state="MEGHALAYA";
	  			break;
	case 18: state="ASSAM";
	  			break;
	case 19: state="WEST BENGAL";
	  			break;
	case 20: state="JHARKHAND";
	  			break;
	case 21: state="ODISHA";
	  			break;
	case 22: state="CHHATTISGARH";
	  			break;
	case 23: state="MADHYA PRADESH";
	  			break;
	case 24: state="GUJARAT";
	  			break;
	case 25: state="DAMAN & DIU";
	  			break;
	case 26: state="DADRA & NAGAR HAVELI";
	  			break;
	case 27: state="MAHARASHTRA";
	  			break;
	case 29: state="KARNATAKA";
	  			break;
	case 30: state="GOA";
	  			break;
	case 31: state="LAKSHDWEEP";
	  			break;
	case 32: state="KERALA";
	  			break;
	case 33: state="TAMIL NADU";
	  			break;
	case 34: state="PONDICHERRY";
	  			break;
	case 35: state="ANDAMAN & NICOBAR ISLANDS";
	  			break;
	case 36: state="TELANGANA";
	  			break;
	case 37: state="ANDHRA PRADESH";
	  			break;
	case 97: state="OTHER TERRITORY";
				break;
	default: state="INVALID STATE";
				break;
	}
	return state;
}

