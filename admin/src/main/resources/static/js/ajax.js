var _neutralPath = "";
var request;
var _progressText = "正在运行, 请稍候...  ";
var X, Y, moving;
	
moving = false;

function move_window(e) {
	if(moving) {
		e.parentNode.parentNode.style.left = window.event.x - X - e.offsetLeft - 1;
		e.parentNode.parentNode.style.top = window.event.y - Y - e.offsetTop - 1;
	}
}

function capture(e) {
	X = window.event.offsetX + e.offsetLeft;
	Y = window.event.offsetY + e.offsetTop;
	moving = true;
	e.setCapture();
}

function release(e) {
	e.releaseCapture();
	moving = false;
	//add by xiaoweichai 2010-03-26 防止层跑到界外
	if(e.parentNode.parentNode.style.left.substring(0,e.parentNode.parentNode.style.left.length-2)<0){
		e.parentNode.parentNode.style.left=1;
	}
	if(e.parentNode.parentNode.style.left.substring(0,e.parentNode.parentNode.style.left.length-2)>document.body.clientWidth-40){
		e.parentNode.parentNode.style.left=document.body.clientWidth-40;
	}		
	if(e.parentNode.parentNode.style.top.substring(0,e.parentNode.parentNode.style.top.length-2)<0){
		e.parentNode.parentNode.style.top=1;
	}
	if(e.parentNode.parentNode.style.top.substring(0,e.parentNode.parentNode.style.top.length-2)>document.body.clientHeight-21){
		e.parentNode.parentNode.style.top=document.body.clientHeight-21;
	}
}

/** 
 * 弹出ajax层
 * @param url 需弹出页面的url
 * @param title 弹出页面的标题 
 * */
function openAjaxDialog(url,title){
	var ajaxHtml=new Ajax.Updater(
		'',url,
		{method:'post',onSuccess:ajaxDialogSuccess, onFailure: ajaxDialogError,evalScripts:true}
	);
	ajaxDialogTitle = title;
}
	
function ajaxDialogSuccess(data){
	var htm=data.responseText;
	var el = document.createElement("<iframe id='popupdialogbackground' style='position: absolute; width: 100%; height: 100%; left: 0px; top: 0px; zindex: 1; background: #aaaaaa; filter: progid:DXImageTransform.Microsoft.Alpha(opacity=15)'></iframe>");
	document.body.appendChild(el);
	var ew = document.createElement("<div style='width: 100%; height: 100%; position: absolute; left: 0px; top: 0px;' id='popupdialog' style='zindex: 1'></div>");
	ew.innerHTML = "	<table class='window' border='0' cellspacing='0' cellpadding='0' id='popuptable_001'><tr onmouseup='release(this)' onmousemove='move_window(this)' onmousedown='capture(this)'><td class='cross-left-top'/><td class='cross-middle-top'><B>" + ajaxDialogTitle + "</B></td><td class='cross-right-top'/></tr><tr><td class='cross-left-middle'/><td bgcolor='#A6C8EE'>" + htm + "</td><td class='cross-left-middle'/d></tr><tr><td class='cross-left-bottom'/><td class='cross-middle-bottom'/><td class='cross-right-bottom'/></tr></table>";
	document.body.appendChild(ew);
	setstylepopwindow();
}
//页面遮罩效果
function waitingBlockUI(){
	var imgurl = "<img src='skin/default/imgs/loading.gif' width='372' height='20' align='absmiddle' vspace='10' />";
	var el = document.createElement("<iframe id='waitingBlockbackgroundID' style='position: absolute; width: 100%; height: 100%; left: 0px; top: 0px; zindex: 1; background: #aaaaaa; filter: progid:DXImageTransform.Microsoft.Alpha(opacity=15)'></iframe>");
	document.body.appendChild(el); 
	var ew = document.createElement("<div id='waitingBlockID' style='width: 100%; height: 100%; position: absolute; left: 0px; top: 0px;' style='zindex: 1'></div>");
	ew.innerHTML = "<table class='window' border='0' cellspacing='0' cellpadding='0' id='popuptable_001'>" +
	"				<tr onmouseup='release(this)' onmousemove='move_window(this)' onmousedown='capture(this)'>" +
	"				<td class='cross-left-top'/>" +
	"				<td class='cross-middle-top'><B>" + _progressText + "</B></td>" +
	"				<td class='cross-right-top'/>" +
	"				</tr>" +
	"				<tr>" +
	"				<td class='cross-left-middle'/>" +
// 	"				<td bgcolor='#A6C8EE'   >" + imgurl + "</td>" +
 	"				<td bgcolor='#A6C8EE' width='372' height='20' class='loadingimg'>&nbsp;</td>" +
	"				<td class='cross-left-middle'/>" +
	"				</tr>" +
	"				<tr>" +
	"				<td class='cross-left-bottom'/>" +
	"				<td class='cross-middle-bottom'/>" +
	"				<td class='cross-right-bottom'/>" +
	"				</tr>" +
	"				</table>";
	document.body.appendChild(ew);
	setstylepopwindow();
}
//关闭遮罩
function closeWaitingBlockUI() {
	e = document.getElementById('waitingBlockID');
	e.innerHTML = "";
	document.body.removeChild(e);
	e = document.getElementById('waitingBlockbackgroundID');
	document.body.removeChild(e);
	afterclose();
}
function setstylepopwindow(){

}
function ajaxDialogError(request){
	alert("ajax error");
}

function closedialog() {
	try
	{
		closedialog001('popupdialog0','popupdialogbackground0');
	}
	catch(e1)
	{
		try
		{
			closedialog001('popupdialog','popupdialogbackground');
		}
		catch(e2)
		{
			window.close();
		}
	}
	
}

function closefinbank() {
	try
	{
		closedialog001('popupdialogfinbank','popupdialogbackgroundfinbank');
	}
	catch(e1)
	{
		try
		{
			closedialog001('popupdialog','popupdialogbackground');
		}
		catch(e2)
		{
			window.close();
		}
	}
	
}
function closedialog001(dialogid,backgroundid) {
	 e = document.getElementById(dialogid);
	 e.innerHTML = "";
	document.body.removeChild(e);
	 e = document.getElementById(backgroundid);
	document.body.removeChild(e);
	afterclose();
}
function afterclose(){

}
function getQueryString(formName){
    var queryString="";
    var frm = document.getElementById(formName);
    var numberElements = frm.elements.length;
    for(var i = 0; i < numberElements; i++) {
        if(i < numberElements-1) {
            queryString += frm.elements[i].name+"="+
                           encodeURIComponent(frm.elements[i].value)+"&";
        } else {
            queryString += frm.elements[i].name+"="+
                           encodeURIComponent(frm.elements[i].value);
        }

    }
    
    return queryString;
}		

/*******************************/
/***** Request (Req) Object *****/
/*******************************/
function Req() {}
Req.makeRequest = function(p_url, p_queryString, p_busyReq, p_successCallBack, p_errorCallBack, p_pass) {
	//p_url: the web service url
	//p_queryString: can use fucntion setQueryString to get the string
	//p_busyReq: is a request for this object currently in progress?
	//p_progId: element id where progress HTML should be shown
	//p_successCallBack: callback function for successful response
	//p_errorCallBack: callback function for erroneous response
	//p_pass: string of params to pass to callback functions
	if (p_busyReq) return;
	var req = Req.getRequest();
	if (req != null) {
		p_busyReq = true;
		Req.showProgress();
		req.onreadystatechange = function() {
			if (req.readyState == 4) {
				p_busyReq = false;
				window.clearTimeout(toId);
				if (Req.p_progId != null) {
					document.body.removeChild(Req.p_progId);
					Req.p_progId = null;
				}
				if (req.status == 200) {
					p_successCallBack(req, p_pass);
				} else {
					p_errorCallBack(req, p_pass);
				}
			}
		};
		req.open("POST", p_url, true);
    	req.setRequestHeader("Content-Type",
            "application/x-www-form-urlencoded; charset=UTF-8");
            
		req.send(p_queryString);
		var toId = window.setTimeout( function() {if (p_busyReq) req.abort();}, Req.timeout );
	}
};
Req.getRequest = function() {
	var xmlHttp;
	try { xmlHttp = new ActiveXObject("MSXML2.XMLHTTP"); return xmlHttp; } catch (e) {}
	try { xmlHttp = new ActiveXObject("Microsoft.XMLHTTP"); return xmlHttp; } catch (e) {}
	try { xmlHttp = new XMLHttpRequest(); return xmlHttp; } catch(e) {}
	return null;
};
Req.showProgress = function() {
	if(Req.p_progId == null) {
		Req.p_progId = document.createElement('<DIV>');
		Req.p_progId.style.position ="absolute";
		Req.p_progId.style.left = "0px";
		Req.p_progId.style.top = "0px";
		Req.p_progId.style.width = "100%";
		Req.p_progId.style.height = "100%";
		Req.p_progId.style.textAlign = "center";
		document.body.appendChild(Req.p_progId);
	}
	Req.p_progId.innerHTML = Req.getProgressHtml();
	Req.p_progId.style.zIndex = 0;
};
Req.getProgressHtml = function() {
	return "<table width=100% height=100%><tr><td align=center><table border=0 class=loading><tr><td><img src='"+_neutralPath+"image/loading.gif' align='middle' alt='" + _progressText + "'/></td><td style='font-family: 宋体; font-size: 9pt'>&nbsp;" + _progressText + "&nbsp;&nbsp;&nbsp;</td></tr></table></td></tr></table>";
};
Req.getErrorHtml = function(p_req) {
	//TODO: implement accepted way to handle request error
	return "<p>" + "(" + p_req.status + ") " + p_req.statusText + "</p>"
};
Req.p_progId= null;
Req.timeout = 5000;//5 seconds
/***** End Request (Req) Object *****/


/*******************************/
/***** httpPageEx Object *****/
/*******************************/
function HttpPageEx() {}
HttpPageEx.forwardForm = function(url, formName, caption) {
	var queryString = getQueryString(formName);
	Req.makeRequest(url, queryString, HttpPageEx.busyReq, HttpPageEx.successCallback, HttpPageEx.errorCallback, caption);
};
HttpPageEx.forwardURL = function(url, queryString, caption) {
	Req.makeRequest(url, queryString, HttpPageEx.busyReq, HttpPageEx.successCallback, HttpPageEx.errorCallback, caption);
};
HttpPageEx.successCallback = function(p_req, caption) {
	var el = document.createElement("<div id='popupdialogbackground' style='position: absolute; width: 100%; height: 100%; left: 0px; top: 0px; zindex: 1; background: #aaaaaa; filter: progid:DXImageTransform.Microsoft.Alpha(opacity=15)'></div>");
	document.body.appendChild(el);
	var ew = document.createElement("<div id='popupdialog' style='zindex: 1'></div>");
	ew.innerHTML = "	<table class=window border='0' cellspacing='0' cellpadding='0'><tr onmouseup='release(this)' onmousemove='move_window(this)' onmousedown='capture(this)'><td class='cross-left-top'/><td class='cross-middle-top'><B>" + caption + "</B></td><td class='cross-right-top'/></tr><tr><td class='cross-left-middle'/><td>" + p_req.responseText + "</td><td class='cross-left-middle'/d></tr><tr><td class='cross-left-bottom'/><td class='cross-middle-bottom'/><td class='cross-right-bottom'/></tr></table>";
	document.body.appendChild(ew);
	//ew.style.zIndex = 0;
};
HttpPageEx.errorCallback = function(p_req, p_pass) {
};
HttpPageEx.busyReq = false;

/*******************************/
/***** httpPage Object *****/
/*******************************/
function HttpPage() {}
HttpPage.forwardForm = function(url, formName, innerElement) {
	var queryString = getQueryString(formName);
	Req.makeRequest(url, queryString, HttpPage.busyReq, HttpPage.successCallback, HttpPage.errorCallback, innerElement);
};
HttpPage.forwardURL = function(url, queryString, innerElement) {
	Req.makeRequest(url, queryString, HttpPage.busyReq, HttpPage.successCallback, HttpPage.errorCallback, innerElement);
};
HttpPage.successCallback = function(p_req,p_pass) {
};
HttpPage.errorCallback = function(p_req, p_pass) {
};
HttpPage.busyReq = false;
/***** End HttpPage Object *****/

/*******************************/
/***** ExcelPage Object *****/
/*******************************/
function ExcelPage() {}
ExcelPage.forwardForm = function(url, formName, innerElement) {
	var queryString = getQueryString(formName);
	Req.makeRequest(url, queryString, ExcelPage.busyReq, "pgMain", ExcelPage.successCallback, ExcelPage.errorCallback, innerElement);
};
ExcelPage.forwardURL = function(url, queryString, innerElement) {
	Req.makeRequest(url, queryString, ExcelPage.busyReq, "pgMain", ExcelPage.successCallback, ExcelPage.errorCallback, innerElement);
};
ExcelPage.successCallback = function(p_req,p_pass) {
	innerElement = document.getElementById(p_pass);
	if(innerElement)
		innerElement.innerHTML = p_req.responseText;
};
ExcelPage.errorCallback = function(p_req, p_pass) {
};
ExcelPage.busyReq = false;
/***** End ExcelPage Object *****/

/*******************************/
/***** FormulaPage Object *****/
/*******************************/
function FormulaPage() {}
FormulaPage.forwardURL = function(url, queryString, innerElement) {
	Req.makeRequest(url, queryString, FormulaPage.busyReq, FormulaPage.successCallback, FormulaPage.errorCallback, innerElement);
};
FormulaPage.successCallback = function(p_req,p_pass) {
	var params = p_pass.split(",");
	var parentControl= document.getElementById(params[0]);
	var attachControl= document.getElementById(params[1]);
	var modalControl;
	if(parentControl)
		if(attachControl) {
			if(!(document.getElementById("modaldiv"))) {
				modalControl = document.createElement("<div id='modaldiv' onclick='dropup(\"" + params[1] + "\")' style='position:absolute; zindex: 1; background: #aaaaaa; left: 0px; top: 0px width: 100%; height: 100%; filter: progid:DXImageTransform.Microsoft.Alpha(opacity=15)'></div>");
				document.body.insertBefore(modalControl);
			} else {
				modalControl = document.getElementById("modaldiv");
                modalControl.style.visibility = "visible";
            }
			modalControl.style.left = "0px";
			modalControl.style.top = "0px";
			modalControl.style.width = document.body.clientWidth + "px";
			modalControl.style.height = document.body.clientHeight + "px";

			parentControl.style.top = "";
			parentControl.style.left = "";
			parentControl.style.left = (parentControl.offsetLeft + 7) + "px";
			parentControl.style.top = (parentControl.offsetTop - 3) + "px";
			parentControl.style.width = (attachControl.offsetWidth + 25) + "px";
			parentControl.innerHTML = p_req.responseText;
			parentControl.style.visibility = "visible";
			parentControl.style.zIndex = 1;
		}
};
FormulaPage.busyReq = false;
/***** End FormulaPage Object *****/

function hidelement(sender, value) {
	document.getElementById("modaldiv").style.visibility = "hidden";
	document.getElementById("div" + sender).style.visibility = "hidden";
	document.getElementById(sender).value = value;
	document.getElementById(sender).focus();
}
function dropup(sender) {
	document.getElementById("modaldiv").style.visibility = "hidden";
	document.getElementById("div" + sender).style.visibility = "hidden";
}