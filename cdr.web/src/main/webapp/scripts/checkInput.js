function checkInput(inputs, callbackFunction){
//	alert(input);
	var re= new RegExp("select|update|delete|exec|count|or |an |union |'|\"|=|;|>|<|%|--|#|&|/");
//	alert(re);
	var sp = "|";
//	alert(sp);
	var result = false;
	for(var i = 0; i < inputs.length; i++){
		result = re.test(inputs[i]);
//		alert("input" + i + ": " + inputs[i] + ", result: " + result);
		if(result){
			break;
		} else {
			var location = inputs[i].indexOf(sp);
//			alert(location);
			if(location >= 0){
				result = true;
				break;
			}
		}
	}
	
	
	if(result){
		alert("输入中含有非法字符或sql关键字");
	} else {
//		alert("提交登陆");
		callbackFunction();	
	}
}