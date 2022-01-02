 function myFunction() {
            var input, filter, ul, li, a, i;
            input = document.getElementById("mySearch");
            filter = input.value.toUpperCase();
            ul = document.getElementById("myMenu");
            li = ul.getElementsByTagName("li");
            for (i = 0; i < li.length; i++) {
                a = li[i].getElementsByTagName("a")[0];
                if (a.innerHTML.toUpperCase().startsWith(filter)) {
                    li[i].style.display = "";
                } else {
                li[i].style.display = "none";
                }
            }
  }
  function editTask(input) {
  		    var txt;
            var newTask = prompt("Please make the change",input);
            if (newTask != null && newTask != "") {
                    let url = 'http://localhost:9090/todos/' + input+'/'+newTask;
                    fetch(url, {
                        method: 'put'
                    }).then(response => {
                    	return response.json()
                    }).then(data => {
                    	makeSearch(1,data.todos);
                    });
            }
  }
  function addTask() {
            var input = document.getElementById("mySearch").value;
            if(input == "")
                     alert("Enter a value to add");
            else
            {
                	 let requestBody = { todoname : input };
                	 fetch('http://localhost:9090/todos/', {
                		method: 'post',
                		body: JSON.stringify(requestBody)
                     }).then(response => {
                        return response.json()
                	 }).then(data => {
                		makeSearch(1,data.todos);
                	 });
            }
  }
  function deleteTask(input) {
            fetch('http://localhost:9090/todos/'+input, {
                        method: 'delete'
            }).then(response => {
                        return response.json()
            }).then(data => {
                        makeSearch(1,data.todos);
            });
  }
  function makeSearch(value,array) {
            if(value==0){
                var buttonscript='<ul id="myMenu"><input type="text" id="mySearch" onkeyup="myFunction()" placeholder="Search..">';
                fetch('http://localhost:9090/todos/', {
            		method: 'get'
            	}).then(response => {
                	return response.json();
            	}).then(data => {
            	    var todoArray = data.todos;
            		for(let i=0;i<todoArray.length;i++){
            		    console.log(todoArray[i]);
                 		buttonscript=buttonscript+"<li><a href=\"#\">"+todoArray[i]+"</a>";
                        var deletemethod="\"deleteTask(\'"+todoArray[i]+"\')\"";
                        var editmethod="\"editTask(\'"+todoArray[i]+"\')\"";
                        buttonscript=buttonscript+'<div class="Delete"><button id="seperate" onclick='+deletemethod+'>Delete</button></div>';
                        buttonscript=buttonscript+'<div class="Edit"><button id="seperate" onclick='+editmethod+'>Edit</button></li><br></div>';
                    }
                    buttonscript=buttonscript+"</ul>";
                    buttonscript=buttonscript+"<button onclick=\"addTask()\">Add Task</button>";
                    document.getElementById("p1").innerHTML=buttonscript;
            	});
            }
            else
            {
                document.getElementById("p1").innerHTML="";
                var buttonscript='<ul id="myMenu"><input type="text" id="mySearch" onkeyup="myFunction()" placeholder="Search..">';
                console.log(array);
                for(let i=0;i<array.length;i++){
                      buttonscript=buttonscript+"<li><a href=\"#\">"+array[i]+"</a>";
                      var deletemethod="\"deleteTask(\'"+array[i]+"\')\"";
                      var editmethod="\"editTask(\'"+array[i]+"\')\"";
                      buttonscript=buttonscript+'<div class="Delete"><button id="seperate" onclick='+deletemethod+'>Delete</button></div>';
                      buttonscript=buttonscript+'<div class="Edit"><button id="seperate" onclick='+editmethod+'>Edit</button></li><br></div>';
                }
                buttonscript=buttonscript+"</ul>";
                buttonscript=buttonscript+"<button onclick=\"addTask()\">Add Task</button>";
                document.getElementById("p1").innerHTML=buttonscript;
            }
  }
  function UselessTab1() {
  		      document.getElementById("p1").innerHTML="Inside useless tab 1";
  }
  function UselessTab2() {
  		      document.getElementById("p1").innerHTML="Inside useless tab 2";
  }
