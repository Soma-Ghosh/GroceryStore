
function showMessage(msg)
{
	document.getElementById("msg").style = "display:block";
    document.getElementById("msg").innerHTML =msg;
    setTimeout(function() {
      document.getElementById("msg").style = "display:none";
    }, 3000);
}


function loadProducts()
{	 
	var xhttp=new XMLHttpRequest();
    xhttp.open("GET",baseURL+"home/products",true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
         {
			res=this.responseText;
			var prod=JSON.parse(res);
			var i=0;
			var div;
			for(i=0;i<prod.length;i++)
			{
				div+="<div class='prod'>" +
					 "<img src='"+baseURL+prod[i].image+"' alt=''/>" +
					 "<p>"+prod[i].productName+"</p><p>"+prod[i].price+"</p>"+
					 "<button id="+prod[i].category+"/"+prod[i].productId+" onclick='openProduct()'>View Details</button>"+
					 "<input type='hidden' value='"+JSON.stringify(prod[i])+"'>"+
					 "<button onclick='addToCart()'>Add to Cart</button></div>";
			}
			document.getElementById("products").innerHTML=div;
			document.getElementById("products").childNodes[0].remove();
         }
    };
    xhttp.send();
}

document.getElementById("search").addEventListener("change", function()
		{
			var s=document.getElementById("search").value;
			var res;
			var xhttp=new XMLHttpRequest();
			xhttp.open("GET",baseURL+"home/search/"+s,true);
			xhttp.onreadystatechange = function () 
		    {	
		        if (xhttp.readyState == 4 && xhttp.status == 200)
		         {
					res=this.responseText;
					var prod=JSON.parse(res);
					var i=0;
					var div;
					for(i=0;i<prod.length;i++)
					{
						div+="<div class='prod'>" +
							 "<img src='"+baseURL+prod[i].image+"' alt=''/>" +
							 "<p>"+prod[i].productName+"</p><p>"+prod[i].price+"</p>"+
							 "<button id="+prod[i].category+"/"+prod[i].productId+" onclick='openProduct()'>View Details</button>"+
							 "<input type='hidden' value='"+JSON.stringify(prod[i])+"'>"+
							 "</div>";
					}
					document.getElementById("products").innerHTML=div;
					document.getElementById("products").childNodes[0].remove()
		         }
		    };
		    xhttp.send();
		});


function getByCategory(category)
{
	var xhttp=new XMLHttpRequest();
    xhttp.open("GET",baseURL+"home/products/"+category,true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
         {
			//console.log(category);
			res=this.responseText;
			var prod=JSON.parse(res);
			var i=0;
			var div;if(prod.length==0)
				{
					div+="<h2>Nothing to see here </h2>";
				}
			for(i=0;i<prod.length;i++)
			{
				div+="<div class='prod'>" +
						"<img src='"+baseURL+prod[i].image+"' alt=''/>" +
						"<p>"+prod[i].productName+"</p>" +
						"<p>"+prod[i].price+"</p>"+
						"<button id='"+prod[i].category+"/"+prod[i].productId+"' onclick='openProduct()'>View Details</button>"+
						"<input type='hidden' value='"+JSON.stringify(prod[i])+"'>"+
				        "<br><button onclick='addToCart()'>Add to Cart</button></div>";
			}
			
			document.getElementById("products").innerHTML=div;
			//console.log(document.getElementById("products"));
			document.getElementById("products").childNodes[0].remove();
         }
    };
    xhttp.send();
}

function openProduct()
{
	var xhttp=new XMLHttpRequest();
    xhttp.open("GET",baseURL+"home/products/"+ event.target.getAttribute('id'),true);
    xhttp.onreadystatechange = function () 
    {	
        if (xhttp.readyState == 4 && xhttp.status == 200)
         {
			res=this.responseText;
			//console.log(res);
			var prod=JSON.parse(res);
			var i=0;
			var div;
			div+="<div class='prodinfocus' align='center'>" +
				 "<img height=120 width=120 src='"+baseURL+prod.image+"' alt='image'/>" +
				 "<p>"+prod.productName+"</p><p>"+prod.price+"</p>" +
				 "<p>"+prod.description+"</p>"+
				 "<input type='hidden' value='"+JSON.stringify(prod)+"'>" +
				 "<button onclick='close_div()'>Back</button> " +
				 "<button onclick='addToWishlist()'>Add to Wishlist</button> "+
				 " <button onclick='addToCart()'>Add to Cart</button></div>";
			document.getElementById("productinfocus").innerHTML=div;
			document.getElementById("productinfocus").style.display="block";
			document.getElementById("productinfocus").childNodes[0].remove();
         }
    };
    xhttp.send();
}

function close_div()
{
	event.target.parentNode.parentNode.style.display= "none";
}