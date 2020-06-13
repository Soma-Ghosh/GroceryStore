var li;
var current;
var baseURL;
window.onload = function()
{
	current=window.location.href;
	baseURL=current.substring(0,current.lastIndexOf("/")+1);
	var uname=current.substring(current.lastIndexOf("?")+1,current.length);
	li=baseURL+"home/users/"+uname;
	//console.log(li);
	loadProducts();
	
}


function addToWishlist()
{
	//console.log(event.target.parentNode.getElementsByTagName("INPUT")[0].value);
	var prod=event.target.parentNode.getElementsByTagName("INPUT")[0].value;
	var xhttp=new XMLHttpRequest();
    xhttp.open("POST",li+"/wishlists",true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
         {
        	
         }
    };
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(prod);
    showMessage("Item added to wishlists");
    getWishlist();
}

function addToCart(from)
{
	//console.log(event.target.parentNode.getElementsByTagName("INPUT")[0].value);
	var prod=event.target.parentNode.getElementsByTagName("INPUT")[0].value;
	var qty=event.target.parentNode.getElementsByClassName("qty")[0].value;
	
	if(qty=="Quantity")
		{
			showMessage("Please select quantity");
		}
	else
		{
		
		
	var map={ product: JSON.parse(prod) ,quantity : parseInt(qty,10)};
	//console.log(map);		
	
	var xhttp=new XMLHttpRequest();
    xhttp.open("POST",li+"/carts",true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
        {
        	
        }
    };
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(map));
    showMessage("Item added to cart");
    if(from=="prodinfocus")
	{
    	close_div();
	}
    
    getCart();
		}
}

function getWishlist()
{
	var div;
	var res;
	var xhttp=new XMLHttpRequest();
    xhttp.open("GET",li+"/wishlists",true);
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
            		div+="<div class='long'>" +
            			 "<img  height=100 width=100 src='"+baseURL+res[i].image+"' />"+
            			 "<p>"+res[i].productName+"</p>" +
            			 "<p> Rs. "+res[i].price+"</p>"+
            			 "<p>"+res[i].category+"</p>"+
            			 "<input type='hidden' value='"+JSON.stringify(res[i])+"'>"+
            			 "<select class='qty'  value='Quantity'><option>Quantity</option><option>1</option><option>2</option><option>3</option></select>"+
            			 "<button onclick='removeFromWishlist()'> Remove from Wishlist </button>"+
            			 "<button onclick='addToCart()'> Add to Cart </button>"+
            			 "</div>";
            	}
            	document.getElementById("products").innerHTML=div;
            	document.getElementById("products").childNodes[0].remove();
        	}
        	

         }
    };
    xhttp.send();
}

function getCart()
{
	var res;
	var xhttp=new XMLHttpRequest();
    xhttp.open("GET",li+"/carts",true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
         {
        	res=JSON.parse(this.responseText);
        	var inp="<input id='c' type='hidden' value='"+JSON.stringify(res)+"'/>";
        	var div;
        	var totalPrice=0;
        	if(res.length==0)
			{
        		div+="<h2>Nothing to see here</h2>";
    			document.getElementById("products").innerHTML=div;
    			document.getElementById("products").childNodes[0].remove();			}
        	else
        	{
        		for(i=0;i<res.length;i++)
            	{
            		div+="<div class='long'>" +
            			 "<input type='hidden' value='"+JSON.stringify(res[i].product)+"'> "+
            			 "<img height=100 width=100 src='"+baseURL+res[i].product.image+"' />"+
            			 "<p>"+res[i].product.productName+"</p>" +
            			 "<p>Rs. "+res[i].product.price+"</p>"+
            			 "<p>"+res[i].product.category+"</p>"+
            			 "<p>"+res[i].quantity+"</p>"+
            			 "<button onclick='removeFromCart()'> Remove from Cart </button>"+
            			 "</div>";
            		totalPrice+=res[i].product.price*res[i].quantity;
            	}
        		
            	document.getElementById("products").innerHTML=inp+div+
            	"<button id='ordernow' onclick='orderNow()' value='ORDER NOW' >Rs. "+totalPrice+" ORDER NOW</button>";
            	document.getElementById("products").childNodes[1].remove();
            	
            		document.getElementById("ordernow").style.display="block";
        	}
        	
        	
         }
    };
    xhttp.send();
}

function getOrder()
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
            			 "<table><tr><th>DATE</th><th>STATUS</th><th>ITEMS & QUANTITY</th></tr>"+
            		"<tr><td>"+d.getDate()+"-"+d.getMonth()+"-"+d.getFullYear()+" </td>" +
            			 "<td>"+res[i].status+" </td> <td><ul>";
            			 for(j=0;j<res[i].orderedItems.length;j++)
            			 {
            				 div+="<li>"+res[i].orderedItems[j].product.productName+" * "+res[i].orderedItems[j].quantity+"</li>";
            			 }
            			 div+="</ul></td></tr></table></div>";
            	}
            	document.getElementById("products").innerHTML=div;
            	document.getElementById("products").childNodes[0].remove();
        	}
        	
         }
    };
    xhttp.send();
}



function setProfile()
{
	var res;
	var xhttp=new XMLHttpRequest();
    xhttp.open("GET",li,true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
         {
        	res=JSON.parse(this.responseText);
         }
    };
    xhttp.send();
    return res;
}

var account;

account=setProfile();

function getProfile()
{
	var res=account;
	var div;
	div+="<div class='profile' align='left'>" +
	 "<p> Name : "+res.customerName+"</p>" +
	 "<p> Email : "+res.email+"</p>"+
	 "<p> Phone Number : "+res.contact+"</p>"+
	 "<p> Address : "+res.address.location+", "+res.address.city+", "+res.address.state+", "+res.address.pincode+" </p>"+
	 "</div>";
	document.getElementById("products").innerHTML=div;
	document.getElementById("products").childNodes[0].remove();	
}


function removeFromCart()
{
	var prod=event.target.parentNode.getElementsByTagName("INPUT")[0].value;
	console.log(prod);
	var res;
	var xhttp=new XMLHttpRequest();
    xhttp.open("DELETE",li+"/carts",true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
         {
        	showMessage("Item removed from cart"); 
         }
    };
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(prod);
    getCart();
}

function removeFromWishlist()
{
	var prod=event.target.parentNode.getElementsByTagName("INPUT")[0].value;
	//console.log(prod);
	var res;
	var xhttp=new XMLHttpRequest();
    xhttp.open("DELETE",li+"/wishlists",true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
         {
        	showMessage("Item removed from wishlist"); 
         }
    };
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(prod);
    getWishlist();
}

function orderNow()
{
	window.open("payuform.jsp","_self");
	
/*	var prod=document.getElementById("c").value;
	//console.log(prod);
	var res;
	var xhttp=new XMLHttpRequest();
	xhttp.open("POST",li+"/orders",true);
	xhttp.onreadystatechange = function () 
	{	
	    if (xhttp.readyState == 4 && xhttp.status == 200)
	     {
	    	
	     }
	};
	xhttp.setRequestHeader('Content-Type', 'application/json');
	xhttp.send(prod);*/
    
}

