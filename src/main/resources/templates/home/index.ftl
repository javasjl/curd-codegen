<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>代码生成工具</title>
	<style type='text/css'>
	*{
		margin: 0;
		padding: 0;
	}
	html,body{
		width: 100%;
		height: 100%;
	}
	h1{
		font-size: 20px;
		font-weight: 400;
		line-height: 1.5;
		/*color: #2d8cf0;*/
		color: #fff;
		box-sizing: border-box;
		padding: 5px;
		border-radius: 4px;
		background-color: #2d8cf0;
	}
	.main{
		width: 80%;
		height: 100%;
		box-sizing: border-box;
		padding: 10px;
		margin: 0 auto;
	}
	.block{
		margin-top: 10px;
		width: 100%;
		border-bottom: 1px solid #2d8cf0;
		box-sizing: border-box;
		border-radius: 4px;
		padding-bottom: 10px;
	}
	.sql-form{

	}
	.form .label{
		font-size: 14px;
		line-height: 34px;
		color: #666;
		display: inline-block;
		min-width: 150px;
	}
	.form .file-input{
		width: 100%;
		height: 100%;
		opacity: 0;
		position: absolute;
		left: 0;
		top: 0;
		z-index: 1;
	}
	.form .file-input:hover{
		cursor: pointer;
	}
	.form .file-input:hover +span{
		cursor: pointer;
		/*cursor: pointer;*/
		background-color: #2d8cf0;
		color: #fff;
		border-radius: 4px;
	}
	.input-container{
		display: inline-block;
		margin-left: 10px;
	}
	.input-container span{
		width: 100%;
		height: 100%;
		display: block;
		box-sizing: border-box;
		background-color: #fff;
		color: #2d8cf0;
		outline:none;
		border-radius: 4px;
		border: 1px solid #2d8cf0;
	}
	.file-upload-container{
		min-width: 150px;
		height: 30px;
		font-size: 14px;
		box-sizing: border-box;
		line-height: 28px;
		position: relative;
		text-align: center;
		color: #666;
	}
	.form-input{
		margin-top: 5px;
		color: #666;
		font-size: 14px;
	}
	.form-input .text-input{
		width: 300px;
		box-sizing: border-box;
		border: 1px solid #2d8cf0;
		border-radius: 4px;
		padding-left: 5px;
		line-height: 28px;
		outline: none;
		/*caret-color: #2d8cf0;*/
	}
	.form-input .textarea-input{
		color: #666;
		border: 1px solid #2d8cf0;
		border-radius: 4px;
		width: 100%;
		font-size: 14px;
		outline: none;
	}
	.form-button{
		width: 100%;
		margin-top: 10px;
		text-align: center;
	}
	.form-button input[type='submit']{
		width: 100px;
		height: 30px;
		background-color: #2d8cf0;
		color: #fff;
		text-align: center;
		outline: none;
		border: 1px solid #2d8cf0;
		border-radius: 4px;
	}
	.form-button input[type='submit']:hover{
		background-color: #fff;
		color: #2d8cf0;
		cursor: pointer;
	}
	</style>
</head>
<body>
	<div class="main">
		<section class='block'>
			<h1>通过建表语句生成增删改查工程</h1>
			<form method="post" action="/sql" enctype="multipart/form-data" class='form sql-form'>
				<div class="form-input">
					<label class='label sql-label'>选择一个SQL文件:</label>
					<div class="input-container file-upload-container">
						<input type="file" name="file" class='file-input' id='sqFile'><span id='sqlName'>选择文件</span>
					</div>
				</div>
				<div class="form-input">
					<label class='label sql-label'>工程根包名:</label>
					<div class="input-container">
						<input class='text-input' type="text" name="packName" size="35" placeholder="com.justdebutit.codegen.yourapp">
					</div>
				</div>
				<div class="form-button">
					<input type="submit" value="上传">
				</div>
			</form>
		</section>
		<section class="block">
			<h1>通过json生成javabean</h1>
			<form method="post" action="/json" class='form'>
				<div class="form-input">
					<label class='label'>请输入json</label>
					<textarea placeholder="贴入要生成Pojo的Json代码" name="json" class='textarea-input' rows='15'>
					{
						"animals|ams":
						{
						   "dog|dg":[
						   	{"name|title":"Rufus","breed":"labrador","count":1,"twoFeet":false},
							 {"name":"Marty","breed":"whippet","count":1,"twoFeet":false}
						   ],
						   "cat":{"name|title":"Matilda"}
					   	}
				  	}
					</textarea>
				</div>
				<div class="form-input">
					<label class='label'>类名:</label>
					<input type="text" class="text-input" name="jsonClazz" placeholder="com.justdebutit.codegen.yourappname">
				</div>
				<div class="form-button">
					<input type="submit" value="生成">
				</div>
			</form>
		</section>
	</div>
	<script>
	document.getElementById('sqFile').onchange = function(e){
		document.getElementById('sqlName').innerText = e.target.files[0].name;
	}
	
	</script>
</body>
</html>