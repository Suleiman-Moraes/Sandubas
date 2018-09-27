function maiuscula(obj) {
	obj.value = obj.value.toUpperCase();
}

function somenteNumero(e) {
	var tecla = (window.event) ? event.keyCode : e.which;
	if ((tecla > 47 && tecla < 58))
		return true;
	else {
		if (tecla == 8 || tecla == 0)
			return true;
		else
			return false;
	}
}
function somenteLetra(e){
	var tecla = (window.event) ? event.keyCode : e.which;
	if (tecla >= 33 && tecla <= 64 || tecla >= 91 && tecla <= 93 || tecla >= 123 && tecla <= 159 || tecla >= 162 && tecla <= 191 ){ 
	    return false;
	}else{
	   return true;
	}
}

function naoAceitaEnter(e) {
	var tecla = (window.event) ? event.keyCode : e.which;
	if(tecla == 13){
		return false;
	} else {
		return true;
	}
}

//function naoAceitaEnter(e) {
//	var tecla = (window.event) ? event.keyCode : e.which;
//	if(tecla == 13){
//		return false;
//	} else {
//		return true;
//	}
//}

function onHourShowCallback(hour) {  
    if ((hour > 20) || (hour < 6)) {  
        return false; // not valid  
    }  
      
    return true; // valid  
}  
  
function onMinuteShowCallback(hour, minute) {  
    if ((hour == 20) && (minute >= 30)) {  
        return false; // not valid  
    }  
      
    if ((hour == 6) && (minute < 30)) {  
        return false; // not valid  
    }  
      
    return true;  // valid  
}  
  
// Use case 3  
  
function tpStartOnHourShowCallback(hour) {  
    if (!PrimeFaces.widgets['endTimeWidget']) {  
        return false;  
    }  
  
    var tpEndHour = parseInt(PF('endTimeWidget').getHours());  
  
    // Check if proposed hour is prior or equal to selected end time hour  
    if (parseInt(hour) <= tpEndHour) {  
        return true;  
    }  
  
    // if hour did not match, it can not be selected  
    return false;  
}  
  
function tpStartOnMinuteShowCallback(hour, minute) {  
    if (!PrimeFaces.widgets['endTimeWidget']) {  
        return false;  
    }  
  
    var tpEndHour = parseInt(PF('endTimeWidget').getHours());  
    var tpEndMinute = parseInt(PF('endTimeWidget').getMinutes());  
  
    // Check if proposed hour is prior to selected end time hour  
    if (parseInt(hour) < tpEndHour) {  
        return true;  
    }  
  
    // Check if proposed hour is equal to selected end time hour and minutes is prior  
    if ((parseInt(hour) == tpEndHour) && (parseInt(minute) < tpEndMinute)) {  
        return true;  
    }  
  
    // if minute did not match, it can not be selected  
    return false;  
}  
  
function tpEndOnHourShowCallback(hour) {  
    if (!PrimeFaces.widgets['startTimeWidget']) {  
        return false;  
    }  
  
    var tpStartHour = parseInt(PF('startTimeWidget').getHours());  
  
    // Check if proposed hour is after or equal to selected start time hour  
    if (parseInt(hour) >= tpStartHour) {  
        return true;  
    }  
  
    // if hour did not match, it can not be selected  
    return false;  
} 
  
function tpEndOnMinuteShowCallback(hour, minute) {  
    if (!PrimeFaces.widgets['startTimeWidget']) {  
        return false;  
    }  
  
    var tpStartHour = parseInt(PF('startTimeWidget').getHours());  
    var tpStartMinute = parseInt(PF('startTimeWidget').getMinutes());  
  
    // Check if proposed hour is after selected start time hour  
    if (parseInt(hour) > tpStartHour) {  
        return true;  
    }  
  
    // Check if proposed hour is equal to selected start time hour and minutes is after  
    if ((parseInt(hour) == tpStartHour) && (parseInt(minute) > tpStartMinute)) {  
        return true;  
    }  
  
    // if minute did not match, it can not be selected  
    return false;  
}  
