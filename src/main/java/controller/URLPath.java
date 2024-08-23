package controller;

public interface URLPath {
	String 登入首頁 = "/group_buy/login.jsp";
	String 團購首頁 = "/group_buy/frontend/main.jsp";
	String 新增完成頁 = "/group_buy/frontend/result.jsp";
	String 購物車頁 = "/group_buy/frontend/cart.jsp"; // 購物出項目刪除與修改
	String 結帳成功 = "/group_buy/frontend/finish.jsp";
	
	String 後台首頁 = "/group_buy/backend/main.jsp";
	String 後台商品新增 = "/group_buy/backend/result.jsp";
	String 後台報表 = "/group_buy/backend/report.jsp";
}