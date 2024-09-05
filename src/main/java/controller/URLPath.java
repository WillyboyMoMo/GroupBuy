package controller;

public interface URLPath {
	String 登入首頁 = "/groupbuy/login.jsp";
	String 團購首頁 = "/groupbuy/frontend/main.jsp";
	String 新增完成頁 = "/groupbuy/frontend/result.jsp";
	String 購物車頁 = "/groupbuy/frontend/cart.jsp"; // 購物出項目刪除與修改
	String 結帳成功 = "/groupbuy/frontend/finish.jsp";
	
	String 後台首頁 = "/groupbuy/backend/main.jsp";
	String 後台商品新增 = "/groupbuy/backend/result.jsp";
	String 後台報表 = "/groupbuy/backend/report.jsp";
}