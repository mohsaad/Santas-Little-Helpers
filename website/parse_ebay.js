function readTextFile(file)
{
	var rawFile = new XMLHttpRequest();
	rawFile.open("GET", file, false);
	rawFile.onreadystatechange = function()
	 {
        if(rawFile.readyState === 4)
        {
            if(rawFile.status === 200 || rawFile.status == 0)
            {
                var allText = rawFile.responseText;
                alert(allText);
            }
        }
    }
    rawFile.send(null);
    return rawFile.responseText;
}

var text = readTextFile("ebay.txt");
var js = JSON.loads(text);

var data = js["findItemsByKeywordsResponse"];
var result = data["searchResult"];

for(var stuff in result)
{
	console.log(stuff);
	console.log(stuff["title"]);
	console.log(stuff["view_item_URL"]);
}


