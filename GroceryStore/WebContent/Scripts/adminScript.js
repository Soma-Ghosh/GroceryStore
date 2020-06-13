var li;
var current;
var baseURL;
window.onload = function()
{
	current=window.location.href;
	baseURL=current.substring(0,current.lastIndexOf("/")+1);
	var uname=current.substring(current.lastIndexOf("?")+1,current.length);
	li=baseURL+"home/shops/"+uname;
	////console.log(li);
	loadProducts();
}


function getOrders()
{
	var res;
	var xhttp=new XMLHttpRequest();
    xhttp.open("GET",li+"/orders",true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
         {
        	res=JSON.parse(this.responseText);
        	var div;
        	if(res.length==0)
        	{
        			div+="<h2>Nothing to see here</h2>";
        			document.getElementById("products").innerHTML=div;
        			document.getElementById("products").childNodes[0].remove();
        	}
        	else
        	{
        		for(i=0;i<res.length;i++)
            	{
            		var d=new Date(res[i].orderedOn);
            		div+="<div class='long' id='"+res[i].orderId+"'>" +
            			 "<p>"+d.getDate()+"-"+d.getMonth()+"-"+d.getFullYear()+" </p>" +
            			 "<p>"+res[i].orderedBy.customerName+" </p> <div>"+
            			 "<p>"+res[i].status+" </p> <div>";
            			 for(j=0;j<res[i].orderedItems.length;j++)
            			 {
            				 div+="<p>"+res[i].orderedItems[j].product.productName+" * "+res[i].orderedItems[j].quantity+"</p>";
            			 }
            			 div+="<p> Address : "+res[i].orderedBy.address.location+", "+res[i].orderedBy.address.city+
            			 ", "+res[i].orderedBy.address.state+", "+res[i].orderedBy.address.pincode+" </p>"+
            				 "</div></div>";
            	}
            	document.getElementById("products").innerHTML=div;
            	document.getElementById("products").childNodes[0].remove();
        	}
        	
         }
    };
    xhttp.send();
}

function getCustomers()
{
	var res;
	var xhttp=new XMLHttpRequest();
    xhttp.open("GET",li+"/customers",true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
         {
        	res=JSON.parse(this.responseText);
        	var div;
        	if(res.length==0)
        	{
        		div+="<h2>Nothing to see here</h2>";
    			document.getElementById("products").innerHTML=div;
    			document.getElementById("products").childNodes[0].remove();
        	}
        	else
        	{
        		for(i=0;i<res.length;i++)
        		{

    	        	div+="<div class='profile' align='left'>" +
    				 "<p> Name : "+res[i].customerName+"</p>" +
    				 "<p> Email : "+res[i].email+"</p>"+
    				 "<p> Phone Number : "+res[i].contact+"</p>"+
    				 "<p> Address : "+res[i].address.location+", "+res[i].address.city+", "
    				 +res[i].address.state+", "+res[i].address.pincode+" </p>"+
    				 "</div>";
            	}
            	document.getElementById("products").innerHTML=div;
            	document.getElementById("products").childNodes[0].remove();
        	}
        	
         }
    };
    xhttp.send();
}

function removeProduct()
{
	var prod=event.target.parentNode.getElementsByTagName("INPUT")[0].value;
	var xhttp=new XMLHttpRequest();
    xhttp.open("DELETE",li+"/products",true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
         {
        	
        	
         }
    };
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(prod);
    close_div();
    loadProducts();
}

function editProduct()
{
	var prod=JSON.parse(event.target.parentNode.getElementsByTagName("INPUT")[0].value);
	var div="<div class='prodinfocus' align='center'>" +
	 	"<img  id='im' height=120 width=120 src='"+prod.image+"' alt='image'/><br>" +
	 	"<table><tr><td><span>Name </span></td><td><input id='n'type='text' value='"+prod.productName+"'></td></tr>" +
	 	"<tr><td><span>Price </span></td><td><input type='number' id='p' value='"+prod.price+"'></td></tr>" +
	 	"<tr><td><span>Quantity </span></td><td><input type='number' id='stock' value='"+prod.quantityInStock+"'></td></tr>" +
	 	"<tr><td><span>Description </span></td><td><input type='textbox' id='d' value='"+prod.description+"'></td></tr>"+
	 	"<input type='hidden' id='i' value='"+prod.productId+"'><br>" +
	 	"<tr><td><span>Category </span></td><td><input type='text' id='c' value='"+prod.category+"'></td></tr>"+
	 	"<tr><td><button onclick='close_div()'>Back</button></td><td> " +
	 	"<button onclick='edit()'>Edit</button></td></tr></table></div>";
	document.getElementById("productinfocus").innerHTML=div;
	document.getElementById("productinfocus").style.display="block";
	//document.getElementById("productinfocus").childNodes[0].remove();
}
	
	
function edit()
{
	var n=document.getElementById("n").value;
	var p=document.getElementById("p").value;
	var i=document.getElementById("i").value;
	var stock=document.getElementById("stock").value;
	var d=document.getElementById("d").value;
	var c=document.getElementById("c").value;
	var im=document.getElementById("im").getAttribute('src');
	
	var prod= { productName : n, price : p, productId : i,description : d,category : c,image : im ,quantityInStock : stock};
	
	//console.log(JSON.stringify(prod));
	var xhttp=new XMLHttpRequest();
    xhttp.open("PUT",li+"/products",true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
         {
        	event.target.parentNode.style.display="none";
         }
    };
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(prod));
    close_div();
    loadProducts();
}

function addProduct()
{
	var div="<div class='prodinfocus' align='center'><table>" +
	"<tr><td><span>Image name in products folder with extension</span></td><td><input id='newim' type='text' value=''></td></tr>"+
 	"<tr><td><span>Name </span></td><td><input id='newn'type='text' value=''></td></tr>" +
 	"<tr><td><span>Quantity in Stock </span></td><td><input id='newstock'type='number' value=''></td></tr>" +
 	"<tr><td><span>Price </span></td><td><input type='number' id='newp' value=''></td></tr>" +
 	"<tr><td><span>Description </span></td><td><input type='textbox' id='newd' value=''></td></tr>"+
 	"<tr><td><span>Category </span></td><td><input type='text' id='newc' value=''></td></tr></table>"+
 	"<button onclick='close_div()'>Back</button>  " +
 	"<button onclick='add()'>Add</button></div>";
	document.getElementById("productinfocus").innerHTML=div;
	document.getElementById("productinfocus").style.display="block";
}

function add()
{
	var newn=document.getElementById("newn").value.trim();
	var newp=document.getElementById("newp").value;
	var newstock=document.getElementById("newstock").value;
	var newd=document.getElementById("newd").value;
	var newc=document.getElementById("newc").value.toUpperCase();
	var newim=document.getElementById("newim").value.trim();
	
	var prod= { productName : newn, price : newp,description : newd,category : newc,image : "products/"+newim ,qantityInStock : newstock };
	
	//console.log(JSON.stringify(prod));
	var xhttp=new XMLHttpRequest();
    xhttp.open("POST",li+"/products",true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
         {
        	
         }
    };
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(prod));
    close_div();
    loadProducts();	
}



