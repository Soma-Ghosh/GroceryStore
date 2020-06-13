var baseURL;

window.onload = function()
{
	baseURL=window.location.href;
	loadProducts();
}

function openLogin()
{
	document.getElementById("signup_div").style.display= "none";
	document.getElementById("login_div").style.display= "block";
	document.getElementById("admin_login").style.display= "none";
}

function openadminLogin()
{
	document.getElementById("signup_div").style.display= "none";
	document.getElementById("login_div").style.display= "none";
	document.getElementById("admin_login").style.display= "block";
}

function openSignup()
{
	document.getElementById("login_div").style.display= "none";
	document.getElementById("signup_div").style.display= "block";
	document.getElementById("admin_login").style.display= "none";
}

function  addToCart()
{
	showMessage("You have to login first")
	openLogin();
	
}

function  addToWishlist()
{
	showMessage("You have to login first")
	openLogin();
	
}


function login()
{
	var username = document.getElementById("username").value.substring(0, document.getElementById('username').value.lastIndexOf("@"));
	var pass = document.getElementById("password").value;
	var xhttp=new XMLHttpRequest();
	var res;
	var flag;
	xhttp.open("GET",baseURL+"home/users/"+username,true);
	xhttp.onreadystatechange = function () 
	{
		if (xhttp.readyState == 4 && xhttp.status == 200)
		{
			res=JSON.parse(xhttp.responseText);
			if(pass==res.password)
			{
				window.open("Dashboard.html?"+res.username,"_self");
				return true;
			}
			else
			{
				document.getElementById("login_msg").innerHTML="Incorrect Credentials";
				return false;
			}
		}
		
	};
	xhttp.send();
	return false;
}

function signup()
{
	var n=document.getElementById("n").value;
	var e=document.getElementById("e").value;
	var p=document.getElementById("p").value;
	var pas=document.getElementById("pas").value;
	var cpas=document.getElementById("cpas").value;
	var loc=document.getElementById("loc").value;
	var ci=document.getElementById("city").value;
	var st=document.getElementById("st").value;
	var pin=document.getElementById("pin").value;
	var uname = e.substring(0, e.lastIndexOf("@"));
	var xhttp=new XMLHttpRequest();
	var res;
	var user= { customerName: n, username : uname, password : pas, contact : p, email : e,
			 	address :  { location : loc, city : ci, state : st, pincode : pin } };
	xhttp.open("POST",baseURL+"home/users",true);
	xhttp.onreadystatechange = function () 
	{
		if (xhttp.readyState == 4 && xhttp.status == 200)
		{
			window.open("Dashboard.html?"+uname,"_self");
			return true;
		}
	};
	xhttp.setRequestHeader('Content-Type', 'application/json');
	xhttp.send(JSON.stringify(user));
	return false;
}

function adminlogin()
{
	var username = document.getElementById("uname").value.substring(0, document.getElementById('uname').value.lastIndexOf("@"));
	var pass = document.getElementById("pass").value;
	var xhttp=new XMLHttpRequest();
	var res;
	var flag;
	xhttp.open("GET",baseURL+"home/shops/"+username,true);
	xhttp.onreadystatechange = function () 
	{
		if (xhttp.readyState == 4 && xhttp.status == 200)
		{
			res=JSON.parse(xhttp.responseText);
			if(pass==res.password)
			{
				window.open("admin_index.html?"+res.username,"_self");
				return true;
			}
			else
			{
				document.getElementById("admin_msg").innerHTML="User already exists";
				return false;
			}
		}
	};
	xhttp.send();
	return false;
}


