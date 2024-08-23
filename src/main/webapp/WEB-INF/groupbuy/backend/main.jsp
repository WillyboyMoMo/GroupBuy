<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>���ʺ�-��O�ӫ~���@</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="../../css/group_buy.css">
		<script type="text/javascript">
			function changeLaunch(productId, isLaunch) {
				location.href = './main.jsp?_method=Put&productId=' + productId + '&isLaunch=' + isLaunch;
			}
		</script>
	</head>
	<body>
		<!-- menu -->
		<%@include file="./menu.jspf" %>
		
		<div style="padding: 15px">
			<table>
				<tr>
					<td valign="top">
						<form method="post" action="./result.jsp" class="pure-form">
							<fieldset>
								<legend>���ʺ�-�ӫ~�s�W</legend>
								�~�W: <input type="text" id="productName" name="productName" value="���G��" /><p />
								����: <input type="number" id="productPrice" name="productPrice" value="40" /><p />
								���: <select id="productUnit" name="productUnit">
										<option value="��">��</option>
										<option value="�]" selected>�]</option>
										<option value="��">��</option>
										<option value="�c">�c</option>
										<option value="��">��</option>
									 </select>
								&nbsp;&nbsp;&nbsp;&nbsp;	 
								�W�[: <input type="checkbox" id="isLaunch" name="isLaunch" value="true" checked> 	 
								<p />
								<button type="submit" class="pure-button pure-button-primary">�s�W</button>
							</fieldset>
						</form>
					</td>
					<td valign="top" style="padding-left: 15px">
						<form class="pure-form">
							<fieldset>
								<legend>���ʺ�-��O�ӫ~���@</legend>
								<table class="pure-table pure-table-bordered">
									<thead>
										<tr><th>�Ǹ�</th><th>ID</th><th>�~�W</th><th>����</th><th>���</th><th>�W�[</th></tr>
									</thead>
									<tbody>
										<c:forEach items="${ products }" var="product" varStatus="status">
										<tr>
											<td>${ status.count+1 }</td>
											<td>${ product.productId }</td>
											<td>${ product.productName }</td>
											<td>${ product.price }</td>
											<td>${ product.unit }</td>
											<td><input 
													onClick="changeLaunch(${ product.productId }, this.checked)"
													type="checkbox" ${ (product.isLaunch) ? 'checked' : '' } 
													value="true"> �W�[</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
								<p />
							</fieldset>
						</form>
					</td>
				</tr>
			</table>
		</div>
		
		
		
		
	</body>
</html>